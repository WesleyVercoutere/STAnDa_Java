package com.gilbos.standa.business;

import com.gilbos.standa.util.Speed;
import com.gilbos.standa.util.Type;

public class FlowData extends Data implements Comparable<FlowData> {

    private final double consumption;
    private String sample;

    public FlowData(String fileName, String machineNumber, String recipe, Speed speed, Type type, String sample, long timeStamp,
                    double consumption) {
        super(fileName, machineNumber, recipe, speed, type, timeStamp);
        this.sample = sample;
        this.consumption = consumption;
    }

    public double getConsumption() {
        return consumption;
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(consumption);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((smarTwistId == null) ? 0 : smarTwistId.hashCode());
        result = prime * result + ((recipe == null) ? 0 : recipe.hashCode());
        result = prime * result + ((speed == null) ? 0 : speed.hashCode());
        result = prime * result + (int) (timeStamp ^ (timeStamp >>> 32));
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FlowData other = (FlowData) obj;
        if (Double.doubleToLongBits(consumption) != Double.doubleToLongBits(other.consumption))
            return false;
        if (smarTwistId == null) {
            if (other.smarTwistId != null)
                return false;
        } else if (!smarTwistId.equals(other.smarTwistId))
            return false;
        if (recipe == null) {
            if (other.recipe != null)
                return false;
        } else if (!recipe.equals(other.recipe))
            return false;
        if (speed != other.speed)
            return false;
        if (timeStamp != other.timeStamp)
            return false;
        return type == other.type;
    }

    @Override
    public int compareTo(FlowData o) {

        if (timeStamp != o.getTimeStamp())
            return (int) (timeStamp - o.getTimeStamp());

        if (!type.equals(o.getType()))
            return type.compareTo(o.getType());

        if (!speed.equals(o.getSpeed()))
            return speed.compareTo(o.getSpeed());

        if (!recipe.equals(o.getRecipe()))
            return  recipe.compareTo(o.getRecipe());

        return 0;
    }
}
