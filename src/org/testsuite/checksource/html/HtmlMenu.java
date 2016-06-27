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

package org.testsuite.checksource.html;

import java.util.ResourceBundle;

/**
 * 
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class HtmlMenu extends Html {
	/**
	 * Specified the menu string for overview in the menu.
	 */
	public static String overview = ResourceBundle.getBundle(BUNDLE_FILE)
			.getString("menu_overview");
	
	/**
	 * Specified the menu string for none tested methods
	 */
	public static String none_methods = ResourceBundle.getBundle(BUNDLE_FILE)
			.getString("menu_noneTestedMethod");
	
	/**
	 * Specified the menu string for none tested source files
	 */
	public static String none_sourceFiles = ResourceBundle
			.getBundle(BUNDLE_FILE).getString("menu_noneTestedSource");
	
	/**
	 * Specified the menu string for to-do list
	 */
	public static String todo_list = ResourceBundle.getBundle(BUNDLE_FILE)
			.getString("menu_todoList");
	
	/**
	 * Specified the menu string for deprecated list.
	 */
	public static String deprecated_list = ResourceBundle.getBundle(BUNDLE_FILE)
			.getString("menu_deprecatedList");
	
	/**
	 * Initialize the class
	 */
	public HtmlMenu() {
		super(null);
	}
	
	/**
	 * Creates the menu for HTML result files.
	 * 
	 * @param actual The actual menu
	 * @return
	 */
	public static String createMenu(String actual) {
		StringBuilder ret = new StringBuilder();
		
		
		
		return ret.toString();
	}

}
