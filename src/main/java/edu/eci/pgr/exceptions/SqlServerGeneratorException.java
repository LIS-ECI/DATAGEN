package edu.eci.pgr.exceptions;


public class SqlServerGeneratorException extends GeneratorException {

	private static final long serialVersionUID = 1L;

	public SqlServerGeneratorException() {

	}

	public SqlServerGeneratorException(String message) {
		super(message);
	}

	public SqlServerGeneratorException(Throwable cause) {
		super(cause);
	}

	public SqlServerGeneratorException(String message, Throwable cause) {
		super(message, cause);
	}

}