The configurations are possible through the spring's system-config.xml.

1. fundCache - The size of this cache can be changed. Funds read from the source are cached in this, hence an appropriate cache size would reduce the number of file reads. Default set to 1000.

2. CSVFundsReturnsReader - The reader used to read the Fund return details. It has two possible configurations
a. mode - FULL/BATCH. As of now only FULL mode is supported. Setting any other mode results in a runtime exception "Unsupported Mode".
b. regex - The file delimiter. By default set to ','. This can be changed to '|', '\t' (for tab) or any other character.

3. CSVBenchmarkReturnsReader - The reader used to read the Benchmark return details. It has following configurations
a. mode - FULL/BATCH. As this is used only for lookup, we can use BATCH mode. In this case only the requested value is read from the file and cached.
b. regex - The file delimiter. By default set to ','. This can be changed to '|', '\t' (for tab) or any other character.
c. dateFormat - The format of the date in the file. The format string follows Java SimpleDateFormat specs.
d. cacheSize - This is optional configuration, needed only in BATCH mode. Defaults to 1000.

3. CSVFundsLookup - The Bean that looks up Funds master data. It has only one configuration
a. regex - The file delimiter. By default set to ','. This can be changed to '|', '\t' (for tab) or any other character.

4. ExcessLookup - The configuration of the code used for excess return.
a. config - A map of return % against a code.
b. max - The code to be used when return % greater than the largest value specified in the config map.

5. CSVOutputWriter - The output creator.
a. fileName - The name of the output file.
b. delimiter - The file delimiter. By default set to ','. This can be changed to '|', '\t' (for tab) or any other character.
