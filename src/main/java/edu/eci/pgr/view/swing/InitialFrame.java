package edu.eci.pgr.view.swing;

import info.clearthought.layout.TableLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

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
public class InitialFrame extends ViewPanel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton newPlanButton;
	private JButton aboutUsButton;
	private JLabel aboutUsLabel;
	private JLabel newLabel;
	private JButton loadPlanButton;
	private JLabel editLabel;

	public InitialFrame() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			TableLayout thisLayout = new TableLayout(new double[][] {{7.0, TableLayout.FILL, TableLayout.FILL, 162.0, TableLayout.FILL, 7.0}, {36.0, 100.0, 100.0, 100.0, 30.0}});
			thisLayout.setHGap(5);
			thisLayout.setVGap(5);
			setLayout(thisLayout);
			setBackground(Mediator.getInstance().BACKGROUND_COLOR);
			{
				newPlanButton = new JButton();
				add(newPlanButton, "1, 1"); //$NON-NLS-1$
				newPlanButton.setIcon(new ImageIcon(ClassLoader.getSystemResource("newPlan.png"))); //$NON-NLS-1$
				newPlanButton.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
				newPlanButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent ae){
						newPlanAction();
					}
				});
			}
			{
				newLabel = new JLabel();
				add(newLabel, "2, 1, 3, 1"); //$NON-NLS-1$
				newLabel.setText(MessageBundleManager.getString("InitialFrame.0")); //$NON-NLS-1$
				newLabel.setHorizontalAlignment(SwingConstants.LEADING);
			}
			{
				loadPlanButton = new JButton();
				add(loadPlanButton, "1, 2"); //$NON-NLS-1$
				loadPlanButton.setIcon(new ImageIcon(ClassLoader.getSystemResource("editRunPlan.png"))); //$NON-NLS-1$
				loadPlanButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent ae){
						loadPlanAction();
					}
				});
			}
			{
				editLabel = new JLabel();
				add(editLabel, "2, 2, 3, 2"); //$NON-NLS-1$
				editLabel.setText(MessageBundleManager.getString("InitialFrame.1")); //$NON-NLS-1$
			}
			{
				aboutUsLabel = new JLabel();
				this.add(aboutUsLabel, "2, 3"); //$NON-NLS-1$
				aboutUsLabel.setText(MessageBundleManager.getString("InitialFrame.2")); //$NON-NLS-1$
			}
			{
				aboutUsButton = new JButton();
				this.add(aboutUsButton, "1, 3"); //$NON-NLS-1$
				aboutUsButton.setIcon(new ImageIcon(ClassLoader.getSystemResource("aboutUs.png"))); //$NON-NLS-1$
				aboutUsButton.setBackground(this.getBackground());
				aboutUsButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						Mediator.getInstance().aboutUsAction();
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadPlanAction() {
		Mediator.getInstance().loadPlanAction(this);	
	}
	
	private void newPlanAction() {
		Mediator.getInstance().newPlanAction();	
	}
	
	public void repaintGUI(){
		this.initGUI();
	}
}
