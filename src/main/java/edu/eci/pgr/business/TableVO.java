package edu.eci.pgr.business;
//=======================================================================
//FILE TableVO
//CREATE DATE: August 2008
//AUTHORS: Beatriz Rojas, Javier Villamil
//Contains the value objects of a table of a database
//=======================================================================
//=======================================================================
//REQUERIED LIBRARIES
//=======================================================================

import java.io.Serializable;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class TableVO implements Comparable<TableVO>,Serializable{
	private static final long serialVersionUID = 1L;
//-----------------------------------------------------------------------
// INSTANCE ATRIBUTES
//-----------------------------------------------------------------------
	/** a single column is a column that not have references and isn't primary
	/** single Columns associated to the table the key is a column name**/
	private Map<String,ColumnVO> singlecolumns;
	/** foreign keys associated to the table **/
	private Map<String,ForeignKeyVO> foreigns;
	/** primary keys associated to the table  **/
	private Map<String,ColumnVO> primaries;
	/** primary foreign keys associated to the table **/
	private Map<String,ForeignKeyVO> primaryForeigns;
	/** Name of the table **/
	private String name;
	/**times to insert in te table**/
	private long insertTimes;
	
	
	//	-----------------------------------------------------------------------
//	CONSTRUCTOR METHODS
//	-----------------------------------------------------------------------
	/**
	 * Creates a new instance of a ColumnVO
	 * @param name ,name of the instantiated table
	 * @pre No instance has been created
	 * @pos A new instance is created
	 */
	public TableVO(String name) {
		super();
		this.name = name;
		this.singlecolumns = new Hashtable<String,ColumnVO>();
		this.primaries = new Hashtable<String,ColumnVO>();
		this.foreigns = new Hashtable<String,ForeignKeyVO>();
		this.primaryForeigns = new Hashtable<String,ForeignKeyVO>();
	}
	/**
	 * Creates a new instance of a ColumnVO
	 * @pre No instance has been created
	 * @pos A new instance is created
	 */
	public TableVO() {
		super();
		singlecolumns = new HashMap<String, ColumnVO>();;
		foreigns = new HashMap<String, ForeignKeyVO>();
		primaries = new HashMap<String, ColumnVO>();
		primaryForeigns = new HashMap<String, ForeignKeyVO>();
		
	}
//-----------------------------------------------------------------------
// INSTANCE METHODS
//-----------------------------------------------------------------------	
//-----------------------------------------------------------------------
// GETTER METHODS
//-----------------------------------------------------------------------
	/**
	 * @return the insertTimes
	 */
	public long getInsertTimes() {
		return insertTimes;
	}
	
	/**
	 * Returns the single columns associated to the table
	 * @pre An instance of a table has been created
	 * @return columns
	 */
	public Map<String,ColumnVO>  getSingleColumns() {
		return singlecolumns;
	}
	/**
	 * Returns the single column of the table associated to the key
	 * @param key ,key from which the column is going to be retrieved
	 * @pre An instance of a table has been created
	 * @return column associated 
	 */
	public ColumnVO getSingleColumn(String key){
		return singlecolumns.get(key);
	}
	
	/**
	 * Returns the foreign columns associated to the table
	 * @pre An instance of a table has been created
	 * @return columns
	 */
	public Map<String,ForeignKeyVO>  getForeignColumns() {
		return foreigns;
	}
	/**
	 * Returns the foreign column of the table associated to the key
	 * @param key ,key from which the column is going to be retrieved
	 * @pre An instance of a table has been created
	 * @return column associated 
	 */
	public ForeignKeyVO getForeignColumn(String key){
		return foreigns.get(key);
	}
	
	/**
	 * Returns the primary columns associated to the table
	 * @pre An instance of a table has been created
	 * @return columns
	 */
	public Map<String,ColumnVO>  getPrimaryColumns() {
		return primaries;
	}
	/**
	 * Returns the primary column of the table associated to the key
	 * @param key ,key from which the column is going to be retrieved
	 * @pre An instance of a table has been created
	 * @return column associated 
	 */
	public ColumnVO getPrimaryColumn(String key){
		return primaries.get(key);
	}
	
	/**
	 * Returns the primary foreign columns associated to the table
	 * @pre An instance of a table has been created
	 * @return columns
	 */
	public Map<String,ForeignKeyVO>  getPkFkColumns() {
		return primaryForeigns;
	}
	/**
	 * Returns the pkfk column of the table associated to the key
	 * @param key ,key from which the column is going to be retrieved
	 * @pre An instance of a table has been created
	 * @return column associated 
	 */
	public ForeignKeyVO getPkFkColumn(String key){
		return primaryForeigns.get(key);
	}
	/**
	 * Returns the total count of columns of the table
	 * @pre An instance of a table has been created
	 * @return columns.size
	 */
	public int getTotalColumns(){
		return singlecolumns.size()+this.primaries.size()+this.foreigns.size()+this.primaryForeigns.size();
	}
	
	public ColumnVO getColumn(String key){
		if(this.primaries.get(key)!=null)
			return this.primaries.get(key);
		else if (this.primaryForeigns.get(key)!=null)
			return this.primaryForeigns.get(key);
		else if (this.foreigns.get(key)!=null)
			return this.foreigns.get(key);
		else if (this.singlecolumns.get(key)!=null)
			return this.singlecolumns.get(key);
		return null;
		
	}
	/**
	 * Returns all column of the table
	 * @pre An instance of a table has been created
	 * @return a map with the columns 
	 */
	public Map<String,ColumnVO> getColumns(){
		Map<String,ColumnVO> ret= new Hashtable<String, ColumnVO>();
		ret.putAll(this.foreigns);
		ret.putAll(this.primaries);
		ret.putAll(this.primaryForeigns);
		ret.putAll(this.singlecolumns);
		return ret;
	}
	
	/**
	 * Returns the name of the table
	 * @pre An instance of a table has been created
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	

//-----------------------------------------------------------------------
// SETTER METHODS
//-----------------------------------------------------------------------
	
	/**
	 * @param insertTimes the insertTimes to set
	 */
	public void setInsertTimes(long insertTimes) {
		this.insertTimes = insertTimes;
	}
	
	/**
	 * Sets a new Map of single columns of the table
	 * @pre An instance of a table has been created
	 * @pos The table has a new set of columns
	 * @param columns ,the new columns to be set to the table
	 */
	public void setSingleColumns(Map<String,ColumnVO> columns) {
		this.singlecolumns = columns;
	}
	/**
	 * Adds a new single column to the table
	 * @pre An instance of a table has been created
	 * @pos A new column has been added to the table
	 * @param column ,column to be added to the table
	 */
	public void addSingleColumn(ColumnVO column){
		singlecolumns.put(column.getName(),column);
	}
	/**
	 * Sets a new Map of foreign columns of the table
	 * @pre An instance of a table has been created
	 * @pos The table has a new set of columns
	 * @param columns ,the new columns to be set to the table
	 */
	public void setForeignColumns(Map<String,ForeignKeyVO> columns) {
		this.foreigns = columns;
	}
	/**
	 * Adds a new foreign column to the table
	 * @pre An instance of a table has been created
	 * @pos A new column has been added to the table
	 * @param column ,column to be added to the table
	 */
	public void addForeignColumn(ForeignKeyVO column){
		foreigns.put(column.getName(),column);
	}
	/**
	 * Sets a new Map of primary columns of the table
	 * @pre An instance of a table has been created
	 * @pos The table has a new set of columns
	 * @param columns ,the new columns to be set to the table
	 */
	public void setPrimaryColumns(Map<String,ColumnVO> columns) {
		this.primaries = columns;
	}
	/**
	 * Adds a new primary column to the table
	 * @pre An instance of a table has been created
	 * @pos A new column has been added to the table
	 * @param column ,column to be added to the table
	 */
	public void addPrimaryColumn(ColumnVO column){
		primaries.put(column.getName(),column);
	}
	/**
	 * Sets a new Map of pkfk columns of the table
	 * @pre An instance of a table has been created
	 * @pos The table has a new set of columns
	 * @param columns ,the new columns to be set to the table
	 */
	public void setPkFkColumns(Map<String,ForeignKeyVO> columns) {
		this.primaryForeigns = columns;
	}
	/**
	 * Adds a new PkFk column to the table
	 * @pre An instance of a table has been created
	 * @pos A new column has been added to the table
	 * @param column ,column to be added to the table
	 */
	public void addPkFkColumn(ForeignKeyVO column){
		primaryForeigns.put(column.getName(),column);
	}
	/**
	 * Sets a new name to the table
	 * @pre An instance of a table has been created
	 * @pos A new name has been set to the table
	 * @param name ,the new name of the table
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Adds a new column to the table
	 * @pre An instance of a table has been created
	 * @pos A new column has been added to the table
	 * @param column ,column to be added to the table
	 */
	public void addColumn(ColumnVO column){
		if(column instanceof ForeignKeyVO){
			if(column.isPrimaryKey()){
				column.setUniqueKey(true);
				column.setPrimaryKey(true);
				this.primaryForeigns.put(column.getName(), (ForeignKeyVO) column);
				this.primaries.remove(column.getName());
			}
			else
				this.foreigns.put(column.getName(), (ForeignKeyVO) column);
			this.singlecolumns.remove(column.getName());
		}
		else{
			this.singlecolumns.put(column.getName(), column);
		}
	}
	
	/**
	 * add a primary column in a table
	 * @param key; the key to the column
	 */
	public void addPrimaryColumn(String key) {
		ColumnVO col=this.getSingleColumn(key);
		col.setPrimaryKey(true);
		col.setUniqueKey(true);
		this.primaries.put(col.getName(), col);
		this.singlecolumns.remove(col.getName());
	}
//-----------------------------------------------------------------------
// OVERRIDE METHODS
//-----------------------------------------------------------------------
	@Override
	public int compareTo(TableVO o) {
		return this.name.compareTo(o.getName());
	}
	
}
