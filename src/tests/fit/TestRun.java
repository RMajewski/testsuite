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

package tests.fit;

/**
 * Implements the test method for testing the run function of Fixture.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestRun {
	
	/**
	 * Saves whether the parameter has been read correctly. 
	 */
	private boolean _parameterRight;
	
	/**
	 * Initialize the test class
	 */
	public TestRun() {
		_parameterRight = false;
	}
	
	/**
	 * Returns if the parameter has been read correctly.
	 * 
	 * @return If the parameter has been read correctly?
	 */
	public boolean isParameterReadRight() {
		return _parameterRight;
	}

	/**
	 * Saves whether the parameter has been read correctly.
	 * 
	 * @param bln If the parameter has been read correctly?
	 */
	public void setParameterReadRight(boolean bln) {
		_parameterRight = true;
	}
}
