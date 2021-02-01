package com.gilbos.standa.business;

import java.util.Set;
import java.util.TreeSet;

public class SmarTwist implements Comparable<SmarTwist> {

    private String id;
    private String customer;
    private String division;

    private Set<Data> fdTwistS;
    private Set<Data> fdTwistZ;
    private Set<Data> fdTack1;
    private Set<Data> fdTack2;

    private Set<Data> avgTwistS;
    private Set<Data> avgTwistZ;
    private Set<Data> avgTack1;
    private Set<Data> avgTack2;

    private Set<Data> ctsTwistS;
    private Set<Data> ctsTwistZ;

    public SmarTwist() {
        super();
        this.fdTack1 = new TreeSet<>();
        this.fdTack2 = new TreeSet<>();
        this.fdTwistS = new TreeSet<>();
        this.fdTwistZ = new TreeSet<>();

        this.avgTack1 = new TreeSet<>();
        this.avgTack2 = new TreeSet<>();
        this.avgTwistS = new TreeSet<>();
        this.avgTwistZ = new TreeSet<>();

        this.ctsTwistS = new TreeSet<>();
        this.ctsTwistZ = new TreeSet<>();
    }

    public SmarTwist(String number) {
        this();
        this.id = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String number) {
        this.id = number;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public Set<Data> getFdTwistS() {
        return fdTwistS;
    }

    public void setFdTwistS(Set<Data> fdTwistS) {
        this.fdTwistS = fdTwistS;
    }

    public Set<Data> getFdTwistZ() {
        return fdTwistZ;
    }

    public void setFdTwistZ(Set<Data> fdTwistZ) {
        this.fdTwistZ = fdTwistZ;
    }

    public Set<Data> getFdTack1() {
        return fdTack1;
    }

    public void setFdTack1(Set<Data> fdTack1) {
        this.fdTack1 = fdTack1;
    }

    public Set<Data> getFdTack2() {
        return fdTack2;
    }

    public void setFdTack2(Set<Data> fdTack2) {
        this.fdTack2 = fdTack2;
    }

    public Set<Data> getAvgTwistS() {
        return avgTwistS;
    }

    public void setAvgTwistS(Set<Data> avgTwistS) {
        this.avgTwistS = avgTwistS;
    }

    public Set<Data> getAvgTwistZ() {
        return avgTwistZ;
    }

    public void setAvgTwistZ(Set<Data> avgTwistZ) {
        this.avgTwistZ = avgTwistZ;
    }

    public Set<Data> getAvgTack1() {
        return avgTack1;
    }

    public void setAvgTack1(Set<Data> avgTack1) {
        this.avgTack1 = avgTack1;
    }

    public Set<Data> getAvgTack2() {
        return avgTack2;
    }

    public void setAvgTack2(Set<Data> avgTack2) {
        this.avgTack2 = avgTack2;
    }

    public Set<Data> getCtsTwistS() {
        return ctsTwistS;
    }

    public void setCtsTwistS(Set<Data> ctsTwistS) {
        this.ctsTwistS = ctsTwistS;
    }

    public Set<Data> getCtsTwistZ() {
        return ctsTwistZ;
    }

    public void setCtsTwistZ(Set<Data> ctsTwistZ) {
        this.ctsTwistZ = ctsTwistZ;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        SmarTwist other = (SmarTwist) obj;
        if (id == null) {
            return other.id == null;
        } else return id.equals(other.id);
    }

    @Override
    public int compareTo(SmarTwist o) {

        if (!id.equals(o.getId()))
            return id.compareTo(o.getId());

        return 0;
    }

}
