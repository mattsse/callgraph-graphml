# AIS Callgraph GraphML Analyser

Parse representation of a callgraph in graphml and reengineer the callgraph of the plc project.


### Usage

#### Installation

```bash
./gradlew shadowJar

````

#### Extract Callgraph

This parses the `graphml-file`, extracts the callgraph and writes it to a new file

````bash
java -jar build/libs/ais-callgraph-1.0.jar -i <graphml-file>
````

*Flags*

```bash
 -i <path>            input file path
 -o <path>            write to specific file
 -f <format>          use a specifc format, currently supported are:
                         - json (default)
                         - xml
                         - csv
 -p                   print export format to console
 --help, -h           print help          
```
