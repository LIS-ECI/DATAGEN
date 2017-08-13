package edu.eci.pgr.persistence.concrete;
//=======================================================================
//FILE SqlServerMetaDataInfo
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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.microsoft.sqlserver.jdbc.SQLServerDatabaseMetaData;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import edu.eci.pgr.business.ColumnVO;
import edu.eci.pgr.business.DatabaseVO;
import edu.eci.pgr.business.ForeignKeyVO;
import edu.eci.pgr.business.InfoDatabaseVO;
import edu.eci.pgr.business.TableVO;
import edu.eci.pgr.business.TypeVO;
import edu.eci.pgr.business.types.SqlServerTypeVO;
import edu.eci.pgr.exceptions.ConnectionException;
import edu.eci.pgr.exceptions.MySqlRetrieverException;
import edu.eci.pgr.exceptions.RetrieverException;
import edu.eci.pgr.exceptions.SqlServerRetrieverException;
import edu.eci.pgr.i18n.MessageBundleManager;
import edu.eci.pgr.persistence.ConnectionFactory;
import edu.eci.pgr.persistence.InformationRetriever;
import net.sf.hajdbc.dialect.Dialect;

//=======================================================================
//CLASS MySqlMetaDataInfo.java
//=======================================================================
public class SqlServerInformationRetriever extends InformationRetriever {

    private static final String TABLE_ERROR = MessageBundleManager.getString("SqlServerInformationRetriever.0"); //$NON-NLS-1$
    private static final String PRIMARY_ERROR = MessageBundleManager.getString("SqlServerInformationRetriever.1"); //$NON-NLS-1$
    private static final String FOREIGN_ERROR = MessageBundleManager.getString("SqlServerInformationRetriever.2"); //$NON-NLS-1$
    private static final String COLUMN_ERROR = MessageBundleManager.getString("SqlServerInformationRetriever.3"); //$NON-NLS-1$
    private static final String SQL_ERROR = MessageBundleManager.getString("SqlServerInformationRetriever.4"); //$NON-NLS-1$
    private static final String SQLSERVER_ERROR = MessageBundleManager.getString("SqlServerInformationRetriever.5"); //$NON-NLS-1$
    private static final String SYSDIAGRAMS = "sysdiagrams"; //$NON-NLS-1$
    private static final String UNIQUE = "UNIQUE"; //$NON-NLS-1$
    private static final String PRIMARY_KEY = "PRIMARY KEY"; //$NON-NLS-1$
//-----------------------------------------------------------------------
// INSTANCE ATTRIBUTES
//-----------------------------------------------------------------------

//-----------------------------------------------------------------------
// CONSTRUCTOR METHODS
//-----------------------------------------------------------------------
    /**
     * Empty constructor
     *
     * @throws MySqlRetrieverException
     * @throws ConnectionException
     */
    public SqlServerInformationRetriever() throws MySqlRetrieverException, ConnectionException {

    }

    /**
     * initialize a database and establishes connection
     *
     * @param database
     * @throws SqlServerRetrieverException
     */
    public SqlServerInformationRetriever(DatabaseVO database) throws ConnectionException, SqlServerRetrieverException {
        this.database = database;
        try {
            dbMetaData = (SQLServerDatabaseMetaData) ConnectionFactory.getInstance().getConnection(database.getInfo()).getMetaData();
        } catch (SQLException e) {
            throw new SqlServerRetrieverException(SQLSERVER_ERROR, e);
        }
    }

    /**
     * Initialize the meta data
     *
     * @param conn, the connection to database
     * @throws SqlServerRetrieverException
     */
    public SqlServerInformationRetriever(Connection conn) throws SqlServerRetrieverException {
        try {
            dbMetaData = (SQLServerDatabaseMetaData) conn.getMetaData();
        } catch (SQLException e) {
            throw new SqlServerRetrieverException(SQLSERVER_ERROR, e);
        }
    }
//-----------------------------------------------------------------------
// INSTANCE METHODS
//-----------------------------------------------------------------------

