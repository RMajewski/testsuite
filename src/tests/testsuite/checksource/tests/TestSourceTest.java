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

import java.util.List;

import org.junit.Test;
import org.testsuite.checksource.tests.SourceTest;

/**
 * Tests for the interface {@link org.testsuite.checksource.test.SourceTest}.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestSourceTest {
	/**
	 * Tests if there is an interface.
	 */
	@Test
	public void testIfAnInterface() {
		assertTrue(SourceTest.class.isInterface());
	}

	/**
	 * Test if it has the method "test" defined.
	 */
	@Test
	public void testHaveTest() throws Exception {
		assertEquals("public abstract boolean org.testsuite.checksource." +
				"tests.SourceTest.test(java.util.List)", 
				SourceTest.class.getMethod("test", List.class).toString());
	}

	/**
	 * Tests if the bundle file is specified correctly.
	 */
	@Test
	public void testBundleFile() {
		assertEquals("resources.lang.org.testsuite.checksource.tests.Tests",
				SourceTest.BUNDLE_FILE);
	}

	/**
	 * Tests if all the tests specified.
	 */
	@Test
	public void testTests() {
		assertEquals(3, SourceTest.TESTS.length);
		assertEquals("org.testsuite.checksource.tests.TestEmptyLines",
				SourceTest.TESTS[0].getName());
		assertEquals("org.testsuite.checksource.tests.TestEmptyMethod",
				SourceTest.TESTS[1].getName());
		assertEquals("org.testsuite.checksource.tests.TestUnusedImports",
				SourceTest.TESTS[2].getName());
	}

}
