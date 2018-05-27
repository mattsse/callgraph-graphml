package de.tum.ais.callgraph;

import de.tum.ais.callgraph.graphml.Vertex;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CallgraphTest {


    @Test
    public void dummyTest() {
        Assert.assertTrue(true);
    }


    @Test
    public void loadGraphmlTest() throws IOException, XMLStreamException {
        String path = "demo/demo.graphml";
        Assert.assertTrue(Files.exists(Paths.get(path)));

        CallgraphAnalyser analyser = new CallgraphAnalyser(path);

        analyser.open();

    }


    @Test
    public void parsePouInfoTest() {
        Vertex vertex = new Vertex();

        vertex.getTexts()
              .add("OB32, symbol name: CYC_INT2, block type: OB, metric (LOC) = 21");

        PouInfo info = vertex.getPouInfo();
        Assert.assertNotNull(info);

        Assert.assertEquals("OB32", info.getName());
        Assert.assertEquals("CYC_INT2", info.getSymbolName());
        Assert.assertEquals(PouBlockType.OB, info.getBlockType());
        Assert.assertEquals("LOC", info.getMetric());
        Assert.assertEquals(21.0, info.getMetricValue(),0);

    }
}
