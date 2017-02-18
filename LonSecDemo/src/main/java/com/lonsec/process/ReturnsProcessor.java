package com.lonsec.process;

/**
 * Process the given information to generate the fund performance output.
 * 
 * @author Rohan Z
 *
 */
public interface ReturnsProcessor {

	void process(String fundsFile, String benchmarkFile,
			String fundsReturnsFile, String benchmarkReturnsFile);
}
