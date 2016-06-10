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

package tests.testsuite.helper;

import static org.junit.Assert.*;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.HashMap;

import org.junit.Test;
import org.testsuite.checksource.annotation.CheckSource;
import org.testsuite.helper.HelperHtmlCodeJava;

/**
 * Tests the helper class {@link org.testsuite.helper.HelperHtmlCodeJava}.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestHelperHtmlCodeJava {
	/**
	 * Tests if the color
	 * {@link org.testsuite.helper.HelperHtmlCodeJava#COLOR_COMMENTS} is set
	 * correctly.
	 */
	@Test
	public void testColorComments() {
		assertEquals(new Color(0x3f7f5f), HelperHtmlCodeJava.COLOR_COMMENTS);
	}
	
	/**
	 * Tests if the color
	 * {@link org.testsuite.helper.HelperHtmlCodeJava#COLOR_KEYWORDS} is set
	 * correctly.
	 */
	@Test
	public void testColorJavadoc() {
		assertEquals(new Color(0x3f5fbf), HelperHtmlCodeJava.COLOR_JAVADOC);
	}
	
	/**
	 * Tests if the color
	 * {@link org.testsuite.helper.HelperHtmlCodeJava#COLOR_KEYWORDS} is set
	 * correctly.
	 */
	@Test
	public void testColorKeywords() {
		assertEquals(new Color(0x7f007e), HelperHtmlCodeJava.COLOR_KEYWORDS);
	}
	
	/**
	 * Tests whether the list of keywords was correctly initialized.
	 */
	@CheckSource(method="HelperHtmlCodeJava")
	@Test
	public void testKeywords() throws Exception {
		Field field = HelperHtmlCodeJava.class.getDeclaredField("_keywords");
		field.setAccessible(true);
		
		@SuppressWarnings("unchecked")
		HashMap<String, Color> keywords = 
			(HashMap<String, Color>)field.get(HelperHtmlCodeJava.getInstance());
		
		assertEquals(53, keywords.size());
		
		assertTrue(keywords.containsKey("abstract"));
		assertTrue(keywords.containsKey("assert"));
		assertTrue(keywords.containsKey("assert"));
		assertTrue(keywords.containsKey("break"));
		assertTrue(keywords.containsKey("break"));
		assertTrue(keywords.containsKey("case"));
		assertTrue(keywords.containsKey("catch"));
		assertTrue(keywords.containsKey("char"));
		assertTrue(keywords.containsKey("class"));
		assertTrue(keywords.containsKey("const"));
		assertTrue(keywords.containsKey("continue"));
		assertTrue(keywords.containsKey("default"));
		assertTrue(keywords.containsKey("do"));
		assertTrue(keywords.containsKey("double"));
		assertTrue(keywords.containsKey("else"));
		assertTrue(keywords.containsKey("enum"));
		assertTrue(keywords.containsKey("extends"));
		assertTrue(keywords.containsKey("final"));
		assertTrue(keywords.containsKey("final"));
		assertTrue(keywords.containsKey("float"));
		assertTrue(keywords.containsKey("for"));
		assertTrue(keywords.containsKey("goto"));
		assertTrue(keywords.containsKey("if"));
		assertTrue(keywords.containsKey("implements"));
		assertTrue(keywords.containsKey("import"));
		assertTrue(keywords.containsKey("instanceof"));
		assertTrue(keywords.containsKey("int"));
		assertTrue(keywords.containsKey("interface"));
		assertTrue(keywords.containsKey("long"));
		assertTrue(keywords.containsKey("native"));
		assertTrue(keywords.containsKey("new"));
		assertTrue(keywords.containsKey("package"));
		assertTrue(keywords.containsKey("private"));
		assertTrue(keywords.containsKey("protected"));
		assertTrue(keywords.containsKey("public"));
		assertTrue(keywords.containsKey("return"));
		assertTrue(keywords.containsKey("short"));
		assertTrue(keywords.containsKey("static"));
		assertTrue(keywords.containsKey("strictfp"));
		assertTrue(keywords.containsKey("super"));
		assertTrue(keywords.containsKey("switch"));
		assertTrue(keywords.containsKey("synchronized"));
		assertTrue(keywords.containsKey("this"));
		assertTrue(keywords.containsKey("throw"));
		assertTrue(keywords.containsKey("throws"));
		assertTrue(keywords.containsKey("transient"));
		assertTrue(keywords.containsKey("try"));
		assertTrue(keywords.containsKey("void"));
		assertTrue(keywords.containsKey("volatile"));
		assertTrue(keywords.containsKey("while"));
		assertTrue(keywords.containsKey("false"));
		assertTrue(keywords.containsKey("null"));
		assertTrue(keywords.containsKey("true"));
	}

	/**
	 * Tests if an empty string is returned.
	 */
	@Test
	public void testFormatStringWithNullAsSource() {
		assertEquals(new String(),
				HelperHtmlCodeJava.formatString(null, false, false));
	}

	/**
	 * Tests if an empty string is returned.
	 */
	@Test
	public void testFormatStringWithEmptyStringAsSource() {
		assertEquals(new String(),
				HelperHtmlCodeJava.formatString(new String(), false, false));
	}
	
	/**
	 * Tests if the string is returned correctly formatted HTML.
	 */
	@CheckSource(method="formatColor")
	@Test
	public void testFormatStringWithMultilineComment() {
		String test = "Dies ist ein Test";
		assertEquals("<span style=\"color: " + 
				HelperHtmlCodeJava.getInstance().formatColor(
						HelperHtmlCodeJava.COLOR_COMMENTS) + ";\">" + test + 
				"</span>", HelperHtmlCodeJava.formatString(test, true, false));
	}
	
	/**
	 * Tests if the string is returned correctly formatted HTML.
	 */
	@CheckSource(method="formatSpan")
	@Test
	public void testFormatStringWithJavadoc() {
		String test = "Dies ist ein Test";
		assertEquals("<span style=\"color: " + 
				HelperHtmlCodeJava.getInstance().formatColor(
						HelperHtmlCodeJava.COLOR_JAVADOC) + ";\">" + test + 
				"</span>", HelperHtmlCodeJava.formatString(test, false, true));
	}
	
	/**
	 * Tests if the string is returned correctly formatted HTML.
	 */
	@CheckSource(method="formatKeywords")
	@Test
	public void testFormatStringWithKeyWord() {
		String test = "class Test {";
		assertEquals("<span style=\"color: " + 
				HelperHtmlCodeJava.getInstance().formatColor(
						HelperHtmlCodeJava.COLOR_KEYWORDS) + 
				";\">class</span> Test {", 
				HelperHtmlCodeJava.formatString(test, false, false));
	}

	/**
	 * Tests if an instance has been created by
	 * {@link org.testsuite.helper.HelperHtmlCodeJava}.
	 */
	@Test
	public void testGetInstance() {
		assertNotNull(HelperHtmlCodeJava.getInstance());
	}

	/**
	 * Tests if the specified color is returned properly formatted for HTML.
	 */
	@Test
	public void testFormatColor() {
		assertEquals("#ffbbaa", 
				HelperHtmlCodeJava.getInstance().formatColor(
						new Color(0xFFBBAA)));
	}

}