    public void setDataBaseMetadata(InfoDatabaseVO database) throws ConnectionException, SqlServerRetrieverException {
        this.database = new DatabaseVO(database);
        try {
            dbMetaData = (SQLServerDatabaseMetaData) ConnectionFactory.getInstance().getConnection(database).getMetaData();
        } catch (SQLException e) {
            throw new SqlServerRetrieverException(SQL_ERROR, e);
        }
    }

    /**
     * Returns the set of columns of a table
     *
     * @param table ,table from where the columns are going to be taken
     * @pre the connection has been instantiated
     * @return the set of columns of the table
     * @throws SqlServerRetrieverException
     */
    public List<ColumnVO> getColumns(String table) throws RetrieverException {
        List<ColumnVO> columns = new ArrayList<ColumnVO>();
        try {
            //dbMetaData= (DatabaseMetaData) conn.getMetaData();
            ResultSet rs = dbMetaData.getColumns(null, null, table, null);
            ColumnVO column = null;
            int i = 1;
            if (rs != null) {
                while (rs.next()) {
                    // get the columnVO the primary key value is false by default
                    TypeVO typ = new SqlServerTypeVO(rs.getString("TYPE_NAME"), //$NON-NLS-1$
                            rs.getInt("COLUMN_SIZE"), rs.getInt("DECIMAL_DIGITS"));   //$NON-NLS-1$ //$NON-NLS-2$
                    column = new ColumnVO(rs.getString("COLUMN_NAME"), typ, //$NON-NLS-1$
                            rs.getInt("NULLABLE") != DatabaseMetaData.columnNoNulls, false); //$NON-NLS-1$
                    column.setAutoIncrement(isAutoincrement(column.getName(), table));
                    column.setUniqueKey(isUniqueKey(rs.getString("COLUMN_NAME"), rs.getString("TABLE_NAME"))); //$NON-NLS-1$ //$NON-NLS-2$
                    columns.add(column);
                    //add the column to table into database
                    database.getTable(table).addColumn(column);
                    i++;
                }
            }
        } catch (SQLException e) {
            throw new SqlServerRetrieverException(COLUMN_ERROR + table + " ", e); //$NON-NLS-1$
        }
        return columns;
    }

    /**
     * Returns the set of foreign keys of the table
     *
     * @param table ,name of the table form where the primary keys are going to
     * be taken
     * @pre the connection has been instantiated
     * @return the set of foreign keys of the table
     * @throws SqlServerRetrieverException
     */
    public List<ForeignKeyVO> getForeignKeys(String table) throws SqlServerRetrieverException {
        List<ForeignKeyVO> fks = new ArrayList<ForeignKeyVO>();
        try {
            ResultSet rs = dbMetaData.getImportedKeys(null, null, table);
            ForeignKeyVO foreign = null;
            String columnname = ""; //$NON-NLS-1$
            if (rs != null) {
                while (rs.next()) {
                    columnname = rs.getString("FKCOLUMN_NAME"); //$NON-NLS-1$
                    foreign = new ForeignKeyVO(database.getTable(table).getColumn(columnname),
                            database.getTable(rs.getString("PKTABLE_NAME")), rs.getString("PKCOLUMN_NAME")); //$NON-NLS-1$ //$NON-NLS-2$
                    fks.add(foreign);
                    //replace the exists column by foreign column
                    database.getTable(table).addColumn(foreign);
                }
            }
        } catch (SQLException e) {
            throw new SqlServerRetrieverException(FOREIGN_ERROR + table + " ", e); //$NON-NLS-1$
        }

        return fks;
    }

