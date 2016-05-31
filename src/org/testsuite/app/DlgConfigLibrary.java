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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Configuration dialog for the libraries.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class DlgConfigLibrary extends DlgConfig {

	/**
	 * Serlisation ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Saves the name of the bundle file
	 */
	public static final String BUNDLE_FILE = 
			"resources.lang.org.testsuite.app.DlgConfigLibrary";
	
	/**
	 * Saves the instance of text field for file name
	 */
	private JTextField _txtFileName;
	
	/**
	 * Saves the instance of text field for path
	 */
	private JTextField _txtPath;
	
	/**
	 * Saves the instance of text field for name
	 */
	private JTextField _txtName;
	
	/**
	 * Saves the instance of text field for version
	 */
	private JTextField _txtVersion;
	
	/**
	 * Initialize the class and create the layout. 
	 * 
	 * @param owner Window that called this dialog.
	 * 
	 * @param fileName The file name of library.
	 * 
	 * @param path Path where the library is located.
	 * 
	 * @param name The Name of library
	 * 
	 * @param version The version of library
	 */
	public DlgConfigLibrary(JFrame owner, String fileName, String path, 
			String name, String version) {
		super(owner, BUNDLE_FILE);
		
		setSize(500, 250);
		setLocationRelativeTo(owner);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {150, 150, 150};
		gridBagLayout.rowHeights = new int[] {30, 30, 30, 30, 30, 30};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel label = new JLabel(_bundle.getString("label_file_name"));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		getContentPane().add(label, gbc);
		
		_txtFileName = new JTextField();
		_txtFileName.setText(fileName);
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 2;
		getContentPane().add(_txtFileName, gbc);
		
		label = new JLabel(_bundle.getString("label_path"));
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 1;
		getContentPane().add(label, gbc);
		
		_txtPath = new JTextField();
		_txtPath.setText(path);
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = 2;
		getContentPane().add(_txtPath, gbc);
		
		label = new JLabel(_bundle.getString("label_name"));
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 2;
		getContentPane().add(label, gbc);
		
		_txtName = new JTextField();
		_txtName.setText(name);
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridheight = 1;
		gbc.gridwidth = 2;
		getContentPane().add(_txtName, gbc);
		
		label = new JLabel(_bundle.getString("label_version"));
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 3;
		getContentPane().add(label, gbc);
		
		_txtVersion = new JTextField();
		_txtVersion.setText(version);
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridheight = 1;
		gbc.gridwidth = 2;
		getContentPane().add(_txtVersion, gbc);
		
		JButton btn = new JButton(_bundle.getString("button_accept"));
		btn.addActionListener(this);
		btn.setActionCommand(BTN_ACCEPT);
		GridBagConstraints gbc_btn = new GridBagConstraints();
		gbc_btn.gridwidth = 1;
		gbc_btn.insets = new Insets(5, 5, 5, 5);
		gbc_btn.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn.gridx = 0;
		gbc_btn.gridy = 4;
		getContentPane().add(btn, gbc_btn);
		
		btn = new JButton(_bundle.getString("button_cancel"));
		btn.addActionListener(this);
		btn.setActionCommand(BTN_CANCEL);
		gbc_btn = new GridBagConstraints();
		gbc_btn.gridwidth = 1;
		gbc_btn.insets = new Insets(5, 5, 5, 5);
		gbc_btn.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn.gridx = 2;
		gbc_btn.gridy = 4;
		getContentPane().add(btn, gbc_btn);
		
		setModal(true);
		setVisible(true);
	}
	
	/**
	 * Returns the file name entered.
	 * 
	 * @return Entered file name
	 */
	public String getFileName() {
		return _txtFileName.getText();
	}
	
	/**
	 * Returns the path entered.
	 * 
	 * @return Entered path
	 */
	public String getPath() {
		return _txtPath.getText();
	}
	
	/**
	 * Returns the name entered.
	 * 
	 * @return Entered name
	 */
	public String getLibraryName() {
		return _txtName.getText();
	}
	
	/**
	 * Returns the version entered
	 * 
	 * @return Entered version
	 */
	public String getVersion() {
		return _txtVersion.getText();
	}
}
