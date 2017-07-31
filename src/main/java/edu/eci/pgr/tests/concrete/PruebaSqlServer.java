package edu.eci.pgr.tests.concrete;

import java.util.ArrayList;
import java.util.List;

import edu.eci.pgr.business.ColumnVO;
import edu.eci.pgr.business.DatabaseVO;
import edu.eci.pgr.business.ForeignKeyVO;
import edu.eci.pgr.business.TableVO;
import edu.eci.pgr.exceptions.ConnectionException;
import edu.eci.pgr.exceptions.InstantException;
import edu.eci.pgr.exceptions.RetrieverException;
import edu.eci.pgr.persistence.Facade;



public class PruebaSqlServer {
	public static void main(String[] args) {
		//ArrayList<TableVO> tablas;

		String url = "jdbc:sqlserver://localhost:1433;databaseName=bdpgr";
		String username = "bdpgr";
		String password = "pg12345";
		String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String engine = "SQLSERVER";
		DatabaseVO database = new DatabaseVO(url,username,password,driver,engine);
		List<TableVO> tables = (List<TableVO>) database.getTables().values();
		for (TableVO tableVO : tables) {
			System.out.println("Nombre de la tabla: "+tableVO.getName());
			List<ColumnVO> columnas = new ArrayList<ColumnVO>(tableVO.getColumns().values());
			System.out.println("Columnas normales");
			for (ColumnVO columnVO : columnas) {
				System.out.println("Nombre de la columna: "+columnVO.getName());
				System.out.println("Tipo de la columna: "+columnVO.getType().getName());
			}
			List<ColumnVO> pks = new ArrayList<ColumnVO>(tableVO.getPrimaryColumns().values());
			System.out.println("Llaves primarias");
			for (ColumnVO columnVO : pks) {
				System.out.println("Nombre de la columna: "+columnVO.getName());
				System.out.println("Tipo de la columna: "+columnVO.getType().getName());
			}
			List<ForeignKeyVO> fks = new ArrayList<ForeignKeyVO>(tableVO.getForeignColumns().values());
			System.out.println("Llaves foraneas");
			for (ForeignKeyVO columnVO : fks) {
				System.out.println("Nombre de la columna: "+columnVO.getName());
				System.out.println("Tipo de la columna: "+columnVO.getType().getName());
				System.out.println("Nombre de la columna original: "+columnVO.getOriginalColumnName());
				System.out.println("Nombre de la tabla original: "+columnVO.getOriginalTable().getName());
			}
			List<ForeignKeyVO> pkfks = new ArrayList<ForeignKeyVO>(tableVO.getPkFkColumns().values());
			System.out.println("Llaves primarias foraneas");
			for (ForeignKeyVO columnVO : pkfks) {
				System.out.println("Nombre de la columna: "+columnVO.getName());
				System.out.println("Tipo de la columna: "+columnVO.getType().getName());
				System.out.println("Nombre de la columna original: "+columnVO.getOriginalColumnName());
				System.out.println("Nombre de la tabla original: "+columnVO.getOriginalTable().getName());
			}
		}

	}

}
