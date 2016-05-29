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
 * Implements the fixtures for testing the button "Validate configuration".
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class FixtureAppConfigurationValidate extends FixtureApp {
	
	/**
	 * Change the name of first test
	 */
	public void changeTestName() {
		_tests.selectElementInTree(3);
		_tests.openTreePopup();
		_tests.pushNoBlockTreePopupItem(2, 3);
		_tests.waitForDialog(ResourceBundle.getBundle(DlgConfigTest.BUNDLE_FILE)
				.getString("dialog_title"));
		_tests.getDialogTextField(0).setText("Test");
		_tests.getDialogButton(0).push();
	}
	
	/**
	 * Determines whether the check box 1 is enabled.
	 * 
	 * @return Is check box 1 enabled?
	 */
	public boolean isCheckBox1Enabled() {
		return _tests.isCheckBoxFromTreeEnabled(3);
	}
	
	/**
	 * Click on the button "Validate configuration"
	 */
	public void pushButtonValidateConfiguration() {
		_tests.pushButtonValidateConfiguration();
	}

}
