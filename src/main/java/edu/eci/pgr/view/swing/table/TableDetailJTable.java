package edu.eci.pgr.view.swing.table;

import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

import edu.eci.pgr.view.swing.model.ColumnDetailRowModel;

public class TableDetailJTable extends JTable {
	private static final long serialVersionUID = 1L;
	private ColumnDetailRowModel rm;
	public TableDetailJTable(){
		super();
		rm = null;
	}
	public TableDetailJTable(TableModel tm){
		super(tm);
		rm = null;
	}

	public TableDetailJTable(int rows, int cols){
		super(rows,cols);
		rm = null;
	}
	public TableDetailJTable(final Vector<Object> rowData, final Vector<Object> columnNames){
		super(rowData, columnNames);
		rm = null;
	}
	public TableDetailJTable(final Object[][] rowData, final Object[] colNames){
		super(rowData, colNames);
		rm = null;
	}
	public TableDetailJTable(TableModel tm, ColumnDetailRowModel rm){
		super(tm,null,null);
		this.rm = rm;
	}
	public void setRowEditorModel(ColumnDetailRowModel rm){
		this.rm = rm;
	}
	public ColumnDetailRowModel getTableDetailRowEditorModel(){
		return rm;
	}
	public TableCellEditor getCellEditor(int row, int col){
		TableCellEditor tmpEditor = null;
		if (rm!=null)
			tmpEditor = rm.getEditor(row);
		if (tmpEditor!=null)
			return tmpEditor;
		return super.getCellEditor(row,col);
	}

}
