package com.lonsec.util.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

import com.lonsec.model.ReturnSeries;
import com.lonsec.process.impl.CSVReturnsReader;

public class CSVReturnsReaderTest {
	
	private String regex = ",";
	private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
	@Test
	public void testFileNotFound() {
		CSVReturnsReader reader = new CSVReturnsReader("FULL", regex);
		reader.openReader("./testFiles/invalidReturns.csv");
		
		assertEquals("Expecting empty list", 0, reader.read().size());
		try {
			assertNull("Non Null Return found", reader.lookup("abc", formatter.parse("30/06/2016")));
		} catch (ParseException e) {
			e.printStackTrace();
			assertFalse("ParseException", true);
		}
	}
	
	@Test
	public void testValidFileReadAndLookup() {
		CSVReturnsReader reader = new CSVReturnsReader("FULL", regex);
		reader.openReader("./testFiles/Returns.csv");
		
		assertEquals("Expecting 36 rows", 36, reader.read().size());
		try {
			ReturnSeries value = reader.lookup("EF-2-21255", formatter.parse("30/06/2016"));
			assertNotNull("Null Return found", value);
		} catch (ParseException e) {
			e.printStackTrace();
			assertFalse("ParseException", true);
		}
	}
	
	@Test
	public void testValidFileLookupAndRead() {
		CSVReturnsReader reader = new CSVReturnsReader("FULL", regex);
		reader.openReader("./testFiles/Returns.csv");

		try {
			ReturnSeries value = reader.lookup("EF-2-21255", formatter.parse("30/06/2016"));
			assertNotNull("Null Return found", value);
		} catch (ParseException e) {
			e.printStackTrace();
			assertFalse("ParseException", true);
		}
		assertEquals("Expecting 0 rows", 0, reader.read().size());
	}
	
	@Test
	public void testValidFileUnsupportedMode() {
		CSVReturnsReader reader = new CSVReturnsReader("BATCH", regex);
		reader.openReader("./testFiles/Returns.csv");
		try {
			reader.read();
			assertFalse("Operation completed successfully", true);
		} catch (Exception e) {
			assertEquals("Unexpected exception", "UnsupportedOperationMode", e.getMessage());
		}
	}
	
	@Test
	public void testValidFileBatchModeLookup() {
		CSVReturnsReader reader = new CSVReturnsReader("BATCH", regex);
		reader.openReader("./testFiles/Returns.csv");
		try {
			ReturnSeries value = reader.lookup("EF-2-21255", formatter.parse("30/06/2016"));
			assertNotNull("Null Return found", value);
		} catch (Exception e) {
			e.printStackTrace();
			assertFalse("Unexpected exception", true);
		}
	}
	
	@Test
	public void testParseError() {
		CSVReturnsReader reader = new CSVReturnsReader("FULL", regex);
		reader.openReader("./testFiles/ReturnsParseError.csv");
		
		assertEquals("Expecting 34 rows", 34, reader.read().size());
		try {
			ReturnSeries value = reader.lookup("EF-2-21255", formatter.parse("30/06/2016"));
			assertNull("Non Null value found", value);
			
			value = reader.lookup("MF-1-4220", formatter.parse("30/11/2016"));
			assertNull("Non Null value found", value);
			
			value = reader.lookup("MF-1-4220", formatter.parse("31/10/2016"));
			assertNotNull("Null value found", value);
		} catch (ParseException e) {
			e.printStackTrace();
			assertFalse("ParseException", true);
		}
	}
	
	@Test
	public void testValidTabFile() {
		CSVReturnsReader reader = new CSVReturnsReader("FULL", "\t");
		reader.openReader("./testFiles/ReturnsTab.csv");
		
		assertEquals("Expecting 36 rows", 36, reader.read().size());
		try {
			ReturnSeries value = reader.lookup("EF-2-21255", formatter.parse("30/06/2016"));
			assertNotNull("Null Return found", value);
		} catch (ParseException e) {
			e.printStackTrace();
			assertFalse("ParseException", true);
		}
	}
}