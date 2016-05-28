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

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.tree.TreePath;

import org.testsuite.core.ConfigParser;
import org.testsuite.core.ConfigSaver;
import org.testsuite.core.HtmlOut;
import org.testsuite.core.JunitTestRunner;
import org.testsuite.core.TestRunner;
import org.testsuite.data.Config;
import org.testsuite.data.Test;
import org.testsuite.data.TestEvent;
import org.testsuite.data.TestEventListener;
import org.testsuite.data.TestSuite;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JProgressBar;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;

/**
 * Window that the test app displays.
 * 
 * In the Version 0.2 configuration dialogs have been added. It can also the
 * configuration is stored. It can also be entered a completely new
 * configuration.
 * 
 * @author René Majewski
 *
 * @version 0.2
 */
@SuppressWarnings("serial")
public class App extends JFrame implements ActionListener, TestEventListener {
	/**
	 * Saves the string of action command for the configuration load button.
	 */
	private static final String AC_CONFIG_LOAD = "App.btnConfigLoad";
	
	/**
	 * Saves the string of action command for the cancel button.
	 */
	private static final String AC_CANCEL = "App.btnCancel";
	
	/**
	 * Saves the string of action command for the run button.
	 */
	private static final String AC_RUN = "App.btnRun";
	
	/**
	 * Saves the string of action command for all tests select ignore
	 */
	private static final String AC_ALL_TESTS_IGNORE = "App.btnAllTestsIgnore";
	
	/**
	 * Saves the string of action command for all tests select execute
	 */
	private static final String AC_ALL_TESTS_EXECUTE = "App.btnAllTestsExecute";
	
	/**
	 * Saves the string of action command for validate the configuration
	 */
	private static final String AC_CONFIG_VALIDATE = "App.btnConfigValidate";
	
	/**
	 * Saves the string of action command for save the configuration
	 */
	private static final String AC_CONFIG_SAVE = "App.btnConfigSave";
	
	/**
	 * Saves the string of action command for open configuration dialog for a
	 * test
	 */
	private static final String CONFIG_TEST = "App.config.Test";
	
	/**
	 * Saves the string of action command for open configuration dialog for a
	 * test suite.
	 */
	private static final String CONFIG_TEST_SUITE = "App.config.TestSuite";
	
	/**
	 * Saves the string of action command for open configuration dialog for a
	 * test runner.
	 */
	private static final String CONFIG_TEST_RUNNER = "App.config.TestRunner";
	
	/**
	 * Saves the string of action command for open configuration dialog for the
	 * general configuration.
	 */
	private static final String CONFIG_GENERAL = "App.config.General";
	
	/**
	 * Saves the string of action command to insert a new test.
	 */
	private static final String TREE_INSERT_TEST = "App.tree.insert.Test";
	
	/**
	 * Saves the string of action command to insert a new test suite.
	 */
	private static final String TREE_INSERT_TEST_SUITE = 
			"App.tree.insert.TestSuite";
	
	/**
	 * Saves the string of action command to insert a new test runner.
	 */
	private static final String TREE_INSERT_TEST_RUNNER = 
			"App.tree.insert.TestRunner";
	
	/**
	 * Saves the string of action command to insert the genral configuration
	 */
	private static final String TREE_INSERT_CONFIG_GENERAL = 
			"App.tree.insert.ConfigGeneral";
	
	/**
	 * Saves the string of action command to delete a test
	 */
	private static final String TREE_DELETE_TEST = "App.tree.delete.Test";
	
	/**
	 * Saves the string of action command to delete a test suite
	 */
	private static final String TREE_DELETE_TEST_SUITE =
			"App.tree.delete.TestSuite";
	
	/**
	 * Saves the string of action command to delete a test runner
	 */
	private static final String TREE_DELETE_TEST_RUNNER =
			"App.tree.delete.TestRunner";
	
	/**
	 * Saves the string of action command to delete the general configuration
	 */
	private static final String TREE_DELETE_CONFIG_GENERAL = 
			"App.tree.delete.ConfigGeneral";
	
	/**
	 * Saves the string of action command to selected tests select as ignore
	 */
	private static final String TREE_IGNORE_SELECTED_TESTS = 
			"App.tree.ignoreSelectedTests";
	
	/**
	 * Saves the string of action command to selected tests select as execute
	 */
	private static final String TREE_EXECUTE_SELECTED_TESTS =
			"App.tree.executeSelectedTests";
	
	/**
	 * Saves the string of action command to other tests select as ignore
	 */
	private static final String TREE_IGNORE_ALL_OTHER_TESTS =
			"App.tree.ignoreOtherTests";
	
	/**
	 * Saves the title of the main window
	 */
	public static final String WND_TITLE = "TestSuiteApp";
	
	/**
	 * Saves the name of the bundle file
	 */
	public static final String BUNDLE_FILE = 
			"resources.lang.org.testsuite.app.App";
	
