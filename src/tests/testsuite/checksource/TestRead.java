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

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.testsuite.checksource.Read;

/**
 * Tests the interface {@link org.testsuite.checksource.Read}.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestRead {

	/**
	 * Tests if there is an interface.
	 */
	@Test
	public void testIsInterface() {
		assertTrue(Read.class.isInterface());
	}
	
	/**
	 * Test if it has a method named read.
	 */
	@Test
	public void testHasAMethodRead() throws Exception {
		assertEquals("read", Read.class.getMethod("read", int.class, 
				String.class, List.class).getName());
	}

}
