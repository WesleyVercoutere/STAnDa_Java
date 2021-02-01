package com.gilbos.standa.business;

public class FlowError {

    private SmarTwist smarTwist;
    private FlowData flowData;
    private FlowData average;

    public SmarTwist getSmarTwist() {
        return smarTwist;
    }

    public void setSmarTwist(SmarTwist smarTwist) {
        this.smarTwist = smarTwist;
    }

    public FlowData getFlowData() {
        return flowData;
    }

    public void setFlowData(FlowData flowData) {
        this.flowData = flowData;
    }

    public FlowData getAverage() {
        return average;
    }

    public void setAverage(FlowData average) {
        this.average = average;
    }

}
