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

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.testsuite.data.Library;

import org.testsuite.core.TestRunner;

public class DlgConfigTestRunner extends DlgConfig {

	/**
	 * Serlisation ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Saves the action command for insert a new classpath
	 */
	private static final String INSERT_CLASSPATH = 
			"App.config.insert.classpath";
	
	/**
	 * Saves the action command for insert a new library
	 */
	private static final String INSERT_LIBRARY = "App.config.insert.library";
	
	/**
	 * Saves the action command for delete a classpath
	 */
	private static final String DELETE_CLASSPATH = 
			"App.config.delete.classpath";
	
	/**
	 * Saves the action command for delete a library
	 */
	private static final String DELETE_LIBRARY = "App.config.delete.library";
	
	/**
	 * Saves the action command for change a library
	 */
	private static final String CHANGE_LIBRARY = "App.config.change.library";
	
	/**
	 * Saves the name of the bundle file
	 */
	public static final String BUNDLE_FILE = 
			"resources.lang.org.testsuite.app.DlgConfigTestRunner";
	
	/**
	 * Saves the instance for text field for test runner class name
	 */
	private JTextField _txtClass;
	
	/**
	 * Saves the instance of text field for file extension
	 */
	private JTextField _txtExtension;
	
	/**
	 * Saves the instance of text area for description
	 */
	private JTextArea _txtDescription;
	
	/**
	 * Saves the instance of list for libraries
	 */
	private JList<Library> _listLibrary;
	
	/**
	 * Saves the instance if list for classpath
	 */
	private JList<String> _listClasspath;
	
	/**
	 * Saves the object of test runner
	 */
	private TestRunner _runner;

