package edu.eci.pgr.business;

import java.io.Serializable;

import edu.eci.pgr.dataGenerators.DataGenerator;
/**
 * The <code> ColumnVO </code> represents a column into database. this
 * class contains all information about the columns into any database.
 * <p>
 * One columnVO contains information about one and only one column
 * in one <code>TableVo</code> in  one database for one instance, 
 * for example:
 * <p><blockquote><pre>
 *     name=column;
 *     type=Character     
 * </pre></blockquote><p>
 * But this information is not completely because for one column in one
 * <code>tableVO</code> is need know other variables:
 * <p>this column can be null?
 * <p>this column is <code>auto increment</code>?
 * <p>this column is <code>unique</code>  key?
 * <p>this column is <code>primary</code> key?
 * <p>this information is stored in <code> ColumnVO class </code> 
 * <p>and other information for do inserts in the column, for example: 
 * <code>generator type</code>, and <code>nullable percentage</code> with 
 * this information can do inserts to generate synthetic data for applications
 * based in load tests in <i>ECI DataBase DataGenerator</i>
 * <p> Two column are compared based in the method <code> compareTo </code> defined 
 * by <code> String </code>. If one <code>ColumnVO</code> is a <code>Foreign Key</code>
 * is defined as {@link ForeignKeyVO} an his type is defined as {@link TypeVO}.  
 * 
 * @author Felipe Villamil
 * @author Beatriz Rojas
 * @version
 * @see ForeignKeyVO
 * @see TableVO
 * @see TypeVO
 *
 */
//=======================================================================
//FILE ColumnVO
//CREATE DATE: August 2008
//AUTHORS: Beatriz Rojas, Javier Villamil
//Contains the value objects of a column of a table
//=======================================================================
//=======================================================================
//REQUERIED LIBRARIES
//=======================================================================


//=======================================================================
//CLASS ColumnVO.java
//=======================================================================

public class ColumnVO implements Comparable<ColumnVO>,Serializable{
	private static final long serialVersionUID = 1L;

//-----------------------------------------------------------------------
// INSTANCE ATRIBUTES
//-----------------------------------------------------------------------
	
	/** The name of the column. **/
	protected String name;
	
	/** The type of the column **/
	protected TypeVO type;
	
	/** Is the column null? **/
	protected boolean nullable;
	
	/** Is the column a primary key? **/
	protected boolean primaryKey;
	
	/** Is the column a unique key? **/
	protected boolean uniqueKey;
	
	/** Is the column auto incremented by the database?**/
	protected boolean autoIncrement;
	
	/** the column generator**/
	protected DataGenerator generator;
	
	/** percentage of null rows**/
	protected double nullablePercentage;
	
//-----------------------------------------------------------------------
//CONSTRUCTOR METHODS
//-----------------------------------------------------------------------


	/**
	 * this is a full constructor, creates a new <code>ColumnVO</code> object
	 * with the specified information, the name of column represents a name with column
	 * is called in the data base the type of column represents a type of data, nullable
	 * attribute is if column can have null values or not, the primary key attribute
	 * represents if this column is a primary key column, is the same with unique value
	 * and auto increment attribute.
	 * the gentype attribute specifies the type of data generator associated to column, this value
	 * has been the name of generator is called in the <i>dataGenerator properties file<i> this
	 * attribute is the string that represents the instance of the {@link DataGenerator} 
	 * @param name 
	 * 			name of the column
	 * @param type 
	 * 			type of the column
	 * @param nullable 
	 * 			is the column nullable?
	 * @param primaryKey 
	 * 			is the column a primary key?
	 * @param uniqueKey 
	 * 			is the column a unique key?
	 * @param autoIncrement
	 * 			is the column auto-incremented?
	 * @param gentype
	 * 			what type of generator is required for this column
	 */
	public ColumnVO(String name, TypeVO type, boolean nullable,
			boolean primaryKey, boolean uniqueKey,boolean autoIncrement,
			DataGenerator gentype) {
		super();
		this.name = name;
		this.type = type;
		this.nullable = nullable;
		this.primaryKey = primaryKey;
		this.uniqueKey = uniqueKey;
		this.setGenerator(gentype);
		this.nullablePercentage=0;
		this.autoIncrement = autoIncrement;
	}

	/**
	 * Creates a new instance of a ColumnVO with <code>nullable</code> 
	 * percentage in zero and instance a new generator Attributes.
	 * @pre No instance has been created
	 * @pos A new instance is created
	 */
	public ColumnVO() {
		super();
		nullablePercentage=0;
	}
	
