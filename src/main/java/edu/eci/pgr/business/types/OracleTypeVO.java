package edu.eci.pgr.business.types;

import edu.eci.pgr.business.TypeVO;

public class OracleTypeVO extends TypeVO{
	private static final long serialVersionUID = 1L;
	private static final String NUMBER = "NUMBER";
	/** string types*/
	private static final String[] STRINGTYPES = {
		"VARCHAR2"
	};
	/** date types*/
	private static final String[] DATETYPES = {
		"DATE", "DATETIME","TIMESTAMP"
	};
	/**double types*/
	private static final String[] DOUBLETYPES = {
		"DOUBLE"
	};
	/**boolean types*/
	private static final String[] BOOLEANTYPES = {
		"BOOLEAN"
	};
	/**file types*/
	private static final String[] FILETYPES = {
		"CLOB","BLOB"
	};
	public OracleTypeVO(String name, int range, int decimaldigits) {
		super(name, range, decimaldigits);
	}

	public boolean isDate() {
		return (this.isContain(DATETYPES));
	}

	public boolean isDouble() {
		return (this.getName().equals(NUMBER) && this.getDecimaldigits() >0 || 
				this.isContain(DOUBLETYPES));
	}

	public boolean isInteger() {
		return (this.getName().equals(NUMBER) && this.getDecimaldigits() == 0);
	}

	public boolean isString() {
		return (this.isContain(STRINGTYPES));
	}
	
	public boolean isBoolean() {
		return this.isContain(BOOLEANTYPES);
	}
	
	public boolean isFile() {
		return this.isContain(FILETYPES);
	}

}
