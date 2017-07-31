package edu.eci.pgr.view.swing;
import info.clearthought.layout.TableLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import edu.eci.pgr.i18n.MessageBundleManager;
import edu.eci.pgr.view.Mediator;
import edu.eci.pgr.view.ViewPanel;
import edu.eci.pgr.view.swing.panel.GeneratorsPanel;


/**
 * This code was edited or generated using CloudGarden's Jigloo
 * SWT/Swing GUI Builder, which is free for non-commercial
 * use. If Jigloo is being used commercially (ie, by a corporation,
 * company or business for any purpose whatever) then you
 * should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details.
 * Use of Jigloo implies acceptance of these licensing terms.
 * A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
 * THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
 * LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class GeneratorChooser extends ViewPanel implements Serializable{
	private static final long serialVersionUID = 1L;
	private JPanel selectButtonPanel;
	private GeneratorsPanel generatorsPanel;
	private JScrollPane generatorspane;
	private JButton doneConfigurationButton;
	private JLabel columnsListTitle;
	private JButton back;

	public GeneratorChooser() {
		super();
		initGUI();
	}

	private void initGUI() {
		try {
			TableLayout thisLayout = new TableLayout(new double[][] {{7.0, TableLayout.FILL, 396.0, TableLayout.FILL, 7.0}, {23.0, TableLayout.FILL, 19.0, 7.0}});
			thisLayout.setHGap(5);
			thisLayout.setVGap(5);
			this.setLayout(thisLayout);
			setBackground(Mediator.getInstance().BACKGROUND_COLOR);
			{
				{
					columnsListTitle = new JLabel();
					this.add(columnsListTitle, "1, 0, 2, 0"); //$NON-NLS-1$
					columnsListTitle.setText(MessageBundleManager.getString("TABLE_LIST_TITLE")+Mediator.getInstance().getSelectedTable());
					columnsListTitle.setFont(new java.awt.Font("Verdana",2,12)); //$NON-NLS-1$

				}
				{
					selectButtonPanel = new JPanel();
					TableLayout selectButtonPanelLayout = new TableLayout(new double[][] {{45.0, 80.0, TableLayout.FILL, 80.0, 45.0}, {TableLayout.FILL}});
					selectButtonPanelLayout.setHGap(5);
					selectButtonPanelLayout.setVGap(5);
					selectButtonPanel.setLayout(selectButtonPanelLayout);
					this.add(selectButtonPanel, "2, 2"); //$NON-NLS-1$
					selectButtonPanel.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
					{
						doneConfigurationButton = new JButton();
						selectButtonPanel.add(doneConfigurationButton, "1, 0"); //$NON-NLS-1$
						doneConfigurationButton.setText(MessageBundleManager.getString("GeneratorChooser.0")); //$NON-NLS-1$

						doneConfigurationButton.setFont(new java.awt.Font("Verdana",0,11)); //$NON-NLS-1$
						doneConfigurationButton.setBorder(BorderFactory.createCompoundBorder(
								new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null), 
								null));
						doneConfigurationButton.setToolTipText(MessageBundleManager.getString("GeneratorChooser.1")); //$NON-NLS-1$
						doneConfigurationButton.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent e) {
								doneButtonAction();
							}
						});
					}
					{
						back = new JButton();
						selectButtonPanel.add(back, "3, 0"); //$NON-NLS-1$
						back.setText(MessageBundleManager.getString("GeneratorChooser.2")); //$NON-NLS-1$
						back.setFont(new java.awt.Font("Verdana",0,11)); //$NON-NLS-1$
						back.setBorder(BorderFactory.createCompoundBorder(
								new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null), 
								null));
						back.setToolTipText(MessageBundleManager.getString("GeneratorChooser.3")); //$NON-NLS-1$
						back.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent e) {
								backButtonAction();
							}
						});
					}
				}
				{
					{
						generatorspane = new JScrollPane();
						this.add(generatorspane, "1, 1, 3, 1"); //$NON-NLS-1$
						generatorspane.setBounds(12, 57, 581, 300);
						generatorspane.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
						{
							generatorsPanel = new GeneratorsPanel();
							generatorspane.setViewportView(generatorsPanel);
						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void backButtonAction() {
		Mediator.getInstance().fromTableDetailToTablesList();
	}

	protected void doneButtonAction() {
		Mediator.getInstance().doneConfigurationAction();
	}


	/**
	 * @return the jPanel
	 */
	public GeneratorsPanel getJPanel() {
		return generatorsPanel;
	}

	/**
	 * @param panel the jPanel to set
	 */
	public void setJPanel(GeneratorsPanel panel) {
		generatorsPanel = panel;
	}

	public void repaintGUI(){
		this.initGUI();
	}

}
