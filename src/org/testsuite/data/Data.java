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
 * In this class attributes are implemented, which are used both in the test
 * class and in the test suite class.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class Data {
	/**
	 * Stores the name of the test
	 */
	protected String _name;
	
	/**
	 * Saves whether the file exists.
	 */
	protected boolean _exists;
	
	/**
	 * Saves the id for the test
	 */
	protected int _id;

	/**
	 * Initialize the data of this class
	 */
	public Data() {
		_name = new String();
		_exists = false;
		_id = -1;
	}
	
	/**
	 * Specifies whether the file exists.
	 * 
	 * @param exists In existence Testfile?
	 */
	public void setExists(boolean exists) {
		_exists = exists;
	}
	
	/**
	 * Returns exists in the test file.
	 * 
	 * @return In existence testfile?
	 */
	public boolean isExists() {
		return _exists;
	}
	
	/**
	 * Specifies the name requested.
	 * 
	 * @param name The new Name
	 */
	public void setName(String name) {
		if ((name == null) || name.isEmpty())
			throw new IllegalArgumentException();
		
		_name = name;
	}
	
	/**
	 * Returns the name.
	 * 
	 * @return Name
	 */
	public String getName() {
		return _name;
	}
	
	/**
	 * Returns the id
	 * 
	 * @return The id
	 */
	public int getId() {
		return _id;
	}
	
	/**
	 * Specifies the name requested.
	 * 
	 * @param id The new id.
	 */
	public void setId(int id) {
		_id = id;
	}
}
