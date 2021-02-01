package com.gilbos.standa.service.manager;

import com.gilbos.standa.business.*;
import com.gilbos.standa.repository.SmarTwistRepository;
import com.gilbos.standa.service.dto.AverageDTO;
import com.gilbos.standa.service.dto.SmarTwistDTO;
import com.gilbos.standa.service.mapper.impl.SmarTwistMapper;
import com.gilbos.standa.service.state.SampleContext;
import com.gilbos.standa.service.state.TypeContext;
import com.gilbos.standa.util.Samples;
import com.gilbos.standa.util.Type;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SmarTwistManager {

    private SmarTwistRepository repo;
    private SmarTwistMapper mapper;
    private SelectedFilters selectedFilters;
    private Settings settings;

    public SmarTwistManager(SmarTwistRepository smarTwistRepository, SmarTwistMapper smarTwistMapper, SelectedFilters selectedFilters, Settings settings) {
        this.repo = smarTwistRepository;
        this.mapper = smarTwistMapper;
        this.selectedFilters = selectedFilters;
        this.settings = settings;
    }

    public synchronized void addData(Data data) {
        SmarTwist st = repo.getAll().stream().filter(e -> e.getId().equals(data.getSmarTwistId())).findFirst()
                .orElse(addSmarTwist(data.getSmarTwistId()));

        TypeContext tc = new TypeContext(data.getType().getTypeState());
        tc.addData(st, data);
    }

    public synchronized void addAverage(FlowData fd) {
        SmarTwist st = repo.getAll().stream().filter(e -> e.getId().equals(fd.getSmarTwistId())).findFirst()
                .orElse(addSmarTwist(fd.getSmarTwistId()));

        TypeContext tc = new TypeContext(fd.getType().getTypeState());
        tc.addAverage(st, fd);
    }

    private SmarTwist addSmarTwist(String id) {
        SmarTwist st = new SmarTwist(id);
        repo.add(st);
        return st;
    }

    public synchronized List<SmarTwistDTO> getFilteredData() {
        return repo.getAll().stream()
                .filter(e -> ((selectedFilters.getSelectedST().equals("All")) || selectedFilters.getSelectedST().equals(e.getId())))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AverageDTO getAverages() {
        AverageDTO dto = new AverageDTO();
        List<Data> averages = getFilteredAverages();

        double boundary = 0;

        if (!averages.isEmpty()) {

            for (int i = 0; i < averages.size(); i++) {

                if (averages.get(i).getType().equals(Type.Tack1) || averages.get(i).getType().equals(Type.Tack2))
                    boundary = settings.getBoundaryTack();

                if (averages.get(i).getType().equals(Type.TwistS) || averages.get(i).getType().equals(Type.TwistZ))
                    boundary = settings.getBoundaryTwist();

                double lower = 1 - (boundary / 100);
                double upper = 1 + (boundary / 100);

                dto.getAverage().put(averages.get(i).getTimeStamp(), ((FlowData)averages.get(i)).getConsumption());
                dto.getLowerbound().put(averages.get(i).getTimeStamp(), ((FlowData)averages.get(i)).getConsumption() * lower);
                dto.getUpperbound().put(averages.get(i).getTimeStamp(), ((FlowData)averages.get(i)).getConsumption() * upper);

                if (i < averages.size() - 1) {
                    dto.getAverage().put((averages.get(i + 1).getTimeStamp() - 1), ((FlowData)averages.get(i)).getConsumption());
                    dto.getLowerbound().put((averages.get(i + 1).getTimeStamp() - 1), ((FlowData)averages.get(i)).getConsumption() * lower);
                    dto.getUpperbound().put((averages.get(i + 1).getTimeStamp() - 1), ((FlowData)averages.get(i)).getConsumption() * upper);
                }

                if (i == averages.size() - 1) {
                    dto.getAverage().put(((long) settings.getxAxisMaxUpperBound()), ((FlowData)averages.get(i)).getConsumption());
                    dto.getLowerbound().put(((long) settings.getxAxisMaxUpperBound()), ((FlowData)averages.get(i)).getConsumption() * lower);
                    dto.getUpperbound().put(((long) settings.getxAxisMaxUpperBound()), ((FlowData)averages.get(i)).getConsumption() * upper);
                }

            }
        }

        return dto;
    }

    private synchronized SmarTwistDTO convertToDTO(SmarTwist st) {
        SmarTwistDTO dto = mapper.mapToDTO(st);

        dto.getTack1().putAll(filterData(st.getFdTack1()));
        dto.getTack2().putAll(filterData(st.getFdTack2()));
        dto.getTwistS().putAll(filterData(st.getFdTwistS()));
        dto.getTwistZ().putAll(filterData(st.getFdTwistZ()));
        dto.getCtsS().putAll(filterData(st.getCtsTwistS()));
        dto.getCtsZ().putAll(filterData(st.getCtsTwistZ()));

        return dto;
    }

    private Map<Long, Double> filterData(Set<Data> data) {
        long time = 0;
        long interval = settings.getTimeBetweenSamples();
        long timeToZero = settings.getTimeToZero();

        Map<Long, Double> dataSet = new TreeMap<>();

        List<Data> list = data.stream().filter(this::checkDataFilters).filter(this::checkSampleFilter).collect(Collectors.toList());

        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).getTimeStamp() > time) {
                Data d = list.get(i);

                long timeStamp = d.getTimeStamp();
                double output = 0;

                if(d instanceof FlowData) {
                    output = ((FlowData) d).getConsumption();
                }

                if(d instanceof CTSData) {
                    output = ((CTSData) d).getAvgTwistLevel();
                }

                dataSet.put(timeStamp, output);
                time = timeStamp + interval;
            }

            if (i < list.size() - 1) {
                if ((list.get(i + 1).getTimeStamp() - list.get(i).getTimeStamp()) > timeToZero) {
                    dataSet.put((list.get(i).getTimeStamp() + 1), 0d);
                    dataSet.put((list.get(i + 1).getTimeStamp() - 1), 0d);
                }
            }
        }

        return dataSet;
    }

    private boolean checkDataFilters(Data data) {
        boolean check = false;

        if ((selectedFilters.getSelectedRecipe().equals("All") || selectedFilters.getSelectedRecipe().equals(data.getRecipe()))
                && (selectedFilters.getSelectedSpeed().equals("All") || selectedFilters.getSelectedSpeed().equals(data.getSpeed().toString()))
                && (selectedFilters.getSelectedType().equals("All") || selectedFilters.getSelectedType().equals(data.getType().toString()))) {
            check = true;
        }

        return check;
    }

    private boolean checkSampleFilter(Data data) {
        if (data instanceof FlowData) {
            Samples sample = Samples.valueOf(((FlowData) data).getSample());
            SampleContext ctx = new SampleContext(sample.getSampleState(), settings);
            return ctx.isShow();
        }

        return true;
    }

    private List<Data> getFilteredAverages() {
        List<Data> averages = new ArrayList<>();

        Optional<SmarTwist> opt = repo.getAll().stream().filter(e -> e.getId().equals(selectedFilters.getSelectedST())).findFirst();

        if (!opt.isPresent())
            return averages;

        Type type = Type.valueOf(selectedFilters.getSelectedType());
        TypeContext tc = new TypeContext(type.getTypeState());

        averages = tc.getAverage(opt.get())
                .stream()
                .filter(e -> {
                    return ((FlowData)e).getRecipe().equals(selectedFilters.getSelectedRecipe());
                })
                .filter(e -> e.getSpeed().toString().equals(selectedFilters.getSelectedSpeed()))
                .collect(Collectors.toList());

        return averages;
    }

    public void clear() {
        repo.clear();
    }

}
