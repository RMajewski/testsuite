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
 * Implements the fixtures for testing the pop-menu for the java script files in
 * DlgConfigGeneral dialog.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class FixtureAppGeneralConfigurationJavascript extends FixtureApp {
	/**
	 * Returns the size of java script files list.
	 * 
	 * @return The size of java script files list.
	 */
	public int getGeneralConfigurationJavascriptCount() {
		return ((JList<?>)_tests.getDialogList(2).getSource()).getModel()
				.getSize();
	}
	
	/**
	 * Open the pop-up from java script files list on general configuration
	 * dialog.
	 */
	public void openGeneralConfigurationJavascriptPopup() {
		_tests.openConfigPopup(2);
	}
	
	/**
	 * Checks whether the pop-up menu entry "Insert java script file" exists.
	 * 
	 * @return Exists the pop-up menu entry "Insert java script file"?
	 */
	public boolean haveGeneralConfigurationJavascriptPopupInsert() {
		return _tests.havePopupItem(0, ResourceBundle.getBundle(
				DlgConfigGeneral.BUNDLE_FILE).getString("insert_javascript"));
	}
	
	/**
	 * Checks whether the pop-up menu entry "Change java script file" exists.
	 * 
	 * @return Exists the pop-up menu entry "Change java script file"?
	 */
	public boolean haveGeneralConfigurationJavascriptPopupChange() {
		return _tests.havePopupItem(1, ResourceBundle.getBundle(
				DlgConfigGeneral.BUNDLE_FILE).getString("change_javascript"));
	}
	
	/**
	 * Checks whether the pop-up menu entry "Delete java script file" exists.
	 * 
	 * @return Exists the pop-up menu entry "Delete java script file"?
	 */
	public boolean haveGeneralConfigurationJavascriptPopupDelete() {
		return _tests.havePopupItem(2, ResourceBundle.getBundle(
				DlgConfigGeneral.BUNDLE_FILE).getString("delete_javascript"));
	}
	
	/**
	 * Determines whether the pop-up menu entry "Insert" is enable.
	 * 
	 * @return If the pop-up menu entry "Insert" enable?
	 */
	public boolean isGeneralConfigurationJavascriptPopupInsertEnabled() {
		return _tests.isPopupItemEnabled(0);
	}
	
	/**
	 * Determines whether the pop-up menu entry "Change" is enable.
	 * 
	 * @return If the pop-up menu entry "Change" enable?
	 */
	public boolean isGeneralConfigurationJavascriptPopupChangeEnabled() {
		return _tests.isPopupItemEnabled(1);
	}
	
	/**
	 * Determines whether the pop-up menu entry "Delete" is enable.
	 * 
	 * @return If the pop-up menu entry "Delete" enable?
	 */
	public boolean isGeneralConfigurationJavascriptPopupDeleteEnabled() {
		return _tests.isPopupItemEnabled(2);
	}
	
	/**
	 * Click on pop-up entry insert.
	 */
	public void pushGeneralConfigurationJavascriptPopupInsert() {
		_tests.pushNoBlockConfigPopup(0, 
				ResourceBundle.getBundle(DlgConfigGeneral.BUNDLE_FILE)
					.getString("insert_javascript_title"));
	}
	
	/**
	 * Sets the the name into the insert java script file dialog
	 * 
	 * @param name The new name
	 */
	public void setGeneralConfigurationJavascriptDialogNameText(String name) {
		_tests.getDialog2TextField(0).setText(name);
	}
	
	/**
	 * Click in the insert dialog on button ok.
	 */
	public void pushGeneralConfigurationJavascriptDialogOk() {
		_tests.getDialog2Button(0).push();
	}
	
	/**
	 * Select the first item in the java script files list 
	 */
	public void selectGeneralConfigurationJavascriptItem0() {
		_tests.getDialogList(2).selectItem(0);
	}
	
	/**
	 * Click on the java script files list pop-up item change.
	 */
	public void pushGeneralConfigurationJavascriptPopupChange() {
		_tests.pushNoBlockConfigPopup(1,
				ResourceBundle.getBundle(DlgConfigGeneral.BUNDLE_FILE)
				.getString("change_javascript_title"));
	}
	
	/**
	 * Determines the name first item of java script files list.
	 * 
	 * @return First item of java script files list.
	 */
	public String getGeneralConfigurationJavascript0Name() {
		return ((JList<?>)_tests.getDialogList(2).getSource()).getModel()
				.getElementAt(0).toString();
	}
	
	/**
	 * Click on the java script files list pop-up item delete
	 */
	public void pushGeneralConfigurationJavascriptPopupDelete() {
		_tests.pushNoBlockConfigPopup(2, 
				ResourceBundle.getBundle(DlgConfigGeneral.BUNDLE_FILE)
				.getString("delete_javascript_title"));
	}
	
	/**
	 * Click on no button in java script file delete dialog
	 */
	public void pushGeneralConfigurationJavascriptDialogNo() {
		_tests.getDialog2Button(1).push();
	}
	
	/**
	 * Click on yes button in java script file delete dialog
	 */
	public void pushGeneralConfigurationJavascriptDialogYes() {
		_tests.getDialog2Button(0).push();
	}

}