	/**
	 * Saves the button for run tests.
	 */
	private JButton _btnRun;
	
	/**
	 * Saves the button for cancel run tests.
	 */
	private JButton _btnCancel;
	
	/**
	 * Saves the button for load the configuration file
	 */
	private JButton _btnConfigLoad;
	
	/**
	 * Saves the button for exit the app.
	 */
	private JButton _btnExit;
	
	/**
	 * Saves the button for all tests select ignore
	 */
	private JButton _btnAllTestsIgnore;
	
	/**
	 * Saves the button for all tests select execute
	 */
	private JButton _btnAllTestsExecute;
	
	/**
	 * Saves the button for save configuration
	 */
	private JButton _btnConfigSave;
	
	/**
	 * Saves the button for validate configuration
	 */
	private JButton _btnConfigValidate;
	
	/**
	 * Saves the instance of resource bundle
	 */
	private ResourceBundle _bundle;
	
	/**
	 * Saves the instance of the configuration.
	 */
	private Config _config;
	
	/**
	 * Saves the instance of the JTree.
	 */
	private JTree _tree;
	
	/**
	 * Saves the instance of the JProgressBar.
	 */
	private JProgressBar _pBar;
	
	/**
	 * Saves the instance of the JTextArea for html output.
	 */
	private JEditorPane _txtHtml;
	
	/**
	 * Saves the instance of the JTextArea for output messages.
	 */
	private JTextArea _txtMessage;
	
	/**
	 * Saves the instance of the JScrollPane for _tpMessage.
	 */
	private JScrollPane _spMessage;
	
	/**
	 * Saves the thread of run tests.
	 */
	private Thread _thread;
	
	/**
	 * Initializes the main window.
	 */
	public App() {
		super();
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		_bundle = ResourceBundle.getBundle(BUNDLE_FILE);
		
		_config = new Config();
		
		createLayout();
	}
	
