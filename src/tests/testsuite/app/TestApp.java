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

import java.io.File;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

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
	 * Determines the maximum value of progress bar
	 * 
	 * @return Maximum value of press bar.
	 */
	public int getMaximumOfProgressBar() {
		return _progress.getMaximum();
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
	 * Determines whether the button for validate configuration is enabled.
	 */
	public boolean isConfigValidateButtonEnabled() {
		return _btnConfigValidate.isEnabled();
	}
	
	/**
	 * Determines whether the button for exit the App is enabled.
	 */
	public boolean isExitButtonEnabled() {
		return _btnExit.isEnabled();
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
	 * Click on the cancel button on dialog.
	 * 
	 * @param index The button index
	 */
	public void pushDialogButton(int index) {
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
	 * Open the pop-up from specified list.
	 * 
	 * @param list List of the pop-up menu to open.
	 */
	public void openConfigPopup(int list) {
		getDialogList(list).clickForPopup();
		_popup = new JPopupMenuOperator();
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
	 * Return the general configuration
	 * 
	 * @return The general configuration
	 */
	public Config getGeneralConfiguration() {
		return ((DlgConfigGeneral)_dlg.getSource()).getConfig();
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
	 * Returns whether the selected configuration file exists.
	 * 
	 * @return Exists the configuration file?
	 */
	public boolean existsConfigurationFile() {
		File file = _fileChooser.getSelectedFile();
		return file.exists();
	}
	
}
