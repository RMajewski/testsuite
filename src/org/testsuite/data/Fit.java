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
 * Saves the data for a Fit Test
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class Fit extends Junit {
	/**
	 * Stores the number of claims that have been ignored.
	 */
	private int _ignore;
	
	/**
	 * Stores the number that are faulty.
	 */
	private int _exception;
	
	/**
	 * Initializes the data of the class
	 * 
	 * @see Test#TestData()
	 */
	public Fit() {
		super();
		
		_exception = 0;
		_ignore = 0;
	}
	
	/**
	 * Specifies the number of failed tests.
	 * 
	 * @param count Number of faulty tests
	 */
	public void setException(int count) {
		_exception = count;
	}
	
	/**
	 * Returns the number of tests that failed.
	 * 
	 * @return Number of faulty tests.
	 */
	public int getException() {
		return _exception;
	}
	
	/**
	 * Specifies the number of ignored tests
	 * 
	 * @param count Number of ignored tests
	 */
	public void setIgnore(int count) {
		_ignore = count;
	}
	
	/**
	 * Returns the number of ignored tests.
	 * 
	 * @return Number of ignored tests
	 */
	public int getIgnore() {
		return _ignore;
	}
}
