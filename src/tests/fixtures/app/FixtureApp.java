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

package tests.fixtures.app;

import java.util.ResourceBundle;

import org.testsuite.app.App;
import org.testsuite.app.DlgConfigGeneral;
import org.testsuite.app.DlgConfigLibrary;
import org.testsuite.app.DlgConfigTestRunner;

import fit.ActionFixture;
import tests.testsuite.app.TestApp;

/**
 * Implements the fixtures for testing the app.
 * 
 * In Version 0.3 the structure has been revised.
 * 
 * @author René Majewski
 *
 * @version 0.3
 */
public class FixtureApp extends ActionFixture {
	/**
	 * Saves the instance of {@link tests.testsuite.app.TestApp}.
	 */
	protected TestApp _tests;
	
	/**
	 * Initialized the fixture.
	 */
	public FixtureApp() {
		_tests = new TestApp();
	}
	
	/**
	 * Generate a test configuration
	 */
	public void generateTestConfiguration() {
		_tests.generateTestConfiguration();
		
		_tests.treeExpand(0);
		_tests.treeExpand(1);
		_tests.treeExpand(2);
		_tests.treeExpand(5);
		_tests.treeExpand(8);
		_tests.treeExpand(9);
	}
	
	/**
	 * Determines the number of tests to executed.
	 * 
	 * @return Number of tests to executed.
	 */
	public int getExecutedTests() {
		return _tests.getExecutedTests();
	}
	
	/**
	 * Determines the maximum value of progress bar
	 * 
	 * @return Maximum value of press bar.
	 */
	public int getMaximumOfProgressBar() {
		return _tests.getMaximumOfProgressBar();
	}
	
	/**
	 * Determines the minimum value of progress bar
	 * 
	 * @return Minimum value of press bar.
	 */
	public int getMinimumOfProgressBar() {
		return _tests.getMinimumOfProgressBar();
	}

	/**
	 * Determines the actual value of progress bar
	 * 
	 * @return Value of progress bar.
	 */
	public int getValueOfProgressBar() {
		return _tests.getValueOfProgressBar();
	}

	/**
	 * Verifies that all tests are ignored.
	 * 
	 * @return All tests are ignored?
	 */
	public boolean isAllTestsIgnore() {
		return _tests.isAllTestsIgnore();
	}
	
	/**
	 * Determines whether the button for cancel tests is visible.
	 */
	public boolean isButtonForCancelTestsEnable() {
		return _tests.isCancelButtonEnabled();
	}
	
	/**
	 * Determines whether the button for exit the App is enabled.
	 */
	public boolean isButtonForExitTheAppEnable() {
		return _tests.isExitButtonEnabled();
	}
	
	/**
	 * Determines whether the button for load configuration file is enabled.
	 */
	public boolean isButtonForLoadTheConfigurationFileEnable() {
		return _tests.isLoadButtonEnabled();
	}
	
	/**
	 * Determines whether the button for run tests is enabled.
	 */
	public boolean isButtonForRunTestsEnable() {
		return _tests.isRunButtonEnabled();
	}
	
	/**
	 * Determines whether the button for all tests select execute is enabled.
	 */
	public boolean isButtonForTestsAllExecuteEnable() {
		return _tests.isAllTestsExecuteButtonEnabled();
	}
	
	/**
	 * Determines whether the button for all tests select ignore is enabled.
	 */
	public boolean isButtonForTestsAllIgnoreEnable() {
		return _tests.isAllTestsIgnoreButtonEnabled();
	}

	/**
	 * Loads the configuration file for this fit test.
	 */
	public void loadConfigurationFile() {
		_tests.pushNoBlockButtonLoadConfigurationFile();
		_tests.interceptFileChooserDialog();
		_tests.fileDialogTextFieldSetText("src/tests/fit_test.xml");
		pushFileDialogOpen();
		_tests.treeExpandAll();
	}
	
	/**
	 * Open the pop-up from tree (simulate a right click on tree).
	 */
	public void openPopup() {
		_tests.openTreePopup();
	}
	
	/**
	 * Click on the button "Cancel" in the main window
	 */
	public void pushButtonCancel() {
		_tests.pushButtonCancel();
	}

	/**
	 * Click on the button "Ignore all tests"
	 */
	public void pushButtonIgnoreAllTests() {
		_tests.pushButtonIgnoreAllTests();
	}
	
	/**
	 * Click on the button to run tests.
	 */
	public void pushButtonRun() {
		_tests.pushButtonRun();
	}
	
