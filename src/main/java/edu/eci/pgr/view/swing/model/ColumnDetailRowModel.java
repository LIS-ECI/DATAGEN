package edu.eci.pgr.view.swing.model;

import javax.swing.table.*;
import java.util.*;
public class ColumnDetailRowModel
{
	private Hashtable<Object,Object> data;
	public ColumnDetailRowModel()
	{
		data = new Hashtable<Object, Object>();
	}
	public void addEditorForRow(int row, TableCellEditor e )
	{
		data.put(new Integer(row), e);
	}
	public void removeEditorForRow(int row)
	{
		data.remove(new Integer(row));
	}
	public TableCellEditor getEditor(int row)
	{
		return (TableCellEditor)data.get(new Integer(row));
	}
}
