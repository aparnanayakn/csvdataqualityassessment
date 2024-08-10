## Datasets

This folder contains the dataset used for analysis.

## Preprocessing

This folder contains a Python file (python notebook) responsible for converting CSV files into graph format. _RDFLib_ python package is used to populate the graph. 

**csv2RDFwithDQ.ipynb**
_Purpose:_ Populate graph with given CSV data. This also adds data quality information such as population completeness and additional information such as mean, standard deviation, upper quartile and lower quartile values for the columns in case the column meets the requirements.
_Input:_ CSV file.
_Output:_ Populated graph in TTL format. (This can be changed to any other format supported by the RDFLib package) 

**csvtordf.owl**
This is an ontology for converting CSV into the triple format. 

This ontology is available using [purl](purl.archive.org/csvtordf).

## DQassessement/src

This folder contains a Java file responsible for identifying the constraints defined in the form of rules. 

File _Main.java_ contains the java program that takes rdf data as input and gives output in triple format (head terms/conclusions) on the console if any of the triples from the input match with the list of body terms(premises).

File _rules.n3_ contains rules in Notation-3 (n3) format, which are validated against input data. 
 
