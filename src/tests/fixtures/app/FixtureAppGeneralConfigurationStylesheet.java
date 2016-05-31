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
 * Implements the fixtures for testing the pop-menu for the style sheet files in
 * DlgConfigGeneral dialog.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class FixtureAppGeneralConfigurationStylesheet extends FixtureApp {
	/**
	 * Returns the size of style sheet files list.
	 * 
	 * @return The size of style sheet files list.
	 */
	public int getGeneralConfigurationStylesheetCount() {
		return ((JList<?>)_tests.getDialogList(3).getSource()).getModel()
				.getSize();
	}
	
	/**
	 * Open the pop-up from style sheet files list on general configuration
	 * dialog.
	 */
	public void openGeneralConfigurationStylesheetPopup() {
		_tests.openConfigPopup(3);
	}
	
	/**
	 * Checks whether the pop-up menu entry "Insert style sheet file" exists.
	 * 
	 * @return Exists the pop-up menu entry "Insert style sheet file"?
	 */
	public boolean haveGeneralConfigurationStylesheetPopupInsert() {
		return _tests.havePopupItem(0, ResourceBundle.getBundle(
				DlgConfigGeneral.BUNDLE_FILE).getString("insert_stylesheet"));
	}
	
	/**
	 * Checks whether the pop-up menu entry "Change style sheet file" exists.
	 * 
	 * @return Exists the pop-up menu entry "Change style sheet file"?
	 */
	public boolean haveGeneralConfigurationStylesheetPopupChange() {
		return _tests.havePopupItem(1, ResourceBundle.getBundle(
				DlgConfigGeneral.BUNDLE_FILE).getString("change_stylesheet"));
	}
	
	/**
	 * Checks whether the pop-up menu entry "Delete style sheet file" exists.
	 * 
	 * @return Exists the pop-up menu entry "Delete style sheet file"?
	 */
	public boolean haveGeneralConfigurationStylesheetPopupDelete() {
		return _tests.havePopupItem(2, ResourceBundle.getBundle(
				DlgConfigGeneral.BUNDLE_FILE).getString("delete_stylesheet"));
	}
	
	/**
	 * Determines whether the pop-up menu entry "Insert" is enable.
	 * 
	 * @return If the pop-up menu entry "Insert" enable?
	 */
	public boolean isGeneralConfigurationStylesheetPopupInsertEnabled() {
		return _tests.isPopupItemEnabled(0);
	}
	
	/**
	 * Determines whether the pop-up menu entry "Change" is enable.
	 * 
	 * @return If the pop-up menu entry "Change" enable?
	 */
	public boolean isGeneralConfigurationStylesheetPopupChangeEnabled() {
		return _tests.isPopupItemEnabled(1);
	}
	
	/**
	 * Determines whether the pop-up menu entry "Delete" is enable.
	 * 
	 * @return If the pop-up menu entry "Delete" enable?
	 */
	public boolean isGeneralConfigurationStylesheetPopupDeleteEnabled() {
		return _tests.isPopupItemEnabled(2);
	}
	
	/**
	 * Click on pop-up entry insert.
	 */
	public void pushGeneralConfigurationStylesheetPopupInsert() {
		_tests.pushNoBlockConfigPopup(0, 
				ResourceBundle.getBundle(DlgConfigGeneral.BUNDLE_FILE)
					.getString("insert_stylesheet_title"));
	}
	
	/**
	 * Sets the the name into the insert style sheet file dialog
	 * 
	 * @param name The new name
	 */
	public void setGeneralConfigurationStylesheetDialogNameText(String name) {
		_tests.getDialog2TextField(0).setText(name);
	}
	
	/**
	 * Click in the insert dialog on button ok.
	 */
	public void pushGeneralConfigurationStylesheetDialogOk() {
		_tests.getDialog2Button(0).push();
	}
	
	/**
	 * Select the first item in the style sheet files list 
	 */
	public void selectGeneralConfigurationStylesheetItem0() {
		_tests.getDialogList(3).selectItem(0);
	}
	
	/**
	 * Click on the style sheet files list pop-up item change.
	 */
	public void pushGeneralConfigurationStylesheetPopupChange() {
		_tests.pushNoBlockConfigPopup(1,
				ResourceBundle.getBundle(DlgConfigGeneral.BUNDLE_FILE)
				.getString("change_stylesheet_title"));
	}
	
	/**
	 * Determines the name first item of style sheet files list.
	 * 
	 * @return First item of style sheet files list.
	 */
	public String getGeneralConfigurationStylesheet0Name() {
		return ((JList<?>)_tests.getDialogList(3).getSource()).getModel()
				.getElementAt(0).toString();
	}
	
	/**
	 * Click on the style sheet files list pop-up item delete
	 */
	public void pushGeneralConfigurationStylesheetPopupDelete() {
		_tests.pushNoBlockConfigPopup(2, 
				ResourceBundle.getBundle(DlgConfigGeneral.BUNDLE_FILE)
				.getString("delete_stylesheet_title"));
	}
	
	/**
	 * Click on no button in style sheet file delete dialog
	 */
	public void pushGeneralConfigurationStylesheetDialogNo() {
		_tests.getDialog2Button(1).push();
	}
	
	/**
	 * Click on yes button in style sheet file delete dialog
	 */
	public void pushGeneralConfigurationStylesheetDialogYes() {
		_tests.getDialog2Button(0).push();
	}

}