	/**
	 * Click on the pop-up menu item for general configuration in delete
	 */
	public void pushDeleteGeneralConfiguration() {
		_tests.pushNoBlockTreePopupItem(1, 0);
		_tests.waitForDialog(ResourceBundle.getBundle(App.BUNDLE_FILE)
				.getString("delete_config_general_title"));
	}
	
	/**
	 * Click on "Yes" button in the dialog
	 */
	public void pushDialogButtonYes() {
		_tests.getDialog2Button(0).push();
	}
	
	/**
	 * Click on "No" button in the dialog
	 */
	public void pushDialogButtonNo() {
		_tests.getDialog2Button(1).push();
	}
	
	/**
	 * Click on the open button into the file dialog.
	 */
	public void pushFileDialogOpen() {
		_tests.pushFileDialogButton(10);
	}
	
	/**
	 * Select the root element in the tree
	 */
	public void selectRootElementInTree() {
		_tests.selectElementInTree(0);
	}
	
	/**
	 * Wait for all tests completed.
	 */
	public void waitForTestsCompleted() {
		while (_tests.getValueOfProgressBar() != 0) {}
	}
	
	/**
	 * Determines whether the "Accept" button exists.
	 * 
	 * @return In existence of the "Accept" button?
	 */
	public boolean haveButtonAccept() {
		return _tests.getDialogButton(0).isEnabled();
	}
	
	/**
	 * Determines whether the "Cancel" button exists.
	 * 
	 * @return In existence of the "Cancel" button?
	 */
	public boolean haveButtonCancel() {
		return _tests.getDialogButton(1).isEnabled();
	}
	
	/**
	 * Click on the "Accept" button in the dialog.
	 */
	public void pushAccept() {
		_tests.getDialogButton(0).push();
	}
	
	/**
	 * Click on the "Cancel" button in the dialog.
	 */
	public void pushCancel() {
		_tests.getDialogButton(1).push();
	}
	
	/**
	 * Returns the number of nodes in the tree.
	 */
	public int getTreeRootItemCount() {
		return _tests.getTreeRootItemCount();
	}
	
	/**
	 * Click on the load configuration file button and determined the dialog.
	 */
	public void pushButtonLoadConfigurationFile() {
		_tests.pushNoBlockButtonLoadConfigurationFile();
		_tests.interceptFileChooserDialog();
	}
	
	/**
	 * Returns is the file dialog showing
	 * 
	 * @return Is the file dialog showing?
	 */
	public boolean isFileDialogVisible() {
		return _tests.isFileDialogShowing();
	}

	/**
	 * Click an the cancel button into the file dialog.
	 */
	public void pushFileDialogCancel() {
		_tests.pushFileDialogButton(9);
	}
	
	/**
	 * Sets the file name of configuration file.
	 * 
	 * @param name The name of the configuration file.
	 */
	public void enterFileName(String name) {
		_tests.fileDialogTextFieldSetText(name);
	}
	
	/**
	 * Click on the open button into the file dialog.
	 */
	public void pushFileDialogSave() {
		_tests.pushFileDialogButton(10);
	}
	
	/**
	 * Determines whether the button for save configuration file is enabled.
	 */
	public boolean isButtonForSaveTheConfigurationFileEnable() {
		return _tests.isConfigSaveButtonEnabled();
	}
	
	/**
	 * Determines whether the button for validate configuration is enabled.
	 */
	public boolean isButtonForValidateTheConfigurationEnable() {
		return _tests.isConfigSaveButtonEnabled();
	}
	
	/**
	 * Determines whether the main window is visible.
	 */
	public boolean isVisible() {
		return _tests.isMainWindowVisible();
	}
	
	/**
	 * Open the dialog for the general configuration.
	 */
	public void pushConfigurationGeneralConfiguration() {
		_tests.pushNoBlockTreePopupItem(2, 0);
		_tests.waitForDialog(ResourceBundle.getBundle(
				DlgConfigGeneral.BUNDLE_FILE).getString("dialog_title"));
	}
	
	/**
	 * Click on the tree pop-up item "TestRunner" in the menu "Insert"
	 */
	public void pushInsertTestRunner() {
		_tests.pushTreePopupItem(0, 1);
	}

	/**
	 * Expand all tree nodes
	 */
	public void expandTree() {
		_tests.treeExpandAll();
	}
	
	/**
	 * Select the TestRunner in the tree
	 */
	public void selectTestRunnerInTree() {
		_tests.selectElementInTree(1);
	}
	
