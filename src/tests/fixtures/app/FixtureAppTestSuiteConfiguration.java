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

import org.testsuite.app.DlgConfigTestSuite;

/**
 * Implements the fixtures for testing the test suite configuration.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class FixtureAppTestSuiteConfiguration extends FixtureApp {
	/**
	 * Click in the tree pop-up menu "Configuration" on "TestSuite". 
	 */
	public void pushConfigurationTestSuite() {
		_tests.pushNoBlockTreePopupItem(2, 2);
		_tests.waitForDialog(ResourceBundle.getBundle(
				DlgConfigTestSuite.BUNDLE_FILE).getString("dialog_title"));
	}
	
	/**
	 * Return the text from text field  name
	 * 
	 * @return Text from text field name
	 */
	public boolean haveConfigurationTestSuiteTextFieldName() {
		return _tests.getDialogTextField(0).isEnabled();
	}
	
	/**
	 * Return the text from text field name
	 * 
	 * @return Text from text field name
	 */
	public String getConfigurationTestSuiteTextName() {
		return _tests.getDialogTextField(0).getText();
	}
	
	/**
	 * Return the text from text field package name
	 * 
	 * @return Text from text field package name
	 */
	public boolean haveConfigurationTestSuiteTextFieldPackageName() {
		return _tests.getDialogTextField(1).isEnabled();
	}
	
	/**
	 * Return the text from text field package name
	 * 
	 * @return Text from text field package name
	 */
	public String getConfigurationTestSuiteTextPackageName() {
		return _tests.getDialogTextField(1).getText();
	}
	
	/**
	 * Set the text on text field for name.
	 * 
	 * @param name The new name
	 */
	public void setConfigurationTestSuiteTextName(String name) {
		_tests.getDialogTextField(0).setText(name);
	}
	
	/**
	 * Set the text on text field for package name.
	 * 
	 * @param name The new package name
	 */
	public void setConfigurationTestSuiteTextPackageName(String name) {
		_tests.getDialogTextField(1).setText(name);
	}
	
	/**
	 * Return the name from the select TestSuite in tree
	 * 
	 * @return The name from the selected TestSuite in tree
	 */
	public String getTestSuiteName() {
		return _tests.getTreeSelectedTestSuite().getName();
	}
	
	/**
	 * Return the package name from the select TestSuite in tree
	 * 
	 * @return The package name from the selected TestSuite in tree
	 */
	public String getTestSuitePackageName() {
		return _tests.getTreeSelectedTestSuite().getPackage();
	}

}