	/**
	 * Initialize the class an create the layout. 
	 * 
	 * @param owner Window that called this dialog.
	 * 
	 * @param runner The object of test runner that you want to configure.
	 */
	@SuppressWarnings("serial")
	public DlgConfigTestRunner(JFrame owner, TestRunner runner) {
		super(owner, BUNDLE_FILE);
		
		_runner = runner;
		
		setSize(600, 550);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {250, 150, 200};
		gridBagLayout.rowHeights = new int[] {30, 30, 30, 30, 30, 30, 30, 30, 
				30, 30, 30, 30, 30, 30, 30};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel label = new JLabel(_bundle.getString("label_class_name"));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		getContentPane().add(label, gbc);
		
		_txtClass = new JTextField();
		_txtClass.setText(_runner.getClass().getName());
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 2;
		getContentPane().add(_txtClass, gbc);
		
		label = new JLabel(_bundle.getString("label_file_extension"));
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 1;
		getContentPane().add(label, gbc);
		
		_txtExtension = new JTextField();
		_txtExtension.setText(_runner.getFileExtension());
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = 2;
		getContentPane().add(_txtExtension, gbc);
		
		label = new JLabel(_bundle.getString("label_description"));
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 2;
		getContentPane().add(label, gbc);
		
		_txtDescription = new JTextArea();
		_txtDescription.setLineWrap(true);
		_txtDescription.setColumns(40);
		_txtDescription.setText(_runner.getDescription());
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridheight = 4;
		gbc.gridwidth = 2;
		getContentPane().add(new JScrollPane(_txtDescription), gbc);
		
		label = new JLabel(_bundle.getString("label_library"));
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 6;
		getContentPane().add(label, gbc);
		
		_listLibrary = new JList<Library>();
		_listLibrary.setComponentPopupMenu(createPopupForLibrary());
		_listLibrary.setCellRenderer(new ListCellRenderer<Library>() {

			@Override
			public Component getListCellRendererComponent(
					JList<? extends Library> list, Library value, int index,
					boolean isSelected, boolean cellHasFocus) {
				JLabel ret = new JLabel();
				ret.setOpaque(true);
				ret.setText(value.getFileName());
				
				Color background = UIManager.getColor("List.background");
				Color foreground = UIManager.getColor("List.foreground");
				
				if (isSelected) {
					background = UIManager.getColor("List.selectionBackground");
					foreground = UIManager.getColor("List.selectionForeground");
				}
				
				ret.setForeground(foreground);
				ret.setBackground(background);
				
				return ret;
			}
			
		});
		_listLibrary.setModel(new AbstractListModel<Library>() {

			@Override
			public Library getElementAt(int index) {
				return _runner.getLibrary(index);
			}

			@Override
			public int getSize() {
				return _runner.libraryCount();
			}
			
		});
		_listLibrary.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getFirstIndex() > -1) {
					_listLibrary.getComponentPopupMenu().getComponent(1)
						.setEnabled(true);
					_listLibrary.getComponentPopupMenu().getComponent(2)
						.setEnabled(true);
				} else {
					_listLibrary.getComponentPopupMenu().getComponent(1)
					.setEnabled(false);
					_listLibrary.getComponentPopupMenu().getComponent(2)
					.setEnabled(false);
				}
			}
			
		});
		_listLibrary.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.gridheight = 4;
		gbc.gridwidth = 2;
		getContentPane().add(new JScrollPane(_listLibrary), gbc);
		
		label = new JLabel(_bundle.getString("label_classpath"));
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 10;
		getContentPane().add(label, gbc);
		
		_listClasspath = new JList<String>();
		_listClasspath.setComponentPopupMenu(createPopupForClasspath());
		_listClasspath.setModel(new AbstractListModel<String>() {

			@Override
			public String getElementAt(int index) {
				return _runner.getClassPath(index);
			}

			@Override
			public int getSize() {
				return _runner.classPathCount();
			}
			
		});
		_listClasspath.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getFirstIndex() > -1) {
					_listClasspath.getComponentPopupMenu().getComponent(1)
						.setEnabled(true);
				} else {
					_listClasspath.getComponentPopupMenu().getComponent(1)
					.setEnabled(false);
				}
			}
			
		});
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 10;
		gbc.gridheight = 4;
		gbc.gridwidth = 2;
		getContentPane().add(new JScrollPane(_listClasspath), gbc);
		
		JButton btn = new JButton(_bundle.getString("button_accept"));
		btn.addActionListener(this);
		btn.setActionCommand(BTN_ACCEPT);
		GridBagConstraints gbc_btn = new GridBagConstraints();
		gbc_btn.gridwidth = 1;
		gbc_btn.insets = new Insets(5, 5, 5, 5);
		gbc_btn.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn.gridx = 0;
		gbc_btn.gridy = 15;
		getContentPane().add(btn, gbc_btn);
		
		btn = new JButton(_bundle.getString("button_cancel"));
		btn.addActionListener(this);
		btn.setActionCommand(BTN_CANCEL);
		gbc_btn = new GridBagConstraints();
		gbc_btn.gridwidth = 1;
		gbc_btn.insets = new Insets(5, 5, 5, 5);
		gbc_btn.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn.gridx = 2;
		gbc_btn.gridy = 15;
		getContentPane().add(btn, gbc_btn);
		
		setModal(true);
		setVisible(true);
	}
	
	/**
	 * Saves the config if click to accept button.
	 * 
	 * @param ae Data of event
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		super.actionPerformed(ae);
		
		switch (ae.getActionCommand()) {
			case BTN_ACCEPT:
				_runner.setDescription(_txtDescription.getText());
				_runner.setFileExtension(_txtExtension.getText());
				break;
				
			case INSERT_CLASSPATH:
				String ncp = JOptionPane.showInputDialog(this, 
						_bundle.getString("insert_classpath_message"),
						_bundle.getString("insert_classpath_title"),
						JOptionPane.OK_CANCEL_OPTION);
				if ((ncp != null) && !ncp.isEmpty()) {
					_runner.addClassPath(ncp);
					_listClasspath.updateUI();
				}
				break;
				
			case DELETE_CLASSPATH:
				int ret = JOptionPane.showConfirmDialog(this, 
						_bundle.getString("delete_classpath_message"),
						_bundle.getString("delete_classpath_title"),
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.QUESTION_MESSAGE);
				if (ret == JOptionPane.YES_OPTION) {
					_runner.removeClassPath(_listClasspath.getSelectedValue());
					_listClasspath.updateUI();
				}
				break;
				
			case INSERT_LIBRARY:
				DlgConfigLibrary dlg = new DlgConfigLibrary((JFrame)getOwner(),
						"", "", "", "");
				if (dlg.getExitStatus() == DlgConfig.EXIT_ACCEPT) {
					Library lib = new Library();
					lib.setFileName(dlg.getFileName());
					lib.setPath(dlg.getPath());
					lib.setName(dlg.getLibraryName());
					lib.setVersion(dlg.getVersion());
					_runner.addLibrary(lib);
					_listLibrary.updateUI();
				}
				break;
				
			case DELETE_LIBRARY:
				ret = JOptionPane.showConfirmDialog(this, 
						_bundle.getString("delete_library_message"),
						_bundle.getString("delete_library_title"),
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.QUESTION_MESSAGE);
				if (ret == JOptionPane.YES_OPTION) {
					_runner.removeLibrary(_listLibrary.getSelectedValue());
					_listLibrary.updateUI();
				}
				break;
				
			case CHANGE_LIBRARY:
				dlg = new DlgConfigLibrary((JFrame)getOwner(), 
						_listLibrary.getSelectedValue().getFileName(),
						_listLibrary.getSelectedValue().getPath(),
						_listLibrary.getSelectedValue().getName(),
						_listLibrary.getSelectedValue().getVersion());
				if (dlg.getExitStatus() == DlgConfig.EXIT_ACCEPT) {
					_listLibrary.getSelectedValue().setFileName(
							dlg.getFileName());
					_listLibrary.getSelectedValue().setPath(dlg.getPath());
					_listLibrary.getSelectedValue().setName(
							dlg.getLibraryName());
					_listLibrary.getSelectedValue().setVersion(
							dlg.getVersion());
					_listLibrary.updateUI();
				}
				break;
		}
	}
	
	/**
	 * Create the popup menu for classpath list.
	 * 
	 * @return Popup menu for classpath list.
	 */
	private JPopupMenu createPopupForClasspath() {
		JPopupMenu ret = new JPopupMenu();
		
		JMenuItem item = new JMenuItem(_bundle.getString("insert_classpath"));
		item.setMnemonic(
				_bundle.getString("insert_classpath_mnemonic").charAt(0));
		item.addActionListener(this);
		item.setActionCommand(INSERT_CLASSPATH);
		ret.add(item);
		
		item = new JMenuItem(_bundle.getString("delete_classpath"));
		item.setMnemonic(
				_bundle.getString("delete_classpath_mnemonic").charAt(0));
		item.addActionListener(this);
		item.setActionCommand(DELETE_CLASSPATH);
		item.setEnabled(false);
		ret.add(item);
		
		return ret;
	}
	
	/**
	 * Create the popup menu for library list.
	 * 
	 * @return Popup menu for library list.
	 */
	private JPopupMenu createPopupForLibrary() {
		JPopupMenu ret = new JPopupMenu();
		
		JMenuItem item = new JMenuItem(_bundle.getString("insert_library"));
		item.setMnemonic(_bundle.getString("insert_library_mnemonic").charAt(0));
		item.addActionListener(this);
		item.setActionCommand(INSERT_LIBRARY);
		ret.add(item);
		
		item = new JMenuItem(_bundle.getString("change_library"));
		item.setMnemonic(_bundle.getString("change_library_mnemonic").charAt(0));
		item.addActionListener(this);
		item.setActionCommand(CHANGE_LIBRARY);
		item.setEnabled(false);
		ret.add(item);
		
		item = new JMenuItem(_bundle.getString("delete_library"));
		item.setMnemonic(_bundle.getString("delete_library_mnemonic").charAt(0));
		item.addActionListener(this);
		item.setActionCommand(DELETE_LIBRARY);
		item.setEnabled(false);
		ret.add(item);
		
		return ret;
	}
}
