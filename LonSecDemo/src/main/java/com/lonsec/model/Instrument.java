package com.lonsec.model;

/**
 * Instrument.
 * 
 * @author Rohan Z
 *
 */
public abstract class Instrument {

	/**
	 * The code of the instrument.
	 */
	private String code;
	
	/**
	 * The name of the instrument.
	 */
	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
