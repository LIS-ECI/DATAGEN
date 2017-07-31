package edu.eci.pgr.view.swing;
import info.clearthought.layout.TableLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.eci.pgr.i18n.MessageBundleManager;
import edu.eci.pgr.view.Mediator;

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
public class ColorChooser extends JFrame {
	private static final long serialVersionUID = 1L;
	private JColorChooser colorChooser;
	private JButton cancelButton;
	private JButton selectButton;
	private JPanel buttonsPanel;
	private JLabel colorTitle;
	public ColorChooser(){
		super();
		initGUI();
	}
	private void initGUI() {
		{
			TableLayout thisLayout = new TableLayout(new double[][] {{7.0, TableLayout.FILL, 7.0}, {TableLayout.FILL, TableLayout.FILL, TableLayout.FILL, TableLayout.FILL, 21.0, 7.0}});
			thisLayout.setHGap(5);
			thisLayout.setVGap(5);
			getContentPane().setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(331, 300));
			this.setSize(500, 600);
			{
				colorTitle = new JLabel();
				this.getContentPane().add(colorTitle, "1, 0");
				colorTitle.setText("Select a background color");
				colorTitle.setFont(new java.awt.Font("Verdana",1,16));
				colorTitle.setHorizontalAlignment(SwingConstants.CENTER);
			}
			{
				colorChooser = new JColorChooser();
				colorChooser.getSelectionModel().addChangeListener(new ChangeListener(){
					public void stateChanged(ChangeEvent evt){
						colorSelectedAction();
					}
				});
				this.getContentPane().add(colorChooser, "1, 1, 1, 3");
			}
			{
				buttonsPanel = new JPanel();
				TableLayout buttonsPanelLayout = new TableLayout(new double[][] {{TableLayout.FILL, TableLayout.FILL, TableLayout.FILL}, {TableLayout.FILL}});
				buttonsPanelLayout.setHGap(5);
				buttonsPanelLayout.setVGap(5);
				buttonsPanel.setLayout(buttonsPanelLayout);
				getContentPane().add(buttonsPanel, "1, 4");
				{
					selectButton = new JButton();
					buttonsPanel.add(selectButton, "0, 0");
					selectButton.setText("Select color");
					selectButton.setFont(new java.awt.Font(MessageBundleManager.getString("DataBaseInformation.51"),0,9)); //$NON-NLS-1$
					selectButton.setBorder(BorderFactory.createCompoundBorder(
							new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null), 
							null));
					selectButton.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent evt){
							selectButtonAction();
						}
					});
				}
				{
					cancelButton = new JButton();
					buttonsPanel.add(cancelButton, "2, 0");
					cancelButton.setText("Cancel");
					cancelButton.setFont(new java.awt.Font(MessageBundleManager.getString("DataBaseInformation.51"),0,9)); //$NON-NLS-1$
					cancelButton.setBorder(BorderFactory.createCompoundBorder(
							new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null), 
							null));
					cancelButton.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent evt){
							cancelButtonAction();
						}
					});
				}
			}
		}
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	protected void selectButtonAction() {
		Mediator.getInstance().setBackGroundColor(colorChooser.getColor(),this);
	}
	protected void cancelButtonAction() {
		this.setVisible(false);
	}
	protected void colorSelectedAction() {
		this.getContentPane().setBackground(colorChooser.getColor());
	}
	
	public static void main(String[] args) {
		ColorChooser chooser = new ColorChooser();
		chooser.setVisible(true);
		chooser.setLocationRelativeTo(null);
	}
}
