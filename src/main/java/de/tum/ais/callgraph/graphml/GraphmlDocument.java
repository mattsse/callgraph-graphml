package de.tum.ais.callgraph.graphml;

import de.tum.ais.callgraph.PouCall;
import de.tum.ais.callgraph.PouCallGraph;
import de.tum.ais.callgraph.PouElement;
import de.tum.ais.callgraph.PouInfo;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class GraphmlDocument {

    private static Logger LOGGER = Logger.getLogger(GraphmlDocument.class.getName());


    static final String VERTEX = "node";
    static final String EDGE = "edge";
    static final String PORT = "port";
    static final String GEOMETRY = "Geometry";
    static final String TEXT = "Text";


    @SuppressWarnings({"unchecked", "null"})
    public static GraphmlDocument parse(String path) throws FileNotFoundException, XMLStreamException {
        GraphmlDocument doc = new GraphmlDocument();

        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream in = new FileInputStream(path);
        XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

        Vertex currentVertex = null;
        Port currentPort = null;
        Edge currentEdge = null;
        Geometry currentGeo = null;
        String currentLabelText = null;

        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();

            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                // If we encountered a node element, we create a new vertex
                if (startElement.getName()
                                .getLocalPart()
                                .equals(VERTEX)) {
                    currentVertex = new Vertex();
                    Attribute att = startElement.getAttributeByName(QName.valueOf("id"));
                    if (att != null) {
                        currentVertex.setId(att.getValue());
                    }
                    continue;
                }

                if (startElement.getName()
                                .getLocalPart()
                                .equals(TEXT)) {
                    event = eventReader.nextEvent();
                    if (event.isCharacters()) {
                        currentLabelText = event.asCharacters()
                                                .getData();
                    }
                    continue;
                }

                if (startElement.getName()
                                .getLocalPart()
                                .equals(EDGE)) {
                    currentEdge = new Edge();
                    Iterator<Attribute> attributes = startElement
                            .getAttributes();
                    while (attributes.hasNext()) {
                        Attribute att = attributes.next();
                        switch (att.getName()
                                   .toString()) {
                            case "source":
                                currentEdge.setSource(att.getValue());
                                break;
                            case "target":
                                currentEdge.setTarget(att.getValue());
                                break;
                            case "id":
                                currentEdge.setId(att.getValue());
                                break;
                            case "sourceport":
                                currentEdge.setSourceport(att.getValue());
                                break;
                            case "targetport":
                                currentEdge.setTargetport(att.getValue());
                                break;
                        }
                    }
                    continue;
                }

                if (startElement.getName()
                                .getLocalPart()
                                .equals(GEOMETRY)) {
                    currentGeo = new Geometry();

                    Iterator<Attribute> attributes = startElement
                            .getAttributes();
                    while (attributes.hasNext()) {
                        Attribute att = attributes.next();
                        switch (att.getName()
                                   .toString()) {
                            case "x":
                                currentGeo.setX(Double.parseDouble(att.getValue()));
                                break;
                            case "y":
                                currentGeo.setY(Double.parseDouble(att.getValue()));
                                break;
                            case "width":
                                currentGeo.setWidth(Double.parseDouble(att.getValue()));
                                break;
                            case "height":
                                currentGeo.setHeight(Double.parseDouble(att.getValue()));
                                break;
                        }
                    }

                    continue;
                }

                if (startElement.getName()
                                .getLocalPart()
                                .equals(PORT)) {
                    currentPort = new Port();
                    Attribute att = startElement.getAttributeByName(QName.valueOf("name"));
                    if (att != null) {
                        currentPort.setName(att.getValue());
                    }
                    continue;
                }

            }

            // reached end
            if (event.isEndElement()) {
                EndElement endElement = event.asEndElement();
                if (endElement.getName()
                              .getLocalPart()
                              .equals(VERTEX)) {
                    doc.getVertices()
                       .add(currentVertex);
                    if (doc.getRoot() == null) {
                        doc.setRoot(currentVertex);
                    }
                    continue;
                }

                if (endElement.getName()
                              .getLocalPart()
                              .equals(PORT)) {
                    currentVertex.getPorts()
                                 .add(currentPort);
                    continue;
                }

                if (endElement.getName()
                              .getLocalPart()
                              .equals(EDGE)) {
                    doc.getEdges()
                       .add(currentEdge);
                    continue;
                }
                if (endElement.getName()
                              .getLocalPart()
                              .equals(GEOMETRY)) {
                    if (currentVertex != null) {
                        currentVertex.setGeometry(currentGeo);
                    }
                }
                if (endElement.getName()
                              .getLocalPart()
                              .equals(TEXT)) {
                    if (currentVertex != null && !currentLabelText.isEmpty()) {
                        currentVertex.getTexts()
                                     .add(currentLabelText);
                    }
                }
            }


        }


        return doc;
    }


    protected Vertex root;

    protected List<Vertex> vertices;

    protected List<Edge> edges;

    public List<Vertex> getVertices() {
        if (vertices == null) {
            vertices = new ArrayList<>();
        }
        return vertices;
    }

    public List<Edge> getEdges() {
        if (edges == null) {
            edges = new ArrayList<>();
        }
        return edges;
    }

    public Vertex getRoot() {
        return root;
    }

    public void setRoot(Vertex root) {
        this.root = root;
    }


    public PouCallGraph createCallgraph() {
        PouCallGraph graph = new PouCallGraph();


        for (Vertex vertex : getVertices()) {
            PouElement element = new PouElement();
            graph.getElements()
                 .add(element);
            element.setId(vertex.getId());
            element.setGeometry(vertex.getGeometry());
            PouInfo info = vertex.getPouInfo();
            if (info == null) {
                LOGGER.info("Could not extract pou information for node " + element.getId());
            } else {
                element.setName(info.getName());
                element.setSymbolName(info.getSymbolName());
                element.setMetric(info.getMetric());
                element.setMetricValue(info.getMetricValue());
                element.setBlockType(info.getBlockType());
            }
        }


        for (Edge edge : getEdges()) {
            PouElement caller = graph.getElementById(edge.getSource())
                                     .get();
            PouElement callee = graph.getElementById(edge.getTarget())
                                     .get();
            PouCall call = new PouCall(caller, callee);
            graph.getCalls()
                 .add(call);
        }

        return graph;
    }

}
