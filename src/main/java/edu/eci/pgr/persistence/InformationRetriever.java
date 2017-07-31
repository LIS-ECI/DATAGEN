package edu.eci.pgr.persistence;
//=======================================================================
//FILE InformationRetreiver
//CREATE DATE: August 2008
//AUTHORS: Beatriz Rojas, Javier Villamil
//Retreives the metadata information of a database
//=======================================================================
//=======================================================================
//REQUERIED LIBRARIES
//=======================================================================

import java.util.List;

import edu.eci.pgr.business.ColumnVO;
import edu.eci.pgr.business.DatabaseVO;
import edu.eci.pgr.business.ForeignKeyVO;
import edu.eci.pgr.business.InfoDatabaseVO;
import edu.eci.pgr.business.TableVO;
import edu.eci.pgr.exceptions.ConnectionException;
import edu.eci.pgr.exceptions.RetrieverException;
//=======================================================================
//CLASS InformationRetreiver.java
//=======================================================================
public abstract class InformationRetriever {
//-----------------------------------------------------------------------
// INSTANCE ATTRIBUTES
//-----------------------------------------------------------------------

	protected DatabaseVO database; 
//-----------------------------------------------------------------------
// INSTANCE ABSTRACT METHODS
//-----------------------------------------------------------------------
	/**
	 * Returns the tables associated to a database
	 * @param conn ,connection to the database
	 * @pre the connection has been instantiated 
	 * @return the tables associated to the database
	 * @throws RetrieverException 
	 */
	public abstract List<TableVO> getTableVOs() throws RetrieverException;
	/**
	 * Returns the set of primary keys of the table
	 * @param table, name of the table from where the primary keys are going to be taken
	 * @pre the connection has been instantiated 
	 * @return the set of primary keys of the table requested
	 * @throws RetrieverException 
	 */
	public abstract List<ColumnVO> getPrimaryKeys( String table) throws RetrieverException;
	/**
	 * Returns the set of foreign keys of the table
	 * @param table ,name of the table form where the priamy keys are going to be taken
	 * @pre the connection has been instantiated
	 * @return the set of foreign keys of the table
	 * @throws RetrieverException 
	 */
	public abstract List<ForeignKeyVO> getForeignKeys(String table) throws RetrieverException;
	/**
	 * Returns the set of columns of a table
	 * @param table ,table from where the columns are going to be taken
	 * @pre the connection has been instantiated
	 * @return the set of columns of the table
	 * @throws RetrieverException 
	 */
	public abstract List<ColumnVO> getColumns(String table) throws RetrieverException;
	
//-----------------------------------------------------------------------
// GETTER AND SETTER METHODS
//-----------------------------------------------------------------------
	/**
	 * @return the database
	 */
	public DatabaseVO getDatabase() {
		return database;
	}
	/**
	 * @param database the database to set
	 */
	public void setDatabase(DatabaseVO database) {
		this.database = database;
	}
//-------------------------------------------------------------------------
// PROTECTED METHODS
//-------------------------------------------------------------------------
	/**
	 * Determines whether the column if a unique key of the table
	 * 
	 * @param name 
	 * 			name of the column
	 * @param table 
	 * 			name of the table
	 * @return 
	 * 			whether the columns is a unique key of the table
	 */
	protected abstract boolean isUniqueKey(String name, String table)throws RetrieverException;
	
	/**
	 * Determines if one column is auto increment
	 * 
	 * @param name
	 * 			name of the column
	 * @param table 
	 * 			name of the table
	 * @return true 
	 * 			if column is auto increment false otherwise
	 */
	protected abstract boolean isAutoincrement(String name, String table)throws RetrieverException;
	
	public abstract void setDataBaseMetadata(InfoDatabaseVO database) throws ConnectionException, RetrieverException;


}
