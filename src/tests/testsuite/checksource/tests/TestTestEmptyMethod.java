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
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.testsuite.checksource.SourceLine;
import org.testsuite.checksource.tests.TestEmptyMethod;
import org.testsuite.checksource.tests.TestJavadoc;
import org.testsuite.helper.HelperUsedColor;

/**
 * Test the test class
 * {@link org.testsuite.checksource.tests.TestEmptyMethod}.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestTestEmptyMethod {
	/**
	 * Saves the instance of TestEmptyMethod.
	 */
	private TestEmptyMethod _test;

	/**
	 * Initialize the tests. 
	 */
	@Before
	public void setUp() throws Exception {
		_test = new TestEmptyMethod();
	}
	
	/**
	 * Tests if the class implements the interface SourceTest.
	 */
	@Test
	public void testImplementsSourceTest() {
		assertEquals("[interface org.testsuite.checksource.tests.SourceTest]",
				Arrays.toString(TestJavadoc.class.getInterfaces()));
	}

	/**
	 * Tests if was correctly initialized.
	 */
	@Test
	public void testTestEmptyMethod() throws Exception {
		Field field = TestEmptyMethod.class.getDeclaredField("_bundle");
		field.setAccessible(true);
		assertEquals(ResourceBundle.getBundle(TestEmptyMethod.BUNDLE_FILE),
				(ResourceBundle)field.get(_test));
	}

	/**
	 * Tests if the empty method was detected.
	 */
	@Test
	public void testTestOneLine() {
		List<SourceLine> list = new ArrayList<SourceLine>();
		
		SourceLine line1 = new SourceLine();
		line1.setLine("public static final void getLine() { }");
		list.add(line1);
		
		SourceLine line2 = new SourceLine();
		line2.setLine("Test");
		list.add(line2);
		
		assertTrue(_test.test(list));
		
		assertEquals(1, line1.messageCount());
		assertEquals(ResourceBundle.getBundle(TestEmptyMethod.BUNDLE_FILE)
				.getString("emptyMethod"), line1.getMessage(0).getMessage());
		assertEquals(HelperUsedColor.WARNING, line1.getMessage(0).getColor());
		
		assertEquals(0, line2.messageCount());
	}

	/**
	 * Tests if the empty method was detected.
	 */
	@Test
	public void testTestTwoLines() {
		List<SourceLine> list = new ArrayList<SourceLine>();
		
		SourceLine line1 = new SourceLine();
		line1.setLine("public static final void getLine(String test) {");
		list.add(line1);
		
		SourceLine line2 = new SourceLine();
		line2.setLine("}");
		list.add(line2);
		
		assertTrue(_test.test(list));
		
		assertEquals(1, line1.messageCount());
		assertEquals(ResourceBundle.getBundle(TestEmptyMethod.BUNDLE_FILE)
				.getString("emptyMethod"), line1.getMessage(0).getMessage());
		assertEquals(HelperUsedColor.WARNING, line1.getMessage(0).getColor());
		
		assertEquals(1, line2.messageCount());
		assertEquals(ResourceBundle.getBundle(TestEmptyMethod.BUNDLE_FILE)
				.getString("emptyMethod"), line2.getMessage(0).getMessage());
		assertEquals(HelperUsedColor.WARNING, line2.getMessage(0).getColor());
	}

	/**
	 * Tests if the empty method was detected.
	 */
	@Test
	public void testTestThreeLines() {
		List<SourceLine> list = new ArrayList<SourceLine>();
		
		SourceLine line1 = new SourceLine();
		line1.setLine("public static final void getLine(String test) {");
		list.add(line1);
		
		SourceLine line2 = new SourceLine();
		line2.setLine("// Test");
		list.add(line2);
		
		SourceLine line3 = new SourceLine();
		line3.setLine("}");
		list.add(line3);
		
		assertTrue(_test.test(list));
		
		assertEquals(1, line1.messageCount());
		assertEquals(ResourceBundle.getBundle(TestEmptyMethod.BUNDLE_FILE)
				.getString("emptyMethod"), line1.getMessage(0).getMessage());
		assertEquals(HelperUsedColor.WARNING, line1.getMessage(0).getColor());
		
		assertEquals(1, line2.messageCount());
		assertEquals(ResourceBundle.getBundle(TestEmptyMethod.BUNDLE_FILE)
				.getString("emptyMethod"), line2.getMessage(0).getMessage());
		assertEquals(HelperUsedColor.WARNING, line2.getMessage(0).getColor());
		
		assertEquals(1, line3.messageCount());
		assertEquals(ResourceBundle.getBundle(TestEmptyMethod.BUNDLE_FILE)
				.getString("emptyMethod"), line3.getMessage(0).getMessage());
		assertEquals(HelperUsedColor.WARNING, line3.getMessage(0).getColor());
	}

}
