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

package org.testsuite.app;

import java.util.List;

import org.testsuite.core.TestRunner;
import org.testsuite.data.TestEventListener;

/**
 * Thread in which the tests are run.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestRun implements Runnable {
	/**
	 * Saves the list of classes TestRunner;
	 */
	private List<TestRunner> _testRunner;
	
	/**
	 * Saves the TestEventListener
	 */
	private TestEventListener _listener;
	
	/**
	 * Initialize the thread.
	 * 
	 * @param testRunner List of TestRunners.
	 * 
	 * @param listener TestEventListener
	 */
	public TestRun(List<TestRunner> testRunner, TestEventListener listener) {
		_testRunner = testRunner;
		_listener = listener;
	}

	/**
	 * Runs the tests.
	 */
	@Override
	public void run() {
		for (int i = 0; i < _testRunner.size(); i++) {
			_testRunner.get(i).addTestEventListener(_listener);
			_testRunner.get(i).run();
		}
	}

}
