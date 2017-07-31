package edu.eci.pgr.tests.sorting;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.eci.pgr.business.ColumnVO;
import edu.eci.pgr.business.ForeignKeyVO;
import edu.eci.pgr.business.TableVO;
import edu.eci.pgr.exceptions.ConnectionException;
import edu.eci.pgr.exceptions.CycleDetectedException;
import edu.eci.pgr.exceptions.GeneratorException;
import edu.eci.pgr.exceptions.InstantException;
import edu.eci.pgr.exceptions.RetrieverException;
import edu.eci.pgr.exceptions.UnsupportedDataException;
import edu.eci.pgr.exceptions.WriterException;
import edu.eci.pgr.persistence.Facade;
import edu.eci.pgr.rowGenerators.RowGenerator;
import edu.eci.pgr.rowGenerators.concrete.MySqlRowGenerator;
import edu.eci.pgr.sorting.SortingTool;
import edu.eci.pgr.sorting.Task;
import edu.eci.pgr.sorting.ecitool.ECISortingTool;
import edu.eci.pgr.sorting.jgrapht.JGraphTSortingTool;

public class TestSorting {
	public static void main(String []args){
		List<TableVO> tableList;
		try {
			tableList = new ArrayList<TableVO>(Facade.getInstance().getDatabase().getTables().values());
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
			tool = new ECISortingTool();
			tool.sort(list1);
			tasks = tool.getSortedList();
			System.out.println("----------LISTING SORTED TASKS (ECISORTINGTOOL)------------");
			for(int i=0;i<tasks.size();i++){
				System.out.println(tasks.get(i).getTable().getName());
			}
			System.out.println("----------LISTED SUCCESFULLY (ECISORTINGTOOL)------------");
			
			System.out.println();
			System.out.println();
			tool = new JGraphTSortingTool();
			tool.sort(list2);
			tasks = tool.getSortedList();
			System.out.println("----------LISTING SORTED TASKS (JGRAPHTSORTINGTOOL)------------");
			for(int i=0;i<tasks.size();i++){
				System.out.println(tasks.get(i).getTable().getName());
			}
			if(false)fill.Fill(tasks);
			System.out.println("----------LISTED SUCCESFULLY (JGRAPHTSORTINGTOOL)------------");
		} catch (InstantException e) {
			e.printStackTrace();
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (RetrieverException e) {
			e.printStackTrace();
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
	
}
