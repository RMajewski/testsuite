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

import org.testsuite.app.DlgConfigTestRunner;

/**
 * Implements the fixtures for testing the test runner configuration class path
 * pop-up.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class FixtureAppTestRunnerConfigurationClasspathPopup extends FixtureApp {
	
	/**
	 * Open the pop-up menu from class path list.
	 */
	public void openConfigTestRunnerClasspathPopup() {
		_tests.openConfigPopup(1);
	}
	
	/**
	 * Determines whether the pop-up menu entry "Insert" enabled is.
	 *  
	 * @return Is the pop-up menu entry "Insert" enabled?
	 */
	public boolean isConfigTestRunnerClasspathPopupInsertEnabled() {
		return _tests.isPopupItemEnabled(0);
	}
	
	/**
	 * Determines whether the pop-up menu entry "Delete" enabled is.
	 *  
	 * @return Is the pop-up menu entry "Delete" enabled?
	 */
	public boolean isConfigTestRunnerClasspathPopupDeleteEnabled() {
		return _tests.isPopupItemEnabled(1);
	}
	
	/**
	 * Select item first item in the class path list.
	 */
	public void selectConfigurationTestRunnerClasspathItem0() {
		_tests.getDialogList(1).selectItem(0);
	}
	
	/**
	 * Click the class path pop-up menu item "Delete"
	 */
	public void deleteConfigTestRunnerClasspath() {
		_tests.getDialogList(1).clickMouse();
		_tests.openConfigPopup(1);
		_tests.pushNoBlockConfigPopup(1, 
				ResourceBundle.getBundle(DlgConfigTestRunner.BUNDLE_FILE)
				.getString("delete_classpath_title"));
	}

}
