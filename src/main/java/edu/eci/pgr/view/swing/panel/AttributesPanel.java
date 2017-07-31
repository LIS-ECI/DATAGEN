package edu.eci.pgr.view.swing.panel;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.eci.pgr.view.Mediator;

public class AttributesPanel extends javax.swing.JPanel {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField[] fields;
	private JTextArea[] labels;
	private static final int JLABEL_WIDTH=250;
	private static final int WIDTH = 150;
	private static final int JLABEL_HEIGHT=35;
	private static final int HEIGHT=20;
	private static final int FACTOR=50;
	private static final int OTHER_FACTOR = 70;
	private static final int ANOTHER_FACTOR = 10;

	public AttributesPanel() {
		super();
		int numAttribs= Mediator.getInstance().getAttributesOfGenerator().length;
		this.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
		try {
			setLayout(null);
			labels= new JTextArea[numAttribs];
			fields= new JTextField[numAttribs];
			for (int j = 0; j <numAttribs ; j++) {
				{
					labels[j] = new JTextArea();
					this.add(labels[j]);
					labels[j].setText(Mediator.getInstance().getNNameAttribute(j));
					labels[j].setBounds(ANOTHER_FACTOR, (j+1)*FACTOR, JLABEL_WIDTH, JLABEL_HEIGHT);
					labels[j].setFocusable(false);
					labels[j].setRequestFocusEnabled(false);
					labels[j].setEditable(false);
					labels[j].setLineWrap(true);
					labels[j].setWrapStyleWord(true);
					labels[j].setFont(new java.awt.Font("Verdana",0,9));
					labels[j].setBackground(this.getBackground());
				}
				{
					fields[j] = new JTextField();
					fields[j].setBounds(JLABEL_WIDTH+OTHER_FACTOR, (j+1)*FACTOR, WIDTH, HEIGHT);
					fields[j].setName(""+j);
					fields[j].addFocusListener(new FocusListener(){
						public void focusGained(FocusEvent e) {
						}
						public void focusLost(FocusEvent e) {
							attributeFocusLost(e);
						}
					});
					this.add(fields[j]);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	protected void attributeFocusLost(FocusEvent e) {
		if(e.getSource() instanceof JTextField){
			JTextField field = (JTextField)e.getSource();
			int position = Integer.parseInt(field.getName());
			String value = field.getText();
			String attribute = labels[position].getText();
			if(value != null && !value.equals("")){
				boolean response = Mediator.getInstance().addAttribute(attribute, value);
				if(!response)
					field.setText("");
			}
		}
	}
	/**
	 * @return the jTextFields
	 */
	public JTextField[] getJTextFields() {
		return fields;
	}
	/**
	 * @param textFields the jTextFields to set
	 */
	public void setJTextFields(JTextField[] textFields) {
		fields = textFields;
	}
	/**
	 * @return the labels
	 */
	public JTextArea[] getLabels() {
		return labels;
	}
	/**
	 * @param labels the labels to set
	 */
	public void setLabels(JTextArea[] labels) {
		this.labels = labels;
	}
	

}
