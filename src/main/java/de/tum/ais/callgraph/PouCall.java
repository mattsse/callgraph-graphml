package de.tum.ais.callgraph;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.tum.ais.callgraph.graphml.Point;

public class PouCall {

    @JsonIgnore
    protected PouElement callee;

    @JsonIgnore
    protected PouElement caller;

    public PouCall(PouElement caller, PouElement callee) {
        this.caller = caller;
        this.callee = callee;
    }

    public PouElement getCallee() {
        return callee;
    }

    public void setCallee(PouElement callee) {
        this.callee = callee;
    }

    public PouElement getCaller() {
        return caller;
    }

    public void setCaller(PouElement caller) {
        this.caller = caller;
    }


    @JsonIgnore
    public boolean isComplete() {
        return getCallee() != null && getCaller() != null;
    }


    /**
     * Assuming the x,y location of the graphml geometry is located at the
     * upper left corner.
     *
     * @return the geometrical distance between the two pous
     */
    @JsonProperty()
    public double distance() {
        Point p1 = getCallee().getGeometry()
                              .center();
        Point p2 = getCaller().getGeometry()
                              .center();

        return Math.sqrt(
                (p1.getX() - p2.getX()) * (p1.getX() - p2.getX()) +
                        (p1.getY() - p2.getY()) * (p1.getY() - p2.getY())
        );
    }


    @JsonProperty("caller")
    public String getCallerId() {
        return getCaller().getId();
    }

    @JsonProperty("callee")
    public String getCalleeId() {
        return getCallee().getId();
    }
}
