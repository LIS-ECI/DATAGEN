package edu.eci.pgr.business.types;

import edu.eci.pgr.business.TypeVO;

public class SqlServerTypeVO extends TypeVO{
	private static final long serialVersionUID = 1L;
	/** integer types*/
	private static final String[] INTEGERTYPES = {
		"bigint","int","smallint","tinyint","bit","decimal","numeric","money","smallmoney"
	};
	/** double types*/
	private static final String[] DOUBLETYPES = {
		"float","real"
	};
	/** string types */
	private static final String[] STRINGTYPES = {
		"char","varchar","text","nchar","nvarchar","ntext"
	};
	/** date types*/
	private static final String[] DATETYPES={
		"datetime","smalldatetime"
	};
	private static final String[] FILETYPES={
		"binary","varbinary","image"
	};
	
	public SqlServerTypeVO(String name, int range, int decimaldigits) {
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
	
	public boolean isFile() {
		return this.isContain(FILETYPES);
	}
	
}
