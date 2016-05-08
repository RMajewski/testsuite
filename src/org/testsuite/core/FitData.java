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

package org.testsuite.core;

/**
 * Saves the data for a Fit Test
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class FitData extends TestData {
	/**
	 * Stores the number of allegations properly executed.
	 */
	private int _right;
	
	/**
	 * Stores the number of claims that are false.
	 */
	private int _wrong;
	
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
	 * @see TestData#TestData()
	 */
	public FitData() {
		super();
		
		_exception = 0;
		_ignore = 0;
		_right = 0;
		_wrong = 0;
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
	
	/**
	 * Specifies the number of correct tests.
	 * 
	 * @param count Number of correct tests
	 */
	public void setRight(int count) {
		_right = count;
	}
	
	/**
	 * Returns the number of correct tests.
	 * 
	 * @return Number of correct tests
	 */
	public int getRight() {
		return _right;
	}
	
	/**
	 * Legt die Anzahl der falschen Tests fest.
	 * 
	 * @param count Number of false tests
	 */
	public void setWrong(int count) {
		_wrong = count;
	}
	
	/**
	 * Returns the number of false tests.
	 * 
	 * @return Number of false tests
	 */
	public int getWrong() {
		return _wrong;
	}
}
