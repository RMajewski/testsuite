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

package org.testsuite.checksource;

import java.awt.Color;
import java.util.ResourceBundle;

/**
 * From this class, the class HtmlOut and HtmlOutOverview be derived.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class Html {
	/**
	 * Saves the instance of resource bundle
	 */
	protected ResourceBundle _bundle;
	
	/**
	 * Saves the name of the resource bundle file
	 */
	public static final String BUNDLE_FILE = 
			"resources.lang.org.testsuite.checksource.HtmlOut";
	
	/**
	 * Saves the name of result file
	 */
	protected String _resultFile;
	
	/**
	 * Specifies the background color for tested lines.
	 * 
	 * @deprecated Use {@link org.testsuite.helper.HelperUsedColor#PASS}.
	 */
	public static final Color COLOR_PASS = new Color(0xCFFFCF);

	/**
	 * Initialize this class
	 * 
	 * @param resultFile The name of result file to create.
	 */
	public Html(String resultFile) {
		_resultFile = resultFile;
		_bundle = ResourceBundle.getBundle(BUNDLE_FILE);
	}
}
