package com.lonsec.process;

import com.lonsec.model.Fund;
import com.lonsec.model.FundPerformance;
import com.lonsec.model.ReturnSeries;

/**
 * Calculate the Funds performance based on the spcified benchmark
 * 
 * @author Rohan Z
 *
 */
public interface ReturnsCalculator {

	public FundPerformance calculate(ReturnSeries fundReturn, Fund fund, ReturnSeries benchmarkReturns);

}
