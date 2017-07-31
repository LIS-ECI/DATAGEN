package edu.eci.pgr.exceptions;

public class UnsupportedAttributeValueException extends Exception {

	private static final long serialVersionUID = 1L;

	public UnsupportedAttributeValueException() {
	}

	public UnsupportedAttributeValueException(String arg0) {
		super(arg0);
	}

	public UnsupportedAttributeValueException(Throwable arg0) {
		super(arg0);
	}

	public UnsupportedAttributeValueException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
