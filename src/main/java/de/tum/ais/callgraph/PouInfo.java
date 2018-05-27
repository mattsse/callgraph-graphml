package de.tum.ais.callgraph;

public class PouInfo {

    protected String name;
    protected String symbolName;
    protected PouBlockType blockType;
    protected String metric;
    protected double metricValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbolName() {
        return symbolName;
    }

    public void setSymbolName(String symbolName) {
        this.symbolName = symbolName;
    }

    public PouBlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(PouBlockType pouType) {
        this.blockType = pouType;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public double getMetricValue() {
        return metricValue;
    }

    public void setMetricValue(double metricValue) {
        this.metricValue = metricValue;
    }
}
