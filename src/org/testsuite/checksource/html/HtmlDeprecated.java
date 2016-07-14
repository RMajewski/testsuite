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
import java.util.Collections;
import java.util.List;

import org.testsuite.checksource.CSMethod;
import org.testsuite.helper.HelperHtml;

/**
 * Generate the HTML result file with the list of deprecated classes, methods
 * and field.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class HtmlDeprecated extends Html {
	/**
	 * Saves the instance of this class.
	 */
	private static HtmlDeprecated _instance;
	
	/**
	 * Saves the list of deprecated classes.
	 */
	private List<String> _class;
	
	/**
	 * Initialize this class.
	 */
	private HtmlDeprecated() {
		super(null);
		_resultBundle = "deprecate_result_file";
		_class = new ArrayList<String>();
	}
	
	/**
	 * Returns the instance of this class.
	 * 
	 * @return The instance of this class.
	 */
	public static HtmlDeprecated getInstance() {
		if (_instance == null)
			_instance = new HtmlDeprecated();
		return _instance;
	}
	
	/**
	 * Generate the HTML result file with the list of deprecated classes,
	 * methods and fields.
	 */
	public void createHtml() {
		// Generate the File
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(getPathAndResultFile()));
			
			bw.write(HelperHtml.head(_bundle.getString("deprecate_head"),
					_bundle.getString("deprecate_description"), 
					HtmlMenu.DEPRECATED_LIST));
			bw.write(createListOfDeprecated());
			
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
	 * Creates the HTML list deprecated methods
	 * 
	 * @return HTML list of deprecated methods
	 */
	private String createListOfDeprecated() {
		List<String> list = new ArrayList<String>();
		List<CSMethod> methods = HtmlOutOverview.getInstance().getMethodList();
		
		for (int method = 0; method < methods.size(); method++)
			if (methods.get(method).isDeprecated()) {
				StringBuilder li = new StringBuilder("\t\t\t\t\t<li>");
				li.append("<a href=\"");
				li.append(methods.get(method).getHtmlOutputFile());
				li.append("#");
				li.append(methods.get(method).getClassName());
				li.append(".");
				li.append(methods.get(method).getName());
				li.append("\">");
				li.append(methods.get(method).getClassName());
				li.append(".");
				li.append(methods.get(method).getName());
				li.append("(");
				
				for (int type = 0; type < methods.get(method).parametersCount();
						type++) {
					if (type > 0)
						li.append(", ");
					li.append(methods.get(method).getParameter(type).getType());
				}
				
				li.append(")");
				li.append("</a></li>");
				list.add(li.toString());
			}
		
		for (int i = 0; i < _class.size(); i++)
			list.add("\t\t\t\t\t<li>" + _class.get(i) + "</li>");
		
		Collections.sort(list);

		StringBuilder ret = new StringBuilder();
		
		if (list.size() > 0) {
			ret.append("\t\t\t<div class=\"deprecatedList\">");
			ret.append(System.lineSeparator());
			
			ret.append("\t\t\t\t<p>");
			ret.append(_bundle.getString("methods_deprecated"));
			ret.append("</p>");
			ret.append(System.lineSeparator());
			
			ret.append("\t\t\t\t<ul>");
			ret.append(System.lineSeparator());
			
			for (int li = 0; li < list.size(); li++) {
				ret.append(list.get(li));
				ret.append(System.lineSeparator());
			}
			
			ret.append("\t\t\t\t</ul>");
			ret.append(System.lineSeparator());

			ret.append("\t\t\t</div>");
			ret.append(System.lineSeparator());
		}

		return ret.toString();
	}

	/**
	 * Added a name of class to the list of deprecated classes.
	 * 
	 * @param name The name of deprecated class
	 */
	public void addDeprecatedClass(String name) {
		_class.add("class " + name);
	}
	
	/**
	 * Clear the list
	 */
	public void clear() {
		_class.clear();
	}
}
