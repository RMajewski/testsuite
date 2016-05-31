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
import org.testsuite.app.ValidationEventRenderer;

/**
 * Tests the English language file for the ValidationEventRenderer class on
 * completeness.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestValidationEventRenderer extends TestLang {

	/**
	 * Initialize the tests
	 */
	@Override
	@Before
	public void setUp() throws Exception {
		_bundleFile = ValidationEventRenderer.BUNDLE_FILE;
		super.setUp();
	}
	
	/**
	 * Tests if have all entries for general configuration
	 */
	@Test
	public void testHaveAllEntriesForGeneralConfiguration() {
		assertNotNull(_bundle.getString("configPathSrc"));
		assertNotNull(_bundle.getString("configNoTestRunner"));
		assertNotNull(_bundle.getString("configPathSrcNotExists"));
		assertNotNull(_bundle.getString("configPathResultNotExists"));
		assertNotNull(_bundle.getString("configPathLibNotExists"));
		assertNotNull(_bundle.getString("configClassPathNotExists"));
		assertNotNull(_bundle.getString("configJavascriptNotExists"));
		assertNotNull(_bundle.getString("configStylesheetNotExists"));
	}
	
	/**
	 * Tests if have all entries for test runner
	 */
	@Test
	public void testHaveAllEntriesForTestRunner() {
		assertNotNull(_bundle.getString("testRunnerDescription"));
		assertNotNull(_bundle.getString("testRunnerFileExtension"));
		assertNotNull(_bundle.getString("testRunnerLibraryNotExists"));
		assertNotNull(_bundle.getString("testRunnerClassPathNotExists"));
		assertNotNull(_bundle.getString("testRunnerNoTestSuite"));
	}
	
	/**
	 * Tests if have all entries for test suite
	 */
	@Test
	public void testHaveAllEntriesForTestSuite() {
		assertNotNull(_bundle.getString("testSuiteName"));
		assertNotNull(_bundle.getString("testSuitePackage"));
		assertNotNull(_bundle.getString("testSuitePackageNotExists"));
		assertNotNull(_bundle.getString("testSuiteNoTest"));
	}
	
	/**
	 * Tests if have all entries for test
	 */
	@Test
	public void testHaveAllEntriesForTest() {
		assertNotNull(_bundle.getString("testName"));
		assertNotNull(_bundle.getString("testNotExists"));
	}
	
	/**
	 * Tests if have all entries for errors.
	 */
	@Test
	public void testHaveAllEntriesForErrors() {
		assertNotNull(_bundle.getString("otherError"));
	}
}
