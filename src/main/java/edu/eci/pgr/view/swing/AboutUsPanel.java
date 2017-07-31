package edu.eci.pgr.view.swing;
import info.clearthought.layout.TableLayout;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

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
public class AboutUsPanel extends ViewPanel {
	private static final long serialVersionUID = 1L;
	private JLabel aboutUsTitle;
	private JLabel created;
	private JButton ok;
	private JPanel buttonPanel;
	private JLabel image;
	private JTextArea hectorDesc;
	private JTextArea felipeDesc;
	private JTextArea beatrizDesc;
	private JLabel hectorCadavid;
	private JLabel felipeVillamil;
	private JLabel beatrizRojas;
	private JLabel versionReleased;
	private JLabel version;
	private JPanel picturesPanel;
	private JPanel infoPanel;

	public AboutUsPanel(){
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setBackground(Mediator.getInstance().BACKGROUND_COLOR);
			TableLayout thisLayout = new TableLayout(new double[][] {{7.0, TableLayout.FILL, 7.0}, {40.0, 7.0, 100.0, 7.0, 277.0, TableLayout.FILL, 7.0}});
			thisLayout.setHGap(5);
			thisLayout.setVGap(5);
			setLayout(thisLayout);
			{
				aboutUsTitle = new JLabel();
				add(aboutUsTitle, "1, 0"); //$NON-NLS-1$
				aboutUsTitle.setText(MessageBundleManager.getString("AboutUsPanel.2")); //$NON-NLS-1$
				aboutUsTitle.setFont(new java.awt.Font("Century Gothic",0,24)); //$NON-NLS-1$
				aboutUsTitle.setHorizontalAlignment(SwingConstants.CENTER);
			}
			{
				infoPanel = new JPanel();
				TableLayout infoPanelLayout = new TableLayout(new double[][] {{202.0, TableLayout.FILL}, {TableLayout.FILL, TableLayout.FILL, TableLayout.FILL}});
				infoPanelLayout.setHGap(5);
				infoPanelLayout.setVGap(5);
				infoPanel.setLayout(infoPanelLayout);
				infoPanel.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
				add(infoPanel, "1, 2"); //$NON-NLS-1$
				{
					version = new JLabel();
					infoPanel.add(version, "0, 0"); //$NON-NLS-1$
					version.setText(MessageBundleManager.getString("AboutUsPanel.0")); //$NON-NLS-1$
					version.setFont(new Font("Verdana",0,12)); //$NON-NLS-1$
				}
				{
					versionReleased = new JLabel();
					infoPanel.add(versionReleased, "0, 1"); //$NON-NLS-1$
					versionReleased.setText(MessageBundleManager.getString("AboutUsPanel.3")); //$NON-NLS-1$
					versionReleased.setFont(new java.awt.Font("Verdana",0,12)); //$NON-NLS-1$
				}
				{
					created = new JLabel();
					infoPanel.add(created, "0, 2"); //$NON-NLS-1$
					created.setText(MessageBundleManager.getString("AboutUsPanel.4")); //$NON-NLS-1$
					created.setFont(new java.awt.Font("Verdana",0,12)); //$NON-NLS-1$
				}
				{
					image = new JLabel();
					infoPanel.add(image, "1, 0, 1, 2"); //$NON-NLS-1$
					image.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/name.png"))); //$NON-NLS-1$
				}
			}
			{
				picturesPanel = new JPanel();
				TableLayout picturesPanelLayout = new TableLayout(new double[][] {{TableLayout.FILL, TableLayout.FILL}, {TableLayout.FILL, TableLayout.FILL, TableLayout.FILL}});
				picturesPanelLayout.setHGap(5);
				picturesPanelLayout.setVGap(5);
				picturesPanel.setLayout(picturesPanelLayout);
				picturesPanel.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
				add(picturesPanel, "1, 4"); //$NON-NLS-1$
				{
					beatrizRojas = new JLabel();
					picturesPanel.add(beatrizRojas, "0, 0"); //$NON-NLS-1$
					beatrizRojas.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/BeatrizRojas.jpg"))); //$NON-NLS-1$
					beatrizRojas.setHorizontalAlignment(SwingConstants.CENTER);
				}
				{
					felipeVillamil = new JLabel();
					picturesPanel.add(felipeVillamil, "0, 1"); //$NON-NLS-1$
					felipeVillamil.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/FelipeVillamil.jpg"))); //$NON-NLS-1$
					felipeVillamil.setHorizontalAlignment(SwingConstants.CENTER);
				}
				{
					hectorCadavid = new JLabel();
					picturesPanel.add(hectorCadavid, "0, 2"); //$NON-NLS-1$
					hectorCadavid.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/HectorCadavid.jpg"))); //$NON-NLS-1$
					hectorCadavid.setHorizontalAlignment(SwingConstants.CENTER);
				}
				{
					beatrizDesc = new JTextArea();
					picturesPanel.add(beatrizDesc, "1, 0"); //$NON-NLS-1$
					beatrizDesc.setText(MessageBundleManager.getString("AboutUsPanel.5")); //$NON-NLS-1$
					beatrizDesc.setFocusable(false);
					beatrizDesc.setRequestFocusEnabled(false);
					beatrizDesc.setEditable(false);
					beatrizDesc.setLineWrap(true);
					beatrizDesc.setBackground(getBackground());
					beatrizDesc.setFont(new java.awt.Font("Century Gothic",0,12)); //$NON-NLS-1$
				}
				{
					felipeDesc = new JTextArea();
					picturesPanel.add(felipeDesc, "1, 1"); //$NON-NLS-1$
					felipeDesc.setText(MessageBundleManager.getString("AboutUsPanel.6")); //$NON-NLS-1$
					felipeDesc.setFocusable(false);
					felipeDesc.setRequestFocusEnabled(false);
					felipeDesc.setEditable(false);
					felipeDesc.setLineWrap(true);
					felipeDesc.setBackground(getBackground());
					felipeDesc.setFont(new java.awt.Font("Century Gothic",0,12)); //$NON-NLS-1$
				}
				{
					hectorDesc = new JTextArea();
					picturesPanel.add(hectorDesc, "1, 2"); //$NON-NLS-1$
					hectorDesc.setText(MessageBundleManager.getString("AboutUsPanel.7")); //$NON-NLS-1$
					hectorDesc.setFocusable(false);
					hectorDesc.setRequestFocusEnabled(false);
					hectorDesc.setLineWrap(true);
					hectorDesc.setEditable(false);
					hectorDesc.setBackground(getBackground());
					hectorDesc.setFont(new java.awt.Font("Century Gothic",0,12)); //$NON-NLS-1$
				}
			}
			{
				buttonPanel = new JPanel();
				TableLayout buttonPanelLayout = new TableLayout(new double[][] {{TableLayout.FILL, TableLayout.FILL, TableLayout.FILL}, {TableLayout.FILL}});
				buttonPanelLayout.setHGap(5);
				buttonPanelLayout.setVGap(5);
				buttonPanel.setLayout(buttonPanelLayout);
				buttonPanel.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
				add(buttonPanel, "1, 5"); //$NON-NLS-1$
				{
					ok = new JButton();
					buttonPanel.add(ok, "2, 0"); //$NON-NLS-1$
					ok.setText(MessageBundleManager.getString("AboutUsPanel.8")); //$NON-NLS-1$
					ok.setFont(new java.awt.Font("Verdana",0,11)); //$NON-NLS-1$
					ok.setBorder(BorderFactory.createCompoundBorder(
							new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null), 
							null));
					ok.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent evt){
							Mediator.getInstance().closeAboutUsPanel();
						}
					});
				}
			}
			{
				this.setSize(410, 520);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void repaintGUI(){
		this.initGUI();
	}
}
