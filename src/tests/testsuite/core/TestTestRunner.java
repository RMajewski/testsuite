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
import org.testsuite.core.HtmlOut;
import org.testsuite.core.TestCore;
import org.testsuite.core.TestRunner;

/**
 * Tests the class {@link org.testsuite.core.TestRunner}
 * 
 * @author René Majewski
 * 
 * @version 0.1
 */
public class TestTestRunner {

	/**
	 * Hold an instance of the TestRunner
	 */
	private TestRunnerImplementation _runner;
	
	
	@Before
	public void setUp() throws Exception {
		_runner = new TestRunnerImplementation();
	}
	
	

}
