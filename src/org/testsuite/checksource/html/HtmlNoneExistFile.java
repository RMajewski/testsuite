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
import java.util.ArrayList;
import java.util.List;

import org.testsuite.helper.HelperHtml;

/**
 * Create the HTML result file for the list with none exists files.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class HtmlNoneExistFile extends Html {
	/**
	 * Saves the instance of this class
	 */
	private static HtmlNoneExistFile _instance;
	
	/**
	 * Saves the list of none exists files.
	 */
	private List<String> _noneExists;
	
	/**
	 * Initialize this class
	 */
	private HtmlNoneExistFile() {
		super(null);
		_noneExists = new ArrayList<String>();
		_resultBundle = "none_exist_result_file";
	}
	
	/**
	 * Returns the instance of this class.
	 * 
	 * @return The instance of this class.
	 */
	public static HtmlNoneExistFile getInstance() {
		if (_instance == null)
			_instance = new HtmlNoneExistFile();
		
		return _instance;
	}
	
	/**
	 * Added a file name to the list of none exists files.
	 * 
	 * @param name The name of none exists files.
	 */
	public void addNoneExistsFileName(String name) {
		if (_noneExists.indexOf(name) == -1)
			_noneExists.add(name);
	}
	
	/**
	 * Generate the HTML output for the none exists file list file.
	 */
	public void createHtml() {
		// Generate the File
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(getPathAndResultFile()));
			
			bw.write(HelperHtml.head(_bundle.getString("none_exist_head"),
					_bundle.getString("none_exist_description"),
					HtmlMenu.NONE_EXISTS_FILES));
			
			bw.write(createNoneExistsList());
			
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

	/**
	 * Creates the HTML list of none exists files.
	 * 
	 * @return The HTML list of none exists files.
	 */
	private String createNoneExistsList() {
		StringBuilder ret = new StringBuilder();
		
		if (_noneExists.size() > 0) {
			ret.append("\t\t\t<div class=\"checkSourceList\">");
			ret.append(System.lineSeparator());
			
			ret.append("\t\t\t\t<p>");
			ret.append(_bundle.getString("none_exist_list"));
			ret.append("</p>");
			ret.append(System.lineSeparator());
			
			ret.append("\t\t\t\t<ul>");
			ret.append(System.lineSeparator());
			
			for (int name = 0; name < _noneExists.size(); name++) {
				ret.append("\t\t\t\t\t<li>");
				ret.append(_noneExists.get(name));
				ret.append("</li>");
				ret.append(System.lineSeparator());
			}
			
			ret.append("\t\t\t\t</ul>");
			ret.append(System.lineSeparator());
			
			ret.append("\t\t\t</div>");
			ret.append(System.lineSeparator());
		}
		
		return ret.toString();
	}
	
	public boolean isNoneTestedFile(String name) {
		for (int file = 0; file < _noneExists.size(); file++) {
			if (_noneExists.get(file).indexOf(name) > -1)
				return true;
		}
		
		return false;
	}

}
