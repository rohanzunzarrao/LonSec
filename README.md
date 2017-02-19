# LonSec
Lon Sec Demo

Pre-requisites
-----------------
1. The project is built using Maven. One would need to download and install Maven from https://maven.apache.org/install.html
2. The project is built on top on Java 7.

Compile
---------
1. One can compile the project using "mvn compile" (command to be run from the LonSecDemo directory).
2. The unit test cases can be run using "mvn test" (command to be run from the LonSecDemo directory).
3. To package the application to run it from a JAR please run "mvn package" (command to be run from the LonSecDemo directory).

Assumptions
--------------
1. The input file of funds return is read completely into memory. Although this file could possibly be read into memory in a preconfigured batch size, the batch mode is unsupported in this demo.
2. Similarly the output monthly performance data is completely stored and sorted in memory before writing it into a file. One possible approach here was splitting the output into multiple files (based on date), sorting individual files and then merging them together. However this approach is not taken in this demo.
3. There are no unit tests for model classes that have simple getters-setters. There are no unit tests for the RequestApp as its testing would be done as part of the integration/functionality testing.

Note: For any out-of-memory problems, for this demo one would need to set the appropriate parameters as described in https://docs.oracle.com/cd/E19900-01/819-4742/abeik/index.html

Configuration
----------------
For details of configuration. Please see the ConfigurationReadme.txt

Running the Code
---------------------
The code can be executed in two ways

1. Using the Launch GUI - Launch the GUI using the command below. Select the appropriate files and click "Compute". The GUI ensures that the files are always present. (Note: command to be run from the LonSecDemo directory)

    java -cp target\LonSecDemo-0.0.1-SNAPSHOT.jar com.lonsec.gui.Launch

2. Command Line mode - Run from the command line using the command below. (Note: command to be run from the LonSecDemo directory)

    java -cp target\LonSecDemo-0.0.1-SNAPSHOT.jar com.lonsec.process.ReturnsApp newFiles\fund.csv newFiles\benchmark.csv newFiles\FundReturnSeries.csv newFiles\BenchReturnSeries.csv

The output file is created in the same directory as the fund return series file. Any existing file of the same name is overwritten.

NOTE: For now there is no Logger used in this application. Any messages are logged on the console.
