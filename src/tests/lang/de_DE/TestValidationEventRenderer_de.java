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

package tests.lang.de_DE;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import tests.lang.en.TestValidationEventRenderer;

/**
 * Tests the English language file for the ValidationEventRenderer class on
 * completeness.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestValidationEventRenderer_de extends TestValidationEventRenderer {

	/**
	 * Initialize the tests
	 */
	@Override
	@Before
	public void setUp() throws Exception {
		_locale = Locale.GERMANY;
		super.setUp();
	}

	/**
	 * Test if have the right locale
	 */
	@Override
	@Test
	public void testHaveRightLocale() {
		assertEquals(Locale.GERMANY, _bundle.getLocale());
	}
}
