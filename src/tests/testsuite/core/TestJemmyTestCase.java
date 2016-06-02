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

package tests.testsuite.core;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.netbeans.jemmy.Timeouts;
import org.testsuite.core.JemmyTestCase;

/**
 * Tests the class {@link org.testsuite.core.JemmyTestCase}.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestJemmyTestCase {
	/**
	 * Saves the instance of JemmyTestCase
	 */
	private JemmyTestCase _test;

	/**
	 * Initialize the tests
	 */
	@Before
	public void setUp() throws Exception {
		_test = new JemmyTestCase(null){

			@Override
			public int runIt(Object arg0) {
				return 0;
			}
			
		};
	}

	/**
	 * Tests if the default time for WaitFrameTimeout was correctly initialized.
	 */
	@Test
	public void testWaitFrameTimeout() {
		assertEquals(20000,
				Timeouts.getDefault("FrameWaiter.WaitFrameTimeout"));
	}

	/**
	 * Tests if the default time for WaitDialogTimeout was correctly initialized.
	 */
	@Test
	public void testWaitDialogTimeout() {
		assertEquals(20000,
				Timeouts.getDefault("DialogWaiter.WaitDialogTimeout"));
	}

	/**
	 * Tests if the default time for WaitingTime was correctly initialized.
	 */
	@Test
	public void testWaitingTime() {
		assertEquals(20000, Timeouts.getDefault("Waiter.WaitingTime"));
	}

	/**
	 * Tests if the default time for WaitWindowTimeout was correctly initialized.
	 */
	@Test
	public void testWaitWindowTimeout() {
		assertEquals(20000, 
				Timeouts.getDefault("WindowWaiter.WaitWindowTimeout"));
	}

	/**
	 * Tests if the default time for WaitComponentTimeout was correctly initialized.
	 */
	@Test
	public void testWaitComponentTimeout() {
		assertEquals(20000, 
				Timeouts.getDefault("ComponentOperator.WaitComponentTimeout"));
	}

}
