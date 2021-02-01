package com.gilbos.standa.business;

import com.gilbos.standa.util.Speed;
import com.gilbos.standa.util.Type;


public abstract class Data {

    protected String fileName;
    protected final String smarTwistId;
    protected final String recipe;
    protected final Speed speed;
    protected final Type type;
    protected final long timeStamp;


    public Data(String fileName, String machineNumber, String recipe, Speed speed, Type type, long timeStamp) {
        super();
        this.fileName = fileName;
        this.smarTwistId = machineNumber;
        this.recipe = recipe;
        this.speed = speed;
        this.type = type;
        this.timeStamp = timeStamp;
    }

    public String getFileName() {
        return fileName;
    }

    public String getSmarTwistId() {
        return smarTwistId;
    }

    public String getRecipe() {
        return recipe;
    }

    public Speed getSpeed() {
        return speed;
    }

    public Type getType() {
        return type;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
