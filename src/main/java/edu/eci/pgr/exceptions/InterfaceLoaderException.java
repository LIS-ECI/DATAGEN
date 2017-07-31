/**
 * 
 */
package edu.eci.pgr.exceptions;

public class InterfaceLoaderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public InterfaceLoaderException() {
	}

	/**
	 * @param message
	 */
	public InterfaceLoaderException(String message) {
		super(message);
	}

	/**
	 * @param message
	 */
	public InterfaceLoaderException(Throwable message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InterfaceLoaderException(String message, Throwable cause) {
		super(message, cause);
	}

}
