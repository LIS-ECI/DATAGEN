/**
 * 
 */
package edu.eci.pgr.exceptions;

/**
 * @author felipe
 *
 */
public class RetrieverException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public RetrieverException() {
	}

	/**
	 * @param message
	 */
	public RetrieverException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public RetrieverException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public RetrieverException(String message, Throwable cause) {
		super(message+ cause.getMessage(), cause);
	}

}
