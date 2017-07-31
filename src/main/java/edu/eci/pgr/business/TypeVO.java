package edu.eci.pgr.business;

import java.io.Serializable;

public abstract class TypeVO implements Serializable{
	
	/** the type **/
	private String name;
	/** the range of type**/
	private int range;
	/** the maximun decimal values**/
	private int decimaldigits;
	
	
	/**
	 * @param name
	 * @param range
	 * @param decimaldigits
	 */
	public TypeVO(String name, int range, int decimaldigits) {
		this.name = name;
		this.range = range;
		this.decimaldigits = decimaldigits;
	}

	public TypeVO(String name, int range) {
		super();
		this.name = name;
		this.range = range;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the range
	 */
	public int getRange() {
		return range;
	}
	/**
	 * @param range the range to set
	 */
	public void setRange(int range) {
		this.range = range;
	}
	public int getDecimaldigits() {
		return decimaldigits;
	}
	public void setDecimaldigits(int decimaldigits) {
		this.decimaldigits = decimaldigits;
	}
	
	
	/**
	 * verify if the array contains a type
	 * @param array; an array
	 * @return the boolean result
	 */
	public boolean isContain(String[] array){
		boolean ret=false;
		for (int i = 0; i < array.length && ! ret; i++) {
			if(array[i].equals(this.getName())) ret=true;
		}
		return ret;
	}
	
	public abstract boolean isInteger();
	
	public abstract boolean isDouble();
	
	public abstract boolean isString();
	
	public abstract boolean isDate();
	
	public abstract boolean isFile();
	
}
