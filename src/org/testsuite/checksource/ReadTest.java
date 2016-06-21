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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author René Majewski
 *
 * @version 0.1
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
	 * Initialize the data
	 */
	public ReadTest() {
		_classNames = new HashSet<String>();
		_variables = new ArrayList<String>();
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
		if ((line.indexOf("class") > -1) || (line.indexOf("import") > -1))
				return;
		
		for (Iterator<String> i = _classNames.iterator(); i.hasNext();) {
			String cls = i.next();
			if (line.indexOf(cls) > -1) {
				
				if (line.matches("^\\s*(public|private|protected)[\\w\\s]*(" +
						cls + ")[\\w\\s]*;$")) {
					_variables.add(
							line.substring(line.indexOf(cls) + cls.length() + 1, 
									line.indexOf(";")));
				}
				
				if(line.matches("^\\s*(" + cls + 
						")[\\w\\s]*=[\\p{Graph}\\s]*;$")) {
					_variables.add(
							line.substring(line.indexOf(cls) + cls.length() + 1, 
									line.indexOf("=")).trim());
				}
			}
		}
		
		for (int i = 0; i < methods.size(); i++) {
			CSMethod method = methods.get(i);
			if (line.matches("^\\s*@CheckSource[\\w\\s(=\")\\{\\},]*")) {
				readCheckSourceAnnotation(line, methods, i, lineNumber);
			} else if ((line.indexOf(method.getClassName() + "." + method.getName()) > -1) ||
				(line.indexOf("new " + method.getName() + "(") > -1)){
				method.addCall(new CSCall(lineNumber, true));
			} else {
				for (int j = 0; j < _variables.size(); j++) {
					if (line.indexOf(_variables.get(j) + "." + method.getName()) > -1) {
						method.addCall(new CSCall(lineNumber, true));
					}
				}
			}
		}
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
			for (int i = 0; i < names.length; i++) {
				String name = names[i].substring(names[i].indexOf("\"") + 1,
						names[i].lastIndexOf("\""));
				if (methods.get(actual).getName().equals(name))
					method.addCall(new CSCall(lineNumber, true));
			}
		}
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
