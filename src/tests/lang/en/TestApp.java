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

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.testsuite.app.App;

/**
 * Tests the English language files for the app class on completeness.
 * 
 * @author René Majewski
 *
 */
public class TestApp {

	/**
	 * Saves the instance of the resource bundle
	 */
	protected ResourceBundle _bundle;
	
	/**
	 * Saves the used locale
	 */
	protected Locale _locale;

	/**
	 * Initalize the tests 
	 */
	@Before
	public void setUp() throws Exception {
		if (_locale == null)
			_locale = Locale.ENGLISH;
		_bundle = ResourceBundle.getBundle(App.BUNDLE_FILE, _locale);
	}
	
	/**
	 * Tests if the right language.
	 */
	@Test
	public void testHaveRightLocale() {
		assertEquals(Locale.ENGLISH, _locale);
	}
	
	/**
	 * If all entries for the buttons?
	 */
	@Test
	public void testEntriesForButtons() {
		assertNotNull(_bundle.getString("btnRun"));
		assertNotNull(_bundle.getString("btnCancel"));
		assertNotNull(_bundle.getString("btnConfigLoad"));
		assertNotNull(_bundle.getString("btnConfigValidate"));
		assertNotNull(_bundle.getString("btnConfigSave"));
		assertNotNull(_bundle.getString("btnExit"));
		assertNotNull(_bundle.getString("html_result"));
		assertNotNull(_bundle.getString("btnAllTestsIgnore"));
		assertNotNull(_bundle.getString("btnAllTestsExecute"));
	}

	/**
	 * If all entries for the configuration file open and save?
	 */
	@Test
	public void testEntriesForSaveFileDialogs() {
		assertNotNull(_bundle.getString("configFileOpenTitle"));
		assertNotNull(_bundle.getString("configFileSaveTitle"));
		assertNotNull(_bundle.getString("configFileFilter"));
		
		assertNotNull(_bundle.getString("fileExistsMessage"));
		assertNotNull(_bundle.getString("fileExistsTitle"));
	}
	
	/**
	 * If all entries for the tree?
	 */
	@Test
	public void testEntriesForTree() {
		assertNotNull(_bundle.getString("tree_null_node"));
		assertNotNull(_bundle.getString("tree_insert_test_runner"));
		assertNotNull(_bundle.getString("tree_configuration"));
		assertNotNull(_bundle.getString("tree_no_test_suite_name"));
		assertNotNull(_bundle.getString("tree_no_test_name"));
	}
	
	/**
	 * If all entries for the pop-up menu names?
	 */
	@Test
	public void testEntriesForTreePopupMenuNames() {
		assertNotNull(_bundle.getString("popup_tree_menu_insert"));
		assertNotNull(_bundle.getString("popup_tree_menu_insert_mnemonic"));
		assertNotNull(_bundle.getString("popup_tree_menu_delete"));
		assertNotNull(_bundle.getString("popup_tree_menu_delete_mnemonic"));
		assertNotNull(_bundle.getString("popup_tree_menu_config"));
		assertNotNull(_bundle.getString("popup_tree_menu_config_mnemonic"));
	}
	
	/**
	 * If all entries for the pop-up menu items?
	 */
	@Test
	public void testEntriesForTreePopupMenuItems() {
		assertNotNull(_bundle.getString("popup_tree_config"));
		assertNotNull(_bundle.getString("popup_tree_config_mnemonic"));
		assertNotNull(_bundle.getString("popup_tree_testrunner"));
		assertNotNull(_bundle.getString("popup_tree_testrunner_mnemonic"));
		assertNotNull(_bundle.getString("popup_tree_testsuite"));
		assertNotNull(_bundle.getString("popup_tree_testsuite_mnemonic"));
		assertNotNull(_bundle.getString("popup_tree_test"));
		assertNotNull(_bundle.getString("popup_tree_test_mnemonic"));
	}
	
	/**
	 * If all entries for the pop-up delete dialogs?
	 */
	@Test
	public void testEntriesForDeleteDialogs() {
		assertNotNull(_bundle.getString("delete_config_general_title"));
		assertNotNull(_bundle.getString("delete_config_general_message"));
		assertNotNull(_bundle.getString("delete_test_runner_title"));
		assertNotNull(_bundle.getString("delete_test_runner_message"));
		assertNotNull(_bundle.getString("delete_test_suite_title"));
		assertNotNull(_bundle.getString("delete_test_suite_message"));
		assertNotNull(_bundle.getString("delete_test_title"));
		assertNotNull(_bundle.getString("delete_test_message"));
	}
}
