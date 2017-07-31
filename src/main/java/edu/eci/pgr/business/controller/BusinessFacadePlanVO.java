package edu.eci.pgr.business.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import edu.eci.pgr.business.ColumnVO;
import edu.eci.pgr.business.DatabaseVO;
import edu.eci.pgr.business.ForeignKeyVO;
import edu.eci.pgr.business.InfoDatabaseVO;
import edu.eci.pgr.business.TableVO;
import edu.eci.pgr.business.TypeVO;
import edu.eci.pgr.business.types.TypesNames;
import edu.eci.pgr.components.SortJgraphComponent;
import edu.eci.pgr.components.instantiation.InstantiationDataGeneratorComponent;
import edu.eci.pgr.components.instantiation.InstantiationRowGeneratorComponent;
import edu.eci.pgr.dataGenerators.DataGenerator;
import edu.eci.pgr.exceptions.ConnectionException;
import edu.eci.pgr.exceptions.GeneratorException;
import edu.eci.pgr.exceptions.InstantException;
import edu.eci.pgr.exceptions.RetrieverException;
import edu.eci.pgr.exceptions.UnsupportedAttribute;
import edu.eci.pgr.exceptions.UnsupportedAttributeValueException;
import edu.eci.pgr.exceptions.UnsupportedDataException;
import edu.eci.pgr.exceptions.WriterException;
import edu.eci.pgr.i18n.MessageBundleManager;
import edu.eci.pgr.persistence.Facade;
import edu.eci.pgr.rowGenerators.RowGenerator;
import edu.eci.pgr.sorting.Task;

