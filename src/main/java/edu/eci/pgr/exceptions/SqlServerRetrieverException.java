package edu.eci.pgr.exceptions;

import edu.eci.pgr.i18n.MessageBundleManager;

public class SqlServerRetrieverException extends RetrieverException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -518616179481422221L;
	private static final String MESSAGE=MessageBundleManager.getString("SqlServerRetrieverException.0"); //$NON-NLS-1$
	/**
	 * 
	 */
	public SqlServerRetrieverException() {
	}

	/**
	 * @param message
	 */
	public SqlServerRetrieverException(String message) {
		super(MESSAGE+message);
	}

	/**
	 * @param cause
	 */
	public SqlServerRetrieverException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SqlServerRetrieverException(String message, Throwable cause) {
		super(MESSAGE+message, cause);
	}

}
