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

import org.junit.Test;
import org.testsuite.data.TestEvent;
import org.testsuite.data.TestEventListener;

/**
 * Tests the interface {@link org.testsuite.data.TestEventListener}
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestTestEventListener {

	/**
	 * Verifies that test event has the testExecuteCompleted method.
	 */
	@Test
	public void testHasEventTestExecutedCompleted() throws Exception {
		assertEquals("testExecutedCompleted", 
				TestEventListener.class.getDeclaredMethod(
						"testExecutedCompleted", TestEvent.class).getName());
	}

}
