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

package tests.testsuite.data;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.testsuite.data.Config;

/**
 * Tests the class {@link org.testsuite.data.Config}.
 * 
 * @author René Majewski
 * 
 * @versionb 0.1
 */
public class TestConfig {
	/**
	 * Save the class TestData.
	 */
	private Config _config;

	/**
	 * Initialize the tests
	 */
	@Before
	public void setUp() throws Exception {
		_config = new Config();
	}

	/**
	 * Tests whether the directory for the source code files is returned
	 * correctly.
	 */
	@Test
	public void testGetPathSrc() {
		assertEquals(new String(), _config.getPathSrc());
	}

	/**
	 * Tests whether the directory for the source files is correctly set.
	 */
	@Test
	public void testSetPathSrc() {
		String test = "Testet";
		_config.setPathSrc(test);
		assertEquals(test, _config.getPathSrc());
	}
	
	/**
	 * Tests whether the error IllegalArgumentException appears when zero is
	 * passed as a parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetPathSrcWithNullAsParameter() {
		_config.setPathSrc(null);
	}
	
	/**
	 * Tests whether the error IllegalArgumentException appears when empty 
	 * string is passed as a parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetPathSrcWithEmptyStringAsParameter() {
		_config.setPathSrc(new String());
	}

	/**
	 * Tests whether the directory for the result is returned correctly.
	 */
	@Test
	public void testGetPathResult() {
		assertEquals(new String(), _config.getPathResult());
	}

	/**
	 * Tests whether the directory for the result is correctly set.
	 */
	@Test
	public void testSetPathResult() {
		String test = "Testet";
		_config.setPathResult(test);
		assertEquals(test, _config.getPathResult());
	}
	
	/**
	 * Tests whether the error IllegalArgumentException appears when zero is
	 * passed as a parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetPathResultWithNullAsParameter() {
		_config.setPathResult(null);
	}
	
	/**
	 * Tests whether the error IllegalArgumentException appears when empty 
	 * string is passed as a parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetPathResultWithEmptyStringAsParameter() {
		_config.setPathResult(new String());
	}

	/**
	 * Tests whether the directory for the fit result files is returned
	 * correctly.
	 */
	@Test
	public void testGetPathSuitesResult() {
		assertEquals(new String(), _config.getPathSuitesResult());
	}

	/**
	 * Tests whether the directory for the fit result files is correctly set.
	 */
	@Test
	public void testSetPathSuitesResult() {
		String test = "Testet";
		_config.setPathSuitesResult(test);
		assertEquals(test, _config.getPathSuitesResult());
	}
	
	/**
	 * Tests whether the error IllegalArgumentException appears when zero is
	 * passed as a parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetPathSuitesResultWithNullAsParameter() {
		_config.setPathSuitesResult(null);
	}
	
	/**
	 * Tests whether the error IllegalArgumentException appears when empty 
	 * string is passed as a parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetPathSuitesResultWithEmptyStringAsParameter() {
		_config.setPathSuitesResult(new String());
	}

	/**
	 * Tests whether the directory for the libraries is returned correctly.
	 */
	@Test
	public void testGetPathLibraries() {
		assertEquals(new String(), _config.getPathLibrary());
	}

	/**
	 * Tests whether the directory for the libraries is correctly set.
	 */
	@Test
	public void testSetPathLibraries() {
		String test = "Testet";
		_config.setPathLibrary(test);
		assertEquals(test, _config.getPathLibrary());
	}
	
	/**
	 * Tests whether the error IllegalArgumentException appears when zero is
	 * passed as a parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetPathLibraryWithNullAsParameter() {
		_config.setPathLibrary(null);
	}
	
	/**
	 * Tests whether the error IllegalArgumentException appears when empty 
	 * string is passed as a parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetPathLibraryWithEmptyStringAsParameter() {
		_config.setPathLibrary(new String());
	}

	/**
	 * Tests whether the classpath is returned correctly.
	 * 
	 * @deprecated
	 */
	@Test
	public void testGetClassPath() {
		assertEquals(new String(), _config.getClasspath());
	}

	/**
	 * Tests whether the classpath is correctly set.
	 * 
	 * @deprecated
	 */
	@Test
	public void testSetClassPath() {
		String test = "Testet";
		_config.setClasspath(test);
		assertEquals(test, _config.getClasspath());
	}
	
	/**
	 * Tests whether the error IllegalArgumentException appears when zero is
	 * passed as a parameter.
	 * 
	 * @deprecated
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetClasspathWithNullAsParameter() {
		_config.setClasspath(null);
	}
	
	/**
	 * Tests whether the error IllegalArgumentException appears when empty 
	 * string is passed as a parameter.
	 * 
	 * @deprecated
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetClasspathhWithEmptyStringAsParameter() {
		_config.setClasspath(new String());
	}
	
	/**
	 * Tests if returned correctly if the HTML file to be created or not.
	 */
	@Test
	public void testIsCreateHtml() {
		assertFalse(_config.isCreateHtml());
	}
	
	/**
	 * Tests if can be set correctly if the HTML file to be created or not.
	 */
	@Test
	public void testSetCreateHtml() {
		_config.setCreateHtml(true);
		assertTrue(_config.isCreateHtml());
	}
	/**
	 * Verifies that the correct number of libraries is returned.
	 */
	@Test
	public void testProperyCount() {
		assertEquals(0, _config.propertyCount());
	}
	
	/**
	 * Checks whether a library can be added to the list.
	 */
	@Test
	public void testAddPropery() {
		String name = "test";
		_config.addProperty(name);
		assertEquals(1, _config.propertyCount());
	}
	
	/**
	 * Tests whether the error IllegalArgumentException appears when zero is
	 * passed as a parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddPropertyWithNullAsParameter() {
		_config.addProperty(null);
		assertEquals(0, _config.propertyCount());
	}
	
	/**
	 * Tests whether the error IllegalArgumentException appears when empty 
	 * string is passed as a parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddPropertyhWithEmptyStringAsParameter() {
		_config.addProperty(new String());
		assertEquals(0, _config.propertyCount());
	}
	
	/**
	 * Tests if the property is returned correctly.
	 */
	@Test
	public void testGetProperty() {
		String name = "test";
		_config.addProperty(name);
		assertEquals(name, _config.getProperty(0));
	}
	
	/**
	 * Tests if the maximum test duration is returned correctly.
	 */
	@Test
	public void testGetMaxDuration() {
		assertEquals(0, _config.getMaxDuration());
	}
	
	/**
	 * Tests if the maximum test duration can be set correctly.
	 */
	@Test
	public void testSetMaxDuration() {
		long duration = 100l;
		_config.setMaxDuration(duration);
		assertEquals(duration, _config.getMaxDuration());
	}
}
