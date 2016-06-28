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
 * Saves a call of a method.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class CSCall {
	/**
	 * Saves the number of line
	 */
	private int _number;
	
	/**
	 * Saves the parameter
	 */
	private List<String> _parameter;
	
	/**
	 * Saves whether the call originates from the test or not.
	 */
	private boolean _callTest;
	
	/**
	 * Initialize the datas
	 * 
	 * @param number The Number of line
	 * 
	 * @param parameter The parameter
	 * 
	 * @param call Originates the call from the test file?
	 */
	public CSCall(int number, boolean call, String... parameter) {
		_number = number;
		_parameter = new ArrayList<String>();
		_callTest = call;
		
		if ((parameter != null) && (parameter.length > 0))
			for (int i = 0; i < parameter.length; i++)
				_parameter.add(parameter[i]);
	}
	
	/**
	 * Initialize the datas
	 * 
	 * @param number The Number of line
	 * 
	 * @param parameter The parameter
	 */
	public CSCall(int number, boolean call) {
		this(number, call, new String[0]);
	}
	
	/**
	 * Returns the number of source line
	 * 
	 * @return The number of source line
	 */
	public int getNumber() {
		return _number;
	}
	
	/**
	 * Sets the number of source line
	 * 
	 * @param number The number of source line
	 */
	public void setNumber(int number) {
		_number = number;
	}
	
	/**
	 * Returns the parameter
	 * 
	 * @param index The index for the returned parameter
	 * 
	 * @return The parameter
	 */
	public String getParameter(int index) {
		return _parameter.get(index);
	}
	
	/**
	 * Added a parameter to the list of parameters.
	 * 
	 * @param parameter The new parameter
	 */
	public void addParameter(String parameter) {
		_parameter.add(parameter);
	}
	
	/**
	 * Returns the count of parameters.
	 * 
	 * @return The count of parameters.
	 */
	public int parameterCount() {
		return _parameter.size();
	}

	/**
	 * Returns if the call from test file
	 * 
	 * @return If the call from test file?
	 */
	public boolean isCallFromTestFile() {
		return _callTest;
	}
	
	/**
	 * Sets if the call from test file
	 */
	public void setCallFromTestFile(boolean call) {
		_callTest = call;
	}
}
