package de.tum.ais.callgraph;

import de.tum.ais.callgraph.format.CallgraphExporter;
import de.tum.ais.callgraph.graphml.GraphmlDocument;
import org.apache.commons.cli.*;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CallgraphLauncher {

    public static void main(String[] args) {

        Options options = new Options();

        Option input = new Option("i", "input", true, "input file path");
        input.setRequired(true);
        options.addOption(input);

        Option output = new Option("o", "output", true, "write to specific path");
        options.addOption(output);

        Option format = new Option("f", "format", true, "uses a specifc format, currently supported are:\n" +
                "                         - json (default)\n" +
                "                         - xml\n" +
                "                         - csv");
        options.addOption(format);

        Option print = new Option("p", "print", true, "print export format to console");
        options.addOption(print);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);
            System.exit(1);
            return;
        }

        String[] validFormats = {"json", "xml", "csv"};

        String exportFormat = cmd.getOptionValue("format");
        if (exportFormat != null) {
            boolean valid = false;
            for (int i = 0; i < validFormats.length; i++) {
                if (validFormats[i].equals(exportFormat)) {
                    valid = true;
                    break;
                }
            }
            if (!valid) {
                System.out.println("Expected a valid export format. Found: " + exportFormat + "\n");
                formatter.printHelp("utility-name", options);
                System.exit(-1);
            }
        } else {
            exportFormat = "json";
        }

        String inputFilePath = cmd.getOptionValue("input");
        GraphmlDocument document = null;
        try {
            document = GraphmlDocument.parse(inputFilePath);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("Check that the file exists.");
            System.exit(-1);
        } catch (XMLStreamException e) {
            System.out.println(e.getMessage());
            System.out.println("Check the file is a valid graphml file.");
            System.exit(-1);
        }

        PouCallGraph graph = document.createCallgraph();

        String outputFilePath = cmd.getOptionValue("output");

        if (outputFilePath != null) {
            if (Files.exists(Paths.get(outputFilePath))) {
                System.out.println("File " + outputFilePath + " already exists. Use a different path instead.");
                System.exit(-1);
            }
        } else {
            SimpleDateFormat df = new SimpleDateFormat("YYYY.MM.DD.HHmmss");
            Path currentRelativePath = Paths.get("");
            String parent = currentRelativePath.toAbsolutePath().toString();
            do {
                File f = new File(inputFilePath);
                String fileName = f.getName();

                String date = df.format(new Date());

                int index = fileName.lastIndexOf('.');
                if (index != -1) {
                    fileName = fileName.substring(0, index);
                }
                outputFilePath = parent + "/" + fileName + "." + date + "." + exportFormat;
            } while (Files.exists(Paths.get(outputFilePath)));
        }


        try {
            switch (exportFormat) {
                case "json":
                    CallgraphExporter.toJsonFile(graph, outputFilePath);
                    break;
                case "xml":
                    CallgraphExporter.toXmlFile(graph, outputFilePath);
                    break;
                case "csv":
//                    System.out.println("Csv is currently not supported");
                    CallgraphExporter.toCsvFile(graph, outputFilePath);
                    break;

            }
        } catch (IOException e) {
            System.out.println("Failed to write callgraph to " + outputFilePath);
            System.exit(-1);
        }

        System.out.println("Exported Callgraph to " + outputFilePath);

    }
}
