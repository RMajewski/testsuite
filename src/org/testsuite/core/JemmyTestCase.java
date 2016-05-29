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
import java.io.IOException;

import org.netbeans.jemmy.Scenario;
import org.netbeans.jemmy.Timeouts;

/**
 * Implemented a test case for Jemmy where the constructor, the file is passed
 * with Jemmy settings.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public abstract class JemmyTestCase implements Scenario {
	/**
	 * Loads the file with Jemmy settings.
	 * 
	 * If no file is specified, it is used as a standard file
	 * "tests/jemmy.properties". If the given file does not exist, so no file
	 * is loaded.
	 * 
	 * @param fileName Name of the file with settings.
	 */
	public JemmyTestCase(String fileName) {
		if ((fileName == null) || fileName.isEmpty())
			fileName = "tests/jemmy.properties";
		Timeouts timeouts = new Timeouts();
		try {
			timeouts.loadDefaults(JemmyTestCase.class.getClassLoader()
					.getResourceAsStream(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new JemmyTestCase(null) {
			@Override
			public int runIt(Object arg0) {
				return 0;
			}
			
		};
	}
}
