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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.testsuite.core.TestRunner;
import org.testsuite.data.Config;
import org.testsuite.helper.HelperCalendar;
import org.testsuite.helper.HelperHtml;
import org.testsuite.helper.HelperHtmlCodeJava;

public class HtmlOutOverview extends Html {
	/**
	 * Saves the list of result files
	 */
	private List<String> _resultFiles;
	
	/**
	 * Saves the list of methods.
	 */
	private List<CSMethod> _methods;
	
	/**
	 * Saves the list of source lines
	 */
	private List<SourceLine> _sources;
	
	/**
	 * Saves the instance of HtmlOutOverview
	 */
	private static HtmlOutOverview _instance;

	/**
	 * Initialize this class
	 * 
	 * @param resultFile The file name of result file to create.
	 */
	private HtmlOutOverview(String resultFile) {
		super(resultFile);
		_resultFiles = new ArrayList<String>();
		_methods = new ArrayList<CSMethod>();
		_sources = new ArrayList<SourceLine>();
	}
	
	/**
	 * Returns the instance of this class
	 * 
	 * @return Instance of this class
	 */
	public static HtmlOutOverview getInstance() {
		if (_instance == null)
			_instance = new HtmlOutOverview(null);
		return _instance;
	}
	
	/**
	 * Added a result file to the list of result files.
	 * 
	 * @param resultFile The new result file added to the list.
	 */
	public void addResultFile(String result) {
		if ((result == null) || result.isEmpty())
			throw new IllegalArgumentException();
		_resultFiles.add(result);
	}

	/**
	 * Added the methods from specified list to the list of methods
	 * 
	 * @param list The specified list of methods their added to the list of
	 * methods of this class.
	 */
	public void addMethods(List<CSMethod> list) {
		if (list == null)
			throw new IllegalArgumentException();
		for (int i = 0; i < list.size(); i++)
			_methods.add(list.get(i));
	}
	
	/**
	 * Added the source lines from specified list to the list of source lines.
	 * 
	 * @param list The specified list of source lines their added to the list of
	 * source lines of this class.
	 */
	public void addSourceLines(List<SourceLine> list) {
		if (list == null)
			throw new IllegalArgumentException();
		for (int i = 0; i < list.size(); i++)
			_sources.add(list.get(i));
	}
	
	/**
	 * 
	 */
	public void createHtml() {
		_resultFile = Config.getInstance().getPathResult() + File.separator +
				Config.getInstance().getPathSuitesResult();
		File file = new File(_resultFile);
		
		if (!file.exists())
			file.mkdirs();
		
		_resultFile += File.separator + 
				ResourceBundle.getBundle(TestRunner.BUNDLE_FILE).getString(
						"result_checksoure") + ".html";
		
		// Generate the File
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(_resultFile));

			String date = HelperCalendar.datetimeToString(new Date().getTime());

			bw.write(HelperHtml.head(_bundle.getString("overview_head") + " (" +
					date + ")", _bundle.getString("overview_description") + 
					" (" + date + ")"));
			
			// List of methods without calls
			bw.write(HelperHtml.createListOfMethods(
					_bundle.getString("methods_without_calls"), _methods, 
					false, true));
			
			// Table with links
			bw.write(createHtmlLink());
			
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
	 * Creates the HTML table for links to the check source result files.
	 * 
	 * @return HtML table for links to the check source result files.
	 */
	private String createHtmlLink() {
		StringBuilder ret = new StringBuilder();
		
		ret.append("\t\t\t<div class=\"checksourceLinks\">");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t<table>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t<tr>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t\t<th>");
		ret.append(_bundle.getString("overview_links"));
		ret.append("</th>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t\t<th>");
		ret.append(_bundle.getString("overview_class"));
		ret.append("</th>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t\t<th>");
		ret.append(_bundle.getString("overview_messages"));
		ret.append("</th>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t</tr>");
		ret.append(System.lineSeparator());
		
		for (int link = 0; link <_resultFiles.size(); link++) {
			File file = new File(_resultFiles.get(link));
			if (file.exists()) {
				String linkSrc = file.getName();
				String linkName = linkSrc.substring(linkSrc.indexOf("_") + 1);
				String className = linkName.substring(linkName.indexOf("Test") + 4, 
						linkName.indexOf(".html"));
				
				ret.append("\t\t\t\t\t<tr>");
				ret.append(System.lineSeparator());
				
				ret.append("\t\t\t\t\t\t<td>");
				ret.append("<a href=\"");
				ret.append(linkSrc);
				ret.append("\">");
				ret.append(linkName);
				ret.append("</a></td>");
				ret.append(System.lineSeparator());
				ret.append(System.lineSeparator());
				
				ret.append("\t\t\t\t\t\t<td>");
				ret.append(className);
				ret.append("</td>");
				ret.append(System.lineSeparator());
				ret.append(System.lineSeparator());
				
				ret.append("\t\t\t\t\t\t<td>");
				ret.append(createMessages(className));
				ret.append("</td>");
				ret.append(System.lineSeparator());
				
				ret.append("\t\t\t\t\t</tr>");
				ret.append(System.lineSeparator());
			}
		}
		
		ret.append("\t\t\t\t</table>");
		ret.append(System.lineSeparator());
		
		return ret.toString();
	}
	
	/**
	 * Creates the messages for the specified class.
	 * 
	 * @param className The name of class.
	 * 
	 * @return Messages for the specified class.
	 */
	private String createMessages(String className) {
		StringBuilder ret = new StringBuilder();
		boolean first = true;
		
		for (int source = 0; source < _sources.size(); source++) {
			if (_sources.get(source).getClassName().equals(className)) {
				for (int message = 0; 
						message <_sources.get(source).messageCount();
						message++) {
					if (!first)
						ret.append("<br/>");
					else
						first = false;
					String linkSrc = new String();
					String linkEnd = new String();
					
					for (int method = 0; method < _methods.size(); method++) {
						if ((_methods.get(method).getClassName().equals(className)) && 
								(_methods.get(method).getLine() == 
								_sources.get(source).getLineNumber())) {
							linkSrc = "<a href=\"Test" +
								_methods.get(method).getClassName() + ".html#" +
								_methods.get(method).getName() + "\">";
							linkEnd = "</a>";
						}
					}
					
					ret.append("<span style=\"background: ");
					ret.append(HelperHtmlCodeJava.getInstance()
							.formatColor(_sources.get(source)
									.getMessage(message).getColor()));
					ret.append(";\" >");
					ret.append(linkSrc);
					ret.append(_sources.get(source)
							.getMessage(message).getMessage());
					ret.append(linkEnd);
					ret.append("</span>");
				}
			}
		}

		return ret.toString();
	}
}