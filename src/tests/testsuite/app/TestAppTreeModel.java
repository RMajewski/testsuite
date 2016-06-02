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
import static org.mockito.Mockito.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testsuite.app.AppTreeModel;
import org.testsuite.app.ValidationEvent;
import org.testsuite.app.ValidationEventListener;
import org.testsuite.core.TestRunner;
import org.testsuite.data.Config;
import org.testsuite.data.Library;
import org.testsuite.data.TestSuite;


/**
 * Tests the class {@link org.testsuite.app.ValidationEvent}.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({AppTreeModel.class})
public class TestAppTreeModel {
	/**
	 * Saves the instance of TestRunnerModel
	 */
	private AppTreeModel _model;

	/**
	 * Initialize the tests
	 */
	@Before
	public void setUp() throws Exception {
		_model = new AppTreeModel();
	}
	
	/**
	 * Generate a configuration
	 * @throws Exception 
	 */
	private List<TestRunner> generateConfiguration(Config config) throws Exception {
		when(config.getPathSrc()).thenReturn("src");
		when(config.classPathCount()).thenReturn(1);
		when(config.getClassPath(0)).thenReturn("classpath");
		when(config.javascriptFileCount()).thenReturn(1);
		when(config.getJavascriptFile(0)).thenReturn("resources/html/out.js");
		when(config.stylesheetFileCount()).thenReturn(1);
		when(config.getStylesheetFile(0)).thenReturn("resources/html/out.css");
		when(config.getPathLibrary()).thenReturn("pathLibrary");
		when(config.getPathResult()).thenReturn("pathResult");
		
		List<TestRunner> ret = new ArrayList<TestRunner>();
		
		org.testsuite.data.Test test = mock(org.testsuite.data.Test.class);
		when(test.getName()).thenReturn("Test");
		when(test.isExists()).thenReturn(true);
		
		TestSuite suite = mock(TestSuite.class);
		when(suite.testCount()).thenReturn(1);
		when(suite.getTest(0)).thenReturn(test);
		when(suite.getPackage()).thenReturn("package");
		when(suite.getName()).thenReturn("TestSuite");
		when(suite.isExists()).thenReturn(true);
		
		Library lib = mock(Library.class);
		when(lib.getFileName()).thenReturn("library");
		when(lib.getPath()).thenReturn(new String());
		
		TestRunner runner = mock(TestRunner.class);
		when(runner.testSuiteCount()).thenReturn(1);
		when(runner.getTestSuite(0)).thenReturn(suite);
		when(runner.classPathCount()).thenReturn(1);
		when(runner.getClassPath(0)).thenReturn("claspath");
		when(runner.libraryCount()).thenReturn(1);
		when(runner.getLibrary(0)).thenReturn(lib);
		when(runner.getFileExtension()).thenReturn("extension");
		when(runner.getDescription()).thenReturn("description");
		ret.add(runner);
		
		File file = mock(File.class);
		when(file.exists()).thenReturn(true);
		PowerMockito.whenNew(File.class).withAnyArguments().thenReturn(file);
		
		return ret;
	}

	/**
	 * Simulate a TreeModelListener
	 * @author René Majewski
	 *
	 */
	private class TDL implements TreeModelListener {

		public boolean _treeNodesChanged = false;
		public boolean _treeStructureChanged = false;
		
		@Override
		public void treeNodesChanged(TreeModelEvent e) {
			_treeNodesChanged = true;
		}

		@Override
		public void treeNodesInserted(TreeModelEvent e) {
		}

		@Override
		public void treeNodesRemoved(TreeModelEvent e) {
		}

		@Override
		public void treeStructureChanged(TreeModelEvent e) {
			_treeStructureChanged = true;
		}
		
	}
	
	/**
	 * Simulate a ValidationEventListener
	 * 
	 * @author René Majewski
	 */
	private class VEL implements ValidationEventListener {
		public int _validationError = 0;
		public int[] _errors = new int[ValidationEvent.typeCount()];
		@Override
		public void validationError(ValidationEvent ve) {
			_validationError++;
			_errors[ve.getType() - 1]++;
		}
	}

	/**
	 * Tests if a TreeModelListener can be added to the list.
	 */
	@Test
	public void testAddTreeModelListener() {
		assertEquals(0, _model.treeModelListenerCount());

		_model.addTreeModelListener(new TDL());
		
		assertEquals(1, _model.treeModelListenerCount());
	}

	/**
	 * Tests if a Validation Event listeners can be added to list.
	 */
	@Test
	public void testAddValidationEventListener() {
		assertEquals(0, _model.validationEventListenerCount());
		
		_model.addValidationEventListener(new VEL());
		
		assertEquals(1, _model.validationEventListenerCount());
	}

	/**
	 * Tests if the event arrives at treeNodesChanged all registered listeners.
	 */
	@Test
	public void testFireTreeNodesChanged() {
		TDL tdl1 = new TDL();
		TDL tdl2 = new TDL();
		TDL tdl3 = new TDL();
		
		_model.addTreeModelListener(tdl1);
		_model.addTreeModelListener(tdl2);
		_model.addTreeModelListener(tdl3);
		
		_model.fireTreeNodesChanged(new TreePath(new Object[]{this}));
		
		assertTrue(tdl1._treeNodesChanged);
		assertTrue(tdl2._treeNodesChanged);
		assertTrue(tdl3._treeNodesChanged);
	}

	/**
	 * Tests if the event arrives at treeNodesChanged all registered listeners.
	 */
	@Test
	public void testFireTreeStructureChanged() {
		TDL tdl1 = new TDL();
		TDL tdl2 = new TDL();
		TDL tdl3 = new TDL();
		
		_model.addTreeModelListener(tdl1);
		_model.addTreeModelListener(tdl2);
		_model.addTreeModelListener(tdl3);
		
		_model.fireTreeStructureChanged();
		
		assertTrue(tdl1._treeStructureChanged);
		assertTrue(tdl2._treeStructureChanged);
		assertTrue(tdl3._treeStructureChanged);
	}

	/**
	 * Tests if the event arrives at validatenError all registered listeners.
	 */
	@Test
	public void testFireValidationError() {
		VEL vel1 = new VEL();
		VEL vel2 = new VEL();
		VEL vel3 = new VEL();
		
		_model.addValidationEventListener(vel1);
		_model.addValidationEventListener(vel2);
		_model.addValidationEventListener(vel3);
		
		_model.fireValidationError(new TreePath(new Object[]{this}), 
				ValidationEvent.TYPE_TEST_NOT_EXISTS, new int[]{10, 10});
		
		assertEquals(1, vel1._validationError);
		assertEquals(1, vel2._validationError);
		assertEquals(1, vel3._validationError);
	}

	/**
	 * Checks if the right child is returned.
	 */
	@Test
	public void testGetChildOfRoot() {
		List<TestRunner> list = new ArrayList<TestRunner>();
		TestRunner runner = mock(TestRunner.class);
		list.add(runner);
		
		assertEquals(runner, _model.getChild(list, 0));
	}

	/**
	 * Checks if the right child is returned.
	 */
	@Test
	public void testGetChildOfTestRunner() {
		TestSuite suite = mock(TestSuite.class);
		TestRunner runner = mock(TestRunner.class);
		
		when(runner.getTestSuite(1)).thenReturn(suite);
		
		assertEquals(suite, _model.getChild(runner, 1));
		
		verify(runner).getTestSuite(1);
	}

	/**
	 * Checks if the right child is returned.
	 */
	@Test
	public void testGetChildOfTestSuite() {
		org.testsuite.data.Test test = mock(org.testsuite.data.Test.class);
		TestSuite suite = mock(TestSuite.class);
		
		when(suite.getTest(2)).thenReturn(test);
		
		assertEquals(test, _model.getChild(suite, 2));
		
		verify(suite).getTest(2);
	}

	/**
	 * Checks if the right child is returned.
	 */
	@Test
	public void testGetChildOfTest() {
		String name = "Test";
		org.testsuite.data.Test test = mock(org.testsuite.data.Test.class);
		when(test.getName()).thenReturn(name);
		
		assertEquals(name, _model.getChild(test, 2));
		
		verify(test).getName();
	}

	/**
	 * Verifies that the correct number of children will be returned.
	 */
	@Test
	public void testGetChildCountOfRoot() {
		List<TestRunner> list = new ArrayList<TestRunner>();
		TestRunner runner1 = mock(TestRunner.class);
		TestRunner runner2 = mock(TestRunner.class);
		list.add(runner1);
		list.add(runner2);
		
		assertEquals(2, _model.getChildCount(list));
	}

	/**
	 * Verifies that the correct number of children will be returned.
	 */
	@Test
	public void testGetChildCountOfTestRunner() {
		int childs = 10;
		TestRunner runner = mock(TestRunner.class);
		when(runner.testSuiteCount()).thenReturn(childs);
		
		assertEquals(childs, _model.getChildCount(runner));
		
		verify(runner).testSuiteCount();
	}

	/**
	 * Verifies that the correct number of children will be returned.
	 */
	@Test
	public void testGetChildCountOfTestSuite() {
		int childs = 5;
		TestSuite suite = mock(TestSuite.class);
		when(suite.testCount()).thenReturn(childs);
		
		assertEquals(childs, _model.getChildCount(suite));
		
		verify(suite).testCount();
	}

	/**
	 * Verifies that the correct number of children will be returned.
	 */
	@Test
	public void testGetChildCountOfAllOthers() {
		assertEquals(0, _model.getChildCount(new Object()));
	}

	/**
	 * Tests if standard return value is returned because the method is not
	 * needed.
	 */
	@Test
	public void testGetIndexOfChild() {
		assertEquals(0, _model.getIndexOfChild(new Object(), new Object()));
	}

	/**
	 * Tests if the root element is returned correctly.
	 */
	@Test
	public void testGetRoot() {
		assertEquals(new ArrayList<TestRunner>(), _model.getRoot());
	}

	/**
	 * Tests if a Test is recognized as a leaf.
	 */
	@Test
	public void testTestIsLeaf() {
		assertTrue(_model.isLeaf(mock(org.testsuite.data.Test.class)));
	}

	/**
	 * Tests if a TestRunner is not recognized as a leaf.
	 */
	@Test
	public void testTestRunnerIsNotLeaf() {
		assertFalse(_model.isLeaf(mock(TestRunner.class)));
	}

	/**
	 * Tests if a TestSuite is not recognized as a leaf.
	 */
	@Test
	public void testTestSuiteIsNotLeaf() {
		assertFalse(_model.isLeaf(mock(TestSuite.class)));
	}

	/**
	 * Tests if a root element is not recognized as a leaf.
	 */
	@Test
	public void testRootElementIsNotLeaf() {
		assertFalse(_model.isLeaf(new ArrayList<TestRunner>()));
	}

	/**
	 * Tests if the specified TreeModelListener will be deleted from the list.
	 */
	@Test
	public void testRemoveTreeModelListener() {
		TDL tdl = new TDL();
		_model.addTreeModelListener(tdl);
		assertEquals(1, _model.treeModelListenerCount());
		
		_model.removeTreeModelListener(tdl);
		assertEquals(0, _model.treeModelListenerCount());
	}

	/**
	 * Tests if the specified ValidationEventListener will be deleted from the
	 * list.
	 */
	@Test
	public void testRemoveValidationEventListener() {
		VEL vel = new VEL();
		_model.addValidationEventListener(vel);
		assertEquals(1, _model.validationEventListenerCount());
		
		_model.removeValidationEventListener(vel);
		assertEquals(0, _model.validationEventListenerCount());
	}

	/**
	 * Tests if the list is taken with test runners and whether they will be
	 * given right back.
	 * 
	 * @see org.testsuite.app.AppTreeModel#setListOfTestRunner(List)
	 * @see org.testsuite.app.AppTreeModel#getTestRunnerList()
	 */
	@Test
	public void testSetListOfTestRunner() {
		List<TestRunner> list = new ArrayList<TestRunner>();
		_model.setListOfTestRunner(list);
		assertEquals(list, _model.getTestRunnerList());
	}
	
	/**
	 * Tests if the correct number of TreeModelListenern is returned.
	 */
	@Test
	public void testTreeModelListenerCount() {
		assertEquals(0, _model.treeModelListenerCount());
	}

	/**
	 * Tests if validated correctly.
	 */
	@Test
	public void testValidateConfiguration() throws Exception {
		Config config = mock(Config.class);
		
		VEL vel = new VEL();
		_model.addValidationEventListener(vel);
		_model.setListOfTestRunner(generateConfiguration(config));
		
		_model.validateConfiguration(config);
		assertEquals(0, vel._validationError);
	}

	/**
	 * Tests if the ValidationError is triggered by Type TYPE_CONFIG_PATH_SRC.
	 * @throws Exception 
	 */
	@Test
	public void testValidateConfigurationErrorOfConfigPathSrc() 
			throws Exception {
		Config config = mock(Config.class);
		
		VEL vel = new VEL();
		_model.addValidationEventListener(vel);
		_model.setListOfTestRunner(generateConfiguration(config));

		when(config.getPathSrc()).thenReturn(new String());
		
		_model.validateConfiguration(config);
		assertEquals(1, vel._errors[ValidationEvent.TYPE_CONFIG_PATH_SRC - 1]);
	}

	/**
	 * Tests if the ValidationError is triggered by Type 
	 * TYPE_CONFIG_PATH_SRC_NOT_EXISTS.
	 */
	@Test
	public void testValidateConfigurationErrorOfConfigPathSrcNotExists() 
			throws Exception {
		Config config = mock(Config.class);
		
		VEL vel = new VEL();
		_model.addValidationEventListener(vel);
		_model.setListOfTestRunner(generateConfiguration(config));
		
		File file = mock(File.class);
		when(file.exists()).thenReturn(false);
		PowerMockito.whenNew(File.class)
			.withArguments(config.getPathSrc())
			.thenReturn(file);
		
		_model.validateConfiguration(config);
		assertEquals(1, 
				vel._errors[ValidationEvent.TYPE_CONFIG_PATH_SRC_NOT_EXISTS - 1]);
	}

	/**
	 * Tests if the ValidationError is triggered by Type 
	 * TYPE_CONFIG_NO_TEST_RUNNER.
	 */
	@Test
	public void testValidateConfigurationErrorOfConfigNoTestRunner() 
			throws Exception {
		Config config = mock(Config.class);
		generateConfiguration(config);
		
		VEL vel = new VEL();
		_model.addValidationEventListener(vel);
		
		_model.validateConfiguration(config);
		assertEquals(1, 
				vel._errors[ValidationEvent.TYPE_CONFIG_NO_TEST_RUNNER - 1]);
	}

	/**
	 * Tests if the ValidationError is triggered by Type 
	 * TYPE_CONFIG_PATH_RESULT_NOT_EXISTS.
	 */
	@Test
	public void testValidateConfigurationErrorOfConfigPathResultNotExists() 
			throws Exception {
		Config config = mock(Config.class);
		
		VEL vel = new VEL();
		_model.addValidationEventListener(vel);
		_model.setListOfTestRunner(generateConfiguration(config));
		
		File file = mock(File.class);
		when(file.exists()).thenReturn(false);
		PowerMockito.whenNew(File.class)
			.withArguments(config.getPathResult())
			.thenReturn(file);
		
		_model.validateConfiguration(config);
		assertEquals(1, 
				vel._errors[ValidationEvent.TYPE_CONFIG_PATH_RESULT_NOT_EXISTS - 1]);
	}

	/**
	 * Tests if the ValidationError is triggered by Type 
	 * TYPE_CONFIG_PATH_LIB_NOT_EXISTS.
	 */
	@Test
	public void testValidateConfigurationErrorOfConfigPathLibNotExists() 
			throws Exception {
		Config config = mock(Config.class);
		
		VEL vel = new VEL();
		_model.addValidationEventListener(vel);
		_model.setListOfTestRunner(generateConfiguration(config));
		
		File file = mock(File.class);
		when(file.exists()).thenReturn(false);
		PowerMockito.whenNew(File.class)
			.withArguments(config.getPathLibrary())
			.thenReturn(file);
		
		_model.validateConfiguration(config);
		assertEquals(1, 
				vel._errors[ValidationEvent.TYPE_CONFIG_PATH_LIB_NOT_EXISTS - 1]);
	}

	/**
	 * Tests if the ValidationError is triggered by Type 
	 * TYPE_CONFIG_CLASSPATH_NOT_EXISTS.
	 */
	@Test
	public void testValidateConfigurationErrorOfConfigClassPathNotExists() 
			throws Exception {
		Config config = mock(Config.class);
		
		VEL vel = new VEL();
		_model.addValidationEventListener(vel);
		_model.setListOfTestRunner(generateConfiguration(config));
		
		File file = mock(File.class);
		when(file.exists()).thenReturn(false);
		PowerMockito.whenNew(File.class)
			.withArguments(config.getClassPath(0))
			.thenReturn(file);
		
		_model.validateConfiguration(config);
		assertEquals(1, 
				vel._errors[ValidationEvent.TYPE_CONFIG_CLASSPATH_NOT_EXISTS - 1]);
	}

	/**
	 * Tests if the ValidationError is triggered by Type 
	 * TYPE_CONFIG_JAVASCRIPT_NOT_EXISTS.
	 */
	@Test
	public void testValidateConfigurationErrorOfConfigJavascriptNotExists() 
			throws Exception {
		Config config = mock(Config.class);
		
		VEL vel = new VEL();
		_model.addValidationEventListener(vel);
		_model.setListOfTestRunner(generateConfiguration(config));

		when(config.getJavascriptFile(0)).thenReturn("out.js");
		
		_model.validateConfiguration(config);
		assertEquals(1, 
				vel._errors[ValidationEvent.TYPE_CONFIG_JAVASCRIPT_NOT_EXISTS - 1]);
	}

	/**
	 * Tests if the ValidationError is triggered by Type 
	 * TYPE_CONFIG_JAVASCRIPT_NOT_EXISTS.
	 */
	@Test
	public void testValidateConfigurationErrorOfConfigStylesheetNotExists() 
			throws Exception {
		Config config = mock(Config.class);
		
		VEL vel = new VEL();
		_model.addValidationEventListener(vel);
		_model.setListOfTestRunner(generateConfiguration(config));

		when(config.getStylesheetFile(0)).thenReturn("out.css");
		
		_model.validateConfiguration(config);
		assertEquals(1, 
				vel._errors[ValidationEvent.TYPE_CONFIG_STYLESHEET_NOT_EXISTS - 1]);
	}

	/**
	 * Tests if the ValidationError is triggered by Type 
	 * TYPE_TEST_RUNNER_DESCRIPTION.
	 */
	@Test
	public void testValidateConfigurationErrorOfTestRunnerDescription() 
			throws Exception {
		Config config = mock(Config.class);
		
		VEL vel = new VEL();
		_model.addValidationEventListener(vel);
		_model.setListOfTestRunner(generateConfiguration(config));
		
		when(_model.getTestRunnerList().get(0).getDescription()).thenReturn(
				new String());
		
		_model.validateConfiguration(config);
		assertEquals(1, 
				vel._errors[ValidationEvent.TYPE_TEST_RUNNER_DESCRIPTION - 1]);
	}

	/**
	 * Tests if the ValidationError is triggered by Type 
	 * TYPE_TEST_RUNNER_FILE_EXTENSION.
	 */
	@Test
	public void testValidateConfigurationErrorOfTestRunnerFileExtension() 
			throws Exception {
		Config config = mock(Config.class);
		
		VEL vel = new VEL();
		_model.addValidationEventListener(vel);
		_model.setListOfTestRunner(generateConfiguration(config));
		
		when(_model.getTestRunnerList().get(0).getFileExtension()).thenReturn(
				new String());
		
		_model.validateConfiguration(config);
		assertEquals(1, 
				vel._errors[ValidationEvent.TYPE_TEST_RUNNER_FILE_EXTENSION - 1]);
	}

	/**
	 * Tests if the ValidationError is triggered by Type 
	 * TYPE_TEST_RUNNER_LIBRARY_NOT_EXISTS.
	 */
	@Test
	public void testValidateConfigurationErrorOfTestRunnerLibraryNotExists() 
			throws Exception {
		Config config = mock(Config.class);
		
		VEL vel = new VEL();
		_model.addValidationEventListener(vel);
		_model.setListOfTestRunner(generateConfiguration(config));
		
		File file = mock(File.class);
		when(file.exists()).thenReturn(false);
		PowerMockito.whenNew(File.class)
			.withArguments(config.getPathLibrary() + File.separator +
					_model.getTestRunnerList().get(0).getLibrary(0)
					.getFileName())
			.thenReturn(file);
		
		_model.validateConfiguration(config);
		assertEquals(1, 
				vel._errors[ValidationEvent.TYPE_TEST_RUNNER_LIBRARY_NOT_EXISTS - 1]);
	}

	/**
	 * Tests if the ValidationError is triggered by Type 
	 * TYPE_TEST_RUNNER_CLASSPATH_NOT_EXISTS.
	 */
	@Test
	public void testValidateConfigurationErrorOfTestRunnerClasspathNotExists() 
			throws Exception {
		Config config = mock(Config.class);
		
		VEL vel = new VEL();
		_model.addValidationEventListener(vel);
		_model.setListOfTestRunner(generateConfiguration(config));
		
		File file = mock(File.class);
		when(file.exists()).thenReturn(false);
		PowerMockito.whenNew(File.class)
			.withArguments(_model.getTestRunnerList().get(0).getClassPath(0))
			.thenReturn(file);
		
		_model.validateConfiguration(config);
		assertEquals(1, 
				vel._errors[ValidationEvent.TYPE_TEST_RUNNER_CLASSPATH_NOT_EXISTS - 1]);
	}

	/**
	 * Tests if the ValidationError is triggered by Type 
	 * TYPE_TEST_SUITE_NAME.
	 */
	@Test
	public void testValidateConfigurationErrorOfTestSuiteName() 
			throws Exception {
		Config config = mock(Config.class);
		
		VEL vel = new VEL();
		_model.addValidationEventListener(vel);
		_model.setListOfTestRunner(generateConfiguration(config));
		
		when(_model.getTestRunnerList().get(0).getTestSuite(0).getName())
			.thenReturn(new String());
		
		_model.validateConfiguration(config);
		assertEquals(1, 
				vel._errors[ValidationEvent.TYPE_TEST_SUITE_NAME - 1]);
	}

	/**
	 * Tests if the ValidationError is triggered by Type 
	 * TYPE_TEST_SUITE_PACKAGE.
	 */
	@Test
	public void testValidateConfigurationErrorOfTestSuitePackage() 
			throws Exception {
		Config config = mock(Config.class);
		
		VEL vel = new VEL();
		_model.addValidationEventListener(vel);
		_model.setListOfTestRunner(generateConfiguration(config));
		
		when(_model.getTestRunnerList().get(0).getTestSuite(0).getPackage())
			.thenReturn(new String());
		
		_model.validateConfiguration(config);
		assertEquals(1, 
				vel._errors[ValidationEvent.TYPE_TEST_SUITE_PACKAGE - 1]);
	}

	/**
	 * Tests if the ValidationError is triggered by Type 
	 * TYPE_TEST_SUITE_PACKAGE_NOT_EXISTS.
	 */
	@Test
	public void testValidateConfigurationErrorOfTestSuitePackageNotExists() 
			throws Exception {
		Config config = mock(Config.class);
		
		VEL vel = new VEL();
		_model.addValidationEventListener(vel);
		_model.setListOfTestRunner(generateConfiguration(config));
		
		when(_model.getTestRunnerList().get(0).getTestSuite(0).isExists())
			.thenReturn(false);
		
		_model.validateConfiguration(config);
		assertEquals(1, 
				vel._errors[ValidationEvent.TYPE_TEST_SUITE_PACKAGE_NOT_EXISTS - 1]);
	}

	/**
	 * Tests if the ValidationError is triggered by Type 
	 * TYPE_TEST_SUITE_NO_TEST.
	 */
	@Test
	public void testValidateConfigurationErrorOfTestSuiteNoTest() 
			throws Exception {
		Config config = mock(Config.class);
		
		VEL vel = new VEL();
		_model.addValidationEventListener(vel);
		_model.setListOfTestRunner(generateConfiguration(config));
		
		when(_model.getTestRunnerList().get(0).getTestSuite(0).testCount())
			.thenReturn(0);
		
		_model.validateConfiguration(config);
		assertEquals(1, 
				vel._errors[ValidationEvent.TYPE_TEST_SUITE_NO_TEST - 1]);
	}

	/**
	 * Tests if the ValidationError is triggered by Type 
	 * TYPE_TEST_NAME.
	 */
	@Test
	public void testValidateConfigurationErrorOfTestName() 
			throws Exception {
		Config config = mock(Config.class);
		
		VEL vel = new VEL();
		_model.addValidationEventListener(vel);
		_model.setListOfTestRunner(generateConfiguration(config));
		
		when(_model.getTestRunnerList().get(0).getTestSuite(0).getTest(0)
				.getName()).thenReturn(new String());
		
		_model.validateConfiguration(config);
		assertEquals(1, 
				vel._errors[ValidationEvent.TYPE_TEST_NAME - 1]);
	}

	/**
	 * Tests if the ValidationError is triggered by Type 
	 * TYPE_TEST_NOT_EXISTS.
	 */
	@Test
	public void testValidateConfigurationErrorOfTestNotExists() 
			throws Exception {
		Config config = mock(Config.class);
		
		VEL vel = new VEL();
		_model.addValidationEventListener(vel);
		_model.setListOfTestRunner(generateConfiguration(config));
		
		when(_model.getTestRunnerList().get(0).getTestSuite(0).getTest(0)
				.isExists()).thenReturn(false);
		
		_model.validateConfiguration(config);
		assertEquals(1, 
				vel._errors[ValidationEvent.TYPE_TEST_NOT_EXISTS - 1]);
	}

	/**
	 * Tests if the ValidationError is triggered by Type 
	 * TYPE_TEST_RUNNER_NO_TEST_SUITE.
	 */
	@Test
	public void testValidateConfigurationErrorOfTestRunnerNoTestSuite() 
			throws Exception {
		Config config = mock(Config.class);
		
		VEL vel = new VEL();
		_model.addValidationEventListener(vel);
		_model.setListOfTestRunner(generateConfiguration(config));
		
		when(_model.getTestRunnerList().get(0).testSuiteCount()).thenReturn(0);
		
		_model.validateConfiguration(config);
		assertEquals(1, 
				vel._errors[ValidationEvent.TYPE_TEST_RUNNER_NO_TEST_SUITE - 1]);
	}

}
