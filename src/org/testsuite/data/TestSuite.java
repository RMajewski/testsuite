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

import java.util.ArrayList;
import java.util.List;

/**
 * Saves the data for a test suite.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestSuite {
	/**
	 * Saves the list with the corresponding tests
	 */
	private List<Test> _tests;
	
	/**
	 * Stores the name of the test suite
	 */
	private String _name;
	
	/**
	 * Saves the package, which contains the Tests
	 */
	private String _package;
	
	/**
	 * Saves whether the package exists or not.
	 */
	private boolean _exists;
	
	/**
	 * Initialize the data 
	 */
	public TestSuite() {
		_name = new String();
		_tests = new ArrayList<Test>();
		_package = new String();
		_exists = false;
	}
	
	/**
	 * Sets the name
	 * 
	 * @param name New name of the test suite
	 */
	public void setName(String name) {
		_name = name;
	}
	
	/**
	 * Returns the name of the test suite
	 * 
	 * @return Name of the test suite
	 */
	public String getName() {
		return _name;
	}
	
	/**
	 * Specifies the name of the package firmly.
	 * 
	 * @param packname Name of the package
	 */
	public void setPackage(String packname) {
		if (packname == null)
			throw new IllegalArgumentException("Where was null as parameter.");
		_package = packname;
	}
	
	/**
	 * Returns the name of the package
	 * 
	 * @return Name of the packages
	 */
	public String getPackage() {
		return _package;
	}
	
	/**
	 * Specifies whether the package exists or not
	 * 
	 * @param exists In existence the package?
	 */
	public void setExists(boolean exists) {
		_exists = exists;
	}
	
	/**
	 * Returns whether the package exists or not.
	 * 
	 * @return In existence the package?
	 */
	public boolean isExists() {
		return _exists;
	}
	
	/**
	 * Adds the specified test to list
	 * 
	 * @param test Test, which is to be added to the list of tests
	 */
	public void addTest(Test test) {
		_tests.add(test);
	}
	
	/**
	 * Gets the specified test
	 * 
	 * @param index Location at which the test in the list is located
	 * 
	 * @return Of specified Test
	 */
	public Test getTest(int index) {
		return _tests.get(index);
	}
	
	/**
	 * Determines the number of tests in this test suite and returns.
	 * 
	 * @return Number of Tests
	 */
	public int testCount() {
		return _tests.size();
	}
}
