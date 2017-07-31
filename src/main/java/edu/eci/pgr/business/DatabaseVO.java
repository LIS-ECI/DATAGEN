package edu.eci.pgr.business;
//=======================================================================
//FILE DatabaseVO
//CREATE DATE: August 2008
//AUTHORS: Beatriz Rojas, Javier Villamil
//Contains the value objects of a database
//=======================================================================
//=======================================================================
//REQUERIED LIBRARIES
//=======================================================================

import java.io.Serializable;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
//=======================================================================
//CLASS DatabaseVO.java
//=======================================================================

public class DatabaseVO implements Serializable{
	private static final long serialVersionUID = 1L;
//-----------------------------------------------------------------------
// INSTANCE ATRIBUTES
//-----------------------------------------------------------------------
	/** Tables associated to the database the key is a table name**/
	private Map<String, TableVO> tables;
	/** Information containing the connection information */
	private InfoDatabaseVO info;
//-----------------------------------------------------------------------
//CONSTRUCTOR METHODS
//-----------------------------------------------------------------------
	/**
	 * Creates a new instance of a database
	 * @param url ,connection url to the database
	 * @param username ,username of the database
	 * @param password ,password of the database
	 * @param driver ,driver used to connect to the database
	 * @param engine ,database engine
	 * @pre No instance has been created
	 * @pos A new instance has been created
	 */
	public DatabaseVO(String url, String username, String password, String driver, String engine) {
		super();
		this.tables = new Hashtable<String,TableVO>();
		info = new InfoDatabaseVO(url,username,password,driver,engine);
	}
//-----------------------------------------------------------------------
// INSTANCE METHODS
//-----------------------------------------------------------------------
	
	public DatabaseVO(InfoDatabaseVO infoDatabaseVO) {
		info = infoDatabaseVO;
		tables = new HashMap<String, TableVO>();
	}

	//-----------------------------------------------------------------------
// GETTER METHODS
//-----------------------------------------------------------------------
	/**
	 * Returns the tables associated to the database
	 * @pre A new instance has been created
	 * @return tables associated to the database
	 */
	public Map<String,TableVO> getTables() {
		return tables;
	}
	/**
	 * Returns the table associated to the key
	 * @param key ,key from which the table is going to be retrieved
	 * @pre A new instance has been created
	 * @return the table associated to the key 
	 */
	public TableVO getTable(String key){
		return tables.get(key);
	}
	/**
	 * Returns the total count of tables associated to the database
	 * @pre A new instance has been created
	 * @return tables.size
	 */
	public int getTotalTables(){
		return tables.size();
	}
	
	public InfoDatabaseVO getInfo() {
		return info;
	}

	//-----------------------------------------------------------------------
// SETTER METHODS
//-----------------------------------------------------------------------
	/**
	 * Sets a new set of tables to the database
	 * @param tables ,new set of tables of the database
	 * @pre A new instance has been created
	 * @pos A new set of tables is set to the database
	 */
	public void setTables(Map<String,TableVO> tables) {
		this.tables = tables;
	}
	/**
	 * Adds a new table to the database
	 * @param table ,new table to be added
	 * @pre A new instance has been created
	 * @pos A new table has been added to the database
	 */
	public void addTable(TableVO table){
		tables.put(table.getName(),table);
	}

	public void setInfo(InfoDatabaseVO info) {
		this.info = info;
	}
	
}
