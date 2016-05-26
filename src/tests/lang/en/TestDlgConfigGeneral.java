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
import org.testsuite.app.DlgConfigGeneral;

/**
 * Tests the English language file for the DlgConfigGeneral class on
 * completeness.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestDlgConfigGeneral extends TestDlgConfig {

	/**
	 * Initialize the tests
	 */
	@Override
	@Before
	public void setUp() throws Exception {
		_bundleFile = DlgConfigGeneral.BUNDLE_FILE;
		super.setUp();
	}

	/**
	 * Tests if have entries for the dialog components.
	 */
	@Override
	@Test
	public void testEntriesForComponents() {
		assertNotNull(_bundle.getString("label_library_path"));
		assertNotNull(_bundle.getString("label_source_path"));
		assertNotNull(_bundle.getString("label_result_path"));
		assertNotNull(_bundle.getString("label_html_out"));
		assertNotNull(_bundle.getString("label_max_duration"));
		assertNotNull(_bundle.getString("label_system_property"));
	}
	
	/**
	 * Tests if have all entries for the po-up menu items for the system
	 * properties.
	 */
	@Test
	public void testEntriesForPopupItemsForProperties() {
		assertNotNull(_bundle.getString("insert_property"));
		assertNotNull(_bundle.getString("insert_property_mnemonic"));
		assertNotNull(_bundle.getString("change_property"));
		assertNotNull(_bundle.getString("change_property_mnemonic"));
		assertNotNull(_bundle.getString("delete_property"));
		assertNotNull(_bundle.getString("delete_property_mnemonic"));
	}

	/**
	 * Tests if have all entries for dialogs for system properties.
	 */
	@Test
	public void testEntriesForDialogsForProperties() {
		assertNotNull(_bundle.getString("insert_property_title"));
		assertNotNull(_bundle.getString("insert_property_message"));
		assertNotNull(_bundle.getString("change_property_title"));
		assertNotNull(_bundle.getString("change_property_message"));
		assertNotNull(_bundle.getString("delete_property_title"));
		assertNotNull(_bundle.getString("delete_property_message"));
	}
}