	/**
	 * this method Creates a new instance of a ColumnVO
	 * @param name
	 * 			name of the column
	 * @param type 
	 * 			data type of the column
	 * @param nullable
	 * 			if the column is null
	 * @param primaryKey 
	 * 			if the column is a primary key
	 * @pre No instance has been created
	 * @pos A new instance is created
	 */
	public ColumnVO(String name, TypeVO type, boolean nullable,
			boolean primaryKey) {
		super();
		this.name = name;
		this.type = type;
		this.nullable = nullable;
		this.primaryKey = primaryKey;
		this.nullablePercentage=0;
	}
//-----------------------------------------------------------------------
// INSTANCE METHODS
//-----------------------------------------------------------------------
	
//-----------------------------------------------------------------------
// GETTER METHODS
//-----------------------------------------------------------------------
	/**
	 * this method Returns the name of the column
	 * @pre An instance of column has been created
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Returns the data type of the column
	 * @pre An instance of column has been created
	 * @return type
	 */
	public TypeVO getType() {
		return type;
	}
	/**
	 * Returns if the column may have null values
	 * @pre An instance of column has been created
	 * @return nullable
	 */
	public boolean isNullable() {
		return nullable;
	}
	/**
	 * Returns if the column is a primary key
	 * @pre An instance of column has been created
	 * @return primaryKey
	 */
	public boolean isPrimaryKey() {
		return primaryKey;
	}
	
	/**
	 * Returns the {@link DataGenerator} instance associated to 
	 * this {@code ColumnVO}, the generator contains the attribute values
	 * configured by the user 
	 * @return the generator type
	 */
	public DataGenerator getGenerator() {
		return generator;
	}

	/**
	 * Returns if the column is a unique key
	 * @pre An instance of column has been created
	 * @return uniqueKey
	 */
	public boolean isUniqueKey() {
		return uniqueKey;
	}

	/**
	 * Returns if the column is auto-incremented
	 * @pre An instance of column has been created
	 * @return autoIncrement
	 */
	public boolean isAutoIncrement() {
		return autoIncrement;
	}
	
	/**
	 * Returns the percent off null fields
	 * @pre An instance of column has been created
	 * @return percent of null fields
	 */
	public double getNullablePercentage(){
		return this.nullablePercentage;
	}

//-----------------------------------------------------------------------
// SETTER METHODS
//-----------------------------------------------------------------------
	/**
	 * Sets a new name for the column
	 * @param name ,the new name for the column
	 * @pre An instance of the column has been created
	 * @pos the new name of the column is set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @param type ,the new type for the data of the column
	 * @pre An instance of the column has been created
	 * @pos the new name of the column is set
	 */
	public void setType(TypeVO type) {
		this.type = type;
	}
	/**
	 * Defines if the column may have null values
	 * @param nullable ,if the values of the column may be null
	 * @pre An instance of the column has been created
	 * @pos the column has a new nullable value
	 */
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
	/**
	 * Defines if the column is a primary key
	 * @param primaryKey ,if the column is a primary key
	 * @pre An instance of the column has been created
	 * @pos the column is set to be a primary key, or not
	 */
	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}


	/**
	 * Defines if the column is a unique key
	 * @param uniqueKey ,if the column is a unique key
	 * @pre An instance of the column has been created
	 * @pos the column is set to be a unique key, or not
	 */
	public void setUniqueKey(boolean uniqueKey) {
		this.uniqueKey = uniqueKey;
	}
	
	/**
	 * Defines if the column is auto-incremented
	 * @param uniqueKey ,if the column is auto-incremented
	 * @pre An instance of the column has been created
	 * @pos the column is set to be auto-incremented, or not
	 */
	public void setAutoIncrement(boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}

	/**
	 * Defines the percent of null fields, only changes if column is nullable
	 * @param percent ,the percent of null fields
	 * @pre An instance of the column has been created
	 * @pos the percent of null fields column is set
	 */
	public void setNullablePercentage (double percent){
		if (this.nullable) this.nullablePercentage= percent;
	}
	/**
	 * Defines a new data generator
	 * @param types the types to set
	 */
	public void setGenerator(DataGenerator gentype ) {
		this.generator = gentype;
		this.generator.setType(this.getType());
	}
//-----------------------------------------------------------------------
// OVERRIDE METHODS
//-----------------------------------------------------------------------
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ColumnVO o) {
		return this.getName().compareTo(o.getName());
	}
	
	
}
