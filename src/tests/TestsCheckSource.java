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

package tests;

import java.io.File;

import org.testsuite.core.TestCore;
import org.testsuite.core.TestsRun;

/**
 * Run the tests for check source
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestsCheckSource {
	public static void run() {
		TestCore core = new TestCore();
		try {
			if (core.readConfig("src" + File.separator + "tests" + 
					File.separator + "test_checksource.xml")) {
				core.checkFileExists();
				core.run();
//				core.checkSource();
				core.createResultHtml();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		TestsCheckSource.run();
	}

}
