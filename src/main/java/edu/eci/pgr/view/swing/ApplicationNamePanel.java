package edu.eci.pgr.view.swing;

import info.clearthought.layout.TableLayout;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.eci.pgr.i18n.MessageBundleManager;
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
public class ApplicationNamePanel extends ViewPanel{
	private static final long serialVersionUID = 1L;
	private JLabel mainTitle;
	private JLabel subtitle;
	private JLabel image;
	private JPanel titlePanel;
	public ApplicationNamePanel(){
		initGUI();
	}
	private void initGUI() {
		ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("images/name.png"));
		TableLayout thisLayout = new TableLayout(new double[][] {{icon.getIconWidth(), TableLayout.FILL}, {TableLayout.FILL}});
		thisLayout.setHGap(5);
		thisLayout.setVGap(5);
		setLayout(thisLayout);
		this.setBackground(Color.WHITE);
		{
			image = new JLabel();
			add(image, "0, 0");
			image.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/name.png")));
		}
		{
			titlePanel = new JPanel();
			TableLayout titlePanelLayout = new TableLayout(new double[][] {{TableLayout.FILL}, {7.0, TableLayout.FILL, TableLayout.FILL, 7.0}});
			titlePanelLayout.setHGap(5);
			titlePanelLayout.setVGap(5);
			titlePanel.setLayout(titlePanelLayout);
			add(titlePanel, "1, 0");
			{
				mainTitle = new JLabel();
				titlePanel.add(mainTitle, "0, 1");
				titlePanel.setBackground(Color.WHITE);
				mainTitle.setFont(new java.awt.Font("Century Gothic",Font.PLAIN,35));
				mainTitle.setHorizontalAlignment(SwingConstants.CENTER);
				mainTitle.setVerticalAlignment(SwingConstants.BOTTOM);
				mainTitle.setText(MessageBundleManager.getString("APPLICATION_TITLE"));
			}
			{
				subtitle = new JLabel();
				titlePanel.add(subtitle, "0, 2");
				subtitle.setFont(new java.awt.Font("Century Gothic",Font.ITALIC,14));
				subtitle.setHorizontalAlignment(SwingConstants.CENTER);
				subtitle.setVerticalAlignment(SwingConstants.CENTER);
				subtitle.setText(MessageBundleManager.getString("APPLICATION_DESCRIPTION"));
			}
		}
	}
	public void repaintGUI(){
		this.initGUI();
	}
}
