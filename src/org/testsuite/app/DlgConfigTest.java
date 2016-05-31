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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Configuration dialog for the tests.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class DlgConfigTest extends DlgConfig {

	/**
	 * Serilisation ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Saves the name of the bundle file
	 */
	public static final String BUNDLE_FILE = 
			"resources.lang.org.testsuite.app.DlgConfigTest";
	
	/**
	 * Saves the instance of text field for name
	 */
	private JTextField _txtName;
	
	/**
	 * Saves the instance of check box for execute test.
	 */
	private JCheckBox _cbExecute;
	
	/**
	 * Saves the instance of check box for run test in a separate JVM.
	 */
	private JCheckBox _cbJvm;

	/**
	 * Initialize the class an create the layout. 
	 * 
	 * @param owner Window that called this dialog.
	 * 
	 * @param name The name of test
	 * 
	 * @param execute Execute the test?
	 * 
	 * @param jvm Run the test in a separate JVM?
	 */
	public DlgConfigTest(JFrame owner, String name, boolean execute, 
			boolean jvm) {
		super(owner, BUNDLE_FILE);
		
		setSize(500, 250);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {150, 150, 150};
		gridBagLayout.rowHeights = new int[] {30, 30, 30, 30, 30, 30};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel label = new JLabel(_bundle.getString("label_name"));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		getContentPane().add(label, gbc);
		
		_txtName = new JTextField();
		_txtName.setText(name);
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 2;
		getContentPane().add(_txtName, gbc);
		
		_cbExecute = new JCheckBox(_bundle.getString("label_execute"));
		_cbExecute.setSelected(execute);
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = 3;
		getContentPane().add(_cbExecute, gbc);
		
		_cbJvm = new JCheckBox(_bundle.getString("label_jvm"));
		_cbJvm.setSelected(jvm);
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridheight = 1;
		gbc.gridwidth = 3;
		getContentPane().add(_cbJvm, gbc);
		
		JButton btn = new JButton(_bundle.getString("button_accept"));
		btn.addActionListener(this);
		btn.setActionCommand(BTN_ACCEPT);
		GridBagConstraints gbc_btn = new GridBagConstraints();
		gbc_btn.gridwidth = 1;
		gbc_btn.insets = new Insets(5, 5, 5, 5);
		gbc_btn.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn.gridx = 0;
		gbc_btn.gridy = 5;
		getContentPane().add(btn, gbc_btn);
		
		btn = new JButton(_bundle.getString("button_cancel"));
		btn.addActionListener(this);
		btn.setActionCommand(BTN_CANCEL);
		gbc_btn = new GridBagConstraints();
		gbc_btn.gridwidth = 1;
		gbc_btn.insets = new Insets(5, 5, 5, 5);
		gbc_btn.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn.gridx = 2;
		gbc_btn.gridy = 5;
		getContentPane().add(btn, gbc_btn);
		
		setModal(true);
		setVisible(true);
	}
	
	/**
	 * Return the name of the test
	 * 
	 * @return Name of the test
	 */
	public String getTestName() {
		return _txtName.getText();
	}
	
	/**
	 * Returns whether the test should be executed.
	 * 
	 * @return Should the test be executed?
	 */
	public boolean isTestExecute() {
		return _cbExecute.isSelected();
	}
	
	/**
	 * Returns whether the test should be run in a separate JVM.
	 * 
	 * @return Should the test be run in a separate JVM?
	 */
	public boolean isTestRunInJvm() {
		return _cbJvm.isSelected();
	}

}
