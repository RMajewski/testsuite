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
import java.text.NumberFormat;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.testsuite.data.Config;

/**
 * Configuration dialog for the general configuration.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class DlgConfigGeneral extends DlgConfig {

	/**
	 * Serlisation ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Saves the name of the bundle file
	 */
	public static final String BUNDLE_FILE = 
			"resources.lang.org.testsuite.app.DlgConfigGeneral";
	
	/**
	 * Saves the instance of text field for library path.
	 */
	private JTextField _txtLibraryPath;
	
	/**
	 * Saves the instance of text field for result path.
	 */
	private JTextField _txtResultPath;
	
	/**
	 * Saves the instance of text field for source path;
	 */
	private JTextField _txtSrcPath;
	
	/**
	 * Saves the instance of check box for html output.
	 */
	private JCheckBox _cbHtml;
	
	/**
	 * Saves the instance of text field for max duration.
	 */
	private JFormattedTextField _txtMaxDuration;
	
	/**
	 * Saves the instance of list for system properties.
	 */
	private JList<String> _listProperties;
	
	/**
	 * Saves the object of the general configuration
	 */
	private Config _config;
	
	/**
	 * Initialize the class an create the layout. 
	 * 
	 * @param owner Window that called this dialog.
	 * 
	 * @param runner The object of test runner that you want to configure.
	 */
	@SuppressWarnings("serial")
	public DlgConfigGeneral(JFrame owner, Config config) {
		super(owner, BUNDLE_FILE);
		
		_config = config;
		
		setSize(600, 550);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {250, 150, 200};
		gridBagLayout.rowHeights = new int[] {30, 30, 30, 30, 30, 30, 30, 30, 
				30};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 
				0.0, 0.0, 0.0};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel label = new JLabel(_bundle.getString("label_library_path"));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		getContentPane().add(label, gbc);
		
		_txtLibraryPath = new JTextField();
		_txtLibraryPath.setText(_config.getPathLibrary());
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 2;
		getContentPane().add(_txtLibraryPath, gbc);
		
		label = new JLabel(_bundle.getString("label_source_path"));
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 1;
		getContentPane().add(label, gbc);
		
		_txtSrcPath = new JTextField();
		_txtSrcPath.setText(_config.getPathSrc());
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = 2;
		getContentPane().add(_txtSrcPath, gbc);
		
		label = new JLabel(_bundle.getString("label_result_path"));
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 2;
		getContentPane().add(label, gbc);
		
		_txtResultPath = new JTextField();
		_txtResultPath.setText(_config.getPathResult());
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridheight = 1;
		gbc.gridwidth = 2;
		getContentPane().add(_txtResultPath, gbc);
		
		_cbHtml = new JCheckBox(_bundle.getString("label_html_out"));
		_cbHtml.setSelected(_config.isCreateHtml());
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridheight = 1;
		gbc.gridwidth = 2;
		getContentPane().add(_cbHtml, gbc);
		
		label = new JLabel(_bundle.getString("label_max_duration"));
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 4;
		getContentPane().add(label, gbc);
		
		NumberFormat lf = NumberFormat.getNumberInstance();
		lf.setMinimumIntegerDigits(1);
		lf.setMaximumIntegerDigits(10);
		lf.setMinimumFractionDigits(0);
		lf.setMaximumFractionDigits(0);
		_txtMaxDuration = new JFormattedTextField(lf);
		_txtMaxDuration.setValue(_config.getMaxDuration());
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridheight = 1;
		gbc.gridwidth = 2;
		getContentPane().add(_txtMaxDuration, gbc);
		
		label = new JLabel(_bundle.getString("label_system_property"));
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 5;
		getContentPane().add(label, gbc);
		
		_listProperties = new JList<String>();
		_listProperties.setModel(new AbstractListModel<String>() {

			@Override
			public String getElementAt(int index) {
				return _config.getProperty(index);
			}

			@Override
			public int getSize() {
				return _config.propertyCount();
			}
			
		});
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.gridheight = 4;
		gbc.gridwidth = 2;
		getContentPane().add(new JScrollPane(_listProperties), gbc);
		
		JButton btn = new JButton(_bundle.getString("button_accept"));
		btn.addActionListener(this);
		btn.setActionCommand(BTN_ACCEPT);
		GridBagConstraints gbc_btn = new GridBagConstraints();
		gbc_btn.gridwidth = 1;
		gbc_btn.insets = new Insets(5, 5, 5, 5);
		gbc_btn.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn.gridx = 0;
		gbc_btn.gridy = 9;
		getContentPane().add(btn, gbc_btn);
		
		btn = new JButton(_bundle.getString("button_cancel"));
		btn.addActionListener(this);
		btn.setActionCommand(BTN_CANCEL);
		gbc_btn = new GridBagConstraints();
		gbc_btn.gridwidth = 1;
		gbc_btn.insets = new Insets(5, 5, 5, 5);
		gbc_btn.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn.gridx = 2;
		gbc_btn.gridy = 9;
		getContentPane().add(btn, gbc_btn);
		
		setModal(true);
		setVisible(true);
	}

}
