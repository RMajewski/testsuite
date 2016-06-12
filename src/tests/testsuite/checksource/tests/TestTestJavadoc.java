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
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.testsuite.checksource.MessageColor;
import org.testsuite.checksource.SourceLine;
import org.testsuite.checksource.tests.TestJavadoc;
import org.testsuite.helper.HelperUsedColor;

/**
 * Tests the test class {@link org.testsuite.checksource.tests.TestJavadoc}.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestTestJavadoc {
	/**
	 * Saves the instance of TestJavadoc
	 */
	private TestJavadoc _test;

	/**
	 * Initialize the tests.
	 */
	@Before
	public void setUp() throws Exception {
		_test = new TestJavadoc();
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
	 * Test if Javadoc is correctly identified.
	 */
	@Test
	public void testTestClass() {
		List<SourceLine> list = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.isJavadoc()).thenReturn(true);
		when(line1.getLineNumber()).thenReturn(1);
		when(line1.getLine()).thenReturn("/**");
		list.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.isJavadoc()).thenReturn(true);
		when(line2.getLineNumber()).thenReturn(2);
		when(line2.getLine()).thenReturn(" * This is a test class.");
		list.add(line2);
		
		SourceLine line3 = mock(SourceLine.class);
		when(line3.isJavadoc()).thenReturn(true);
		when(line3.getLineNumber()).thenReturn(3);
		when(line3.getLine()).thenReturn(" */");
		list.add(line3);
		
		SourceLine line4 = mock(SourceLine.class);
		when(line4.isJavadoc()).thenReturn(false);
		when(line4.getLineNumber()).thenReturn(4);
		when(line4.getLine()).thenReturn("public class Test {");
		list.add(line4);
		
		assertTrue(_test.test(list));
		
		verify(line1, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line1).isJavadoc();
		verify(line1).getLineNumber();
		verify(line1, never()).getLine();
		
		verify(line2, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line2).isJavadoc();
		verify(line2).getLineNumber();
		verify(line2, never()).getLine();

		verify(line3, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line3).isJavadoc();
		verify(line3).getLineNumber();
		verify(line3, never()).getLine();
		
		verify(line4, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line4).isJavadoc();
		verify(line4).getLineNumber();
		verify(line4, times(2)).getLine();
	}

	/**
	 * Test if Javadoc is correctly identified.
	 */
	@Test
	public void testTestClassWithAnnotation() {
		List<SourceLine> list = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.isJavadoc()).thenReturn(true);
		when(line1.getLineNumber()).thenReturn(1);
		when(line1.getLine()).thenReturn("/**");
		list.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.isJavadoc()).thenReturn(true);
		when(line2.getLineNumber()).thenReturn(2);
		when(line2.getLine()).thenReturn(" * This is a test class.");
		list.add(line2);
		
		SourceLine line3 = mock(SourceLine.class);
		when(line3.isJavadoc()).thenReturn(true);
		when(line3.getLineNumber()).thenReturn(3);
		when(line3.getLine()).thenReturn(" */");
		list.add(line3);
		
		SourceLine line4 = mock(SourceLine.class);
		when(line4.isJavadoc()).thenReturn(false);
		when(line4.getLineNumber()).thenReturn(4);
		when(line4.getLine()).thenReturn("@Test");
		list.add(line4);
		
		SourceLine line5 = mock(SourceLine.class);
		when(line5.isJavadoc()).thenReturn(false);
		when(line5.getLineNumber()).thenReturn(5);
		when(line5.getLine()).thenReturn("@Test2(list={})");
		list.add(line5);
		
		SourceLine line6 = mock(SourceLine.class);
		when(line6.isJavadoc()).thenReturn(false);
		when(line6.getLineNumber()).thenReturn(6);
		when(line6.getLine()).thenReturn("public class Test {");
		list.add(line6);
		
		assertTrue(_test.test(list));
		
		verify(line1, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line1).isJavadoc();
		verify(line1).getLineNumber();
		verify(line1, never()).getLine();
		
		verify(line2, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line2).isJavadoc();
		verify(line2).getLineNumber();
		verify(line2, never()).getLine();

		verify(line3, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line3).isJavadoc();
		verify(line3).getLineNumber();
		verify(line3, never()).getLine();
		
		verify(line4, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line4).isJavadoc();
		verify(line4, times(2)).getLineNumber();
		verify(line4).getLine();
		
		verify(line5, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line5).isJavadoc();
		verify(line5, times(2)).getLineNumber();
		verify(line5).getLine();
		
		verify(line6, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line6).isJavadoc();
		verify(line6).getLineNumber();
		verify(line6, times(2)).getLine();
	}

	/**
	 * Tests if an error message is generated when no Javadoc is available.
	 */
	@Test
	public void testTestClassWithoutJavadoc() {
		List<SourceLine> list = new ArrayList<SourceLine>();
		
		SourceLine line1 = new SourceLine();
		line1.setLineNumber(1);
		line1.setLine("public class Test {");
		list.add(line1);
		
		assertTrue(_test.test(list));
		
		assertEquals(1, line1.messageCount());
		assertEquals(ResourceBundle.getBundle(TestJavadoc.BUNDLE_FILE)
				.getString("emptyJavadocClass"), 
				line1.getMessage(0).getMessage());
		assertEquals(HelperUsedColor.WARNING, line1.getMessage(0).getColor());
	}

	/**
	 * Test if Javadoc is correctly identified.
	 */
	@Test
	public void testTestField() {
		List<SourceLine> list = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.isJavadoc()).thenReturn(true);
		when(line1.getLineNumber()).thenReturn(1);
		when(line1.getLine()).thenReturn("/**");
		list.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.isJavadoc()).thenReturn(true);
		when(line2.getLineNumber()).thenReturn(2);
		when(line2.getLine()).thenReturn(" * This is a test field.");
		list.add(line2);
		
		SourceLine line3 = mock(SourceLine.class);
		when(line3.isJavadoc()).thenReturn(true);
		when(line3.getLineNumber()).thenReturn(3);
		when(line3.getLine()).thenReturn(" */");
		list.add(line3);
		
		SourceLine line4 = mock(SourceLine.class);
		when(line4.isJavadoc()).thenReturn(false);
		when(line4.getLineNumber()).thenReturn(4);
		when(line4.getLine()).thenReturn("private String _test;");
		list.add(line4);
		
		assertTrue(_test.test(list));
		
		verify(line1, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line1).isJavadoc();
		verify(line1).getLineNumber();
		verify(line1, never()).getLine();
		
		verify(line2, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line2).isJavadoc();
		verify(line2).getLineNumber();
		verify(line2, never()).getLine();

		verify(line3, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line3).isJavadoc();
		verify(line3).getLineNumber();
		verify(line3, never()).getLine();
		
		verify(line4, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line4).isJavadoc();
		verify(line4).getLineNumber();
		verify(line4, times(3)).getLine();
	}

	/**
	 * Test if Javadoc is correctly identified.
	 */
	@Test
	public void testTestFieldWithAnnotation() {
		List<SourceLine> list = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.isJavadoc()).thenReturn(true);
		when(line1.getLineNumber()).thenReturn(1);
		when(line1.getLine()).thenReturn("/**");
		list.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.isJavadoc()).thenReturn(true);
		when(line2.getLineNumber()).thenReturn(2);
		when(line2.getLine()).thenReturn(" * This is a test field.");
		list.add(line2);
		
		SourceLine line3 = mock(SourceLine.class);
		when(line3.isJavadoc()).thenReturn(true);
		when(line3.getLineNumber()).thenReturn(3);
		when(line3.getLine()).thenReturn(" */");
		list.add(line3);
		
		SourceLine line4 = mock(SourceLine.class);
		when(line4.isJavadoc()).thenReturn(false);
		when(line4.getLineNumber()).thenReturn(4);
		when(line4.getLine()).thenReturn("@Test");
		list.add(line4);
		
		SourceLine line5 = mock(SourceLine.class);
		when(line5.isJavadoc()).thenReturn(false);
		when(line5.getLineNumber()).thenReturn(5);
		when(line5.getLine()).thenReturn("@Test2(list={})");
		list.add(line5);
		
		SourceLine line6 = mock(SourceLine.class);
		when(line6.isJavadoc()).thenReturn(false);
		when(line6.getLineNumber()).thenReturn(6);
		when(line6.getLine()).thenReturn("private String _test;");
		list.add(line6);
		
		assertTrue(_test.test(list));
		
		verify(line1, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line1).isJavadoc();
		verify(line1).getLineNumber();
		verify(line1, never()).getLine();
		
		verify(line2, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line2).isJavadoc();
		verify(line2).getLineNumber();
		verify(line2, never()).getLine();

		verify(line3, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line3).isJavadoc();
		verify(line3).getLineNumber();
		verify(line3, never()).getLine();
		
		verify(line4, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line4).isJavadoc();
		verify(line4, times(2)).getLineNumber();
		verify(line4).getLine();
		
		verify(line5, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line5).isJavadoc();
		verify(line5, times(2)).getLineNumber();
		verify(line5).getLine();
		
		verify(line6, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line6).isJavadoc();
		verify(line6).getLineNumber();
		verify(line6, times(3)).getLine();
	}

	/**
	 * Tests if an error message is generated when no Javadoc is available.
	 */
	@Test
	public void testTestFieldWithoutJavadoc() {
		List<SourceLine> list = new ArrayList<SourceLine>();
		
		SourceLine line1 = new SourceLine();
		line1.setLineNumber(1);
		line1.setLine("private String _test;");
		list.add(line1);
		
		assertTrue(_test.test(list));
		
		assertEquals(1, line1.messageCount());
		assertEquals(ResourceBundle.getBundle(TestJavadoc.BUNDLE_FILE)
				.getString("emptyJavadocField"), 
				line1.getMessage(0).getMessage());
		assertEquals(HelperUsedColor.WARNING, line1.getMessage(0).getColor());
	}

	/**
	 * Test if Javadoc is correctly identified.
	 */
	@Test
	public void testTestConst() {
		List<SourceLine> list = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.isJavadoc()).thenReturn(true);
		when(line1.getLineNumber()).thenReturn(1);
		when(line1.getLine()).thenReturn("/**");
		list.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.isJavadoc()).thenReturn(true);
		when(line2.getLineNumber()).thenReturn(2);
		when(line2.getLine()).thenReturn(" * This is a test field.");
		list.add(line2);
		
		SourceLine line3 = mock(SourceLine.class);
		when(line3.isJavadoc()).thenReturn(true);
		when(line3.getLineNumber()).thenReturn(3);
		when(line3.getLine()).thenReturn(" */");
		list.add(line3);
		
		SourceLine line4 = mock(SourceLine.class);
		when(line4.isJavadoc()).thenReturn(false);
		when(line4.getLineNumber()).thenReturn(4);
		when(line4.getLine()).thenReturn("private static final String TEST " +
				"= \"test\";");
		list.add(line4);
		
		assertTrue(_test.test(list));
		
		verify(line1, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line1).isJavadoc();
		verify(line1).getLineNumber();
		verify(line1, never()).getLine();
		
		verify(line2, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line2).isJavadoc();
		verify(line2).getLineNumber();
		verify(line2, never()).getLine();

		verify(line3, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line3).isJavadoc();
		verify(line3).getLineNumber();
		verify(line3, never()).getLine();
		
		verify(line4, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line4).isJavadoc();
		verify(line4).getLineNumber();
		verify(line4, times(4)).getLine();
	}

	/**
	 * Test if Javadoc is correctly identified.
	 */
	@Test
	public void testTestConstWithAnnotation() {
		List<SourceLine> list = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.isJavadoc()).thenReturn(true);
		when(line1.getLineNumber()).thenReturn(1);
		when(line1.getLine()).thenReturn("/**");
		list.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.isJavadoc()).thenReturn(true);
		when(line2.getLineNumber()).thenReturn(2);
		when(line2.getLine()).thenReturn(" * This is a test field.");
		list.add(line2);
		
		SourceLine line3 = mock(SourceLine.class);
		when(line3.isJavadoc()).thenReturn(true);
		when(line3.getLineNumber()).thenReturn(3);
		when(line3.getLine()).thenReturn(" */");
		list.add(line3);
		
		SourceLine line4 = mock(SourceLine.class);
		when(line4.isJavadoc()).thenReturn(false);
		when(line4.getLineNumber()).thenReturn(4);
		when(line4.getLine()).thenReturn("@Test");
		list.add(line4);
		
		SourceLine line5 = mock(SourceLine.class);
		when(line5.isJavadoc()).thenReturn(false);
		when(line5.getLineNumber()).thenReturn(5);
		when(line5.getLine()).thenReturn("@Test2(list={})");
		list.add(line5);
		
		SourceLine line6 = mock(SourceLine.class);
		when(line6.isJavadoc()).thenReturn(false);
		when(line6.getLineNumber()).thenReturn(6);
		when(line6.getLine()).thenReturn("private static final String TEST " +
				"= \"test\";");
		list.add(line6);
		
		assertTrue(_test.test(list));
		
		verify(line1, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line1).isJavadoc();
		verify(line1).getLineNumber();
		verify(line1, never()).getLine();
		
		verify(line2, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line2).isJavadoc();
		verify(line2).getLineNumber();
		verify(line2, never()).getLine();

		verify(line3, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line3).isJavadoc();
		verify(line3).getLineNumber();
		verify(line3, never()).getLine();
		
		verify(line4, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line4).isJavadoc();
		verify(line4, times(2)).getLineNumber();
		verify(line4).getLine();
		
		verify(line5, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line5).isJavadoc();
		verify(line5, times(2)).getLineNumber();
		verify(line5).getLine();
		
		verify(line6, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line6).isJavadoc();
		verify(line6).getLineNumber();
		verify(line6, times(4)).getLine();
	}

	/**
	 * Tests if an error message is generated when no Javadoc is available.
	 */
	@Test
	public void testTestConstWithoutJavadoc() {
		List<SourceLine> list = new ArrayList<SourceLine>();
		
		SourceLine line1 = new SourceLine();
		line1.setLineNumber(1);
		line1.setLine("private static final String TEST = \"test\";");
		list.add(line1);
		
		assertTrue(_test.test(list));
		
		assertEquals(1, line1.messageCount());
		assertEquals(ResourceBundle.getBundle(TestJavadoc.BUNDLE_FILE)
				.getString("emptyJavadocConst"), 
				line1.getMessage(0).getMessage());
		assertEquals(HelperUsedColor.WARNING, line1.getMessage(0).getColor());
	}

	/**
	 * Test if Javadoc is correctly identified.
	 */
	@Test
	public void testTestMethod() {
		List<SourceLine> list = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.isJavadoc()).thenReturn(true);
		when(line1.getLineNumber()).thenReturn(1);
		when(line1.getLine()).thenReturn("/**");
		list.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.isJavadoc()).thenReturn(true);
		when(line2.getLineNumber()).thenReturn(2);
		when(line2.getLine()).thenReturn(" * This is a test field.");
		list.add(line2);
		
		SourceLine line3 = mock(SourceLine.class);
		when(line3.isJavadoc()).thenReturn(true);
		when(line3.getLineNumber()).thenReturn(3);
		when(line3.getLine()).thenReturn(" */");
		list.add(line3);
		
		SourceLine line4 = mock(SourceLine.class);
		when(line4.isJavadoc()).thenReturn(false);
		when(line4.getLineNumber()).thenReturn(4);
		when(line4.getLine()).thenReturn("protected String test(int test) {");
		list.add(line4);
		
		assertTrue(_test.test(list));
		
		verify(line1, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line1).isJavadoc();
		verify(line1).getLineNumber();
		verify(line1, never()).getLine();
		
		verify(line2, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line2).isJavadoc();
		verify(line2).getLineNumber();
		verify(line2, never()).getLine();

		verify(line3, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line3).isJavadoc();
		verify(line3).getLineNumber();
		verify(line3, never()).getLine();
		
		verify(line4, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line4).isJavadoc();
		verify(line4).getLineNumber();
		verify(line4, times(5)).getLine();
	}

	/**
	 * Test if Javadoc is correctly identified.
	 */
	@Test
	public void testTestMethodWithAnnotation() {
		List<SourceLine> list = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.isJavadoc()).thenReturn(true);
		when(line1.getLineNumber()).thenReturn(1);
		when(line1.getLine()).thenReturn("/**");
		list.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.isJavadoc()).thenReturn(true);
		when(line2.getLineNumber()).thenReturn(2);
		when(line2.getLine()).thenReturn(" * This is a test field.");
		list.add(line2);
		
		SourceLine line3 = mock(SourceLine.class);
		when(line3.isJavadoc()).thenReturn(true);
		when(line3.getLineNumber()).thenReturn(3);
		when(line3.getLine()).thenReturn(" */");
		list.add(line3);
		
		SourceLine line4 = mock(SourceLine.class);
		when(line4.isJavadoc()).thenReturn(false);
		when(line4.getLineNumber()).thenReturn(4);
		when(line4.getLine()).thenReturn("@Test");
		list.add(line4);
		
		SourceLine line5 = mock(SourceLine.class);
		when(line5.isJavadoc()).thenReturn(false);
		when(line5.getLineNumber()).thenReturn(5);
		when(line5.getLine()).thenReturn("@Test2(list={})");
		list.add(line5);
		
		SourceLine line6 = mock(SourceLine.class);
		when(line6.isJavadoc()).thenReturn(false);
		when(line6.getLineNumber()).thenReturn(6);
		when(line6.getLine()).thenReturn("protected String test(int test) {");
		list.add(line6);
		
		assertTrue(_test.test(list));
		
		verify(line1, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line1).isJavadoc();
		verify(line1).getLineNumber();
		verify(line1, never()).getLine();
		
		verify(line2, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line2).isJavadoc();
		verify(line2).getLineNumber();
		verify(line2, never()).getLine();

		verify(line3, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line3).isJavadoc();
		verify(line3).getLineNumber();
		verify(line3, never()).getLine();
		
		verify(line4, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line4).isJavadoc();
		verify(line4, times(2)).getLineNumber();
		verify(line4).getLine();
		
		verify(line5, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line5).isJavadoc();
		verify(line5, times(2)).getLineNumber();
		verify(line5).getLine();
		
		verify(line6, never()).addMessage(Matchers.any(MessageColor.class));
		verify(line6).isJavadoc();
		verify(line6).getLineNumber();
		verify(line6, times(5)).getLine();
	}

	/**
	 * Tests if an error message is generated when no Javadoc is available.
	 */
	@Test
	public void testTestMethodWithoutJavadoc() {
		List<SourceLine> list = new ArrayList<SourceLine>();
		
		SourceLine line1 = new SourceLine();
		line1.setLineNumber(1);
		line1.setLine("protected String test(int test) {");
		list.add(line1);
		
		assertTrue(_test.test(list));
		
		assertEquals(1, line1.messageCount());
		assertEquals(ResourceBundle.getBundle(TestJavadoc.BUNDLE_FILE)
				.getString("emptyJavadocMethod"), 
				line1.getMessage(0).getMessage());
		assertEquals(HelperUsedColor.WARNING, line1.getMessage(0).getColor());
	}

}
