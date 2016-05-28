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
	 * Saves the instance of {@link tests.suite.app.TestApp}.
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
		
		_tests.selectElementInTree(0);
		_tests.openTreePopup();
		_tests.pushTreePopupItem(0, 1);
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
	 * Click on the open button into the file dialog.
	 */
	public void pushFileDialogOpen() {
		_tests.pushDialogButton(10);
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
		_tests.pushDialogButton(9);
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
		_tests.pushDialogButton(10);
	}

}
