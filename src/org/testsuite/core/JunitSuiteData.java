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

/**
 * Saves the data for a junit test suite.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class JunitSuiteData extends TestSuiteData {

	/**
	 * Initialized data.
	 */
	public JunitSuiteData() {
		super();
	}
	
	/**
	 * Adds the specified Fit Test the list of tests added.
	 * 
	 * @param test Fit-Test, which is to be added to the list.
	 */
	public void addTest(JunitData test) {
		super.addTest(test);
	}
	
	/**
	 * Returns the specified index on Fit Test.
	 * 
	 * @param index Point at which the fit test is to be returned.
	 * 
	 * @return Determined Fit Test
	 */
	@Override
	public JunitData getTest(int index) {
		return (JunitData) super.getTest(index);
	}

}
