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

package tests.fixtures.fit;

import fit.ActionFixture;
import tests.fit.TestRun;

/**
 * Implements the fixtures for testing the run function of Fixture.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class FixtureRun extends ActionFixture {

	/**
	 * Save the instance of test class
	 */
	private TestRun _tests;
	
	/**
	 * Initialize the tests
	 */
	public FixtureRun() {
		_tests = new TestRun();
	}
	
	/**
	 * Returns if the parameter has been read correctly.
	 * 
	 * @return If the parameter has been read correctly?
	 */
	public boolean isReadRightParamterOnRun() {
		return _tests.isParameterReadRight();
	}
	
	/**
	 * Tests if the parameter is properly read as a string.
	 * 
	 * @param param Test parameter
	 */
	public void testString(String param) {
		boolean test = false;
		if ((param != null) && !param.isEmpty())
			test = true;
		_tests.setParameterReadRight(test);
	}
	
	/**
	 * Tests if the parameter is properly read as a integer.
	 * 
	 * @param param Test parameter
	 */
	public void testInteger(int param) {
		boolean test = false;
		if (param > 0) 
			test = true;
		_tests.setParameterReadRight(test);
	}
	
	/**
	 * Tests if the parameter is properly read as a boolean.
	 * 
	 * @param param Test parameter
	 */
	public void testBoolean(boolean param) {
		_tests.setParameterReadRight(param);
	}
	
	/**
	 * Tests if the parameter is properly read as a double.
	 * 
	 * @param param Test parameter
	 */
	public void testDouble(double param) {
		boolean test = false;
		if (param > 0) 
			test = true;
		_tests.setParameterReadRight(test);
	}
	
	/**
	 * Tests if the parameter is properly read as a long.
	 * 
	 * @param param Test parameter
	 */
	public void testLong(long param) {
		boolean test = false;
		if (param > 0) 
			test = true;
		_tests.setParameterReadRight(test);
	}
}
