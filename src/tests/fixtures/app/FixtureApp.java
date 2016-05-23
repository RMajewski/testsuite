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

import fit.ActionFixture;
import tests.testsuite.app.TestApp;

/**
 * Implements the fixtures for testing the app.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class FixtureApp extends ActionFixture {
	/**
	 * Hold an instance of the tests.
	 */
	private TestApp _tests; 
	
	/**
	 * Initializes the fixture.
	 */
	public FixtureApp() {
		_tests = new TestApp();
	}
	
	/**
	 * Determines whether the tree is enabled.
	 */
	public boolean haveTree() {
		return _tests.isTreeEnabled();
	}

	/**
	 * Determines whether the text pane for html output is enabled.
	 */
	public boolean haveTextPaneForHtmlOut() {
		return _tests.isTextPaneEnabled();
	}
	

	/**
	 * Determines whether the text pane for html output is editable.
	 */
	public boolean isTextPaneForHtmlOutEditable() {
		return _tests.isTextPaneEditable();
	}
	
	/**
	 * Determines whether the progress bar is enabled.
	 */
	public boolean haveProgressBar() {
		return _tests.isProgressBarEnabled();
	}
	
	/**
	 * Determines whether the button for run tests is visible.
	 */
	public boolean haveButtonForRunTests() {
		return _tests.isRunButtonVisible();
	}
	
	/**
	 * Determines whether the button for run tests is enabled.
	 */
	public boolean isButtonForRunTestsEnable() {
		return _tests.isRunButtonEnabled();
	}
	
	/**
	 * Determines whether the button for cancel tests is visible.
	 */
	public boolean haveButtonForCancelTests() {
		return _tests.isRunButtonVisible();
	}
	
	/**
	 * Determines whether the button for cancel tests is visible.
	 */
	public boolean isButtonForCancelTestsEnable() {
		return _tests.isCancelButtonEnabled();
	}
	
	/**
	 * Determines whether the button for load configuration file is visible.
	 */
	public boolean haveButtonForLoadTheConfigurationFile() {
		return _tests.isLoadButtonVisible();
	}
	
	/**
	 * Determines whether the button for load configuration file is enabled.
	 */
	public boolean isButtonForLoadTheConfigurationFileEnable() {
		return _tests.isLoadButtonEnabled();
	}
	
	/**
	 * Determines whether the button for exit the app is visible.
	 */
	public boolean haveButtonForExitTheApp() {
		return _tests.isExitButtonVisible();
	}
	
	/**
	 * Determines whether the button for exit the App is enabled.
	 */
	public boolean isButtonForExitTheAppEnable() {
		return _tests.isExitButtonEnabled();
	}
	
	/**
	 * Determines whether the main window is visible.
	 */
	public boolean isVisible() {
		return _tests.isMainWindowVisible();
	}
	
	/**
	 * Click on the exit button.
	 */
	public void pushExitButton() {
		_tests.pushExitButton();
	}
	
	/**
	 * Click on the load configuration file button and determined the dialog.
	 */
	public void pushButtonLoadConfigurationFile() {
		_tests.pushNoBlockButtonLoadConfigurationFile();
		_tests.determineDialog(TestApp.DIALOG_CONFIG_FILE_OPEN);
	}
	
	/**
	 * Showing der dialog?
	 */
	public boolean isDialogVisible() {
		return _tests.isDialogShowing();
	}

	/**
	 * Click an the cancel button into the dialog.
	 */
	public void pushDialogCancel() {
		_tests.pushDialogButton(6);
	}
	
	/**
	 * Sets the file name of configuration file.
	 * 
	 * @param name The name of the configuration file.
	 */
	public void enterFileName(String name) {
		_tests.dialogTextFieldSetText(name);
	}
	
	/**
	 * Click on the open button into the dialog.
	 */
	public void pushDialogOpen() {
		_tests.pushDialogButton(10);
	}
	
	/**
	 * Returns the number of nodes in the tree.
	 */
	public int getTreeRootItemCount() {
		return _tests.getTreeRootItemCount();
	}

	/**
	 * Return the name of selected configuration file.
	 */
	public String getConfigurationFileName() {
		return _tests.getConfigurationFileName();
	}
	
	/**
	 * Exists the selected configuration file?
	 */
	public boolean existsConfigurationFile() {
		return _tests.existsConfigurationFile();
	}
	
	/**
	 * Loads the configuration file for this fit test.
	 */
	public void loadConfigurationFile() {
		_tests.pushNoBlockButtonLoadConfigurationFile();
		_tests.determineDialog(TestApp.DIALOG_CONFIG_FILE_OPEN);
		_tests.dialogTextFieldSetText("src/tests/fit_test.xml");
		pushDialogOpen();
		_tests.treeExpandAll();
	}
	
	public boolean isCheckBox1Enabled() {
		return _tests.isCheckBoxFromTreeEnabled(3);
	}
	
	public boolean isCheckBox2Enabled() {
		return _tests.isCheckBoxFromTreeEnabled(4);
	}
	
	public boolean isCheckBox3Enabled() {
		return _tests.isCheckBoxFromTreeEnabled(5);
	}
	
	public boolean isCheckBox1Selected() {
		return _tests.isCheckBoxFromTreeSelected(3);
	}
	
	public boolean isCheckBox2Selected() {
		return _tests.isCheckBoxFromTreeSelected(4);
	}
	
	public void doubleClickOnCheckBox1() {
		_tests.doubleClickOnCheckBoxFromTree(3);
	}
	
	public int getMaximumOfProgressBar() {
		return _tests.getMaximumOfProgressBar();
	}
	
	public int getMinimumOfProgressBar() {
		return _tests.getMinimumOfProgressBar();
	}
	
	public int getValueOfProgressBar() {
		return _tests.getValueOfProgressBar();
	}
	
	public void pushButtonRun() {
		_tests.pushButtonRun();
	}
	
	public void waitForTestsEnd() {
		int end = _tests.getMaximumOfProgressBar();
		while (_tests.getValueOfProgressBar() != end);
	}
	
	public void pushButtonCancel() {
		_tests.pushButtonCancel();
	}
	
	public boolean existsResultHtmlFile() throws Exception {
		Thread.sleep(500);
		return _tests.existsResultHtmlFile();
	}
	
	public boolean drawsHtmlFileIntoApp() throws InterruptedException {
		Thread.sleep(500);
		return _tests.rowsFromHtmlTextEditor() > 0;
	}
	
	public void openPopup() {
		_tests.openTreePopup();
	}
	
	public boolean isInsertGeneralConfigurationSelected() {
		return _tests.isPopupItemEnabled(0, 0);
	}
	
	public boolean isInsertTestRunnerSelected() {
		return _tests.isPopupItemEnabled(0, 1);
	}
	
	public boolean isInsertTestSuiteSelected() {
		return _tests.isPopupItemEnabled(0, 2);
	}
	
	public boolean isInsertTestSelected() {
		return _tests.isPopupItemEnabled(0, 3);
	}
	
	public boolean isDeleteGeneralConfigurationSelected() {
		return _tests.isPopupItemEnabled(1, 0);
	}
	
	public boolean isDeleteTestRunnerSelected() {
		return _tests.isPopupItemEnabled(1, 1);
	}
	
	public boolean isDeleteTestSuiteSelected() {
		return _tests.isPopupItemEnabled(1, 2);
	}
	
	public boolean isDeleteTestSelected() {
		return _tests.isPopupItemEnabled(1, 3);
	}
	
	public boolean isConfigurationGeneralConfigurationSelected() {
		return _tests.isPopupItemEnabled(2, 0);
	}
	
	public boolean isConfigurationInsertTestRunnerSelected() {
		return _tests.isPopupItemEnabled(2, 1);
	}
	
	public boolean isConfigurtionInsertTestSuiteSelected() {
		return _tests.isPopupItemEnabled(2, 2);
	}
	
	public boolean isConfigurationtTestSelected() {
		return _tests.isPopupItemEnabled(2, 3);
	}
	
	public void selectRootElementInTree() {
		_tests.selectElementInTree(0);
	}
	
	public void selectTestRunnerInTree() {
		_tests.selectElementInTree(1);
	}
	
	public void selectTestSuiteInTree() {
		_tests.selectElementInTree(2);
	}
	
	public void selectTestInTree() {
		_tests.selectElementInTree(3);
	}
	
	public void expandTree() {
		_tests.treeExpandAll();
	}
}
