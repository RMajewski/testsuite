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

import java.util.EventListener;

/**
 * Defines all events for the tests.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public interface TestEventListener extends EventListener {
	/**
	 * Called before a test started
	 * 
	 * @param te Data of this event.
	 */
	public void testSelectTest(TestSelectEvent tse);

	/**
	 * Called when a test is complete.
	 * 
	 * @param te Data of this event.
	 */
	public void testExecutedCompleted(TestEvent te);
	
	/**
	 * Called when all tests are complete.
	 * 
	 * @param te Data of this event.
	 */
	public void testEnd(TestEvent te);
}