	/**
	 * Creates the popup menu for the tree
	 */
	private JPopupMenu treePopup() {
		JPopupMenu popup = new JPopupMenu();
		
		// Insert menu
		JMenu menu = new JMenu(_bundle.getString("popup_tree_menu_insert"));
		menu.setMnemonic(
				_bundle.getString("popup_tree_menu_insert_mnemonic").charAt(0));
		popup.add(menu);
		
		// Insert -> general configuration
		JMenuItem item = new JMenuItem(
				_bundle.getString("popup_tree_insert_config"));
		item.setMnemonic(_bundle.getString(
				"popup_tree_insert_config_mnemonic").charAt(0));
		item.addActionListener(this);
		item.setActionCommand(TREE_INSERT_CONFIG_GENERAL);
		item.setEnabled(false);
		menu.add(item);
		
		// Insert -> TestRunner
		item = new JMenuItem(
				_bundle.getString("popup_tree_testrunner"));
		item.setMnemonic(_bundle.getString(
				"popup_tree_testrunner_mnemonic").charAt(0));
		item.addActionListener(this);
		item.setActionCommand(TREE_INSERT_TEST_RUNNER);
		item.setEnabled(false);
		menu.add(item);
		
		// Insert -> TestSuite
		item = new JMenuItem(
				_bundle.getString("popup_tree_testsuite"));
		item.setMnemonic(_bundle.getString(
				"popup_tree_testsuite_mnemonic").charAt(0));
		item.addActionListener(this);
		item.setActionCommand(TREE_INSERT_TEST_SUITE);
		item.setEnabled(false);
		menu.add(item);
		
		// Insert -> Test
		item = new JMenuItem(
				_bundle.getString("popup_tree_test"));
		item.setMnemonic(_bundle.getString(
				"popup_tree_test_mnemonic").charAt(0));
		item.addActionListener(this);
		item.setActionCommand(TREE_INSERT_TEST);
		item.setEnabled(false);
		menu.add(item);
		
		// Delete menu
		menu = new JMenu(_bundle.getString("popup_tree_menu_delete"));
		menu.setMnemonic(
				_bundle.getString("popup_tree_menu_delete_mnemonic").charAt(0));
		popup.add(menu);
		
		// Delete -> general configuration
		item = new JMenuItem(
				_bundle.getString("popup_tree_config"));
		item.setMnemonic(_bundle.getString(
				"popup_tree_config_mnemonic").charAt(0));
		item.addActionListener(this);
		item.setActionCommand(TREE_DELETE_CONFIG_GENERAL);
		item.setEnabled(false);
		menu.add(item);
		
		// Delete -> TestRunner
		item = new JMenuItem(
				_bundle.getString("popup_tree_testrunner"));
		item.setMnemonic(_bundle.getString(
				"popup_tree_testrunner_mnemonic").charAt(0));
		item.addActionListener(this);
		item.setActionCommand(TREE_DELETE_TEST_RUNNER);
		item.setEnabled(false);
		menu.add(item);
		
		// Delete -> TestSuite
		item = new JMenuItem(
				_bundle.getString("popup_tree_testsuite"));
		item.setMnemonic(_bundle.getString(
				"popup_tree_testsuite_mnemonic").charAt(0));
		item.addActionListener(this);
		item.setActionCommand(TREE_DELETE_TEST_SUITE);
		item.setEnabled(false);
		menu.add(item);
		
		// Delete -> Test
		item = new JMenuItem(
				_bundle.getString("popup_tree_test"));
		item.setMnemonic(_bundle.getString(
				"popup_tree_test_mnemonic").charAt(0));
		item.addActionListener(this);
		item.setActionCommand(TREE_DELETE_TEST);
		item.setEnabled(false);
		menu.add(item);
		
		// Delete menu
		menu = new JMenu(_bundle.getString("popup_tree_menu_config"));
		menu.setMnemonic(
				_bundle.getString("popup_tree_menu_config_mnemonic").charAt(0));
		popup.add(menu);
		
		// Configuration -> general configuration
		item = new JMenuItem(
				_bundle.getString("popup_tree_config"));
		item.setMnemonic(_bundle.getString(
				"popup_tree_config_mnemonic").charAt(0));
		item.addActionListener(this);
		item.setActionCommand(CONFIG_GENERAL);
		item.setEnabled(false);
		menu.add(item);
		
		// Configuration -> TestRunner
		item = new JMenuItem(
				_bundle.getString("popup_tree_testrunner"));
		item.setMnemonic(_bundle.getString(
				"popup_tree_testrunner_mnemonic").charAt(0));
		item.addActionListener(this);
		item.setActionCommand(CONFIG_TEST_RUNNER);
		item.setEnabled(false);
		menu.add(item);
		
		// Configuration -> TestSuite
		item = new JMenuItem(
				_bundle.getString("popup_tree_testsuite"));
		item.setMnemonic(_bundle.getString(
				"popup_tree_testsuite_mnemonic").charAt(0));
		item.addActionListener(this);
		item.setActionCommand(CONFIG_TEST_SUITE);
		item.setEnabled(false);
		menu.add(item);
		
		// Configuration -> Test
		item = new JMenuItem(
				_bundle.getString("popup_tree_test"));
		item.setMnemonic(_bundle.getString(
				"popup_tree_test_mnemonic").charAt(0));
		item.addActionListener(this);
		item.setActionCommand(CONFIG_TEST);
		item.setEnabled(false);
		menu.add(item);
		
		// Ignore all other Tests
		item = new JMenuItem(
				_bundle.getString("popup_tree_ignore_all_other_tests"));
		item.setMnemonic(
				_bundle.getString("popup_tree_ignore_all_other_tests_mnemonic")
				.charAt(0));
		item.setActionCommand(TREE_IGNORE_ALL_OTHER_TESTS);
		item.addActionListener(this);
		item.setEnabled(false);
		popup.addSeparator();
		popup.add(item);
		
		// Ignore all selected Tests
		item = new JMenuItem(_bundle.getString("popup_tree_ignore_all_tests"));
		item.setMnemonic(
				_bundle.getString("popup_tree_ignore_all_tests_mnemonic")
				.charAt(0));
		item.setActionCommand(TREE_IGNORE_SELECTED_TESTS);
		item.addActionListener(this);
		item.setEnabled(false);
		popup.add(item);
		
		// Execute all selected Tests
		item = new JMenuItem(_bundle.getString("popup_tree_execute_all_tests"));
		item.setMnemonic(
				_bundle.getString("popup_tree_execute_all_tests_mnemonic")
				.charAt(0));
		item.setActionCommand(TREE_EXECUTE_SELECTED_TESTS);
		item.addActionListener(this);
		item.setEnabled(false);
		popup.add(item);

		// Return the popup menu
		return popup;
	}
	
	/**
	 * Create the Layout of the main window.
	 */
	private void createLayout() {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new Date());
		DecimalFormat df = new DecimalFormat("00");
		_config.setPathSuitesResult(
				df.format(gc.get(GregorianCalendar.YEAR)) +
				df.format(gc.get(GregorianCalendar.MONTH) + 1) +
				df.format(gc.get(GregorianCalendar.DAY_OF_MONTH)) +
				df.format(gc.get(GregorianCalendar.HOUR_OF_DAY)) +
				df.format(gc.get(GregorianCalendar.MINUTE)) +
				df.format(gc.get(GregorianCalendar.SECOND)));
		
