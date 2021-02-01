package com.gilbos.standa.service.dto;

import java.util.Map;
import java.util.TreeMap;

public class AverageDTO {

    private Map<Long, Double> average;
    private Map<Long, Double> upperbound;
    private Map<Long, Double> lowerbound;

    public AverageDTO() {
        average = new TreeMap<>();
        upperbound = new TreeMap<>();
        lowerbound = new TreeMap<>();
    }

    public Map<Long, Double> getAverage() {
        return average;
    }

    public void setAverage(Map<Long, Double> average) {
        this.average = average;
    }

    public Map<Long, Double> getUpperbound() {
        return upperbound;
    }

    public void setUpperbound(Map<Long, Double> upperbound) {
        this.upperbound = upperbound;
    }

    public Map<Long, Double> getLowerbound() {
        return lowerbound;
    }

    public void setLowerbound(Map<Long, Double> lowerbound) {
        this.lowerbound = lowerbound;
    }

}
