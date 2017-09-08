package edu.eci.pgr.exceptions;

import edu.eci.pgr.components.SortJgraphComponent;
import edu.eci.pgr.sorting.SortingTool;
import edu.eci.pgr.sorting.jgrapht.JGraphTSortingTool;


/**
 * Thrown to indicate that the application has attempted to connect
 * with a database because the connection parameters is not valid or
 * any problem is occurred in this process . 
 *
 * @author  Beatriz Rojas
 * @author  Felipe Villamil
 * @version 
 * @see SortJgraphComponent#sortTables(java.util.List)
 * @see SortingTool
 * @see JGraphTSortingTool
 * @see TestSorting
 * @see CycleTest
 * 
 */
public class CycleDetectedException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	* Constructs a <code> CycleDetectedException </code> with no detail message.
	*/
	public CycleDetectedException() {
	}
	
	/**
	 * Constructs a<code> CycleDetectedException </code>with detail message.
	 * @param mes
	 * 			the detail message
	 */
	public CycleDetectedException(String mes) {
		super(mes);
	}
	
	/**
	 * Constructs a <code>CycleDetectedException</code> with detail cause.
	 * @param cause
	 * 			the cause
	 */
	public CycleDetectedException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a <code>CycleDetectedException</code> with detail cause
	 * and detail message
	 * @param cause
	 * 			the cause
	 * @param mes
	 * 			the message
	 */
	public CycleDetectedException(String mes, Throwable cause) {
		super(mes, cause);
	}

}
