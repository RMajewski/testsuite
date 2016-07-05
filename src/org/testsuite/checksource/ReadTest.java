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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 
 * @author René Majewski
 *
 * @version 0.1
 * 
 * @deprecated Is no longer needed.
 */
public class ReadTest implements Read {
	/**
	 * Saves the name of class
	 */
	private HashSet<String> _classNames;
	
	/**
	 * Saves the names of variables.
	 */
	private List<String> _variables;
	
	/**
	 * If a multiline comment
	 */
	private boolean _multiComment;
	
	/**
	 * Saves the multi line
	 */
	private String _line;
	
	/**
	 * Initialize the data
	 */
	public ReadTest() {
		_classNames = new HashSet<String>();
		_variables = new ArrayList<String>();
		_line = new String();
		_multiComment = false;
	}

	/**
	 * 
	 * @param lineNumber The number of line in the test file
	 * 
	 * @param line The line of source code of the test file
	 * 
	 * @param methods The list of methods from source file
	 */
	@Override
	public void read(int lineNumber, String line, List<CSMethod> methods) {
		if ((line.indexOf("class") > -1) || (line.indexOf("import") > -1) || 
				(line.indexOf("package") > -1) || line.trim().isEmpty())
				return;
		
		if (line.startsWith("\\s*/\\*")) {
			_multiComment = true;
			return;
		}
		
		if (_multiComment && (line.indexOf("*/") > -1)) {
			_multiComment = false;
			return;
		}
		
		if (_multiComment)
			return;
		
		if ((line.indexOf(";") == -1) && 
				!line.endsWith("\\{\\s*") && (line.indexOf("@") == -1))  {
			_line += line;
			return;
		}
		
		if (line.indexOf(";") > -1)
			_line += line.trim();
		
		for (Iterator<String> i = _classNames.iterator(); i.hasNext();) {
			String cls = i.next();
			if (line.indexOf(cls) > -1) {
				
				if (line.matches("^\\s*(public|private|protected)[\\w\\s]*(" +
						cls + ")[\\w\\s]*;$")) {
					_variables.add(
							_line.substring(_line.indexOf(cls) + 
									cls.length() + 1, _line.indexOf(";")));
				}
				
				if(line.matches("^\\s*(" + cls + 
						")[\\w\\s]*=[\\p{Graph}\\s]*;$")) {
					_variables.add(
							_line.substring(_line.indexOf(cls) + 
									cls.length() + 1, 
									_line.indexOf("=")).trim());
				}
			}
		}
		
		for (int i = 0; i < methods.size(); i++) {
			CSMethod method = methods.get(i);
			if (line.matches("^\\s*@CheckSource[\\w\\s(=\")\\{\\},]*")) {
				readCheckSourceAnnotation(line, methods, i, lineNumber);
			} else if (line.indexOf(";") > -1) {
				StringBuilder test = new StringBuilder(method.getClassName());
				
				for (int var = 0; var < _variables.size(); var++) {
					test.append("|");
					test.append(_variables.get(var));
				}
				
				if (_line.matches(".*(" + test.toString() + ")." + 
						method.getName() + "\\([\\w\\s\\[\\]=\\{\\},\"]*\\).*")) {
					addCall(method, lineNumber, _line.substring(
							_line.indexOf(method.getName() + "(") + 
							method.getName().length()));
				} else if (_line.matches(".*new " + method.getClassName() + 
						"\\([\\w\\s\\[\\]=\\{\\},\"]*\\).*") && 
						method.getClassName().equals(method.getName())) {
					addCall(method, lineNumber, _line.substring(
							_line.indexOf(method.getName() + "(") +
							method.getName().length()));
				}
			}
		}
		
		if (_line.indexOf(";") > -1)
			_line = new String();
	}

	/**
	 * Determine the parameters of the check source annotation.
	 * 
	 * @param line The actual source line
	 * 
	 * @param methods List of methods
	 * 
	 * @param actual The actual method
	 * 
	 * @param lineNumber The number of actual source line
	 */
	private void readCheckSourceAnnotation(String line, List<CSMethod> methods, 
			int actual, int lineNumber) {
		CSMethod method = methods.get(actual);
		
		String methodName = line.substring(line.indexOf("(") + 1, 
				line.indexOf("="));
		if (methodName.equals("method")){
			String name = line.substring(line.indexOf("=\"") + 2, 
					line.indexOf("\")"));
			
			if (methods.get(actual).getName().equals(name))
				method.addCall(new CSCall(lineNumber, true));
		} else if (methodName.equals("methodList")) {
			String[] names = line.substring(line.indexOf("={") + 2, line.
					indexOf("}")).split(",");
			System.out.println(Arrays.toString(names));
			for (int i = 0; i < names.length; i++) {
				String name = names[i].substring(names[i].indexOf("\"") + 1,
						names[i].lastIndexOf("\""));
				if (methods.get(actual).getName().equals(name))
					method.addCall(new CSCall(lineNumber, true));
			}
		}
	}
	
	/**
	 * Added a call to the specified method.
	 * 
	 * @param method The method to which a call is to be added.
	 * 
	 * @param lineNumber The number of source line
	 * 
	 * @param parameters The parameters passed
	 */
	private void addCall(CSMethod method, int lineNumber, String parameters) {
		CSCall call = new CSCall(lineNumber, true);
		
		if ((parameters != null) && !parameters.isEmpty()) {
			String[] tmp = readParameters(parameters);
			for (int i = 0; i < tmp.length; i++)
				call.addParameter(tmp[i].trim());
		}
		
		if (call.parameterCount() == method.parametersCount())
			method.addCall(call);
	}
	
	private String[] readParameters(String src) {
		List<String> ret = new ArrayList<String>();
		
		int level = 0;
		int start = 1;
		int end = 1;
		boolean quote = false;
		
		for (int i = 0; i < src.length(); i++) {
			if ((src.charAt(i) == '"') && !quote)
				quote = true;
			else  if (src.charAt(i) == '"' && quote) {
				quote = false;
				continue;
			}
			
			if (quote) {
				continue;
			}
			
			if ((src.charAt(i) == '(') || (src.charAt(i) == '{') ||
					(src.charAt(i) == '['))
				level++;
			else if ((src.charAt(i) == ')')  || (src.charAt(i) == '}') ||
			(src.charAt(i) == ']'))
				level--;
			
			if (((src.charAt(i) == ',') && (level == 1)) || 
					((level == 0) && ((src.charAt(i) == ')') ||
							(src.charAt(i) == '}') || (src.charAt(i) == ']')) &&
					(end < i))) {
				end = i;
				ret.add(src.substring(start, end));
				start = i + 1;
			}
		}
		
		return ret.toArray(new String[ret.size()]);
	}

	/**
	 * Added a class name to the list of class names.
	 * 
	 * @param name The new name of class
	 */
	public void addClassName(String name) {
		_classNames.add(name);
	}
}
