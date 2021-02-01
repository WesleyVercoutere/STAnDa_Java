package com.gilbos.standa.service.dto;

public class SettingsDTO {

    private double yAxisUpperBound;
    private double yAxisLowerBound;
    private double yAxisTickUnit;
    private double xAxisMaxUpperBound;
    private double xAxisUpperBound;
    private double xAxisMinLowerBound;
    private double xAxisLowerBound;
    private double xAxisTickUnit;

    private String timeBetweenSamples;
    private String timeToZero;
    private boolean showSymbols;

    private boolean showManualAverage;
    private String manualAverage;
    private String manualBoundary;

    private boolean showAverage;
    private String boundaryTack;
    private String boundaryTwist;

    private double upperBound;
    private double lowerBound;

    private boolean showColdSamples;
    private boolean showTeachingSamples;
    private boolean showSpeedChangeSamples;
    private boolean showMonitorSamples;

    public double getyAxisUpperBound() {
        return yAxisUpperBound;
    }

    public void setyAxisUpperBound(double yAxisUpperBound) {
        this.yAxisUpperBound = yAxisUpperBound;
    }

    public double getyAxisLowerBound() {
        return yAxisLowerBound;
    }

    public void setyAxisLowerBound(double yAxisLowerBound) {
        this.yAxisLowerBound = yAxisLowerBound;
    }

    public double getyAxisTickUnit() {
        return yAxisTickUnit;
    }

    public void setyAxisTickUnit(double yAxisTickUnit) {
        this.yAxisTickUnit = yAxisTickUnit;
    }

    public double getxAxisMaxUpperBound() {
        return xAxisMaxUpperBound;
    }

    public void setxAxisMaxUpperBound(double xAxisMaxUpperBound) {
        this.xAxisMaxUpperBound = xAxisMaxUpperBound;
    }

    public double getxAxisUpperBound() {
        return xAxisUpperBound;
    }

    public void setxAxisUpperBound(double xAxisUpperBound) {
        this.xAxisUpperBound = xAxisUpperBound;
    }

    public double getxAxisMinLowerBound() {
        return xAxisMinLowerBound;
    }

    public void setxAxisMinLowerBound(double xAxisMinLowerBound) {
        this.xAxisMinLowerBound = xAxisMinLowerBound;
    }

    public double getxAxisLowerBound() {
        return xAxisLowerBound;
    }

    public void setxAxisLowerBound(double xAxisLowerBound) {
        this.xAxisLowerBound = xAxisLowerBound;
    }

    public double getxAxisTickUnit() {
        return xAxisTickUnit;
    }

    public void setxAxisTickUnit(double xAxisTickUnit) {
        this.xAxisTickUnit = xAxisTickUnit;
    }

    public String getTimeBetweenSamples() {
        return timeBetweenSamples;
    }

    public void setTimeBetweenSamples(String timeBetweenSamples) {
        this.timeBetweenSamples = timeBetweenSamples;
    }

    public String getTimeToZero() {
        return timeToZero;
    }

    public void setTimeToZero(String timeToZero) {
        this.timeToZero = timeToZero;
    }

    public boolean isShowManualAverage() {
        return showManualAverage;
    }

    public void setShowManualAverage(boolean showManualAverage) {
        this.showManualAverage = showManualAverage;
    }

    public String getManualAverage() {
        return manualAverage;
    }

    public void setManualAverage(String manualAverage) {
        this.manualAverage = manualAverage;
    }

    public String getManualBoundary() {
        return manualBoundary;
    }

    public void setManualBoundary(String manualBoundary) {
        this.manualBoundary = manualBoundary;
    }

    public boolean isShowAverage() {
        return showAverage;
    }

    public void setShowAverage(boolean showAverage) {
        this.showAverage = showAverage;
    }

    public String getBoundaryTack() {
        return boundaryTack;
    }

    public void setBoundaryTack(String boundaryTack) {
        this.boundaryTack = boundaryTack;
    }

    public String getBoundaryTwist() {
        return boundaryTwist;
    }

    public void setBoundaryTwist(String boundaryTwist) {
        this.boundaryTwist = boundaryTwist;
    }

    public boolean isShowSymbols() {
        return showSymbols;
    }

    public void setShowSymbols(boolean showSymbols) {
        this.showSymbols = showSymbols;
    }

    public double getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(double upperBound) {
        this.upperBound = upperBound;
    }

    public double getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(double lowerBound) {
        this.lowerBound = lowerBound;
    }

    public boolean isShowColdSamples() {
        return showColdSamples;
    }

    public void setShowColdSamples(boolean showColdSamples) {
        this.showColdSamples = showColdSamples;
    }

    public boolean isShowTeachingSamples() {
        return showTeachingSamples;
    }

    public void setShowTeachingSamples(boolean showTeachingSamples) {
        this.showTeachingSamples = showTeachingSamples;
    }

    public boolean isShowSpeedChangeSamples() {
        return showSpeedChangeSamples;
    }

    public void setShowSpeedChangeSamples(boolean showSpeedChangeSamples) {
        this.showSpeedChangeSamples = showSpeedChangeSamples;
    }

    public boolean isShowMonitorSamples() {
        return showMonitorSamples;
    }

    public void setShowMonitorSamples(boolean showMonitorSamples) {
        this.showMonitorSamples = showMonitorSamples;
    }

}
