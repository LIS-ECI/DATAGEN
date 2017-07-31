/**
 * 
 */
package edu.eci.pgr.tests.concrete;

import edu.eci.pgr.business.DatabaseVO;
import edu.eci.pgr.tests.generic.InformationRetrieverTest;


/*
 * this file has been created by Beatriz Rojas and Felipe Villamil.
 * this file is a component of MetaDatBrowsing  
 */
/** 
 * @author Beatriz Rojas
 * @author Felipe Villamil
 * @date 13/04/2009 
 * @version 1.0
 * @class SqlServerInformationRetrieverTest
 * @project MetaDataBrowsing
 * @company Escuela Colombiana de Ingenieria  
 */
public class SqlServerInformationRetrieverTest extends InformationRetrieverTest {
	
	private static final String URL="jdbc:sqlserver://localhost:1433;databaseName=bdpgr";
	private static final String USER="bdpgr";
	private static final String PASSWORD="pg12345";
	private static final String DRIVER="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String ENGINE="SQLSERVER";
	
	/* (non-Javadoc)
	 * @see edu.eci.pgr.tests.generic.InformationRetrieverTest#getDatabseConcrete()
	 */
	public DatabaseVO getDatabseConcrete() {
		return new DatabaseVO(URL,USER,PASSWORD,DRIVER,ENGINE);
	}
	public String[] getTypes(){
		return new String[]{"VARCHAR","INT","DATE","DOUBLE"};
	}
	
	public String[] getTables(){
		return new String[]{"pt_er1", 
				"pt_2_er2" , 
				"pt_er1xpt_1_er2" ,
				"pt_er3" ,
				"pt_er4" ,
				"pt_1_er2" ,
				"st_er1" ,
				"st_er2" ,
				"st_er3" ,
				"st_er4" ,
				"st_er4xtt_er3" ,
				"tt_er3"

			};
	}

}
