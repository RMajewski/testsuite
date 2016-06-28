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

package tests.testsuite.checksource;

import static org.junit.Assert.*;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.testsuite.checksource.html.Html;

/**
 * Tests for class {@link org.testsuite.checksource.html.Html}.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestHtml {
	/**
	 * Saves the instance of HTML
	 */
	private Html _html;
	
	/**
	 * Saves the name of result file
	 */
	private String _resultFile;

	/**
	 * Initialize the tests 
	 */
	@Before
	public void setUp() throws Exception {
		_resultFile = "test.html";
		_html = new Html(_resultFile);
	}
	
	@Test
	public void testHtml() throws Exception {
		Field field = Html.class.getDeclaredField("_resultFile");
		field.setAccessible(true);
		assertEquals(_resultFile, field.get(_html));
		
		field = Html.class.getDeclaredField("_bundle");
		field.setAccessible(true);
		assertEquals(ResourceBundle.getBundle(Html.BUNDLE_FILE), 
				field.get(_html));
	}

	/**
	 * Tests if the bundle file is declared correctly.
	 */
	@Test
	public void testBundleFile() {
		assertEquals("resources.lang.org.testsuite.checksource.HtmlOut", 
				Html.BUNDLE_FILE);
	}
	
	/**
	 * Tests if the color has been correctly declared for success.
	 * 
	 * @deprecated
	 */
	@Test
	public void testColorPass() {
		assertEquals(new Color(0xCFFFCF), Html.COLOR_PASS);
	}
}
