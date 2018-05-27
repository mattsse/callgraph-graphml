package de.tum.ais.callgraph.graphml;

import de.tum.ais.callgraph.PouBlockType;
import de.tum.ais.callgraph.PouInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Vertex {

    public static final String FULL_INFO_REGEX = "(.+),\\s*(symbol name:\\s*(.+),)?\\s*block type:\\s+(\\w+),\\s+metric\\s+\\(?(\\w+)\\)?\\s+=\\s+(\\d+)";
    public static final String MISSING_SOURCE_REGEX = "(.+),\\s*(symbol name:\\s*(.+),)?\\s*block type:\\s+(\\w+),\\s+source code not found\\s*";

    public static final Pattern FULL_INFO_PATTERN = Pattern.compile(FULL_INFO_REGEX, Pattern.MULTILINE);
    public static final Pattern MISSING_SOURCE_PATTERN = Pattern.compile(MISSING_SOURCE_REGEX, Pattern.MULTILINE);


    protected String id;

    protected Geometry geometry;

    protected List<Port> ports;

    protected List<String> texts;

    public List<Port> getPorts() {
        if (ports == null) {
            ports = new ArrayList<>();
        }
        return ports;
    }

    public List<String> getTexts() {
        if (texts == null) {
            texts = new ArrayList<>();
        }
        return texts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public boolean hasGeometry() {
        return getGeometry() != null;
    }


    /**
     * @return The Info about the POU element this vertex represents
     * or null if the description is incomplete
     */
    public PouInfo getPouInfo() {
        List<String> txt = getTexts();

        BiConsumer<PouInfo, Matcher> setCommon = (i, m) -> {
            i.setName(m.group(1));
            i.setSymbolName(m.group(3));
            i.setBlockType(PouBlockType.fromIdentifier(m.group(4)));
        };

        for (int i = txt.size() - 1; i >= 0; i--) {
            Matcher matcher = Vertex.FULL_INFO_PATTERN.matcher(txt.get(i));
            while (matcher.find()) {
                PouInfo info = new PouInfo();
                setCommon.accept(info, matcher);
                info.setMetric(matcher.group(5));
                info.setMetricValue(Double.parseDouble(matcher.group(6)));
                return info;
            }
            matcher = Vertex.MISSING_SOURCE_PATTERN.matcher(txt.get(i));
            while (matcher.find()) {
                PouInfo info = new PouInfo();
                setCommon.accept(info, matcher);
                info.setMetric("N/A");
                info.setMetricValue(-1);
                return info;
            }
        }
        return null;
    }


}
