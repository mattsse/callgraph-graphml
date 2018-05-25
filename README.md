# AIS Callgraph GraphML Analyser

Parse representation of a callgraph in graphml and reengineer the callgraph of the plc project.


### Usage

#### Installation

```bash
mvn clean install

````

#### Extract Callgraph

This parses the `graphml-file`, extracts the callgraph and writes it to a new file

````bash
java -jar target/ais-callgraph-1.0.jar <graphml-file>
````

*Additional flags*
```bash
 -o <path>            output file
 -f <format>          uses a specifc format, currently supported are           
```
