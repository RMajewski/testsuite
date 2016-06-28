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

package tests.testsuite.checksource;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.testsuite.checksource.CSCall;

/**
 * Tests for the class {@link org.testsuite.checksource.CSCall}.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestCSCall {
	/**
	 * Save the instance of CSCall.
	 */
	private CSCall _call;
	
	/**
	 * Saves if the call from a test
	 */
	private boolean _test;
	
	/**
	 * Saves the line number
	 */
	private int _number;
	
	/**
	 * Saves the parameter array.
	 */
	private String[] _parameters;
	
	/**
	 * Intialize the tests
	 */
	@Before
	public void setUp() throws Exception {
		_parameters = new String[] {"param1", "param2"};
		_number = 100;
		_test = false;
		_call = new CSCall(_number, _test, _parameters);
	}

	/**
	 * Tests if was correctly initialized.
	 * 
	 * @see org.testsuite.checksource.CSCall#CSCall(int, boolean, String...)
	 */
	@Test
	public void testCSCallIntBooleanString() {
		assertFalse(_call.isCallFromTestFile());
		assertEquals(_number, _call.getNumber());
		assertEquals(2, _call.parameterCount());
	}
	
	/**
	 * Test if was correctly initialized.
	 * 
	 * @see org.testsuite.checksource.CSCall#CSCall(int, boolean)
	 */
	@Test
	public void testCSCallIntBoolean() {
		_call = new CSCall(_number, _test);
		assertFalse(_call.isCallFromTestFile());
		assertEquals(_number, _call.getNumber());
		assertEquals(0, _call.parameterCount());
	}
	
	/**
	 * Tests if the line number is returned correctly.
	 * 
	 * @see org.testsuite.checksource.CSCall#getNumber()
	 */
	@Test
	public void testGetNumber() {
		assertEquals(_number, _call.getNumber());
	}
	
	/**
	 * Tests if the line number can be set correctly.
	 * 
	 * @see org.testsuite.checksource.CSCall#setNumber()
	 */
	@Test
	public void testSetNumber() {
		int number = 200;
		_call.setNumber(number);
		assertEquals(number, _call.getNumber());
	}
	
	/**
	 * Tests if the call from test is returned correctly.
	 * 
	 * @see org.testsuite.checksource.CSCall#isCallFromTestFile()
	 */
	@Test
	public void testIsCallFromTestFile() {
		assertFalse(_call.isCallFromTestFile());
	}
	
	/**
	 * Tests if the call from test can be set correctly.
	 * 
	 * @see org.testsuite.checksource.CSCall#setCallFromTestFile(boolean)
	 */
	@Test
	public void testSetCallFromTestFile() {
		_call.setCallFromTestFile(true);
		assertTrue(_call.isCallFromTestFile());
	}
	
	/**
	 * Tests if the count of parameter is returned correctly.
	 * 
	 * @see org.testsuite.checksource.CSCall#parameterCount()
	 */
	@Test
	public void testParamterCount() {
		assertEquals(2, _call.parameterCount());
	}
	
	/**
	 * Tests if the specified parameter is returned correctly.
	 * 
	 * @see org.testsuite.checksource.CSCall#getParameter(int)
	 */
	@Test
	public void testGetParameter() {
		assertEquals(_parameters[0], _call.getParameter(0));
	}
	
	/**
	 * Tests if the parameter can be added correctly.
	 * 
	 * @see org.testsuite.checksource.CSCall#setCallFromTestFile(boolean)
	 */
	@Test
	public void testAddParameter() {
		String test = "param3";
		_call.addParameter(test);
		assertEquals(3, _call.parameterCount());
		assertEquals(test, _call.getParameter(2));
	}
}
