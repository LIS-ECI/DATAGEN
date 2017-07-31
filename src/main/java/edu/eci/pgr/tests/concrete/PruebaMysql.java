package edu.eci.pgr.tests.concrete;

import java.util.ArrayList;

import edu.eci.pgr.business.ColumnVO;
import edu.eci.pgr.business.ForeignKeyVO;
import edu.eci.pgr.business.TableVO;
import edu.eci.pgr.exceptions.ConnectionException;
import edu.eci.pgr.exceptions.InstantException;
import edu.eci.pgr.exceptions.RetrieverException;
import edu.eci.pgr.persistence.Facade;



public class PruebaMysql {
	public static void main(String[] args) {
		ArrayList<TableVO> tablas;
		try {
			tablas = new ArrayList<TableVO>(Facade.getInstance().getDatabase().getTables().values());
			System.out.println("total tablas: "+tablas.size());
			for(TableVO table: tablas){
				System.out.println();
				System.out.println("tabla: "+table.getName());
				ArrayList<ColumnVO> columns= new ArrayList<ColumnVO> (table.getColumns().values());
				System.out.println("total columnas= "+table.getTotalColumns()+"\n");
				for (ColumnVO col: columns){
					System.out.println("columna: "+col.getName()+"  nullable: "+col.isNullable()+"   type: "+col.getType() + 
							"   esprimaria?: "+  col.isPrimaryKey()+"\ntipo de generador:"+ col.getGenerator()+
							" es autoincrementada? "+col.isAutoIncrement()+" es unica? "+col.isUniqueKey());
					if(col.isNullable()) System.out.print("porcentaje de nulos: "+col.getNullablePercentage());
					if(col instanceof ForeignKeyVO) {
						System.out.println("ESTA COLUMNA TIENE REFERENCIA ");
						System.out.println("en: " +((ForeignKeyVO)col).getOriginalTable().getName()+"."+((ForeignKeyVO)col).getOriginalColumnName() );
					}
					System.out.println("\n*********************************************************************");
				}
				System.out.println();
			}
		} catch (InstantException e) {
			e.printStackTrace();
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (RetrieverException e) {
			e.printStackTrace();
		}
		
	}
}
