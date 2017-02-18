package com.lonsec.process;

import com.lonsec.model.FundPerformance;

/**
 * Class used to write the FundPerformance data to a specified location.
 * 
 * @author Rohan Z
 *
 * @see FundPerformance
 */
public interface OutputWriter {

	/**
	 * Specify the location at which the write the output.
	 * 
	 * @param location
	 */
	public void writeTo(String location);

	/**
	 * Write the given performance data to the specified output.
	 * 
	 * @param performance
	 */
	public void writeRecord(FundPerformance performance);

	/**
	 * Flush the data in the buffer to the configured output location
	 */
	public void flush();

	/**
	 * Close the Writer.
	 */
	public void closeWriter();

}
