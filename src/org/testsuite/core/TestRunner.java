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

import java.util.ArrayList;
import java.util.List;

import org.testsuite.data.TestSuite;

/**
 * From this class all TestRunner be derived.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public abstract class TestRunner {
	/**
	 * Hold an instance of the list of TestSuites.
	 */
	protected List<TestSuite> _suits;
	
	/**
	 * Initialis the data of the class.
	 */
	public TestRunner() {
		_suits = new ArrayList<TestSuite>();
	}
	
	/**
	 * Verifies that the directories and files Test exist.
	 */
	public void checkFileExists() {
		
	}
	
	/**
	 * Called to start the stored tests.
	 */
	public abstract void run();
	
	/**
	 * Called to generate the HTML code from the test results.
	 * 
	 * @param html Class, which helps to generate the HTML code.
	 */
	public abstract void createHtml(HtmlOut html);
}
