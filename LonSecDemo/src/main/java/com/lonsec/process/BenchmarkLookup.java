package com.lonsec.process;

import com.lonsec.model.Benchmark;

/**
 * Look up the Benchmark details from a specified source.
 * 
 * @author Rohan Z
 *
 * @see CSVBenchmarkLookup
 */
public interface BenchmarkLookup {

	/**
	 * Load the Benchmark details from the specified location.
	 *  
	 * @param String location of the resource
	 */
	public void loadFrom(String location);
	
	/**
	 * Lookup the specified Benchmark code and fetch the Benchmark details.
	 * 
	 * @param String The benchmark code
	 * @return
	 */
	public Benchmark lookup(String code);

}
