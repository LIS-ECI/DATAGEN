/**

 * 
 */
package edu.eci.pgr.view.swing;

import info.clearthought.layout.TableLayout;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import edu.eci.pgr.i18n.MessageBundleManager;
import edu.eci.pgr.view.Mediator;
import edu.eci.pgr.view.ViewPanel;

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
/**
 * @author Administrador
 * 
 */
public class FileOptionsPanel extends ViewPanel {
	private static final long serialVersionUID = 1L;
	private JPanel newPanel;
	private JPanel openPanel;
	private JPanel fontPanel;
	private JLabel languageTitle;
	private JComboBox languageSelection;
	private JPanel selectLanguagePanel;
	private JButton fontColorButton;
	private JLabel fontLabel;
	private JButton colorButton;
	private JLabel color;
	private JPanel changeColorPanel;
	private JButton aboutUsButton;
	private JLabel aboutUsTitle;
	private JPanel aboutUsPanel;
	private JButton saveAsButton;
	private JLabel saveAsLabel;
	private JPanel saveAsPanel;
	private JButton saveButton;
	private JLabel saveLabel;
	private JPanel savePanel;
	private JButton openButton;
	private JLabel openLabel;
	private JButton newButton;
	private JLabel newLabel;
	private boolean activateButtons;
	public FileOptionsPanel(){
		super();
		initGUI();
	}
	private void initGUI() {
		activateButtons = true;
		TableLayout thisLayout = new TableLayout(new double[][] {{TableLayout.FILL, TableLayout.FILL, TableLayout.FILL, TableLayout.FILL, TableLayout.FILL}, {25.0, 25.0}});
		thisLayout.setHGap(5);
		thisLayout.setVGap(5);
		this.setLayout(thisLayout);
		this.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
		{
			newPanel = new JPanel();
			TableLayout newPanelLayout = new TableLayout(new double[][] {{TableLayout.FILL, TableLayout.FILL}, {TableLayout.FILL}});
			newPanelLayout.setHGap(5);
			newPanelLayout.setVGap(5);
			newPanel.setLayout(newPanelLayout);
			newPanel.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
			this.add(newPanel, "0, 0");  //$NON-NLS-1$
			{
				newLabel = new JLabel();
				newPanel.add(newLabel, "0, 0");  //$NON-NLS-1$
				newLabel.setText(MessageBundleManager.getString("FileOptionsPanel.0"));  //$NON-NLS-1$
				newLabel.setFont(new java.awt.Font("Century Gothic",0,10));  //$NON-NLS-1$
				newLabel.setHorizontalAlignment(SwingConstants.RIGHT); 
			}
			{
				newButton = new JButton();
				newPanel.add(newButton, "1, 0");  //$NON-NLS-1$
				newButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/new.png")));  //$NON-NLS-1$
				newButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						Mediator.getInstance().newPlanAction();
					}
				});
				newButton.setBorder(null);
				newButton.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
				newButton.setEnabled(activateButtons);
			}
		}
		{
			openPanel = new JPanel();
			this.add(openPanel, "1, 0");  //$NON-NLS-1$
			TableLayout jPanel1Layout = new TableLayout(new double[][] {{TableLayout.FILL, TableLayout.FILL}, {TableLayout.FILL}});
			jPanel1Layout.setHGap(5);
			jPanel1Layout.setVGap(5);
			openPanel.setLayout(jPanel1Layout);
			openPanel.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
			{
				openLabel = new JLabel();
				openPanel.add(openLabel, "0, 0");  //$NON-NLS-1$
				openLabel.setText(MessageBundleManager.getString("FileOptionsPanel.1"));  //$NON-NLS-1$
				openLabel.setFont(new java.awt.Font("Century Gothic",0,10)); //$NON-NLS-1$
				openLabel.setHorizontalAlignment(SwingConstants.RIGHT); 
			}
			{
				openButton = new JButton();
				openPanel.add(openButton, "1, 0");  //$NON-NLS-1$
				openButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/open.png")));  //$NON-NLS-1$
				openButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent act){
						Mediator.getInstance().loadPlanAction(null);
					}
				});
				openButton.setBorder(null);
				openButton.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
				openButton.setEnabled(activateButtons);
			}
		}
		{
			savePanel = new JPanel();
			this.add(savePanel, "2, 0");  //$NON-NLS-1$
			TableLayout jPanel2Layout = new TableLayout(new double[][] {{TableLayout.FILL, TableLayout.FILL}, {TableLayout.FILL}});
			jPanel2Layout.setHGap(5);
			jPanel2Layout.setVGap(5);
			savePanel.setLayout(jPanel2Layout);
			savePanel.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
			{
				saveLabel = new JLabel();
				savePanel.add(saveLabel, "0, 0");  //$NON-NLS-1$
				saveLabel.setText(MessageBundleManager.getString("FileOptionsPanel.2"));  
				saveLabel.setHorizontalAlignment(SwingConstants.RIGHT);
				saveLabel.setFont(new Font("Century Gothic",0,10));  //$NON-NLS-1$
			}
			{
				saveButton = new JButton();
				savePanel.add(saveButton, "1, 0");  //$NON-NLS-1$
				saveButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/save.png")));  //$NON-NLS-1$
				saveButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						Mediator.getInstance().savePlanAction();
					}
				});
				saveButton.setBorder(null);
				saveButton.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
				saveButton.setEnabled(activateButtons);
			}
		}
		{
			saveAsPanel = new JPanel();
			this.add(saveAsPanel, "3, 0");  //$NON-NLS-1$
			TableLayout jPanel3Layout = new TableLayout(new double[][] {{TableLayout.FILL, TableLayout.FILL}, {TableLayout.FILL}});
			jPanel3Layout.setHGap(5);
			jPanel3Layout.setVGap(5);
			saveAsPanel.setLayout(jPanel3Layout);
			saveAsPanel.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
			{
				saveAsLabel = new JLabel();
				saveAsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
				saveAsLabel.setText(MessageBundleManager.getString("FileOptionsPanel.3"));  
				saveAsLabel.setFont(new Font("Century Gothic",0,10)); //$NON-NLS-1$
				saveAsPanel.add(saveAsLabel,"0,0");
			}
			{
				saveAsButton = new JButton();
				saveAsPanel.add(saveAsButton, "1, 0");  //$NON-NLS-1$
				saveAsButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/saveAs.png")));  //$NON-NLS-1$
				saveAsButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						Mediator.getInstance().saveAsPlanAction();
					}
				});
				saveAsButton.setBorder(null);
				saveAsButton.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
				saveAsButton.setEnabled(activateButtons);
			}
		}
		{
			aboutUsPanel = new JPanel();
			TableLayout aboutUsPanelLayout = new TableLayout(new double[][] {{TableLayout.FILL, TableLayout.FILL}, {TableLayout.FILL}});
			aboutUsPanelLayout.setHGap(5);
			aboutUsPanelLayout.setVGap(5);
			aboutUsPanel.setLayout(aboutUsPanelLayout);
			aboutUsPanel.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
			{
				this.add(aboutUsPanel, "4, 0");  //$NON-NLS-1$
				aboutUsTitle = new JLabel();
				aboutUsPanel.add(aboutUsTitle, "0, 0");  //$NON-NLS-1$
				aboutUsTitle.setFont(new java.awt.Font("Century Gothic",0,10));  //$NON-NLS-1$
				aboutUsTitle.setHorizontalAlignment(SwingConstants.TRAILING);
				aboutUsTitle.setText(MessageBundleManager.getString("FileOptionsPanel.4"));  //$NON-NLS-1$
			}
			{
				aboutUsButton = new JButton();
				aboutUsPanel.add(aboutUsButton, "1, 0");  //$NON-NLS-1$
				aboutUsButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/about.png")));  //$NON-NLS-1$
				aboutUsButton.setBorder(null);
				aboutUsButton.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
				aboutUsButton.setEnabled(activateButtons);
				aboutUsButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						Mediator.getInstance().aboutUsAction();
					}
				});
			}
		}
		{
			changeColorPanel = new JPanel();
			changeColorPanel.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
			changeColorPanel.setLayout(new TableLayout(new double[][]{{TableLayout.FILL,TableLayout.FILL},{TableLayout.FILL}}));
			this.add(changeColorPanel, "3, 1"); //$NON-NLS-1$
			{
				color = new JLabel();
				changeColorPanel.add(color,"0,0");
				color.setText(MessageBundleManager.getString("FileOptionsPanel.5"));  //$NON-NLS-1$
				color.setFont(new java.awt.Font("Century Gothic",0,10));  //$NON-NLS-1$
				color.setHorizontalAlignment(SwingConstants.RIGHT);
				color.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
			}
			{
				colorButton = new JButton();
				changeColorPanel.add(colorButton, "1, 0"); //$NON-NLS-1$
				colorButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/template-icon.png"))); //$NON-NLS-1$
				colorButton.setBorder(null);
				colorButton.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
				colorButton.setEnabled(activateButtons);
				colorButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						Mediator.getInstance().setBackGroundColor();
					}
				});
			}
		}
		{
			fontPanel = new JPanel();
			TableLayout fontPanelLayout = new TableLayout(new double[][] {{TableLayout.FILL, TableLayout.FILL}, {TableLayout.FILL}});
			fontPanelLayout.setHGap(5);
			fontPanelLayout.setVGap(5);
			fontPanel.setLayout(fontPanelLayout);
			fontPanel.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
			this.add(fontPanel, "4, 1"); //$NON-NLS-1$
			{
				fontLabel = new JLabel();
				fontPanel.add(fontLabel, "0, 0"); //$NON-NLS-1$
				fontLabel.setText(MessageBundleManager.getString("FileOptionsPanel.6")); //$NON-NLS-1$
				fontLabel.setFont(new java.awt.Font("Century Gothic",0,10)); //$NON-NLS-1$
				fontLabel.setHorizontalAlignment(SwingConstants.RIGHT);
				fontLabel.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
			}
			{
				fontColorButton = new JButton();
				fontPanel.add(fontColorButton, "1, 0"); //$NON-NLS-1$
				fontColorButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/font.png"))); //$NON-NLS-1$
				fontColorButton.setBorder(null);
				fontColorButton.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
				fontColorButton.setEnabled(activateButtons);
			}
		}
		{
			selectLanguagePanel = new JPanel();
			TableLayout selectLanguagePanelLayout = new TableLayout(new double[][] {{TableLayout.FILL}, {TableLayout.FILL}});
			selectLanguagePanelLayout.setHGap(5);
			selectLanguagePanelLayout.setVGap(5);
			selectLanguagePanel.setLayout(selectLanguagePanelLayout);
			selectLanguagePanel.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
			this.add(selectLanguagePanel, "1, 1"); //$NON-NLS-1$
			{
				ComboBoxModel languageSelectionModel = 
					new DefaultComboBoxModel(Mediator.LANGUAGES);
				languageSelection = new JComboBox();
				selectLanguagePanel.add(languageSelection, "0, 0"); //$NON-NLS-1$
				languageSelection.setModel(languageSelectionModel);
				languageSelection.setBackground(new java.awt.Color(255,255,255));
				languageSelection.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				languageSelection.setFont(new java.awt.Font("Century Gothic",0,10)); //$NON-NLS-1$
				languageSelection.setOpaque(false);
				languageSelection.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent act) {
						languageSelectionAction(act);
					}
				});
			}
		}
		{
			languageTitle = new JLabel();
			this.add(languageTitle, "0, 1"); //$NON-NLS-1$
			languageTitle.setText(MessageBundleManager.getString("FileOptionsPanel.7")); //$NON-NLS-1$
			languageTitle.setFont(new java.awt.Font("Century Gothic",0,10)); //$NON-NLS-1$
			languageTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void activateButtons(){
		newButton.setEnabled(true);
		openButton.setEnabled(true);
		saveButton.setEnabled(true);
		saveAsButton.setEnabled(true);
		aboutUsButton.setEnabled(true);
		fontColorButton.setEnabled(true);
		colorButton.setEnabled(true);
	}
	public void deactivateButtons(){
		newButton.setEnabled(false);
		openButton.setEnabled(false);
		saveButton.setEnabled(false);
		saveAsButton.setEnabled(false);
		aboutUsButton.setEnabled(false);
		fontColorButton.setEnabled(false);
		colorButton.setEnabled(false);
	}

	private void languageSelectionAction(ActionEvent act) {
		String language = (String)languageSelection.getSelectedItem();
		Mediator.getInstance().setMessageBundle(language);
		Object [] revertLanguage = orderLanguages(Mediator.LANGUAGES,language);
		languageSelection.setModel(new DefaultComboBoxModel(revertLanguage));
	}

	private Object[] orderLanguages(String[] languages,String language) {
		List<String> result = new ArrayList<String>();
		result.add(language);
		for(int i=0;i<languages.length;i++){
			if(!languages[i].equals(language))
				result.add(languages[i]);
		}
		return result.toArray();
	}
	public void repaintGUI(){
		this.initGUI();
	}
}
