package com.gilbos.standa.service.state.typestateimpl;

import com.gilbos.standa.business.Data;
import com.gilbos.standa.business.FlowData;
import com.gilbos.standa.business.SmarTwist;
import com.gilbos.standa.service.state.TypeState;

import java.util.Set;

public class Tack2State implements TypeState {

    @Override
    public void addData(SmarTwist st, Data data) {
        st.getFdTack2().add((FlowData)data);
    }

    @Override
    public void addAverage(SmarTwist st, Data data) {
        st.getAvgTack2().add((FlowData)data);
    }

    @Override
    public Set<Data> getAverage(SmarTwist st) {
        return st.getAvgTack2();
    }

}
