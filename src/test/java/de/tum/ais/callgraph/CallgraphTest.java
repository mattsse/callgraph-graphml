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
    @Test
    public void parseIncompletePouInfoTest() {
        Vertex vertex = new Vertex();

        vertex.getTexts()
              .add("FC410, block type: FC, source code not found ");

        PouInfo info = vertex.getPouInfo();
        Assert.assertNotNull(info);

        Assert.assertEquals("FC410", info.getName());
        Assert.assertEquals("CYC_INT2", info.getSymbolName());
        Assert.assertEquals(PouBlockType.FC, info.getBlockType());
        Assert.assertEquals("N/A", info.getMetric());
        Assert.assertEquals(-1.0, info.getMetricValue(),0);

    }
}
