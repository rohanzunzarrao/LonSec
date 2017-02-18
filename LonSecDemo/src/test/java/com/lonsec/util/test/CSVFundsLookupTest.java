package com.lonsec.util.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

import org.junit.Test;

import com.lonsec.model.Fund;
import com.lonsec.process.impl.CSVFundsLookup;
import com.lonsec.util.Cache;
import com.lonsec.util.impl.CacheImpl;

public class CSVFundsLookupTest {

	private String regex = ",";
	private Cache<String, Fund> cache = new CacheImpl<String, Fund>(10);
	
	@Test
	public void testFileNotFound() {
		CSVFundsLookup lookup = new CSVFundsLookup(cache, regex);
		lookup.loadFrom("./testFiles/invalidFile.csv");
		
		assertNull("Non Null Fund returned", lookup.lookup("abc"));
	}
	
	@Test
	public void testValidFile() {
		CSVFundsLookup lookup = new CSVFundsLookup(cache, regex);
		lookup.loadFrom("./testFiles/FundFile.csv");
		
		Fund fund = lookup.lookup("MF-1-4220");
		assertNotNull("Non Null Fund returned", fund);
		assertEquals("Incorrect Fund Name", "Goldman Sachs Emerging Leaders Fund", fund.getName());
		assertEquals("Incorrect Benchmark", "BM-672", fund.getBenchmarkCode());
	}
	
	@Test
	public void testCacheSize() {
		CSVFundsLookup lookup = new CSVFundsLookup(cache, regex);
		lookup.loadFrom("./testFiles/FundFile.csv");
		
		lookup.lookup("MF-1-4220");
		lookup.lookup("EF-2-21254");
		
		try {
			Class<?> cacheClass = Class.forName(CacheImpl.class.getName());
			Field LRUCache = cacheClass.getDeclaredField("cache");
			LRUCache.setAccessible(true);
			Object innerObject = LRUCache.get(cache);
			@SuppressWarnings("rawtypes")
			int size = ((LinkedHashMap)innerObject).size();
			
			assertEquals("Unexpected cache size", 2, size);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testParsingFile() {
		CSVFundsLookup lookup = new CSVFundsLookup(cache, regex);
		lookup.loadFrom("./testFiles/FundFileParseError.csv");
		
		Fund fund = lookup.lookup("MF-1-4220");
		assertNotNull("Non Null Fund returned", fund);
		assertEquals("Incorrect Fund Name", "Goldman Sachs Emerging Leaders Fund", fund.getName());
		assertEquals("Incorrect Benchmark", "BM-672", fund.getBenchmarkCode());
		
		assertNull("Non Null Fund returned", lookup.lookup("MF-1-10685"));
		assertNull("Non Null Fund returned", lookup.lookup("MF-1-10685Vanguard Wholesale Australian Fixed Interest Index"));
		
		fund = lookup.lookup("EF-2-21255");
		assertNotNull("Non Null Fund returned", fund);
		assertEquals("Incorrect Fund Name", "Vanguard All-World Ex-US Shares Index ETF", fund.getName());
		assertEquals("Incorrect Benchmark", "BM-672", fund.getBenchmarkCode());

	}
}
