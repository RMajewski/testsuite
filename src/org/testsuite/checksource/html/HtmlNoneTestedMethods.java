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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import org.testsuite.helper.HelperHtml;

/**
 * Creates the HTML file with none tested methods.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class HtmlNoneTestedMethods extends Html {
	/**
	 * Saves the instance of this class.
	 */
	private static HtmlNoneTestedMethods _instance;
	
	/**
	 * Initialize this class
	 */
	private HtmlNoneTestedMethods() {
		super(null);
		_resultBundle = "none_tested_method_result_file";
	}
	
	/**
	 * Returns the instance of this class.
	 * 
	 * @return The instance of this class.
	 */
	public static HtmlNoneTestedMethods getInstance() {
		if (_instance == null)
			_instance = new HtmlNoneTestedMethods();
		return _instance;
	}
	
	/**
	 * Generate the HTML result file for the list of none tested methods.
	 */
	public void createHtml() {
		// Generate the File
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(getPathAndResultFile()));
			
			bw.write(HelperHtml.head(_bundle.getString(
					"none_tested_method_head"),
					_bundle.getString("none_tested_method_description"),
					HtmlMenu.NONE_METHODS));
			
			bw.write(HelperHtml.createListOfMethods(
					_bundle.getString("methods_without_calls"),
					HtmlOutOverview.getInstance().getMethodList(), false, 
					true));
			
			// HTML end
			bw.write(HelperHtml.footer());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
