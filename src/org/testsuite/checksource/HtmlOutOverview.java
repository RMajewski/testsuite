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
import java.util.Collections;
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
	 * Saves the list of none exists files.
	 */
	private List<String> _noneExists;

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
		_noneExists = new ArrayList<String>();
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
	 * Added a file name to the list of none exists files.
	 * 
	 * @param name The name of none exists files.
	 */
	public void addNoneExistsFileName(String name) {
		_noneExists.add(name);
	}
	
	/**
	 * Generate the HTML output for the overview file.
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
			
			// List of none exists files
			bw.write(createNoneExistsList());
			
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
	 * Creates the HTML list of none exists files.
	 * 
	 * @return The HTML list of none exists files.
	 */
	private String createNoneExistsList() {
		StringBuilder ret = new StringBuilder();
		
		if (_noneExists.size() > 0) {
			ret.append("\t\t\t<div class=\"checksourceList\">");
			ret.append(System.lineSeparator());
			
			ret.append("\t\t\t\t<p>");
			ret.append(_bundle.getString("overview_none_exists_files"));
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
		
		List<String> links = new ArrayList<String>();
		
		for (int link = 0; link <_resultFiles.size(); link++) {
			File file = new File(_resultFiles.get(link));
			if (file.exists()) {
				String linkSrc = file.getName();
				String linkName = linkSrc.substring(linkSrc.indexOf("_") + 1);
				String className = linkName.substring(linkName.indexOf("Test") + 4, 
						linkName.indexOf(".html"));
				StringBuilder table = new StringBuilder("\t\t\t\t\t<tr>");
				table.append(System.lineSeparator());
				
				table.append("\t\t\t\t\t\t<td>");
				table.append("<a href=\"");
				table.append(linkSrc);
				table.append("\">");
				table.append(linkName);
				table.append("</a></td>");
				table.append(System.lineSeparator());
				
				table.append("\t\t\t\t\t\t<td>");
				table.append(className);
				table.append("</td>");
				table.append(System.lineSeparator());
				
				table.append("\t\t\t\t\t\t<td>");
				table.append(createMessages(className));
				table.append("</td>");
				table.append(System.lineSeparator());
				
				table.append("\t\t\t\t\t</tr>");
				table.append(System.lineSeparator());
				
				links.add(table.toString());
			}
		}
		
		Collections.sort(links);
		for (int i = 0; i < links.size(); i++)
			ret.append(links.get(i));
		
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
		
		if ((className == null) || className.isEmpty())
			return ret.toString();
		
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
							linkSrc = "<a href=\"" +ResourceBundle.getBundle(
									TestRunner.BUNDLE_FILE).getString(
											"result_checksoure") + "_Test" +
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
	
	/**
	 * Searching the correct result file using the test name.
	 * 
	 * @param name The name of test
	 * 
	 * @return The result file
	 */
	public String getResultFileFromTestName(String name) {
		for (int i = 0; i < _resultFiles.size(); i++)
			if (_resultFiles.get(i).indexOf(name) > -1)
				return _resultFiles.get(i);
		
		return new String();
	}
	
	/**
	 * Returns the name of result file
	 * 
	 * @return The name of result file
	 */
	public String getResultFile() {
		return _resultFile;
	}
}