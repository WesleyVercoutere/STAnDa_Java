package com.gilbos.standa.service.state.typestateimpl;

import com.gilbos.standa.business.CTSData;
import com.gilbos.standa.business.Data;
import com.gilbos.standa.business.SmarTwist;
import com.gilbos.standa.service.state.TypeState;

import java.util.Set;

public class CtsSState implements TypeState {

    @Override
    public void addData(SmarTwist st, Data data) {
        st.getCtsTwistS().add((CTSData) data);
    }

    @Override
    public void addAverage(SmarTwist st, Data data) {

    }

    @Override
    public Set<Data> getAverage(SmarTwist st) {
        return null;
    }
}
