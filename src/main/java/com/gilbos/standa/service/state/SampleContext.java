package com.gilbos.standa.service.state;

import com.gilbos.standa.business.Settings;

public class SampleContext {

    private SampleState sampleState;
    private Settings settings;

    public SampleContext(SampleState sampleState, Settings settings) {
        this.sampleState = sampleState;
        this.settings = settings;
    }

    public boolean isShow() {
        return sampleState.isShow(settings);
    }
}
