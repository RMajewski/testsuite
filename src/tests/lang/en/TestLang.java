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

/**
 * Test class are derived from the all test classes for testing the language
 * files.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestLang {

	/**
	 * Saves the instance of resource bundle.
	 */
	protected ResourceBundle _bundle;
	
	/**
	 * Saves the name of the resource bundle file.
	 */
	protected String _bundleFile;
	
	/**
	 * Saves the locale
	 */
	protected Locale _locale;

	/**
	 * Initialize the tests.
	 */
	@Before
	public void setUp() throws Exception {
		if (_locale == null) {
			Locale.setDefault(Locale.ENGLISH);
			_locale = Locale.ENGLISH;
		}
		_bundle = ResourceBundle.getBundle(_bundleFile, _locale);
	}

	/**
	 * Tests if the right language.
	 */
	@Test
	public void testHaveRightLocale() {
		assertEquals(Locale.ENGLISH, _locale);
	}

}
