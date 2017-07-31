/**
 * 
 */
package edu.eci.pgr.exceptions;

/**
 * @author felipe
 *
 */
public class GeneratorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public GeneratorException() {
	}

	/**
	 * @param message
	 */
	public GeneratorException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public GeneratorException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public GeneratorException(String message, Throwable cause) {
		super(message, cause);
	}

}
