package com.lonsec.model;

/**
 * A Fund instrument. 
 */
public class Fund extends Instrument {

	/**
	 * The benchmark for this fund
	 */
	private String benchmarkCode;

	/**
	 * Get the Benchmark code
	 * @return The benchmark code
	 */
	public String getBenchmarkCode() {
		return benchmarkCode;
	}

	/**
	 * Set the Benchmark code
	 * @param benchmarkCode
	 */
	public void setBenchmarkCode(String benchmarkCode) {
		this.benchmarkCode = benchmarkCode;
	}
	
}
