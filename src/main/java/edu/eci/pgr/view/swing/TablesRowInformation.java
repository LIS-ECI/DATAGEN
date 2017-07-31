package edu.eci.pgr.view.swing;

import info.clearthought.layout.TableLayout;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import edu.eci.pgr.i18n.MessageBundleManager;
import edu.eci.pgr.utilities.ValidationUtilities;
import edu.eci.pgr.view.Mediator;
import edu.eci.pgr.view.ViewPanel;
import edu.eci.pgr.view.swing.cellEditor.CustomCellEditor;
import edu.eci.pgr.view.swing.model.TableListModel;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class TablesRowInformation extends ViewPanel implements Serializable {
	private static final long serialVersionUID = 1L;

	private JLabel tablesListTitle;
	private JPanel selectButtonPanel;
	private JScrollPane tableListScrollPane;
	private JButton back;
	private JButton generateDataButton;
	private JTable tableList;

	public TablesRowInformation() {
		super();
		initGUI();
	}

	private void initGUI() {
		try {
			TableLayout thisLayout = new TableLayout(new double[][] {
					{ 7.0, TableLayout.FILL, 396.0, TableLayout.FILL, 7.0 },
					{ 29.0, 7.0, TableLayout.FILL, 3.0, 22.0, 7.0 } });
			thisLayout.setHGap(5);
			thisLayout.setVGap(5);
			this.setLayout(thisLayout);
			setBackground(Mediator.getInstance().BACKGROUND_COLOR);
			{
				{
					tablesListTitle = new JLabel();
					this.add(tablesListTitle, "1, 0, 2, 0"); //$NON-NLS-1$
					tablesListTitle
							.setFont(new java.awt.Font("Verdana", 2, 12)); //$NON-NLS-1$
					tablesListTitle.setText(MessageBundleManager.getString("TablesRowInformation.0")); //$NON-NLS-1$

				}
				{
					selectButtonPanel = new JPanel();
					TableLayout selectButtonPanelLayout = new TableLayout(
							new double[][] {
									{ TableLayout.FILL, 7.0, TableLayout.FILL,
											7.0, TableLayout.FILL },
									{ TableLayout.FILL } });
					selectButtonPanelLayout.setHGap(5);
					selectButtonPanelLayout.setVGap(5);
					selectButtonPanel.setLayout(selectButtonPanelLayout);
					this.add(selectButtonPanel, "2, 4"); //$NON-NLS-1$
					selectButtonPanel.setBackground(Mediator.getInstance()
							.BACKGROUND_COLOR);
					{
						generateDataButton = new JButton();
						selectButtonPanel.add(generateDataButton, "0, 0"); //$NON-NLS-1$
						generateDataButton.setText(MessageBundleManager.getString("TablesRowInformation.1")); //$NON-NLS-1$
						generateDataButton.setFont(new java.awt.Font(
								"Verdana", 0, 11)); //$NON-NLS-1$
						generateDataButton.setBorder(BorderFactory
								.createCompoundBorder(new SoftBevelBorder(
										BevelBorder.RAISED, null, null, null,
										null), null));
						generateDataButton.setToolTipText(MessageBundleManager.getString("TablesRowInformation.2")); //$NON-NLS-1$
						generateDataButton
								.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										generateDataButtonAction();
									}
								});
					}
					{
						back = new JButton();
						selectButtonPanel.add(back, "4, 0"); //$NON-NLS-1$
						back.setFont(new java.awt.Font("Verdana", 0, 11)); //$NON-NLS-1$
						back.setBorder(BorderFactory.createCompoundBorder(
								new SoftBevelBorder(BevelBorder.RAISED, null,
										null, null, null), null));
						back.setText(MessageBundleManager.getString("TablesRowInformation.3")); //$NON-NLS-1$
						back.setToolTipText(MessageBundleManager.getString("TablesRowInformation.4")); //$NON-NLS-1$
						back.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								backButtonAction();
							}
						});
					}
				}
				{
					tableListScrollPane = new JScrollPane();
					this.add(tableListScrollPane, "2, 2"); //$NON-NLS-1$
					tableListScrollPane.setToolTipText(MessageBundleManager.getString("TablesRowInformation.5")); //$NON-NLS-1$
					tableListScrollPane.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
					{
						TableListModel tableListModel = new TableListModel(
								Mediator.getInstance().getTablesRowColumns(),
								Mediator.getInstance().getTableRowList(), 1);
						tableListModel
								.addTableModelListener(new TableModelListener() {
									public void tableChanged(TableModelEvent e) {
										tableListModelAction(e);
									}
								});
						tableList = new JTable();
						tableList.setFont(new Font("Verdana", 0, 11)); //$NON-NLS-1$
						tableList.setBorder(BorderFactory.createCompoundBorder(
								new SoftBevelBorder(BevelBorder.RAISED, null,
										null, null, null), null));
						tableList.setDefaultEditor(Object.class,
								new CustomCellEditor());
						tableList.setFillsViewportHeight(true);
						tableListScrollPane.setViewportView(tableList);
						tableList.setPreferredSize(new java.awt.Dimension(394,
								0));
						tableList.setModel(tableListModel);
						tableList.addFocusListener(new FocusAdapter() {
							public void focusLost(FocusEvent e) {
							}
						});
					}
				}
				UIManager.setLookAndFeel(UIManager
						.getSystemLookAndFeelClassName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void backButtonAction() {
		Mediator.getInstance().fromTableRowsToTableListInformation();
	}

	protected void tableListModelAction(TableModelEvent e) {
		int row = e.getLastRow();
		int col = e.getColumn();
		if (col == 1 && e.getSource() instanceof TableListModel) {
			String tableName = (String) ((TableListModel) e.getSource())
					.getValueAt(row, 0);
			String value = (String) ((TableListModel) e.getSource())
					.getValueAt(row, col);
			String[] maximumVals = Mediator.getInstance()
					.getMaximumRowsForTable(
							(String) ((TableListModel) e.getSource())
									.getValueAt(row, 0));
			long max = Long.parseLong(maximumVals[0]);
			if (!ValidationUtilities.isNumeric(value)) {
				JOptionPane.showMessageDialog(null, "El volúmen de datos debe ser numérico"); //$NON-NLS-1$
				((TableListModel) e.getSource()).setValueAt("" + 0, row, 1); //$NON-NLS-1$
			} else {
				if (Long.parseLong(value) > max) {
					JOptionPane
							.showMessageDialog(
									null,
									max == 0 ? " debe configurar "+ maximumVals[2] + //$NON-NLS-1$
											" antes de " + tableName + "porque esta tiene referencias a " //$NON-NLS-1$ //$NON-NLS-2$
											: " Las filas a generar en la tabla " + tableName + " debe ser menor a " + max + "\n" + maximumVals[1] + //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
													" porque: " + maximumVals[2] + " no puede generar " + value + " filas "); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					((TableListModel) e.getSource()).setValueAt("" + 0, row, 1); //$NON-NLS-1$
				} else if (Long.parseLong(value) < 0) {
					JOptionPane.showMessageDialog(null," la cantidad de filas a generar debe ser mayor que " + 0); //$NON-NLS-1$
					((TableListModel) e.getSource()).setValueAt("" + 0, row, 1); //$NON-NLS-1$
				} else {
					Mediator.getInstance().rowsToGenerateAction(value,
							tableName);
				}
			}
		}
	}

	protected void generateDataButtonAction() {
		boolean allow = true;
		TableListModel tableModel = (TableListModel) tableList.getModel();
		for (int i = 0; i < tableModel.getRowCount() && allow; i++) {
			String tableName = (String) (tableModel).getValueAt(i, 0);
			String value = (String) (tableModel).getValueAt(i, 1);
			String[] maximumVals = Mediator.getInstance()
					.getMaximumRowsForTable(
							(String) (tableModel).getValueAt(i, 0));
			long max = Long.parseLong(maximumVals[0]);
			if (!ValidationUtilities.isNumeric(value)) {
				JOptionPane
						.showMessageDialog(
								null,
								" La cantidad de filas a generar en la tabla " + value + " debe ser un valor entero "); //$NON-NLS-1$ //$NON-NLS-2$
				(tableModel).setValueAt("" + 0, i, 1); //$NON-NLS-1$
				allow = false;
			} else {
				if (Long.parseLong(value) > max) {
					JOptionPane
							.showMessageDialog(
									null,
									max == 0 ? " debe ser configurada " + maximumVals[2] + //$NON-NLS-1$
											" antes de " + tableName + " porque tiene referencias a " //$NON-NLS-1$ //$NON-NLS-2$
											: " Las filas a generar en la tabla " + tableName + " debe ser menor a " + max + "\n" + maximumVals[1] + //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
													" porque " + maximumVals[2] + " no puede generar " + value + " filas "); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					(tableModel).setValueAt("" + 0, i, 1); //$NON-NLS-1$
					allow = false;
				} else if (Long.parseLong(value) < 0) {
					JOptionPane
							.showMessageDialog(
									null,
									" Las filas a generar en la tabla " + value + " deben ser mayor que " + 0); //$NON-NLS-1$ //$NON-NLS-2$
					(tableModel).setValueAt("" + 0, i, 1); //$NON-NLS-1$
					allow = false;
				} else {
					Mediator.getInstance().rowsToGenerateAction(value,
							tableName);
				}
			}
		}
		if (allow) {
			Mediator.getInstance().generateDataButtonAction();
		}
	}

	protected void selectButtonAction() {
		int i = tableList.getSelectedRow();
		Mediator.getInstance().selectButtonAction(i);
	}

	public void repaintGUI(){
		this.initGUI();
	}
}
