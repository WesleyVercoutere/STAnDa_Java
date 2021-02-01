package com.gilbos.standa.service.manager;

import com.gilbos.standa.business.Data;
import com.gilbos.standa.business.FlowData;
import com.gilbos.standa.business.STCAverageError;
import com.gilbos.standa.repository.FilterRepository;
import com.gilbos.standa.repository.STCAverageRepository;
import com.gilbos.standa.repository.SmarTwistRepository;
import com.gilbos.standa.util.Speed;
import com.gilbos.standa.util.Type;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class STCAverageManager {

    private STCAverageRepository stcAverageErrorRepo;
    private STCAverageRepository stcAverageSuccessRepo;
    private SmarTwistRepository smarTwistRepository;
    private FilterRepository filterRepository;

    private StringBuilder output;

    public STCAverageManager(STCAverageRepository stcAverageErrorRepo, STCAverageRepository stcAverageSuccessRepo, SmarTwistRepository smarTwistRepository, FilterRepository filterRepository) {
        this.stcAverageErrorRepo = stcAverageErrorRepo;
        this.stcAverageSuccessRepo = stcAverageSuccessRepo;
        this.smarTwistRepository = smarTwistRepository;
        this.filterRepository = filterRepository;
    }

    public void exportAveragesToFile(String file) {
        //reset stc averages repo
        stcAverageErrorRepo.clear();
        stcAverageSuccessRepo.clear();
        //calculate averages and add to repo
            // per recept -> voor elke st en elk signaal en elke snelheid -> het laatst gekende gemiddelde in een lijst toevoegen
            // van die lijst het stc gemiddelde bepalen
        calculateAverages();
        // build output string: overview per recipe
        createOutput();
        //export to file
        exportToFile(file);
    }

    private void calculateAverages() {
        filterRepository.getRecipes().forEach(this::filterData);
    }

    private void filterData(String recipe) {
        Arrays.asList(Speed.values()).forEach(speed -> {

            List<FlowData> averagesTack1 = new ArrayList<>();
            List<FlowData> averagesTack2 = new ArrayList<>();
            List<FlowData> averagesTwistS = new ArrayList<>();
            List<FlowData> averagesTwistZ = new ArrayList<>();

            smarTwistRepository.getAll().forEach(st -> {
                Optional<Data> avgTack1 = st.getAvgTack1().stream().filter(fd -> fd.getRecipe().equals(recipe) && fd.getSpeed().equals(speed)).sorted().reduce((first, second) -> second);
                Optional<Data> avgTack2 = st.getAvgTack2().stream().filter(fd -> fd.getRecipe().equals(recipe) && fd.getSpeed().equals(speed)).sorted().reduce((first, second) -> second);
                Optional<Data> avgTwistS = st.getAvgTwistS().stream().filter(fd -> fd.getRecipe().equals(recipe) && fd.getSpeed().equals(speed)).sorted().reduce((first, second) -> second);
                Optional<Data> avgTwistZ = st.getAvgTwistZ().stream().filter(fd -> fd.getRecipe().equals(recipe) && fd.getSpeed().equals(speed)).sorted().reduce((first, second) -> second);

                avgTack1.ifPresent(e -> {
                    if (((FlowData)e).getConsumption() > 0)
                        averagesTack1.add(((FlowData)e));
                });
                avgTack2.ifPresent(e -> {
                    if (((FlowData)e).getConsumption() > 0)
                        averagesTack2.add(((FlowData)e));
                });
                avgTwistS.ifPresent(e -> {
                    if (((FlowData)e).getConsumption() > 0)
                        averagesTwistS.add(((FlowData)e));
                });
                avgTwistZ.ifPresent(e -> {
                    if (((FlowData)e).getConsumption() > 0)
                        averagesTwistZ.add(((FlowData)e));
                });
            });

            stcLogic(averagesTack1);
            stcLogic(averagesTack2);
            stcLogic(averagesTwistS);
            stcLogic(averagesTwistZ);
        });
    }

    private void stcLogic(List<FlowData> averages) {

        List<FlowData> toCheck = averages;

        //calculate average from list
        OptionalDouble average = toCheck.stream().mapToDouble(FlowData::getConsumption).average();

        if (!average.isPresent()) return;


        //check boundaries
        Map<FlowData, Double> boundaries = new HashMap<>();

        toCheck.forEach(fd -> {
            double deviation = fd.getConsumption() / average.getAsDouble();
            boundaries.put(fd, deviation);
        });

        FlowData largestDeviation = Collections.max(boundaries.entrySet(), Map.Entry.comparingByValue()).getKey();

        //remove greatest deviation
        if (largestDeviation.getConsumption() > average.getAsDouble() * 1.1 ||
            largestDeviation.getConsumption() < average.getAsDouble() *0.9) {

            STCAverageError error = new STCAverageError();
            error.setAverage(average.getAsDouble());
            error.setFlowData(largestDeviation);
            stcAverageErrorRepo.add(error);

            toCheck.remove(largestDeviation);
            stcLogic(toCheck);
        } else {
            STCAverageError success = new STCAverageError();
            success.setAverage(average.getAsDouble());
            success.setFlowData(toCheck.get(0));
            success.setSuccess(toCheck);
            stcAverageSuccessRepo.add(success);
        }
    }

    private void createOutput() {
        output = new StringBuilder();
        output.append("Overview STC average");

        filterRepository.getRecipes().forEach(recipe -> {
            output.append("\r\n").append("\r\n");
            output.append("Recipe: ").append(recipe).append("\r\n");

            Arrays.asList(Speed.values()).forEach(speed -> {
                Arrays.asList(Type.values()).forEach(type -> {
                    output.append("\r\n").append("\t").append(speed).append(" ").append(type).append("\r\n");
                    output.append("\t\t").append("Average runs:").append("\r\n");

                    List<STCAverageError> errors = stcAverageErrorRepo.getAll().stream().filter(e -> e.getFlowData().getRecipe().equals(recipe) && e.getFlowData().getSpeed().equals(speed) && e.getFlowData().getType().equals(type)).collect(Collectors.toList());

                    errors.forEach(error -> {
                        output.append("\t\t").append("- ").append(error.getFlowData().getSmarTwistId()).append("\t\t").append(String.format("%.6f", (error.getFlowData().getConsumption()))).append("\t\t").append(String.format("%.6f", (error.getAverage()))).append("\r\n");
                    });

                    List<STCAverageError> success = stcAverageSuccessRepo.getAll().stream().filter(e -> e.getFlowData().getRecipe().equals(recipe) && e.getFlowData().getSpeed().equals(speed) && e.getFlowData().getType().equals(type)).collect(Collectors.toList());

                    output.append("\r\n").append("\t\t").append("ST's within boundaries:").append("\r\n");

                    success.forEach(suc -> {
                        suc.getSuccess().forEach(e -> {
                            output.append("\t\t").append("- ").append(e.getSmarTwistId()).append("\t\t").append(String.format("%.6f", (e.getConsumption()))).append("\t\t").append(String.format("%.6f", (suc.getAverage()))).append("\r\n");
                        });
                    });
                });
            });
        });
    }

    private void exportToFile(String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.append(output.toString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
