package com.gilbos.standa.service.manager;

import com.gilbos.standa.business.*;
import com.gilbos.standa.repository.FilterRepository;
import com.gilbos.standa.repository.FlowErrorRepository;
import com.gilbos.standa.repository.SmarTwistRepository;
import com.gilbos.standa.util.DateUtil;
import com.gilbos.standa.util.Type;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FlowErrorManager {

    private FlowErrorRepository flowErrorRepository;
    private FlowErrorRepository flowStopRepository;
    private SmarTwistRepository smarTwistRepository;
    private FilterRepository filterRepository;
    private Settings settings;

    private ErrorOutput errorOutput;

    public FlowErrorManager(FlowErrorRepository flowErrorRepository, FlowErrorRepository flowStopRepository, SmarTwistRepository smarTwistRepository, FilterRepository filterRepository, Settings settings, ErrorOutput errorOutput) {
        this.flowErrorRepository = flowErrorRepository;
        this.flowStopRepository = flowStopRepository;
        this.smarTwistRepository = smarTwistRepository;
        this.filterRepository = filterRepository;
        this.settings = settings;
        this.errorOutput = errorOutput;
    }

    public void exportErrorsToFile(String file) {
        //reset flow error repo
        flowErrorRepository.clear();
        flowStopRepository.clear();
        //calculate errors and add to repo
        calculateErrors();
        //build string for file
        createErrorOutput();
        //export to file
        exportToFile(file);
    }

    private void calculateErrors() {
        smarTwistRepository.getAll().forEach(this::filterRecipe);
    }

    private void filterRecipe(SmarTwist st) {
        /*
        filterRepository.getRecipes().forEach(recipe ->
                Arrays.asList(Speed.values()).forEach(speed -> {

                    List<FlowData> filteredFlowDataTack1 = st.getFdTack1().stream().filter(fd -> fd.getRecipe().equals(recipe) && fd.getSpeed().equals(speed)).collect(Collectors.toList());
                    List<FlowData> filteredFlowDataTack2 = st.getFdTack2().stream().filter(fd -> fd.getRecipe().equals(recipe) && fd.getSpeed().equals(speed)).collect(Collectors.toList());
                    List<FlowData> filteredFlowDataTwistS = st.getFdTwistS().stream().filter(fd -> fd.getRecipe().equals(recipe) && fd.getSpeed().equals(speed)).collect(Collectors.toList());
                    List<FlowData> filteredFlowDataTwistZ = st.getFdTwistZ().stream().filter(fd -> fd.getRecipe().equals(recipe) && fd.getSpeed().equals(speed)).collect(Collectors.toList());

                    List<FlowData> filteredAverageTack1 = st.getAvgTack1().stream().filter(fd -> fd.getRecipe().equals(recipe) && fd.getSpeed().equals(speed)).collect(Collectors.toList());
                    List<FlowData> filteredAverageTack2 = st.getAvgTack2().stream().filter(fd -> fd.getRecipe().equals(recipe) && fd.getSpeed().equals(speed)).collect(Collectors.toList());
                    List<FlowData> filteredAverageTwistS = st.getAvgTwistS().stream().filter(fd -> fd.getRecipe().equals(recipe) && fd.getSpeed().equals(speed)).collect(Collectors.toList());
                    List<FlowData> filteredAverageTwistZ = st.getAvgTwistZ().stream().filter(fd -> fd.getRecipe().equals(recipe) && fd.getSpeed().equals(speed)).collect(Collectors.toList());

                    checkFlowData(st, filteredFlowDataTack1, filteredAverageTack1);
                    checkFlowData(st, filteredFlowDataTack2, filteredAverageTack2);
                    checkFlowData(st, filteredFlowDataTwistS, filteredAverageTwistS);
                    checkFlowData(st, filteredFlowDataTwistZ, filteredAverageTwistZ);

                    checkStop(st, filteredFlowDataTack1.stream().filter(fd -> fd.getSample().equals("monitor_sample")).collect(Collectors.toList()), filteredAverageTack1.stream().filter(fd -> fd.getConsumption() > 0).collect(Collectors.toList()));
                    checkStop(st, filteredFlowDataTack2.stream().filter(fd -> fd.getSample().equals("monitor_sample")).collect(Collectors.toList()), filteredAverageTack2.stream().filter(fd -> fd.getConsumption() > 0).collect(Collectors.toList()));
                    checkStop(st, filteredFlowDataTwistS.stream().filter(fd -> fd.getSample().equals("monitor_sample")).collect(Collectors.toList()), filteredAverageTwistS.stream().filter(fd -> fd.getConsumption() > 0).collect(Collectors.toList()));
                    checkStop(st, filteredFlowDataTwistZ.stream().filter(fd -> fd.getSample().equals("monitor_sample")).collect(Collectors.toList()), filteredAverageTwistZ.stream().filter(fd -> fd.getConsumption() > 0).collect(Collectors.toList()));
                })
        ); */
    }

    private void checkFlowData(SmarTwist st, List<FlowData> data, List<FlowData> avg) {

        if (data.isEmpty())
            return;

        data.forEach(fd -> {

            if (checkFlowError(fd, avg)) {
                FlowError error = new FlowError();
                error.setFlowData(fd);
                error.setSmarTwist(st);
                error.setAverage(avg.stream().filter(e -> fd.getTimeStamp() > e.getTimeStamp()).reduce((first, second) -> second).get());
                flowErrorRepository.add(error);
            }
        });
    }

    private boolean checkFlowError(FlowData fd, List<FlowData> avg) {

        Optional<FlowData> opt = avg.stream().filter(e -> fd.getTimeStamp() > e.getTimeStamp()).reduce((first, second) -> second);

        if (!opt.isPresent())
            return false;

        FlowData average = opt.get();

        if (average.getConsumption() == 0)
            return false;

        double boundary = 0;

        if (average.getType().equals(Type.Tack1) || average.getType().equals(Type.Tack2))
            boundary = settings.getBoundaryTack();

        if (average.getType().equals(Type.TwistS) || average.getType().equals(Type.TwistZ))
            boundary = settings.getBoundaryTwist();

        double lower = average.getConsumption() * (1 - (boundary / 100));
        double upper = average.getConsumption() * (1 + (boundary / 100));

        return (fd.getConsumption() < lower || fd.getConsumption() > upper);
    }

    private void checkStop(SmarTwist st, List<FlowData> data, List<FlowData> avg) {

        if (data.isEmpty() || avg.isEmpty())
            return;

        for (int i = 1; i < data.size(); i++) {
            FlowData average = getAverage(data.get(i).getTimeStamp(), avg);

            double boundary = 0;

            if (average.getType().equals(Type.Tack1) || average.getType().equals(Type.Tack2))
                boundary = settings.getBoundaryTack();

            if (average.getType().equals(Type.TwistS) || average.getType().equals(Type.TwistZ))
                boundary = settings.getBoundaryTwist();

            double lower = average.getConsumption() * (1 - (boundary / 100));
            double upper = average.getConsumption() * (1 + (boundary / 100));


            if (((data.get(i).getConsumption() > upper) && (data.get(i - 1).getConsumption() > upper)) ||
                    (data.get(i).getConsumption() < lower) && (data.get(i - 1).getConsumption() < lower)) {

                FlowError error = new FlowError();
                error.setFlowData(data.get(i));
                error.setSmarTwist(st);
                error.setAverage(average);
                flowStopRepository.add(error);

                return;
            }
        }
    }

    private FlowData getAverage(long timeStamp, List<FlowData> avg) {
        Optional<FlowData> opt = avg.stream().filter(e -> timeStamp > e.getTimeStamp()).reduce((first, second) -> second);

        if (!opt.isPresent())
            return null;

        return opt.get();
    }

    private void createErrorOutput() {
        errorOutput = new ErrorOutput();

        createSettings();
        createSmarTwistsWithoutErrors();
        createSmarTwistsWithoutStops();
        createErrors();
    }

    private void createSettings() {
        StringBuilder sb = new StringBuilder();

        sb.append("Tack boundaries = ").append(settings.getBoundaryTack()).append(" [%]").append("\r\n");
        sb.append("Twist boundaries = ").append(settings.getBoundaryTwist()).append(" [%]").append("\r\n");

        errorOutput.setSettings(sb.toString());
    }

    private void createSmarTwistsWithoutErrors() {
        Set<String> stWithErrors = flowErrorRepository.getAll().stream().map(e -> e.getSmarTwist().getId()).collect(Collectors.toSet());

        Set<String> stWithoutErrors = smarTwistRepository.getAll().stream().map(e -> e.getId()).collect(Collectors.toSet());
        stWithoutErrors.removeAll(stWithErrors);

        errorOutput.setSmarTwistsWithoutErrors(stWithoutErrors);
    }

    private void createSmarTwistsWithoutStops() {
        Set<String> stWithStops = flowStopRepository.getAll().stream().map(e -> e.getSmarTwist().getId()).collect(Collectors.toSet());

        Set<String> stWithoutStops = smarTwistRepository.getAll().stream().map(e -> e.getId()).collect(Collectors.toSet());
        stWithoutStops.removeAll(stWithStops);

        errorOutput.setSmarTwistsWithoutStops(stWithoutStops);
    }

    private void createErrors() {

        flowErrorRepository.getAll().forEach(error -> {

            StringBuilder sb = new StringBuilder();

            sb.append("\t").append("ST id: ").append(error.getSmarTwist().getId()).append("\r\n");
            sb.append("\t").append("Recipe: ").append(error.getFlowData().getRecipe()).append("\r\n");
            sb.append("\t").append("Signal & speed: ").append(error.getFlowData().getType()).append(" ").append(error.getFlowData().getSpeed()).append("\r\n");
            sb.append("\t").append("Sample : ").append(error.getFlowData().getSample()).append("\r\n");
            sb.append("\t").append("Consumption / average : ").append(error.getFlowData().getConsumption()).append(" / ").append(error.getAverage().getConsumption()).append("\r\n");
            sb.append("\t").append("Time: ").append(DateUtil.fromEpochMilli(error.getFlowData().getTimeStamp())).append("\r\n");
            sb.append("\r\n");

            errorOutput.getErrors().add(sb.toString());
        });

        flowStopRepository.getAll().forEach(error -> {

            StringBuilder sb = new StringBuilder();

            sb.append("\t").append("ST id: ").append(error.getSmarTwist().getId()).append("\r\n");
            sb.append("\t").append("Recipe: ").append(error.getFlowData().getRecipe()).append("\r\n");
            sb.append("\t").append("Signal & speed: ").append(error.getFlowData().getType()).append(" ").append(error.getFlowData().getSpeed()).append("\r\n");
            sb.append("\t").append("Sample : ").append(error.getFlowData().getSample()).append("\r\n");
            sb.append("\t").append("Consumption / average : ").append(error.getFlowData().getConsumption()).append(" / ").append(error.getAverage().getConsumption()).append("\r\n");
            sb.append("\t").append("Time: ").append(DateUtil.fromEpochMilli(error.getFlowData().getTimeStamp())).append("\r\n");
            sb.append("\r\n");

            errorOutput.getStops().add(sb.toString());
        });

    }

    private void exportToFile(String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.append(errorOutput.toString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
