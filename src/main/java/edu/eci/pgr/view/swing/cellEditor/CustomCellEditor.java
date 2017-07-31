package edu.eci.pgr.view.swing.cellEditor;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;

public class CustomCellEditor extends DefaultCellEditor {
	private static final long serialVersionUID = 1L;
	public CustomCellEditor() {
		super(new JTextField());
	}
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
			int row, int column) {
		JTextField editor = (JTextField) super.getTableCellEditorComponent(table, value, isSelected,
				row, column);
		editor.selectAll();
		return editor;
	}
}
