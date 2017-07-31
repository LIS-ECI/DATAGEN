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
public class MySqlInformationRetrieverTest extends InformationRetrieverTest {
	
	private static final String URL="jdbc:mysql://localhost:3306/testRetriever";
	private static final String USER="root";
	private static final String PASSWORD="pass";
	private static final String DRIVER="com.mysql.jdbc.Driver";
	private static final String ENGINE="MYSQL";
	
	/* (non-Javadoc)
	 * @see edu.eci.pgr.tests.generic.InformationRetrieverTest#getDatabseConcrete()
	 */
	public DatabaseVO getDatabseConcrete() {
		return new DatabaseVO(URL,USER,PASSWORD,DRIVER,ENGINE);
	}
	public String[] getTypes(){
		return new String[]{"VARCHAR","TIMESTAMP","DOUBLE",
				"INT","MEDIUMINT","BIGINT","BINARY"};
	}
	
	public String[] getTables(){
		return new String[]{"ESTADO_ORDEN", 
				"ESTADO_ARCHIVO" , 
				"TIPO_ARCHIVO" ,
				"TIPO_PROCESO" ,
				"TIPO_ORDEN" ,
				"TIPO_MOTIVO" ,
				"ORDEN_PROCESO" ,
				"ARCHIVO" ,
				"IMPRESORA" ,
				"JOB" ,
				"JOB_POR_IMPRESORA" ,
				"PROCESO" ,
				"PROCESO_CARGUE" ,
				"PROCESO_IMPRESION" ,
				"ORDEN_PROCESO_MOVIMIENTO" ,
				"ARCHIVO_MOVIMIENTO" 
			};
	}

}
