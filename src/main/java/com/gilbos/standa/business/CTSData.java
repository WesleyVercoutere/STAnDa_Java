package com.gilbos.standa.business;

import com.gilbos.standa.util.Speed;
import com.gilbos.standa.util.Type;

import java.util.Objects;

public class CTSData extends Data implements Comparable<CTSData> {
    /*
    protected final String smarTwistId;
    protected final String recipe;
    protected final Speed speed;
    protected final long timeStamp;
    protected String fileName;
    */

    //private final Type type;
    private final double avgTwistLevel;
    private final int nbrOfSegments;
    private final double realSpeed;

    public CTSData(String fileName, String machineNumber, String recipe, Speed speed, Type type, long timeStamp, double realSpeed, double avgTwistLevel, int nbrOfSegments) {
        super(fileName, machineNumber, recipe, speed, type, timeStamp);
        this.avgTwistLevel = avgTwistLevel;
        this.nbrOfSegments = nbrOfSegments;
        this.realSpeed =realSpeed;
    }

    public double getAvgTwistLevel() {
        return avgTwistLevel;
    }

    public int getNbrOfSegments() {
        return nbrOfSegments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CTSData ctsData = (CTSData) o;
        return Double.compare(ctsData.avgTwistLevel, avgTwistLevel) == 0 && nbrOfSegments == ctsData.nbrOfSegments;
    }

    @Override
    public int hashCode() {
        return Objects.hash(avgTwistLevel, nbrOfSegments);
    }

    @Override
    public int compareTo(CTSData o) {
        if (timeStamp != o.getTimeStamp())
            return (int) (timeStamp - o.getTimeStamp());

        if (!speed.equals(o.getSpeed()))
            return speed.compareTo(o.getSpeed());

        if (!recipe.equals(o.getRecipe()))
            return  recipe.compareTo(o.getRecipe());

        return 0;
    }
}
