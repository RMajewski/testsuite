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

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.testsuite.checksource.CSConfig;

/**
 * Tests the class {@link org.testsuite.checksource.CSConfig}.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestCSConfig {

	/**
	 * Initialize the tests.
	 */
	@Before
	public void setUp() throws Exception {
		CSConfig.getInstance().clear();
	}

	/**
	 * Tests the method
	 * {@link org.testsuite.checksource.CSConfig#getInstance()} and tests the
	 * initialized the data correctly.
	 */
	@Test
	public void testGetInstance() {
		assertNotNull(CSConfig.getInstance());
		assertEquals(0, CSConfig.getInstance().testCount());
		assertEquals(new String(), 
				CSConfig.getInstance().getPathCheckSourceTests());
		assertFalse(CSConfig.getInstance().isListNoneTestedFiles());
		assertEquals(new String(), CSConfig.getInstance().getNoneListedPath());
		assertEquals(-1, CSConfig.getInstance().getLineWidth());
		assertEquals(-1, CSConfig.getInstance().getTabSpace());
		assertNull(CSConfig.getInstance().getParserName());
		assertEquals(20000, CSConfig.getInstance().getParserTimeout());
		assertFalse(CSConfig.getInstance().isParserParse());
	}

	/**
	 * Tests the method {@link org.testsuite.checksource.CSConfig#testCount()}.
	 */
	@Test
	public void testTestCount() {
		assertEquals(0, CSConfig.getInstance().testCount());
	}

	/**
	 * Tests the method
	 * {@link org.testsuite.checksource.CSConfig#getTestName(int)}.
	 */
	@Test
	public void testGetTestName() {
		String test = "Test";
		CSConfig.getInstance().addTestName(test);
		assertEquals(test, CSConfig.getInstance().getTestName(0));
	}

	/**
	 * Tests the method
	 * {@link org.testsuite.checksource.CSConfig#addTestName(String)}.
	 */
	@Test
	public void testAddTestName() {
		String test = "Test";
		assertEquals(0, CSConfig.getInstance().testCount());
		CSConfig.getInstance().addTestName(test);
		assertEquals(1, CSConfig.getInstance().testCount());
		assertEquals(test, CSConfig.getInstance().getTestName(0));
	}

	/**
	 * Tests the method
	 * {@link org.testsuite.checksource.CSConfig#getPathCheckSourceTests()}.
	 */
	@Test
	public void testGetPathCheckSourceTests() {
		assertEquals(new String(), 
				CSConfig.getInstance().getPathCheckSourceTests());
	}

	/**
	 * Tests the method
	 * {@link org.testsuite.checksource.CSConfig#setPathCheckSourceTests(String)}.
	 */
	@Test
	public void testSetPathCheckSourceTests() {
		String test = "test1/test2";
		CSConfig.getInstance().setPathCheckSourceTests(test);
		assertEquals(test, CSConfig.getInstance().getPathCheckSourceTests());
	}

	/**
	 * Tests the method
	 * {@link org.testsuite.checksource.CSConfig#isListNoneTestedFiles()}.
	 */
	@Test
	public void testIsListNoneTestedFiles() {
		assertFalse(CSConfig.getInstance().isListNoneTestedFiles());
	}

	/**
	 * Tests the method
	 * {@link org.testsuite.checksource.CSConfig#setNoneListedPath(String)}.
	 */
	@Test
	public void testSetListNoneTestedFiles() {
		CSConfig.getInstance().setListNoneTestedFiles(true);
		assertTrue(CSConfig.getInstance().isListNoneTestedFiles());
	}

	/**
	 * Tests the method
	 * {@link org.testsuite.checksource.CSConfig#getNoneListedPath()}.
	 */
	@Test
	public void testGetNoneListedPath() {
		assertEquals(new String(), CSConfig.getInstance().getNoneListedPath());
	}

	/**
	 * Tests the method
	 * {@link org.testsuite.checksource.CSConfig#setNoneListedPath(String)}.
	 */
	@Test
	public void testSetNoneListedPath() {
		String test = "Test";
		CSConfig.getInstance().setNoneListedPath(test);
		assertEquals(test, CSConfig.getInstance().getNoneListedPath());
	}

	/**
	 * Tests the method
	 * {@link org.testsuite.checksource.CSConfig#getLineWidth()}.
	 */
	@Test
	public void testGetLineWidth() {
		assertEquals(-1, CSConfig.getInstance().getLineWidth());
	}

	/**
	 * Tests the method
	 * {@link org.testsuite.checksource.CSConfig#setLineWidth(int)}.
	 */
	@Test
	public void testSetLineWidth() {
		int width = 90;
		CSConfig.getInstance().setLineWidth(width);
		assertEquals(width, CSConfig.getInstance().getLineWidth());
	}

	/**
	 * Tests the method
	 * {@link org.testsuite.checksource.CSConfig#getTabSpace()}.
	 */
	@Test
	public void testGetTabSpace() {
		assertEquals(-1, CSConfig.getInstance().getTabSpace());
	}

	/**
	 * Tests the method
	 * {@link org.testsuite.checksource.CSConfig#setTabSpace(int)}.
	 */
	@Test
	public void testSetTabSpace() {
		int tab = 50;
		CSConfig.getInstance().setTabSpace(tab);
		assertEquals(tab, CSConfig.getInstance().getTabSpace());
	}

	/**
	 * Tests the method
	 * {@link org.testsuite.checksource.CSConfig#getParserName()}.
	 */
	@Test
	public void testGetParserName() {
		assertNull(CSConfig.getInstance().getParserName());
	}

	/**
	 * Tests the method {@link org.testsuite.checksource.CSConfig#getParser()}.
	 */
	@Test
	public void testGetParserWithNullAsParserName() {
		assertNull(CSConfig.getInstance().getParser());
	}

	/**
	 * Tests the method {@link org.testsuite.checksource.CSConfig#getParser()}.
	 */
	@Test
	public void testGetParser() {
		String name = "org.testsuite.checksource.SimpleParser";
		CSConfig.getInstance().setParserName(name);
		assertNotNull(CSConfig.getInstance().getParser());
		assertEquals(name,
				CSConfig.getInstance().getParser().getClass().getName());
	}

	/**
	 * Tests the method
	 * {@link org.testsuite.checksource.CSConfig#setParserName(String)}.
	 */
	@Test
	public void testSetParserName() {
		String test = "Parser";
		CSConfig.getInstance().setParserName(test);
		assertEquals(test, CSConfig.getInstance().getParserName());
	}

	/**
	 * Tests the method
	 * {@link org.testsuite.checksource.CSConfig#getParserTimeout()}.
	 */
	@Test
	public void testGetParserTimeout() {
		assertEquals(20000, CSConfig.getInstance().getParserTimeout());
	}

	/**
	 * Tests the method
	 * {@link org.testsuite.checksource.CSConfig#setParserTimeout(long)}.
	 */
	@Test
	public void testSetParserTimeout() {
		long time = 5000;
		CSConfig.getInstance().setParserTimeout(time);
		assertEquals(time, CSConfig.getInstance().getParserTimeout());
	}

	/**
	 * Tests the method
	 * {@link org.testsuite.checksource.CSConfig#isParserParse()}.
	 */
	@Test
	public void testIsParserParse() {
		assertFalse(CSConfig.getInstance().isParserParse());
	}

	/**
	 * Tests the method
	 * {@link org.testsuite.checksource.CSConfig#setParserParse(boolean)}.
	 */
	@Test
	public void testSetParserParse() {
		CSConfig.getInstance().setParserParse(true);
		assertTrue(CSConfig.getInstance().isParserParse());
	}

}
