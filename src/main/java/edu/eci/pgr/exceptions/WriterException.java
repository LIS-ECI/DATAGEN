/**
 * 
 */
package edu.eci.pgr.exceptions;

public class WriterException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public WriterException() {
	}

	/**
	 * @param message
	 */
	public WriterException(String message) {
		super(message);
	}

	/**
	 * @param message
	 */
	public WriterException(Throwable message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public WriterException(String message, Throwable cause) {
		super(message, cause);
	}

}
