package edu.eci.pgr.persistence.concrete;
//=======================================================================
//FILE OracleMetaDataInfo
//CREATE DATE: August 2008
//AUTHORS: Beatriz Rojas, Javier Villamil
//Retreives the metadata information of a mysql database
//=======================================================================
//=======================================================================
//REQUERIED LIBRARIES
//=======================================================================


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.hajdbc.QualifiedName;
import net.sf.hajdbc.UniqueConstraint;
import net.sf.hajdbc.cache.DatabaseMetaDataSupport;
import net.sf.hajdbc.dialect.OracleDialect;
import edu.eci.pgr.business.ColumnVO;
import edu.eci.pgr.business.DatabaseVO;
import edu.eci.pgr.business.ForeignKeyVO;
import edu.eci.pgr.business.InfoDatabaseVO;
import edu.eci.pgr.business.TableVO;
import edu.eci.pgr.business.TypeVO;
import edu.eci.pgr.business.types.OracleTypeVO;
import edu.eci.pgr.exceptions.ConnectionException;
import edu.eci.pgr.exceptions.MySqlRetrieverException;
import edu.eci.pgr.exceptions.OracleRetrieverException;
import edu.eci.pgr.exceptions.RetrieverException;
import edu.eci.pgr.i18n.MessageBundleManager;
import edu.eci.pgr.persistence.ConnectionFactory;
import edu.eci.pgr.persistence.InformationRetriever;

//=======================================================================
//CLASS MySqlMetaDataInfo.java
//=======================================================================
public class OracleInformationRetriever extends InformationRetriever {
private static final String SQL_ERROR = MessageBundleManager.getString("OracleInformationRetriever.0"); //$NON-NLS-1$

private static final String PRIMARY_MESSAGE = MessageBundleManager.getString("OracleInformationRetriever.1"); //$NON-NLS-1$

private static final String FOREIGN_MESSAGE = MessageBundleManager.getString("OracleInformationRetriever.2"); //$NON-NLS-1$

private static final String COLUMN_MESSAGE = MessageBundleManager.getString("OracleInformationRetriever.3"); //$NON-NLS-1$

private static final String RETRIEVER_MESSAGE = MessageBundleManager.getString("OracleInformationRetriever.4"); //$NON-NLS-1$
//-----------------------------------------------------------------------
// INSTANCE ATTRIBUTES
//-----------------------------------------------------------------------

	private static final String ORACLE_ERROR = MessageBundleManager.getString("OracleInformationRetriever.5"); //$NON-NLS-1$
	private DatabaseMetaData dbMetaData;
//-----------------------------------------------------------------------
// CONSTRUCTOR METHODS
//-----------------------------------------------------------------------
	
	/**
	 * Empty constructor 
	 * @throws MySqlRetrieverException 
	 * @throws ConnectionException 
	 */
	public OracleInformationRetriever() throws  MySqlRetrieverException, ConnectionException{
	}
	