	/**
	 * Determines whether the tree pop-up item for "All other Tests ignored" is 
	 * enabled.
	 */
	public boolean isIgnoreAllOtherTestsEnabled() {
		return _tests.isPopupItemEnabled(4);
	}
	
	/**
	 * Select the test in the tree
	 */
	public void selectTestInTree() {
		_tests.selectElementInTree(3);
	}
	
	/**
	 * Click on the tree pop-up menu item "All other tests ignored"
	 */
	public void pushIgnoreAllOtherTests() {
		_tests.pushTreePopupItem(4);
	}

	/**
	 * Verifies that all selected tests are ignored.
	 * 
	 * @return All selected tests are ignored?
	 */
	public boolean isAllSelectedTestsIgnore() {
		return _tests.isAllSelectedTestsIgnore();
	}
	
	/**
	 * Determines whether the tree pop-up menu item execute all selected tests
	 * enabled.
	 * 
	 * @return Is the tree pop-up menu item execute all selected tests enabled? 
	 */
	public boolean isExecuteAllSelectedTestsEnabled() {
		return _tests.isPopupItemEnabled(6);
	}
	
	/**
	 * Click on the tree pop-up menu item "Ignore all selected tests"
	 */
	public void pushIgnoreAllSelectedTests() {
		_tests.pushTreePopupItem(5);
	}
	
	/**
	 * Click on the tree pop-up menu item "Execute all selected tests"
	 */
	public void pushExecuteAllSelectedTests() {
		_tests.pushTreePopupItem(6);
	}
	
	/**
	 * Determines whether the tree pop-up item for "All selected Tests ignored"
	 * is enabled.	/**
	 * Click in the the tree pop-up menu configuration the menu item
	 * "TestRunner".
	 */
	public void pushConfigurationTestRunner() {
		_tests.pushNoBlockTreePopupItem(2, 1);
		_tests.waitForDialog(ResourceBundle.getBundle(
				DlgConfigTestRunner.BUNDLE_FILE).getString("dialog_title"));
	}
	
	/**
	 * Returns the selected TestRunner count of class path list
	 * 
	 * @return Selected TestRunner count of class path
	 */
	public int getTestRunnerClasspathCount() {
		return _tests.getTreeSelectedTestRunner().classPathCount();
	}
	
	/**
	 * Insert a library in the libraries list.
	 */
	public void insertConfigurationTestRunnerLibraryItem() {
		_tests.getDialogList(0).clickMouse();
		_tests.openConfigPopup(0);
		_tests.pushNoBlockConfigPopup(0, 
				ResourceBundle.getBundle(DlgConfigLibrary.BUNDLE_FILE)
					.getString("dialog_title"));
		_tests.getDialog2TextField(0).setText("test.jar");
		_tests.getDialog2Button(0).push();
	}
	
	/**
	 * Insert a class path in the class paths list.
	 */
	public void insertConfigurationTestRunnerClasspathItem() {
		_tests.getDialogList(1).clickMouse();
		_tests.openConfigPopup(1);
		_tests.pushNoBlockConfigPopup(0, 
				ResourceBundle.getBundle(DlgConfigTestRunner.BUNDLE_FILE)
					.getString("insert_classpath_title"));
		_tests.getDialog2TextField(0).setText("bin");
		_tests.getDialog2Button(0).push();
	}
	
	/**
	 * Returns the selected TestRunner first library list item names
	 * 
	 * @return Selected TestRunner first library item names
	 */
	public String getTestRunnerLibraryItem0Name() {
		return _tests.getTreeSelectedTestRunner().getLibrary(0).getFileName();
	}
	
	/**
	 * Returns the selected TestRunner first class path list item
	 * 
	 * @return Selected TestRunner first class path item
	 */
	public String getTestRunnerClasspathItem0() {
		return _tests.getTreeSelectedTestRunner().getClassPath(0);
	}
	
	/**
	 * Returns the selected TestRunner description
	 * 
	 * @return Selected TestRunner description
	 */
	public int getTestRunnerLibraryCount() {
		return _tests.getTreeSelectedTestRunner().libraryCount();
	}
	
	/**
	 * Select the TestSuite in the tree
	 */
	public void selectTestSuiteInTree() {
		_tests.selectElementInTree(2);
	}
	
	/**
	 * Determines whether the tree pop-up item for "All selected Tests ignored"
	 * is enabled.
	 */
	public boolean isIgnoreAllSelectedTestsEnabled() {
		return _tests.isPopupItemEnabled(5);
	}

}
