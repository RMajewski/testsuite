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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.testsuite.checksource.SourceLine;
import org.testsuite.checksource.tests.TestEmptyLines;
import org.testsuite.helper.HelperUsedColor;

/**
 * Test the test class
 * {@link org.testsuite.checksource.tests.TestEmptyLines}.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestTestEmptyLines {
	/**
	 * Saves the instance of TestEmptyLines
	 */
	private TestEmptyLines _test;
	
	/**
	 * Initialize the tests 
	 */
	@Before
	public void setUp() throws Exception {
		_test = new TestEmptyLines();
	}
	
	/**
	 * Tests if was correctly initialized.
	 */
	@Test
	public void testTestEmptyLines() throws Exception {
		Field field = TestEmptyLines.class.getDeclaredField("_bundle");
		field.setAccessible(true);
		assertEquals(ResourceBundle.getBundle(TestEmptyLines.BUNDLE_FILE),
				(ResourceBundle)field.get(_test));
	}

	/**
	 * Tests if the test was performed correctly.
	 */
	@Test
	public void testTest() {
		List<SourceLine> list = new ArrayList<SourceLine>();
		
		SourceLine line1 = new SourceLine();
		line1.setLine("Test");
		list.add(line1);
		
		SourceLine line2 = new SourceLine();
		line2.setLine(new String());
		list.add(line2);
		
		SourceLine line3 = new SourceLine();
		line3.setLine(new String());
		list.add(line3);
		
		SourceLine line4 = new SourceLine();
		line4.setLine("Test");
		list.add(line4);
		
		assertTrue(_test.test(list));

		assertEquals(0, line1.messageCount());
		
		assertEquals(1, line2.messageCount());
		assertEquals(ResourceBundle.getBundle(TestEmptyLines.BUNDLE_FILE)
				.getString("emptylines"), line2.getMessage(0).getMessage());
		assertEquals(HelperUsedColor.WARNING, line2.getMessage(0).getColor());
		
		assertEquals(1, line3.messageCount());
		assertEquals(ResourceBundle.getBundle(TestEmptyLines.BUNDLE_FILE)
				.getString("emptylines"), line3.getMessage(0).getMessage());
		assertEquals(HelperUsedColor.WARNING, line3.getMessage(0).getColor());
		
		assertEquals(0, line4.messageCount());
	}

}
