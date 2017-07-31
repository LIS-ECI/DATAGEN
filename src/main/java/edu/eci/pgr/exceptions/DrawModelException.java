/**
 * 
 */
package edu.eci.pgr.exceptions;

import edu.eci.pgr.view.graph.GraphDrawer;

/**
 * Thrown to indicate that the application has not been
 * draw a entity relation model because any problem is 
 * occurred in this process . 
 *
 * @author  Beatriz Rojas
 * @author  Felipe Villamil
 * @version 
 * @see     GraphDrawer
 */
public class DrawModelException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
    * Constructs a <code>DrawModelException</code> with no detail message.
    */
	public DrawModelException() {
	}

	/**
	 * Constructs a<code> DrawModelException </code>with detail message.
	 * @param mes
	 * 			the detail message
	 */
	public DrawModelException(String mes) {
		super(mes);
	}

	/**
	 * Constructs a <code>DrawModelException</code> with detail cause.
	 * @param cause
	 * 			the cause
	 */
	public DrawModelException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a <code>DrawModelException</code> with detail cause
	 * and detail message
	 * @param cause
	 * 			the cause
	 * @param mes
	 * 			the message
	 */
	public DrawModelException(String mes, Throwable cause) {
		super(mes + cause.getMessage(), cause);
	}

}