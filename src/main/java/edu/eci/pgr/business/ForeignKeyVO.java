package edu.eci.pgr.business;
//=======================================================================
//FILE ForeignKeyVO
//CREATE DATE: August 2008
//AUTHORS: Beatriz Rojas, Javier Villamil
//Contains the value objects of a foreignKey column of a table
//=======================================================================

//=======================================================================
//CLASS ForeignKey.java
//=======================================================================
public class ForeignKeyVO extends ColumnVO{
	private static final long serialVersionUID = 1L;
	//	-----------------------------------------------------------------------
// INSTANCE ATRIBUTES
//-----------------------------------------------------------------------
	/** The name from which the foreign key has been retrieved**/
	private TableVO originalTable;
	/** The name of the column that belongs to the table from where the key has been retrieved**/
	private String originalColumnName;
//	-----------------------------------------------------------------------
//	CONSTRUCTOR METHODS
//	-----------------------------------------------------------------------
	
	/**
	 * Creates a new instance of a ColumnVO
	 * @pre No instance has been created
	 * @pos A new instance is created
	 */
	public ForeignKeyVO() {
		super();
	}
	/**
	 * Creates a new instance of a ColumnVO
	 * @pre No instance has been created
	 * @pos A new instance is created
	 * @param originalTableName ,the name of the table from where the key is taken
	 * @param originalColumnName ,the name of column that belongs to the table from where the key is retrieved
	 */
	public ForeignKeyVO(TableVO originalTable, String originalColumnName) {
		super();
		this.originalTable = originalTable;
		this.originalColumnName = originalColumnName;
	}


	/**
	 * Creates a new instance of a ColumnVO from super class
	 * @pre No instance has been created
	 * @pos A new instance is created
	 * @param name
	 * @param type
	 * @param nullable
	 * @param primaryKey
	 * @param originaltable
	 * @param originalcolumn
	 */
	public ForeignKeyVO(String name, TypeVO type, boolean nullable,
			boolean primaryKey, TableVO originaltable , String originalcolumn) {
		super(name, type, nullable, primaryKey);
		this.originalColumnName=originalcolumn;
		this.originalTable=originaltable;
	}
	
	/**
	 * Creates a new instance of a ColumnVO from super class
	 * @pre No instance has been created
	 * @pos A new instance is created
	 * @param column
	 * @param originaltable
	 * @param originalname
	 */
	public ForeignKeyVO(ColumnVO column, TableVO originaltable, String originalname ){
		super(column.getName(), column.getType(),column.isNullable(), column.isPrimaryKey());
		this.originalColumnName=originalname;
		this.originalTable=originaltable;
	}
//-----------------------------------------------------------------------
// INSTANCE METHODS
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
// GETTER METHODS
//-----------------------------------------------------------------------
	/**
	 * Returns the table's value object from where this foreign key came from
	 * @pre An instance of ForeignKey has been created
	 * @return originalTableName
	 */
	public TableVO getOriginalTable() {
		return originalTable;
	}
	/**
	 * Returns the column's name at the table from where this foreign key came from
	 * @pre An instance of ForeignKey has been created
	 * @return originalColumnName
	 */
	public String getOriginalColumnName() {
		return originalColumnName;
	}

//-----------------------------------------------------------------------
// SETTER METHODS
//-----------------------------------------------------------------------
	/**
	 * Sets a new name for the table from where the foreign key was taken
	 * @param originalTableName , the new name of the original table
	 * @pre An instance of ForeignKey has been created
	 * @pos A new name for the original table has been set
	 */
	public void setOriginalTableName(TableVO originalTable) {
		this.originalTable = originalTable;
	}
	/**
	 * Sets a new column name for the column of the table from where the foreign key was taken
	 * @param originalColumnName ,the new name for the column of the original table
	 * @pre An instance of ForeignKey has been created
	 * @pos A new name for the column of the original table has been set
	 */
	public void setOriginalColumnName(String originalColumnName) {
		this.originalColumnName = originalColumnName;
	}
	
}
