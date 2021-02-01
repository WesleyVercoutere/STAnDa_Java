package com.gilbos.standa.service.state;

import com.gilbos.standa.business.Data;
import com.gilbos.standa.business.SmarTwist;

import java.util.Set;

public interface TypeState {

    void addData(SmarTwist st, Data data);

    void addAverage(SmarTwist st, Data data);

    Set<Data> getAverage(SmarTwist st);

}
