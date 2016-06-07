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
import org.testsuite.checksource.HtmlOut;

/**
 * Tests the English language file for the HtmlOut class on completeness.
 * 
 * @author René Majewski
 *
 */
public class TestChecksourceHtmlOut extends TestLang {

	/**
	 * Initialize the tests
	 */
	@Override
	@Before
	public void setUp() throws Exception {
		_bundleFile = HtmlOut.BUNDLE_FILE;
		super.setUp();
	}

	/**
	 * Tests if have all entries for the HTML head
	 */
	@Test
	public void testHaveAllEntriesForHtmlHead() {
		assertNotNull(_bundle.getString("htmlHead_head"));
		assertNotNull(_bundle.getString("htmlHead_description"));
	}

	/**
	 * Tests if have all entries for the HTML lists
	 */
	@Test
	public void testHaveAllEntriesForLists() {
		assertNotNull(_bundle.getString("methods_calls"));
		assertNotNull(_bundle.getString("methods_without_calls"));
	}
}
