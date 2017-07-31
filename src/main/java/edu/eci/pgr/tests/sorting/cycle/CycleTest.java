package edu.eci.pgr.tests.sorting.cycle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.eci.pgr.business.ColumnVO;
import edu.eci.pgr.business.ForeignKeyVO;
import edu.eci.pgr.business.TableVO;
import edu.eci.pgr.business.types.OracleTypeVO;
import edu.eci.pgr.business.types.TypesNames;
import edu.eci.pgr.exceptions.CycleDetectedException;
import edu.eci.pgr.exceptions.GeneratorException;
import edu.eci.pgr.exceptions.UnsupportedDataException;
import edu.eci.pgr.exceptions.WriterException;
import edu.eci.pgr.rowGenerators.RowGenerator;
import edu.eci.pgr.rowGenerators.concrete.MySqlRowGenerator;
import edu.eci.pgr.sorting.SortingTool;
import edu.eci.pgr.sorting.Task;
import edu.eci.pgr.sorting.jgrapht.JGraphTSortingTool;

public class CycleTest {
	public static void main(String []args){
		List<TableVO> tableList;
		try {
			tableList = getTablesGeneratingCycle();
			LinkedList<Task> list1 = new LinkedList<Task>();
			LinkedList<Task> list2 = new LinkedList<Task>();
			RowGenerator fill= new MySqlRowGenerator();
			for(int i=0;i<tableList.size();i++){
				Task task1 = new Task(tableList.get(i));
				Task task2 = new Task(tableList.get(i));
				List<ColumnVO> columns = new ArrayList<ColumnVO>(tableList.get(i).getColumns().values());
				for(int j=0;j<columns.size();j++){
					if(columns.get(j) instanceof ForeignKeyVO){
						task1.addDependency(new Task(((ForeignKeyVO)columns.get(j)).getOriginalTable()));
						task2.addDependency(new Task(((ForeignKeyVO)columns.get(j)).getOriginalTable()));
					}
				}
				list1.add(task1);
				list2.add(task2);
			}
			SortingTool tool;
			List<Task> tasks;
//			tool = new ECISortingTool();
//			tool.sort(list1);
//			tasks = tool.getSortedList();
//			System.out.println("----------LISTING SORTED TASKS (ECISORTINGTOOL)------------");
//			for(int i=0;i<tasks.size();i++){
//				System.out.println(tasks.get(i).getTable().getName());
//			}
//			System.out.println("----------LISTED SUCCESFULLY (ECISORTINGTOOL)------------");
//			
//			System.out.println();
//			System.out.println();
			tool = new JGraphTSortingTool();
			tool.sort(list2);
			tasks = tool.getSortedList();
			System.out.println("----------LISTING SORTED TASKS (JGRAPHTSORTINGTOOL)------------");
			for(int i=0;i<tasks.size();i++){
				System.out.println(tasks.get(i).getTable().getName());
			}
			fill.Fill(tasks);
			System.out.println("----------LISTED SUCCESFULLY (JGRAPHTSORTINGTOOL)------------");
		} catch (GeneratorException e) {
			e.printStackTrace();
		} catch (UnsupportedDataException e) {
			e.printStackTrace();
		} catch (CycleDetectedException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static ArrayList<TableVO> getTablesGeneratingCycle(){
		ArrayList<TableVO> tables = new ArrayList<TableVO>();
		TableVO tablaa = new TableVO("tablaa");
		TableVO tablab = new TableVO("tablab");
		TableVO tablac = new TableVO("tablac");
		TableVO tablad = new TableVO("tablad");
		TableVO tablae = new TableVO("tablae");
		
		ColumnVO columna1a = new ColumnVO("a01",new OracleTypeVO(TypesNames.INT,5,0),false,true);
		ColumnVO columna2a = new ColumnVO("a02",new OracleTypeVO(TypesNames.INT,5,0),false,true);
		ForeignKeyVO columna1b = new ForeignKeyVO("a03",new OracleTypeVO(TypesNames.INT,5,0),false,true,null,null);
		ForeignKeyVO columna2b = new ForeignKeyVO("a04",new OracleTypeVO(TypesNames.INT,5,0),false,true,null,null);
		ForeignKeyVO columna3b = new ForeignKeyVO("a003",new OracleTypeVO(TypesNames.INT,5,0),false,true,null,null);
		ForeignKeyVO columna4b = new ForeignKeyVO("a004",new OracleTypeVO(TypesNames.INT,5,0),false,true,null,null);
		ForeignKeyVO columna1c = new ForeignKeyVO("a05",new OracleTypeVO(TypesNames.INT,5,0),false,true,null,null);
		ForeignKeyVO columna2c = new ForeignKeyVO("a06",new OracleTypeVO(TypesNames.INT,5,0),false,true,null,null);
		ForeignKeyVO columna1d = new ForeignKeyVO("a07",new OracleTypeVO(TypesNames.INT,5,0),false,true,null,null);
		ForeignKeyVO columna2d = new ForeignKeyVO("a08",new OracleTypeVO(TypesNames.INT,5,0),false,true,null,null);
		ForeignKeyVO columna1e = new ForeignKeyVO("a09",new OracleTypeVO(TypesNames.INT,5,0),false,true,null,null);
		ForeignKeyVO columna2e = new ForeignKeyVO("a10",new OracleTypeVO(TypesNames.INT,5,0),false,true,null,null);

		tablaa.addPrimaryColumn(columna1a);
		tablaa.addPrimaryColumn(columna2a);
		tablab.addPkFkColumn(columna1b);
		tablab.addPkFkColumn(columna2b);
		tablab.addPkFkColumn(columna3b);
		tablab.addPkFkColumn(columna4b);
		tablac.addPkFkColumn(columna1c);
		tablac.addPkFkColumn(columna2c);
		tablad.addPkFkColumn(columna1d);
		tablad.addPkFkColumn(columna2d);
		tablae.addPkFkColumn(columna1e);
		tablae.addPkFkColumn(columna2e);
		
		tablab.getPkFkColumn("a003").setOriginalTableName(tablaa);
		tablab.getPkFkColumn("a003").setOriginalColumnName("a01");
		tablab.getPkFkColumn("a004").setOriginalTableName(tablaa);
		tablab.getPkFkColumn("a004").setOriginalColumnName("a11");
		
		tablab.getPkFkColumn("a03").setOriginalTableName(tablae);
		tablab.getPkFkColumn("a03").setOriginalColumnName("a09");
		tablab.getPkFkColumn("a04").setOriginalTableName(tablae);
		tablab.getPkFkColumn("a04").setOriginalColumnName("a10");
		
		tablac.getPkFkColumn("a05").setOriginalTableName(tablab);
		tablac.getPkFkColumn("a05").setOriginalColumnName("a03");
		tablac.getPkFkColumn("a06").setOriginalTableName(tablab);
		tablac.getPkFkColumn("a06").setOriginalColumnName("a04");

		tablad.getPkFkColumn("a07").setOriginalTableName(tablac);
		tablad.getPkFkColumn("a07").setOriginalColumnName("a05");
		tablad.getPkFkColumn("a08").setOriginalTableName(tablac);
		tablad.getPkFkColumn("a08").setOriginalColumnName("a06");

		tablae.getPkFkColumn("a09").setOriginalTableName(tablab);
		tablae.getPkFkColumn("a09").setOriginalColumnName("a07");
		tablae.getPkFkColumn("a10").setOriginalTableName(tablab);
		tablae.getPkFkColumn("a10").setOriginalColumnName("a08");
		
		tablab.getPkFkColumn("a03").setOriginalTableName(tablae);
		tablab.getPkFkColumn("a03").setOriginalColumnName("a09");
		tablab.getPkFkColumn("a04").setOriginalTableName(tablae);
		tablab.getPkFkColumn("a04").setOriginalColumnName("a10");

		tables.add(tablaa);
		tables.add(tablab);
		tables.add(tablac);
		tables.add(tablad);
		tables.add(tablae);
		return tables;
	}

}
