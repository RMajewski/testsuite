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
import org.testsuite.data.Library;

/**
 * Implements the fixtures for testing the library configuration dialog.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class FixtureAppLibraryConfiguration extends FixtureApp {
	
	/**
	 * Click on the tree pop-up item TestRunner in menu configuration
	 */
	public void pushConfigurationTestRunner() {
		_tests.pushNoBlockTreePopupItem(2, 1);
		_tests.waitForDialog(ResourceBundle.getBundle(
				DlgConfigTestRunner.BUNDLE_FILE).getString("dialog_title"));
	}

	/**
	 * Open the pop-up menu from library list
	 */
	public void openConfigTestRunnerLibraryPopup() {
		_tests.openConfigPopup(0);
	}
	
	/**
	 * Click on Insert in the library pop-up.
	 */
	public void pushTestRunnerLibraryPopupInsert() {
		_tests.pushNoBlockConfigPopup(0, 
				ResourceBundle.getBundle(DlgConfigLibrary.BUNDLE_FILE)
					.getString("dialog_title"));
	}
	
	/**
	 * Determines whether the text field for the file name exists.
	 * 
	 * @return In existence the text field for the file name?
	 */
	public boolean haveConfigurationLibraryTextFieldFileName() {
		return _tests.getDialog2TextField(0).isEnabled();
	}
	
	/**
	 * Returns the library file name
	 * 
	 * @return The library file name
	 */
	public String getConfigurationLibraryTextFileName() {
		return _tests.getDialog2TextField(0).getText();
	}
	
	/**
	 * Determines whether the text field for the path exists.
	 * 
	 * @return In existence the text field for the path?
	 */
	public boolean haveConfigurationLibraryTextFieldPath() {
		return _tests.getDialog2TextField(1).isEnabled();
	}
	
	/**
	 * Returns the library path
	 * @return The library path
	 */
	public String getConfigurationLibraryTextPath() {
		return _tests.getDialog2TextField(1).getText();
	}
	
	/**
	 * Determines whether the text field for the name exists.
	 * 
	 * @return In existence the text field for the name?
	 */
	public boolean haveConfigurationLibraryTextName() {
		return _tests.getDialog2TextField(2).isEnabled();
	}
	
	/**
	 * Returns the library name
	 * @return The library name
	 */
	public String getConfigurationLibraryTextName() {
		return _tests.getDialog2TextField(2).getText();
	}
	
	/**
	 * Determines whether the text field for the version exists.
	 * 
	 * @return In existence the text field for the version?
	 */
	public boolean haveConfigurationLibraryTextFieldVersion() {
		return _tests.getDialog2TextField(3).isEnabled();
	}
	
	/**
	 * Returns the library version
	 * @return The library version
	 */
	public String getConfigurationLibraryTextVersion() {
		return _tests.getDialog2TextField(3).getText();
	}
	
	/**
	 * Determines whether the accept button exists.
	 * 
	 * @return In existence the accept button?
	 */
	public boolean haveConfigurationLibraryButtonAccept() {
		return _tests.getDialog2Button(0).isEnabled();
	}
	
	/**
	 * Determines whether the accept cancel exists.
	 * 
	 * @return In existence the cancel button?
	 */
	public boolean haveConfigurationLibraryButtonCancel() {
		return _tests.getDialog2Button(1).isEnabled();
	}
	
	/**
	 * Sets the text into the text field for file name.
	 * 
	 * @param name The text for text field of file name
	 */
	public void setConfigurationLibraryTextFileName(String name) {
		_tests.getDialog2TextField(0).setText(name);
	}
	
	/**
	 * Sets the text into the text field for library path.
	 * 
	 * @param name The text for text field of library path
	 */
	public void setConfigurationLibraryTextPath(String path) {
		_tests.getDialog2TextField(1).setText(path);
	}
	
	/**
	 * Sets the text into the text field for library name.
	 * 
	 * @param name The text for text field of library name.
	 */
	public void setConfigurationLibraryTextName(String name) {
		_tests.getDialog2TextField(2).setText(name);
	}
	
	/**
	 * Sets the text into the text field for library version.
	 * 
	 * @param name The text for text field of library version.
	 */
	public void setConfigurationLibraryTextVersion(String version) {
		_tests.getDialog2TextField(3).setText(version);
	}
	
	/**
	 * Click on the accept button
	 */
	public void pushConfigurationLibraryButtonAccept() {
		_tests.getDialog2Button(0).push();
	}
	
	/**
	 * Click on library list pop-up item change
	 */
	public void pushConfigurationTestRunnerLibraryPopupChange() {
		_tests.pushNoBlockConfigPopup(1, 
				ResourceBundle.getBundle(DlgConfigLibrary.BUNDLE_FILE)
					.getString("dialog_title"));
	}
	
	/**
	 * Select the first item in the library list.
	 */
	public void selectConfigurationTestRunnerLibraryItem0() {
		_tests.getDialogList(0).setSelectedIndex(0);
	}
	
	/**
	 * Determines the file name of the first library.
	 * 
	 * @return The file name from first library
	 */
	public String getSelectedConfigurationLibraryTextFileName() {
		return ((Library)_tests.getDialogList(0).getModel().getElementAt(0))
				.getFileName();
	}
	
	/**
	 * Determines the path of the first library.
	 * 
	 * @return The path from first library
	 */
	public String getSelectedConfigurationLibraryTextPath() {
		return ((Library)_tests.getDialogList(0).getModel().getElementAt(0))
				.getPath();
	}
	
	/**
	 * Determines the name of the first library.
	 * 
	 * @return The name from first library
	 */
	public String getSelectedConfigurationLibraryTextName() {
		return ((Library)_tests.getDialogList(0).getModel().getElementAt(0))
				.getName();
	}
	
	/**
	 * Determines the version of the first library.
	 * 
	 * @return The version from first library
	 */
	public String getSelectedConfigurationLibraryTextVersion() {
		return ((Library)_tests.getDialogList(0).getModel().getElementAt(0))
				.getVersion();
	}

}
