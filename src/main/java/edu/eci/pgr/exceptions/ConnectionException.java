/**
 * 
 */
package edu.eci.pgr.exceptions;

import edu.eci.pgr.persistence.ConnectionFactory;

/**
 * Thrown to indicate that the application has attempted to connect
 * with a database because the connection parameters is not valid or
 * any problem is occurred in this process . 
 *
 * @author  Beatriz Rojas
 * @author  Felipe Villamil
 * @version 
 * @see     ConnectionFactory
 */
public class ConnectionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
    * Constructs a <code>ConnectionException</code> with no detail message.
    */
	public ConnectionException() {
	}

	/**
	 * Constructs a<code> ConnectionException </code>with detail message.
	 * @param mes
	 * 			the detail message
	 */
	public ConnectionException(String mes) {
		super(mes);
	}

	/**
	 * Constructs a <code>ConnectionException</code> with detail cause.
	 * @param cause
	 * 			the cause
	 */
	public ConnectionException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a <code>ConnectionException</code> with detail cause
	 * and detail message
	 * @param cause
	 * 			the cause
	 * @param mes
	 * 			the message
	 */
	public ConnectionException(String mes, Throwable cause) {
		super(mes + cause.getMessage(), cause);
	}

}