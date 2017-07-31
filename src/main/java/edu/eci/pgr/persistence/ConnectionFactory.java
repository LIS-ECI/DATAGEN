package edu.eci.pgr.persistence;
//=======================================================================
//FILE ConnectionFactory
//CREATE DATE: Agosto 2008
//AUTHORS: Beatriz Rojas, Javier Villamil
// Gets a connection to a database
//=======================================================================


//=======================================================================
//REQUIRED LIBRARIES
//=======================================================================
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import edu.eci.pgr.business.InfoDatabaseVO;
import edu.eci.pgr.exceptions.ConnectionException;
import edu.eci.pgr.i18n.MessageBundleManager;




//=======================================================================
//CLASS ConnectionFactory.java
//=======================================================================


public class ConnectionFactory implements Serializable{
	private static final String SQL_ERROR = MessageBundleManager.getString("ConnectionFactory.0"); //$NON-NLS-1$
	private static final String DRIVER_MESSAGE = MessageBundleManager.getString("ConnectionFactory.3"); //$NON-NLS-1$
	private static final String ERROR_CONNECTION = MessageBundleManager.getString("ConnectionFactory.4"); //$NON-NLS-1$
	private static final long serialVersionUID = 1L;
	//	-----------------------------------------------------------------------
	//	CLASS ATRIBUTES
	//	-----------------------------------------------------------------------
	/** Defines the connection factory as a singleton*/
	private static ConnectionFactory solitario;

	//	-----------------------------------------------------------------------
	//	INSTANCE ATRIBUTES
	//	-----------------------------------------------------------------------
	private InfoDatabaseVO databaseInfo;
	private Connection conn;

	//	-----------------------------------------------------------------------
	//	CONSTRUCTOR METHODS
	//	-----------------------------------------------------------------------
	/** 
	 * Creates a new connection
	 * @throws ConnectionException 
	 * @pre No instance has been created 
	 * @pos A new instance is created
	 */
	private ConnectionFactory() {
	}
	//	-----------------------------------------------------------------------
	//	CLASS METHODS
	//	-----------------------------------------------------------------------
	/** 
	 * Returns the singleton of the class
	 * @pre True
	 * @return the only instance of ConnectionFactory 
	 * @throws ConnectionException 
	 */
	public static ConnectionFactory getInstance(){
		if(solitario==null)
			solitario=new ConnectionFactory();
		return(solitario);  
	}

	public static void resetInstance(){
		solitario=null;
	}
	//	-----------------------------------------------------------------------
	//	INSTANCE METHODS
	//	-----------------------------------------------------------------------
	/**
	 * Returns a new connection to the database
	 * @param data ,database from where the connection is needed
	 * @return a new connection to the database selected
	 * @throws ConnectionException 
	 * @pre true
	 * @pos a new connection to the database selected
	 */
	public Connection getConnection(InfoDatabaseVO infodatabase) throws ConnectionException {
		try {
			if (conn==null || connectionInformationChanged(infodatabase)){
				System.out.println(infodatabase.getUrl());
				this.databaseInfo = infodatabase;
				Class.forName(databaseInfo.getDriver());
				conn =  DriverManager.getConnection(databaseInfo.getUrl(),databaseInfo.getUsername(),databaseInfo.getPassword());
			}
		} catch (ClassNotFoundException e) {
			throw new ConnectionException(ERROR_CONNECTION+this.databaseInfo.getUrl()+DRIVER_MESSAGE);
		} catch (SQLException e) {
			throw new ConnectionException(ERROR_CONNECTION+this.databaseInfo.getUrl()+SQL_ERROR+e.getMessage());
		} 
		return conn;
	}


	private boolean connectionInformationChanged(InfoDatabaseVO infodatabase) {
		return !((infodatabase.getDriver().equals(databaseInfo.getDriver()) 
				&& infodatabase.getEngine().equals(databaseInfo.getEngine())
				&& infodatabase.getPassword().equals(databaseInfo.getPassword()) 
				&& infodatabase.getUrl().equals(databaseInfo.getUrl())
				&& infodatabase.getUsername().equals(databaseInfo.getUsername())));
	}
	public Connection getConnection(){
		return conn;
	}
	public void closeConnection() throws ConnectionException{
		try {
			if (false)conn.close();
		} catch (SQLException e) {
			throw new ConnectionException(MessageBundleManager.getString("ConnectionFactory.7")); //$NON-NLS-1$
		}
	}
	//	-----------------------------------------------------------------------
	//	GETTER AND SETTER METHODS
	//	-----------------------------------------------------------------------
	
	public InfoDatabaseVO getInfoDatabaseVO(){
		return databaseInfo;
	}

}