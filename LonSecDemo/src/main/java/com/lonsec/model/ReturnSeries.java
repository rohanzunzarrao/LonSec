package com.lonsec.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Class representing the return of an instrument.
 * 
 * @author Rohan Z
 *
 */
public class ReturnSeries {

	/**
	 * The instrument code.
	 */
	private String code;
	
	/**
	 * The date of the return.
	 */
	private Date returnDate;
	
	/**
	 * The return value.
	 */
	private BigDecimal returnPercentage;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public BigDecimal getReturnPercentage() {
		return returnPercentage;
	}

	public void setReturnPercentage(BigDecimal returnPercentage) {
		this.returnPercentage = returnPercentage;
	}
}
