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

import java.io.File;
import java.util.ResourceBundle;

import org.testsuite.core.TestCore;
import org.testsuite.data.Config;

/**
 * Generate the menu for HTML result files. 
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class HtmlMenu extends Html {
	/**
	 * Specified the menu string for overview in the menu.
	 */
	public static final String OVERVIEW = ResourceBundle.getBundle(BUNDLE_FILE)
			.getString("menu_overview");
	
	/**
	 * Specified the menu string for none tested methods
	 */
	public static final String NONE_METHODS =
			ResourceBundle.getBundle(BUNDLE_FILE)
			.getString("menu_noneTestedMethod");
	
	/**
	 * Specified the menu string for none tested source files
	 */
	public static final String NONE_SOURCE_FILES = ResourceBundle
			.getBundle(BUNDLE_FILE).getString("menu_noneTestedSource");
	
	/**
	 * Specified the menu string for to-do list
	 */
	public static final String TODO_LIST = ResourceBundle.getBundle(BUNDLE_FILE)
			.getString("menu_todoList");
	
	/**
	 * Specified the menu string for deprecated list.
	 */
	public static final String DEPRECATED_LIST = 
			ResourceBundle.getBundle(BUNDLE_FILE)
			.getString("menu_deprecatedList");
	
	/**
	 * Specified the menu string for none exists files.
	 */
	public static final String NONE_EXISTS_FILES = 
			ResourceBundle.getBundle(BUNDLE_FILE)
			.getString("menu_noneExistFiles");
	
	/**
	 * Specified the menu string for the test HTML output
	 */
	public static final String TEST = ResourceBundle.getBundle(BUNDLE_FILE)
			.getString("menu_tests");
	
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
	 * 
	 * @return The menu for HTML result files.
	 */
	public static String createMenu(String actual) {
		StringBuilder ret = new StringBuilder("\t\t<div ");
		ret.append("class=\"checkSourceMenu\">");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t<ul>");
		ret.append(System.lineSeparator());

		ret.append(menuEntry(OVERVIEW, actual, 
				HtmlOutOverview.getInstance().getPathAndResultFile()));

		ret.append(menuEntry(NONE_METHODS, actual, 
				HtmlNoneTestedMethods.getInstance().getPathAndResultFile()));

		ret.append(menuEntry(NONE_SOURCE_FILES, actual, 
				HtmlNoneTestedSourceFiles.getInstance()
				.getPathAndResultFile()));

		ret.append(menuEntry(NONE_EXISTS_FILES, actual, 
				HtmlNoneExistFile.getInstance().getPathAndResultFile()));

		ret.append(menuEntry(TODO_LIST, actual, 
				HtmlTodo.getInstance().getPathAndResultFile()));

		ret.append(menuEntry(DEPRECATED_LIST, actual, 
				HtmlDeprecated.getInstance().getPathAndResultFile()));
		
		ret.append(menuEntry(TEST, actual,
				Config.getInstance().getPathResult() + File.separator +
				ResourceBundle.getBundle(TestCore.BUNDLE_FILE)
				.getString("html_result") + "_" + 
				Config.getInstance().getPathSuitesResult() + ".html"));
		
		ret.append("\t\t\t</ul>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t</div>");
		ret.append(System.lineSeparator());
		
		return ret.toString();
	}

	private static String menuEntry(String entry, String actual, String file) {
		StringBuilder ret = new StringBuilder("\t\t\t\t<li");
		
		if (entry.equals(actual))
			ret.append(" class=\"actual\"");
		
		ret.append("><a href=\"");
		ret.append(new File(file).getAbsolutePath());
		ret.append("\">");
		ret.append(entry);
		ret.append("</a></li>");
		ret.append(System.lineSeparator());
		
		return ret.toString();
	}

}
