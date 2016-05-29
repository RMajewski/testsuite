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

import org.testsuite.app.DlgConfigLibrary;
import org.testsuite.app.DlgConfigTestRunner;

/**
 * Implements the fixtures for testing the test runner configuration library
 * pop-up.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class FixtureAppTestRunnerConfigurationLibraryPopup extends FixtureApp {
	
	/**
	 * Open the library list pop-up
	 */
	public void openConfigTestRunnerLibraryPopup() {
		_tests.openConfigPopup(0);
	}
	
	/**
	 * Determines whether the pop-up menu entry "Insert" enabled is.
	 *  
	 * @return Is the pop-up menu entry "Insert" enabled?
	 */
	public boolean isConfigTestRunnerLibraryPopupInsertEnabled() {
		return _tests.isPopupItemEnabled(0);
	}
	
	/**
	 * Determines whether the pop-up menu entry "Change" enabled is.
	 *  
	 * @return Is the pop-up menu entry "Change" enabled?
	 */
	public boolean isConfigTestRunnerLibraryPopupChangeEnabled() {
		return _tests.isPopupItemEnabled(1);
	}
	
	/**
	 * Determines whether the pop-up menu entry "Delete" enabled is.
	 *  
	 * @return Is the pop-up menu entry "Delete" enabled?
	 */
	public boolean isConfigTestRunnerLibraryPopupDeleteEnabled() {
		return _tests.isPopupItemEnabled(2);
	}
	
	/**
	 * Select the first item in the library list.
	 */
	public void selectConfigurationTestRunnerLibraryItem0() {
		_tests.getDialogList(0).setSelectedIndex(0);
	}
	
	/**
	 * Change the file name of the first item in the libarary list
	 */
	public void changeConfigurationTestRunnerLibraryItem0() {
		_tests.getDialogList(0).clickMouse();
		_tests.openConfigPopup(0);
		_tests.pushNoBlockConfigPopup(1, 
				ResourceBundle.getBundle(DlgConfigLibrary.BUNDLE_FILE)
					.getString("dialog_title"));
		_tests.getDialog2TextField(0).setText("test1.jar");
		_tests.getDialog2Button(0).push();
	}
	
	/**
	 * Click on the libary list pop-up item "Delete"
	 */
	public void deleteConfigTestRunnerLibrary() {
		_tests.getDialogList(0).clickMouse();
		_tests.openConfigPopup(0);
		_tests.pushNoBlockConfigPopup(2, 
				ResourceBundle.getBundle(DlgConfigTestRunner.BUNDLE_FILE)
				.getString("delete_library_title"));
	}

}
