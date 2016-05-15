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

package tests.testsuite.data;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.testsuite.data.TestEvent;

/**
 * Tests the class {@link org.testsuite.data.TextEvent}.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestTestEvent {
	/**
	 * Saves the name of the package.
	 */
	private String _package;
	
	/**
	 * Saves the name of the test.
	 */
	private String _name;
	
	/**
	 * Saves the id of the tests suite.
	 */
	private int _suiteId;
	
	/**
	 * Saves the id of the test.
	 */
	
	private int _testId;
	
	/**
	 * Saves the string of the result.
	 */
	private String _result;
	
	/**
	 * Saves the instance of the test event data.
	 */
	private TestEvent _event;

	/**
	 * Initialize the tests.
	 */
	@Before
	public void setUp() throws Exception {
		_package = "package";
		_name = "Test";
		_suiteId = 1;
		_testId = 19;
		_result = "ausgeführt";
		
		_event = new TestEvent(this, _package, _name, _suiteId, _testId, 
				_result);
	}

	/**
	 * Tests if the test name is returned correctly.
	 * 
	 * @see org.testsuite.data.TestEvent#getName()
	 */
	@Test
	public void testGetName() {
		assertEquals(_name, _event.getName());
	}

	/**
	 * Tests if the package name is returned correctly.
	 * 
	 * @see org.testsuite.data.TestEvent#getPackageName()
	 */
	@Test
	public void testGetPackageName() {
		assertEquals(_package, _event.getPackageName());
	}

	/**
	 * Tests if the test suite id is returned correctly.
	 * 
	 * @see org.testsuite.data.TestEvent#getSuiteId()
	 */
	@Test
	public void testGetSuiteId() {
		assertEquals(_suiteId, _event.getSuiteId());
	}

	/**
	 * Tests if the test id is returned correctly.
	 * 
	 * @see org.testsuite.data.TestEvent#getTestId()
	 */
	@Test
	public void testGetTestId() {
		assertEquals(_testId, _event.getTestId());
	}

	/**
	 * Tests if the tresult string is returned correctly.
	 * 
	 * @see org.testsuite.data.TestEvent#getResult()
	 */
	@Test
	public void testGetResult() {
		assertEquals(_result, _event.getResult());
	}

}
