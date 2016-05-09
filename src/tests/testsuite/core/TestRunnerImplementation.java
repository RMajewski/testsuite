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

import org.testsuite.core.TestRunner;
import org.testsuite.data.Config;

/**
 * Implements the class TestRunner for testing purposes.
 * 
 * @author René Majewski
 * 
 * @version 0.1
 */
class TestRunnerImplementation extends TestRunner {
	public TestRunnerImplementation(String test, Config config) {
		super(test, config);
	}

	@Override
	public void run() {
	}

	@Override
	protected String createHtmlTableHead(int suite) {
		return new String();
	}

	@Override
	protected String createHtmlColumn(int suite, int test) {
		return new String();
	}
}
