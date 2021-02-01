package com.gilbos.standa.business;

import java.util.ArrayList;
import java.util.List;

public class STCAverageError {

    private double average;
    private FlowData flowData;
    private List<FlowData> success;

    public STCAverageError() {
        success = new ArrayList<>();
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public FlowData getFlowData() {
        return flowData;
    }

    public void setFlowData(FlowData flowData) {
        this.flowData = flowData;
    }

    public List<FlowData> getSuccess() {
        return success;
    }

    public void setSuccess(List<FlowData> success) {
        this.success = success;
    }

}
