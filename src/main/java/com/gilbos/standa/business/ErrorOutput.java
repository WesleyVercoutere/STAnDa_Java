package com.gilbos.standa.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ErrorOutput {

    private String settings;
    private Set<String> smarTwistsWithoutErrors;
    private Set<String> smarTwistsWithoutStops;
    private List<String> errors;
    private List<String> stops;

    public ErrorOutput() {
        this.smarTwistsWithoutErrors = new TreeSet<>();
        this.errors = new ArrayList<>();
        this.smarTwistsWithoutStops = new TreeSet<>();
        this.stops = new ArrayList<>();
    }

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public Set<String> getSmarTwistsWithoutErrors() {
        return smarTwistsWithoutErrors;
    }

    public void setSmarTwistsWithoutErrors(Set<String> smarTwistsWithoutErrors) {
        this.smarTwistsWithoutErrors = smarTwistsWithoutErrors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public Set<String> getSmarTwistsWithoutStops() {
        return smarTwistsWithoutStops;
    }

    public void setSmarTwistsWithoutStops(Set<String> smarTwistsWithoutStops) {
        this.smarTwistsWithoutStops = smarTwistsWithoutStops;
    }

    public List<String> getStops() {
        return stops;
    }

    public void setStops(List<String> stops) {
        this.stops = stops;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Settings boundaries: ").append("\r\n");
        sb.append(settings);
        sb.append("\r\n");
        sb.append("\r\n");
        sb.append("Overview SmarTwist without errors: ").append("\r\n");

        if (!smarTwistsWithoutErrors.isEmpty()) {
            smarTwistsWithoutErrors.stream().sorted().forEach(e -> sb.append("\t").append("- ").append(e).append("\r\n"));
        } else {
            sb.append("\t").append("All SmarTwists have errors!").append("\r\n");
        }

        sb.append("\r\n");
        sb.append("\r\n");
        sb.append("Overview errors: ").append("\r\n");

        if (!errors.isEmpty()) {
            errors.forEach(error -> sb.append(error));
        } else {
            sb.append("No Errors").append("\r\n");
        }

        sb.append("\r\n");
        sb.append("\r\n");
        sb.append("Overview SmarTwist without stop: ").append("\r\n");

        if (!smarTwistsWithoutStops.isEmpty()) {
            smarTwistsWithoutStops.stream().sorted().forEach(e -> sb.append("\t").append("- ").append(e).append("\r\n"));
        } else {
            sb.append("\t").append("All SmarTwists have stopped!").append("\r\n");
        }

        sb.append("\r\n");
        sb.append("\r\n");
        sb.append("Overview stops: ").append("\r\n");

        if (!stops.isEmpty()) {
            stops.forEach(error -> sb.append(error));
        } else {
            sb.append("No stops").append("\r\n");
        }

        return sb.toString();
    }

}
