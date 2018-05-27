package de.tum.ais.callgraph;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.tum.ais.callgraph.graphml.Geometry;

public class PouElement {

    protected String id;

    protected String name;

    protected String symbolName;

    protected PouBlockType blockType;

    protected String metric;

    @JsonProperty("size")
    protected double metricValue;

    protected Geometry geometry;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PouBlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(PouBlockType blockType) {
        this.blockType = blockType;
    }

    public String getSymbolName() {
        return symbolName;
    }

    public void setSymbolName(String symbolName) {
        this.symbolName = symbolName;
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

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
}
