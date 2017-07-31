/**
 * 
 */
package edu.eci.pgr.exceptions;

import edu.eci.pgr.i18n.MessageBundleManager;

/**
 * @author felipe
 *
 */
public class OracleGeneratorException extends GeneratorException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String MESSAGE=MessageBundleManager.getString("OracleGeneratorException.0"); //$NON-NLS-1$
	/**
	 * 
	 */
	public OracleGeneratorException() {
	}

	/**
	 * @param message
	 */
	public OracleGeneratorException(String message) {
		super(MESSAGE+message);
	}

	/**
	 * @param cause
	 */
	public OracleGeneratorException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public OracleGeneratorException(String message, Throwable cause) {
		super(MESSAGE+message+cause.getMessage(), cause);
	}

}
