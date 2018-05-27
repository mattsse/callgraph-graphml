package de.tum.ais.callgraph;

import de.tum.ais.callgraph.graphml.GraphmlDocument;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class CallgraphAnalyser {

    private static Logger LOGGER = Logger.getLogger(CallgraphAnalyser.class.getName());

    protected String path;

    private boolean opened;

    protected GraphmlDocument document;

    public CallgraphAnalyser(String path) {
        this.path = path;
    }


    public GraphmlDocument getDocument() {
        return document;
    }

    public void setDocument(GraphmlDocument document) {
        this.document = document;
    }

    /**
     *
     * @return whether the targeted xmnl could be parsed correctly
     * @throws IOException
     * @throws XMLStreamException
     */
    public boolean open() throws IOException, XMLStreamException {
        if (isOpened()) {
            return false;
        }

        if (!Files.exists(Paths.get(path))) {
            LOGGER.info(String.format("File {} does not exist.", path));
            return false;
        }

        document = GraphmlDocument.parse(path);

        return true;
    }

    public boolean isOpened() {
        return opened;
    }


}
