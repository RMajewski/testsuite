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
import org.testsuite.app.DlgConfigTest;

/**
 * Tests the English language file for the DlgConfigtest class on
 * completeness.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestDlgConfigTest extends TestDlgConfig {

	/**
	 * Initialize the tests
	 */
	@Override
	@Before
	public void setUp() throws Exception {
		_bundleFile = DlgConfigTest.BUNDLE_FILE;
		super.setUp();
	}

	/**
	 * Tests if have entries for the dialog components.
	 */
	@Override
	@Test
	public void testEntriesForComponents() {
		assertNotNull(_bundle.getString("label_name"));
		assertNotNull(_bundle.getString("label_execute"));
		assertNotNull(_bundle.getString("label_jvm"));
		assertNotNull(_bundle.getString("label_checkSource"));
	}

}
