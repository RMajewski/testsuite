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

package tests.testsuite.app;

import java.awt.Component;
import java.io.File;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JCheckBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.SchemaFactory;

import org.netbeans.jemmy.ClassReference;
import org.netbeans.jemmy.Scenario;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JCheckBoxOperator;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.operators.JFileChooserOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JListOperator;
import org.netbeans.jemmy.operators.JMenuItemOperator;
import org.netbeans.jemmy.operators.JMenuOperator;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.netbeans.jemmy.operators.JProgressBarOperator;
import org.netbeans.jemmy.operators.JTextAreaOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.jemmy.operators.JTreeOperator;
import org.testsuite.app.App;
import org.testsuite.app.DlgConfigGeneral;
import org.testsuite.app.TestRunnerModel;
import org.testsuite.core.JunitTestRunner;
import org.testsuite.core.TestRunner;
import org.testsuite.data.Config;
import org.testsuite.data.Test;
import org.testsuite.data.TestSuite;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 * Tests the test suite app.
 * 
 * In Version 0.3 the structure has been revised.
 * 
 * @author René Majewski
 *
 * @version 0.3
 */
public class TestApp implements Scenario {
	
	/**
	 * Saves the instance of button for all tests select execute
	 */
	protected JButtonOperator _btnAllTestsExecute;
	
	/**
	 * Saves the instance of button for all tests select ignore
	 */
	protected JButtonOperator _btnAllTestsIgnore;
	
	/**
	 * Saves the instance of button for cancel tests.
	 */
	protected JButtonOperator _btnCancel;
	
	/**
	 * Saves the instance of button for save configuration
	 */
	protected JButtonOperator _btnConfigSave;
	
	/**
	 * Saves the instance of button for validate configuration
	 */
	protected JButtonOperator _btnConfigValidate;
	
	/**
	 * Saves the instance of button for exit the app.
	 */
	protected JButtonOperator _btnExit;
	
	/**
	 * Saves the instance of button for load the configuration file.
	 */
	protected JButtonOperator _btnLoad;
	
	/**
	 * Saves the instance of button for run tests.
	 */
	protected JButtonOperator _btnRun;
	
	/**
	 * Saves the instance of the resource bundle.
	 */
	protected ResourceBundle _bundle;
	
	/**
	 * Saves the instance of dialog
	 */
	protected JDialogOperator _dlg;
	
	/**
	 * Saves the instance of second dialog
	 */
	protected JDialogOperator _dlg2;
	
	/**
	 * Saves the instance of open configuration file dialog.
	 */
	protected JFileChooserOperator _fileChooser;
	
	/**
	 * Saves the instance of pop-up menu
	 */
	protected JPopupMenuOperator _popup;
	
	/**
	 * Saves the instance of progress bar.
	 */
	protected JProgressBarOperator _progress;
	
	/**
	 * Saves the instance of tree.
	 */
	protected JTreeOperator _tree;
	
	/**
	 * Saves the instance of text pane.
	 */
	protected JTextAreaOperator _txtHtml;
	
	/**
	 * Saves the instance of the main window.
	 */
	protected JFrameOperator _wnd;
	
	/**
	 * Initializes the tests.
	 */
	public TestApp() {
		try {
			new ClassReference("org.testsuite.app.App").startApplication();
			_bundle = ResourceBundle.getBundle(App.BUNDLE_FILE);
		} catch (Exception e) {
			e.printStackTrace();
		}

		_wnd = new JFrameOperator(App.WND_TITLE);
		_tree = new JTreeOperator(_wnd);
		_txtHtml = new JTextAreaOperator(_wnd, 0);
		_progress = new JProgressBarOperator(_wnd);
		_btnRun = new JButtonOperator(_wnd, _bundle.getString("btnRun"));
		_btnCancel = new JButtonOperator(_wnd, _bundle.getString("btnCancel"));
		_btnLoad = new JButtonOperator(_wnd, _bundle.getString("btnConfigLoad"));
		_btnExit = new JButtonOperator(_wnd, _bundle.getString("btnExit"));
		_btnConfigSave = new JButtonOperator(_wnd, 
				_bundle.getString("btnConfigSave"));
		_btnConfigValidate = new JButtonOperator(_wnd, 
				_bundle.getString("btnConfigValidate"));
		_btnAllTestsIgnore = new JButtonOperator(_wnd, 
				_bundle.getString("btnAllTestsIgnore"));
		_btnAllTestsExecute = new JButtonOperator(_wnd, 
				_bundle.getString("btnAllTestsExecute"));
	}
	
