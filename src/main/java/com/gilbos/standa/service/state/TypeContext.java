package com.gilbos.standa.service.state;

import com.gilbos.standa.business.Data;
import com.gilbos.standa.business.SmarTwist;

import java.util.Set;

public class TypeContext {

    private TypeState typeState;

    public TypeContext(TypeState typeState) {
        super();
        this.typeState = typeState;
    }

    public void addData(SmarTwist st, Data data) {
        typeState.addData(st, data);
    }

    public void addAverage(SmarTwist st, Data data) {
        typeState.addAverage(st, data);
    }

    public Set<Data> getAverage(SmarTwist st) {
        return typeState.getAverage(st);
    }

}
