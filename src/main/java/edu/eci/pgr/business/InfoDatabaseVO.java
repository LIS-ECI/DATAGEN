package edu.eci.pgr.business;

import java.io.Serializable;

public class InfoDatabaseVO implements Comparable<InfoDatabaseVO>,Serializable{
	private static final long serialVersionUID = 1L;
	/** Connection url to access the database**/
	private String url;
	/** Username of the database**/
	private String username;
	/** Password of the database**/
	private String password;
	/** Driver used to connect to the database **/
	private String driver;
	/** database engine**/
	private String engine;
	
	public InfoDatabaseVO(String url, String username, String password,
			String driver, String engine) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
		this.driver = driver;
		this.engine = engine;
	}
	public InfoDatabaseVO() {
	}
	/**
	 * Returns the driver needed for the connection
	 * @pre A new instance has been created
	 * @return driver needed for the connection
	 */
	public String getDriver() {
		return driver;
	}
	/**
	 * Returns the password of the database
	 * @pre A new instance has been created
	 * @return password of the database
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Returns the connection url to the database
	 * @pre A new instance has been created
	 * @return url for the connection to the database
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * Returns the username of the database
	 * @pre A new instance has been created
	 * @return username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * @return the engine
	 */
	public String getEngine() {
		return engine;
	}
	//-----------------------------------------------------------------------
	// SETTER METHODS
	//-----------------------------------------------------------------------
	/**
	 * Sets a new password for the database
	 * @param password ,new password for the database
	 * @pre A new instance has been created
	 * @pos A new password has been set to the database
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * Sets a new connection url to the database
	 * @param url ,new connection url to the database
	 * @pre A new instance has been created
	 * @pos A new connection url has been set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * Sets a new username to the database
	 * @param username ,new username for the database
	 * @pre A new instance has been created
	 * @pos A new username is set to the database
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	


	/**
	 * @param engine the engine to set
	 */
	public void setEngine(String engine) {
		this.engine = engine;
	}
	
	public void setDriver(String driver){
		this.driver = driver;
	}
	//-----------------------------------------------------------------------
	// OVERRIDE METHODS
	//-----------------------------------------------------------------------
		
		@Override
		public int compareTo(InfoDatabaseVO o) {
			return this.getUrl().compareTo(o.getUrl());
		}

}
