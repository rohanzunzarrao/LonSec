package com.lonsec.process;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class is used to look up the excess code performance string against a given
 * excess %.
 *  
 * @author Rohan Z
 *
 */
public class ExcessLookup {

	private TreeMap<BigDecimal, String> lookupData = new TreeMap<BigDecimal, String>();
	private String MAX = "";
	
	/**
	 * Create the excess lookup using the specified mapping and default maximum % string
	 * 
	 * @param config
	 * @param max
	 */
	public ExcessLookup(Map<BigDecimal, String> config, String max) {
		if(config != null)
			lookupData.putAll(config);
		
		if(max != null)
			MAX = max;
	}
	
	/**
	 * Get the excess code for the specified excess %
	 * @param excess
	 * @return
	 */
	public String getExcessCode(BigDecimal excess) {
		if(excess == null)
			return "";

		BigDecimal previous = null;
		for(Map.Entry<BigDecimal, String> entry: lookupData.entrySet()) {
			BigDecimal current = entry.getKey();
			if(previous == null) {
				if(excess.compareTo(current) < 0)
					return entry.getValue();
			}
			else {
				if(excess.compareTo(previous) >=0 && excess.compareTo(current) < 0)
					return entry.getValue();
			}
			
			previous = current;
		}
		
		return MAX;
	}
}
