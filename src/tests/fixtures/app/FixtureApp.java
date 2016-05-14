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

import fit.ActionFixture;
import tests.testsuite.app.TestApp;

/**
 * Implements the fixtures for testing the app.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class FixtureApp extends ActionFixture {
	/**
	 * Hold an instance of the tests.
	 */
	private TestApp _tests; 
	
	/**
	 * Initializes the fixture.
	 */
	public FixtureApp() {
		_tests = new TestApp();
	}
	
	/**
	 * Determines whether the tree is enabled.
	 */
	public boolean haveTree() {
		return _tests.isTreeEnabled();
	}

	/**
	 * Determines whether the text pane for html output is enabled.
	 */
	public boolean haveTextPaneForHtmlOut() {
		return _tests.isTextPaneEnabled();
	}
	

	/**
	 * Determines whether the text pane for html output is editable.
	 */
	public boolean isTextPaneForHtmlOutEditable() {
		return _tests.isTextPaneEditable();
	}
	
	/**
	 * Determines whether the progress bar is enabled.
	 */
	public boolean haveProgressBar() {
		return _tests.isProgressBarEnabled();
	}
	
	/**
	 * Determines whether the button for run tests is visible.
	 */
	public boolean haveButtonForRunTests() {
		return _tests.isRunButtonVisible();
	}
	
	/**
	 * Determines whether the button for run tests is enabled.
	 */
	public boolean isButtonForRunTestsEnable() {
		return _tests.isRunButtonEnabled();
	}
	
	/**
	 * Determines whether the button for cancel tests is visible.
	 */
	public boolean haveButtonForCancelTests() {
		return _tests.isRunButtonVisible();
	}
	
	/**
	 * Determines whether the button for cancel tests is visible.
	 */
	public boolean isButtonForCancelTestsEnable() {
		return _tests.isCancelButtonEnabled();
	}
	
	/**
	 * Determines whether the button for load configuration file is visible.
	 */
	public boolean haveButtonForLoadTheConfigurationFile() {
		return _tests.isLoadButtonVisible();
	}
	
	/**
	 * Determines whether the button for load configuration file is enabled.
	 */
	public boolean isButtonForLoadTheConfigurationFileEnable() {
		return _tests.isLoadButtonEnabled();
	}
	
	/**
	 * Determines whether the button for exit the app is visible.
	 */
	public boolean haveButtonForExitTheApp() {
		return _tests.isExitButtonVisible();
	}
	
	/**
	 * Determines whether the button for exit the App is enabled.
	 */
	public boolean isButtonForExitTheAppEnable() {
		return _tests.isExitButtonEnabled();
	}
	
	/**
	 * Determines whether the main window is visible.
	 */
	public boolean isVisible() {
		return _tests.isMainWindowVisible();
	}
	
	/**
	 * Click on the exit button.
	 */
	public void pushExitButton() {
		_tests.pushExitButton();
	}
}
