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

package org.testsuite.data;

/**
 * In this class stores information about a to-do list entry.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TodoData {
	/**
	 * Saves the line of source code
	 */
	private String _line;
	
	/**
	 * Saves the number of source code line
	 */
	private int _number;
	
	/**
	 * Saves the name of source code file
	 */
	private String _file;
	
	/**
	 * Initialize a empty data record
	 */
	public TodoData() {
		_line = new String();
		_number = -1;
		_file = null;
	}
	
	/**
	 * Initialize with the specified data
	 * 
	 * @param line The source code line
	 * 
	 * @param number The number of source code line
	 * 
	 * @param name The name of source code file
	 */
	public TodoData(String line, int number, String name) {
		_line = line;
		_number = number;
		_file = name;
	}
	
	/**
	 * Returns the source code line
	 * 
	 * @return The source code line
	 */
	public String getLine() {
		return _line;
	}
	
	/**
	 * Sets the source code line
	 * 
	 * @param line The new source code line
	 */
	public void setLine(String line) {
		_line = line;
	}
	
	/**
	 * Returns the number of source code line
	 * 
	 * @return The number of source code line
	 */
	public int getNumber() {
		return _number;
	}
	
	/**
	 * Sets the number of source code line
	 * 
	 * @param number The new number of source code line
	 */
	public void setNumber(int number) {
		_number = number;
	}
	
	/**
	 * Return the name of source file
	 * 
	 * @return The name of source file
	 */
	public String getFileName() {
		return _file;
	}
	
	/**
	 * Sets the name of source file
	 * 
	 * @param name The new name of source file
	 */
	public void setFileName(String name) {
		_file = name;
	}
}
