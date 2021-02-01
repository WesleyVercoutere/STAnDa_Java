package com.gilbos.standa.service.state.typestateimpl;

import com.gilbos.standa.business.Data;
import com.gilbos.standa.business.FlowData;
import com.gilbos.standa.business.SmarTwist;
import com.gilbos.standa.service.state.TypeState;

import java.util.Set;

public class TwistSState implements TypeState {

    @Override
    public void addData(SmarTwist st, Data data) {
        st.getFdTwistS().add((FlowData)data);
    }

    @Override
    public void addAverage(SmarTwist st, Data data) {
        st.getAvgTwistS().add((FlowData)data);
    }

    @Override
    public Set<Data> getAverage(SmarTwist st) {
        return st.getAvgTwistS();
    }

}
