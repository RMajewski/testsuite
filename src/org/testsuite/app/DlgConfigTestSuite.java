/* 
* Copyright 2016 René Majewski
*  
* Lizenziert unter der EUPL, Version 1.1 oder - sobald diese von der
* Europäischen Kommission genehmigt wurden - Folgeversionen der EUPL
* ("Lizenz"); Sie dürfen dieses Werk ausschließlich gemäß dieser Lizenz
* nutzen. 
* 
* Eine Kopie der Lizenz finden Sie hier: 
* https://joinup.ec.europa.eu/software/page/eupl
*  
* Sofern nicht durch anwendbare Rechtsvorschriften gefordert oder in 
* schriftlicher Form vereinbart, wird die unter der Lizenz verbreitete 
* Software "so wie sie ist", OHNE JEGLICHE GEWÄHRLEISTUNG ODER BEDINGUNGEN -
* ausdrücklich oder stillschweigend - verbreitet.
* Die sprachspezifischen Genehmigungen und Beschränkungen unter der Lizenz
* sind dem Lizenztext zu entnehmen.
*/ 

package org.testsuite.app;

import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Konfigurations-Dialog für die TestSuite.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class DlgConfigTestSuite extends JDialog implements ActionListener {

	/**
	 *  Serilisation ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Saves the name of the bundle file
	 */
	public static final String BUNDLE_FILE = 
			"resources.lang.org.testsuite.app.DlgConfigTestSuite";
	
	/**
	 * Saves the action command for accept button
	 */
	private static final String BTN_ACCEPT = "DlgConfigTestSuite.accept";
	
	/**
	 * Saves the action command for cancel button
	 */
	private static final String BTN_CANCEL = "DlgConfigTestSuite.cancel";
	
	/**
	 * Specifies that ended with accept.
	 */
	public static final int EXIT_ACCEPT = 1;
	
	/**
	 * Specifies that ended with cancel.
	 */
	public static final int EXIT_CANCEL = 2;
	
	/**
	 * Saves the Exit status
	 */
	private int _exitStatus;
	
	/**
	 * Saves the instance of resource bundle
	 */
	private ResourceBundle _bundle;
	
	/**
	 * Saves the input field for the name of test suite
	 */
	private JTextField _txtName;
	
	/**
	 * Saves the input field for the name of package
	 */
	private JTextField _txtPackage;

	/**
	 * Initializes the class and creates the layout.
	 * 
	 * @param owner Window that called this dialog.
	 * 
	 * @param name Name of the test suite
	 * 
	 * @param packageName name of the package for test suite
	 */
	public DlgConfigTestSuite(JFrame owner, String name, String packageName) {
		super(owner);
		
		_bundle = ResourceBundle.getBundle(BUNDLE_FILE);
		_exitStatus = -1;
		
		setSize(500, 200);
		setTitle(_bundle.getString("dialog_title"));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {150, 150, 200};
		gridBagLayout.rowHeights = new int[] {30, 30, 30, 30};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel label = new JLabel(_bundle.getString("label_name"));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.insets = new Insets(5, 5, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		getContentPane().add(label, gbc_label);
		
		_txtName = new JTextField();
		_txtName.setText(name);
		GridBagConstraints gbc__txtName = new GridBagConstraints();
		gbc__txtName.insets = new Insets(5, 5, 5, 5);
		gbc__txtName.gridwidth = 2;
		gbc__txtName.fill = GridBagConstraints.BOTH;
		gbc__txtName.gridx = 1;
		gbc__txtName.gridy = 0;
		getContentPane().add(_txtName, gbc__txtName);
		
		label = new JLabel(_bundle.getString("label_package"));
		gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.insets = new Insets(5, 5, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 1;
		getContentPane().add(label, gbc_label);
		
		_txtPackage = new JTextField();
		_txtPackage.setText(packageName);
		GridBagConstraints gbc__txtPackage = new GridBagConstraints();
		gbc__txtPackage.gridwidth = 2;
		gbc__txtPackage.insets = new Insets(5, 5, 5, 5);
		gbc__txtPackage.fill = GridBagConstraints.HORIZONTAL;
		gbc__txtPackage.gridx = 1;
		gbc__txtPackage.gridy = 1;
		getContentPane().add(_txtPackage, gbc__txtPackage);
		
		JButton btn = new JButton(_bundle.getString("button_accept"));
		btn.addActionListener(this);
		btn.setActionCommand(BTN_ACCEPT);
		GridBagConstraints gbc_btn = new GridBagConstraints();
		gbc_btn.gridwidth = 1;
		gbc_btn.insets = new Insets(5, 5, 5, 5);
		gbc_btn.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn.gridx = 0;
		gbc_btn.gridy = 2;
		getContentPane().add(btn, gbc_btn);
		
		btn = new JButton(_bundle.getString("button_cancel"));
		btn.addActionListener(this);
		btn.setActionCommand(BTN_CANCEL);
		gbc_btn = new GridBagConstraints();
		gbc_btn.gridwidth = 1;
		gbc_btn.insets = new Insets(5, 5, 5, 5);
		gbc_btn.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn.gridx = 2;
		gbc_btn.gridy = 2;
		getContentPane().add(btn, gbc_btn);
		
		setModal(true);
		setVisible(true);
	}
	
	/**
	 * Returns the name of test suite
	 * 
	 * @param Name of test suite
	 */
	public String getTestSuiteName() {
		return _txtName.getText();
	}
	
	/**
	 * Returns the name of package
	 * 
	 * @return Name of package
	 */
	public String getPackageName() {
		return _txtPackage.getText();
	}
	
	/**
	 * Returns the exit status
	 * 
	 * @return Exit status of this dialog
	 */
	public int getExitStatus() {
		return _exitStatus;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals(BTN_CANCEL)) {
			_exitStatus = EXIT_CANCEL;
			setVisible(false);
		}
		
		else if(ae.getActionCommand().equals(BTN_ACCEPT)) {
			_exitStatus = EXIT_ACCEPT;
			setVisible(false);
		}
	}
}
