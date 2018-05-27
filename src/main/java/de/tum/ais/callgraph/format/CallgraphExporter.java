package de.tum.ais.callgraph.format;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.tum.ais.callgraph.PouCallGraph;
import de.tum.ais.callgraph.PouElement;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CallgraphExporter {

    public static String asXml(PouCallGraph graph) {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            return xmlMapper.writerWithDefaultPrettyPrinter()
                            .writeValueAsString(graph);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void toXmlFile(PouCallGraph graph, String file) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.writerWithDefaultPrettyPrinter()
                 .writeValue(new File(file), graph);
    }


    public static String asJson(PouCallGraph graph) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writerWithDefaultPrettyPrinter()
                         .writeValueAsString(graph);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void toJsonFile(PouCallGraph graph, String file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter()
              .writeValue(new File(file), graph);
    }


    public static void toCsvFile(PouCallGraph graph, String file) throws IOException {
        FileWriter writer = new FileWriter(file);

        CSVUtils.writeLine(writer, Arrays.asList("POU", "id", "symbolName", "blocktype",
                "metric", "size", "x", "y", "width", "height", "calls", "receives"));

        for (PouElement element : graph.getElements()) {
            String calls = graph.getAllCallees(element)
                                .stream()
                                .map(m -> m.getCallee()
                                           .getId() + " : " + m.distance())
                                .collect(Collectors.joining(";"));
            String recieves = graph.getAllCallers(element)
                                   .stream()
                                   .map(m -> m.getCaller()
                                              .getId() + " : " + m.distance())
                                   .collect(Collectors.joining(";"));


            List<String> vals = new ArrayList<>();
            vals.add(element.getName());
            vals.add(element.getId());
            vals.add(element.getSymbolName());
            vals.add(element.getBlockType()
                            .getIdentifier());
            vals.add(element.getMetric());
            vals.add(String.valueOf(element.getMetricValue()));
            vals.add(String.valueOf(element.getGeometry()
                                           .getX()));
            vals.add(String.valueOf(element.getGeometry()
                                           .getY()));
            vals.add(String.valueOf(element.getGeometry()
                                           .getWidth()));
            vals.add(String.valueOf(element.getGeometry()
                                           .getHeight()));
            vals.add(calls);
            vals.add(recieves);

            CSVUtils.writeLine(writer, vals);
        }
        writer.flush();
        writer.close();
    }


    public static void toMatrixFile(PouCallGraph graph, String file) throws IOException {

    }

}
