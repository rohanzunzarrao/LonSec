package com.lonsec.process;

import com.lonsec.model.Fund;

/**
 * Look up the Fund details from a specified source.
 * 
 * @author Rohan Z
 *
 * @see CSVFundsLookup
 */
public interface FundsLookup {

	/**
	 * Load the Fund details from the specified location.
	 *  
	 * @param String location of the resource
	 */
	public void loadFrom(String location);

	/**
	 * Lookup the specified Fund code and fetch the Fund details.
	 * 
	 * @param String The fund code
	 * @return
	 */
	public Fund lookup(String fundCode);

}
