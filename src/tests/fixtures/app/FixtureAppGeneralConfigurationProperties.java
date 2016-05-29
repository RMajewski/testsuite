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
 * Implements the fixtures for testing the pop-menu for the system properties in
 * DlgConfigGeneral dialog.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class FixtureAppGeneralConfigurationProperties extends FixtureApp {
	/**
	 * Returns the size of system properties list.
	 * 
	 * @return The size of system properties list.
	 */
	public int getGeneralConfigurationPropertyCount() {
		return ((JList<?>)_tests.getDialogList(0).getSource()).getModel()
				.getSize();
	}
	
	/**
	 * Open the pop-up from system properties list on general configuration
	 * dialog.
	 */
	public void openGeneralConfigurationPropertyPopup() {
		_tests.openConfigPopup(0);
	}
	
	/**
	 * Checks whether the pop-up menu entry "Insert property" exists.
	 * 
	 * @return Exists the pop-up menu entry "Insert property"?
	 */
	public boolean haveGeneralConfigurationPropertyPopupInsert() {
		return _tests.havePopupItem(0, ResourceBundle.getBundle(
				DlgConfigGeneral.BUNDLE_FILE).getString("insert_property"));
	}
	
	/**
	 * Checks whether the pop-up menu entry "Change property" exists.
	 * 
	 * @return Exists the pop-up menu entry "Change property"?
	 */
	public boolean haveGeneralConfigurationPropertyPopupChange() {
		return _tests.havePopupItem(1, ResourceBundle.getBundle(
				DlgConfigGeneral.BUNDLE_FILE).getString("change_property"));
	}
	
	/**
	 * Checks whether the pop-up menu entry "Delete property" exists.
	 * 
	 * @return Exists the pop-up menu entry "Delete property"?
	 */
	public boolean haveGeneralConfigurationPropertyPopupDelete() {
		return _tests.havePopupItem(2, ResourceBundle.getBundle(
				DlgConfigGeneral.BUNDLE_FILE).getString("delete_property"));
	}
	
	/**
	 * Determines whether the pop-up menu entry "Insert" is enable.
	 * 
	 * @return If the pop-up menu entry "Insert" enable?
	 */
	public boolean isGeneralConfigurationPropertyPopupInsertEnabled() {
		return _tests.isPopupItemEnabled(0);
	}
	
	/**
	 * Determines whether the pop-up menu entry "Change" is enable.
	 * 
	 * @return If the pop-up menu entry "Change" enable?
	 */
	public boolean isGeneralConfigurationPropertyPopupChangeEnabled() {
		return _tests.isPopupItemEnabled(1);
	}
	
	/**
	 * Determines whether the pop-up menu entry "Delete" is enable.
	 * 
	 * @return If the pop-up menu entry "Delete" enable?
	 */
	public boolean isGeneralConfigurationPropertyPopupDeleteEnabled() {
		return _tests.isPopupItemEnabled(2);
	}
	
	public void pushGeneralConfigurationPropertyPopupInsert() {
		_tests.pushNoBlockConfigPopup(0, 
				ResourceBundle.getBundle(DlgConfigGeneral.BUNDLE_FILE)
					.getString("insert_property_title"));
	}
	
	/**
	 * Sets the the name into the insert property dialog
	 * 
	 * @param name The new name
	 */
	public void setGeneralConfigurationPropertyDialogNameText(String name) {
		_tests.getDialog2TextField(0).setText(name);
	}
	
	/**
	 * Click in the insert dialog on button ok.
	 */
	public void pushGeneralConfigurationPropertyDialogOk() {
		_tests.getDialog2Button(0).push();
	}
	
	/**
	 * Slect the first item in the system property list 
	 */
	public void selectGeneralConfigurationPropertyItem0() {
		_tests.getDialogList(0).selectItem(0);
	}
	
	/**
	 * Click on the system property list pop-up item change.
	 */
	public void pushGeneralConfigurationPropertyPopupChange() {
		_tests.pushNoBlockConfigPopup(1,
				ResourceBundle.getBundle(DlgConfigGeneral.BUNDLE_FILE)
				.getString("change_property_title"));
	}
	
	/**
	 * Determines the name first item of system property list.
	 * 
	 * @return First item of system property list.
	 */
	public String getGeneralConfigurationProperty0Name() {
		return ((JList<?>)_tests.getDialogList(0).getSource()).getModel()
				.getElementAt(0).toString();
	}
	
	/**
	 * Click on the system property list pop-up item delete
	 */
	public void pushGeneralConfigurationPropertyPopupDelete() {
		_tests.pushNoBlockConfigPopup(2, 
				ResourceBundle.getBundle(DlgConfigGeneral.BUNDLE_FILE)
				.getString("delete_property_title"));
	}
	
	/**
	 * Click on no button in property delete dialog
	 */
	public void pushGeneralConfigurationPropertyDialogNo() {
		_tests.getDialog2Button(1).push();
	}
	
	/**
	 * Click on yes button in property delete dialog
	 */
	public void pushGeneralConfigurationPropertyDialogYes() {
		_tests.getDialog2Button(0).push();
	}

}
