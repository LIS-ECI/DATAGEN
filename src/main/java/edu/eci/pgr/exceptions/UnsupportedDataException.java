/**
 * 
 */
package edu.eci.pgr.exceptions;

/**
 * @author felipe
 *
 */
public class UnsupportedDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public UnsupportedDataException() {
	}

	/**
	 * @param message
	 */
	public UnsupportedDataException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public UnsupportedDataException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UnsupportedDataException(String message, Throwable cause) {
		super(message, cause);
	}

}
