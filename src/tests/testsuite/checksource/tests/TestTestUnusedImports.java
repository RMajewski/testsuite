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

package tests.testsuite.checksource.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.testsuite.checksource.SourceLine;
import org.testsuite.checksource.tests.TestUnusedImports;
import org.testsuite.helper.HelperUsedColor;

/**
 * Test the test class
 * {@link org.testsuite.checksource.tests.TestUnusedImports}.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestTestUnusedImports {
	/**
	 * Save the instance of TestUnusedImports
	 */
	private TestUnusedImports _test;

	/**
	 * Initialize the tests 
	 */
	@Before
	public void setUp() throws Exception {
		_test = new TestUnusedImports();
	}

	/**
	 * Tests if unused Imports are found.
	 */
	@Test
	public void testTest() {
		List<SourceLine> list = new ArrayList<SourceLine>();
		
		SourceLine line1 = new SourceLine();
		line1.setLine("import org.testsuite.checksource.Html;");
		line1.setLineNumber(1);
		list.add(line1);
		
		SourceLine line2 = new SourceLine();
		line2.setLine("import org.testsuite.checksource.HtmlOut;");
		line2.setLineNumber(2);
		list.add(line2);
		
		SourceLine line3 = new SourceLine();
		line3.setLine("String bundle = Html.BUNDLE_FILE");
		line3.setLineNumber(3);
		list.add(line3);
		
		assertTrue(_test.test(list));
		
		assertEquals(0, line1.messageCount());

		assertEquals(1, line2.messageCount());
		assertEquals(ResourceBundle.getBundle(TestUnusedImports.BUNDLE_FILE)
				.getString("unusedImports"), line2.getMessage(0).getMessage());
		assertEquals(HelperUsedColor.WARNING, line2.getMessage(0).getColor());

		assertEquals(0, line3.messageCount());
	}

}
