package com.lonsec.process.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lonsec.model.ReturnSeries;
import com.lonsec.process.ReturnsReader;
import com.lonsec.util.Cache;
import com.lonsec.util.impl.CacheImpl;

/**
 * The class the can read Fund returns from a CSV file. Although this class can be written
 * as FULL mode (read entire file in one go) and BATCH mode (read specified number of records in one go),
 * the current implementation only supports FULL mode.
 * <br></br>
 * Note: Please see the <code>ReturnsReader</code> for details on usage of the 
 * <code>read()</code> and <code>lookup()</code> methods.
 * 
 * @author Rohan Z
 * @see ReturnsReader
 */
public class CSVReturnsReader implements ReturnsReader {
	
	private BufferedReader returnsFile = null;
	private String mode = null;
	private String regex = "\t";
	private String filePath = null;
	
	private String expectedPattern = "dd/MM/yyyy";
    private SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
    private Cache<String, ReturnSeries> cache = null;
	
	public CSVReturnsReader(String mode, String regex) {
		this.mode = mode;
		this.regex = regex;
	}
	
	public CSVReturnsReader(String mode, String regex, String dateFormat) {
		this.mode = mode;
		this.regex = regex;
		expectedPattern = dateFormat;
		formatter = new SimpleDateFormat(expectedPattern);
	}
	
	public void openReader(String filePath) {
		this.filePath = filePath;
		try {
			returnsFile = new BufferedReader(new FileReader(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public List<ReturnSeries> read() {
		List<ReturnSeries> returnSeries = new ArrayList<ReturnSeries>();
		if(returnsFile == null)
			return returnSeries;
		
		try {
			if("FULL".equalsIgnoreCase(mode)) {
				String data = returnsFile.readLine();
				while(data != null) {
					String[] tokens = data.split(regex);
					
					if(tokens.length != 3) {
						System.out.println("Error parsing " + data);
						data = returnsFile.readLine();
						continue;
					}
					
					try {
						ReturnSeries returns = new ReturnSeries();
						returns.setCode(tokens[0].trim());
						returns.setReturnDate(formatter.parse(tokens[1].trim()));
						returns.setReturnPercentage(new BigDecimal(tokens[2].trim()));
						returnSeries.add(returns);
					} catch (Exception e) {
						System.out.println("Error parsing " + data);
					}
					
					data = returnsFile.readLine();
				}
			}
			else {
				throw new RuntimeException("UnsupportedOperationMode");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		addAllToCache(returnSeries);
		return returnSeries;
	}

	public ReturnSeries lookup(String code, Date returnDate) {
		if("FULL".equalsIgnoreCase(mode)) {
			if(cache == null) {
				populateCache();
				
				// If cache is still null, we are dealing with a non-existing file.
				if(cache == null)
					return null;
			}
			
			return cache.get(code + "|" + formatter.format(returnDate));
		}
		else {
			String key = code + "|" + formatter.format(returnDate);
			ReturnSeries value = cache.get(key);
			
			if(value == null) {
				value = loadFromFile(key);
				if(value == null)
					return null;
				cache.put(key, value);
			}
			
			return value;
		}
	}

	private ReturnSeries loadFromFile(String key) {
		if(filePath == null)
			return null;
		
		ReturnSeries value = null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filePath));
			String data = reader.readLine();
			while(data != null) {
				String[] tokens = data.split(regex);
				
				if(tokens.length != 3) {
					System.out.println("Error parsing " + data);
				}
				
				String code = tokens[0];
				Date date = null;
				try {
					date = formatter.parse(tokens[1]);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				String thisKey = "";
				if(date != null)
					thisKey = code + "|" + formatter.format(date);
				
				if(thisKey.equals(key)) {
					value = new ReturnSeries();
					value.setCode(code);
					value.setReturnDate(date);
					value.setReturnPercentage(new BigDecimal(tokens[2]));
					return value;
				}
				
				data = reader.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if(reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		return value;
	}

	private void populateCache() {
		read();
	}
	
	private void addAllToCache(List<ReturnSeries> returns) {
		cache = new CacheImpl<String, ReturnSeries>(returns.size() + 10);
		for(ReturnSeries data : returns) {
			cache.put(data.getCode() + "|" + formatter.format(data.getReturnDate()), data);
		}
	}

	public void closeReader() {
		if(returnsFile != null) {
			try {
				returnsFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
