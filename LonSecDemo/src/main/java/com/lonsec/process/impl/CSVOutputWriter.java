package com.lonsec.process.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.lonsec.model.FundPerformance;
import com.lonsec.process.OutputWriter;
import com.lonsec.util.PerformanceComparator;

/**
 * The CSV File writer. This class holds all the data in memory and sorts it before writing it into the file.
 * An alternative to this approach is a class the writes data to multiple files based on their dates and then individually sorts
 * these files and merges them into one massive file. As this approach uses multiple IO, we have proceeded with the assumption
 * that the file is not very large and can be held in memory.
 * 
 * @author Rohan Z
 *
 */
public class CSVOutputWriter implements OutputWriter {

	private List<FundPerformance> monthlyOutperformance = new ArrayList<FundPerformance>();
	private String fileName = null;
	private String filePath = null;
	private String delimiter = null;
	private String expectedPattern = "dd/MM/yyyy";
    private SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);

	public CSVOutputWriter(String fileName, String delimiter) {
		this.fileName = fileName;
		this.delimiter = delimiter;
	}
	
	public void writeTo(String fundsReturnsFile) {
		File returnsFile = new File(fundsReturnsFile);
		String parent = returnsFile.getParent();
		
		if(parent != null)
			filePath = parent + "/" + fileName;
		else
			filePath = fileName; 
	}

	public void writeRecord(FundPerformance performance) {
		monthlyOutperformance.add(performance);
	}

	public void flush() {
		Collections.sort(monthlyOutperformance, new PerformanceComparator());
        try {
			FileOutputStream fileOutputStream = new FileOutputStream(filePath, false);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, 128 * 100);
			StringBuilder messageToWrite = new StringBuilder();

			messageToWrite.append("FundName");
			messageToWrite.append(delimiter);
			messageToWrite.append("Date");
			messageToWrite.append(delimiter);
			messageToWrite.append("Excess");
			messageToWrite.append(delimiter);
			messageToWrite.append("OutPerformance");
			messageToWrite.append(delimiter);
			messageToWrite.append("Return");
			messageToWrite.append(delimiter);
			messageToWrite.append("Rank");
			messageToWrite.append("\r\n");
			
		    bufferedOutputStream.write(messageToWrite.toString().getBytes());
		    messageToWrite.delete(0, messageToWrite.length());
		    
		    Date date = null;
		    int rank = 1;

			for (FundPerformance performance : monthlyOutperformance) {
				messageToWrite.append(performance.getFundName());
				messageToWrite.append(delimiter);
				messageToWrite.append(formatter.format(performance.getDate()));
				messageToWrite.append(delimiter);
				messageToWrite.append(performance.getExcess().setScale(2, BigDecimal.ROUND_HALF_UP));
				messageToWrite.append(delimiter);
				messageToWrite.append(performance.getExcessCode());
				messageToWrite.append(delimiter);
				messageToWrite.append(performance.getFundReturn().setScale(2, BigDecimal.ROUND_HALF_UP));
				messageToWrite.append(delimiter);
				
				if(date == null || !performance.getDate().equals(date)) {
					rank = 1;
					date = performance.getDate();
				}
				else {
					rank++;
				}
				
				messageToWrite.append(rank);
				messageToWrite.append("\r\n");
				
			    bufferedOutputStream.write(messageToWrite.toString().getBytes());
			    messageToWrite.delete(0, messageToWrite.length());
			}
			
			bufferedOutputStream.flush();
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeWriter() {
	}

	public String getExpectedPattern() {
		return expectedPattern;
	}

	public void setExpectedPattern(String expectedPattern) {
		this.expectedPattern = expectedPattern;
	}
}
