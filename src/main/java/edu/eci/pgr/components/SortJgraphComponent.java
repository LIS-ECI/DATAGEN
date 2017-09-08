package edu.eci.pgr.components;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.eci.pgr.business.ColumnVO;
import edu.eci.pgr.business.ForeignKeyVO;
import edu.eci.pgr.business.TableVO;
import edu.eci.pgr.components.instantiation.InstantiationSortingToolComponent;
import edu.eci.pgr.exceptions.CycleDetectedException;
import edu.eci.pgr.exceptions.InstantException;
import edu.eci.pgr.exceptions.LoaderException;
import edu.eci.pgr.persistence.Facade;
import edu.eci.pgr.properties.SortingProperties;
import edu.eci.pgr.sorting.SortingTool;
import edu.eci.pgr.sorting.Task;

public class SortJgraphComponent  {
	private static final long serialVersionUID = 1L;
	private SortingTool tool;
	
	public List<Task> sortTables(List<TableVO> tableList){
		LinkedList<Task> list1 = new LinkedList<Task>();
		List<Task> tasks = null;
		for(int i=0;i<tableList.size();i++){
			Task task1 = new Task(tableList.get(i));
                        

                        
			List<ColumnVO> columns = new ArrayList<ColumnVO>(tableList.get(i).getColumns().values());
			for(int j=0;j<columns.size();j++){
				if(columns.get(j) instanceof ForeignKeyVO){
                                                                        
					Task newTask = new Task(((ForeignKeyVO)columns.get(j)).getOriginalTable());
					newTask.setIsPrimaryDependency(((ForeignKeyVO)columns.get(j)).isPrimaryKey());
					task1.addDependency(newTask);
				}
			}
			list1.add(task1);
		}
		try {
			LinkedList<String> sortProperties = Facade.getInstance().LoadSortingData();
			String value = sortProperties.get(SortingProperties.SORTING).toUpperCase();
			tool = (SortingTool) InstantiationSortingToolComponent.getInstance().InstantiateObject(value);
			tool.sort(list1);
			tasks = tool.getSortedList();
		} catch (LoaderException e) {
			e.printStackTrace();
		} catch (InstantException e) {
			e.printStackTrace();
		} catch (CycleDetectedException e) {
			e.printStackTrace();
		}
		return tasks;
	}
}