package edu.eci.pgr.business.types;

import edu.eci.pgr.business.TypeVO;

public class MySqlTypeVO extends TypeVO{
	private static final long serialVersionUID = 1L;
	/** integer types*/
	private static final String[] INTEGERTYPES = {
		"INTEGER", "INT","TINYINT","SMALLINT","MEDIUMINT","BIGINT","BINARY"
	};
	/** double types*/
	private static final String[] DOUBLETYPES = {
		"DOUBLE","FLOAT","REAL","DECIMAL"
	};
	/** string types */
	private static final String[] STRINGTYPES = {
		"VARCHAR", "CHAR", "TINYTEXT","TEXT","MEDIUMTEXT","LONGTEXT"
	};
	/** date types*/
	private static final String[] DATETYPES={
		"TIMESTAMP","DATE","TIME","DATETIME","YEAR"
		};
	/** file types*/
	private static final String[] FILETYPES={
		"TINYBLOB","BLOB","MEDIUMBLOB","LONGBLOB"
		};
	
	
	public MySqlTypeVO(String name, int range, int decimaldigits) {
		super(name, range, decimaldigits);
	}

	public boolean isDate() {
		return this.isContain(DATETYPES);
	}

	public boolean isDouble() {
		return this.isContain(DOUBLETYPES);
	}

	public boolean isInteger() {
		return this.isContain(INTEGERTYPES);
	}

	public boolean isString() {
		return this.isContain(STRINGTYPES);
	}
	
	public boolean isFile(){
		return this.isContain(FILETYPES);
	}
	

}
