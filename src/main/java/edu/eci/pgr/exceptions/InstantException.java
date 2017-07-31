package edu.eci.pgr.exceptions;

public class InstantException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public InstantException() {
	}

	/**
	 * @param message
	 */
	public InstantException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public InstantException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InstantException(String message, Throwable cause) {
		super(message, cause);
	}

}
