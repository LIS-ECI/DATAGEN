package edu.eci.pgr.view.swing;
import info.clearthought.layout.TableLayout;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

import edu.eci.pgr.i18n.MessageBundleManager;
import edu.eci.pgr.view.Mediator;
import edu.eci.pgr.view.ViewPanel;

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
public class ProgressFrame extends ViewPanel implements Observer,Serializable {
	private static final long serialVersionUID = 1L;
	private static final int MAX = 255;
	private static final int R = 0;
	private static final int G = 1;
	private static final int B = 2;
	private static final int DELTA = 3;
	private JProgressBar progressBar;
	private JScrollPane infoPane;
	private JButton doneButton;
	private JPanel doneButtonPanel;
	private JLabel infoLabel;
	private JTextArea warningMessages;
    private int rgb[];
	public ProgressFrame() {
		super();
		initGUI();
	}
	private void initGUI() {
		try {
	        rgb = new int[3];
	        rgb[R] = 255;
	        rgb[G] = 0;
	        rgb[B] = 0;
	        this.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
			{
				TableLayout thisLayout = new TableLayout(new double[][] {{7.0, TableLayout.FILL, 7.0}, {16.0, 22.0, 7.0, 229.0, 19.0, 7.0}});
				thisLayout.setHGap(5);
				thisLayout.setVGap(5);
				setLayout(thisLayout);
			}
			{
				progressBar = new JProgressBar();
				progressBar.setStringPainted(true);
				progressBar.setToolTipText(MessageBundleManager.getString("ProgressFrame.0")); //$NON-NLS-1$
				progressBar.setValue(0);
				add(progressBar, "1, 1"); //$NON-NLS-1$
			}
			{
				infoPane = new JScrollPane();
				{
					warningMessages = new JTextArea();
					infoPane.setViewportView(warningMessages);
					warningMessages.setBounds(49, 236, 280, 126);
					warningMessages.setEditable(false);
					warningMessages.setToolTipText(MessageBundleManager.getString("ProgressFrame.1")); //$NON-NLS-1$
				}
				add(infoPane, "1, 3"); //$NON-NLS-1$
			}
			{
				infoLabel = new JLabel();
				infoLabel.setText(MessageBundleManager.getString("ProgressFrame.2")); //$NON-NLS-1$
				add(infoLabel, "1, 0"); //$NON-NLS-1$
			}
			{
				doneButtonPanel = new JPanel();
				TableLayout jPanel2Layout = new TableLayout(new double[][] {{TableLayout.FILL, TableLayout.FILL, TableLayout.FILL}, {TableLayout.FILL}});
				jPanel2Layout.setHGap(5);
				jPanel2Layout.setVGap(5);
				doneButtonPanel.setLayout(jPanel2Layout);
				doneButtonPanel.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
				add(doneButtonPanel, "1, 4"); //$NON-NLS-1$
				{
					doneButton = new JButton();
					doneButtonPanel.add(doneButton, "1, 0"); //$NON-NLS-1$
					doneButton.setText(MessageBundleManager.getString("ProgressFrame.3")); //$NON-NLS-1$
					doneButton.setBounds(50, 373, 85, 21);
					doneButton.setFont(new java.awt.Font("Verdana",0,11)); //$NON-NLS-1$
					doneButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED),null));
					doneButton.setEnabled(false);
					doneButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							rowGenerationFinished();
						}
					});
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void rowGenerationFinished() {
		JOptionPane.showMessageDialog(null,MessageBundleManager.getString("ProgressFrame.4")); //$NON-NLS-1$
		System.exit(0);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Double){
			Integer progressValue = ((Double)arg).intValue();
			progressBar.setValue(progressValue);
	    	nextColor();
			Color c = getHexadecimalColor();
            progressBar.setForeground(c);
			if(progressBar.getValue() == 100){
				doneButton.setEnabled(true);
				Mediator.getInstance().activateButtons();
				JOptionPane.showMessageDialog(null,MessageBundleManager.getString("ProgressFrame.5")); //$NON-NLS-1$
			}
		}
		else if (arg instanceof String){
			this.warningMessages.setText((String) arg);
		}
			
	}
    private Color getHexadecimalColor() {
    	return new Color(rgb[R],rgb[G],rgb[B]);
	}

	public void nextColor() {
		if(rgb[R] == MAX && rgb[B] == 0 && rgb[G]!= MAX)
			rgb[G] += DELTA;
		else if(rgb[G] == MAX && rgb[B] == 0 && rgb[R] != 0)
			rgb[R] -=DELTA;
		else if(rgb[R] == 0 && rgb[G] == MAX && rgb[B] != MAX)
			rgb[B] +=DELTA;
		else if(rgb[R] == 0 && rgb[B] == MAX && rgb[G] != 0)
			rgb[G] -=DELTA;
		else if(rgb[G] == 0 && rgb[B] == MAX && rgb[R] != MAX)
			rgb[R] +=DELTA;
		else if(rgb[R] == MAX && rgb[G] == 0 && rgb[B] != 0)
			rgb[B] -=DELTA;
	}

	/**
	 * @return the warningMessages
	 */
	public JTextArea getWarningMessages() {
		return warningMessages;
	}

	/**
	 * @param warningMessages the warningMessages to set
	 */
	public void setWarningMessages(JTextArea warningMessages) {
		this.warningMessages = warningMessages;
	}
	
	public void repaintGUI(){
		this.initGUI();
	}

}
