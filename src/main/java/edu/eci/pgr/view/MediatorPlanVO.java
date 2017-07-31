package edu.eci.pgr.view;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import edu.eci.pgr.business.controller.BusinessFacade;
import edu.eci.pgr.exceptions.InstantException;
import edu.eci.pgr.i18n.MessageBundleManager;

public class MediatorPlanVO implements Serializable{
	private static final String INFORMATION_MESSAGE = MessageBundleManager.getString("MediatorPlanVO.0"); //$NON-NLS-1$

	private static final String REASON = MessageBundleManager.getString("MediatorPlanVO.1"); //$NON-NLS-1$

	private static final String STORED_DATA = MessageBundleManager.getString("MediatorPlanVO.2"); //$NON-NLS-1$

	private static final String TABLE = MessageBundleManager.getString("MediatorPlanVO.3"); //$NON-NLS-1$

	private static final long serialVersionUID = 1L;

	private HashMap<String,String> engineMap;
	private HashMap<String, HashMap<String, Object[]>> tableNcolumnDetail;
	private Object[] selectedTableNames;
	private Object[][] tableListValues;
	private String selectedTable;
	private boolean tableListInitialized;
	private String actualGeneratorName;
	private String selectedColumn;
	private File fileName;
	public MediatorPlanVO() {
		super();
	}
	public String getColumnName(int i) {
		return (String)selectedTableNames[i];
	}
	public Object[] getGeneratorsFor(String columnName) {
		return (tableNcolumnDetail.get(selectedTable)).get(columnName);
	}
	public void setSelectedTable(String selectedValue) {
		selectedTable = selectedValue;
		selectedTableNames = tableNcolumnDetail.get(selectedValue).keySet().toArray();
	}
	public Object[][] getColumnNames() {
		Object [][] response = new Object[getTotalColumnCount()][3];
		for(int i=0;i<getTotalColumnCount();i++){
			String columnName = (String) selectedTableNames[i];
			response[i][0] = columnName;
			response[i][1] = getCurrentGenerator(columnName);
			response[i][2] = ""; //$NON-NLS-1$
		}
		return response;
	}
	private String getCurrentGenerator(String columnName) {
		String generator = BusinessFacade.getInstance().getGenerator(selectedTable, columnName);
		if(generator != null)
			return generator;
		return MessageBundleManager.getString("SELECT_ONE_LABEL"); //$NON-NLS-1$
	}
	public int getTotalColumnCount() {
		return tableNcolumnDetail.get(selectedTable).size();
	}
	public Object[][] getTableList() {
		if(!tableListInitialized)
			initializeTableList();
		return tableListValues;
	}
	private void initializeTableList() {
		tableListValues = new Object[tableNcolumnDetail.keySet().size()][3];
		Iterator<String> keyset = tableNcolumnDetail.keySet().iterator();
		List<String> values = new ArrayList<String>();
		while(keyset.hasNext()){
			values.add(keyset.next());
		}
		Collections.sort(values);
		for(int i=0;i<values.size();i++){
			String tableName = values.get(i);
			tableListValues[i][0] = tableName;
			tableListValues[i][1] = new Boolean(BusinessFacade.getInstance().doesntNeedGenerators(tableName));
			tableListValues[i][2] = Boolean.FALSE;
		}
		tableListInitialized = true;
	}
	public void setDoneConfigurationSelectedTable() {
		int i = getSelectedTableIndex();
		tableListValues[i][1] = new Boolean(true);
	}
	private int getSelectedTableIndex() {
		int response = -1;
		for(int i=0;i<tableListValues.length && response == -1;i++)
			if(((String)tableListValues[i][0]).equals(selectedTable))
				response = i;
		return response;
	}
	public void validateExistingValues() {
		String msn = ""; //$NON-NLS-1$
		Iterator<String> tables = tableNcolumnDetail.keySet().iterator();
		while(tables.hasNext()){
			String table = tables.next();
			if(BusinessFacade.getInstance().hasStoredData(table))
				msn+=TABLE+" "+table+" "+STORED_DATA+"\n";
		}
		if(!msn.equals("")){ //$NON-NLS-1$
			msn+="\n"+REASON;
			JOptionPane.showMessageDialog(null, msn,INFORMATION_MESSAGE,JOptionPane.WARNING_MESSAGE);
		}
	}
	public File getFileName() {
		return fileName;
	}
	public void setFileName(File fileName) {
		this.fileName = fileName;
	}
	public void setTableListInitialized(boolean b) {
		tableListInitialized = b;
	}
	public void setTableNcolumnDetail() throws InstantException {
		tableNcolumnDetail = BusinessFacade.getInstance().getTableNColumnDetail();
	}
	public String getTableName(int i) {
		return (String)tableListValues[i][0];
	}
	public String getSelectedTable() {
		return selectedTable;
	}
	public HashMap<String, HashMap<String, Object[]>> getTableNcolumnDetail() {
		return tableNcolumnDetail;
	}
	public void setActualGeneratorName(String name) {
		actualGeneratorName = name;
	}
	public String getActualGeneratorName() {
		return actualGeneratorName;
	}
	public String getSelectedColumn() {
		return selectedColumn;
	}
	public boolean isTableConfigured(int i) {
		return (Boolean)tableListValues[i][1];
	}
	public void setSelectedColumn(String selectedColumn2) {
		selectedColumn = selectedColumn2;
	}
	public void setEngineMap(HashMap<String, String> hashMap) {
		this.engineMap = hashMap;
	}
	public HashMap<String,String> getEngineMap() {
		return engineMap;
	}
	
}
