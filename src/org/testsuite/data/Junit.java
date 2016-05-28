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
 * Saves the data for a junit test
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class Junit extends Test {
	/**
	 * Stores the number of successful tests
	 */
	protected int _ok;
	
	/**
	 * Stores the number of tests that failed.
	 */
	protected int _fail;
	
	/**
	 * Initializes the data of the class
	 * 
	 * @see Test#Test()
	 */
	public Junit() {
		super();
		
		_ok = 0;
		_fail = 0;
	}
	
	/**
	 * Returns the number of successfully executed tests back
	 * 
	 * @return Number of successful tests
	 */
	public int getOk() {
		return _ok;
	}
	
	/**
	 * Stores the number of tests completed successfully
	 * 
	 * @param ok Number of successful tests
	 */
	public void setOk(int ok) {
		_ok = ok;
	}
	
	/**
	 * Returns the number of tests that failed.
	 * 
	 * @return Number of faulty tests
	 */
	public int getFail() {
		return _fail;
	}
	
	/**
	 * Stores the number of tests that failed.
	 * 
	 * @param fail Number of faulty tests
	 */
	public void setFail(int fail) {
		_fail = fail;
	}
}
