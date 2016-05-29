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

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.testsuite.data.Config;

/**
 * Tests the class {@link org.testsuite.data.Config}.
 * 
 * @author René Majewski
 * 
 * @version 0.1
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
	 * Tests if no fault is triggered when an empty string is passed as a
	 * parameter.
	 */
	@Test
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
	 * Tests if no fault is triggered when an empty string is passed as a
	 * parameter.
	 */
	@Test
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
	 * Tests if no fault is triggered when an empty string is passed as a
	 * parameter.
	 */
	@Test
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
	 * Verifies that the correct number of class path is returned.
	 */
	@Test
	public void testClasspathCount() {
		assertEquals(0, _config.classPathCount());
	}
	
	/**
	 * Tests whether the error IllegalArgumentException appears when zero is
	 * passed as a parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddClasspathWithNullAsParameter() {
		_config.addClassPath(null);
	}
	
	/**
	 * Tests whether the error IllegalArgumentException appears when empty 
	 * string is passed as a parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddClasspathWithEptyStringAsParameter() {
		_config.addClassPath(new String());
	}
	
	/**
	 * Checks whether a class path can be added to the list.
	 */
	@Test
	public void testAddClasspath() {
		String name = "test";
		_config.addClassPath(name);
		assertEquals(1, _config.classPathCount());
	}
	
	/**
	 * Tests if the class path is returned correctly.
	 */
	@Test
	public void testGetClasspath() {
		String name = "test";
		_config.addClassPath(name);
		assertEquals(name, _config.getClassPath(0));
	}
	
	/**
	 * Tests if the class paths is returned correctly for the class path
	 * parameter from JVM. 
	 */
	@Test
	public void testClassPathsAsParameterJVM() {
		String cp1 = "classpath1";
		String cp2 = "classpath2";
		String ret = cp1 + File.pathSeparator + cp2;
		_config.addClassPath(cp1);
		_config.addClassPath(cp2);
		assertEquals(ret, _config.classPathsAsParameterJVM());
	}
	
	/**
	 * Verifies that the correct system property was deleted.
	 */
	@Test
	public void testRemoveClasspath() {
		String prop1 = "Test1";
		String prop2 = "Test2";
		String prop3 = "Test3";
		
		_config.addClassPath(prop1);
		_config.addClassPath(prop2);
		_config.addClassPath(prop3);
		
		assertEquals(3, _config.classPathCount());
		
		_config.removeClassPath(prop2);
		
		assertEquals(2, _config.classPathCount());
		assertEquals(prop1, _config.getClassPath(0));
		assertEquals(prop3, _config.getClassPath(1));
	}
	
	/**
	 * Verifies that the correct system property has changed.
	 */
	@Test
	public void testChangeClasspath() {
		String prop1 = "Test1";
		String prop2 = "Test2";
		String prop3 = "Test3";
		
		_config.addClassPath(prop1);
		_config.addClassPath(prop2);
		_config.addClassPath(prop3);
		
		assertEquals(3, _config.classPathCount());
		
		_config.changeClassPath(prop2, "Test4");
		
		assertEquals(3, _config.classPathCount());
		assertEquals(prop1, _config.getClassPath(0));
		assertEquals("Test4", _config.getClassPath(1));
		assertEquals(prop3, _config.getClassPath(2));
	}
	
	/**
	 * Verifies that the correct number of libraries is returned.
	 */
	@Test
	public void testProperyCount() {
		assertEquals(0, _config.propertyCount());
	}
	
	/**
	 * Checks whether a property can be added to the list.
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
	 * Verifies that the correct system property was deleted.
	 */
	@Test
	public void testRemoveProperty() {
		String prop1 = "Test1";
		String prop2 = "Test2";
		String prop3 = "Test3";
		
		_config.addProperty(prop1);
		_config.addProperty(prop2);
		_config.addProperty(prop3);
		
		assertEquals(3, _config.propertyCount());
		
		_config.removeProperty(prop2);
		
		assertEquals(2, _config.propertyCount());
		assertEquals(prop1, _config.getProperty(0));
		assertEquals(prop3, _config.getProperty(1));
	}
	
	/**
	 * Verifies that the correct system property has changed.
	 */
	@Test
	public void testChangeProperty() {
		String prop1 = "Test1";
		String prop2 = "Test2";
		String prop3 = "Test3";
		
		_config.addProperty(prop1);
		_config.addProperty(prop2);
		_config.addProperty(prop3);
		
		assertEquals(3, _config.propertyCount());
		
		_config.changeProperty(prop2, "Test4");
		
		assertEquals(3, _config.propertyCount());
		assertEquals(prop1, _config.getProperty(0));
		assertEquals("Test4", _config.getProperty(1));
		assertEquals(prop3, _config.getProperty(2));
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
	
	/**
	 * Checks whether the configuration is empty.
	 */
	@Test
	public void testIsEmpty() {
		assertTrue(_config.isEmpty());
	}
	
	/**
	 * Checks whether the configuration is not empty, if the result path is set.
	 */
	@Test
	public void testIsNotEmptyResultPathIsSet() {
		_config.setPathResult("Test");
		assertFalse(_config.isEmpty());
	}
	
	/**
	 * Checks whether the configuration is not empty, if the src path is set.
	 */
	@Test
	public void testIsNotEmptySrcPathIsSet() {
		_config.setPathSrc("Test");
		assertFalse(_config.isEmpty());
	}
	
	/**
	 * Checks whether the configuration is not empty, if the library path is
	 * set.
	 */
	@Test
	public void testIsNotEmptyLibraryPathIsSet() {
		_config.setPathLibrary("Test");
		assertFalse(_config.isEmpty());
	}
	
	/**
	 * Checks whether the configuration is not empty, if the create html is true.
	 */
	@Test
	public void testIsNotEmptyCreateHtmlIsTrue() {
		_config.setCreateHtml(true);;
		assertFalse(_config.isEmpty());
	}
	
	/**
	 * Checks whether the configuration is not empty, if the max duration time
	 * is zero.
	 */
	@Test
	public void testIsNotEmptyMaxDurationTimeIsNotZero() {
		_config.setMaxDuration(100l);;
		assertFalse(_config.isEmpty());
	}
	
	/**
	 * Checks whether the configuration is not empty, if a system property is in
	 * the list of system properties.
	 */
	@Test
	public void testIsNotEmptyListOfPropertyHavOneProperty() {
		_config.addProperty("Test");
		assertFalse(_config.isEmpty());
	}
	
	/**
	 * Checks whether the configuration is not empty, if a class path is in
	 * the list of class paths.
	 */
	@Test
	public void testIsNotEmptyListOfClasspathHavOneProperty() {
		_config.addClassPath("Test");
		assertFalse(_config.isEmpty());
	}
	
	/**
	 * Tests whether the proper configuration entries have been reset.
	 */
	@Test
	public void testClear() {
		_config.setClasspath("Test");
		_config.addProperty("Test");
		_config.addClassPath("test");
		_config.setPathLibrary("Test");
		_config.setMaxDuration(100l);
		_config.setCreateHtml(true);
		_config.setPathResult("Test");
		_config.setPathSrc("Test");
		_config.setPathSuitesResult("Test");
		
		_config.clear();
		
		assertTrue(_config.getPathLibrary().isEmpty());
		assertTrue(_config.getPathResult().isEmpty());
		assertTrue(_config.getPathSrc().isEmpty());
		assertEquals(0, _config.propertyCount());
		assertEquals(0, _config.getMaxDuration());
		assertEquals(0, _config.classPathCount());
		assertFalse(_config.isCreateHtml());
		
		assertEquals("Test", _config.getClasspath());
		assertEquals("Test", _config.getPathSuitesResult());
	}
}
