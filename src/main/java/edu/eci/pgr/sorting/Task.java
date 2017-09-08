package edu.eci.pgr.sorting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.eci.pgr.business.ColumnVO;
import edu.eci.pgr.business.TableVO;
import edu.eci.pgr.business.TypeVO;
import edu.eci.pgr.components.combination.CombinationGenerator;
import edu.eci.pgr.sorting.ecitool.QuickSort;

public class Task implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final int NO_ELEMENTS = -1;
	private TableVO table;
	private boolean isAutoReferenced;
	private List<Task> dependencies;
	private Map<String,TypeVO> types;
	private CombinationGenerator combinationGenerator;
	private boolean needsCombinationGenerator;
	private boolean primaryDependency;
	private boolean useStoredData;
	public Task() {
		super();
		dependencies = new LinkedList<Task>();
		isAutoReferenced = false;
		combinationGenerator = new CombinationGenerator();
	}
	
	public Task(TableVO table) {
		super();
		this.table = table;
		dependencies = new ArrayList<Task>();
		isAutoReferenced = false;
		combinationGenerator = new CombinationGenerator();
		initializeTypes(table);
	}
	
	/*public Task(TableVO table, List<Task> dependencies) {
		super();
                this.table = table;
		this.dependencies = dependencies;
		isAutoReferenced = false;
		initializeTypes(table);
	}*/
	
	private  void initializeTypes(TableVO table){
		types=new Hashtable<String,TypeVO>();
		for(ColumnVO col: table.getColumns().values()){
			types.put(col.getName(),col.getType());
		}
	}

	public boolean needsCombinationGenerator() {
		return needsCombinationGenerator;
	}

	public boolean isPrimaryDependency(){
		return primaryDependency;
	}
	public TableVO getTable() {
		return table;
	}
	public void setTable(TableVO table) {
		this.table = table;
	}
	public List<Task> getDependencies() {
		dependencies = new QuickSort().quicksortNode(dependencies);
		return dependencies;
	}
	public void setDependencies(List<Task> dependencies) {
		this.dependencies = dependencies;
	}
	public void addDependency(Task dependency){
		dependencies.add(dependency);
	}
	public Task getDependency(int i){
		return dependencies.get(i);
	}
	public int getDependenciesLength(){
		if(dependencies == null || dependencies.size()<1 )
			return NO_ELEMENTS;
		return dependencies.size();
	}

	public boolean isAutoReferenced() {
		return isAutoReferenced;
	}

	public void setAutoReferenced(boolean isAutoReferenced) {
		this.isAutoReferenced = isAutoReferenced;
	}
	
	public void setIsPrimaryDependency(boolean primaryDependency){
		this.primaryDependency = primaryDependency;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dependencies == null) ? 0 : dependencies.hashCode());
		result = prime * result + (isAutoReferenced ? 1231 : 1237);
		result = prime * result + ((table == null) ? 0 : table.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Task other = (Task) obj;
		if (dependencies == null) {
			if (other.dependencies != null)
				return false;
		} else if (!dependencies.equals(other.dependencies))
			return false;
		if (isAutoReferenced != other.isAutoReferenced)
			return false;
		if (table == null) {
			if (other.table != null)
				return false;
		} else if (!table.equals(other.table))
			return false;
		return true;
	}

	/**
	 * @return the types
	 */
	public Map<String, TypeVO> getTypes() {
		return types;
	}

	/**
	 * @param types the types to set
	 */
	public void setTypes(Map<String, TypeVO> types) {
		this.types = types;
	}

	public void generateMaxValuesForCombination(){
		if(!useStoredData){
			if(dependencies.size()>0){
				needsCombinationGenerator = true;
				int max = getTotalTablesCount();
				if(max>0){
					long []maxValuesForCombination = new long[max];
					String tableName = "";
					int pos = -1;
					for(int i=0;i<dependencies.size();i++){
						if(dependencies.get(i).isPrimaryDependency() && !dependencies.get(i).getTable().getName().equals(tableName)){
							tableName = dependencies.get(i).getTable().getName();
							pos++;
						}
						if(dependencies.get(i).isPrimaryDependency())
							maxValuesForCombination[pos]=dependencies.get(i).getTable().getInsertTimes();
					}
					combinationGenerator.generateValues(maxValuesForCombination);
				}
				else
					needsCombinationGenerator = false;
			}else{
				needsCombinationGenerator = false;
			}
		}
	}
	
	private int getTotalTablesCount() {
		int response = 0;
		String tableName = "";
		for(int i=0;i<dependencies.size();i++){
			if(dependencies.get(i).isPrimaryDependency() && !dependencies.get(i).getTable().getName().equals(tableName)){
				tableName = dependencies.get(i).getTable().getName();
				response++;
			}
		}
		return response;
	}

	public long[] getNextCombinationValues(){
		if(needsCombinationGenerator)
			return combinationGenerator.getNextValues();
		return null;
	}

	public boolean isUseStoredData() {
		return useStoredData;
	}

	public void setUseStoredData(boolean useStoredData) {
		this.useStoredData = useStoredData;
	}
}
