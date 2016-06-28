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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testsuite.checksource.CSConfig;
import org.testsuite.helper.HelperFile;
import org.testsuite.helper.HelperHtml;

/**
 * Generate the HTML result file with the list of none tested source files.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class HtmlNoneTestedSourceFiles extends Html {
	/**
	 * Saves the instance of this class.
	 */
	private static HtmlNoneTestedSourceFiles _instance;
	
	/**
	 * Initialize this class.
	 */
	private HtmlNoneTestedSourceFiles() {
		super(null);
		_resultBundle = "none_tested_source_result_file";
	}
	
	/**
	 * Returns the instance of this class.
	 * 
	 * @return The instance of this class
	 */
	public static HtmlNoneTestedSourceFiles getInstance() {
		if (_instance == null)
			_instance = new HtmlNoneTestedSourceFiles();
		return _instance;
	}
	
	/**
	 * Generate the HTML result file with the list of none tested source files.
	 */
	public void createHtml() {
		// Generate the File
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(getPathAndResultFile()));
			
			bw.write(HelperHtml.head(
					_bundle.getString("none_tested_source_head"),
					_bundle.getString("none_tested_source_description"),
					HtmlMenu.NONE_SOURCE_FILES));
			
			bw.write(createNoneTestedList());
			
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
	 * Generates a list of the files have not been tested.
	 * 
	 * @return The HTML list of the files have not been tested.
	 */
	private String createNoneTestedList() {
		StringBuilder ret = new StringBuilder();
		List<String> resultFiles = 
				HtmlOutOverview.getInstance().getResultFileList();
		
		if (CSConfig.getInstance().isListNoneTestedFiles()) {
			List<String> none = new ArrayList<String>();
			List<File> list = HelperFile.getSourceFiles(
					CSConfig.getInstance().getNoneListedPath(), "(.*\\.java$)");
			for (int file = 0; file < list.size(); file++) {
				String name = list.get(file).getName();
				name = name.substring(0, name.lastIndexOf("."));
				boolean available = false;
				for (int rs = 0; rs < resultFiles.size(); rs++)
					if (resultFiles.get(rs).indexOf(name) > -1)
						available = true;
				
				if (!available && (!name.equals("package-info")))
					none.add(list.get(file).getAbsolutePath().substring(
							list.get(file).getAbsolutePath().indexOf(
									CSConfig.getInstance().getNoneListedPath())));
			}
			
			if (none.size() > 0) {
				Collections.sort(none);
				
				ret.append("\t\t\t<div class=\"noneList\">");
				ret.append(System.lineSeparator());

				ret.append("\t\t\t\t<p>");
				ret.append(_bundle.getString("none_tested_source_list"));
				ret.append("</p>");
				ret.append(System.lineSeparator());
				
				ret.append("\t\t\t\t<ul>");
				ret.append(System.lineSeparator());
				
				for (int i = 0; i < none.size(); i++) {
					ret.append("\t\t\t\t\t<li>");
					ret.append(none.get(i));
					ret.append("</li>");
					ret.append(System.lineSeparator());
				}
				
				ret.append("\t\t\t\t</ul>");
				ret.append(System.lineSeparator());

				ret.append("\t\t\t</div>");
				ret.append(System.lineSeparator());
			}
		}
		
		return ret.toString();
	}
}
