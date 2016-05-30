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

package tests.testsuite.app;

import static org.junit.Assert.*;

import javax.swing.tree.TreePath;

import org.junit.Before;
import org.junit.Test;
import org.testsuite.app.ValidationEvent;

/**
 * Tests the class {@link org.testsuite.app.ValidationEvent}.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestValidationEvent {
	/**
	 * Saves the selection path object 
	 */
	private TreePath _path;
	
	/**
	 * Saves the type of event
	 */
	private int _type;
	
	/**
	 * Saves the indexes
	 */
	private int[] _indexes;
	
	/**
	 * Saves the instance of validation event
	 */
	private ValidationEvent _event;
	
	/**
	 * Initialize the tests 
	 */
	@Before
	public void setUp() throws Exception {
		_type = 10;
		Object[] test = {this};
		_path = new TreePath(test);
		
		_indexes = new int[]{10, 90, 77};
		
		_event = new ValidationEvent(this, _path, _type, _indexes);
	}

	/**
	 * Tests if the selection path is returned correctly.
	 */
	@Test
	public void testGetSelectionPath() {
		assertEquals(_path, _event.getSelectionPath());
	}

	/**
	 * Tests if the type is returned correctly.
	 */
	@Test
	public void testGetType() {
		assertEquals(_type, _event.getType());
	}

	/**
	 * Tests if the indexes is returned correctly.
	 */
	@Test
	public void testGetIndexes() {
		assertEquals(_indexes, _event.getIndexes());
	}

	/**
	 * Tests if the number of types is returned correctly.
	 */
	@Test
	public void testTypeCount() {
		assertEquals(14, ValidationEvent.typeCount());
	}
	
	/**
	 * Tests whether the error is raised IllegalArgumentException if the Type is
	 * smaller 0.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testValidationEventWithZeroAsType() {
		_event = new ValidationEvent(this, _path, 0, null);
	}
	
	/**
	 * Tests whether the error IllegalArgumentException is raised if the type is
	 * bigger 14.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testValidationEventWith15AsType() {
		_event = new ValidationEvent(this, _path, 
				ValidationEvent.typeCount() + 1, null);
	}
	
	/**
	 * Tests whether the type 
	 * {@link org.testsuite.app.ValidationEvent#TYPE_CONFIG_CLASSPATH_NOT_EXISTS}
	 * is greater than 0 and less than typeCount().
	 */
	@Test
	public void testTypeConfigClassPathNotExists() {
		assertTrue(ValidationEvent.TYPE_CONFIG_CLASSPATH_NOT_EXISTS > 0);
		assertTrue(ValidationEvent.TYPE_CONFIG_CLASSPATH_NOT_EXISTS <= 
				ValidationEvent.typeCount());
	}
	
	/**
	 * Tests whether the type 
	 * {@link org.testsuite.app.ValidationEvent#TYPE_CONFIG_PATH_LIB_NOT_EXISTS}
	 * is greater than 0 and less than typeCount().
	 */
	@Test
	public void testTypeConfigPathLibNotExists() {
		assertTrue(ValidationEvent.TYPE_CONFIG_PATH_LIB_NOT_EXISTS > 0);
		assertTrue(ValidationEvent.TYPE_CONFIG_PATH_LIB_NOT_EXISTS <= 
				ValidationEvent.typeCount());
	}
	
	/**
	 * Tests whether the type 
	 * {@link org.testsuite.app.ValidationEvent#TYPE_CONFIG_PATH_RESULT_NOT_EXISTS}
	 * is greater than 0 and less than typeCount().
	 */
	@Test
	public void testTypeConfigPathResultNotExists() {
		assertTrue(ValidationEvent.TYPE_CONFIG_PATH_RESULT_NOT_EXISTS > 0);
		assertTrue(ValidationEvent.TYPE_CONFIG_PATH_RESULT_NOT_EXISTS <= 
				ValidationEvent.typeCount());
	}
	
	/**
	 * Tests whether the type 
	 * {@link org.testsuite.app.ValidationEvent#TYPE_CONFIG_PATH_SRC} is greater
	 * than 0 and less than typeCount().
	 */
	@Test
	public void testTypeConfigPathSrc() {
		assertTrue(ValidationEvent.TYPE_CONFIG_PATH_SRC > 0);
		assertTrue(ValidationEvent.TYPE_CONFIG_PATH_SRC <= 
				ValidationEvent.typeCount());
	}
	
	/**
	 * Tests whether the type 
	 * {@link org.testsuite.app.ValidationEvent#TYPE_CONFIG_PATH_SRC_NOT_EXISTS}
	 * is greater than 0 and less than typeCount().
	 */
	@Test
	public void testTypeConfigPathSrcNotExists() {
		assertTrue(ValidationEvent.TYPE_CONFIG_PATH_SRC_NOT_EXISTS > 0);
		assertTrue(ValidationEvent.TYPE_CONFIG_PATH_SRC_NOT_EXISTS <= 
				ValidationEvent.typeCount());
	}
	
	/**
	 * Tests whether the type 
	 * {@link org.testsuite.app.ValidationEvent#TYPE_TEST_NOT_EXISTS}
	 * is greater than 0 and less than typeCount().
	 */
	@Test
	public void testTypeTestNotExists() {
		assertTrue(ValidationEvent.TYPE_TEST_NOT_EXISTS > 0);
		assertTrue(ValidationEvent.TYPE_TEST_NOT_EXISTS <= 
				ValidationEvent.typeCount());
	}
	
	/**
	 * Tests whether the type 
	 * {@link org.testsuite.app.ValidationEvent#TYPE_TEST_RUNNER_CLASSPATH_NOT_EXISTS}
	 * is greater than 0 and less than typeCount().
	 */
	@Test
	public void testTypeTestRunnerClasspathNotExists() {
		assertTrue(ValidationEvent.TYPE_TEST_RUNNER_CLASSPATH_NOT_EXISTS > 0);
		assertTrue(ValidationEvent.TYPE_TEST_RUNNER_CLASSPATH_NOT_EXISTS <= 
				ValidationEvent.typeCount());
	}
	
	/**
	 * Tests whether the type 
	 * {@link org.testsuite.app.ValidationEvent#TYPE_TEST_RUNNER_DESCRIPTION}
	 * is greater than 0 and less than typeCount().
	 */
	@Test
	public void testTypeTestRunnerDescription() {
		assertTrue(ValidationEvent.TYPE_TEST_RUNNER_DESCRIPTION > 0);
		assertTrue(ValidationEvent.TYPE_TEST_RUNNER_DESCRIPTION <= 
				ValidationEvent.typeCount());
	}
	
	/**
	 * Tests whether the type 
	 * {@link org.testsuite.app.ValidationEvent#TYPE_TEST_RUNNER_FILE_EXTENSION}
	 * is greater than 0 and less than typeCount().
	 */
	@Test
	public void testTypeTestRunnerFileExtension() {
		assertTrue(ValidationEvent.TYPE_TEST_RUNNER_FILE_EXTENSION > 0);
		assertTrue(ValidationEvent.TYPE_TEST_RUNNER_FILE_EXTENSION <= 
				ValidationEvent.typeCount());
	}
	
	/**
	 * Tests whether the type 
	 * {@link org.testsuite.app.ValidationEvent#TYPE_TEST_RUNNER_LIBRARY_NOT_EXISTS}
	 * is greater than 0 and less than typeCount().
	 */
	@Test
	public void testTypeTestRunnerLibraryNotExists() {
		assertTrue(ValidationEvent.TYPE_TEST_RUNNER_LIBRARY_NOT_EXISTS > 0);
		assertTrue(ValidationEvent.TYPE_TEST_RUNNER_LIBRARY_NOT_EXISTS <= 
				ValidationEvent.typeCount());
	}
	
	/**
	 * Tests whether the type 
	 * {@link org.testsuite.app.ValidationEvent#TYPE_TEST_SUITE_NAME}
	 * is greater than 0 and less than typeCount().
	 */
	@Test
	public void testTypeTestSuiteName() {
		assertTrue(ValidationEvent.TYPE_TEST_SUITE_NAME > 0);
		assertTrue(ValidationEvent.TYPE_TEST_SUITE_NAME <= 
				ValidationEvent.typeCount());
	}
	
	/**
	 * Tests whether the type 
	 * {@link org.testsuite.app.ValidationEvent#TYPE_TEST_SUITE_NO_TEST}
	 * is greater than 0 and less than typeCount().
	 */
	@Test
	public void testTypeTestSuiteNoTest() {
		assertTrue(ValidationEvent.TYPE_TEST_SUITE_NO_TEST > 0);
		assertTrue(ValidationEvent.TYPE_TEST_SUITE_NO_TEST <= 
				ValidationEvent.typeCount());
	}
	
	/**
	 * Tests whether the type 
	 * {@link org.testsuite.app.ValidationEvent#TYPE_TEST_SUITE_PACKAGE}
	 * is greater than 0 and less than typeCount().
	 */
	@Test
	public void testTypeTestSuitePackage() {
		assertTrue(ValidationEvent.TYPE_TEST_SUITE_PACKAGE > 0);
		assertTrue(ValidationEvent.TYPE_TEST_SUITE_PACKAGE <= 
				ValidationEvent.typeCount());
	}
	
	/**
	 * Tests whether the type 
	 * {@link org.testsuite.app.ValidationEvent#TYPE_TEST_SUITE_PACKAGE_NOT_EXISTS}
	 * is greater than 0 and less than typeCount().
	 */
	@Test
	public void testTypeTestSuitePackageNotExists() {
		assertTrue(ValidationEvent.TYPE_TEST_SUITE_PACKAGE_NOT_EXISTS > 0);
		assertTrue(ValidationEvent.TYPE_TEST_SUITE_PACKAGE_NOT_EXISTS <= 
				ValidationEvent.typeCount());
	}
}
