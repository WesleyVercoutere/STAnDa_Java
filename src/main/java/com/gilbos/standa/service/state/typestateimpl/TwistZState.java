package com.gilbos.standa.service.state.typestateimpl;

import com.gilbos.standa.business.Data;
import com.gilbos.standa.business.FlowData;
import com.gilbos.standa.business.SmarTwist;
import com.gilbos.standa.service.state.TypeState;

import java.util.Set;

public class TwistZState implements TypeState {

    @Override
    public void addData(SmarTwist st, Data data) {
        st.getFdTwistZ().add((FlowData)data);
    }

    @Override
    public void addAverage(SmarTwist st, Data data) {
        st.getAvgTwistZ().add((FlowData)data);
    }

    @Override
    public Set<Data> getAverage(SmarTwist st) {
        return st.getAvgTwistZ();
    }

}
