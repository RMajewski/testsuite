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

import org.testsuite.core.HtmlOut;
import org.testsuite.core.TestRunner;

/**
 * Implements the class TestRunner for testing purposes.
 * 
 * @author René Majewski
 * 
 * @version 0.1
 */
class TestRunnerImplementation extends TestRunner {
	public TestRunnerImplementation(String test) {
		super(test);
	}

	@Override
	public void run() {
	}

	@Override
	public void createHtml(HtmlOut html) {
	}
}
