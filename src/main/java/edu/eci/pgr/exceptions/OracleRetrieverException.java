/**
 * 
 */
package edu.eci.pgr.exceptions;

import edu.eci.pgr.i18n.MessageBundleManager;

/**
 * @author felipe
 *
 */
public class OracleRetrieverException extends RetrieverException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE=MessageBundleManager.getString("OracleRetrieverException.0"); //$NON-NLS-1$
	/**
	 * 
	 */
	public OracleRetrieverException() {
	}

	/**
	 * @param message
	 */
	public OracleRetrieverException(String message) {
		super(MESSAGE+message);
	}

	/**
	 * @param cause
	 */
	public OracleRetrieverException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public OracleRetrieverException(String message, Throwable cause) {
		super(MESSAGE+message, cause);
	}

}