public class BusinessFacadePlanVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private DatabaseVO database;
	private List<Task> tasks;
	private DataGenerator actualGenerator;
	private RowGenerator filler;
	private HashMap<String,HashMap<String, Object[]>> tableNcolumnDetail;
	
	public BusinessFacadePlanVO() {
		super();
	}
	
	public void setDatabase(InfoDatabaseVO infodatabase) throws RetrieverException, ConnectionException, InstantException{
		database = Facade.getInstance().getDatabase(infodatabase);
		SortJgraphComponent sorter = new SortJgraphComponent();
		tasks = sorter.sortTables(new LinkedList<TableVO>(database.getTables().values()));
	}
	
	public boolean hasTables(){
		return tasks.size()>0;
	}
	
	public List<String> getTablesThatNeedInsertionNumber() {
		List<String> response = new ArrayList<String>();
		for(int i=0;i<tasks.size();i++)
			if(!tasks.get(i).isUseStoredData())
				response.add(tasks.get(i).getTable().getName());
		return response;
	}
	public void setInsertTimes(String tableName,long insertTimes){
		int pos = getTasksIndex(tableName);
		tasks.get(pos).getTable().setInsertTimes(insertTimes);
	}
	public long getInsertTimesSelectedTable(String tableName){
		int pos = getTasksIndex(tableName);
		return tasks.get(pos).getTable().getInsertTimes();
	}
	public void setNullablePercentageColumnInTable(String tableName,String columnName,double nullablepercentage){
		int pos = getTasksIndex(tableName);
		tasks.get(pos).getTable().getColumn(columnName).setNullablePercentage(nullablepercentage);
	}
	public double getNullablePercentageColumnInTable(String tableName,String columnName){
		int pos = getTasksIndex(tableName);
		return tasks.get(pos).getTable().getColumn(columnName).getNullablePercentage();
	}
	public void setColumnAttributesInTable(String tableName,String columnName) throws UnsupportedAttributeValueException, UnsupportedAttribute{
		int pos = getTasksIndex(tableName);
		tasks.get(pos).getTable().getColumn(columnName).getGenerator().doneConfiguration();
	}
	public void setGenerator(String tableName,String columnName,String datagenname) throws InstantException{
		int pos = getTasksIndex(tableName);
		if(datagenname!=null){
			DataGenerator datagen = (DataGenerator) InstantiationDataGeneratorComponent.getInstance().InstantiateObject(datagenname);
			datagen.setName(datagenname);
			tasks.get(pos).getTable().getColumn(columnName).setGenerator(datagen);
		}
	}
	public String getGenerator(String tableName,String columnName){
		int pos = getTasksIndex(tableName);
		return tasks.get(pos).getTable().getColumn(columnName).getGenerator()==null?
				null:tasks.get(pos).getTable().getColumn(columnName).getGenerator().getName();
	}
	public void addColumnAttributeInTable(String tableName,String columnName,String attname,String attvalue) throws UnsupportedAttributeValueException, UnsupportedAttribute{
		int pos = getTasksIndex(tableName);
		tasks.get(pos).getTable().getColumn(columnName).getGenerator().addAttributeValue(attname, attvalue);
	}
	public String[] getMaximumRowsForTable(String tableName){
		String resp[] = {"","",""};  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		long response = Long.MAX_VALUE;
		int pos = getTasksIndex(tableName);
		LinkedList<ForeignKeyVO> foreignColumns = new LinkedList<ForeignKeyVO>(tasks.get(pos).getTable().getPkFkColumns().values());
		LinkedList<ColumnVO> columns = new LinkedList<ColumnVO>(tasks.get(pos).getTable().getColumns().values());
		Collections.sort(foreignColumns);
		//ordenar las columnas por la cantidad de datos maxima que genera un generador
		Collections.sort(columns);
		if(foreignColumns != null && foreignColumns.size() > 0){
			response = 1;
			String currentTable = ""; //$NON-NLS-1$
			/*recorre las columnas primarias foraneas validando la cantidad maxima que se puede insertar en ellas
			que es la multiplicación de las cantidades de las columnas referenciadas */
			TableVO table;
			for(int i=0;i<foreignColumns.size();i++){
				if(!currentTable.equals(foreignColumns.get(i).getOriginalTable().getName())){
					table = foreignColumns.get(i).getOriginalTable();
					response*=table.getInsertTimes();
					currentTable = table.getName();
					resp[1] = MessageBundleManager.getString("BusinessFacade.1"); //$NON-NLS-1$
					resp[2] += currentTable+","; //$NON-NLS-1$
				}
			}

		}
		/*valida la cantidad de valores unicos que puede generar el generador si la columna es unica
		el valor retornado es la cantidad de datos menor de la tabla*/
		if(columns != null && columns.size() > 0){
			for (ColumnVO columnVO : columns) {
				long max=Long.MAX_VALUE;
				if(columnVO.getGenerator()!=null)
					max = columnVO.getGenerator().getMaximumUniqueValues();
				//si la columna no es unica, no se asigna el valor a response
				if(columnVO.isUniqueKey()  && max<response && !columnVO.isAutoIncrement()){
					response = max;
					resp[1] = MessageBundleManager.getString("BusinessFacade.2"); //$NON-NLS-1$
					resp[2] = columnVO.getGenerator().getName();
				}
			}
		}
		// TODO Poner la m�xima cantidad de datos por tabla si selecciona utilizar los datos ya almacenados o
		resp[0] = response+"";  //$NON-NLS-1$
		return resp;
	}
	public void generateData() throws ConnectionException, InstantException, GeneratorException, UnsupportedDataException, WriterException{
		filler = InstantiationRowGeneratorComponent.getInstance().getRowGeneratorForEngine(database.getInfo().getEngine());
		for(int i=0;i<tasks.size();i++){
			String msn = MessageBundleManager.getString("BusinessFacade.3")+tasks.get(i).getTable().getName(); //$NON-NLS-1$
			if(!tasks.get(i).needsCombinationGenerator())
				msn+=MessageBundleManager.getString("BusinessFacade.4"); //$NON-NLS-1$
			else
				msn+=MessageBundleManager.getString("BusinessFacade.5"); //$NON-NLS-1$
		}
		filler.Fill(tasks);
	}
	/**
	 * @return the actualGenerator
	 */
	public DataGenerator getActualGenerator() {
		return actualGenerator;
	}
	/**
	 * @param actualGenerator
	 *            the actualGenerator to set
	 */
	public void setActualGenerator(DataGenerator actualGenerator) {
		this.actualGenerator = actualGenerator;
	}
	public void setActualGeneratorPerName(String datagen) throws InstantException {
		this.setActualGenerator(getGeneratorPerName(datagen));
	}

	public DataGenerator getGeneratorPerName(String name) throws InstantException{
		return	InstantiationDataGeneratorComponent.getInstance().getGeneratorPerName(name);
	}
	/**
	 * @return the database
	 */
	public DatabaseVO getDatabase() {
		return database;
	}
	public boolean columnIsNullable(String tableName,String columnName) {
		int pos = getTasksIndex(tableName);
		return tasks.get(pos).getTable().getColumn(columnName).isNullable();
	}
	/**
	 * @return the filler
	 */
	public RowGenerator getFiller() {
		return filler;
	}
	/**
	 * @param filler
	 *            the filler to set
	 */
	public void setFiller(RowGenerator filler) {
		this.filler = filler;
	}	
	private int getTasksIndex(String tableName){
		int response = -1;
		for(int i=0;i<tasks.size() && response == -1;i++){
			if(tasks.get(i).getTable().getName().equals(tableName))
				response = i;
		}
		return response;
	}
	public HashMap<String, HashMap<String, Object[]>> getTableNColumnDetail() throws InstantException {
		tableNcolumnDetail = new HashMap<String, HashMap<String,Object[]>>();
		for(int i=0;i<tasks.size();i++){
			HashMap<String,Object[]> columnDetail = new HashMap<String, Object[]>();
			String tableName = tasks.get(i).getTable().getName();
			LinkedList<ColumnVO> columns = new LinkedList<ColumnVO>(tasks.get(i).getTable().getColumns().values());
			for(int j=0;j<columns.size();j++){
				String columnName = null;
				ColumnVO column = columns.get(j);
				if(!(column instanceof ForeignKeyVO) && !column.isAutoIncrement())
					columnName = column.getName();
				if(columnName != null){
					Object[] columnGenerators = getColumnGenerators(column.getType(),column.isUniqueKey());
					columnDetail.put(columnName,columnGenerators);
				}
			}
			tableNcolumnDetail.put(tableName,columnDetail);
		}
		return tableNcolumnDetail;
	}
	private Object[] getColumnGenerators(TypeVO type, boolean isUnique) throws InstantException {
		LinkedList<String> response = null;
		List<String> dataGenerators = null;
		dataGenerators = InstantiationDataGeneratorComponent.getInstance().getDataGeneratorsAvailable();
		response = new LinkedList<String>();
		for(int i=0;i<dataGenerators.size();i++){
			DataGenerator datagen = (DataGenerator) InstantiationDataGeneratorComponent.getInstance().InstantiateObject(dataGenerators.get(i));
			List<String> supportedTypes = datagen.getTypes();
			List<String> supportedUniqueTypes = datagen.getUniqueTypes();
			for(int j=0;j<supportedTypes.size();j++){
				String dataGenName = dataGenerators.get(i);
				if(supportedTypes.get(j).equals(TypesNames.DATE) && type.isDate() && !isUnique){
					if(!response.contains(dataGenName)){
						response.add(dataGenName);
					}
				}
				else if(supportedTypes.get(j).equals(TypesNames.DOUBLE) && type.isDouble()&& !isUnique){
					if(!response.contains(dataGenName)){
						response.add(dataGenName);
					}
				}
				else if(supportedTypes.get(j).equals(TypesNames.INT) && type.isInteger()&& !isUnique){
					if(!response.contains(dataGenName)){
						response.add(dataGenName);
					}
				}
				else if(supportedTypes.get(j).equals(TypesNames.STRING) && type.isString()&& !isUnique){
					if(!response.contains(dataGenName)){
						response.add(dataGenName);
					}
				}
				else if(supportedTypes.get(j).equals(TypesNames.FILE) && type.isFile()&& !isUnique){
					if(!response.contains(dataGenName)){
						response.add(dataGenName);
					}
				}
			}
			if(supportedUniqueTypes != null){
				for(int j=0;j<supportedUniqueTypes.size();j++){
					String dataGenName = dataGenerators.get(i);
					if(supportedUniqueTypes.get(j).equals(TypesNames.DATE) && type.isDate() && isUnique){
						if(!response.contains(dataGenName)){
							response.add(dataGenName);
						}
					}
					else if(supportedUniqueTypes.get(j).equals(TypesNames.DOUBLE) && type.isDouble()&& isUnique){
						if(!response.contains(dataGenName)){
							response.add(dataGenName);
						}
					}
					else if(supportedUniqueTypes.get(j).equals(TypesNames.INT) && type.isInteger()&& isUnique){
						if(!response.contains(dataGenName)){
							response.add(dataGenName);
						}
					}
					else if(supportedUniqueTypes.get(j).equals(TypesNames.STRING) && type.isString()&& isUnique){
						if(!response.contains(dataGenName)){
							response.add(dataGenName);
						}
					}
					else if(supportedUniqueTypes.get(j).equals(TypesNames.FILE) && type.isFile()&& isUnique){
						if(!response.contains(dataGenName)){
							response.add(dataGenName);
						}
					}
				}
			}
		}
		if(response.size()>0)
			response.addFirst(MessageBundleManager.getString("SELECT_ONE_LABEL")); //$NON-NLS-1$
		return response.toArray();
	}
	public List<Task> getTasks() {
		return tasks;
	}
	public void setUseStoredData(String table, boolean b) {
		int index = getTasksIndex(table);
		tasks.get(index).setUseStoredData(b);
	}
	public boolean doesntNeedGenerators(String tableName) {
		int pos = getTasksIndex(tableName);
		List<ColumnVO> columns = new ArrayList<ColumnVO>(tasks.get(pos).getTable().getColumns().values());
		int response = 0;
		for(int i=0;i<columns.size();i++)
			if(this.tableNcolumnDetail.get(tableName).containsKey(columns.get(i).getName())){
				response++;
			}
		return response==0;
	}

	public void showGenerators() {
//		for(int i=0;i<tasks.size();i++){
//			List<ColumnVO> columns = new ArrayList<ColumnVO>(tasks.get(i).getTable().getColumns().values());
//			for(int j=0;j<columns.size();j++)
//				if(columns.get(i) != null && columns.get(i).getGenerator() != null)
//				System.out.println(columns.get(j).getName()+"-->"+columns.get(j).getGenerator().getName());
//		}
	}

}
