/**
 * 
 */
package edu.eci.pgr.exceptions;

import edu.eci.pgr.i18n.MessageBundleManager;

/**
 * @author felipe
 *
 */
public class MySqlRetrieverException extends RetrieverException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -518616179481422221L;
	private static final String MESSAGE=MessageBundleManager.getString("MySqlRetrieverException.0"); //$NON-NLS-1$
	/**
	 * 
	 */
	public MySqlRetrieverException() {
	}

	/**
	 * @param message
	 */
	public MySqlRetrieverException(String message) {
		super(MESSAGE+message);
	}

	/**
	 * @param cause
	 */
	public MySqlRetrieverException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MySqlRetrieverException(String message, Throwable cause) {
		super(MESSAGE+message, cause);
	}

}
