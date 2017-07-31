package edu.eci.pgr.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import edu.eci.pgr.business.InfoDatabaseVO;
import edu.eci.pgr.business.types.TypesNames;
import edu.eci.pgr.exceptions.ConnectionException;
import edu.eci.pgr.exceptions.DAOException;
import edu.eci.pgr.exceptions.DAOInsertException;
import edu.eci.pgr.exceptions.DAOSelectException;
import edu.eci.pgr.i18n.MessageBundleManager;

public class DAO {
	private static final String CONNECTION_ERROR = MessageBundleManager.getString("DAO.0"); //$NON-NLS-1$
	private static final String INSERT_DATA = MessageBundleManager.getString("DAO.1"); //$NON-NLS-1$
	private static final String READ_DATA = MessageBundleManager.getString("DAO.2"); //$NON-NLS-1$
	private static final String DATA_ERROR = MessageBundleManager.getString("DAO.3"); //$NON-NLS-1$
	private static final String BECAUSE = MessageBundleManager.getString("DAO.4"); //$NON-NLS-1$
	private static final String NOT_EXECUTE = MessageBundleManager.getString("DAO.5"); //$NON-NLS-1$
	private InfoDatabaseVO database;
	public DAO(InfoDatabaseVO database) throws ConnectionException{
		this.database = database;
	}
	public void createTable(String table)  {
			try {
				String sqlDrop = "DROP TABLE "+table;
				Statement stm = ConnectionFactory.getInstance().getConnection(database).createStatement();
				stm.execute(sqlDrop);
			} catch (SQLException e) {
				System.out.println("Didn't need drop");
			}

			try {
				String sqlCreate = "CREATE TABLE "+table+" (ROWID INT NOT NULL, VALUE VARCHAR(200) NOT NULL, PRIMARY KEY (ROWID,VALUE))";
				Statement stmt = ConnectionFactory.getInstance().getConnection(database).createStatement();
				System.out.println(sqlCreate);
				stmt.executeUpdate(sqlCreate);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	public void executeUpdateQuery(String query) throws DAOInsertException{
		try {
			Statement stmt = ConnectionFactory.getInstance().getConnection(database).createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			throw new DAOInsertException(NOT_EXECUTE+query+BECAUSE+e.getMessage());
		}finally{
			ConnectionFactory.getInstance().closeConnection();
		}
	}
	public void insert(String[]fields, Object[]values, String[]types, String tableName) throws DAOInsertException, DAOException{
		PreparedStatement psInsert = null;
		try {
			String sqlInsert = "INSERT INTO "+tableName+"("; //$NON-NLS-1$ //$NON-NLS-2$
			for(int i=0;i<fields.length;i++){
				sqlInsert+=fields[i];
				if(i<fields.length-1)
					sqlInsert+=","; //$NON-NLS-1$
			}
			sqlInsert+=") VALUES ("; //$NON-NLS-1$
			for(int i=0;i<values.length;i++){
				sqlInsert+="?"; //$NON-NLS-1$
				if(i<fields.length-1)
					sqlInsert+=","; //$NON-NLS-1$
			}
			sqlInsert+=")"; //$NON-NLS-1$
			psInsert = ConnectionFactory.getInstance().getConnection(database).prepareStatement(sqlInsert);

			for(int i=0;i<fields.length;i++){
				if(types[i].equals(TypesNames.DATE))
					psInsert.setDate(i+1,(Date)values[i]);
				else if(types[i].equals(TypesNames.DOUBLE))
					psInsert.setDouble(i+1,(Double)values[i]);
				else if(types[i].equals(TypesNames.INT))
					psInsert.setInt(i+1,(Integer)values[i]);
				else if(types[i].equals(TypesNames.STRING))
					psInsert.setString(i+1,(String)values[i]);
			}

			psInsert.executeUpdate();
		} catch (SQLException e) {
			throw new DAOInsertException(INSERT_DATA+e.getMessage());
		} finally{
			if(psInsert!=null){
				try {
					psInsert.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new DAOException(CONNECTION_ERROR+e.getMessage());
				}
			}
			ConnectionFactory.getInstance().closeConnection();
		}
	}
	public LinkedList<Object[]> selectData(String[] fields,String[]types,String table) throws DAOSelectException, DAOException {
		LinkedList<Object[]> response = null;
		Statement stmt2 = null;
		ResultSet rs = null;
		try {
			String sqlSelect = "SELECT"; //$NON-NLS-1$
			if(fields == null || fields.length<1)
				sqlSelect+=" * "; //$NON-NLS-1$
			else{
				for(int i=0;i<fields.length;i++){
					sqlSelect+=" "+fields[i]+" "; //$NON-NLS-1$ //$NON-NLS-2$
					if(i<fields.length-1)
						sqlSelect+=","; //$NON-NLS-1$
				}
			}
			sqlSelect+=" FROM "+table; //$NON-NLS-1$
			stmt2 = ConnectionFactory.getInstance().getConnection(database).createStatement();
			rs = stmt2.executeQuery(sqlSelect);
			if(rs != null){
				response = new LinkedList<Object[]>();
				while (rs.next()) {
					Object[] res = new Object[fields.length];
					for(int i=1;i<=fields.length;i++){
						if(types[i-1].equals(TypesNames.DATE))
							res[i-1] = rs.getDate(i);
						if(types[i-1].equals(TypesNames.DOUBLE))
							res[i-1] = rs.getDouble(i);
						if(types[i-1].equals(TypesNames.INT))
							res[i-1] = rs.getInt(i);
						if(types[i-1].equals(TypesNames.STRING))
							res[i-1] = rs.getString(i);
					}
					response.add(res);
				}
			}
			rs.close();
		} catch (SQLException e) {
			throw new DAOSelectException(READ_DATA+e.getMessage());
		} finally{
			if(stmt2!=null){
				try {
					stmt2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new DAOException(DATA_ERROR+table+" ",e); //$NON-NLS-1$
				}
			}
			ConnectionFactory.getInstance().closeConnection();

		}
		return response;
	}
	public LinkedList<Object[]> selectDataWithWhereClause(String[] fields,String[]types,String table,String whereClause) throws DAOSelectException, DAOException {
		LinkedList<Object[]> response = null;
		Statement stmt2 = null;
		ResultSet rs = null;
		try {
			String sqlSelect = "SELECT"; //$NON-NLS-1$
			if(fields == null || fields.length<1)
				sqlSelect+=" * "; //$NON-NLS-1$
			else{
				for(int i=0;i<fields.length;i++){
					sqlSelect+=" "+fields[i]+" "; //$NON-NLS-1$ //$NON-NLS-2$
					if(i<fields.length-1)
						sqlSelect+=","; //$NON-NLS-1$
				}
			}
			sqlSelect+=" FROM "+table+" WHERE "+whereClause; //$NON-NLS-1$ //$NON-NLS-2$
			stmt2 = ConnectionFactory.getInstance().getConnection(database).createStatement();
			rs = stmt2.executeQuery(sqlSelect);
			if(rs != null){
				response = new LinkedList<Object[]>();
				while (rs.next()) {
					Object[] res = new Object[fields.length];
					for(int i=1;i<=fields.length;i++){
						if(types[i-1].equals(TypesNames.DATE))
							res[i-1] = rs.getDate(i);
						if(types[i-1].equals(TypesNames.DOUBLE))
							res[i-1] = rs.getDouble(i);
						if(types[i-1].equals(TypesNames.INT))
							res[i-1] = rs.getInt(i);
						if(types[i-1].equals(TypesNames.STRING))
							res[i-1] = rs.getString(i);
					}
					response.add(res);
				}
			}
			rs.close();
		} catch (SQLException e) {
			throw new DAOSelectException(READ_DATA+e.getMessage());
		} finally{
			if(stmt2!=null){
				try {
					stmt2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new DAOException(DATA_ERROR+table+" ",e); //$NON-NLS-1$
				}
			}

			ConnectionFactory.getInstance().closeConnection();
		}
		return response;
	}
	public long totalRowCount(String table) throws DAOException{
		Statement s=null;
		ResultSet rs=null;
		long ret=0;
		try {
			s=(Statement) ConnectionFactory.getInstance().getConnection(database).createStatement();
			String sql= "SELECT COUNT(*)  FROM "+ table; //$NON-NLS-1$
			s.execute(sql);
			rs=s.getResultSet();
			if(rs!=null){
				rs.next();
				ret=rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(DATA_ERROR+table+" ",e); //$NON-NLS-1$
		} finally{
			if(s!=null){
				try {
					s.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new DAOException(DATA_ERROR+table+" ",e); //$NON-NLS-1$
				}
			}
			ConnectionFactory.getInstance().closeConnection();
		}
		return ret;
	}
	protected static class ConnectionFactory{
		private static ConnectionFactory instance;
		private Connection connection;
		public static ConnectionFactory getInstance(){
			if(instance == null)
				instance = new ConnectionFactory();
			return instance;
		}
		public Connection getConnection(InfoDatabaseVO database) {
			try {
				if (connection == null || !connection.isClosed()) {
					try {
						Class.forName(database.getDriver());
						connection = DriverManager.getConnection(database.getUrl(),
								database.getUsername(), database.getPassword());
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return connection;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return connection;
		}
		public void closeConnection(){
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			connection = null;
		}
	}
}
