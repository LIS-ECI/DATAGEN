package edu.eci.pgr.exceptions;

public class UnsupportedAttribute extends Exception {

	private static final long serialVersionUID = 1L;

	public UnsupportedAttribute() {
	}

	public UnsupportedAttribute(String message) {
		super(message);
	}

	public UnsupportedAttribute(Throwable cause) {
		super(cause);
	}

	public UnsupportedAttribute(String message, Throwable cause) {
		super(message, cause);
	}

}
