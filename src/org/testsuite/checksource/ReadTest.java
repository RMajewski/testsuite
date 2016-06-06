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
		for (Iterator<String> i = _classNames.iterator(); i.hasNext();) {
			if (line.indexOf(i.next()) > -1) {
				String[] tmp = line.split(" ");
				_variables.add(tmp[1]);
			}
		}
		
		for (int i = 0; i < methods.size(); i++) {
			CSMethod method = methods.get(i);
			if ((line.indexOf(method.getClassName() + "." + method.getName()) > -1) ||
				(line.indexOf("new " + method.getName() + "(") > -1)){
				method.addCall(lineNumber);
			} else {
				for (int j = 0; j < _variables.size(); j++) {
					if (line.indexOf(_variables.get(j) + "." + method.getName()) > -1) {
						method.addCall(lineNumber);
					}
				}
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
