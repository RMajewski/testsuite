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
import java.awt.event.ActionEvent;
import java.text.NumberFormat;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
	 * Saves the action command for the insert a new property
	 */
	private static final String PROPERTY_INSERT = "App.insert.Property";
	
	/**
	 * Saves the action command for the change a property
	 */
	private static final String PROPERTY_CHANGE = "App.change.Property";
	
	/**
	 * Saves the action command for the delete a property
	 */
	private static final String PROPERTY_DELETE = "App.delete.Property";
	
	/**
	 * Saves the action command for the insert a new class path
	 */
	private static final String CLASSPATH_INSERT = "App.insert.ClassPath";
	
	/**
	 * Saves the action command for the change a class path
	 */
	private static final String CLASSPATH_CHANGE = "App.change.ClassPath";
	
	/**
	 * Saves the action command for the delete a class path
	 */
	private static final String CLASSPATH_DELETE = "App.delete.ClassPath";
	
	/**
	 * Saves the action command for the insert a new java script file
	 */
	private static final String JAVASCRIPT_INSERT = "App.insert.Javascript";
	
	/**
	 * Saves the action command for the change a java script file
	 */
	private static final String JAVASCRIPT_CHANGE = "App.change.Javascript";
	
	/**
	 * Saves the action command for the delete a java script file
	 */
	private static final String JAVASCRIPT_DELETE = "App.delete.Javascript";
	
	/**
	 * Saves the action command for the insert a new style sheet file
	 */
	private static final String STYLESHEET_INSERT = "App.insert.Stylesheet";
	
	/**
	 * Saves the action command for the change a style sheet file
	 */
	private static final String STYLESHEET_CHANGE = "App.change.Stylesheet";
	
	/**
	 * Saves the action command for the delete a style sheet file
	 */
	private static final String STYLESHEET_DELETE = "App.delete.Stylesheet";
	
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
	 * Saves the instance of list for class paths.
	 */
	private JList<String> _listClassPaths;
	
	/**
	 * Saves the instance of list for java script files.
	 */
	private JList<String> _listJavascript;
	
	/**
	 * Saves the instance of list for style sheet files.
	 */
	private JList<String> _listStylesheets;
	
	/**
	 * Saves the object of the general configuration
	 */
	private Config _config;
	
	/**
	 * Initialize the class an create the layout. 
	 * 
	 * @param owner Window that called this dialog.
	 *
	 * @param config The general configuration
	 */
	@SuppressWarnings("serial")
	public DlgConfigGeneral(JFrame owner, Config config) {
		super(owner, BUNDLE_FILE);
		
		_config = config;
		
		setSize(700, 730);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {250, 150, 200};
		gridBagLayout.rowHeights = new int[] {30, 30, 30, 30, 30, 30, 30, 30, 
				30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0};
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
		_listProperties.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				setPopupEnabled(_listProperties, e.getFirstIndex() > -1);
			}
			
		});
		_listProperties.setComponentPopupMenu(createPopupForProperties());
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.gridheight = 4;
		gbc.gridwidth = 2;
		getContentPane().add(new JScrollPane(_listProperties), gbc);
		
		label = new JLabel(_bundle.getString("label_system_classpath"));
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 9;
		getContentPane().add(label, gbc);
		
		_listClassPaths = new JList<String>();
		_listClassPaths.setModel(new AbstractListModel<String>() {

			@Override
			public String getElementAt(int index) {
				return _config.getClassPath(index);
			}

			@Override
			public int getSize() {
				return _config.classPathCount();
			}
			
		});
		_listClassPaths.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				setPopupEnabled(_listClassPaths, e.getFirstIndex() > -1);
			}
			
		});
		_listClassPaths.setComponentPopupMenu(createPopupForClassPaths());
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 9;
		gbc.gridheight = 4;
		gbc.gridwidth = 2;
		getContentPane().add(new JScrollPane(_listClassPaths), gbc);
		
		label = new JLabel(_bundle.getString("label_javascript"));
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 13;
		getContentPane().add(label, gbc);
		
		_listJavascript = new JList<String>();
		_listJavascript.setModel(new AbstractListModel<String>() {

			@Override
			public String getElementAt(int index) {
				return _config.getJavascriptFile(index);
			}

			@Override
			public int getSize() {
				return _config.javascriptFileCount();
			}
			
		});
		_listJavascript.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				setPopupEnabled(_listJavascript, e.getFirstIndex() > -1);
			}
			
		});
		_listJavascript.setComponentPopupMenu(createPopupForJavascript());
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 13;
		gbc.gridheight = 4;
		gbc.gridwidth = 2;
		getContentPane().add(new JScrollPane(_listJavascript), gbc);
		
		label = new JLabel(_bundle.getString("label_stylesheet"));
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 17;
		getContentPane().add(label, gbc);
		
		_listStylesheets = new JList<String>();
		_listStylesheets.setModel(new AbstractListModel<String>() {

			@Override
			public String getElementAt(int index) {
				return _config.getStylesheetFile(index);
			}

			@Override
			public int getSize() {
				return _config.stylesheetFileCount();
			}
			
		});
		_listStylesheets.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				setPopupEnabled(_listStylesheets, e.getFirstIndex() > -1);
			}
			
		});
		_listStylesheets.setComponentPopupMenu(createPopupForStylesheet());
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 17;
		gbc.gridheight = 4;
		gbc.gridwidth = 2;
		getContentPane().add(new JScrollPane(_listStylesheets), gbc);
		
		JButton btn = new JButton(_bundle.getString("button_accept"));
		btn.addActionListener(this);
		btn.setActionCommand(BTN_ACCEPT);
		GridBagConstraints gbc_btn = new GridBagConstraints();
		gbc_btn.gridwidth = 1;
		gbc_btn.insets = new Insets(5, 5, 5, 5);
		gbc_btn.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn.gridx = 0;
		gbc_btn.gridy = 22;
		getContentPane().add(btn, gbc_btn);
		
		btn = new JButton(_bundle.getString("button_cancel"));
		btn.addActionListener(this);
		btn.setActionCommand(BTN_CANCEL);
		gbc_btn = new GridBagConstraints();
		gbc_btn.gridwidth = 1;
		gbc_btn.insets = new Insets(5, 5, 5, 5);
		gbc_btn.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn.gridx = 2;
		gbc_btn.gridy = 22;
		getContentPane().add(btn, gbc_btn);
		
		setModal(true);
		setVisible(true);
	}

	/**
	 * Responds to the click of a button or the click on a pop-up menu item.
	 * 
	 * @param ae Data for the event
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		super.actionPerformed(ae);
		
		switch (ae.getActionCommand()) {
		
			// Save the entered configuration
			case BTN_ACCEPT:
				_config.setCreateHtml(_cbHtml.isSelected());
				_config.setPathLibrary(_txtLibraryPath.getText());
				_config.setMaxDuration(
						((Number)_txtMaxDuration.getValue()).longValue());
				_config.setPathResult(_txtResultPath.getText());
				_config.setPathSrc(_txtSrcPath.getText());
				break;
		
			// Insert a new system property
			case PROPERTY_INSERT:
				String ncp = JOptionPane.showInputDialog(this, 
						_bundle.getString("insert_property_message"),
						_bundle.getString("insert_property_title"),
						JOptionPane.OK_CANCEL_OPTION);
				if ((ncp != null) && !ncp.isEmpty()) {
					_config.addProperty(ncp);
					_listProperties.updateUI();
				}
				break;
		
			// Change the selected property 
			case PROPERTY_CHANGE:
				ncp = JOptionPane.showInputDialog(this, 
						_bundle.getString("change_property_message"),
						_bundle.getString("change_property_title"),
						JOptionPane.OK_CANCEL_OPTION);
				if ((ncp != null) && !ncp.isEmpty()) {
					_config.changeProperty(_listProperties.getSelectedValue(), ncp);
					_listProperties.updateUI();
				}
				break;
		
			// Delete the selected system property
			case PROPERTY_DELETE:
				int ret = JOptionPane.showConfirmDialog(this, 
						_bundle.getString("delete_property_message"),
						_bundle.getString("delete_property_title"),
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.QUESTION_MESSAGE);
				if (ret == JOptionPane.YES_OPTION) {
					_config.removeProperty(_listProperties.getSelectedValue());
					_listProperties.updateUI();
					// OPT Insert in a private method 
					if (_listProperties.getSelectedIndex() >= 
							_listProperties.getModel().getSize()) {
						_listProperties.clearSelection();
						_listProperties.getComponentPopupMenu().getComponent(1)
							.setEnabled(false);
						_listProperties.getComponentPopupMenu().getComponent(2)
						.setEnabled(false);
					}
				}
				break;
				
			// Insert a new class path
			case CLASSPATH_INSERT:
				ncp = JOptionPane.showInputDialog(this, 
						_bundle.getString("insert_classpath_message"),
						_bundle.getString("insert_classpath_title"),
						JOptionPane.OK_CANCEL_OPTION);
				if ((ncp != null) && !ncp.isEmpty()) {
					_config.addClassPath(ncp);
					_listClassPaths.updateUI();
				}
				break;
		
			// Change the selected class path 
			case CLASSPATH_CHANGE:
				ncp = JOptionPane.showInputDialog(this, 
						_bundle.getString("change_classpath_message"),
						_bundle.getString("change_classpath_title"),
						JOptionPane.OK_CANCEL_OPTION);
				if ((ncp != null) && !ncp.isEmpty()) {
					_config.changeClassPath(_listClassPaths.getSelectedValue(),
							ncp);
					_listClassPaths.updateUI();
				}
				break;
		
			// Delete the selected class path
			case CLASSPATH_DELETE:
				ret = JOptionPane.showConfirmDialog(this, 
						_bundle.getString("delete_classpath_message"),
						_bundle.getString("delete_classpath_title"),
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.QUESTION_MESSAGE);
				if (ret == JOptionPane.YES_OPTION) {
					_config.removeClassPath(_listClassPaths.getSelectedValue());
					_listClassPaths.updateUI();
					// OPT Insert in a private method (Merge with system properties) 
					if (_listClassPaths.getSelectedIndex() >= 
							_listClassPaths.getModel().getSize()) {
						_listClassPaths.clearSelection();
						_listClassPaths.getComponentPopupMenu().getComponent(1)
							.setEnabled(false);
						_listClassPaths.getComponentPopupMenu().getComponent(2)
						.setEnabled(false);
					}
				}
				break;
				
			// Insert a new java script file
			case JAVASCRIPT_INSERT:
				ncp = JOptionPane.showInputDialog(this, 
						_bundle.getString("insert_javascript_message"),
						_bundle.getString("insert_javascript_title"),
						JOptionPane.OK_CANCEL_OPTION);
				if ((ncp != null) && !ncp.isEmpty()) {
					_config.addJavascriptFile(ncp);
					_listJavascript.updateUI();
				}
				break;
		
			// Change the selected java script file
			case JAVASCRIPT_CHANGE:
				ncp = JOptionPane.showInputDialog(this, 
						_bundle.getString("change_javascript_message"),
						_bundle.getString("change_javascript_title"),
						JOptionPane.OK_CANCEL_OPTION);
				if ((ncp != null) && !ncp.isEmpty()) {
					_config.changeJavascriptFile(
							_listJavascript.getSelectedValue(), ncp);
					_listJavascript.updateUI();
				}
				break;
		
			// Delete the selected class path
			case JAVASCRIPT_DELETE:
				ret = JOptionPane.showConfirmDialog(this, 
						_bundle.getString("delete_javascript_message"),
						_bundle.getString("delete_javascript_title"),
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.QUESTION_MESSAGE);
				if (ret == JOptionPane.YES_OPTION) {
					_config.removeJavascriptFile(_listJavascript.getSelectedValue());
					_listJavascript.updateUI();
					// OPT Insert in a private method (Merge with system properties) 
					if (_listJavascript.getSelectedIndex() >= 
							_listJavascript.getModel().getSize()) {
						_listJavascript.clearSelection();
						_listJavascript.getComponentPopupMenu().getComponent(1)
							.setEnabled(false);
						_listJavascript.getComponentPopupMenu().getComponent(2)
						.setEnabled(false);
					}
				}
				break;
				
			// Insert a new style sheet file
			case STYLESHEET_INSERT:
				ncp = JOptionPane.showInputDialog(this, 
						_bundle.getString("insert_stylesheet_message"),
						_bundle.getString("insert_stylesheet_title"),
						JOptionPane.OK_CANCEL_OPTION);
				if ((ncp != null) && !ncp.isEmpty()) {
					_config.addStylesheetFile(ncp);
					_listStylesheets.updateUI();
				}
				break;
		
			// Change the selected java script file
			case STYLESHEET_CHANGE:
				ncp = JOptionPane.showInputDialog(this, 
						_bundle.getString("change_stylesheet_message"),
						_bundle.getString("change_stylesheet_title"),
						JOptionPane.OK_CANCEL_OPTION);
				if ((ncp != null) && !ncp.isEmpty()) {
					_config.changeStylesheetFile(
							_listStylesheets.getSelectedValue(), ncp);
					_listStylesheets.updateUI();
				}
				break;
		
			// Delete the selected class path
			case STYLESHEET_DELETE:
				ret = JOptionPane.showConfirmDialog(this, 
						_bundle.getString("delete_stylesheet_message"),
						_bundle.getString("delete_stylesheet_title"),
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.QUESTION_MESSAGE);
				if (ret == JOptionPane.YES_OPTION) {
					_config.removeStylesheetFile(_listStylesheets.getSelectedValue());
					_listStylesheets.updateUI();
					// OPT Insert in a private method (Merge with system properties) 
					if (_listStylesheets.getSelectedIndex() >= 
							_listStylesheets.getModel().getSize()) {
						_listStylesheets.clearSelection();
						_listStylesheets.getComponentPopupMenu().getComponent(1)
							.setEnabled(false);
						_listStylesheets.getComponentPopupMenu().getComponent(2)
						.setEnabled(false);
					}
				}
				break;
		}
	}
	
	/**
	 * Create the pop-up menu for the property list
	 * 
	 * @return Pop-up menu for the property list
	 */
	private JPopupMenu createPopupForProperties() {
		JPopupMenu ret = new JPopupMenu();
		
		JMenuItem item = new JMenuItem(_bundle.getString("insert_property"));
		item.setMnemonic(_bundle.getString("insert_property_mnemonic").charAt(0));
		item.addActionListener(this);
		item.setActionCommand(PROPERTY_INSERT);
		ret.add(item);
		
		item = new JMenuItem(_bundle.getString("change_property"));
		item.setMnemonic(_bundle.getString("change_property_mnemonic").charAt(0));
		item.addActionListener(this);
		item.setActionCommand(PROPERTY_CHANGE);
		item.setEnabled(false);
		ret.add(item);
		
		item = new JMenuItem(_bundle.getString("delete_property"));
		item.setMnemonic(_bundle.getString("delete_property_mnemonic").charAt(0));
		item.addActionListener(this);
		item.setActionCommand(PROPERTY_DELETE);
		item.setEnabled(false);
		ret.add(item);
		
		return ret;
	}
	
	/**
	 * Create the pop-up menu for the class path list
	 * 
	 * @return Pop-up menu for the class path list
	 */
	private JPopupMenu createPopupForClassPaths() {
		JPopupMenu ret = new JPopupMenu();
		
		JMenuItem item = new JMenuItem(_bundle.getString("insert_classpath"));
		item.setMnemonic(_bundle.getString("insert_classpath_mnemonic").charAt(0));
		item.addActionListener(this);
		item.setActionCommand(CLASSPATH_INSERT);
		ret.add(item);
		
		item = new JMenuItem(_bundle.getString("change_classpath"));
		item.setMnemonic(_bundle.getString("change_classpath_mnemonic").charAt(0));
		item.addActionListener(this);
		item.setActionCommand(CLASSPATH_CHANGE);
		item.setEnabled(false);
		ret.add(item);
		
		item = new JMenuItem(_bundle.getString("delete_classpath"));
		item.setMnemonic(_bundle.getString("delete_classpath_mnemonic").charAt(0));
		item.addActionListener(this);
		item.setActionCommand(CLASSPATH_DELETE);
		item.setEnabled(false);
		ret.add(item);
		
		return ret;
	}
	
	/**
	 * Create the pop-up menu for the java script files list
	 * 
	 * @return Pop-up menu for the java script files list
	 */
	private JPopupMenu createPopupForJavascript() {
		JPopupMenu ret = new JPopupMenu();
		
		JMenuItem item = new JMenuItem(_bundle.getString("insert_javascript"));
		item.setMnemonic(_bundle.getString("insert_javascript_mnemonic").charAt(0));
		item.addActionListener(this);
		item.setActionCommand(JAVASCRIPT_INSERT);
		ret.add(item);
		
		item = new JMenuItem(_bundle.getString("change_javascript"));
		item.setMnemonic(_bundle.getString("change_javascript_mnemonic").charAt(0));
		item.addActionListener(this);
		item.setActionCommand(JAVASCRIPT_CHANGE);
		item.setEnabled(false);
		ret.add(item);
		
		item = new JMenuItem(_bundle.getString("delete_javascript"));
		item.setMnemonic(_bundle.getString("delete_javascript_mnemonic").charAt(0));
		item.addActionListener(this);
		item.setActionCommand(JAVASCRIPT_DELETE);
		item.setEnabled(false);
		ret.add(item);
		
		return ret;
	}
	
	/**
	 * Create the pop-up menu for the java script files list
	 * 
	 * @return Pop-up menu for the java script files list
	 */
	private JPopupMenu createPopupForStylesheet() {
		JPopupMenu ret = new JPopupMenu();
		
		JMenuItem item = new JMenuItem(_bundle.getString("insert_stylesheet"));
		item.setMnemonic(_bundle.getString("insert_stylesheet_mnemonic").charAt(0));
		item.addActionListener(this);
		item.setActionCommand(STYLESHEET_INSERT);
		ret.add(item);
		
		item = new JMenuItem(_bundle.getString("change_stylesheet"));
		item.setMnemonic(_bundle.getString("change_stylesheet_mnemonic").charAt(0));
		item.addActionListener(this);
		item.setActionCommand(STYLESHEET_CHANGE);
		item.setEnabled(false);
		ret.add(item);
		
		item = new JMenuItem(_bundle.getString("delete_stylesheet"));
		item.setMnemonic(_bundle.getString("delete_stylesheet_mnemonic").charAt(0));
		item.addActionListener(this);
		item.setActionCommand(STYLESHEET_DELETE);
		item.setEnabled(false);
		ret.add(item);
		
		return ret;
	}
	
	/**
	 * Returns the general configuration
	 * 
	 * @return General configuration
	 */
	public Config getConfig() {
		return _config;
	}
	
	/**
	 * Enabled or disabled from pop-up menu of the specified list the "Change" 
	 * and the "Delete" menu item.
	 *  
	 * @param list The list, which enabled or disabled the pop-up menu items.
	 * 
	 * @param enabled If the menu items are enabled?
	 */
	private void setPopupEnabled(JList<String> list, boolean enabled) {
		list.getComponentPopupMenu().getComponent(1).setEnabled(enabled);
		list.getComponentPopupMenu().getComponent(2).setEnabled(enabled);
	}
}
