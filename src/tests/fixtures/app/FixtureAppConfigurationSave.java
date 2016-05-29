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

import java.io.File;
import java.util.ResourceBundle;

import org.testsuite.app.App;

/**
 * Implements the fixtures for testing the button "Save configuration".
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class FixtureAppConfigurationSave extends FixtureApp {
	
	/**
	 * If the result configuration exists, then delete it.
	 */
	public void deleteTestSaveFile() {
		File file = new File("result/test.xml");
		if (file.exists())
			file.delete();
	}
	
	/**
	 * Click on the save configuration file button an determined the dialog.
	 */
	public void pushButtonSaveConfigurationFile() {
		_tests.pushNoBlockButtonSaveConfigurationFile();
		_tests.interceptFileChooserDialog();
	}
	
	/**
	 * Click on the open button into the dialog.
	 */
	public void pushDialogSaveFileExists() {
		_tests.pushNoBlockFileDialogButton(10);
		_tests.waitForDialog2(ResourceBundle.getBundle(App.BUNDLE_FILE)
				.getString("fileExistsTitle"));
	}

	/**
	 * Return the name of selected configuration file.
	 */
	public String getConfigurationSaveFileName() {
		return _tests.getConfigurationFileName(1);
	}
	
	/**
	 * Exists the selected configuration file?
	 */
	public boolean existsConfigurationFile() {
		return _tests.existsConfigurationFile();
	}
	
	/**
	 * Determines whether the "Overwrite File" dialog is visible or not.
	 * @return Is the "Overwrite File" dialog visible?
	 */
	public boolean isDialogForOverridingFileVisible() {
		return _tests.isDialog2Visible();
	}
	
	/**
	 * Click on no in the "Overwrite file" dialog.
	 */
	public void pushDialogNoOverride() {
		_tests.getDialog2Button(1).push();
	}
	
	/**
	 * Tests if the saved configuration file is valid.
	 * 
	 * @return Is the saved configuration file valid?
	 */
	public boolean isConfigurationFileValide() {
		return _tests.isConfigurationFileValide();
	}

}
