package com.lonsec.util.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.lonsec.process.ExcessLookup;

public class ExcessLookupTest {
	
	@Test
	public void testEmptyMapAndMax() {
		ExcessLookup lookup = new ExcessLookup(null, null);
		assertEquals("Unexpected lookup code", "", lookup.getExcessCode(new BigDecimal(1)));
	}
	
	@Test
	public void testEmptyMap() {
		ExcessLookup lookup = new ExcessLookup(null, "ALWAYS");
		assertEquals("Unexpected lookup code", "ALWAYS", lookup.getExcessCode(new BigDecimal(1)));
	}
	
	@Test
	public void testEmptyAndNullLookup() {
		ExcessLookup lookup = new ExcessLookup(null, null);
		assertEquals("Unexpected lookup code", "", lookup.getExcessCode(null));
	}
	
	@Test
	public void testValidMapAndNullLookup() {
		Map<BigDecimal, String> config = new HashMap<BigDecimal, String>();
		config.put(new BigDecimal("-1"), "underPerformed");
		config.put(new BigDecimal("1"), "");
		
		ExcessLookup lookup = new ExcessLookup(config, "overPerformed");
		assertEquals("Unexpected lookup code", "", lookup.getExcessCode(null));
	}
	
	@Test
	public void testValidMapBetween2() {
		Map<BigDecimal, String> config = new HashMap<BigDecimal, String>();
		config.put(new BigDecimal("-1"), "underPerformed");
		config.put(new BigDecimal("1"), "");
		
		ExcessLookup lookup = new ExcessLookup(config, "overPerformed");
		assertEquals("Unexpected lookup code", "", lookup.getExcessCode(new BigDecimal("-0.999999999999")));
	}
	
	@Test
	public void testValidMapBetween1() {
		Map<BigDecimal, String> config = new HashMap<BigDecimal, String>();
		config.put(new BigDecimal("-1"), "underPerformed");
		config.put(new BigDecimal("1"), "");
		
		ExcessLookup lookup = new ExcessLookup(config, "overPerformed");
		assertEquals("Unexpected lookup code", "", lookup.getExcessCode(new BigDecimal("0.99999999999")));
	}
	
	@Test
	public void testValidMapUnder() {
		Map<BigDecimal, String> config = new HashMap<BigDecimal, String>();
		config.put(new BigDecimal("-1"), "underPerformed");
		config.put(new BigDecimal("1"), "");
		
		ExcessLookup lookup = new ExcessLookup(config, "overPerformed");
		assertEquals("Unexpected lookup code", "underPerformed", lookup.getExcessCode(new BigDecimal("-1.00000001")));
	}
	
	@Test
	public void testValidMapOver() {
		Map<BigDecimal, String> config = new HashMap<BigDecimal, String>();
		config.put(new BigDecimal("-1"), "underPerformed");
		config.put(new BigDecimal("1"), "");
		
		ExcessLookup lookup = new ExcessLookup(config, "overPerformed");
		assertEquals("Unexpected lookup code", "overPerformed", lookup.getExcessCode(new BigDecimal("1.000000001")));
	}
	
	@Test
	public void testBigMapFull() {
		Map<BigDecimal, String> config = new HashMap<BigDecimal, String>();
		config.put(new BigDecimal("-2"), "less than -2");
		config.put(new BigDecimal("-1"), "between -2 and -1");
		config.put(new BigDecimal("0"), "between -1 and 0");
		config.put(new BigDecimal("1"), "between 0 and 1");
		config.put(new BigDecimal("2"), "between 1 and 2");
		
		ExcessLookup lookup = new ExcessLookup(config, "more than 2");
		assertEquals("Unexpected lookup code", "less than -2", lookup.getExcessCode(new BigDecimal("-2.00001")));
		assertEquals("Unexpected lookup code", "between -2 and -1", lookup.getExcessCode(new BigDecimal("-2")));
		assertEquals("Unexpected lookup code", "between -2 and -1", lookup.getExcessCode(new BigDecimal("-1.999999999")));
		assertEquals("Unexpected lookup code", "between -2 and -1", lookup.getExcessCode(new BigDecimal("-1.000000001")));
		assertEquals("Unexpected lookup code", "between -1 and 0", lookup.getExcessCode(new BigDecimal("-1")));
		assertEquals("Unexpected lookup code", "between -1 and 0", lookup.getExcessCode(new BigDecimal("-0.00000000001")));
		assertEquals("Unexpected lookup code", "between -1 and 0", lookup.getExcessCode(new BigDecimal("-0.9999999999")));
		assertEquals("Unexpected lookup code", "between 0 and 1", lookup.getExcessCode(new BigDecimal("0")));
		assertEquals("Unexpected lookup code", "between 1 and 2", lookup.getExcessCode(new BigDecimal("1")));
		assertEquals("Unexpected lookup code", "between 1 and 2", lookup.getExcessCode(new BigDecimal("1.999999999")));
		assertEquals("Unexpected lookup code", "between 1 and 2", lookup.getExcessCode(new BigDecimal("1.000000001")));
		assertEquals("Unexpected lookup code", "more than 2", lookup.getExcessCode(new BigDecimal("2")));
	}
}
