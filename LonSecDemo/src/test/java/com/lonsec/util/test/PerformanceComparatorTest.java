package com.lonsec.util.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

import com.lonsec.model.FundPerformance;
import com.lonsec.util.PerformanceComparator;

public class PerformanceComparatorTest {
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	PerformanceComparator comparator = new PerformanceComparator();

	@Test
	public void testEquals() {
		try {
			FundPerformance performance1 = new FundPerformance();
			performance1.setDate(dateFormat.parse("30/06/2016"));
			performance1.setFundReturn(new BigDecimal("0.1"));
			
			FundPerformance performance2 = new FundPerformance();
			performance2.setDate(dateFormat.parse("30/06/2016"));
			performance2.setFundReturn(new BigDecimal("0.1"));
			
			assertEquals("Funds performances returned as not equal.", 0, comparator.compare(performance1, performance2));
		} catch (ParseException e) {
			assertFalse("ParseException", true);
		}
	}
	
	@Test
	public void testDateBefore() {
		try {
			FundPerformance performance1 = new FundPerformance();
			performance1.setDate(dateFormat.parse("29/06/2016"));
			performance1.setFundReturn(new BigDecimal("0.1"));
			
			FundPerformance performance2 = new FundPerformance();
			performance2.setDate(dateFormat.parse("30/06/2016"));
			performance2.setFundReturn(new BigDecimal("0.1"));
			
			assertEquals("Funds performance 1 was not less than 2.", -1, comparator.compare(performance1, performance2));
		} catch (ParseException e) {
			assertFalse("ParseException", true);
		}
	}
	
	@Test
	public void testDateAfter() {
		try {
			FundPerformance performance1 = new FundPerformance();
			performance1.setDate(dateFormat.parse("01/07/2016"));
			performance1.setFundReturn(new BigDecimal("0.1"));
			
			FundPerformance performance2 = new FundPerformance();
			performance2.setDate(dateFormat.parse("30/06/2016"));
			performance2.setFundReturn(new BigDecimal("0.1"));
			
			assertEquals("Funds performances 2 was not less than 1.", 1, comparator.compare(performance1, performance2));
		} catch (ParseException e) {
			assertFalse("ParseException", true);
		}
	}
	
	@Test
	public void testReturns() {
		try {
			FundPerformance performance1 = new FundPerformance();
			performance1.setDate(dateFormat.parse("30/06/2016"));
			performance1.setFundReturn(new BigDecimal(".12"));
			
			FundPerformance performance2 = new FundPerformance();
			performance2.setDate(dateFormat.parse("30/06/2016"));
			performance2.setFundReturn(new BigDecimal(".15"));
			
			assertEquals("Funds performance 2 was not less than 1.", 1, comparator.compare(performance1, performance2));
		} catch (ParseException e) {
			assertFalse("ParseException", true);
		}
	}
	
	@Test
	public void testNulls() {
		assertEquals("Funds performances are not equal.", 0, comparator.compare(null, null));
	}
	
	@Test
	public void testNull1() {
		try {
			FundPerformance performance2 = new FundPerformance();
			performance2.setDate(dateFormat.parse("01/07/2016"));
			performance2.setFundReturn(new BigDecimal("0.1"));
			
			assertEquals("Funds performances 2 was not less than 1.", -1, comparator.compare(null, performance2));
		} catch (ParseException e) {
			assertFalse("ParseException", true);
		}
	}
	
	@Test
	public void testNull2() {
		try {
			FundPerformance performance1 = new FundPerformance();
			performance1.setDate(dateFormat.parse("01/07/2016"));
			performance1.setFundReturn(new BigDecimal("0.1"));
			
			assertEquals("Funds performances 2 was not less than 1.", 1, comparator.compare(performance1, null));
		} catch (ParseException e) {
			assertFalse("ParseException", true);
		}
	}
}