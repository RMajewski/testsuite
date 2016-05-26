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
import org.testsuite.app.DlgConfigTestRunner;

/**
 * Tests the English language file for the DlgConfigTestRunner class on
 * completeness.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestDlgConfigTestRunner extends TestDlgConfig {

	/**
	 * Initialize the tests
	 */
	@Override
	@Before
	public void setUp() throws Exception {
		_bundleFile = DlgConfigTestRunner.BUNDLE_FILE;
		super.setUp();
	}
	
	/**
	 * Tests if have all entries for the dialog components.
	 */
	@Override
	@Test
	public void testEntriesForComponents() {
		assertNotNull(_bundle.getString("label_class_name"));
		assertNotNull(_bundle.getString("label_file_extension"));
		assertNotNull(_bundle.getString("label_description"));
		assertNotNull(_bundle.getString("label_library"));
		assertNotNull(_bundle.getString("label_classpath"));
	}
	
	/**
	 * Tests if have alle entries for the pop-up menu items for the classpath.
	 */
	@Test
	public void testEntriesForPopupMenuForClasspath() {
		assertNotNull(_bundle.getString("insert_classpath"));
		assertNotNull(_bundle.getString("insert_classpath_mnemonic"));
		assertNotNull(_bundle.getString("delete_classpath"));
		assertNotNull(_bundle.getString("delete_classpath_mnemonic"));
	}

	/**
	 * Tests if have all entries for the pop-up menu items for the libraries.
	 */
	@Test
	public void testEntriesForPopupMenuForLibraries() {
		assertNotNull(_bundle.getString("insert_library"));
		assertNotNull(_bundle.getString("insert_library_mnemonic"));
		assertNotNull(_bundle.getString("change_library"));
		assertNotNull(_bundle.getString("change_library_mnemonic"));
		assertNotNull(_bundle.getString("delete_library"));
		assertNotNull(_bundle.getString("delete_library_mnemonic"));
	}
	
	/**
	 * Tests if have all entries for the insert dialog for classpath
	 */
	@Test
	public void testEntriesForInsertDialogOfClasspath() {
		assertNotNull(_bundle.getString("insert_classpath_title"));
		assertNotNull(_bundle.getString("insert_classpath_message"));
	}
	
	/**
	 * Tests if have all entries for the delete dialogs
	 */
	@Test
	public void testEntriesForDeleteDialogs() {
		assertNotNull(_bundle.getString("delete_classpath_title"));
		assertNotNull(_bundle.getString("delete_classpath_message"));
		assertNotNull(_bundle.getString("delete_library_title"));
		assertNotNull(_bundle.getString("delete_library_message"));
	}
}