    /**
     * Returns the set of primary keys of the table
     *
     * @param table, name of the table from where the primary keys are going to
     * be taken
     * @pre the connection has been instantiated
     * @return the set of primary keys of the table requested
     * @throws SqlServerRetrieverException
     */
    public List<ColumnVO> getPrimaryKeys(String table) throws SqlServerRetrieverException {
        //primary keys to database
        List<ColumnVO> pks = new ArrayList<ColumnVO>();
        ColumnVO columnvo;
        ResultSet rs;
        try {
            //get the tables
            rs = dbMetaData.getPrimaryKeys(null, null, table);
            while (rs.next()) {
                //build the tableVOs
                database.getTable(table).addPrimaryColumn(rs.getString("COLUMN_NAME")); //$NON-NLS-1$
                columnvo = database.getTable(table).getColumn(rs.getString("COLUMN_NAME")); //$NON-NLS-1$
                pks.add(columnvo);
            }
        } catch (SQLException e) {
            throw new SqlServerRetrieverException(PRIMARY_ERROR + table + " ", e); //$NON-NLS-1$
        }
        return pks;
    }

    /**
     * Returns the tables associated to a database
     *
     * @pre the connection has been instantiated
     * @return the tables associated to the database
     * @throws SqlServerRetrieverException
     */
    public List<TableVO> getTableVOs() throws SqlServerRetrieverException {
        List<TableVO> retorno = new ArrayList<TableVO>();
        TableVO tablevo;
        String tipos[] = {"TABLE"}; //$NON-NLS-1$
        ResultSet rs;
        String table;
        try {
            //get the tables
            rs = dbMetaData.getTables(null, null, null, tipos);
            while (rs.next()) {
                //build the tableVOs
                table = rs.getString("TABLE_NAME"); //$NON-NLS-1$
                if (!table.equals(SYSDIAGRAMS)) {
                    tablevo = new TableVO(table);
                    retorno.add(tablevo);
                    //add database
                    database.addTable(tablevo);
                }
            }
            return retorno;
        } catch (SQLException e) {
            throw new SqlServerRetrieverException(TABLE_ERROR, e);
        }
    }

    protected boolean isUniqueKey(String name, String table) throws RetrieverException {
        boolean response = false;
        try {
            Statement statement = dbMetaData.getConnection().createStatement();
            String query = "SELECT CONSTRAINT_TYPE FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE TABLE_NAME = '" + table + "'"
                    + //$NON-NLS-1$ //$NON-NLS-2$
                    "AND CONSTRAINT_NAME IN (SELECT CONSTRAINT_NAME FROM INFORMATION_SCHEMA.CONSTRAINT_COLUMN_USAGE WHERE COLUMN_NAME='" + name + "' and TABLE_NAME = '" + table + "')"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet != null) {
                if (resultSet.next()) {
                    String value = resultSet.getString(1);
                    if (value.equals(UNIQUE) || value.equals(PRIMARY_KEY)) {
                        response = true;
                    }
                }
            }
            resultSet.close();
            statement.close();
        } catch (SQLServerException e) {
            throw new RetrieverException(e);
        } catch (SQLException e) {
            throw new RetrieverException(e);
        }
        return response;
    }

    @Override
    public Dialect getDialect() {
        throw new UnsupportedOperationException("HAJDBC Dialect not available.");
    }

    @Override
    protected boolean isAutoincrement(String name, String table)
            throws RetrieverException {
        boolean response = false;
        try {
            Statement statement = dbMetaData.getConnection().createStatement();
            String query = "SELECT COLUMN_NAME "
                    + //$NON-NLS-1$
                    "FROM INFORMATION_SCHEMA.COLUMNS "
                    + //$NON-NLS-1$
                    "WHERE COLUMNPROPERTY(OBJECT_ID(QUOTENAME(TABLE_SCHEMA) + '.' +"
                    + //$NON-NLS-1$
                    "QUOTENAME('" + table + "')), COLUMN_NAME, 'IsIdentity') = 1"; //$NON-NLS-1$ //$NON-NLS-2$
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet != null) {
                if (resultSet.next()) {
                    String value = resultSet.getString(1);
                    if (value.equals(name)) {
                        response = true;
                    }
                }
            }
            resultSet.close();
            statement.close();
        } catch (SQLServerException e) {
            throw new RetrieverException(e);
        } catch (SQLException e) {
            throw new RetrieverException(e);
        }
        return response;
    }

}
