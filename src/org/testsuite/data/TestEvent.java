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

import java.util.EventObject;

/**
 * Stores all necessary data for a test event.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestEvent extends EventObject {
	/**
	 * Saves the name of the test.
	 */
	private String _name;
	
	/**
	 * Saves the package of the test.
	 */
	private String _package;
	
	/**
	 * Saves the id for the test.
	 */
	private int _testId;
	
	/**
	 * Saves the id for the test suite.
	 */
	private int _suiteId;
	
	/**
	 * Saves the string for the result.
	 */
	private String _result;

	/**
	 * Initialize the data of the test event.
	 * 
	 * @param src Object that the event has been triggered.
	 * 
	 * @param packageName The name of the package.
	 * 
	 * @param name The name of the test.
	 * 
	 * @param suite The id of the test suite.
	 * 
	 * @param test The id of the test.
	 * 
	 * @param result The string of the result.
	 */
	public TestEvent(Object src, String packageName, String name, int suite,
			int test, String result) {
		super(src);
		_package = packageName;
		_name = name;
		_testId = test;
		_suiteId = suite;
		_result = result;
	}

	/**
	 * Return the name of the test.
	 * 
	 * @return The name of the test.
	 */
	public String getName() {
		return _name;
	}
	
	/**
	 * Returns the name of the package.
	 * 
	 * @return The name of the package.
	 */
	public String getPackageName() {
		return _package;
	}
	
	/**
	 * Return the id of the test suite.
	 * 
	 * @return The id of the test suite.
	 */
	public int getSuiteId() {
		return _suiteId;
	}
	
	/**
	 * Returns the id of the test.
	 * 
	 * @return The id of the test.
	 */
	public int getTestId() {
		return _testId;
	}
	
	/**
	 * Return the string of the result.
	 * 
	 * @return The string of result.
	 */
	public String getResult() {
		return _result;
	}
}
