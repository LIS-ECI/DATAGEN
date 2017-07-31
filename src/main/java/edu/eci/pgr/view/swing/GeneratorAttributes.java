package edu.eci.pgr.view.swing;
import info.clearthought.layout.TableLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import edu.eci.pgr.i18n.MessageBundleManager;
import edu.eci.pgr.view.Mediator;
import edu.eci.pgr.view.ViewPanel;
import edu.eci.pgr.view.swing.panel.AttributesPanel;

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
public class GeneratorAttributes extends ViewPanel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton cancelButton;
	private JScrollPane panel;
	private JLabel titulo;
	private AttributesPanel jPanel;
	private JButton okButton;
	private String genName;
	private String column;


	public GeneratorAttributes(String genName, String Column) {
		super();
		this.genName = genName;
		this.column = Column;
		initGUI();
	}

	private void initGUI() {
		try {
			TableLayout thisLayout = new TableLayout(new double[][] {{7.0, TableLayout.FILL, TableLayout.FILL, 7.0, TableLayout.FILL, TableLayout.FILL, 7.0}, {7.0, 28.0, 7.0, TableLayout.FILL, 22.0, 7.0}});
			thisLayout.setHGap(5);
			thisLayout.setVGap(5);
			setLayout(thisLayout);
			this.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
			{
				cancelButton = new JButton();
				add(cancelButton, "4, 4"); //$NON-NLS-1$
				cancelButton.setText(MessageBundleManager.getString("GeneratorAttributes.0")); //$NON-NLS-1$
				cancelButton.setBounds(294, 373, 85, 21);
				cancelButton.setFont(new java.awt.Font("Verdana",0,11)); //$NON-NLS-1$
				cancelButton.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createBevelBorder(BevelBorder.RAISED), 
						null));
				cancelButton.setToolTipText(MessageBundleManager.getString("GeneratorAttributes.1")); //$NON-NLS-1$
				cancelButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						cancelButtonAction();
					}
				});
			}
			{

				{
					okButton = new JButton();
					okButton.setText(MessageBundleManager.getString("GeneratorAttributes.2")); //$NON-NLS-1$
					okButton.setBounds(129, 373, 85, 21);
					okButton.setFont(new java.awt.Font("Verdana",0,11)); //$NON-NLS-1$
					add(okButton, "2, 4"); //$NON-NLS-1$
					okButton.setBorder(BorderFactory.createCompoundBorder(
							BorderFactory.createBevelBorder(BevelBorder.RAISED), 
							null));
					okButton.setToolTipText(MessageBundleManager.getString("GeneratorAttributes.3")); //$NON-NLS-1$
					okButton.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							okButtonAction();
						}
					});
				}

				{
					panel = new JScrollPane();
					add(panel, "1, 3, 5, 3"); //$NON-NLS-1$
					panel.setBounds(13, 60, 473, 287);
					{
						jPanel = new AttributesPanel();
						panel.setViewportView(jPanel);
					}

				}
				{
					titulo = new JLabel();
					add(titulo, "1, 1, 5, 1"); //$NON-NLS-1$
					titulo.setText(MessageBundleManager.getString("GeneratorAttributes.4")+column+" with generator "+genName); //$NON-NLS-1$
					titulo.setBounds(19, 22, 467, 27);
					titulo.setFont(new java.awt.Font("Verdana",2,11)); //$NON-NLS-1$
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void cancelButtonAction() {
		Mediator.getInstance().cancelButtonAction();
	}

	private void okButtonAction() {
		Mediator.getInstance().okGenAttributesAction();
	}

	public void repaintGUI(){
		this.initGUI();
	}

}
