package com.gilbos.standa.util;

import com.gilbos.standa.service.state.SampleState;
import com.gilbos.standa.service.state.samplestateimpl.*;

public enum Samples {

    FIRST_FLOW_DATA(new FirstDataSampleState()),
    AVERAGE(new AverageSampleState()),
    cold_machine(new ColdSampleState()),
    speed_change_teach(new SpeedChangeSampleState()),
    speed_change_monitor(new SpeedChangeSampleState()),
    teach_sample(new TeachSampleState()),
    monitor_sample(new MonitorSampleState()),
    cold_average(new ColdSampleState()),
    teach_average(new TeachSampleState()),
    monitor_average(new MonitorSampleState());

    private SampleState sampleState;

    Samples(SampleState sampleState) {
        this.sampleState = sampleState;
    }

    public SampleState getSampleState() {
        return sampleState;
    }

}
