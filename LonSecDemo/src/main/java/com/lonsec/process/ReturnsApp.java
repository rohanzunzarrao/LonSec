package com.lonsec.process;

import org.springframework.context.ApplicationContext;

import com.lonsec.config.AppContext;

/**
 * This is the entry point of the application. This class can be invoked from the command line
 * by specifying four command line parameters, as follows
 * 
 * <li> Funds file - The file that contains the Funds master data i.e. Fund Code, Fund Name, Benchmark Code.
 * <li> Benchmark file - The file that contains the Benchmark master data i.e. Benchmark Code, Benchmark Name.
 * <li> Funds Returns file - The file containing the details of Fund returns for a date.
 * <li> Benchmark Returns file - The file containing the details of Benchmark returns for a date.
 * <br></br>
 * The class would generate an output file named monthlyPerformance.csv in the same location as the 
 * funds returns file with the monthly performance data. If a file with the same name already exists at
 * the location, it would be overwritten.
 * <br></br>
 * <b>Note:</b> Although the above documentation talks in terms of file, these could be replaced by othe
 * input and output sources.
 * 
 * @author Rohan Z
 *
 */
public class ReturnsApp {

	public static void main(String[] args) {
		if(args.length < 4) {
			System.out.println("Please execute as 'ReturnsApp funds.csv benchmark.csv fundsReturns.csv benchmarkReturns.csv'");
			return;
		}
		
		String fundsFile = args[0];
		String benchmarkFile = args[1];
		String fundsReturnsFile = args[2];
		String benchmarkReturnsFile = args[3];
		
		ApplicationContext context = AppContext.getContext();
		ReturnsProcessor processor = context.getBean(ReturnsProcessor.class);
		processor.process(fundsFile, benchmarkFile, fundsReturnsFile, benchmarkReturnsFile);
	}
}
