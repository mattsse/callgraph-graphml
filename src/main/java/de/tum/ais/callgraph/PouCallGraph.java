package de.tum.ais.callgraph;

import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@JsonRootName(value = "callgraph")
public class PouCallGraph {

    protected List<PouElement> elements;

    protected List<PouCall> calls;

    public List<PouCall> getCalls() {
        if (calls == null) {
            calls = new ArrayList<>();
        }
        return calls;
    }

    public List<PouElement> getElements() {
        if (elements == null) {
            elements = new ArrayList<>();
        }
        return elements;
    }


    public Optional<PouElement> getElementById(String id) {
        return getElements().stream()
                            .filter(p -> p.getId()
                                          .equals(id))
                            .findFirst();
    }
}
