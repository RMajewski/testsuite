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
 * Stores all necessary data for a test select event.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
@SuppressWarnings("serial")
public class TestSelectEvent extends EventObject {
	/**
	 * Saves the index for the TestRunner class.
	 */
	private int _testRunner;
	
	/**
	 * Saves the index for the TestSuite class.
	 */
	private int _testSuite;
	
	/**
	 * Saves the index for the Test class
	 */
	private int _test;
	
	/**
	 * Initialize this class
	 */
	public TestSelectEvent(Object src, int testRunner, int testSuite,
			int test) {
		super(src);
		_testRunner = testRunner;
		_testSuite = testSuite;
		_test = test;
	}
	
	/**
	 * Returns the index of TestRunner.
	 * 
	 * @return Index of TestRunner.
	 */
	public int getIndexTestRunner() {
		return _testRunner;
	}
	
	/**
	 * Returns the index of TestSuite.
	 * 
	 * @return Index of TestSuite.
	 */
	public int getIndexTestSuite() {
		return _testSuite;
	}
	
	/**
	 * Returns the index of Test.
	 * 
	 * @return Index of Test.
	 */
	public int getIndexTest() {
		return _test;
	}
}
