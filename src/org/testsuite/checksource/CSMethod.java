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

/**
 * Holds informations about a method.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class CSMethod {
	/**
	 * Saves the list of parameters.
	 */
	private List<CSParameter> _parameter;
	
	/**
	 * Saves the last line of this method.
	 */
	private int _lastLine;
	
	/**
	 * Saves the line number.
	 */
	private int _line;
	
	/**
	 * Saves the value.
	 */
	private String _value;
	
	/**
	 * Saves the type.
	 */
	private String _type;
	
	/**
	 * Saves the visibility modifiers
	 */
	private String _modifier;
	
	/**
	 * Saves the the list of calls
	 */
	private List<Integer> _calls;
	
	/**
	 * Saves the name
	 */
	private String _name;
	
	/**
	 * Saves the name of class
	 */
	private String _className;
	
	/**
	 * Saves whether the method is deprecated.
	 */
	private boolean _deprecated;

	/**
	 * Initialize the data
	 */
	public CSMethod() {
		super();
		_parameter = new ArrayList<CSParameter>();
		_lastLine = -1;
		_line = -1;
		_value = new String();
		_modifier = new String();
		_type = new String();
		_calls = new ArrayList<Integer>();
		_name = new String();
		_className = new String();
		_deprecated = false;
	}
	
	/**
	 * Returns the number of parameters.
	 */
	public int parametersCount() {
		return _parameter.size();
	}
	
	/**
	 * Adds a parameter to the list of parameters.
	 * 
	 * @param parameter The new parameter
	 */
	public void addParameter(CSParameter parameter) {
		_parameter.add(parameter);
	}
	
	/**
	 * Returns the specified parameter from the list of parameters.
	 * 
	 * @param index The index of the parameter was return.
	 * 
	 * @return The specified parameter
	 */
	public CSParameter getParameter(int index) {
		return _parameter.get(index);
	}
	
	/**
	 * Return the number of last line.
	 * 
	 * @return The number of last line.
	 */
	public int getLastLineNumber() {
		return _lastLine;
	}
	
	/**
	 * Sets the number of last line.
	 * 
	 * @param last The new number of last line
	 */
	public void setLastLineNumber(int last) {
		_lastLine = last;
	}

	/**
	 * Returns the line number in which has been declared.
	 * 
	 * @return The line number in which has been declared.
	 */
	public int getLine() {
		return _line;
	}

	/**
	 * Sets the line number in which has been declared.
	 * 
	 * @param line The line number in which has been declared.
	 */
	public void setLine(int line) {
		_line = line;
	}

	/**
	 * Returns the value
	 * 
	 * @return The value
	 */
	public String getValue() {
		return _value;
	}

	/**
	 * Sets the value
	 * 
	 * @param value The new value
	 */
	public void setValue(String value) {
		if (value == null)
			throw new IllegalArgumentException();
		_value = value;
	}

	/**
	 * Returns the type
	 * 
	 * @return The type
	 */
	public String getType() {
		return _type;
	}

	/**
	 * Sets the type
	 * 
	 * @param type The new type
	 */
	public void setType(String type) {
		if (type == null)
			throw new IllegalArgumentException();
		_type = type;
	}

	/**
	 * Returns the modifier.
	 * 
	 * @return The modifier.
	 */
	public String getModifier() {
		return _modifier;
	}

	/**
	 * Sets the modifier
	 * 
	 * @param modifier The new modifier
	 */
	public void setModifier(String modifier) {
		if (modifier == null)
			throw new IllegalArgumentException();
		_modifier = modifier;
	}
	
	/**
	 * Returns the number of calls
	 * 
	 * @return The Number of calls
	 */
	public int callsCount() {
		return _calls.size();
	}
	
	/**
	 * Added a call to the list of calls.
	 * 
	 * @param call The new call
	 */
	public void addCall(int call) {
		_calls.add(call);
	}
	
	/**
	 * Returns the specified call from the list of calls.
	 * 
	 * @param index The index of the call.
	 * 
	 * @return The specified call
	 */
	public int getCall(int index) {
		return _calls.get(index);
	}
	
	/**
	 * Returns the name
	 * 
	 * @return The name
	 */
	public String getName() {
		return _name;
	}
	
	/**
	 * Sets the name
	 * 
	 * @param name The new name
	 */
	public void setName(String name) {
		if ((name == null) || name.isEmpty())
			throw new IllegalArgumentException();
		_name = name;
	}
	
	/**
	 * Return the name of class
	 * 
	 * @return The name of class
	 */
	public String getClassName() {
		return _className;
	}
	
	/**
	 * Sets the name of class
	 * 
	 * @param name The new class name
	 */
	public void setClassName(String name) {
		if ((name == null) || name.isEmpty())
			throw new IllegalArgumentException();
		_className = name;
	}
	
	/**
	 * Returns if the method deprecated.
	 * 
	 * @return If the method deprecated?
	 */
	public boolean isDeprecated() {
		return _deprecated;
	}
	
	/**
	 * Sets if the method deprecated.
	 * 
	 * @param deprecated If the method deprecated?
	 */
	public void setDeprecated(boolean deprecated) {
		_deprecated = deprecated;
	}
}