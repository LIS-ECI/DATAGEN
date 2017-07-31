package edu.eci.pgr.exceptions;

public class DAOSelectException extends Exception {
	private static final long serialVersionUID = 1L;

	public DAOSelectException() {
	}

	public DAOSelectException(String arg0) {
		super(arg0);
	}

	public DAOSelectException(Throwable arg0) {
		super(arg0);
	}

	public DAOSelectException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
