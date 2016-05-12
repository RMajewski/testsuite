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

package org.testsuite.core;

import java.io.FileNotFoundException;

import javax.xml.stream.XMLStreamException;

/**
 * Lets run the tests.
 * 
 * For this, the test core class is initialized and read the configuration file.
 * Thereafter, it is checked whether all tests are available and run the tests.
 * HTML output is still generated the end.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestsRun {
	/**
	 * Run the tests.
	 * 
	 * @param config The name of configuration file.
	 */
	public static void run(String config) {
		TestCore core = new TestCore();
		try {
			if (core.readConfig(config)) {
				core.checkFileExists();
				core.run();
				core.createResultHtml();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
