package de.tum.ais.callgraph;

public enum PouBlockType {
    OB("Organisation Block"),
    FB("Function Block"),
    FC("Function"),
    UNRESOLVED("Unresolved");

    private String qualifedName;

    PouBlockType(String qualifedName) {
        this.qualifedName = qualifedName;
    }

    public String getQualifedName() {
        return qualifedName;
    }

    public String getIdentifier() {
        return name();
    }


    public static PouBlockType fromIdentifier(String identifier) {
        try {
            return PouBlockType.valueOf(identifier);
        } catch (IllegalArgumentException e) {
            return UNRESOLVED;
        }
    }
}
