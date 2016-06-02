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

import org.testsuite.app.DlgConfigTest;

/**
 * Implements the fixtures for testing the test configuration.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class FixtureAppTestConfiguration extends FixtureApp {
	
	/**
	 * Click in the tree pop-up menu configuration on item test
	 */
	public void pushConfigurationTest() {
		_tests.pushNoBlockTreePopupItem(2, 3);
		_tests.waitForDialog(ResourceBundle.getBundle(
				DlgConfigTest.BUNDLE_FILE).getString("dialog_title"));
	}
	
	/**
	 * Determines whether the text field for the test name exists.
	 * 
	 * @return In existence the text field for the test name?
	 */
	public boolean haveConfigurationTestTextFieldName() {
		return _tests.getDialogTextField(0).isEnabled();
	}
	
	/**
	 * Return the text from text field test name
	 * 
	 * @return Text from text field test name
	 */
	public String getConfigurationTestTextName() {
		return _tests.getDialogTextField(0).getText();
	}
	
	/**
	 * Determines whether the check box for the execute test exists.
	 * 
	 * @return In existence the check box for the execute test?
	 */
	public boolean haveConfigurationTestCheckBoxExecute() {
		return _tests.getDialogCheckBox(0).isEnabled();
	}
	
	 /**
	  * Return is checked the check box for execute test.
	  * 
	  * @return Is checked the check box?
	  */
	public boolean isConfigurationTestCheckBoxExecute() {
		return _tests.getDialogCheckBox(0).isSelected();
	}
	
	/**
	 * Determines whether the check box for the separate JVM exists.
	 * 
	 * @return In existence the check box for the separate JVM?
	 */
	public boolean haveConfigurationTestCheckBoxJvm() {
		return _tests.getDialogCheckBox(1).isEnabled();
	}
	
	 /**
	  * Return is checked the check box for separate JVM.
	  * 
	  * @return Is checked the check box?
	  */
	public boolean isConfigurationTestCheckBoxJvm() {
		return _tests.getDialogCheckBox(1).isSelected();
	}
	
	/**
	 * Set the text on text field.
	 * 
	 * @param name The new text on text field
	 */
	public void setConfigurationTestTextName(String name) {
		_tests.getDialogTextField(0).setText(name);
	}
	
	/**
	 * Set the selected on check box for executed the test.
	 * 
	 * @param execute Selected the check box?
	 */
	public void setConfigurationTestCheckBoxExecute(boolean execute) {
		_tests.getDialogCheckBox(0).setSelected(execute);
	}
	
	/**
	 * Set the selected on check box for separate JVM.
	 * 
	 * @param execute Selected the check box?
	 */
	public void setConfigurationTestCheckBoxJvm(boolean execute) {
		_tests.getDialogCheckBox(1).setSelected(execute);
	}
	
	/**
	 * Return the test name from selected test in the tree
	 * 
	 * @return The test name from selected test
	 */
	public String getTestName() {
		return _tests.getTreeSelectedTest().getName();
	}
	
	/**
	 * Return the test executed from selected test in the tree
	 * 
	 * @return The test executed from selected test
	 */
	public boolean isTestExecuteSelected() {
		return _tests.getTreeSelectedTest().isExecuted();
	}
	
	/**
	 * Return the separate JVM from selected test in the tree
	 * 
	 * @return The separate JVM from selected test
	 */
	public boolean isTestJvmSelected() {
		return _tests.getTreeSelectedTest().isJvm();
	}

}
