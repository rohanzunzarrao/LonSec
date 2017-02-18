package com.lonsec.process;

import java.util.Date;
import java.util.List;

import com.lonsec.model.ReturnSeries;

/**
 * Read the returns details from the specified location.
 * <br></br>
 * This class has two methods to read the data namely <code>read()</code> and <code>lookup()</code>.
 * The read method cannot be called after a lookup method call. The read method would read the data
 * into a cache and the lookup method can return the data from the cache. You can directly call the
 * lookup method without an explicit read as this is done internally. Multiple calls to <code>read()</code>
 * will only return new data. Previously read data is not returned again.
 * 
 * @author Rohan Z
 *
 */
public interface ReturnsReader {

	/**
	 * Open a reader for the specified location.
	 * 
	 * @param location
	 */
	public void openReader(String location);
	
	/**
	 * Close the reader.
	 */
	public void closeReader();

	/**
	 * Read the returns data.
	 * @return
	 */
	public List<ReturnSeries> read();

	/**
	 * For a given code and date, lookup the returns data.
	 * 
	 * @param code
	 * @param returnDate
	 * @return
	 */
	public ReturnSeries lookup(String code, Date returnDate);
}
