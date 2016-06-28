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

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.testsuite.checksource.CSConfig;
import org.testsuite.checksource.CSMethod;
import org.testsuite.checksource.SourceLine;
import org.testsuite.core.TestRunner;
import org.testsuite.data.Config;
import org.testsuite.data.TodoData;
import org.testsuite.helper.HelperCalendar;
import org.testsuite.helper.HelperFile;
import org.testsuite.helper.HelperHtml;
import org.testsuite.helper.HelperHtmlCodeJava;
import org.testsuite.helper.HelperUsedColor;

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
	 * 
	 * @deprecated
	 */
	private List<String> _noneExists;
	
	/**
	 * Saves the to do list
	 * 
	 * @deprecated Use HtmlTodo
	 */
	private List<TodoData> _todo;

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
		_todo = new ArrayList<TodoData>();
		_resultBundle = "result_checksoure";
		_resultBundleFile = TestRunner.BUNDLE_FILE;
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
	 * @param result The new result file added to the list.
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
	 * 
	 * @deprecated Use {@link HtmlNoneExistFile#addNoneExistsFileName(String)}
	 */
	public void addNoneExistsFileName(String name) {
		_noneExists.add(name);
	}
	
	/**
	 * Generate the HTML output for the overview file.
	 */
	public void createHtml() {
		// Generate the File
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(getPathAndResultFile()));

			String date = HelperCalendar.datetimeToString(new Date().getTime());

			bw.write(HelperHtml.head(_bundle.getString("overview_head") + " (" +
					date + ")", _bundle.getString("overview_description") + 
					" (" + date + ")", HtmlMenu.OVERVIEW));
			
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
	 * Generates a list of the files have not been tested.
	 * 
	 * @return The HTML list of the files have not been tested.
	 * 
	 * @deprecated use {@link HtmlNoneTestedSourceFiles}
	 */
	@SuppressWarnings("unused")
	private String createNoneTestedList() {
		StringBuilder ret = new StringBuilder();
		
		if (CSConfig.getInstance().isListNoneTestedFiles()) {
			List<String> none = new ArrayList<String>();
			List<File> list = HelperFile.getSourceFiles(
					CSConfig.getInstance().getNoneListedPath(), "(.*\\.java$)");
			for (int file = 0; file < list.size(); file++) {
				String name = list.get(file).getName();
				name = name.substring(0, name.lastIndexOf("."));
				boolean available = false;
				for (int rs = 0; rs < _resultFiles.size(); rs++)
					if (_resultFiles.get(rs).indexOf(name) > -1)
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
				ret.append(_bundle.getString("none_list"));
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

	/**
	 * Creates the HTML output for the to-do list
	 * 
	 * @return HTML output for the to-do list
	 * 
	 * @deprecated Use {@link HtmlTodo}.
	 */
	@SuppressWarnings("unused")
	private String createTodoList() {
		StringBuilder ret = new StringBuilder();
		
		if (_todo.size() > 0) {
			ret.append("\t\t\t<div class=\"todoList\">");
			ret.append(System.lineSeparator());

			ret.append("\t\t\t\t<p>");
			ret.append(_bundle.getString("todo_list"));
			ret.append("</p>");
			ret.append(System.lineSeparator());

			ret.append("\t\t\t\t<table>");
			ret.append(System.lineSeparator());
			
			String td = "\t\t\t\t\t\t<td>";
			String trStart = "\t\t\t\t\t<tr>";
			String trEnd = "\t\t\t\t\t</tr>"; 
			
			ret.append(trStart);
			ret.append(System.lineSeparator());
			
			ret.append("\t\t\t\t\t\t<th style=\"width: 300px;\">");
			ret.append(_bundle.getString("todo_file"));
			ret.append("</th>");
			ret.append(System.lineSeparator());
			
			ret.append("\t\t\t\t\t\t<th style=\"width: 140px;\">");
			ret.append(_bundle.getString("todo_row"));
			ret.append("</th>");
			ret.append(System.lineSeparator());
			
			ret.append(td.replace("td", "th"));
			ret.append(_bundle.getString("todo_source"));
			ret.append("</th>");
			ret.append(System.lineSeparator());
			
			ret.append(trEnd);
			ret.append(System.lineSeparator());

			for (int todo = 0; todo < _todo.size(); todo++) {
				File file = new File(_todo.get(todo).getFileName());
				if (file.exists()) {
					String name = file.getName().substring(
							file.getName().indexOf("Test") + 4);
					
					ret.append(trStart);
					ret.append(System.lineSeparator());
					
					ret.append(td);
					ret.append("<a href=\"");
					ret.append(file.getAbsolutePath());
					ret.append("\">");
					ret.append(name);
					ret.append("</a></td>");
					ret.append(System.lineSeparator());
					
					ret.append(td);
					ret.append(_todo.get(todo).getNumber());
					ret.append("</td>");
					ret.append(System.lineSeparator());
					
					ret.append(td);
					ret.append(HelperHtmlCodeJava.formatString(
							_todo.get(todo).getLine().trim(), false, false, -1,
							null));
					ret.append("</td>");
					ret.append(System.lineSeparator());
					
					ret.append(trEnd);
					ret.append(System.lineSeparator());
				}
			}
			
			ret.append("\t\t\t\t</table>");
			ret.append(System.lineSeparator());
			
			ret.append("\t\t\t</div>");
			ret.append(System.lineSeparator());
		}
		
		return ret.toString();
	}

	/**
	 * Creates the HTML list deprecated methods
	 * 
	 * @return HTML list of deprecated methods
	 * 
	 * @deprecated Use {@link HtmlDeprecated}
	 */
	@SuppressWarnings("unused")
	private String createListOfDeprecated() {
		List<String> list = new ArrayList<String>();
		
		for (int method = 0; method < _methods.size(); method++)
			if (_methods.get(method).isDeprecated()) {
				StringBuilder li = new StringBuilder("\t\t\t\t\t<li>");
				li.append("<a href=\"");
				li.append(_methods.get(method).getHtmlOutputFile());
				li.append("\">");
				li.append(_methods.get(method).getClassName());
				li.append(".");
				li.append(_methods.get(method).getName());
				li.append("</a></li>");
				list.add(li.toString());
			}
		
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
	 * Creates the HTML list of none exists files.
	 * 
	 * @return The HTML list of none exists files.
	 * 
	 * @deprecated
	 */
	@SuppressWarnings("unused")
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
		
		Collections.sort(_resultFiles);
		String path = new String();
		
		for (int link = 0; link <_resultFiles.size(); link++) {
			File file = new File(_resultFiles.get(link));
			if (file.exists()) {
				String linkSrc = file.getAbsolutePath();
				String linkName = linkSrc.substring(linkSrc.indexOf("_") + 1);
				String className = linkName.substring(0, 
						linkName.indexOf(".html"));

				if (!path.equals(linkSrc.substring(linkSrc.indexOf(
						Config.getInstance().getPathSrc()),
						linkSrc.lastIndexOf("/")))) {
					path = linkSrc.substring(
							linkSrc.indexOf(Config.getInstance().getPathSrc()), 
							linkSrc.lastIndexOf("/"));
					ret.append("\t\t\t\t\t<tr>");
					ret.append(System.lineSeparator());
					
					ret.append("\t\t\t\t\t\t<td colspan=\"3\" class=\"newPath\">");
					ret.append(path);
					ret.append("</td>");
					ret.append(System.lineSeparator());

					ret.append("\t\t\t\t\t</tr>");
					ret.append(System.lineSeparator());
				}
				
				ret.append("\t\t\t\t\t<tr>");
				ret.append(System.lineSeparator());
				
				Color background = getMessageColor(className);
				
				ret.append("\t\t\t\t\t\t<td style=\"background: ");
				ret.append(HelperHtmlCodeJava.getInstance()
						.formatColor(background));
				ret.append(";\" >");
				ret.append("<a href=\"");
				ret.append(linkSrc);
				ret.append("\">");
				ret.append(linkName);
				ret.append("</a></td>");
				ret.append(System.lineSeparator());
				
				ret.append("\t\t\t\t\t\t<td style=\"background: ");
				ret.append(HelperHtmlCodeJava.getInstance()
						.formatColor(background));
				ret.append(";\" >");
				ret.append(className);
				ret.append("</td>");
				ret.append(System.lineSeparator());
				
				ret.append("\t\t\t\t\t\t<td style=\"background: ");
				ret.append(HelperHtmlCodeJava.getInstance()
						.formatColor(background));
				ret.append(";\" >");
				
				if (!background.equals(HelperUsedColor.PASS)) {
					ret.append("<a href=\"");
					ret.append(linkSrc);
					ret.append("\">");
					ret.append(_bundle.getString("overview_table"));
					ret.append("</a>");
				} else {
					ret.append("&nbsp;");
				}
				
				ret.append("</td>");
				ret.append(System.lineSeparator());
				
				ret.append("\t\t\t\t\t</tr>");
				ret.append(System.lineSeparator());
			}
		}
		
		ret.append("\t\t\t\t</table>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t</div>");
		ret.append(System.lineSeparator());
		
		return ret.toString();
	}
	
	/**
	 * Returns the color for the HTML table row.
	 * 
	 * @param className The name of the class
	 * 
	 * @return Color for the HTML table row.
	 */
	private Color getMessageColor(String className) {
		if ((className == null) || className.isEmpty())
			throw new IllegalArgumentException();
		
		for (int source = 0; source < _sources.size(); source++) {
			if (_sources.get(source).getClassName().equals(className)) {
				int messages = _sources.get(source).messageCount();
				
				if (messages > 0) {
					for (int message = 0; message < messages; message++) {
						if (_sources.get(source).getMessage(message)
								.getColor() == HelperUsedColor.ERROR)
							return HelperUsedColor.ERROR;
					}
					
					return HelperUsedColor.WARNING;
				}
			}
		}
		
		// Returns green
		return HelperUsedColor.PASS;
	}

	/**
	 * Creates the messages for the specified class.
	 * 
	 * @param className The name of class.
	 * 
	 * @return Messages for the specified class.
	 * 
	 * @deprecated Use {@link #createMessages(String, String)}
	 */
	@SuppressWarnings("unused")
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
						String link = "<a href=\"" +ResourceBundle.getBundle(
								TestRunner.BUNDLE_FILE).getString(
										"result_checksoure") + "_Test" +
							_methods.get(method).getClassName() + ".html#";
						if ((_methods.get(method).getClassName().equals(className)) && 
								(_methods.get(method).getLine() == 
								_sources.get(source).getLineNumber())) {
							linkSrc = link + _methods.get(method).getName() + 
									"\">";
							linkEnd = "</a>";
						} else { 
							linkSrc = link + "Line_" + 
									_methods.get(method).getLine() + "\">";
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
	 * Creates the messages for the specified class.
	 * 
	 * @param className The name of class.
	 * 
	 * @param src The link to the result file.
	 * 
	 * @return Messages for the specified class.
	 * 
	 * @deprecated The method never used.
	 */
	@SuppressWarnings("unused")
	private String createMessages(String className, String src) {
		if ((className == null) || className.isEmpty())
			return new String();
		
		StringBuilder ret = new StringBuilder();
		boolean out = false;
		String linkSrc = "<a href=\"" + src + "\">";
		Color background = HelperUsedColor.WARNING;
		
		for (int source = 0; source < _sources.size(); source++) {
			if (_sources.get(source).getClassName().equals(className)) {
				for (int message = 0; 
						message <_sources.get(source).messageCount();
						message++) {
					if (_sources.get(source).getMessage(message).getColor() ==
							HelperUsedColor.ERROR)
						background = HelperUsedColor.ERROR;
					out = true;
				}
			}
		}
		
		if (out) {
			ret.append("<span style=\"background: ");
			ret.append(HelperHtmlCodeJava.getInstance()
					.formatColor(background));
			ret.append(";\" >");
			ret.append(linkSrc);
			ret.append(ResourceBundle.getBundle(HtmlOut.BUNDLE_FILE)
					.getString("overview_table"));
			ret.append("</a>");
			ret.append("</span>");
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
	 * Added a to-do to the list
	 * 
	 * @param todo The todo that added to the list
	 * 
	 * @deprecated Use {@link HtmlTodo#addTodo(TodoData)}
	 */
	public void addTodo(TodoData todo) {
		_todo.add(todo);
	}
	
	/**
	 * Returns the count of to-do list
	 * 
	 * @return Count of to-do list
	 * 
	 * @deprecated {@link HtmlTodo#todoCount()}
	 */
	public int todoCount() {
		return _todo.size();
	}
	
	/**
	 * Returns the specified to-do of to-do list
	 * 
	 * @param index The index for the to-do
	 * 
	 * @return The specified to-do
	 * 
	 * @deprecated Use {@link HtmlTodo#getTodo(int)}
	 */
	public TodoData getTodo(int index) {
		return _todo.get(index);
	}
	
	/**
	 * Returns the list of methods.
	 * 
	 * @return List of methods
	 */
	public List<CSMethod> getMethodList() {
		return _methods;
	}
	
	/**
	 * Returns the list of result files.
	 * 
	 * @return List of result files.
	 */
	public List<String> getResultFileList() {
		return _resultFiles;
	}
}