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
import org.testsuite.core.TestRunner;

/**
 * Tests the English language file for the TestRunner class on completeness.
 * 
 * @author René Majewski
 *
 */
public class TestTestRunner extends TestLang {

	/**
	 * Initialize the tests
	 */
	@Override
	@Before
	public void setUp() throws Exception {
		_bundleFile = TestRunner.BUNDLE_FILE;
		super.setUp();
	}

	/**
	 * Tests if have all entries for lists
	 */
	@Test
	public void testHaveAllEntriesForLists() {
		assertNotNull(_bundle.getString("createHtmlListOfLibraries_libraries"));
		assertNotNull(_bundle.getString("createHtmlListOfNonExistsTests_tests"));
	}
	
	/**
	 * Tests if have all entries for create column
	 */
	@Test
	public void testHaveAllEntriesForHtmlColumn() {
		assertNotNull(_bundle.getString("createHtmlColumn_terminated"));
		assertNotNull(_bundle.getString("createHtmlColumn_noneExistingTest"));
		assertNotNull(_bundle.getString("createHtmlColumn_noneExecuted"));
		assertNotNull(_bundle.getString("createHtmlColumn_yes"));
		assertNotNull(_bundle.getString("createHtmlColumn_no"));
	}

	/**
	 * Tests if have all entries for run
	 */
	@Test
	public void testHaveAllEntriesForRun() {
		assertNotNull(_bundle.getString("run_notFound"));
		assertNotNull(_bundle.getString("run_pass"));
		assertNotNull(_bundle.getString("run_failure"));
		assertNotNull(_bundle.getString("run_terminated"));
		assertNotNull(_bundle.getString("run_duration"));
	}
	
	/**
	 * Tests if have all entries for table head
	 */
	@Test
	public void testHaveAllEntriesForTableHead() {
		assertNotNull(_bundle.getString("createHtmlTableHead_ok"));
		assertNotNull(_bundle.getString("createHtmlTableHead_wrong"));
		assertNotNull(_bundle.getString("createHtmlTableHead_ignore"));
		assertNotNull(_bundle.getString("createHtmlTableHead_time"));
	}
	
	/**
	 * Tests if have all entries for results
	 */
	@Test
	public void testHaveAllEntriesForResults() {
		assertNotNull(_bundle.getString("test_runner_result_name"));
		assertNotNull(_bundle.getString("test_runner_result_tests"));
		assertNotNull(_bundle.getString("test_runner_result_tests_executed"));
		assertNotNull(_bundle.getString("test_runner_result_tests_terminated"));
		assertNotNull(_bundle.getString("test_runner_result_tests_ignored"));
		assertNotNull(_bundle.getString("test_runner_result_duration"));
		assertNotNull(_bundle.getString("test_runner_result_all"));
		assertNotNull(_bundle.getString("test_runner_result_right"));
		assertNotNull(_bundle.getString("test_runner_result_wrong"));
		assertNotNull(_bundle.getString("test_runner_result_ignore"));
		assertNotNull(_bundle.getString("test_runner_result_exception"));
		assertNotNull(_bundle.getString("test_runner_result_tests_not_exists"));
	}
	
	/**
	 * Tests if have all entries for check source
	 */
	@Test
	public void testHaveAllEntriesForCheckSource() {
		assertNotNull(_bundle.getString("result_checksoure"));
		assertNotNull(_bundle.getString("checksource_no_source"));
		assertNotNull(_bundle.getString("checksource_no_test"));
	}
}
