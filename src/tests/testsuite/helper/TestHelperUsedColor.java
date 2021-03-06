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

import org.junit.Test;
import org.testsuite.helper.HelperUsedColor;

/**
 * Tests the helper class {@link org.testsuite.helper.HelperUsedColor};
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestHelperUsedColor {
	/**
	 * Tests if the color of warnings has been specified correctly.
	 */
	@Test
	public void testWarning() {
		assertEquals(new Color(0xFFFFCF), HelperUsedColor.WARNING);
	}

	/**
	 * Tests if the color of errors has been specified correctly.
	 */
	@Test
	public void testError() {
		assertEquals(new Color(0xFFCFCF), HelperUsedColor.ERROR);
	}

	/**
	 * Tests if the color of passes has been specified correctly.
	 */
	@Test
	public void testPass() {
		assertEquals(new Color(0xCFFFCF), HelperUsedColor.PASS);
	}

}
