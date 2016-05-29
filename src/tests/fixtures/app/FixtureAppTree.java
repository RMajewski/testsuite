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

/**
 * Implements the fixtures for testing the tree.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class FixtureAppTree extends FixtureApp {
	
	/**
	 * Determines whether check box 1 is enabled.
	 * 
	 * @return Is check box 1 enabled?
	 */
	public boolean isCheckBox1Enabled() {
		return _tests.isCheckBoxFromTreeEnabled(3);
	}
	
	/**
	 * Determines whether check box 2 is enabled.
	 * 
	 * @return Is check box 2 enabled?
	 */
	public boolean isCheckBox2Enabled() {
		return _tests.isCheckBoxFromTreeEnabled(4);
	}
	
	/**
	 * Determines whether check box 3 is enabled.
	 * 
	 * @return Is check box 3 enabled?
	 */
	public boolean isCheckBox3Enabled() {
		return _tests.isCheckBoxFromTreeEnabled(5);
	}
	
	/**
	 * Determines whether check box 1 is selected.
	 * 
	 * @return Is check box 1 selected?
	 */
	public boolean isCheckBox1Selected() {
		return _tests.isCheckBoxFromTreeSelected(3);
	}
	
	/**
	 * Determines whether check box 2 is selected.
	 * 
	 * @return Is check box 2 selected?
	 */
	public boolean isCheckBox2Selected() {
		return _tests.isCheckBoxFromTreeSelected(4);
	}
	
	/**
	 * Double click on check box 1
	 */
	public void doubleClickOnCheckBox1() {
		_tests.doubleClickOnCheckBoxFromTree(3);
	}
	
	
	public int getTreeTestSuiteItemCount() {
		return _tests.getTreeTestSuiteItemCount();
	}
	
	/**
	 * Click in the tree pop-up menu "Delete" on menu item "Test"
	 */
	public void pushDeleteTest() {
		_tests.pushNoBlockTreePopupItem(1, 3);
		_tests.waitForDialog2(ResourceBundle.getBundle(App.BUNDLE_FILE)
				.getString("delete_test_title"));
	}
	
	/**
	 * Click in the tree pop-up menu "Delete" on menu item "TestSuite"
	 */
	public void pushDeleteTestSuite() {
		_tests.pushNoBlockTreePopupItem(1, 2);
		_tests.waitForDialog2(ResourceBundle.getBundle(App.BUNDLE_FILE)
				.getString("delete_test_suite_title"));
	}
	
	/**
	 * Click in the tree pop-up menu "Delete" on menu item "TestRunner"
	 */
	public void pushDeleteTestRunner() {
		_tests.pushNoBlockTreePopupItem(1, 1);
		_tests.waitForDialog2(ResourceBundle.getBundle(App.BUNDLE_FILE)
				.getString("delete_test_runner_title"));
	}
	
	/**
	 * Finds of selected TestRunner the number of items.
	 * 
	 * @return Number of items.
	 */
	public int getTreeTestRunnerItemCount() {
		return _tests.getTreeTestRunnerItemCount();
	}
	
	/**
	 * Click in the tree pop-up menu "Insert" on menu item "TestSuite"
	 */
	public void pushInsertTestSuite() {
		_tests.pushTreePopupItem(0, 2);
	}
	
	/**
	 * Click in the tree pop-up menu "Insert" on menu item "Test"
	 */
	public void pushInsertTest() {
		_tests.pushTreePopupItem(0, 3);
	}
	
	/**
	 * Return the name of selected test
	 * 
	 * @return Name of selected test
	 */
	public String getSelectTestName() {
		return _tests.getTreeSelectedTest().getName();
	}
	
	/**
	 * Returns the class name of selected test runner
	 * 
	 * @return Class name of selected test runner
	 */
	public String getTestRunnerClassName() {
		return _tests.getTreeSelectedTestRunner().getClass().getName();
	}
	
	/**
	 * Return the name of selected test suite.
	 * 
	 * @return Name of selected test suite.
	 */
	public String getSelectTestSuiteName() {
		return _tests.getTreeSelectedTestSuite().getName();
	}
	
	/**
	 * Determines whether the tree pop-up menu item "General Configuration" from
	 * pop-up menu "Insert" enabled.
	 * 
	 * @return Is the tree pop-up menu item "General configuration" from
	 * pop-up menu "Insert" enabled? 
	 */
	public boolean isInsertGeneralConfigurationSelected() {
		return _tests.isPopupItemEnabled(0, 0);
	}
	
	/**
	 * Determines whether the tree pop-up menu item "TestRunner" from
	 * pop-up menu "Insert" enabled.
	 * 
	 * @return Is the tree pop-up menu item "TestRunner" from
	 * pop-up menu "Insert" enabled? 
	 */
	public boolean isInsertTestRunnerSelected() {
		return _tests.isPopupItemEnabled(0, 1);
	}
	
	/**
	 * Determines whether the tree pop-up menu item "TestSuite" from
	 * pop-up menu "Insert" enabled.
	 * 
	 * @return Is the tree pop-up menu item "TestSuite" from
	 * pop-up menu "Insert" enabled? 
	 */
	public boolean isInsertTestSuiteSelected() {
		return _tests.isPopupItemEnabled(0, 2);
	}
	
	/**
	 * Determines whether the tree pop-up menu item "Test" from
	 * pop-up menu "Insert" enabled.
	 * 
	 * @return Is the tree pop-up menu item "Test" from
	 * pop-up menu "Insert" enabled? 
	 */
	public boolean isInsertTestSelected() {
		return _tests.isPopupItemEnabled(0, 3);
	}
	
	/**
	 * Determines whether the tree pop-up menu item "General Configuration" from
	 * pop-up menu "Delete" enabled.
	 * 
	 * @return Is the tree pop-up menu item "General configuration" from
	 * pop-up menu "Delete" enabled? 
	 */
	public boolean isDeleteGeneralConfigurationSelected() {
		return _tests.isPopupItemEnabled(1, 0);
	}
	
	/**
	 * Determines whether the tree pop-up menu item "TestRunner" from
	 * pop-up menu "Delete" enabled.
	 * 
	 * @return Is the tree pop-up menu item "TestRunner" from
	 * pop-up menu "Delete" enabled? 
	 */
	public boolean isDeleteTestRunnerSelected() {
		return _tests.isPopupItemEnabled(1, 1);
	}
	
	/**
	 * Determines whether the tree pop-up menu item "TestSuite" from
	 * pop-up menu "Delete" enabled.
	 * 
	 * @return Is the tree pop-up menu item "TestSuite" from
	 * pop-up menu "Delete" enabled? 
	 */
	public boolean isDeleteTestSuiteSelected() {
		return _tests.isPopupItemEnabled(1, 2);
	}
	
	/**
	 * Determines whether the tree pop-up menu item "Test" from
	 * pop-up menu "Delete" enabled.
	 * 
	 * @return Is the tree pop-up menu item "Test" from
	 * pop-up menu "Delete" enabled? 
	 */
	public boolean isDeleteTestSelected() {
		return _tests.isPopupItemEnabled(1, 3);
	}
	
	/**
	 * Determines whether the tree pop-up menu item "General Configuration" from
	 * pop-up menu "Configuration" enabled.
	 * 
	 * @return Is the tree pop-up menu item "General configuration" from
	 * pop-up menu "Configuration" enabled? 
	 */
	public boolean isConfigurationGeneralConfigurationSelected() {
		return _tests.isPopupItemEnabled(2, 0);
	}
	
	/**
	 * Determines whether the tree pop-up menu item "TestRunner" from
	 * pop-up menu "Configuration" enabled.
	 * 
	 * @return Is the tree pop-up menu item "TestRunner" from
	 * pop-up menu "Configuration" enabled? 
	 */
	public boolean isConfigurationInsertTestRunnerSelected() {
		return _tests.isPopupItemEnabled(2, 1);
	}
	
	/**
	 * Determines whether the tree pop-up menu item "TestSuite" from
	 * pop-up menu "Configuration" enabled.
	 * 
	 * @return Is the tree pop-up menu item "TestSuite" from
	 * pop-up menu "Configuration" enabled? 
	 */
	public boolean isConfigurtionInsertTestSuiteSelected() {
		return _tests.isPopupItemEnabled(2, 2);
	}
	
	/**
	 * Determines whether the tree pop-up menu item "Test" from
	 * pop-up menu "Configuration" enabled.
	 * 
	 * @return Is the tree pop-up menu item "Test" from
	 * pop-up menu "Configuration" enabled? 
	 */
	public boolean isConfigurationtTestSelected() {
		return _tests.isPopupItemEnabled(2, 3);
	}

}