		setTitle(WND_TITLE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setSize(600, 400);
		setExtendedState(MAXIMIZED_BOTH);
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.25);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		_tree = new JTree();
		_tree.setModel(new TestRunnerModel());
		_tree.setCellRenderer(new TestRunnerRenderer(_config));
		_tree.setComponentPopupMenu(treePopup());
		_tree.addMouseListener(new MouseListener() {

			/**
			 * If you double-click a test selected will toggle.
			 * 
			 * @param me Dates of the events.
			 */
			@Override
			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount() == 2) {
					Object c = _tree.getLastSelectedPathComponent();
					
					// toogle ignore on Test
					if (c instanceof org.testsuite.data.Test) {
						((org.testsuite.data.Test)c).setExecuted(
								!((org.testsuite.data.Test)c).isExecuted());
						((TestRunnerModel)_tree.getModel())
							.fireTreeNodesChanged(_tree.getSelectionPath());
					}
					
					// config for TestSuite
					else if (c instanceof TestSuite) {
						configTestSuite((TestSuite)c);
					}
					
					// config for TestRunner
					else if (c instanceof TestRunner) {
						configTestRunner((TestRunner)c);
					}
					
					// General configuration
					else if (c instanceof List<?>) {
						configGeneral();
					}
				}
			}

			/**
			 * Is not needed.
			 */
			@Override
			public void mouseEntered(MouseEvent arg0) {
				
			}

