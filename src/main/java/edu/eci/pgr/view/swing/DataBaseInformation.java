package edu.eci.pgr.view.swing;

import info.clearthought.layout.TableLayout;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import edu.eci.pgr.i18n.MessageBundleManager;
import edu.eci.pgr.view.Mediator;
import edu.eci.pgr.view.ViewConstants;
import edu.eci.pgr.view.ViewPanel;

/**
 * This code was edited or generated using CloudGarden's Jigloo
 * SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class DataBaseInformation extends ViewPanel implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String VIEW_PASSWORD_MESSAGE = MessageBundleManager
			.getString("DataBaseInformation.0"); //$NON-NLS-1$
	private static final String HIDE_PASSWORD_MESSAGE = MessageBundleManager
			.getString("DataBaseInformation.1"); //$NON-NLS-1$

	protected HashMap<String, String[]> connectionInformation;

	private JPanel driverPane;
	private JPanel userPasswordPane;
	private JLabel connectionUrlTitle;
	private JPasswordField password;
	private JTextField visiblePassword;
	private JTextField userName;
	private JLabel passwordTitle;
	private JButton backButton;
	private JSeparator separator1;
	private JLabel savedPasswordAlert2;
	private JLabel savedPasswordAlert;
	private JButton connectButton;
	private JComboBox driverClassname;
	private JLabel driverClassNameTitle;
	private JButton viewPassword;
	private JCheckBox savePassword;
	private JLabel userNameTitle;
	private JLabel connectionConfigurationTitle;
	private JTextField serverAddress;
	private JTextField database;
	private JButton wizardButton;
	private JLabel databaseTitle;
	private JLabel serverAddressTitle;
	private JTextField connectionUrl;
	private JComboBox driverTemplate;
	private JLabel driverTemplateTitle;
	private JPanel finalPane;
	private JPanel wizardPane;
	private HashMap<String, String> connectionTemplates;

	public DataBaseInformation() {
		super();
		setValuesForConnectionUrls();
		initGUI();
	}

	private void setValuesForConnectionUrls() {
		connectionInformation = new HashMap<String, String[]>();
		for (int i = 0; i < Mediator.getInstance().getDatabaseTemplates().length; i++)
			connectionInformation.put(Mediator.getInstance()
					.getDatabaseTemplates()[i], new String[] {
					Mediator.getInstance().getDatabaseUrlConnection()[i],
					Mediator.getInstance().getDatabaseDriverClassName()[i] });
		connectionTemplates = new HashMap<String, String>();
		for (int i = 0; i < Mediator.getInstance().getDatabaseEngines().length; i++) {
			connectionTemplates.put(
					Mediator.getInstance().getDatabaseEngines()[i], Mediator
							.getInstance().getDatabaseTemplates()[i]);
		}
	}

	private void initGUI() {
		try {
			TableLayout thisLayout = new TableLayout(new double[][] {
					{ 7.0, TableLayout.FILL, TableLayout.FILL, 162.0,
							TableLayout.FILL, 7.0 },
					{ 22.0, 60.0, 102.0, 119.0, 29.0, 3.0, 22.0, 7.0 } });
			thisLayout.setHGap(5);
			thisLayout.setVGap(5);
			this.setLayout(thisLayout);
			setBackground(Mediator.getInstance().BACKGROUND_COLOR);
			{
				driverPane = new JPanel();
				TableLayout driverPaneLayout = new TableLayout(new double[][] {
						{ 105.0, TableLayout.FILL },
						{ TableLayout.FILL, TableLayout.FILL } });
				driverPaneLayout.setHGap(5);
				driverPaneLayout.setVGap(5);
				driverPane.setLayout(driverPaneLayout);
				this.add(driverPane, "1, 1, 4, 1"); //$NON-NLS-1$
				driverPane
						.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
				{
					connectionUrl = new JTextField();
					driverPane.add(connectionUrl, "1, 1"); //$NON-NLS-1$
					connectionUrl.setFont(new java.awt.Font("Verdana", 0, 11)); //$NON-NLS-1$
					connectionUrl.addFocusListener(new FocusAdapter() {
						public void focusGained(FocusEvent evt) {
							connectionUrlFocusGained(evt);
						}
					});
					String url = Mediator.getInstance().getUrl();
					if (url == null)
						url = Mediator.getInstance().getDatabaseUrlConnection()[0];
					connectionUrl.setText(url);
				}
				{
					driverTemplateTitle = new JLabel();
					driverPane.add(driverTemplateTitle, "0, 0"); //$NON-NLS-1$
					driverTemplateTitle.setFont(new java.awt.Font(
							"Verdana", 0, 11)); //$NON-NLS-1$
					driverTemplateTitle.setText(MessageBundleManager
							.getString("DataBaseInformation.2")); //$NON-NLS-1$
				}
				{
					connectionUrlTitle = new JLabel();
					driverPane.add(connectionUrlTitle, "0, 1"); //$NON-NLS-1$
					connectionUrlTitle.setFont(new java.awt.Font(
							"Verdana", 0, 11)); //$NON-NLS-1$
					connectionUrlTitle.setText(MessageBundleManager
							.getString("DataBaseInformation.3")); //$NON-NLS-1$
				}
				{
					ComboBoxModel driverTemplateModel = new DefaultComboBoxModel(
							Mediator.getInstance().getDatabaseTemplates());
					String engine = Mediator.getInstance().getEngine();
					driverTemplate = new JComboBox();
					driverPane.add(driverTemplate, "1, 0"); //$NON-NLS-1$
					driverTemplate.setModel(driverTemplateModel);
					driverTemplate.setFont(new java.awt.Font("Verdana", 0, 11)); //$NON-NLS-1$
					driverTemplate.setBackground(Color.WHITE);
					if (engine != null)
						driverTemplate.setSelectedItem(connectionTemplates
								.get(engine.toUpperCase()));
					driverTemplate.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							driverTemplateAction();
						}
					});
				}
			}
			{
				wizardPane = new JPanel();
				TableLayout wizardPaneLayout = new TableLayout(
						new double[][] {
								{ 7.0, 93.0, 285.0, TableLayout.FILL },
								{ 27.0, 28.0 } });
				wizardPaneLayout.setHGap(5);
				wizardPaneLayout.setVGap(5);
				wizardPane.setLayout(wizardPaneLayout);
				this.add(wizardPane, "1, 2, 4, 2"); //$NON-NLS-1$
				wizardPane
						.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
				wizardPane
						.setBorder(BorderFactory
								.createTitledBorder(
										null,
										MessageBundleManager
												.getString("DataBaseInformation.4"), TitledBorder.LEADING, TitledBorder.TOP, new java.awt.Font("Verdana", 0, 11))); //$NON-NLS-1$//$NON-NLS-2$
				{
					serverAddressTitle = new JLabel();
					wizardPane.add(serverAddressTitle, "1, 0"); //$NON-NLS-1$
					serverAddressTitle.setFont(new java.awt.Font(
							"Verdana", 0, 11)); //$NON-NLS-1$
					serverAddressTitle.setText(MessageBundleManager
							.getString("DataBaseInformation.5")); //$NON-NLS-1$
				}
				{
					databaseTitle = new JLabel();
					wizardPane.add(databaseTitle, "1, 1"); //$NON-NLS-1$
					databaseTitle.setFont(new java.awt.Font("Verdana", 0, 11)); //$NON-NLS-1$
					databaseTitle.setText(MessageBundleManager
							.getString("DataBaseInformation.6")); //$NON-NLS-1$
				}
				{
					wizardButton = new JButton();
					wizardPane.add(wizardButton, "3, 1"); //$NON-NLS-1$
					wizardButton.setFont(new java.awt.Font("Verdana", 0, 11)); //$NON-NLS-1$
					wizardButton.setBorder(BorderFactory.createCompoundBorder(
							new SoftBevelBorder(BevelBorder.RAISED, null, null,
									null, null), null));
					wizardButton.setText(MessageBundleManager
							.getString("DataBaseInformation.7")); //$NON-NLS-1$
					wizardButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							wizardButtonAction();
						}
					});
				}
				{
					database = new JTextField();
					wizardPane.add(database, "2, 1"); //$NON-NLS-1$
					database.addFocusListener(new FocusAdapter() {
						public void focusGained(FocusEvent evt) {
							databaseFocusGained(evt);
						}
					});
				}
				{
					serverAddress = new JTextField();
					wizardPane.add(serverAddress, "2, 0"); //$NON-NLS-1$
					serverAddress.addFocusListener(new FocusAdapter() {
						public void focusGained(FocusEvent evt) {
							serverAddressFocusGained(evt);
						}
					});
				}
			}
			{
				userPasswordPane = new JPanel();
				TableLayout userPasswordPaneLayout = new TableLayout(
						new double[][] {
								{ 72.0, 264.0, 105.0 },
								{ 23.0, 23.0, 23.0, TableLayout.FILL,
										TableLayout.FILL } });
				userPasswordPaneLayout.setHGap(5);
				userPasswordPaneLayout.setVGap(5);
				userPasswordPane.setLayout(userPasswordPaneLayout);
				this.add(userPasswordPane, "1, 3, 4, 3"); //$NON-NLS-1$
				userPasswordPane
						.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
				{
					userNameTitle = new JLabel();
					userPasswordPane.add(userNameTitle, "0, 0"); //$NON-NLS-1$
					userNameTitle.setFont(new java.awt.Font("Verdana", 0, 11)); //$NON-NLS-1$
					userNameTitle.setText(MessageBundleManager
							.getString("DataBaseInformation.8")); //$NON-NLS-1$
				}
				{
					passwordTitle = new JLabel();
					userPasswordPane.add(passwordTitle, "0, 1"); //$NON-NLS-1$
					passwordTitle.setFont(new java.awt.Font("Verdana", 0, 11)); //$NON-NLS-1$
					passwordTitle.setText(MessageBundleManager
							.getString("DataBaseInformation.9")); //$NON-NLS-1$
				}
				{
					userName = new JTextField();
					userPasswordPane.add(userName, "1, 0"); //$NON-NLS-1$
					userName.setFont(new java.awt.Font("Verdana", 0, 11)); //$NON-NLS-1$
					String value = Mediator.getInstance().getUser();
					if (value == null)
						value = null;
					userName.setText(value);
					userName.addFocusListener(new FocusAdapter() {
						public void focusGained(FocusEvent evt) {
							userNameFocusGained(evt);
						}
					});
				}
				{
					password = new JPasswordField();
					userPasswordPane.add(password, "1, 1"); //$NON-NLS-1$
					password.setFont(new java.awt.Font("Verdana", 0, 11)); //$NON-NLS-1$
					password.setVisible(true);
					String value = Mediator.getInstance().getPassword();
					if (value == null)
						value = null;
					password.setText(value);
					password.addFocusListener(new FocusAdapter() {
						public void focusGained(FocusEvent evt) {
							visiblePasswordFocusGained(evt);
						}
					});

					visiblePassword = new JTextField();
					userPasswordPane.add(visiblePassword, "1,1"); //$NON-NLS-1$
					visiblePassword
							.setFont(new java.awt.Font("Verdana", 0, 11)); //$NON-NLS-1$
					visiblePassword.setVisible(false);
					visiblePassword.setText(value);
					visiblePassword.addFocusListener(new FocusAdapter() {
						public void focusGained(FocusEvent evt) {
							visiblePasswordFocusGained(evt);
						}
					});
				}
				{
					savePassword = new JCheckBox();
					userPasswordPane.add(savePassword, "1, 2"); //$NON-NLS-1$
					savePassword.setFont(new java.awt.Font("Verdana", 0, 11)); //$NON-NLS-1$
					savePassword
							.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
					savePassword.setText(MessageBundleManager
							.getString("DataBaseInformation.20")); //$NON-NLS-1$
				}
				{
					viewPassword = new JButton();
					userPasswordPane.add(viewPassword, "2, 1"); //$NON-NLS-1$
					viewPassword.setText(VIEW_PASSWORD_MESSAGE);
					viewPassword.setFont(new java.awt.Font("Verdana", 0, 9)); //$NON-NLS-1$
					viewPassword.setBorder(BorderFactory.createCompoundBorder(
							new SoftBevelBorder(BevelBorder.RAISED, null, null,
									null, null), null));
					viewPassword.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							viewPasswordAction();
						}

					});
				}
				{
					savedPasswordAlert = new JLabel();
					userPasswordPane.add(savedPasswordAlert, "0, 3, 2, 3"); //$NON-NLS-1$
					savedPasswordAlert.setAutoscrolls(true);
					savedPasswordAlert
							.setHorizontalAlignment(SwingConstants.LEFT);
					savedPasswordAlert.setIcon(new ImageIcon(getClass()
							.getClassLoader().getResource("images/alert.png"))); //$NON-NLS-1$
					savedPasswordAlert.setFocusTraversalPolicyProvider(true);
					savedPasswordAlert.setFont(new java.awt.Font(
							"Verdana", 0, 10)); //$NON-NLS-1$
					savedPasswordAlert
							.setVerticalAlignment(SwingConstants.BOTTOM);
					savedPasswordAlert
							.setVerticalTextPosition(SwingConstants.BOTTOM);
					savedPasswordAlert.setText(MessageBundleManager
							.getString("DataBaseInformation.10")); //$NON-NLS-1$
				}
				{
					savedPasswordAlert2 = new JLabel();
					userPasswordPane.add(savedPasswordAlert2, "0, 4, 2, 4"); //$NON-NLS-1$
					savedPasswordAlert2
							.setHorizontalAlignment(SwingConstants.LEFT);
					savedPasswordAlert2.setAutoscrolls(true);
					savedPasswordAlert2.setFont(new java.awt.Font(
							"Verdana", 0, 10)); //$NON-NLS-1$
					savedPasswordAlert2
							.setVerticalAlignment(SwingConstants.TOP);
					savedPasswordAlert2
							.setVerticalTextPosition(SwingConstants.TOP);
					savedPasswordAlert2.setText(MessageBundleManager
							.getString("DataBaseInformation.11")); //$NON-NLS-1$
				}
			}
			{
				finalPane = new JPanel();
				TableLayout finalPaneLayout = new TableLayout(new double[][] {
						{ 109.0, TableLayout.FILL, TableLayout.FILL,
								TableLayout.FILL }, { 23.0 } });
				finalPaneLayout.setHGap(5);
				finalPaneLayout.setVGap(5);
				finalPane.setLayout(finalPaneLayout);
				this.add(finalPane, "1, 4, 4, 4"); //$NON-NLS-1$
				finalPane
						.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
				{
					driverClassNameTitle = new JLabel();
					finalPane.add(driverClassNameTitle, "0, 0"); //$NON-NLS-1$
					driverClassNameTitle.setFont(new java.awt.Font(
							"Verdana", 0, 11)); //$NON-NLS-1$
					driverClassNameTitle.setText(MessageBundleManager
							.getString("DataBaseInformation.12")); //$NON-NLS-1$
				}
				{
					ComboBoxModel driverClassnameModel = new DefaultComboBoxModel(
							Mediator.getInstance().getDatabaseDriverClassName());
					driverClassname = new JComboBox();
					finalPane.add(driverClassname, "1, 0, 3, 0"); //$NON-NLS-1$
					driverClassname.setModel(driverClassnameModel);
					driverClassname
							.setFont(new java.awt.Font("Verdana", 0, 11)); //$NON-NLS-1$
					driverClassname.setBackground(Color.WHITE);
					String selectedItem = MessageBundleManager
							.getString("DATABASE_DRIVER_CLASSNAME1"); //$NON-NLS-1$
					if (connectionInformation != null)
						selectedItem = connectionInformation
								.get((String) driverTemplate.getSelectedItem())[ViewConstants.DRIVER];
					driverClassname.setSelectedItem(selectedItem);
					driverClassname.setBorder(new LineBorder(
							new java.awt.Color(191, 191, 191), 1, false));
				}
			}
			{
				connectionConfigurationTitle = new JLabel();
				this.add(connectionConfigurationTitle, "1, 0, 2, 0"); //$NON-NLS-1$
				connectionConfigurationTitle.setFont(new java.awt.Font(
						"Verdana", 2, 12)); //$NON-NLS-1$
				connectionConfigurationTitle.setText(MessageBundleManager
						.getString("DataBaseInformation.13")); //$NON-NLS-1$
			}
			{
				connectButton = new JButton();
				this.add(connectButton, "4, 6"); //$NON-NLS-1$
				connectButton.setFont(new java.awt.Font("Verdana", 0, 11)); //$NON-NLS-1$
				connectButton.setBorder(BorderFactory.createCompoundBorder(
						new SoftBevelBorder(BevelBorder.RAISED, null, null,
								null, null), null));
				connectButton.setText(MessageBundleManager
						.getString("DataBaseInformation.14")); //$NON-NLS-1$
				connectButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						connectButtonAction();
					}

				});
			}
			{
				separator1 = new JSeparator();
				this.add(separator1, "0, 5, 5, 5"); //$NON-NLS-1$
				separator1
						.setBackground(Mediator.getInstance().BACKGROUND_COLOR);
				separator1.setForeground(new java.awt.Color(220, 220, 220));
			}
			{
				backButton = new JButton();
				this.add(backButton, "1, 6"); //$NON-NLS-1$
				backButton.setFont(new java.awt.Font("Verdana", 0, 11)); //$NON-NLS-1$
				backButton.setBorder(BorderFactory.createCompoundBorder(
						new SoftBevelBorder(BevelBorder.RAISED, null, null,
								null, null), null));
				backButton.setText(MessageBundleManager
						.getString("DataBaseInformation.15")); //$NON-NLS-1$
				backButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						backFromDataBaseInformation();
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void backFromDataBaseInformation() {
		Mediator.getInstance().fromDataBaseInformationToInitialFrame();
	}

	protected void connectButtonAction() {
		String username = userName.getText();
		String password = null;
		if (this.password.isVisible())
			password = String.valueOf(this.password.getPassword());
		else
			password = String.valueOf(this.visiblePassword.getText());
		String url = connectionUrl.getText();
		String driver = (String) driverClassname.getSelectedItem();
		String engine = getEngine((String) driverTemplate.getSelectedItem());
		boolean complete = true;
		if (url.contains(MessageBundleManager.getString("SERVER_ADDRESS"))) { //$NON-NLS-1$
			JOptionPane.showMessageDialog(null, MessageBundleManager
					.getString("DataBaseInformation.16")); //$NON-NLS-1$
			complete = false;
		}
		if (url.contains(MessageBundleManager.getString("DATABASE_NAME"))) { //$NON-NLS-1$
			JOptionPane.showMessageDialog(null, MessageBundleManager
					.getString("DataBaseInformation.17")); //$NON-NLS-1$
			complete = false;
		}
		if (username == null || username.equals("")) { //$NON-NLS-1$
			JOptionPane.showMessageDialog(null, MessageBundleManager
					.getString("DataBaseInformation.18")); //$NON-NLS-1$
			complete = false;
		}
		if (password == null || password.equals("")) { //$NON-NLS-1$
			JOptionPane.showMessageDialog(null, MessageBundleManager
					.getString("DataBaseInformation.19")); //$NON-NLS-1$
			complete = false;
		}
		if (complete)
			Mediator.getInstance().connectButtonAction(username, password, url,
					driver, engine, savePassword.isSelected());

	}

	private String getEngine(String selectedItem) {
		String engine = "";
		Iterator<String> keys = connectionTemplates.keySet().iterator();
		while (keys.hasNext()) {
			String val = keys.next();
			if (connectionTemplates.get(val).equals(selectedItem))
				engine = val;
		}
		return engine;
	}

	protected void viewPasswordAction() {
		if (viewPassword.getText().equals(VIEW_PASSWORD_MESSAGE)) {
			viewPassword.setText(HIDE_PASSWORD_MESSAGE); //$NON-NLS-1$
			password.setVisible(false);
			visiblePassword.setVisible(true);
			visiblePassword.setText(String.valueOf(password.getPassword()));
		} else {
			viewPassword.setText(VIEW_PASSWORD_MESSAGE);
			password.setVisible(true);
			visiblePassword.setVisible(false);
			password.setText(visiblePassword.getText());
		}
	}

	public void driverTemplateAction() {
		String url = connectionInformation.get((String) driverTemplate
				.getSelectedItem())[ViewConstants.URL];
		connectionUrl.setText(url);
		String classname = connectionInformation.get((String) driverTemplate
				.getSelectedItem())[ViewConstants.DRIVER];
		driverClassname.setSelectedItem(classname);
	}

	public void wizardButtonAction() {
		String url = connectionInformation.get((String) driverTemplate
				.getSelectedItem())[ViewConstants.URL];
		url = url
				.replace(
						MessageBundleManager.getString("SERVER_ADDRESS"), serverAddress.getText()); //$NON-NLS-1$
		url = url
				.replace(
						MessageBundleManager.getString("DATABASE_NAME"), database.getText()); //$NON-NLS-1$
		connectionUrl.setText(url);
		String passwordRecovered = Mediator.getInstance().recoverPassword(
				url + userName.getText());
		if (passwordRecovered != null)
			password.setText(passwordRecovered);
	}

	private void connectionUrlFocusGained(FocusEvent evt) {
		selectAllMethod(evt);
	}

	private void selectAllMethod(FocusEvent evt) {
		if (evt.getSource() instanceof JTextField) {
			JTextField text = (JTextField) evt.getSource();
			text.selectAll();
		} else if (evt.getSource() instanceof JPasswordField) {
			JPasswordField pass = (JPasswordField) evt.getSource();
			pass.selectAll();
		}
	}

	private void serverAddressFocusGained(FocusEvent evt) {
		selectAllMethod(evt);
	}

	private void databaseFocusGained(FocusEvent evt) {
		selectAllMethod(evt);
	}

	private void userNameFocusGained(FocusEvent evt) {
		selectAllMethod(evt);
	}

	private void visiblePasswordFocusGained(FocusEvent evt) {
		selectAllMethod(evt);
	}

	public void repaintGUI() {
		this.initGUI();
	}

}
