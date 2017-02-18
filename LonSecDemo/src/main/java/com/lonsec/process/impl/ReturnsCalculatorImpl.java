package com.lonsec.process.impl;

import java.math.BigDecimal;

import com.lonsec.model.Fund;
import com.lonsec.model.FundPerformance;
import com.lonsec.model.ReturnSeries;
import com.lonsec.process.ExcessLookup;
import com.lonsec.process.ReturnsCalculator;

/**
 * Implementation of the ReturnsCalculator interface. This class calculates the excess using the formula
 * <br></br>
 * excess returns = fund return - benchmark return
 * <br></br>
 * It then uses this data to get the excess code using the configured excess lookup.
 * 
 * @author Rohan Z
 * @see ReturnsCalculator
 * @see ExcessLookup
 */
public class ReturnsCalculatorImpl implements ReturnsCalculator {
	
	ExcessLookup excessLookup = null;
	
	public ReturnsCalculatorImpl(ExcessLookup lookup) {
		excessLookup = lookup;
	}

	public FundPerformance calculate(ReturnSeries fundReturn, Fund fund, ReturnSeries benchmarkReturns) {
		FundPerformance fundPerformance = new FundPerformance();
		fundPerformance.setFundName(fund.getName());
		fundPerformance.setDate(fundReturn.getReturnDate());
		fundPerformance.setFundReturn(fundReturn.getReturnPercentage());
		
		BigDecimal excess = null;
		
		if(benchmarkReturns == null)
			excess = fundReturn.getReturnPercentage();
		else
			excess = fundReturn.getReturnPercentage().subtract(benchmarkReturns.getReturnPercentage());
		
		fundPerformance.setExcess(excess);
		
		if(benchmarkReturns == null)
			fundPerformance.setExcessCode("");
		else
			fundPerformance.setExcessCode(excessLookup.getExcessCode(excess));
		
		return fundPerformance;
	}

	public ExcessLookup getExcessLookup() {
		return excessLookup;
	}

	public void setExcessLookup(ExcessLookup excessLookup) {
		this.excessLookup = excessLookup;
	}
}
