# AIS Callgraph GraphML Analyser

Parse the representation of a callgraph in graphml and reengineer the callgraph of the underlying plc project.

## Usage

### Installation

Compile the source files to an executeable jar file.

```bash
# this downloads the gradle wrapper, so you don't need any gradle installation beforehand
# requires a valid java 8 installation though
./gradlew shadowJar

````

#### Extract Callgraph

This parses the `graphml-file`, extracts the callgraph and writes it to a new file

````bash
java -jar build/libs/ais-callgraph-1.0.jar -i <graphml-file>
````

##### Commandline Flags

```bash
 -i <path>            input file path
 -o <path>            write to specific file
 -f <format>          use a specifc format, currently supported are:
                         - json (default)
                         - xml
                         - csv
 -p                   print callgraph format to console
 --help, -h           print help
```

##### Example

Extract the callgraph from `demo/demo.graphml` as json to `callgraph.json`.

```bash
java -jar build/libs/ais-callgraph-1.0.jar -i demo/demo.graphml -o callgraph.json -f json
```

#### Authors

* **Matthias Seitz** Institute of Automation and Information Systems - TU Munich<br>Contact: [*matthias.seitz@tum.de*](mailto:matthias.seitz@tum.de)