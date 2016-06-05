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

/**
 * Hold the informations about a parameter.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class CSParameter {
	/**
	 * Saves the type of parameter.
	 */
	private String _type;
	
	/**
	 * Saves the name of parameter.
	 */
	private String _name;
	
	/**
	 * Initialize the data of this class.
	 */
	public CSParameter(String type, String name) {
		if ((type == null) || type.isEmpty())
			throw new IllegalArgumentException();
		if ((name == null) || type.isEmpty())
			throw new IllegalArgumentException();
		_type = type;
		_name = name;
	}
	
	/**
	 * Returns the type of parameter.
	 * 
	 * @return The type of parameter.
	 */
	public String getType() {
		return _type;
	}
	
	/**
	 * Sets the type of parameter
	 * 
	 * @param type The new type of the parameter
	 */
	public void setType(String type) {
		if ((type == null) || type.isEmpty())
			throw new IllegalArgumentException();
		_type = type;
	}
	
	/**
	 * Returns the name of the parameter.
	 * 
	 * @return The name of parameter.
	 */
	public String getName() {
		return _name;
	}
	
	/**
	 * Sets the name of the parameter.
	 * 
	 * @param name The new name of parameter.
	 */
	public void setName(String name) {
		if ((name == null) || name.isEmpty())
			throw new IllegalArgumentException();
		_name = name;
	}

}