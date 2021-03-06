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

package tests.fixtures.app;

/**
 * Implements the fixtures for testing the button "All tests executed".
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class FixtureAppAllTestsExecuted extends FixtureApp {
	/**
	 * Initialize the fixture
	 */
	public FixtureAppAllTestsExecuted() {
		super();
	}
	
	/**
	 * Determines whether the button for all tests select execute is visible.
	 */
	public boolean haveButtonForTestsAllExecute() {
		return _tests.isAllTestsExecuteButtonVisible();
	}
	
	/**
	 * Click an the button "All tests execute" 
	 */
	public void pushButtonExecuteAllTests() {
		_tests.pushButtonExecuteAllTests();
	}

	/**
	 * Insert a TestRunner in the tree
	 */
	public void insertTestRunner() {
		_tests.selectElementInTree(0);
		_tests.openTreePopup();
		_tests.pushTreePopupItem(0, 1);
	}

}
