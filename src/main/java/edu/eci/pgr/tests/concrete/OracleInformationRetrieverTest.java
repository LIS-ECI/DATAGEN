/**
 * 
 */
package edu.eci.pgr.tests.concrete;

import edu.eci.pgr.business.DatabaseVO;
import edu.eci.pgr.tests.generic.InformationRetrieverTest;

/**
 * @author Felipe
 *
 */
public class OracleInformationRetrieverTest extends InformationRetrieverTest {
	
	private static final String URL="jdbc:oracle:thin:@localhost:1521:ORCL";;
	private static final String USER="PRUEBASPGR";
	private static final String PASSWORD="pg12345";
	private static final String DRIVER="oracle.jdbc.driver.OracleDriver";;
	private static final String ENGINE="ORACLE";
	
	/* (non-Javadoc)
	 * @see edu.eci.pgr.tests.generic.InformationRetrieverTest#getDatabseConcrete()
	 */
	public DatabaseVO getDatabseConcrete() {
		return new DatabaseVO(URL,USER,PASSWORD,DRIVER,ENGINE);
	}
	
	public String[] getTypes(){
		return new String[]{"VARCHAR2","NUMBER","DATE","NUMBER"};
	}
	
	public String[] getTables(){
		return new String[]{"PT_ER1", 
				"PT_2_ER2" , 
				"PT_ER1XPT_1_ER2" ,
				"PT_ER3" ,
				"PT_ER4" ,
				"PT_1_ER2" ,
				"ST_ER1" ,
				"ST_ER2" ,
				"ST_ER3" ,
				"ST_ER4" ,
				"ST_ER4XTT_ER3" ,
				"TT_ER3"
			};
	}

}
