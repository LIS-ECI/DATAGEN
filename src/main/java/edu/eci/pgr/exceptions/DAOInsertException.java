package edu.eci.pgr.exceptions;

public class DAOInsertException extends Exception {

	private static final long serialVersionUID = 1L;

	public DAOInsertException() {
	}

	public DAOInsertException(String arg0) {
		super(arg0);
	}

	public DAOInsertException(Throwable arg0) {
		super(arg0);
	}

	public DAOInsertException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
