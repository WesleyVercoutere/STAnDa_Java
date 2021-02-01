package com.gilbos.standa.util;

import com.gilbos.standa.service.state.TypeState;
import com.gilbos.standa.service.state.typestateimpl.*;

public enum Type {
    TwistS(new TwistSState()),
    TwistZ(new TwistZState()),
    Tack1(new Tack1State()),
    Tack2(new Tack2State()),
    CtsS(new CtsSState()),
    CtsZ(new CtsZState());

    private TypeState typeState;

    Type(TypeState typeState) {
        this.typeState = typeState;
    }

    public TypeState getTypeState() {
        return typeState;
    }
}