	/**
	 * Returns whether the selected configuration file exists.
	 * 
	 * @return Exists the configuration file?
	 */
	public boolean existsConfigurationFile() {
		File file = _fileChooser.getSelectedFile();
		return file.exists();
	}
	
	/**
	 * Sets the text in text field in the dialog.
	 * 
	 * @param text Text to sets into the text field.
	 */
	public void fileDialogTextFieldSetText(String text) {
		if (_fileChooser != null) {
			JTextFieldOperator tf = new JTextFieldOperator(_fileChooser);
			tf.setText(text);
		}
	}
	
	/**
	 * Generate a test configuration.
	 */
	public void generateTestConfiguration() {
		TestSuite suite1 = new TestSuite();
		suite1.addTest(new Test("Test1"));
		suite1.addTest(new Test("Test2"));
		
		TestSuite suite2 = new TestSuite();
		suite2.addTest(new Test("Test3"));
		suite2.addTest(new Test("Test4"));
		
		TestSuite suite3 = new TestSuite();
		suite3.addTest(new Test("Test5"));
		suite3.addTest(new Test("Test6"));

		JunitTestRunner runner1 = new JunitTestRunner();
		runner1.addTestSuite(suite1);
		runner1.addTestSuite(suite2);
		
		JunitTestRunner runner2 = new JunitTestRunner();
		runner2.addTestSuite(suite3);
		
		List<TestRunner> list = ((TestRunnerModel)_tree.getModel())
				.getTestRunnerList();
		list.add(runner1);
		list.add(runner2);
		((TestRunnerModel)_tree.getModel()).setListOfTestRunner(list);
	}
	
	/**
	 * Returns the the name selected configuration file.
	 * 
	 * @param lastPaths Paths that you want to include.
	 */
	public String getConfigurationFileName(int lastPaths) {
		File file = _fileChooser.getSelectedFile();
		String[] tmp = file.getPath().split(File.separator);
		StringBuilder ret = new StringBuilder();
		for (int i = tmp.length - 1 - lastPaths; i < tmp.length; i++) {
			ret.append(tmp[i]);
			if (i < tmp.length - 1)
				ret.append(File.separator);
		}
		return ret.toString();
	}

	/**
	 * Returns the specified button from dialog 2
	 *  
	 * @param index Index at which the button is located.
	 * 
	 * @return The specified button
	 */
	public JButtonOperator getDialog2Button(int index) {
		return new JButtonOperator(_dlg2, index);
	}
	
	/**
	 * Returns the specified text field from dialog 2
	 * 
	 * @param index Index at which the text field is located.
	 * 
	 * @return The specified text field
	 */
	public JTextFieldOperator getDialog2TextField(int index) {
		return new JTextFieldOperator(_dlg2, index);
	}
	
	/**
	 * Returns the specified button
	 *  
	 * @param index Index at which the button is located.
	 * 
	 * @return The specified button
	 */
	public JButtonOperator getDialogButton(int index) {
		return new JButtonOperator(_dlg, index);
	}
	
	/**
	 * Returns the specified text area
	 * 
	 * @param index Index at which the text area is located.
	 * 
	 * @return The specified text area
	 */
	public JTextAreaOperator getDialogTextArea(int index) {
		return new JTextAreaOperator(_dlg, index);
	}
	
	/**
	 * Returns the specified check box
	 * 
	 * @param index Index at which the check box is located.
	 * 
	 * @return The specified check box
	 */
	public JCheckBoxOperator getDialogCheckBox(int index) {
		return new JCheckBoxOperator(_dlg, index);
	}
	
	/**
	 * Returns the specified list
	 * 
	 * @param index Index at which the list is located.
	 * 
	 * @return The specified list
	 */
	public JListOperator getDialogList(int index) {
		return new JListOperator(_dlg, index);
	}
	
	/**
	 * Returns the specified text field
	 * 
	 * @param index Index at which the text field is located.
	 * 
	 * @return The specified text field
	 */
	public JTextFieldOperator getDialogTextField(int index) {
		return new JTextFieldOperator(_dlg, index);
	}
	
	/**
	 * Determines the number of tests to executed.
	 * 
	 * @return Number of tests to executed.
	 */
	public int getExecutedTests() {
		int ret = 0;

		List<TestRunner> list = ((TestRunnerModel)_tree.getModel()).getTestRunnerList();
		for (int runner = 0; runner < list.size(); runner++)
			for (int suite = 0; suite < list.get(runner).testSuiteCount(); suite++)
				for (int test = 0; test < list.get(runner).getTestSuite(suite).testCount(); test++)
					if (list.get(runner).getTestSuite(suite).getTest(test).isExecuted())
						ret++;
		
		return ret;
	}
	
