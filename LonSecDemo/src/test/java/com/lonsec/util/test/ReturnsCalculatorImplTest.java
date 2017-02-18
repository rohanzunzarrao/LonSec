package com.lonsec.util.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.lonsec.model.Fund;
import com.lonsec.model.FundPerformance;
import com.lonsec.model.ReturnSeries;
import com.lonsec.process.ExcessLookup;
import com.lonsec.process.impl.ReturnsCalculatorImpl;

public class ReturnsCalculatorImplTest {
	
	Date today = new Date();
	Map<BigDecimal, String> config = new HashMap<BigDecimal, String>();
	
	ExcessLookup lookup = null;
	
	ReturnsCalculatorImpl calculator = null;
	
	@Before
	public void setup() {
		if(config.isEmpty()) {
			config.put(new BigDecimal("-1"), "underPerformed");
			config.put(new BigDecimal("1"), "");
		}
		
		if(lookup == null)
			lookup = new ExcessLookup(config, "overPerformed");
		
		if(calculator == null && lookup != null)
			calculator = new ReturnsCalculatorImpl(lookup);
	}

	@Test
	public void testUnderPerformingFund() {
		ReturnSeries fundReturn = new ReturnSeries();
		fundReturn.setCode("abc");
		fundReturn.setReturnDate(today);
		BigDecimal fundReturnVal = new BigDecimal("-4.229084292");
		fundReturn.setReturnPercentage(fundReturnVal);
		
		Fund fund = new Fund();
		fund.setCode("abc");
		fund.setName("ABC");
		fund.setBenchmarkCode("def");
		
		ReturnSeries benchmarkReturns = new ReturnSeries();
		benchmarkReturns.setCode("def");
		benchmarkReturns.setReturnDate(today);
		BigDecimal bmRVal = new BigDecimal("-1.809283199");
		benchmarkReturns.setReturnPercentage(bmRVal);
		
		FundPerformance performance = calculator.calculate(fundReturn, fund, benchmarkReturns);
		
		assertEquals("Incorrect fund name", "ABC", performance.getFundName());
		assertEquals("Incorrect date", today, performance.getDate());
		assertEquals("Incorrect excess %", fundReturnVal.subtract(bmRVal), performance.getExcess());
		assertEquals("Incorrect excess code", "underPerformed", performance.getExcessCode());
		assertEquals("Incorrect fund name", fundReturnVal, performance.getFundReturn());
	}
	
	@Test
	public void testAvgPerformingFund() {
		ReturnSeries fundReturn = new ReturnSeries();
		fundReturn.setCode("abc");
		fundReturn.setReturnDate(today);
		BigDecimal fundReturnVal = new BigDecimal("-2.229084292");
		fundReturn.setReturnPercentage(fundReturnVal);
		
		Fund fund = new Fund();
		fund.setCode("abc");
		fund.setName("ABC");
		fund.setBenchmarkCode("def");
		
		ReturnSeries benchmarkReturns = new ReturnSeries();
		benchmarkReturns.setCode("def");
		benchmarkReturns.setReturnDate(today);
		BigDecimal bmRVal = new BigDecimal("-1.809283199");
		benchmarkReturns.setReturnPercentage(bmRVal);
		
		FundPerformance performance = calculator.calculate(fundReturn, fund, benchmarkReturns);
		
		assertEquals("Incorrect fund name", "ABC", performance.getFundName());
		assertEquals("Incorrect date", today, performance.getDate());
		assertEquals("Incorrect excess %", fundReturnVal.subtract(bmRVal), performance.getExcess());
		assertEquals("Incorrect excess code", "", performance.getExcessCode());
		assertEquals("Incorrect fund name", fundReturnVal, performance.getFundReturn());
	}

	@Test
	public void testBenchmarkPerformingFund() {
		ReturnSeries fundReturn = new ReturnSeries();
		fundReturn.setCode("abc");
		fundReturn.setReturnDate(today);
		BigDecimal fundReturnVal = new BigDecimal("-1.809283199");
		fundReturn.setReturnPercentage(fundReturnVal);
		
		Fund fund = new Fund();
		fund.setCode("abc");
		fund.setName("ABC");
		fund.setBenchmarkCode("def");
		
		ReturnSeries benchmarkReturns = new ReturnSeries();
		benchmarkReturns.setCode("def");
		benchmarkReturns.setReturnDate(today);
		BigDecimal bmRVal = new BigDecimal("-1.809283199");
		benchmarkReturns.setReturnPercentage(bmRVal);
		
		FundPerformance performance = calculator.calculate(fundReturn, fund, benchmarkReturns);
		
		assertEquals("Incorrect fund name", "ABC", performance.getFundName());
		assertEquals("Incorrect date", today, performance.getDate());
		assertEquals("Incorrect excess %", fundReturnVal.subtract(bmRVal), performance.getExcess());
		assertEquals("Incorrect excess code", "", performance.getExcessCode());
		assertEquals("Incorrect fund name", fundReturnVal, performance.getFundReturn());
	}
	
	@Test
	public void testOverPerformingFund() {
		ReturnSeries fundReturn = new ReturnSeries();
		fundReturn.setCode("abc");
		fundReturn.setReturnDate(today);
		BigDecimal fundReturnVal = new BigDecimal("-1.809283199");
		fundReturn.setReturnPercentage(fundReturnVal);
		
		Fund fund = new Fund();
		fund.setCode("abc");
		fund.setName("ABC");
		fund.setBenchmarkCode("def");
		
		ReturnSeries benchmarkReturns = new ReturnSeries();
		benchmarkReturns.setCode("def");
		benchmarkReturns.setReturnDate(today);
		BigDecimal bmRVal = new BigDecimal("-4.229084292");
		benchmarkReturns.setReturnPercentage(bmRVal);
		
		FundPerformance performance = calculator.calculate(fundReturn, fund, benchmarkReturns);
		
		assertEquals("Incorrect fund name", "ABC", performance.getFundName());
		assertEquals("Incorrect date", today, performance.getDate());
		assertEquals("Incorrect excess %", fundReturnVal.subtract(bmRVal), performance.getExcess());
		assertEquals("Incorrect excess code", "overPerformed", performance.getExcessCode());
		assertEquals("Incorrect fund name", fundReturnVal, performance.getFundReturn());
	}
	
	@Test
	public void testFundWOBenchmark() {
		ReturnSeries fundReturn = new ReturnSeries();
		fundReturn.setCode("abc");
		fundReturn.setReturnDate(today);
		BigDecimal fundReturnVal = new BigDecimal("-1.809283199");
		fundReturn.setReturnPercentage(fundReturnVal);
		
		Fund fund = new Fund();
		fund.setCode("abc");
		fund.setName("ABC");
		fund.setBenchmarkCode("def");
		
		FundPerformance performance = calculator.calculate(fundReturn, fund, null);
		
		assertEquals("Incorrect fund name", "ABC", performance.getFundName());
		assertEquals("Incorrect date", today, performance.getDate());
		assertEquals("Incorrect excess %", fundReturnVal, performance.getExcess());
		assertEquals("Incorrect excess code", "", performance.getExcessCode());
		assertEquals("Incorrect fund name", fundReturnVal, performance.getFundReturn());
	}
}
