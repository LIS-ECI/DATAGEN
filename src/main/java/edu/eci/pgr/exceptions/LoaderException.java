/**
 * 
 */
package edu.eci.pgr.exceptions;

public class LoaderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public LoaderException() {
	}

	/**
	 * @param message
	 */
	public LoaderException(String message) {
		super(message);
	}

	/**
	 * @param message
	 */
	public LoaderException(Throwable message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public LoaderException(String message, Throwable cause) {
		super(message+"\n"+cause.getMessage(), cause);
	}

}