	/**
	 * Return the general configuration
	 * 
	 * @return The general configuration
	 */
	public Config getGeneralConfiguration() {
		return ((DlgConfigGeneral)_dlg.getSource()).getConfig();
	}
	
	/**
	 * Determines the maximum value of progress bar
	 * 
	 * @return Maximum value of press bar.
	 */
	public int getMaximumOfProgressBar() {
		return _progress.getMaximum();
	}
	
	/**
	 * Determines the minimum value of progress bar
	 * 
	 * @return Minimum value of press bar.
	 */
	public int getMinimumOfProgressBar() {
		return _progress.getMinimum();
	}
	
	/**
	 * Returns the number of nodes in the tree.
	 * 
	 * @return Number of nodes in the tree.
	 */
	public int getTreeRootItemCount() {
		return _tree.getModel().getChildCount(_tree.getModel().getRoot());
	}
	
	/**
	 * Determines the actual value of progress bar
	 * 
	 * @return Value of progress bar.
	 */
	public int getValueOfProgressBar() {
		return _progress.getValue();
	}
	
	/**
	 * Intercepts the FileChooserDialog.
	 */
	public void interceptFileChooserDialog() {
		_fileChooser = new JFileChooserOperator();
	}

	/**
	 * Determines whether the button for all tests select execute is enabled.
	 */
	public boolean isAllTestsExecuteButtonEnabled() {
		return _btnAllTestsExecute.isEnabled();
	}
	
	/**
	 * Determines whether the button for all tests select execute is visible.
	 */
	public boolean isAllTestsExecuteButtonVisible() {
		return _btnAllTestsExecute.isVisible();
	}
	
	/**
	 * Verifies that all tests are ignored.
	 * 
	 * @return All tests are ignored?
	 */
	public boolean isAllTestsIgnore() {
		boolean ret = true;
		
		List<TestRunner> list = ((TestRunnerModel)_tree.getModel()).getTestRunnerList();
		for (int runner = 0; runner < list.size(); runner++)
			for (int suite = 0; suite < list.get(runner).testSuiteCount(); suite++)
				for (int test = 0; test < list.get(runner).getTestSuite(suite).testCount(); test++)
					if (list.get(runner).getTestSuite(suite).getTest(test).isExecuted())
						return false;
		
		return ret;
	}
	
	/**
	 * Determines whether the button for all tests select ignore is enabled.
	 */
	public boolean isAllTestsIgnoreButtonEnabled() {
		return _btnAllTestsIgnore.isEnabled();
	}
	
	/**
	 * Determines whether the button for all tests select ignore is visible.
	 */
	public boolean isAllTestsIgnoreButtonVisible() {
		return _btnAllTestsIgnore.isVisible();
	}
	
	/**
	 * Determines whether the button for cancel tests is visible.
	 */
	public boolean isCancelButtonEnabled() {
		return _btnCancel.isEnabled();
	}
	
	/**
	 * Determines whether the button for save configuration file is enabled.
	 */
	public boolean isConfigSaveButtonEnabled() {
		return _btnConfigSave.isEnabled();
	}
	
