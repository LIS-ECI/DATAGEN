package edu.eci.pgr.view.swing.model;

import javax.swing.table.AbstractTableModel;

public class TableDetailModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private String[] columnNames;
	private Object[][] data;
	
	public TableDetailModel() {
		super();
	}
	
	public TableDetailModel(Object[][] data,String[] columnNames) {
		super();
		this.columnNames = columnNames;
		this.data = data;
	}

	public int getColumnCount() {
		return columnNames.length;
	}
	public int getRowCount() {
		return data.length;
	}
	public String getColumnName(int col) {
		return columnNames[col];
	}
	public Object getValueAt(int row, int col) {
		return data[row][col];
	}
	@SuppressWarnings("unchecked")
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
	public boolean isCellEditable(int row, int col) {
		if (col == 0) {
			return false;
		} else {
			return true;
		}
	}
	public void setValueAt(Object value, int row, int col) {
		data[row][col] = value;
		fireTableCellUpdated(row, col);
	}
}
