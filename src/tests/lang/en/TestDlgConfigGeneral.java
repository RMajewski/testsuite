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
		assertNotNull(_bundle.getString("label_system_classpath"));
		assertNotNull(_bundle.getString("label_javascript"));
		assertNotNull(_bundle.getString("label_stylesheet"));
	}
	
	/**
	 * Tests if have all entries for the pop-up menu items for the system
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
	
	/**
	 * Tests if have all entries for the pop-up menu items for the class paths.
	 */
	@Test
	public void testEntriesForPopupItemsForClassPaths() {
		assertNotNull(_bundle.getString("insert_classpath"));
		assertNotNull(_bundle.getString("insert_classpath_mnemonic"));
		assertNotNull(_bundle.getString("change_classpath"));
		assertNotNull(_bundle.getString("change_classpath_mnemonic"));
		assertNotNull(_bundle.getString("delete_classpath"));
		assertNotNull(_bundle.getString("delete_classpath_mnemonic"));
	}
	
	/**
	 * Tests if have all entries for dialogs for class paths.
	 */
	@Test
	public void testEntriesForDialogsForClasspaths() {
		assertNotNull(_bundle.getString("insert_classpath_title"));
		assertNotNull(_bundle.getString("insert_classpath_message"));
		assertNotNull(_bundle.getString("change_classpath_title"));
		assertNotNull(_bundle.getString("change_classpath_message"));
		assertNotNull(_bundle.getString("delete_classpath_title"));
		assertNotNull(_bundle.getString("delete_classpath_message"));
		
	}
	
	/**
	 * Tests if have all entries for the pop-up menu items for the java script
	 * files.
	 */
	@Test
	public void testEntriesForPopupItemsForJavascriptFiles() {
		assertNotNull(_bundle.getString("insert_javascript"));
		assertNotNull(_bundle.getString("insert_javascript_mnemonic"));
		assertNotNull(_bundle.getString("change_javascript"));
		assertNotNull(_bundle.getString("change_javascript_mnemonic"));
		assertNotNull(_bundle.getString("delete_javascript"));
		assertNotNull(_bundle.getString("delete_javascript_mnemonic"));
	}
	
	/**
	 * Tests if have all entries for dialogs for java script files.
	 */
	@Test
	public void testEntriesForDialogsForJavascriptFiles() {
		assertNotNull(_bundle.getString("insert_javascript_title"));
		assertNotNull(_bundle.getString("insert_javascript_message"));
		assertNotNull(_bundle.getString("change_javascript_title"));
		assertNotNull(_bundle.getString("change_javascript_message"));
		assertNotNull(_bundle.getString("delete_javascript_title"));
		assertNotNull(_bundle.getString("delete_javascript_message"));
		
	}
	
	/**
	 * Tests if have all entries for the pop-up menu items for the style sheet
	 * files.
	 */
	@Test
	public void testEntriesForPopupItemsForStylesheetFiles() {
		assertNotNull(_bundle.getString("insert_stylesheet"));
		assertNotNull(_bundle.getString("insert_stylesheet_mnemonic"));
		assertNotNull(_bundle.getString("change_stylesheet"));
		assertNotNull(_bundle.getString("change_stylesheet_mnemonic"));
		assertNotNull(_bundle.getString("delete_stylesheet"));
		assertNotNull(_bundle.getString("delete_stylesheet_mnemonic"));
	}
	
	/**
	 * Tests if have all entries for dialogs for style sheet files.
	 */
	@Test
	public void testEntriesForDialogsForStylesheetFiles() {
		assertNotNull(_bundle.getString("insert_stylesheet_title"));
		assertNotNull(_bundle.getString("insert_stylesheet_message"));
		assertNotNull(_bundle.getString("change_stylesheet_title"));
		assertNotNull(_bundle.getString("change_stylesheet_message"));
		assertNotNull(_bundle.getString("delete_stylesheet_title"));
		assertNotNull(_bundle.getString("delete_stylesheet_message"));
		
	}
}
