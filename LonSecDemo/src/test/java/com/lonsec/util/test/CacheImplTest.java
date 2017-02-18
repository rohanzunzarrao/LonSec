package com.lonsec.util.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.lonsec.util.impl.CacheImpl;

public class CacheImplTest {

	@Test
	public void createCache() {
		CacheImpl<String, String> cache = new CacheImpl<String, String>(1);
		assertNotNull("Cache object was null", cache);
	}
	
	@Test
	public void createZeroSizeCache() {
		CacheImpl<String, String> cache = new CacheImpl<String, String>(0);
		assertNotNull("Cache object was null", cache);
	}
	
	@Test
	public void createNegativeSizeCache() {
		CacheImpl<String, String> cache = new CacheImpl<String, String>(-1);
		assertNotNull("Cache object was null", cache);
	}
	
	@Test
	public void addEntry() {
		CacheImpl<String, String> cache = new CacheImpl<String, String>(1);
		cache.put("A", "a");
		String returned = cache.get("A");
		assertEquals("Expected 'a' but returned '" + returned + "'", "a", returned);
	}
	
	@Test
	public void getWithoutAddingEntry() {
		CacheImpl<String, String> cache = new CacheImpl<String, String>(1);
		String returned = cache.get("A");
		assertNull("Expected Null but returned '" + returned + "'", returned);
	}
	
	@Test
	public void checkEviction() {
		CacheImpl<String, String> cache = new CacheImpl<String, String>(2);
		cache.put("A", "a");
		cache.put("B", "b");
		cache.put("C", "c");
		
		String returned = cache.get("C");
		assertEquals("Expected 'c' but returned '" + returned + "'", "c", returned);
		
		returned = cache.get("B");
		assertEquals("Expected 'b' but returned '" + returned + "'", "b", returned);
		
		returned = cache.get("A");
		assertNull("Expected Null but returned '" + returned + "'", returned);
	}
}
