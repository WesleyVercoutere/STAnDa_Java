package com.gilbos.standa.business;

public class Settings {

    private double yAxisUpperBound;
    private double yAxisLowerBound;
    private double yAxisTickUnit;

    private double xAxisMaxUpperBound;
    private double xAxisUpperBound;
    private double xAxisMinLowerBound;
    private double xAxisLowerBound;
    private double xAxisTickUnit;

    private long timeBetweenSamples;
    private long timeToZero;
    private boolean showSymbols;

    private boolean showManualAverage;
    private double manualAverage;
    private double manualBoundary;

    private boolean showAverage;
    private double boundaryTack;
    private double boundaryTwist;


    private double upperBound;
    private double lowerBound;

    private boolean showColdSamples;
    private boolean showTeachingSamples;
    private boolean showSpeedChangeSamples;
    private boolean showMonitorSamples;

    public Settings() {
        super();
        yAxisUpperBound = 5d;
        yAxisLowerBound = 0d;
        yAxisTickUnit = 1d;
        timeBetweenSamples = 3600000;
        timeToZero = 3600000;
        boundaryTack = 10;
        boundaryTwist = 10;
        manualBoundary = 10;

        showColdSamples = true;
        showTeachingSamples = true;
        showSpeedChangeSamples = true;
        showMonitorSamples = true;
    }

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

    public long getTimeBetweenSamples() {
        return timeBetweenSamples;
    }

    public void setTimeBetweenSamples(long timeBetweenSamples) {
        this.timeBetweenSamples = timeBetweenSamples;
    }

    public long getTimeToZero() {
        return timeToZero;
    }

    public void setTimeToZero(long timeToZero) {
        this.timeToZero = timeToZero;
    }

    public boolean isShowSymbols() {
        return showSymbols;
    }

    public void setShowSymbols(boolean showSymbols) {
        this.showSymbols = showSymbols;
    }

    public boolean isShowManualAverage() {
        return showManualAverage;
    }

    public void setShowManualAverage(boolean showManualAverage) {
        this.showManualAverage = showManualAverage;
    }

    public double getManualAverage() {
        return manualAverage;
    }

    public void setManualAverage(double manualAverage) {
        this.manualAverage = manualAverage;
    }

    public double getManualBoundary() {
        return manualBoundary;
    }

    public void setManualBoundary(double manualBoundary) {
        this.manualBoundary = manualBoundary;
    }

    public boolean isShowAverage() {
        return showAverage;
    }

    public void setShowAverage(boolean showAverage) {
        this.showAverage = showAverage;
    }

    public double getBoundaryTack() {
        return boundaryTack;
    }

    public void setBoundaryTack(double boundaryTack) {
        this.boundaryTack = boundaryTack;
    }

    public double getBoundaryTwist() {
        return boundaryTwist;
    }

    public void setBoundaryTwist(double boundaryTwist) {
        this.boundaryTwist = boundaryTwist;
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
