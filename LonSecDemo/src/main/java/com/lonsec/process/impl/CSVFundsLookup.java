package com.lonsec.process.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.lonsec.model.Fund;
import com.lonsec.process.FundsLookup;
import com.lonsec.util.Cache;

/**
 * The funds lookup class that can read the Funds data from a CSV file.
 * 
 * @author Rohan Z
 *
 * @see FundsLookup
 */
public class CSVFundsLookup implements FundsLookup {
	
	/**
	 * Internal LRU Cache
	 */
	private Cache<String, Fund> lookupCache = null;
	
	/**
	 * Data delimiter
	 */
	private String regex = ",";
	
	/**
	 * File location
	 */
	private String filePath = null;

	/**
	 * Create the lookup using the given cache and file delimiter.
	 * 
	 * @param cache
	 * @param regex
	 */
	@SuppressWarnings("unchecked")
	public CSVFundsLookup(Cache<?, ?> cache, String regex) {
		lookupCache = (Cache<String, Fund>) cache;
		this.regex = regex;
	}
	
	public void loadFrom(String fundsFile) {
		filePath = fundsFile;
	}

	/**
	 * Lookup the specified fund code.
	 */
	public Fund lookup(String fundCode) {
		// Check in cache.
		Fund fund = lookupCache.get(fundCode);
		
		// If not in cache, search in file and add to cache.
		if(fund == null) {
			fund = loadFromFile(fundCode);
			// If not found in file, return null
			if(fund == null)
				return null;
			
			lookupCache.put(fundCode, fund);
		}
		
		return fund;
	}

	/**
	 * Lookup the specified fund from the file. If the file path is not specified or if there is a problem reading data from the file,
	 * log an error and return null. If there is any error in parsing a particular line, skip it and continue.
	 *  
	 * @param code
	 * @return
	 */
	private Fund loadFromFile(String code) {
		if(filePath == null)
			return null;
		
		Fund fund = null;
		BufferedReader fundsFile = null;
		try {
			fundsFile = new BufferedReader(new FileReader(filePath));
			String data = fundsFile.readLine();
			while(data != null) {
				String[] tokens = data.split(regex);
				
				if(tokens.length != 3) {
					System.out.println("Error parsing " + data);
					data = fundsFile.readLine();
					continue;
				}
				
				String fundCode = tokens[0].trim();
				String fundName = tokens[1].trim();
				String benchmarkCode = tokens[2].trim();
				
				if(code.equals(fundCode)) {
					fund = new Fund();
					fund.setCode(fundCode);
					fund.setName(fundName);
					fund.setBenchmarkCode(benchmarkCode);
					return fund;
				}
				
				data = fundsFile.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if(fundsFile != null)
				try {
					fundsFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		return fund;
	}
}