			/**
			 * Is not needed.
			 */
			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			/**
			 * Is not needed.
			 */
			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			/**
			 * Is not needed.
			 */
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			
		});
		_tree.addTreeSelectionListener(new TreeSelectionListener() {

			/**
			 * What pop-up menu items are enabled and which are disabled?
			 * 
			 * @param e Datas of the event
			 */
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				disabledAllPopupMenuItems();
				
				JPopupMenu popup = _tree.getComponentPopupMenu();
				JMenu insert = (JMenu)popup.getComponent(0);
				JMenu delete = (JMenu)popup.getComponent(1);
				JMenu config = (JMenu)popup.getComponent(2);
				
				// selected root element
				if ((e.getPath().getLastPathComponent() instanceof List<?>) &&
					e.isAddedPath()) {
					insert.getItem(0).setEnabled(true);
					insert.getItem(1).setEnabled(true);

					delete.getItem(0).setEnabled(true);

					config.getItem(0).setEnabled(true);
				}
				
				// selected test runner
				else if ((e.getPath().getLastPathComponent() instanceof 
						TestRunner) && e.isAddedPath()) {
					insert.getItem(2).setEnabled(true);

					delete.getItem(1).setEnabled(true);

					config.getItem(1).setEnabled(true);
					
					popup.getComponent(4).setEnabled(true);
					popup.getComponent(5).setEnabled(true);
					popup.getComponent(6).setEnabled(true);
				}
				
				// selected test suite
				else if ((e.getPath().getLastPathComponent() instanceof
						TestSuite) && e.isAddedPath()) {
					insert.getItem(3).setEnabled(true);

					delete.getItem(2).setEnabled(true);

					config.getItem(2).setEnabled(true);
					
					popup.getComponent(4).setEnabled(true);
					popup.getComponent(5).setEnabled(true);
					popup.getComponent(6).setEnabled(true);
				}
				
				// selected test
				else if ((e.getPath().getLastPathComponent() instanceof Test) &&
						e.isAddedPath()) {
					delete.getItem(3).setEnabled(true);

					config.getItem(3).setEnabled(true);
					
					popup.getComponent(4).setEnabled(true);
				}
			}
			
		});
		
		splitPane.setLeftComponent(new JScrollPane(_tree));
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setRightComponent(scrollPane);
		
		_txtHtml = new JEditorPane();
		_txtHtml.setContentType("text/html");
		_txtHtml.setEditorKit(new HTMLEditorKit());
		_txtHtml.setEditable(false);
		scrollPane.setViewportView(_txtHtml);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 10));
		
		_pBar = new JProgressBar();
		_pBar.setMaximum(0);
		panel.add(_pBar, BorderLayout.NORTH);
		
		JPanel panButtons = new JPanel();
		panel.add(panButtons, BorderLayout.SOUTH);
		panButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		_btnRun = new JButton(_bundle.getString("btnRun"));
		_btnRun.setEnabled(false);
		_btnRun.addActionListener(this);
		_btnRun.setActionCommand(AC_RUN);
		_btnRun.setName(AC_RUN);
		panButtons.add(_btnRun);
		
		_btnCancel = new JButton(_bundle.getString("btnCancel"));
		_btnCancel.setEnabled(false);
		_btnCancel.addActionListener(this);
		_btnCancel.setActionCommand(AC_CANCEL);
		_btnCancel.setName(AC_CANCEL);
		panButtons.add(_btnCancel);
		
		_btnAllTestsIgnore = new JButton(_bundle.getString("btnAllTestsIgnore"));
		_btnAllTestsIgnore.addActionListener(this);
		_btnAllTestsIgnore.setActionCommand(AC_ALL_TESTS_IGNORE);
		_btnAllTestsIgnore.setName(AC_ALL_TESTS_IGNORE);
		_btnAllTestsIgnore.setEnabled(false);
		panButtons.add(_btnAllTestsIgnore);
		
		_btnAllTestsExecute = new JButton(
				_bundle.getString("btnAllTestsExecute"));
		_btnAllTestsExecute.addActionListener(this);
		_btnAllTestsExecute.setActionCommand(AC_ALL_TESTS_EXECUTE);
		_btnAllTestsExecute.setName(AC_ALL_TESTS_EXECUTE);
		_btnAllTestsExecute.setEnabled(false);
		panButtons.add(_btnAllTestsExecute);
		
		_btnConfigLoad = new JButton(_bundle.getString("btnConfigLoad"));
		_btnConfigLoad.addActionListener(this);
		_btnConfigLoad.setActionCommand(AC_CONFIG_LOAD);
		_btnConfigLoad.setName(AC_CONFIG_LOAD);
		panButtons.add(_btnConfigLoad);
		
		_btnConfigValidate = new JButton(_bundle.getString("btnConfigValidate"));
		_btnConfigValidate.addActionListener(this);
		_btnConfigValidate.setActionCommand(AC_CONFIG_VALIDATE);
		_btnConfigValidate.setName(AC_CONFIG_VALIDATE);
		_btnConfigValidate.setEnabled(false);
		panButtons.add(_btnConfigValidate);
		
		_btnConfigSave = new JButton(_bundle.getString("btnConfigSave"));
		_btnConfigSave.addActionListener(this);
		_btnConfigSave.setActionCommand(AC_CONFIG_SAVE);
		_btnConfigSave.setName(AC_CONFIG_SAVE);
		_btnConfigSave.setEnabled(false);
		panButtons.add(_btnConfigSave);
		
		_btnExit = new JButton(_bundle.getString("btnExit"));
		_btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				dispose();
			}
		});
		panButtons.add(_btnExit);
		
		_txtMessage = new JTextArea();
		_txtMessage.setEditable(false);
		_spMessage = new JScrollPane(_txtMessage);
		Dimension d = new Dimension();
		d.setSize(400, 75);
		_spMessage.setPreferredSize(d);
		panel.add(_spMessage, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	/**
	 * Open the configuration dialog for general configuration
	 */
	private void configGeneral() {
		new DlgConfigGeneral(this, _config);
	}
	
	/**
	 * Open the configuration dialog for test runner
	 */
	private void configTestRunner(TestRunner tr) {
		DlgConfigTestRunner dlg = new DlgConfigTestRunner(this, tr);
		if (dlg.getExitStatus() == DlgConfig.EXIT_ACCEPT) {
			if (dlg.isTestRunnerChanged()) {
				int index = ((TestRunnerModel)_tree.getModel())
							.getTestRunnerList().indexOf(tr);
				((TestRunnerModel)_tree.getModel())
					.getTestRunnerList().remove(index);
				((TestRunnerModel)_tree.getModel())
					.getTestRunnerList().add(index, 
						dlg.getTestRunner());
				((TestRunnerModel)_tree.getModel())
					.fireTreeStructureChanged();
			}
		}
	}
	
	/**
	 * Open the configuration dialog for test suite
	 */
	private void configTestSuite(TestSuite ts) {
		DlgConfigTestSuite dlg = new DlgConfigTestSuite(this, ts.getName(),
				ts.getPackage());
		if (dlg.getExitStatus() == DlgConfig.EXIT_ACCEPT) {
			ts.setName(dlg.getTestSuiteName());
			ts.setPackage(dlg.getPackageName());
		}
	}
	
	/**
	 * Returns the configuration.
	 * 
	 * @return Configuration
	 */
	public Config getConfig() {
		return _config;
	}

	/**
	 * Responds to a button click.
	 * 
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case AC_CANCEL:
				_thread.interrupt();
			break;
				
			case AC_CONFIG_LOAD:
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle(_bundle.getString("configFileOpenTitle"));
				fc.setFileFilter(new FileNameExtensionFilter(
						_bundle.getString("configFileFilter"), "xml"));
				fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
				int state = fc.showOpenDialog(this);
				File file = fc.getSelectedFile();
				
				if ((state == JFileChooser.APPROVE_OPTION) && (file != null) && 
						(file.exists())) {
					ConfigParser parser = new ConfigParser(_config, 
							file.getPath());
					
					if (parser.parse()) {
						((TestRunnerModel)_tree.getModel()).setListOfTestRunner(
								parser.getTestRunnerList());
						((TestRunnerModel)_tree.getModel())
								.validateConfiguration();
						_config = parser.getConfig();
						_btnRun.setEnabled(true);
						_btnConfigSave.setEnabled(true);
						_btnConfigValidate.setEnabled(true);
						_btnAllTestsIgnore.setEnabled(true);
						_btnAllTestsExecute.setEnabled(true);
						
						int tests = 0;
						for (int index = 0; 
								index < parser.getTestRunnerList().size(); 
								index++)
							tests += parser.getTestRunnerList().get(index)
								.getTestsCount();
						_pBar.setMaximum(tests);
						_pBar.setValue(0);
					} 
				}
				break;
				
			case AC_CONFIG_SAVE:
				fc = new JFileChooser();
				fc.setDialogTitle(_bundle.getString("configFileSaveTitle"));
				fc.setFileFilter(new FileNameExtensionFilter(
						_bundle.getString("configFileFilter"), "xml"));
				fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
				state = fc.showOpenDialog(this);
				file = fc.getSelectedFile();
				if ((state == JFileChooser.APPROVE_OPTION) && (file != null)) {
					if (file.exists()) {
						int s = JOptionPane.showConfirmDialog(this, 
								_bundle.getString("fileExistsMessage"),
								_bundle.getString("fileExistsTitle"),
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
						if (s == JOptionPane.NO_OPTION) {
							break;
						}
					}
					
					ConfigSaver.save(_config, 
							((TestRunnerModel)_tree.getModel())
							.getTestRunnerList(), file);
				}
				break;
				
			case AC_CONFIG_VALIDATE:
				((TestRunnerModel)_tree.getModel()).validateConfiguration();
				_tree.updateUI();
				break;
				
			case AC_RUN:
				_thread = new TestRun(((TestRunnerModel)_tree.getModel())
					.getTestRunnerList(), this);
				// OPT Into own function with boolean as parameter
				_btnExit.setEnabled(false);
				_btnRun.setEnabled(false);
				_btnCancel.setEnabled(true);
				_btnConfigLoad.setEnabled(false);
				_btnConfigSave.setEnabled(false);
				_btnConfigValidate.setEnabled(false);
				_btnAllTestsIgnore.setEnabled(false);
				_btnAllTestsExecute.setEnabled(false);
				_txtMessage.setText(new String());
				_thread.start();				
				break;
				
			case AC_ALL_TESTS_IGNORE:
				// OPT Into a separate method with boolean as parameter
				List<TestRunner> list = 
					((TestRunnerModel)_tree.getModel()).getTestRunnerList();
				for (int i = 0; i < list.size(); i++)
					for (int j = 0; j < list.get(i).testSuiteCount(); j++)
						for (int k = 0; k < list.get(i).getTestSuite(j).testCount(); k++)
							list.get(i).getTestSuite(j).getTest(k).setExecuted(false);
				break;
				
			case AC_ALL_TESTS_EXECUTE:
				// OPT Into a separate method with boolean as parameter
				list = 
					((TestRunnerModel)_tree.getModel()).getTestRunnerList();
				for (int i = 0; i < list.size(); i++)
					for (int j = 0; j < list.get(i).testSuiteCount(); j++)
						for (int k = 0; k < list.get(i).getTestSuite(j).testCount(); k++)
							list.get(i).getTestSuite(j).getTest(k).setExecuted(true);
				break;
				
			case CONFIG_GENERAL:
				configGeneral();
				break;
				
			case CONFIG_TEST_RUNNER:
				configTestRunner(
						(TestRunner)_tree.getLastSelectedPathComponent());
				break;
				
			case CONFIG_TEST_SUITE:
				configTestSuite(
						(TestSuite)_tree.getLastSelectedPathComponent());
				break;
				
			case CONFIG_TEST:
				TreePath path = _tree.getSelectionPath();
				Test test = (Test)path.getLastPathComponent();
				DlgConfigTest dlg = new DlgConfigTest(this, test.getName(), 
						test.isExecuted());
				if (dlg.getExitStatus() == DlgConfigTest.EXIT_ACCEPT) {
					test.setName(dlg.getTestName());
					test.setExecuted(dlg.isTestExecute());
				}
				break;
				
			case TREE_INSERT_CONFIG_GENERAL:
				break;
				
			case TREE_INSERT_TEST_RUNNER:
				((TestRunnerModel)_tree.getModel()).getTestRunnerList().add(
						new JunitTestRunner(_config));
				_tree.updateUI();
				// OPT Into a separate method with boolean as parameter
				_btnConfigValidate.setEnabled(true);
				_btnConfigSave.setEnabled(true);
				_btnAllTestsIgnore.setEnabled(true);
				_btnAllTestsExecute.setEnabled(true);
				_btnRun.setEnabled(true);
				break;
				
			case TREE_INSERT_TEST_SUITE:
				((TestRunner)_tree.getLastSelectedPathComponent()).addTestSuite(
						new TestSuite());
				_tree.updateUI();
				break;
				
			case TREE_INSERT_TEST:
				((TestSuite)_tree.getLastSelectedPathComponent()).addTest(
						new Test());
				_tree.updateUI();
				break;
				
			case TREE_DELETE_CONFIG_GENERAL:
				int ret = JOptionPane.showConfirmDialog(this, 
						_bundle.getString("delete_config_general_message"),
						_bundle.getString("delete_config_general_title"),
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.QUESTION_MESSAGE);
				if (ret == JOptionPane.YES_OPTION) {
					((TestRunnerModel)_tree.getModel()).getTestRunnerList()
						.clear();
					_config.clear();
					_tree.updateUI();
					// Opt Into separate method with boolean as parameter
					_btnConfigValidate.setEnabled(false);
					_btnConfigSave.setEnabled(false);
					_btnAllTestsIgnore.setEnabled(false);
					_btnAllTestsExecute.setEnabled(false);
					_btnRun.setEnabled(false);
				}
				break;
				
			case TREE_DELETE_TEST_RUNNER:
				ret = JOptionPane.showConfirmDialog(this, 
						_bundle.getString("delete_test_runner_message"),
						_bundle.getString("delete_test_runner_title"),
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.QUESTION_MESSAGE);
				if (ret == JOptionPane.YES_OPTION) {
					int index = ((TestRunnerModel)_tree.getModel())
							.getTestRunnerList().indexOf(
									(TestRunner)_tree
										.getLastSelectedPathComponent());
					((TestRunnerModel)_tree.getModel()).getTestRunnerList()
						.remove(index);
					_tree.updateUI();
					_tree.clearSelection();
					
					if (_config.isEmpty() && 
							(((TestRunnerModel)_tree.getModel())
									.getTestRunnerList().size() == 0)) {
						// Opt Into separate method with boolean as parameter
						_btnConfigValidate.setEnabled(false);
						_btnConfigSave.setEnabled(false);
						_btnAllTestsIgnore.setEnabled(false);
						_btnAllTestsExecute.setEnabled(false);
						_btnRun.setEnabled(false);
					}
				}
				break;
				
			case TREE_DELETE_TEST_SUITE:
				ret = JOptionPane.showConfirmDialog(this, 
						_bundle.getString("delete_test_suite_message"),
						_bundle.getString("delete_test_suite_title"),
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.QUESTION_MESSAGE);
				if (ret == JOptionPane.YES_OPTION) {
					path = _tree.getSelectionPath();
					((TestRunner)path.getPathComponent(1)).removeTestSuite(
							(TestSuite)path.getLastPathComponent());
					_tree.updateUI();
				}
				break;
				
			case TREE_DELETE_TEST:
				ret = JOptionPane.showConfirmDialog(this, 
						_bundle.getString("delete_test_message"),
						_bundle.getString("delete_test_title"),
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.QUESTION_MESSAGE);
				if (ret == JOptionPane.YES_OPTION) {
					path = _tree.getSelectionPath();
					((TestSuite)path.getPathComponent(2)).removeTest(
							(Test)path.getLastPathComponent());
					_tree.updateUI();
				}
				break;
				
			case TREE_IGNORE_SELECTED_TESTS:
				// OPT Into a method with boolean as parameter
				if (_tree.getLastSelectedPathComponent() instanceof TestRunner) {
					TestRunner runner = 
							(TestRunner)_tree.getLastSelectedPathComponent();
					for (int suite = 0; suite < runner.testSuiteCount(); suite++)
						for (int i = 0; i < runner.getTestSuite(suite).testCount(); i++)
							runner.getTestSuite(suite).getTest(i).setExecuted(false);
				} else if (_tree.getLastSelectedPathComponent() instanceof TestSuite) {
					TestSuite suite = 
							(TestSuite)_tree.getLastSelectedPathComponent();
					for (int i = 0; i < suite.testCount(); i++)
						suite.getTest(i).setExecuted(false);
				}
				break;
				
			case TREE_EXECUTE_SELECTED_TESTS:
				// OPT Into a method with boolean as parameter
				if (_tree.getLastSelectedPathComponent() instanceof TestRunner) {
					TestRunner runner = 
							(TestRunner)_tree.getLastSelectedPathComponent();
					for (int suite = 0; suite < runner.testSuiteCount(); suite++)
						for (int i = 0; i < runner.getTestSuite(suite).testCount(); i++)
							runner.getTestSuite(suite).getTest(i).setExecuted(true);
				} else if (_tree.getLastSelectedPathComponent() instanceof TestSuite) {
					TestSuite suite = 
							(TestSuite)_tree.getLastSelectedPathComponent();
					for (int i = 0; i < suite.testCount(); i++)
						suite.getTest(i).setExecuted(true);
				}
				break;
				
			case TREE_IGNORE_ALL_OTHER_TESTS:
				// OPT Into a method with boolean as parameter
				if (_tree.getLastSelectedPathComponent() instanceof TestRunner) {
					list = 
							((TestRunnerModel)_tree.getModel())
							.getTestRunnerList();
					for (int i = 0; i < list.size(); i++) {
						TestRunner runner = list.get(i);
						if (runner == 
								(TestRunner)_tree.getLastSelectedPathComponent())
							continue;
						for (int suite = 0; suite < runner.testSuiteCount(); suite++)
							for (int j = 0; j < runner.getTestSuite(suite).testCount(); j++)
								runner.getTestSuite(suite).getTest(j).setExecuted(false);
					}
				} else if (_tree.getLastSelectedPathComponent() instanceof TestSuite) {
					list = 
							((TestRunnerModel)_tree.getModel())
							.getTestRunnerList();
					for (int i = 0; i < list.size(); i++) {
						TestRunner runner = list.get(i);
						for (int j = 0; j < runner.testSuiteCount(); j++) {
							TestSuite suite = runner.getTestSuite(j);
							if (suite == 
									(TestSuite)_tree.getLastSelectedPathComponent())
								continue;
							for (int k = 0; k < suite.testCount(); k++)
								suite.getTest(k).setExecuted(false);
						}
					}
				} else if (_tree.getLastSelectedPathComponent() instanceof Test) {
					list = 
							((TestRunnerModel)_tree.getModel())
							.getTestRunnerList();
					for (int i = 0; i < list.size(); i++) {
						TestRunner runner = list.get(i);
						for (int j = 0; j < runner.testSuiteCount(); j++) {
							TestSuite suite = runner.getTestSuite(j);
							for (int k = 0; k < suite.testCount(); k++) {
								if (suite.getTest(k) == 
										(Test)_tree
										.getLastSelectedPathComponent())
									continue;
								suite.getTest(k).setExecuted(false);
							}
						}
					}
				}
				break;
		}
	}

	/**
	 * All tests have been completed.
	 * 
	 * @param te Data of the event.
	 */
	@Override
	public void testExecutedCompleted(TestEvent te) {
		_pBar.setValue(_pBar.getValue() + 1);
		String tmp = te.getPackageName() + "." + te.getName() + ": " + 
				te.getResult();
		_txtMessage.setText(_txtMessage.getText() + System.lineSeparator() + tmp);
		_txtMessage.setRows(_txtMessage.getRows() + 1);
		_spMessage.getVerticalScrollBar().getModel().setValue(
				_spMessage.getVerticalScrollBar().getModel().getValue() +
				_txtMessage.getRows());
	}
	
	@Override
	public void testEnd(TestEvent te) {
		_thread = null;
		_pBar.setValue(0);
		_btnExit.setEnabled(true);
		_btnRun.setEnabled(true);
		_btnCancel.setEnabled(false);
		_btnConfigLoad.setEnabled(true);
		_btnConfigSave.setEnabled(true);
		_btnConfigValidate.setEnabled(true);
		_btnAllTestsIgnore.setEnabled(true);
		_btnAllTestsExecute.setEnabled(true);

		// Create HTML ouput
		String htmlFile = _config.getPathResult() + File.separator;
		File f = new File(htmlFile);
		if (!f.exists())
			f.mkdirs();
		
		htmlFile += _bundle.getString("html_result") + "_" +
				_config.getPathSuitesResult() + ".html";
		try {
			
			HtmlOut html = new HtmlOut(htmlFile);
			html.htmlHead();
			
			List<TestRunner> testRunner = 
					((TestRunnerModel)_tree.getModel())
					.getTestRunnerList();
			
			for (int runner = 0; runner < testRunner.size(); runner++) {
				boolean line = true;
				if (runner == 0)
					line = false;
				html.writeHtml(testRunner.get(runner).createHtml(html, 
						line));
			}
			
			html.writeHtml(TestRunner.createHtmlAllResultTable(testRunner));
			
			html.htmlEnd();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		// Read Html
		try {
			f = new File(htmlFile);
			if (f.exists())
				_txtHtml.setPage(f.toURI().toURL());
		} catch (Exception er) {
			er.printStackTrace();
		}
	}
	
	/**
	 * Disabled all pop-up menu items
	 */
	private void disabledAllPopupMenuItems() {
		JPopupMenu popup = _tree.getComponentPopupMenu();
		JMenu insert = (JMenu)popup.getComponent(0);
		JMenu delete = (JMenu)popup.getComponent(1);
		JMenu config = (JMenu)popup.getComponent(2);

		insert.getItem(0).setEnabled(false);
		insert.getItem(1).setEnabled(false);
		insert.getItem(2).setEnabled(false);
		insert.getItem(3).setEnabled(false);

		delete.getItem(0).setEnabled(false);
		delete.getItem(1).setEnabled(false);
		delete.getItem(2).setEnabled(false);
		delete.getItem(3).setEnabled(false);

		config.getItem(0).setEnabled(false);
		config.getItem(1).setEnabled(false);
		config.getItem(2).setEnabled(false);
		config.getItem(3).setEnabled(false);
		
		popup.getComponent(4).setEnabled(false);
		popup.getComponent(5).setEnabled(false);
		popup.getComponent(6).setEnabled(false);
	}

	/**
	 * Starts the test app.
	 * 
	 * @param args Parameters from command line.
	 */
	public static void main(String[] args) {
		new App();
	}
}