	/**
	 * Returns whether the selected configuration file validated.
	 * 
	 * @return Validated the configuration file?
	 */
	public boolean isConfigurationFileValide() {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setValidating(false);
			factory.setNamespaceAware(true);
			
			SchemaFactory schema = SchemaFactory.newInstance(
					"http://www.w3.org/2001/XMLSchema");
			factory.setSchema(schema.newSchema(getClass().getClassLoader()
					.getResource("resources/xml/config.xsd")));
			
			SAXParser parser = factory.newSAXParser();
			
			XMLReader reader = parser.getXMLReader();
			reader.parse(new InputSource("result/test.xml"));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Determines whether the button for validate configuration is enabled.
	 */
	public boolean isConfigValidateButtonEnabled() {
		return _btnConfigValidate.isEnabled();
	}
	
	/**
	 * Determines whether the dialog 2 is visible or not.
	 * 
	 * @return Is the dialog 2 visible?
	 */
	public boolean isDialog2Visible() {
		return _dlg2.isVisible();
	}
	
	/**
	 * Determines whether the button for exit the App is enabled.
	 */
	public boolean isExitButtonEnabled() {
		return _btnExit.isEnabled();
	}
	
	/**
	 * Returns is the file dialog showing or not.
	 * 
	 * @return Is the file dialog showing?
	 */
	public boolean isFileDialogShowing() {
		if (_fileChooser != null) {
			return _fileChooser.isShowing();
		}
		
		return false;
	}
	
	/**
	 * Determines whether the button for load configuration file is enabled.
	 */
	public boolean isLoadButtonEnabled() {
		return _btnLoad.isEnabled();
	}
	
	/**
	 * Determines whether the button for run tests is enabled.
	 */
	public boolean isRunButtonEnabled() {
		return _btnRun.isEnabled();
	}
	
	/**
	 * Open the pop-up from specified list.
	 * 
	 * @param list List of the pop-up menu to open.
	 */
	public void openConfigPopup(int list) {
		getDialogList(list).clickForPopup();
		_popup = new JPopupMenuOperator();
	}
	
	/**
	 * open the pop-up from tree
	 */
	public void openTreePopup() {
		_txtHtml.clickMouse();
		_tree.clickForPopup();
		_popup = new JPopupMenuOperator();
	}
	
	/**
	 * Click on the button "Cancel" in the main window
	 */
	public void pushButtonCancel() {
		_btnCancel.push();
	}
	
	/**
	 * Click on the button "All tests ignore" 
	 */
	public void pushButtonExecuteAllTests() {
		_btnAllTestsExecute.push();
	}
	
	/**
	 * Click on the button "All tests ignore" 
	 */
	public void pushButtonIgnoreAllTests() {
		_btnAllTestsIgnore.push();
	}
	
	/**
	 * Click on the button to run the tests.
	 */
	public void pushButtonRun() {
		_btnRun.push();
	}
	
	/**
	 * Click on the specified button on file dialog.
	 * 
	 * @param index The button index
	 */
	public void pushFileDialogButton(int index) {
		JButtonOperator btn;
		if (_fileChooser != null) {
			btn = new JButtonOperator(_fileChooser, index);
			btn.push();
		}
	}
	
	/**
	 * Click on load configuration file button.
	 */
	public void pushNoBlockButtonLoadConfigurationFile() {
		_btnLoad.pushNoBlock();
	}

	/**
	 * Click on save configuration file button.
	 */
	public void pushNoBlockButtonSaveConfigurationFile() {
		_btnConfigSave.pushNoBlock();
	}
	
	/**
	 * Click on the specified pop-up menu item and open dialog 2.
	 * 
	 * @param index Specified pop-up menu item
	 * 
	 * @param name Name of the dialog
	 */
	public void pushNoBlockConfigPopup(int index, String name) {
		new JMenuItemOperator((JMenuItem)_popup.getComponent(index))
			.pushNoBlock();
		_dlg2 = new JDialogOperator(_wnd, name);
	}
	
	/**
	 * Click on the specified button on file dialog.
	 * 
	 * @param index The button index
	 */
	public void pushNoBlockFileDialogButton(int index) {
		JButtonOperator btn;
		if (_fileChooser != null) {
			btn = new JButtonOperator(_fileChooser, index);
			btn.pushNoBlock();
		}
	}
	
	/**
	 * Clicked on the specified pop-up menu from tree the specified menu item.
	 * 
	 * @param menu Menu by the menu item is located.
	 * 
	 * @param item Menu item to be clicked on.
	 */
	public void pushNoBlockTreePopupItem(int menu, int item) {
		JMenuOperator mo = new JMenuOperator((JMenu)_popup.getComponent(menu));
		_popup.pushMenuNoBlock(mo.getText() + "|" + mo.getItem(item).getText());
	}
	
	/**
	 * Clicked on the specified pop-up menu from tree the specified menu item.
	 * 
	 * @param menu Menu by the menu item is located.
	 * 
	 * @param item Menu item to be clicked on.
	 */
	public void pushTreePopupItem(int menu, int item) {
		JMenuOperator mo = new JMenuOperator((JMenu)_popup.getComponent(menu));
		_popup.pushMenu(mo.getText() + "|" + mo.getItem(item).getText());
	}
	
	/**
	 * Clicked on the specified pop-up menu from tree the specified menu item.
	 * 
	 * @param item Pop-up menu item to be clicked on.
	 */
	public void pushTreePopupItem(int item) {
		_popup.pushMenu(((JMenuItem)_popup.getComponent(item)).getText());
	}
	
	/**
	 * Is not needed.
	 */
	@Override
	public int runIt(Object arg0) {
		return 0;
	}
	
	/**
	 * Select the specified row
	 * 
	 * @param row Row to be selected.
	 */
	public void selectElementInTree(int row) {
		_tree.setSelectionRow(row);
	}
	
	/**
	 * Expand the specified row.
	 * 
	 * @param row Row to be expanded.
	 */
	public void treeExpand(int row) {
		_tree.expandRow(row);
	}

	/**
	 * Tree expand.
	 */
	public void treeExpandAll() {
		_tree.expandRow(0);
		_tree.expandRow(1);
		_tree.expandRow(2);
	}
	
	/**
	 * Wait until the dialog has been opened.
	 *  
	 * @param name Name of the Dialog
	 */
	public void waitForDialog(String name) {
		_dlg = new JDialogOperator(_wnd, name);
	}
	
	/**
	 * Wait until the dialog has been opened.
	 *  
	 * @param name Name of the Dialog
	 */
	public void waitForDialog2(String name) {
		_dlg2 = new JDialogOperator(_wnd, name);
	}
	
	/**
	 * Determines whether the specified check box from the tree is enabled.
	 * 
	 * @param index Specifying the check box.
	 * 
	 * @return If the check box is enabled?
	 */
	public boolean isCheckBoxFromTreeEnabled(int index) {
		Component c = _tree.getRenderedComponent(_tree.getPathForRow(index));
		if (c instanceof JCheckBox) {
			JCheckBoxOperator checkbox = new JCheckBoxOperator((JCheckBox)c);
			return checkbox.isEnabled();
		}
		
		return false;
	}
	
	/**
	 * Click on the button "Validate configuration"
	 */
	public void pushButtonValidateConfiguration() {
		_btnConfigValidate.push();
	}
	
	/**
	 * Checks whether the HTML file was created.
	 * 
	 * @return Was HTML file created?
	 */
	public boolean existsResultHtmlFile() throws Exception{
		Config config = ((App)_wnd.getSource()).getConfig();
		String html = config.getPathResult() + File.separator +
				_bundle.getString("html_result") + "_" +
				config.getPathSuitesResult() + ".html";
		
		System.out.println(html);
		File file = new File(html);
		return file.exists();
	}
	
	/**
	 * Determines whether the main window is visible.
	 */
	public boolean isMainWindowVisible() {
		return _wnd.isVisible();
	}
	
	/**
	 * Determines whether the tree is enabled.
	 */
	public boolean isTreeEnabled() {
		return _tree.isEnabled();
	}
	
	/**
	 * Determines whether the text pane for html output is enabled.
	 */
	public boolean isTextPaneEnabled() {
		return _txtHtml.isEnabled();
	}
	
	/**
	 * Determines whether the text pane for html output is editable.
	 */
	public boolean isTextPaneEditable() {
		return _txtHtml.isEditable();
	}
	
	/**
	 * Determines whether the progress bar is enabled.
	 */
	public boolean isProgressBarEnabled() {
		return _progress.isEnabled();
	}
	
	/**
	 * Determines whether the button for run tests is visible.
	 */
	public boolean isRunButtonVisible() {
		return _btnRun.isVisible();
	}
	
	/**
	 * Determines whether the button for cancel tests is visible.
	 */
	public boolean isCancelButtonVisible() {
		return _btnCancel.isVisible();
	}
	
	/**
	 * Determines whether the button for load configuration file is visible.
	 */
	public boolean isLoadButtonVisible() {
		return _btnLoad.isVisible();
	}
	
	/**
	 * Determines whether the button for save configuration file is visible.
	 */
	public boolean isConfigSaveButtonVisible() {
		return _btnConfigSave.isVisible();
	}
	
	/**
	 * Determines whether the button for validate configuration is visible.
	 */
	public boolean isConfigValidateButtonVisible() {
		return _btnConfigValidate.isVisible();
	}
	
	/**
	 * Determines whether the button for exit the app is visible.
	 */
	public boolean isExitButtonVisible() {
		return _btnExit.isVisible();
	}
	
	/**
	 * Click on the exit button
	 */
	public void pushExitButton() {
		_btnExit.push();
	}
	
	/**
	 * Checks if the specified pop.up menu entry named specified.
	 * 
	 * @param item Specified pop-up menu entry.
	 * 
	 * @param text Text he is supposed to have.
	 * 
	 * @return Wearing the specified pop-up menu listing the given text?
	 */
	public boolean havePopupItem(int item, String text) {
		return ((JMenuItem)_popup.getComponent(item)).getText().equals(text);
	}
	
	/**
	 * Determines whether the specified pop-up menu item is enabled or not.
	 * 
	 * @param item Specified pop-up menu entry.
	 * 
	 * @return If the specified pop-up menu entry enabled?
	 */
	public boolean isPopupItemEnabled(int item) {
		return ((JMenuItem)_popup.getComponent(item)).isEnabled();
	}
	
	/**
	 * Determines whether the specified pop-up menu item is enabled or not.
	 * 
	 * @param menu Specified pop-up menu
	 * 
	 * @param item Specified pop-up menu entry.
	 * 
	 * @return If the specified pop-up menu entry enabled?
	 */
	public boolean isPopupItemEnabled(int menu, int item) {
		JMenuOperator mo = new JMenuOperator((JMenu)_popup.getComponent(menu));
		return mo.getItem(item).isEnabled();
	}

	/**
	 * Determines the number of rows in the HTML output.
	 * 
	 * @return Number of rows in the HTML output.
	 */
	public int rowsFromHtmlTextEditor() {
		return _txtHtml.getRows();
	}
	
	/**
	 * Verifies that all selected tests are ignored.
	 * 
	 * @return All selected tests are ignored?
	 */
	public boolean isAllSelectedTestsIgnore() {
		if (_tree.getLastSelectedPathComponent() instanceof TestRunner) {
			TestRunner runner = (TestRunner)_tree.getLastSelectedPathComponent();
			for (int suite = 0; suite < runner.testSuiteCount(); suite++)
				for (int test = 0; test < runner.getTestSuite(suite).testCount(); test++)
					if (runner.getTestSuite(suite).getTest(test).isExecuted())
						return false;
		} else if (_tree.getLastSelectedPathComponent() instanceof TestSuite) {
			TestSuite suite = (TestSuite)_tree.getLastSelectedPathComponent();
			for (int test = 0; test < suite.testCount(); test++)
				if (suite.getTest(test).isExecuted())
					return false;
		} else if (_tree.getLastSelectedPathComponent() instanceof Test) {
			if (((Test)_tree.getLastSelectedPathComponent()).isExecuted())
				return false;
		}
		return true;
	}
	
	/**
	 * Returns the selected test in tree
	 * 
	 * @return Selected test in tree
	 */
	public org.testsuite.data.Test getTreeSelectedTest() {
		return ((org.testsuite.data.Test)_tree.getLastSelectedPathComponent());
	}
	
	/**
	 * Returns the selected TestRunner in tree
	 * 
	 * @return Selected TestRunner in tree
	 */
	public TestRunner getTreeSelectedTestRunner() {
		return (TestRunner)_tree.getLastSelectedPathComponent();
	}
	
	/**
	 * Returns the selected test suite in tree
	 * 
	 * @return Selected TestSuite in tree
	 */
	public TestSuite getTreeSelectedTestSuite() {
		return (TestSuite)_tree.getLastSelectedPathComponent();
	}
	
	/**
	 * Determines whether the specified check box from the tree is selected.
	 * 
	 * @param index Specifying the check box.
	 * 
	 * @return If the check box is selected?
	 */
	public boolean isCheckBoxFromTreeSelected(int index) {
		Component c = _tree.getRenderedComponent(_tree.getPathForRow(index));
		if (c instanceof JCheckBox) {
			JCheckBoxOperator checkbox = new JCheckBoxOperator((JCheckBox)c);
			return checkbox.isSelected();
		}
		
		return false;
	}
	
	/**
	 * Performs a double click on the specified check box.
	 * 
	 * @param index Specifying the check box.
	 */
	public void doubleClickOnCheckBoxFromTree(int index) {
		_tree.clickOnPath(_tree.getPathForRow(index), 2);
	}
	
	/**
	 * Determined by the selected test suite the number of items.
	 * 
	 * @return Number of items.
	 */
	public int getTreeTestSuiteItemCount() {
		return _tree.getChildCount(_tree.getLastSelectedPathComponent());
	}
	
	/**
	 * Finds of selected TestRunner the number of items.
	 * 
	 * @return Number of items.
	 */
	public int getTreeTestRunnerItemCount() {
		return _tree.getChildCount(((TestRunnerModel)_tree.getModel())
				.getTestRunnerList().get(0));
	}

	/**
	 * Click on the HTML output area, to exit the pop-up menu.
	 */
	public void closePopup() {
		_txtHtml.clickMouse();
	}
}
