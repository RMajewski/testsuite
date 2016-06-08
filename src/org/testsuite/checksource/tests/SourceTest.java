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

package org.testsuite.checksource.tests;

import java.awt.Color;
import java.util.List;

import org.testsuite.checksource.SourceLine;

/**
 * Defines the testing methods.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public interface SourceTest {
	/**
	 * Is called to perform the test.
	 * 
	 * @param list The list of source lines.
	 * 
	 * @return True, if the test was successful. False if an error has occurred
	 * in the line.
	 */
	public boolean test(List<SourceLine> list);
	
	/**
	 * Specified the background color for a warning.
	 */
	Color COLOR_WARNING = new Color(0xFFFFCF);
	
	/**
	 * Specified the background color for a exception
	 */
	Color COLOR_EXCEPTION = new Color(0xFFCFCF);
	
	/**
	 * Saves the file name for resource bundle.
	 */
	String BUNDLE_FILE = "resources.lang.org.testsuite.checksource.tests.Tests";
	
	/**
	 * Saves the list with all tests for check source.
	 */
	Class<?>[] TESTS = {TestEmptyLines.class, TestEmptyMethod.class, 
			TestUnusedImports.class};
}
