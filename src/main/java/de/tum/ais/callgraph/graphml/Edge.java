package de.tum.ais.callgraph.graphml;

public class Edge {

    protected String id;
    protected String source;
    protected String target;
    protected String sourceport;
    protected String targetport;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getSourceport() {
        return sourceport;
    }

    public void setSourceport(String sourceport) {
        this.sourceport = sourceport;
    }

    public String getTargetport() {
        return targetport;
    }

    public void setTargetport(String targetport) {
        this.targetport = targetport;
    }
}
