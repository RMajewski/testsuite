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
import org.testsuite.data.TestSelectEvent;

/**
 * Tests the class {@link org.testsuite.data.TestSelectEvent}.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestTestSelectEvent {
	/**
	 * Save the instance for TestSelectEvent
	 */
	private TestSelectEvent _event;
	
	/**
	 * Saves the index for the test runner
	 */
	private int _testRunner;
	
	/**
	 * Saves the index for the test suite
	 */
	private int _testSuite;
	
	/**
	 * Saves the index for the test
	 */
	private int _test;
	
	/**
	 * Initialize the tests 
	 */
	@Before
	public void setUp() throws Exception {
		_testRunner = 0;
		_testSuite = 1;
		_test = 2;
		_event = new TestSelectEvent(this, _testRunner, _testSuite, _test);
	}

	/**
	 * Tests if the index for the test runner is returned correctly.
	 */
	@Test
	public void testGetIndexTestRunner() {
		assertEquals(_testRunner, _event.getIndexTestRunner());
	}

	/**
	 * Tests if the index for the test suite is returned correctly.
	 */
	@Test
	public void testGetIndexTestSuite() {
		assertEquals(_testSuite, _event.getIndexTestSuite());
	}

	/**
	 * Tests if the index for the test is returned correctly.
	 */
	@Test
	public void testGetIndexTest() {
		assertEquals(_test, _event.getIndexTest());
	}

}
