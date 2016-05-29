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

import javax.swing.JList;

import org.testsuite.app.DlgConfigGeneral;

/**
 * Implements the fixtures for testing the pop-menu for the class paths in
 * DlgConfigGeneral dialog.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class FixtureAppGeneralConfigurationClasspaths extends FixtureApp {
	/**
	 * Returns the size of class path list.
	 * 
	 * @return The size of class path list.
	 */
	public int getGeneralConfigurationClasspathCount() {
		return ((JList<?>)_tests.getDialogList(1).getSource()).getModel()
				.getSize();
	}
	
	/**
	 * Open the pop-up from class path list on general configuration dialog.
	 */
	public void openGeneralConfigurationClasspathPopup() {
		_tests.openConfigPopup(1);
	}
	
	/**
	 * Checks whether the pop-up menu entry "Insert class path" exists.
	 * 
	 * @return Exists the pop-up menu entry "Insert class path"?
	 */
	public boolean haveGeneralConfigurationClasspathPopupInsert() {
		return _tests.havePopupItem(0, ResourceBundle.getBundle(
				DlgConfigGeneral.BUNDLE_FILE).getString("insert_classpath"));
	}
	
	/**
	 * Checks whether the pop-up menu entry "Change class path" exists.
	 * 
	 * @return Exists the pop-up menu entry "Change class path"?
	 */
	public boolean haveGeneralConfigurationClasspathPopupChange() {
		return _tests.havePopupItem(1, ResourceBundle.getBundle(
				DlgConfigGeneral.BUNDLE_FILE).getString("change_classpath"));
	}
	
	/**
	 * Checks whether the pop-up menu entry "Delete class path" exists.
	 * 
	 * @return Exists the pop-up menu entry "Delete class path"?
	 */
	public boolean haveGeneralConfigurationClasspathPopupDelete() {
		return _tests.havePopupItem(2, ResourceBundle.getBundle(
				DlgConfigGeneral.BUNDLE_FILE).getString("delete_classpath"));
	}
	
	/**
	 * Determines whether the pop-up menu entry "Insert" is enable.
	 * 
	 * @return If the pop-up menu entry "Insert" enable?
	 */
	public boolean isGeneralConfigurationClasspathPopupInsertEnabled() {
		return _tests.isPopupItemEnabled(0);
	}
	
	/**
	 * Determines whether the pop-up menu entry "Change" is enable.
	 * 
	 * @return If the pop-up menu entry "Change" enable?
	 */
	public boolean isGeneralConfigurationClasspathPopupChangeEnabled() {
		return _tests.isPopupItemEnabled(1);
	}
	
	/**
	 * Determines whether the pop-up menu entry "Delete" is enable.
	 * 
	 * @return If the pop-up menu entry "Delete" enable?
	 */
	public boolean isGeneralConfigurationClasspathPopupDeleteEnabled() {
		return _tests.isPopupItemEnabled(2);
	}
	
	/**
	 * Click on the system class path pop-up item change.
	 */
	public void pushGeneralConfigurationClasspathPopupInsert() {
		_tests.pushNoBlockConfigPopup(0, 
				ResourceBundle.getBundle(DlgConfigGeneral.BUNDLE_FILE)
					.getString("insert_classpath_title"));
	}
	
	/**
	 * Sets the the name into the insert class path dialog
	 * 
	 * @param name The new name
	 */
	public void setGeneralConfigurationClasspathDialogNameText(String name) {
		_tests.getDialog2TextField(0).setText(name);
	}
	
	/**
	 * Click in the insert dialog on button ok.
	 */
	public void pushGeneralConfigurationClasspathDialogOk() {
		_tests.getDialog2Button(0).push();
	}
	
	/**
	 * Select the first item in the system property list 
	 */
	public void selectGeneralConfigurationClasspathItem0() {
		_tests.getDialogList(1).selectItem(0);
	}
	
	/**
	 * Click on the system property list pop-up item change.
	 */
	public void pushGeneralConfigurationClasspathPopupChange() {
		_tests.pushNoBlockConfigPopup(1,
				ResourceBundle.getBundle(DlgConfigGeneral.BUNDLE_FILE)
				.getString("change_classpath_title"));
	}
	
	/**
	 * Determines the name first item of system class path list.
	 * 
	 * @return First item of system class path list.
	 */
	public String getGeneralConfigurationClasspath0Name() {
		return ((JList<?>)_tests.getDialogList(1).getSource()).getModel()
				.getElementAt(0).toString();
	}
	
	/**
	 * Click on the system class path list pop-up item delete
	 */
	public void pushGeneralConfigurationClasspathPopupDelete() {
		_tests.pushNoBlockConfigPopup(2, 
				ResourceBundle.getBundle(DlgConfigGeneral.BUNDLE_FILE)
				.getString("delete_classpath_title"));
	}
	
	/**
	 * Click on no button in class path delete dialog
	 */
	public void pushGeneralConfigurationClasspathDialogNo() {
		_tests.getDialog2Button(1).push();
	}
	
	/**
	 * Click on yes button in class path delete dialog
	 */
	public void pushGeneralConfigurationClasspathDialogYes() {
		_tests.getDialog2Button(0).push();
	}

}
