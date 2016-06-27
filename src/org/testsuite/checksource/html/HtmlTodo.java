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
import java.util.List;

import org.testsuite.data.TodoData;
import org.testsuite.helper.HelperHtml;
import org.testsuite.helper.HelperHtmlCodeJava;

/**
 * Creates the to-do list from the source files.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class HtmlTodo extends Html {
	
	/**
	 * Saves the to do list
	 */
	private List<TodoData> _todo;

	/**
	 * Saves the instance of this class.
	 */
	private static HtmlTodo _instance;
	
	/**
	 * Initialize the class.
	 */
	private HtmlTodo() {
		super(null);
		_todo = new ArrayList<TodoData>();
		_resultBundle = "todo_result_file";
	}
	
	/**
	 * Returns the instance of this class.
	 * 
	 * @return The instance of this class.
	 */
	public static HtmlTodo getInstance() {
		if (_instance == null)
			_instance = new HtmlTodo();
		
		return _instance;
	}

	/**
	 * Generate the HTML output for the to-do list file.
	 */
	public void createHtml() {
		// Generate the File
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(getPathAndResultFile()));
			
			bw.write(HelperHtml.head(_bundle.getString("todo_head"),
					_bundle.getString("todo_description"), HtmlMenu.TODO_LIST));
			
			bw.write(createTodoList());
			
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
	 * Creates the HTML output for the to-do list
	 * 
	 * @return HTML output for the to-do list
	 */
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
	 * Added a to-do to the list
	 * 
	 * @param todo The to-do that added to the list
	 */
	public void addTodo(TodoData todo) {
		_todo.add(todo);
	}
	
	/**
	 * Returns the count of to-do list
	 * 
	 * @return Count of to-do list
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
	 */
	public TodoData getTodo(int index) {
		return _todo.get(index);
	}
}
