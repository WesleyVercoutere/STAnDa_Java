package com.gilbos.standa.service.dto;

import java.util.Map;
import java.util.TreeMap;

public class SmarTwistDTO {

    private String id;

    private Map<Long, Double> twistS;
    private Map<Long, Double> twistZ;
    private Map<Long, Double> tack1;
    private Map<Long, Double> tack2;
    private Map<Long, Double> ctsS;
    private Map<Long, Double> ctsZ;

    public SmarTwistDTO() {
        twistS = new TreeMap<>();
        twistZ = new TreeMap<>();
        tack1 = new TreeMap<>();
        tack2 = new TreeMap<>();
        ctsS = new TreeMap<>();
        ctsZ = new TreeMap<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<Long, Double> getTwistS() {
        return twistS;
    }

    public void setTwistS(Map<Long, Double> twistS) {
        this.twistS = twistS;
    }

    public Map<Long, Double> getTwistZ() {
        return twistZ;
    }

    public void setTwistZ(Map<Long, Double> twistZ) {
        this.twistZ = twistZ;
    }

    public Map<Long, Double> getTack1() {
        return tack1;
    }

    public void setTack1(Map<Long, Double> tack1) {
        this.tack1 = tack1;
    }

    public Map<Long, Double> getTack2() {
        return tack2;
    }

    public void setTack2(Map<Long, Double> tack2) {
        this.tack2 = tack2;
    }

    public Map<Long, Double> getCtsS() {
        return ctsS;
    }

    public void setCtsS(Map<Long, Double> ctsS) {
        this.ctsS = ctsS;
    }

    public Map<Long, Double> getCtsZ() {
        return ctsZ;
    }

    public void setCtsZ(Map<Long, Double> ctsZ) {
        this.ctsZ = ctsZ;
    }
}
