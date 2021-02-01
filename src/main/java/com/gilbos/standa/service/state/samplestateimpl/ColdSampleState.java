package com.gilbos.standa.service.state.samplestateimpl;

import com.gilbos.standa.business.Settings;
import com.gilbos.standa.service.state.SampleState;

public class ColdSampleState extends SampleState {

    @Override
    public boolean isShow(Settings settings) {
        return settings.isShowColdSamples();
    }

}