	/**
	 * initialize a database and establishes connection
	 * @param database
	 * @throws MySqlRetrieverException 
	 */
	public OracleInformationRetriever(DatabaseVO database) throws ConnectionException, OracleRetrieverException{
		this.database=database;
		try {
			dbMetaData=(DatabaseMetaData) ConnectionFactory.getInstance().getConnection(database.getInfo()).getMetaData();
		} catch (SQLException e) {
			throw new OracleRetrieverException(ORACLE_ERROR,e);
		}
	}
	/**
	 * Initialize the meta data
	 * @param conn, the connection to database
	 * @throws OracleRetrieverException 
	 */
	public OracleInformationRetriever(Connection conn) throws OracleRetrieverException {
		try {
			dbMetaData=(DatabaseMetaData) conn.getMetaData();
		} catch (SQLException e) {
			throw new OracleRetrieverException(ORACLE_ERROR,e);
		}
	}
//-----------------------------------------------------------------------
// INSTANCE METHODS
//-----------------------------------------------------------------------
	public void setDataBaseMetadata(InfoDatabaseVO database) throws ConnectionException, OracleRetrieverException{
		this.database = new DatabaseVO(database);
		try {
			dbMetaData=(DatabaseMetaData) ConnectionFactory.getInstance().getConnection(database).getMetaData();
		} catch (SQLException e) {
			throw new OracleRetrieverException(RETRIEVER_MESSAGE,e);
		}
	}
	/**
	 * Returns the set of columns of a table
	 * @param table ,table from where the columns are going to be taken
	 * @pre the connection has been instantiated
	 * @return the set of columns of the table
	 * @throws OracleRetrieverException 
	 */
	public List<ColumnVO> getColumns(String table) throws OracleRetrieverException {
		List<ColumnVO> columns=new ArrayList<ColumnVO>();
		try {
			//dbMetaData= (DatabaseMetaData) conn.getMetaData();
			ResultSet rs=dbMetaData.getColumns(null, dbMetaData.getUserName(), table, null);
			ResultSetMetaData rsMeta = (ResultSetMetaData) rs.getMetaData();
			ColumnVO column=null;
			int i = 1;
			if(rs != null){
				while(rs.next()){
					// get the columnVO the primary key value is false by default
					TypeVO typ= new OracleTypeVO(rs.getString("TYPE_NAME"), //$NON-NLS-1$
							rs.getInt("COLUMN_SIZE"),rs.getInt("DECIMAL_DIGITS"));   //$NON-NLS-1$ //$NON-NLS-2$
					column = new ColumnVO(rs.getString("COLUMN_NAME"),typ, //$NON-NLS-1$
							rs.getInt("NULLABLE") != DatabaseMetaData.columnNoNulls, false); //$NON-NLS-1$
					column.setAutoIncrement(rsMeta.isAutoIncrement(i));
					column.setUniqueKey(isUniqueKey(rs.getString("COLUMN_NAME"), rs.getString("TABLE_NAME"))); //$NON-NLS-1$ //$NON-NLS-2$
					columns.add(column);
					//add the column to table into database
					database.getTable(table).addColumn(column);
					i++;
				}
			}
		} catch (SQLException e) {
			throw new OracleRetrieverException(COLUMN_MESSAGE+table+" ",e); //$NON-NLS-1$
		}
		return columns;
	}
	/**
	 * Returns the set of foreign keys of the table
	 * @param table ,name of the table form where the priamy keys are going to be taken
	 * @pre the connection has been instantiated
	 * @return the set of foreign keys of the table
	 * @throws OracleRetrieverException 
	 */
	public List<ForeignKeyVO> getForeignKeys(String table) throws OracleRetrieverException {
		List<ForeignKeyVO> fks = new ArrayList<ForeignKeyVO>();
		try {
			ResultSet rs=dbMetaData.getImportedKeys(null, dbMetaData.getUserName(), table);
			ForeignKeyVO foreign=null;
			String columnname=""; //$NON-NLS-1$
			if(rs != null){
				while(rs.next()){
					columnname=rs.getString("FKCOLUMN_NAME"); //$NON-NLS-1$
					foreign = new ForeignKeyVO(database.getTable(table).getColumn(columnname),
							database.getTable(rs.getString("PKTABLE_NAME")),rs.getString("PKCOLUMN_NAME")); //$NON-NLS-1$ //$NON-NLS-2$
					fks.add(foreign);
					//replace the exists column by foreign column
					database.getTable(table).addColumn(foreign);
				}
			}
		} catch (SQLException e) {
			throw new OracleRetrieverException(FOREIGN_MESSAGE+table+" ",e); //$NON-NLS-1$
		}
		
		return fks;
	}
	/**
	 * Returns the set of primary keys of the table
	 * @param table, name of the table from where the primary keys are going to be taken
	 * @pre the connection has been instantiated 
	 * @return the set of primary keys of the table requested
	 * @throws OracleRetrieverException 
	 */
	public List<ColumnVO> getPrimaryKeys(String table) throws OracleRetrieverException {
		//primary keys to database
		List<ColumnVO> pks = new ArrayList<ColumnVO>();
		ColumnVO columnvo;
		ResultSet rs;
		try {
			//get the tables
			rs = dbMetaData.getPrimaryKeys(null, dbMetaData.getUserName(), table);
			while(rs.next()){
				//build the tableVOs
				database.getTable(table).addPrimaryColumn(rs.getString("COLUMN_NAME")); //$NON-NLS-1$
				columnvo=database.getTable(table).getColumn(rs.getString("COLUMN_NAME")); //$NON-NLS-1$
				pks.add(columnvo);
			}
		} catch (SQLException e) {
			throw new OracleRetrieverException(PRIMARY_MESSAGE+table+" ",e); //$NON-NLS-1$
		}
		return pks;
	}

	/**
	 * Returns the tables associated to a database
	 * @pre the connection has been instantiated 
	 * @return the tables associated to the database
	 * @throws OracleRetrieverException 
	 */
	public List<TableVO> getTableVOs() throws OracleRetrieverException {
		List<TableVO> retorno = new ArrayList<TableVO>();
		TableVO tablevo;
		String tipos[] = {"TABLE"}; //$NON-NLS-1$
		ResultSet rs;
		String table;
		try {
			//get the tables
			rs = dbMetaData.getTables(null,dbMetaData.getUserName(), null, tipos);
			while(rs.next()){
				//build the tableVOs
				table=rs.getString("TABLE_NAME"); //$NON-NLS-1$
				tablevo=new TableVO(table);
				retorno.add(tablevo);
				//add database
				database.addTable(tablevo);
			}
			return retorno;
		} catch (SQLException e) {
			throw new OracleRetrieverException(SQL_ERROR,e);
		}
	}
	
	protected boolean isUniqueKey(String name, String table) throws OracleRetrieverException{
		try {
			boolean response = false;
			DatabaseMetaDataSupport metaSup = new DatabaseMetaDataSupport(dbMetaData,new OracleDialect());
			Iterator<UniqueConstraint> uniqueConstraints = metaSup.getUniqueConstraints(dbMetaData,new QualifiedName(dbMetaData.getUserName(),table), null).iterator();
			while(uniqueConstraints.hasNext() && !response){
				UniqueConstraint uc = uniqueConstraints.next();
				List<String> columns = uc.getColumnList();
				for(int i=0;i<columns.size() && !response;i++)
					if(columns.get(i).equals(name))
						response = true;
			}
			return response;
		} catch (SQLException e) {
			throw new OracleRetrieverException(SQL_ERROR,e);
		}
	}

	@Override
	protected boolean isAutoincrement(String name, String table)
			throws RetrieverException {
		return false;
	}

}
