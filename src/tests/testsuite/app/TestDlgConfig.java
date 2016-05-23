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

package tests.testsuite.app;

import static org.junit.Assert.*;

import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.testsuite.app.DlgConfig;
import org.testsuite.app.DlgConfigTest;

/**
 * Tests the class {@link org.testsuite.app.DlgConfig}
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestDlgConfig {
	/**
	 * Saves the instance of DlgConfig
	 */
	private DlgConfig _dlg;
	
	/**
	 * Saves the instance of resource bundle
	 */
	private ResourceBundle _bundle;

	/**
	 * Initialize the tests 
	 */
	@Before
	public void setUp() throws Exception {
		_dlg = new DlgConfig(null, DlgConfigTest.BUNDLE_FILE);
		_bundle = ResourceBundle.getBundle(DlgConfigTest.BUNDLE_FILE);
	}

	/**
	 * Verifies that the dialog title has been set correctly.
	 */
	@Test
	public void testDlgConfig() {
		assertEquals(_bundle.getString("dialog_title"), _dlg.getTitle());
	}

	/**
	 * Verifies that the dialog exit status has been set correctly.
	 */
	@Test
	public void testGetExitStatus() {
		assertEquals(-1, _dlg.getExitStatus());
	}
	
	/**
	 * Checks the constant EXIT_ACCEPT correctness to her.
	 */
	@Test
	public void testExitAcceptHasRightNumber() {
		assertEquals(1, DlgConfig.EXIT_ACCEPT);
	}
	
	/**
	 * Checks the constant EXIT_CANCEL correctness to her.
	 */
	@Test
	public void testExitCancelHasRightNumber() {
		assertEquals(2, DlgConfig.EXIT_CANCEL);
	}

}
