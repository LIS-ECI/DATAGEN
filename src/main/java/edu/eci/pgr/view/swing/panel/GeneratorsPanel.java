package edu.eci.pgr.view.swing.panel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Hashtable;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import edu.eci.pgr.business.controller.BusinessFacade;
import edu.eci.pgr.exceptions.InstantException;
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
public class GeneratorsPanel extends javax.swing.JPanel {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField[] nullablePercents;
	private JLabel[] columnNames;
	/** table <columnName,combobox>  **/
	private Hashtable<String,JComboBox> generatorOptions;
	private static final int WIDTH=70;
	private static final int HEIGHT=20;
	private static final int FACTOR=30;
	private static final int COMBO_WIDTH=200;
	public static final String NA = "N/A";
	private JLabel labelNull;
	private JLabel labelGenerator;
	private JLabel labelColumnName;

	
	public GeneratorsPanel() {
		super();
		int numGens= Mediator.getInstance().getTotalColumnCount();
		try {
			this.setLayout(null);
			this.setPreferredSize(new java.awt.Dimension(2*WIDTH+2*COMBO_WIDTH+(3*FACTOR),numGens*(HEIGHT+FACTOR/2)));
			this.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
			{
				labelColumnName = new JLabel();
				this.add(labelColumnName);
				labelColumnName.setText(Mediator.getInstance().getTableDetailColumns()[0]);
				labelColumnName.setBounds(0, 0,2*WIDTH , HEIGHT);
			}
			{
				labelGenerator = new JLabel();
				this.add(labelGenerator);
				labelGenerator.setText(Mediator.getInstance().getTableDetailColumns()[1]);
				labelGenerator.setBounds(2*WIDTH+FACTOR, 0,COMBO_WIDTH , HEIGHT);
			}
			{
				labelNull = new JLabel();
				this.add(labelNull);
				labelNull.setText(Mediator.getInstance().getTableDetailColumns()[2]);
				labelNull.setBounds(4*WIDTH+2*FACTOR, 0, COMBO_WIDTH, HEIGHT);
			}
			columnNames= new JLabel[numGens];
			nullablePercents= new JTextField[numGens];
			generatorOptions = new Hashtable<String, JComboBox>();
			for (int j = 0; j <numGens ; j++) {
				String columnName = Mediator.getInstance().getColumnName(j);
				{
					columnNames[j] = new JLabel();
					this.add(columnNames[j]);
					columnNames[j].setText(columnName);
					columnNames[j].setBounds(0, (j+1)*FACTOR, 2*WIDTH, HEIGHT);
					
				}
				{
					generatorOptions.put (columnNames[j].getText(), new JComboBox(Mediator.getInstance().getGeneratorsFor(columnName)));
					generatorOptions.get(columnNames[j].getText()).setBounds(2*WIDTH+FACTOR, (j+1)*FACTOR, COMBO_WIDTH, HEIGHT);
					generatorOptions.get(columnNames[j].getText()).setFont(new java.awt.Font("Verdana",0,11));
					generatorOptions.get(columnNames[j].getText()).setBackground(Color.WHITE);
					generatorOptions.get(columnNames[j].getText()).setBorder(new LineBorder(new java.awt.Color(191,191,191), 1, false));
					generatorOptions.get(columnNames[j].getText()).setSelectedItem(Mediator.getInstance().getSelectedIndexFor(columnName));
					generatorOptions.get(columnNames[j].getText()).setName(columnNames[j].getText());	
					this.add(generatorOptions.get(columnNames[j].getText()));
					generatorOptions.get(columnNames[j].getText()).addActionListener(new ActionListener (){
						public void actionPerformed(ActionEvent a){
							selectedComboBoxAction(a);
						}
					});
				}
				{
					nullablePercents[j] = new JTextField();
					nullablePercents[j].setBounds(2*WIDTH+2*FACTOR+COMBO_WIDTH, (j+1)*FACTOR, WIDTH, HEIGHT);
					nullablePercents[j].setName(columnNames[j].getText());
					nullablePercents[j].setText(Mediator.getInstance().getNullableValue(j));
					nullablePercents[j].setEnabled(Mediator.getInstance().columnIsNull(columnNames[j].getText()));
					if(!nullablePercents[j].isEnabled())nullablePercents[j].setText(NA);
					this.add(nullablePercents[j]);
					nullablePercents[j].addFocusListener(new FocusListener(){
						public void focusGained(FocusEvent e) {
							focusGainedNull(e);
						}
						public void focusLost(FocusEvent e) {
							validateNullablePercentae(e);
						}
					});
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void focusGainedNull(FocusEvent e) {
		if(e.getSource() instanceof JTextField){
			JTextField tfield = (JTextField)e.getSource();
			tfield.selectAll();
		}
	}

	protected void validateNullablePercentae(FocusEvent e) {
		if(e.getSource() instanceof JTextField){
			JTextField nullable = (JTextField)e.getSource();
			if (!nullable.getText().equals("")) {
				try {
					double value = Double.parseDouble(nullable.getText());
					if(value<0){JOptionPane.showMessageDialog(null, "The nullable percentage must be a positive value");nullable.setText("");};
					if(value>100){JOptionPane.showMessageDialog(null, "The nullable percentage must be less than 100");nullable.setText("");};
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "The nullable percentage must be a double value");
					nullable.setText("");
				}
			}
		}
	}

	/**
	 * @return the nullablePercents
	 */
	public JTextField[] getNullablePercents() {
		return nullablePercents;
	}

	/**
	 * @param nullablePercents the nullablePercents to set
	 */
	public void setNullablePercents(JTextField[] nullablePercents) {
		this.nullablePercents = nullablePercents;
	}

	/**
	 * @return the columnNames
	 */
	public JLabel[] getColumnNames() {
		return columnNames;
	}

	/**
	 * @param columnNames the columnNames to set
	 */
	public void setColumnNames(JLabel[] columnNames) {
		this.columnNames = columnNames;
	}

	/**
	 * @return the generatorOptions
	 */
	public Hashtable<String, JComboBox> getGeneratorOptions() {
		return generatorOptions;
	}

	/**
	 * @param generatorOptions the generatorOptions to set
	 */
	public void setGeneratorOptions(Hashtable<String, JComboBox> generatorOptions) {
		this.generatorOptions = generatorOptions;
	}
	
	protected void actionComboBox() {
		Mediator.getInstance().comboBoxAction();
	}
	
	private void selectedComboBoxAction(ActionEvent a) {
		if(a.getSource() instanceof JComboBox){
			String selectedItem=(String) ((JComboBox)a.getSource()).getSelectedItem();
			if(!selectedItem.equals(MessageBundleManager.getString("SELECT_ONE_LABEL"))){
				Mediator.getInstance().setActualGeneratorName(selectedItem,((JComboBox)a.getSource()).getName());
				try {
					BusinessFacade.getInstance().setActualGeneratorPerName(selectedItem);
				} catch (InstantException e) {
					// unhandled exception for internal management
					e.printStackTrace();
				}
				actionComboBox();
			}else Mediator.getInstance().setActualGeneratorName(null,((JComboBox)a.getSource()).getName());
		}
	}

}