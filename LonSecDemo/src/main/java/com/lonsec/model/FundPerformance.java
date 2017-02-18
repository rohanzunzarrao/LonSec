package com.lonsec.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The Funds Performance
 * 
 * @author Rohan Z
 *
 */
public class FundPerformance {
	/**
	 * The Fund Name
	 */
	private String fundName;
	
	/**
	 * Date of the funds return.
	 */
	private Date date;
	
	/**
	 * Excess % compared to the Benchmark return % on the same date
	 */
	private BigDecimal excess;
	
	/**
	 * The code configured for the excess %
	 */
	private String excessCode;
	
	/**
	 * The funds return % for the date.
	 */
	private BigDecimal fundReturn;

	public String getFundName() {
		return fundName;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public BigDecimal getExcess() {
		return excess;
	}
	public void setExcess(BigDecimal excess) {
		this.excess = excess;
	}
	public String getExcessCode() {
		return excessCode;
	}
	public void setExcessCode(String excessCode) {
		this.excessCode = excessCode;
	}
	public BigDecimal getFundReturn() {
		return fundReturn;
	}
	public void setFundReturn(BigDecimal fundReturn) {
		this.fundReturn = fundReturn;
	}
}
