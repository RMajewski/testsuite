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
import java.util.List;

public class ListMethod {
	/**
	 * Saves the list of the methods
	 */
	private List<CSMethod> _list;
	
	/**
	 * Initialize the class
	 */
	public ListMethod() {
		_list = new ArrayList<CSMethod>();
	}
	
	/**
	 * Returns the size of list of methods.
	 * 
	 * @return The size of list of methods.
	 */
	public int size() {
		return _list.size();
	}
	
	/**
	 * Returns the specified method from the list of method.
	 * 
	 * @param index Index for the method.
	 * 
	 * @return The specified method from the list of method.
	 */
	public CSMethod get(int index) {
		return _list.get(index);
	}
	
	/**
	 * Added a method to the list of methods.
	 * 
	 * @param method Method, which is to be added to the list of methods.
	 */
	public void add(CSMethod method) {
		_list.add(method);
	}
	
	/**
	 * Returns the list of methods.
	 * 
	 * @return The list of methods.
	 */
	public List<CSMethod> list() {
		return _list;
	}
	
	/**
	 * Converts a list to ListMethod.
	 * 
	 * @param list The list object.
	 * 
	 * @return The ListMethod object.
	 */
	public static ListMethod convertFromList(List<CSMethod> list) {
		ListMethod ret = new ListMethod();
		
		for (int i = 0; i < list.size(); i++)
			ret.add(list.get(i));
		
		return ret;
	}
	
	/**
	 * Determined the method by start line
	 * 
	 * @param line The line number of start
	 * 
	 * @return The method
	 */
	public CSMethod determineMethod(int line) {
		for (int i = 0; i < _list.size(); i++) {
			if (_list.get(i).getLine() == line) {
				return _list.get(i);
			}
		}

		
		return null;
	}
}
