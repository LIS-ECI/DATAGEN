package edu.eci.pgr.components.combination;

import java.io.Serializable;


public class CombinationDescription implements Serializable{
	private static final long serialVersionUID = 1L;

	private long finalValue;
	private int direction;
	private long currentValue;
	private long initialValue;
	public CombinationDescription() {
		super();
	}

	public CombinationDescription(long finalValue, int direction,
			long currentValue) {
		super();
		this.finalValue = finalValue;
		this.direction = direction;
		this.currentValue = currentValue;
		this.initialValue = currentValue;
	}

	public long getFinalValue() {
		return finalValue;
	}

	public int getDirection() {
		return direction;
	}

	public long getCurrentValue() {
		return currentValue;
	}

	public long getInitialValue() {
		return initialValue;
	}

	public void setFinalValue(long finalValue) {
		this.finalValue = finalValue;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public void setCurrentValue(long currentValue) {
		this.currentValue = currentValue;
	}

	public void setInitialValue(long initialValue) {
		this.initialValue = initialValue;
	}

	public long getNextValue(){
		currentValue +=direction;
		if(currentValue > finalValue-1)
			currentValue = 0;
		else if(currentValue <0)
			currentValue = finalValue-1;
		return currentValue;
	}
	
	public boolean isAtInitialValue(){
		return (currentValue == initialValue);
	}
	public void reset(){
		currentValue = initialValue;
	}
}
