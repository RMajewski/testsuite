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

package tests.lang.en;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class are derived from the all test classes for testing the language
 * files for the configuration dialogs.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public abstract class TestDlgConfig extends TestLang{
	
	/**
	 * Initialize the tests
	 */
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * Tests if have the dialog title
	 */
	@Test
	public void testDialogTitle() {
		assertNotNull(_bundle.getString("dialog_title"));
	}
	
	/**
	 * Tests if have the two standard buttons
	 */
	@Test
	public void testStandardButtons() {
		assertNotNull(_bundle.getString("button_accept"));
		assertNotNull(_bundle.getString("button_cancel"));
	}
	
	/**
	 * Tests if have entries for the dialog components.
	 */
	@Test
	public abstract void testEntriesForComponents();
}
