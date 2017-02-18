package com.lonsec.process.impl;

import java.util.List;

import com.lonsec.model.Fund;
import com.lonsec.model.FundPerformance;
import com.lonsec.model.ReturnSeries;
import com.lonsec.process.BenchmarkLookup;
import com.lonsec.process.FundsLookup;
import com.lonsec.process.OutputWriter;
import com.lonsec.process.ReturnsCalculator;
import com.lonsec.process.ReturnsProcessor;
import com.lonsec.process.ReturnsReader;

/**
 * The implementation of the ReturnsProcessor interface.
 * 
 * @author Rohan Z
 * @see ReturnsProcessor
 */
public class ReturnProcessorImpl implements ReturnsProcessor {
	
	private ReturnsReader fundsReturnsReader;
	private FundsLookup fundsLookup;
	private BenchmarkLookup benchmarkLookup;
	private ReturnsReader benchmarkReturnsReader;
	private ReturnsCalculator calculator;
	private OutputWriter writer;

	public void process(String fundsFile, String benchmarkFile,
			String fundsReturnsFile, String benchmarkReturnsFile) {
		// Initialize the beans
		fundsReturnsReader.openReader(fundsReturnsFile);
		benchmarkReturnsReader.openReader(benchmarkReturnsFile);
		fundsLookup.loadFrom(fundsFile);
		// benchmarkLookup.loadFrom(benchmarkFile);
		writer.writeTo(fundsReturnsFile);
		
		List<ReturnSeries> fundReturns = fundsReturnsReader.read();
		
		while(fundReturns != null && fundReturns.size() > 0) {
			for(ReturnSeries fundReturn : fundReturns) {
				String fundCode = fundReturn.getCode();
				Fund fund = fundsLookup.lookup(fundCode);

				if(fund == null)
					continue;

				String benchmarkCode = fund.getBenchmarkCode();
				ReturnSeries benchmarkReturns = benchmarkReturnsReader.lookup(benchmarkCode, fundReturn.getReturnDate());
				FundPerformance performance = calculator.calculate(fundReturn, fund, benchmarkReturns);
				writer.writeRecord(performance);
			}
			
			fundReturns = fundsReturnsReader.read();
		}
		
		fundsReturnsReader.closeReader();
		benchmarkReturnsReader.closeReader();
		writer.flush();
		writer.closeWriter();
	}

	public FundsLookup getFundsLookup() {
		return fundsLookup;
	}

	public void setFundsLookup(FundsLookup fundsLookup) {
		this.fundsLookup = fundsLookup;
	}

	public BenchmarkLookup getBenchmarkLookup() {
		return benchmarkLookup;
	}

	public void setBenchmarkLookup(BenchmarkLookup benchmarkLookup) {
		this.benchmarkLookup = benchmarkLookup;
	}

	public ReturnsCalculator getCalculator() {
		return calculator;
	}

	public void setReturnsCalculator(ReturnsCalculator calculator) {
		this.calculator = calculator;
	}

	public OutputWriter getWriter() {
		return writer;
	}

	public void setOutputWriter(OutputWriter writer) {
		this.writer = writer;
	}

	public ReturnsReader getFundsReturnsReader() {
		return fundsReturnsReader;
	}

	public void setFundsReturnsReader(ReturnsReader fundsReturnsReader) {
		this.fundsReturnsReader = fundsReturnsReader;
	}

	public ReturnsReader getBenchmarkReturnsReader() {
		return benchmarkReturnsReader;
	}

	public void setBenchmarkReturnsReader(ReturnsReader benchmarkReturnsReader) {
		this.benchmarkReturnsReader = benchmarkReturnsReader;
	}
}
