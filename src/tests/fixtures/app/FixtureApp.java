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

import java.text.ParseException;
import java.util.ResourceBundle;

import javax.swing.JFormattedTextField;
import javax.swing.JList;

import org.testsuite.app.App;
import org.testsuite.app.DlgConfigGeneral;
import org.testsuite.app.DlgConfigLibrary;
import org.testsuite.app.DlgConfigTest;
import org.testsuite.app.DlgConfigTestRunner;
import org.testsuite.app.DlgConfigTestSuite;

import fit.ActionFixture;
import tests.testsuite.app.TestApp;

/**
 * Implements the fixtures for testing the app.
 * 
 * @author René Majewski
 *
 * @version 0.2
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
	
	public void pushConfigurationGeneralConfiguration() {
		_tests.pushNoBlockTreePopupItem(2, 0);
		_tests.waitForDialog(ResourceBundle.getBundle(
				DlgConfigGeneral.BUNDLE_FILE).getString("dialog_title"));
	}
	
	public boolean haveTextFieldPathLibrary() {
		return _tests.getDialogTextField(0).isEnabled();
	}
	
	public boolean haveTextFieldPathResult() {
		return _tests.getDialogTextField(2).isEnabled();
	}
	
	public boolean haveTextFieldPathSrc() {
		return _tests.getDialogTextField(1).isEnabled();
	}
	
	public boolean haveTextFieldMaxDuration() {
		return _tests.getDialogTextField(3).isEnabled();
	}
	
	public boolean haveCheckButtonHtmlCreate() {
		return _tests.getDialogCheckBox(0).isEnabled();
	}
	
	public boolean haveListSystemProperties() {
		return _tests.getDialogList(0).isEnabled();
	}
	
	public boolean haveButtonAccept() {
		return _tests.getDialogButton(0).isEnabled();
	}
	
	public boolean haveButtonCancel() {
		return _tests.getDialogButton(1).isEnabled();
	}
	
	public void setPathLibrary(String str) {
		_tests.getDialogTextField(0).setText(str);
	}
	
	public void setPathSrc(String str) {
		_tests.getDialogTextField(1).setText(str);
	}
	
	public void setPathResult(String str) {
		_tests.getDialogTextField(2).setText(str);
	}
	
	public void setMaxDuration(String str) throws ParseException {
		_tests.getDialogTextField(3).setText(str);
		((JFormattedTextField)_tests.getDialogTextField(3).getSource()).commitEdit();
	}
	
	public void pushAccept() {
		_tests.getDialogButton(0).push();
	}
	
	public String getConfigurationPathLibrary() {
		return _tests.getGeneralConfiguration().getPathLibrary();
	}
	
	public String getConfigurationPathResult() {
		return _tests.getGeneralConfiguration().getPathResult();
	}
	
	public String getConfigurationPathSrc() {
		return _tests.getGeneralConfiguration().getPathSrc();
	}
	
	public void setHtmlCreate(boolean bln) {
		_tests.getDialogCheckBox(0).setSelected(bln);
	}
	
	 public long getMaxDuration() {
		 return _tests.getGeneralConfiguration().getMaxDuration();
	 }
	 
	 public boolean getHtmlCreate() {
		 return _tests.getGeneralConfiguration().isCreateHtml();
	 }
	 public String getListItem0() {
		 if (_tests.getGeneralConfiguration().propertyCount() > 0)
			 return _tests.getGeneralConfiguration().getProperty(0);
		 
		 return new String();
	 }
	
	public void addSystemProperty(String str) {
		_tests.openConfigGeneralPropertyPopup();
		_tests.pushNoBlockConfigGeneralPropertyPopup(0, 
				ResourceBundle.getBundle(DlgConfigGeneral.BUNDLE_FILE)
					.getString("insert_property_title"));
		_tests.setConfigGeneralPropertyName(str);
		_tests.pushConfigPopupDialogButton(0);
	}
	
	public int getGeneralConfigurationPropertyCount() {
		return ((JList<?>)_tests.getDialogList(0).getSource()).getModel()
				.getSize();
	}
	
	public void openGeneralConfigurationPropertyPopup() {
		_tests.openConfigGeneralPropertyPopup();
	}
	
	public boolean isGeneralConfigurationPropertyPopupInsertEnabled() {
		return _tests.isPopupItemEnabled(0);
	}
	
	public boolean isGeneralConfigurationPropertyPopupChangeEnabled() {
		return _tests.isPopupItemEnabled(1);
	}
	
	public boolean isGeneralConfigurationPropertyPopupDeleteEnabled() {
		return _tests.isPopupItemEnabled(2);
	}
	
	public boolean haveGeneralConfigurationPropertyPopupInsert() {
		return _tests.havePopupItem(0, ResourceBundle.getBundle(
				DlgConfigGeneral.BUNDLE_FILE).getString("insert_property"));
	}
	
	public boolean haveGeneralConfigurationPropertyPopupChange() {
		return _tests.havePopupItem(1, ResourceBundle.getBundle(
				DlgConfigGeneral.BUNDLE_FILE).getString("change_property"));
	}
	
	public boolean haveGeneralConfigurationPropertyPopupDelete() {
		return _tests.havePopupItem(2, ResourceBundle.getBundle(
				DlgConfigGeneral.BUNDLE_FILE).getString("delete_property"));
	}
	
	public void pushGeneralConfigurationPropertyPopupInsert() {
		_tests.pushNoBlockConfigGeneralPropertyPopup(0, 
				ResourceBundle.getBundle(DlgConfigGeneral.BUNDLE_FILE)
					.getString("insert_property_title"));
	}
	
	public void setGeneralConfigurationPropertyDialogNameText(String name) {
		_tests.setConfigGeneralPropertyName(name);
	}
	
	public void pushGeneralConfigurationPropertyDialogOk() {
		_tests.pushConfigPopupDialogButton(0);
	}
	
	public void selectGeneralConfigurationPropertyItem0() {
		_tests.getDialogList(0).selectItem(0);
	}
	
	public void pushGeneralConfigurationPropertyPopupChange() {
		_tests.pushNoBlockConfigGeneralPropertyPopup(1,
				ResourceBundle.getBundle(DlgConfigGeneral.BUNDLE_FILE)
				.getString("change_property_title"));
	}
	
	public String getGeneralConfigurationProperty0Name() {
		return ((JList<?>)_tests.getDialogList(0).getSource()).getModel()
				.getElementAt(0).toString();
	}
	
	public void pushGeneralConfigurationPropertyPopupDelete() {
		_tests.pushNoBlockConfigGeneralPropertyPopup(2, 
				ResourceBundle.getBundle(DlgConfigGeneral.BUNDLE_FILE)
				.getString("delete_property_title"));
	}
	
	public void pushGeneralConfigurationPropertyDialogNo() {
		_tests.pushConfigPopupDialogButton(1);
	}
	
	public void pushGeneralConfigurationPropertyDialogYes() {
		_tests.pushConfigPopupDialogButton(0);
	}
	
	public void pushInsertTestRunner() {
		_tests.pushTreePopupItem(0, 1);
	}
	
	public String getTestRunnerClassName() {
		return _tests.getTreeSelectedTestRunner().getClass().getName();
	}
	
	public void pushConfigurationTestRunner() {
		_tests.pushNoBlockTreePopupItem(2, 1);
		_tests.waitForDialog(ResourceBundle.getBundle(
				DlgConfigTestRunner.BUNDLE_FILE).getString("dialog_title"));
	}
	
	public boolean haveConfigurationTestRunnerTextFieldClassName() {
		return _tests.getDialogTextField(0).isEnabled();
	}
	
	public String getConfigurationTestRunnerTextClassName() {
		return _tests.getDialogTextField(0).getDisplayedText();
	}
	
	public boolean haveConfigurationTestRunnerTextFieldFileExtension() {
		return _tests.getDialogTextField(1).isEnabled();
	}
	
	public String getConfigurationTestRunnerTextFileExtension() {
		return _tests.getDialogTextField(1).getDisplayedText();
	}
	
	public boolean haveConfigurationTestRunnerTextAreaDescription() {
		return _tests.getDialogTextArea(0).isEnabled();
	}
	
	public String getConfigurationTestRunnerTextDescription() {
		return _tests.getDialogTextArea(0).getDisplayedText();
	}
	
	public boolean haveConfigurationTestRunnerListLibraries() {
		return _tests.getDialogList(0).isEnabled();
	}
	
	public int getConfigurationTestRunnerLibraryCount() {
		return _tests.getDialogList(0).getModel().getSize();
	}
	
	public boolean haveConfigurationTestRunnerListClasspath() {
		return _tests.getDialogList(1).isEnabled();
	}
	
	public int getConfigurationTestRunnerClasspathCount() {
		return _tests.getDialogList(1).getModel().getSize();
	}
	
	public void setConfigurationTestRunnerTextClassName(String name) {
		_tests.getDialogTextField(0).setText(name);
	}
	
	public void setConfigurationTestRunnerTextFileExtension(String extension) {
		_tests.getDialogTextField(1).setText(extension);
	}
	
	public void setConfigurationTestRunnerTextDescription(String description) {
		_tests.getDialogTextArea(0).setText(description);
	}
	
	public void insertConfigurationTestRunnerLibraryItem() {
		_tests.getDialogList(0).clickMouse();
		_tests.openConfigTestRunnerLibraryPopup();
		_tests.pushNoBlockConfigGeneralPropertyPopup(0, 
				ResourceBundle.getBundle(DlgConfigLibrary.BUNDLE_FILE)
					.getString("dialog_title"));
		_tests.setConfigTestRunnerLibraryFileName("test.jar");
		_tests.pushConfigPopupDialogButton(0);
	}
	
	public void insertConfigurationTestRunnerClasspathItem() {
		_tests.getDialogList(1).clickMouse();
		_tests.openConfigTestRunnerClasspathPopup();
		_tests.pushNoBlockConfigGeneralPropertyPopup(0, 
				ResourceBundle.getBundle(DlgConfigTestRunner.BUNDLE_FILE)
					.getString("insert_classpath_title"));
		_tests.setConfigGeneralPropertyName("bin");
		_tests.pushConfigPopupDialogButton(0);
	}
	
	// FIXME Rename this function in pushConfigurationButt[o]nAccept()
	public void pushConfigurationButtanAccept() {
		_tests.getDialogButton(0).push();
	}
	
	public String getTestRunnerExtension() {
		return _tests.getTreeSelectedTestRunner().getFileExtension();
	}
	
	public String getTestRunnerDescription() {
		return _tests.getTreeSelectedTestRunner().getDescription();
	}
	
	public int getTestRunnerLibraryCount() {
		return _tests.getTreeSelectedTestRunner().libraryCount();
	}
	
	public String getTestRunnerLibraryItem0Name() {
		return _tests.getTreeSelectedTestRunner().getLibrary(0).getFileName();
	}
	
	public int getTestRunnerClasspathCount() {
		return _tests.getTreeSelectedTestRunner().classPathCount();
	}
	
	public String getTestRunnerClasspathItem0() {
		return _tests.getTreeSelectedTestRunner().getClassPath(0);
	}
	
	public void openConfigTestRunnerLibraryPopup() {
		_tests.openConfigTestRunnerLibraryPopup();
	}
	
	public boolean isConfigTestRunnerLibraryPopupInsertEnabled() {
		return _tests.isPopupItemEnabled(0);
	}
	
	public boolean isConfigTestRunnerLibraryPopupChangeEnabled() {
		return _tests.isPopupItemEnabled(1);
	}
	
	public boolean isConfigTestRunnerLibraryPopupDeleteEnabled() {
		return _tests.isPopupItemEnabled(2);
	}
	
	public void selectConfigurationTestRunnerLibraryItem0() {
		_tests.getDialogList(0).setSelectedIndex(0);
	}
	
	public void changeConfigurationTestRunnerLibraryItem0() {
		_tests.getDialogList(0).clickMouse();
		_tests.openConfigTestRunnerLibraryPopup();
		_tests.pushNoBlockConfigGeneralPropertyPopup(1, 
				ResourceBundle.getBundle(DlgConfigLibrary.BUNDLE_FILE)
					.getString("dialog_title"));
		_tests.setConfigTestRunnerLibraryFileName("test1.jar");
		_tests.pushConfigPopupDialogButton(0);
	}
	
	public void deleteConfigTestRunnerLibrary() {
		_tests.getDialogList(0).clickMouse();
		_tests.openConfigTestRunnerLibraryPopup();
		_tests.pushNoBlockConfigGeneralPropertyPopup(2, 
				ResourceBundle.getBundle(DlgConfigTestRunner.BUNDLE_FILE)
				.getString("delete_library_title"));
	}
	
	public void selectConfigurationTestRunnerClasspathItem0() {
		_tests.getDialogList(1).selectItem(0);
	}
	
	public void openConfigTestRunnerClasspathPopup() {
		_tests.openConfigTestRunnerClasspathPopup();
	}
	
	public void deleteConfigTestRunnerClasspath() {
		_tests.getDialogList(1).clickMouse();
		_tests.openConfigTestRunnerClasspathPopup();
		_tests.pushNoBlockConfigGeneralPropertyPopup(1, 
				ResourceBundle.getBundle(DlgConfigTestRunner.BUNDLE_FILE)
				.getString("delete_classpath_title"));
	}
	
	public boolean isConfigTestRunnerClasspathPopupInsertEnabled() {
		return _tests.isPopupItemEnabled(0);
	}
	
	public boolean isConfigTestRunnerClasspathPopupDeleteEnabled() {
		return _tests.isPopupItemEnabled(1);
	}
	
	public int getTreeTestRunnerItemCount() {
		return _tests.getTreeTestRunnerItemCount();
	}
	
	public void pushInsertTestSuite() {
		_tests.pushTreePopupItem(0, 2);
	}
	
	// OPT mit getTestSuiteName() zusammen legen
	public String getSelectTestSuiteName() {
		return _tests.getTreeSelectedTestSuite().getName();
	}
	
	public void pushConfigurationTestSuite() {
		_tests.pushNoBlockTreePopupItem(2, 2);
		_tests.waitForDialog(ResourceBundle.getBundle(
				DlgConfigTestSuite.BUNDLE_FILE).getString("dialog_title"));
	}
	
	public boolean haveConfigurationTestSuiteTextFieldName() {
		return _tests.getDialogTextField(0).isEnabled();
	}
	
	public String getConfigurationTestSuiteTextName() {
		return _tests.getDialogTextField(0).getText();
	}
	
	public boolean haveConfigurationTestSuiteTextFieldPackageName() {
		return _tests.getDialogTextField(0).isEnabled();
	}
	
	public String getConfigurationTestSuiteTextPackageName() {
		return _tests.getDialogTextField(1).getText();
	}
	
	public void setConfigurationTestSuiteTextName(String name) {
		_tests.getDialogTextField(0).setText(name);
	}
	
	public void setConfigurationTestSuiteTextPackageName(String name) {
		_tests.getDialogTextField(1).setText(name);
	}
	
	// OPT Mit getSelectTestSuiteName() zusammen legen
	public String getTestSuiteName() {
		return _tests.getTreeSelectedTestSuite().getName();
	}
	
	public String getTestSuitePackageName() {
		return _tests.getTreeSelectedTestSuite().getPackage();
	}
	
	public int getTreeTestSuiteItemCount() {
		return _tests.getTreeTestSuiteItemCount();
	}
	
	public void pushInsertTest() {
		_tests.pushTreePopupItem(0, 3);
	}
	
	public String getSelectTestName() {
		return _tests.getTreeSelectedTest().getName();
	}
	
	public void pushConfigurationTest() {
		_tests.pushNoBlockTreePopupItem(2, 3);
		_tests.waitForDialog(ResourceBundle.getBundle(
				DlgConfigTest.BUNDLE_FILE).getString("dialog_title"));
	}
	
	public boolean haveConfigurationTestTextFieldName() {
		return _tests.getDialogTextField(0).isEnabled();
	}
	
	public String getConfigurationTestTextName() {
		return _tests.getDialogTextField(0).getText();
	}
	
	public boolean haveConfigurationTestCheckBoxExecute() {
		return _tests.getDialogTextField(0).isEnabled();
	}
	
	public boolean isConfigurationTestCheckBoxExecute() {
		return _tests.getDialogCheckBox(0).isSelected();
	}
	
	public void setConfigurationTestTextName(String name) {
		_tests.getDialogTextField(0).setText(name);
	}
	
	public void setConfigurationTestCheckBoxExecute(boolean execute) {
		_tests.getDialogCheckBox(0).setSelected(execute);
	}
	
	public String getTestName() {
		return _tests.getTreeSelectedTest().getName();
	}
	
	public boolean isTestExecuteSelected() {
		return _tests.getTreeSelectedTest().isExecuted();
	}
	
	public void pushTestRunnerLibraryPopupInsert() {
		_tests.pushNoBlockConfigGeneralPropertyPopup(0, 
				ResourceBundle.getBundle(DlgConfigLibrary.BUNDLE_FILE)
					.getString("dialog_title"));
	}
	
	public boolean haveConfigurationLibraryTextFieldFileName() {
		return _tests.getDialog2TextField(0).isEnabled();
	}
	
	public String getConfigurationLibraryTextFileName() {
		return _tests.getDialog2TextField(0).getText();
	}
	
	public boolean haveConfigurationLibraryTextFieldPath() {
		return _tests.getDialog2TextField(1).isEnabled();
	}
	
	public String getConfigurationLibraryTextPath() {
		return _tests.getDialog2TextField(1).getText();
	}
	
	public boolean haveConfigurationLibraryTextName() {
		return _tests.getDialog2TextField(2).isEnabled();
	}
	public String getConfigurationLibraryTextName() {
		return _tests.getDialog2TextField(2).getText();
	}
	
	public boolean haveConfigurationLibraryTextFieldVersion() {
		return _tests.getDialog2TextField(3).isEnabled();
	}
	
	public String getConfigurationLibraryTextVersion() {
		return _tests.getDialog2TextField(3).getText();
	}
	
	public boolean haveConfigurationLibraryButtonAccept() {
		return _tests.getDialog2Button(0).isEnabled();
	}
	
	public boolean haveConfigurationLibraryButtonCancel() {
		return _tests.getDialog2Button(1).isEnabled();
	}
	
	public void setConfigurationLibraryTextFileName(String name) {
		_tests.getDialog2TextField(0).setText(name);
	}
	
	public void setConfigurationLibraryTextPath(String path) {
		_tests.getDialog2TextField(1).setText(path);
	}
	
	public void setConfigurationLibraryTextName(String name) {
		_tests.getDialog2TextField(2).setText(name);
	}
	
	public void setConfigurationLibraryTextVersion(String version) {
		_tests.getDialog2TextField(3).setText(version);
	}
	
	public String getSelectedConfigurationLibraryTextFileName() {
		return _tests.getSelectedConfigurationLibrary().getFileName();
	}
	
	public String getSelectedConfigurationLibraryTextPath() {
		return _tests.getSelectedConfigurationLibrary().getPath();
	}
	
	public String getSelectedConfigurationLibraryTextName() {
		return _tests.getSelectedConfigurationLibrary().getName();
	}
	
	public String getSelectedConfigurationLibraryTextVersion() {
		return _tests.getSelectedConfigurationLibrary().getVersion();
	}
	
	public void pushConfigurationLibraryButtonAccept() {
		_tests.getDialog2Button(0).push();
	}
	
	public void pushConfigurationTestRunnerLibraryPopupChange() {
		_tests.pushNoBlockConfigGeneralPropertyPopup(1, 
				ResourceBundle.getBundle(DlgConfigLibrary.BUNDLE_FILE)
					.getString("dialog_title"));
	}
	
	public void pushDeleteTest() {
		_tests.pushNoBlockTreePopupItem(1, 3);
		_tests.waitForDialog2(ResourceBundle.getBundle(App.BUNDLE_FILE)
				.getString("delete_test_title"));
	}
}
