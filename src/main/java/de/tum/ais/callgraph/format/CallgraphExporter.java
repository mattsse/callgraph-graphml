package de.tum.ais.callgraph.format;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.tum.ais.callgraph.PouCallGraph;

import java.io.File;
import java.io.IOException;

public class CallgraphExporter {

    public static String asXml(PouCallGraph graph) {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            return xmlMapper.writeValueAsString(graph);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void toXmlFile(PouCallGraph graph, String file) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.writeValue(new File(file), graph);
    }


    public static String asJson(PouCallGraph graph) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(graph);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void toJsonFile(PouCallGraph graph, String file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(file), graph);
    }


    public static void toCsvFile(PouCallGraph graph, String file) throws IOException {

    }


    public static void toMatrixFile(PouCallGraph graph, String file) throws IOException {

    }

}
