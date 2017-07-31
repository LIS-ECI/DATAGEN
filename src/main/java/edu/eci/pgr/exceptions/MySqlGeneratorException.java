/**
 * 
 */
package edu.eci.pgr.exceptions;

import edu.eci.pgr.i18n.MessageBundleManager;

/**
 * @author felipe
 *
 */
public class MySqlGeneratorException extends GeneratorException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String MESSAGE=MessageBundleManager.getString("MySqlGeneratorException.0"); //$NON-NLS-1$
	/**
	 * 
	 */
	public MySqlGeneratorException() {
	}

	/**
	 * @param message
	 */
	public MySqlGeneratorException(String message) {
		super(MESSAGE+message);
	}

	/**
	 * @param cause
	 */
	public MySqlGeneratorException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MySqlGeneratorException(String message, Throwable cause) {
		super(MESSAGE+message + cause.getMessage(), cause);
	}

}
