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

package org.testsuite.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.Timer;

import org.testsuite.data.Config;
import org.testsuite.data.Library;
import org.testsuite.data.Test;
import org.testsuite.data.TestEvent;
import org.testsuite.data.TestEventListener;
import org.testsuite.data.TestSuite;

/**
 * From this class all TestRunner be derived.
 * 
 * @author René Majewski
 *
 * @version 0.2
 */
public abstract class TestRunner {
	/**
	 * Saves the exit status for terminated execute
	 */
	public static final int EXIT_TERMINATED_EXEC = 143;
	
	/**
	 * Saves the exit status for error
	 */
	public static final int EXIT_ERROR = 1001;
	
	/**
	 * Saves the exit status for executed ok
	 */
	public static final int EXIT_OK = 0;
	
	/**
	 * Saves the file for resource bundle
	 */
	protected static final String BUNDLE_FILE =
			"resources.lang.org.testsuite.core.TestRunner";
	
	/**
	 * Hold an instance of the list of TestSuites.
	 */
	protected List<TestSuite> _suites;
	
	/**
	 * Hold an instance of the list of Library.
	 */
	protected List<Library> _library;
	
	/**
	 * Hold a instance of the list of classpath.
	 */
	protected List<String> _classpath;
	
	/**
	 * Saves the file extension.
	 */
	protected String _fileExtension;
	
	/**
	 * Saves the configuration.
	 */
	protected Config _config;
	
	/**
	 * Saves the description
	 */
	protected String _description;
	
	/**
	 * Saves the last suite id
	 */
	protected int _lastSuiteId;
	
	/**
	 * Saves the instance of resource bundle
	 */
	protected ResourceBundle _bundle;
	
	/**
	 * Saves the list of TestEventListener.
	 */
	protected Vector<TestEventListener> _testEventListeners;
	
	/**
	 * Initialize the data of the class.
	 * 
	 * <strong>Important</strong>: It must also be initialized config. Please
	 * use {@link #setConfig(Config)}.
	 */
	public TestRunner() {
		try {
			_bundle = ResourceBundle.getBundle(BUNDLE_FILE);
		} catch (MissingResourceException e) {
			e.printStackTrace();
		}
		_suites = new ArrayList<TestSuite>();
		_library = new ArrayList<Library>();
		_classpath = new ArrayList<String>();
		_testEventListeners = new Vector<TestEventListener>();
		_fileExtension = new String();
		_config = null;
		_description = new String();
		_lastSuiteId = -1;
	}
	
	/**
	 * Initialis the data of the class.
	 * 
	 * @param config The configuration
	 */
	public TestRunner(Config config) {
		this();
		_config = config;
	}
	
	/**
	 * Determines the number of test suites.
	 * 
	 * @return Number of test suites
	 */
	public int testSuiteCount() {
		return _suites.size();
	}
	
	/**
	 * Determines the number of all include tests.
	 * 
	 * @return Number of all include tests.
	 */
	public int getTestsCount() {
		int ret = 0;
		
		for (int suite = 0; suite < _suites.size(); suite++)
			ret += _suites.get(suite).testCount();
		
		return ret;
	}
	
	/**
	 * Adds a test suite to the list.
	 * 
	 * @param suite Test Suite, which is to be added to the list.
	 */
	public void addTestSuite(TestSuite suite) {
		_suites.add(suite);
	}
	
	/**
	 * Sets a new list with test suites
	 * 
	 * @param list New list with test suites
	 */
	public void setTestSuiteList(List<TestSuite> list) {
		_suites = list;
	}
	
	/**
	 * Returns the test suite with the index
	 * 
	 * @param index Index of test suite
	 * 
	 * @return Test suite with the index
	 */
	public TestSuite getTestSuite(int index) {
		return _suites.get(index);
	}
	
	/**
	 * Returns the list with test suites.
	 * 
	 * @return List with test suites.
	 */
	public List<TestSuite> getTestSuiteList() {
		return _suites;
	}
	
	/**
	 * Removes the test suite from list
	 * 
	 * @param o TestSuite, which is to be deleted
	 */
	public void removeTestSuite(Object o) {
		_suites.remove(o);
	}
	
	/**
	 * Determines the number of libraries.
	 * 
	 * @return Number of libraries.
	 */
	public int libraryCount() {
		return _library.size();
	}
	
	/**
	 * Adds a library to the list.
	 * 
	 * @param library Library, which is to be added to the list.
	 */
	public void addLibrary(Library library) {
		_library.add(library);
	}
	
	/**
	 * Sets the list with libraries with new datas
	 * 
	 * @param list New list with libraries
	 */
	public void setLibraryList(List<Library> list) {
		_library = list;
	}
	
	/**
	 * Returns the library with the index
	 * 
	 * @param index Index of the library
	 * 
	 * @return Library with theindex
	 */
	public Library getLibrary(int index) {
		return _library.get(index);
	}
	
	/**
	 * Returns the list with libraries
	 * 
	 * @return List with libraries
	 */
	public List<Library> getLibraryList() {
		return _library;
	}
	
	/**
	 * Removes the library from the list
	 * 
	 * @param lib Library, to be deleted.
	 */
	public void removeLibrary(Library lib) {
		for (int i = 0; i < _library.size(); i++)
			if (_library.get(i).getFileName().equals(lib.getFileName()))
				_library.remove(i);
	}
	
	/**
	 * Determines the number of paths for classpath.
	 * 
	 * @return Number of libraries.
	 */
	public int classPathCount() {
		return _classpath.size();
	}
	
	/**
	 * Returns the classpath with the index
	 * 
	 * @param index Index of the classpath
	 * 
	 * @return Classpath with the index
	 */
	public String getClassPath(int index) {
		return _classpath.get(index);
	}
	
	/**
	 * Returns the list with class paths
	 * 
	 * @return List with class paths
	 */
	public List<String> getClassPathList() {
		return _classpath;
	}
	
	/**
	 * Adds a path to the list.
	 * 
	 * @param library Path, which is to be added to the list.
	 */
	public void addClassPath(String path) {
		if ((path == null) || path.isEmpty())
			throw new IllegalArgumentException();
		_classpath.add(path);
	}
	
	/**
	 * Sets the new list with class paths
	 * 
	 * @param list New list with class paths
	 */
	public void setClassPathList(List<String> list) {
		_classpath = list;
	}
	
	/**
	 * Deletes the specified classpath from the list.
	 * 
	 * @param path Classpath, to be deleted.
	 */
	public void removeClassPath(String path) {
		for (int i = 0;i < _classpath.size(); i++)
			if (_classpath.get(i).equals(path))
				_classpath.remove(i);
	}
	
	/**
	 * Returns the file extension.
	 * 
	 * @return The file extension.
	 */
	public String getFileExtension() {
		return _fileExtension;
	}
	
	/**
	 * Sets the file extension
	 * 
	 * @param extension The new file extension
	 */
	public void setFileExtension(String extension) {
		if ((extension == null) || extension.isEmpty())
			throw new IllegalArgumentException();
		_fileExtension = extension;
	}
	
	/**
	 * Returns the description.
	 * 
	 * @return The description.
	 */
	public String getDescription() {
		return _description;
	}
	
	/**
	 * Sets the description
	 * 
	 * @param description The new description.
	 */
	public void setDescription(String description) {
		if ((description == null) || description.isEmpty())
			throw new IllegalArgumentException();
		_description = description;
	}
	
	/**
	 * Returns the configuration.
	 * 
	 * @return Configuration
	 */
	public Config getConfig() {
		return _config;
	}
	
	/**
	 * Sets the configuration.
	 * 
	 * @param config The new configuration.
	 */
	public void setConfig(Config config) {
		if (config == null)
			throw new IllegalArgumentException();
		_config = config;
	}
	
	
	/**
	 * Specifies the last test suite id firmly.
	 * 
	 * @param test The new last test suite id
	 */
	public int getLastSuiteId() {
		return _lastSuiteId;
	}
	
	/**
	 * Gets the last test suite id.
	 * 
	 * @return The last test suite id.
	 */
	public void setLastSuiteId(int lastSuiteId) {
		_lastSuiteId = lastSuiteId;
	}
	
	/**
	 * Verifies that the directories and files Test exist.
	 */
	public void checkFileExists() {
		for (int suite = 0; suite < _suites.size(); suite++) {
			// Überprüft, ob das Verzeichnis existiert
			String path = _config.getPathSrc() +  File.separator +
					_suites.get(suite).getPackage()
					.replaceAll("\\.", File.separator);
			boolean pathExists = false;
			if (new File(path).exists())
				pathExists = true;
			_suites.get(suite).setExists(pathExists);
			
			for (int test = 0; test < _suites.get(suite).testCount(); test++) {
				// Überprüft, ob das Verzeichnis existiert
				boolean fileExists = false;
				if (pathExists) {
					String fileName = path + File.separator +
							_suites.get(suite).getTest(test).getName() + "." +
							_fileExtension;
					File file = new File(fileName);
					if (file.exists())
						fileExists = true;
				}
				_suites.get(suite).getTest(test).setExists(fileExists);
			}
		}
	}
	
	/**
	 * Creates the HTML list of libraries used.
	 * 
	 * @return HTML list of libraries.
	 */
	public String createHtmlListOfLibraries() {
		StringBuilder ret = new StringBuilder();
		
		boolean library = false;
		
		for (int i = 0; i < _library.size(); i++) {
			String name = _library.get(i).getName();
			if (!name.isEmpty()) {
				ret.append("\t\t\t\t\t<li>");
				ret.append(name);
				
				String version = _library.get(i).getVersion();
				if (!version.isEmpty()) {
					ret.append(" ");
					ret.append(version);
				}
				
				ret.append("</li>");
				ret.append(System.lineSeparator());
				
				library = true;
			}
		}
		
		if (library) {
			StringBuilder t = new StringBuilder();
			t.append("\t\t\t<div class=\"libraries\">");
			t.append(System.lineSeparator());
			
			t.append("\t\t\t\t<p>");
			t.append(_bundle.getString("createHtmlListOfLibraries_libraries"));
			t.append(":</p>");
			t.append(System.lineSeparator());
			
			t.append("\t\t\t\t<ul>");
			t.append(System.lineSeparator());
			ret.insert(0, t);
			
			ret.append("\t\t\t\t</ul>");
			ret.append(System.lineSeparator());
			
			ret.append("\t\t\t</div>");
			ret.append(System.lineSeparator());
		}
		
		return ret.toString();
	}
	
	/**
	 * It is issued the description of the tests. If you pass true, a horizontal
	 * line is output before that. If false, no line is output.
	 * 
	 * @param line If a horizontal line is output.
	 * 
	 * @return The description of tests.
	 */
	public String createHtmlHead(boolean line) {
		StringBuilder ret = new StringBuilder();

		if (line) {
			ret.append("\t\t\t<hr/>");
			ret.append(System.lineSeparator());
		}

		if (!_description.isEmpty()) {
			
			ret.append("\t\t\t<div class=\"testdescription\">");
	
			for (int i = 0; i < _description.length(); i++) {
				char c = _description.charAt(i);
				if (c == '[')
					c = '<';
				else if (c == ']')
					c = '>';
				ret.append(c);
			}
			
			ret.append("</div>");
			ret.append(System.lineSeparator());
		}
		
		return ret.toString();
	}
	
	/**
	 * Creates the HTML list with nonexistent tests.
	 * 
	 * @return List with nonexistent tests.
	 */
	public String createHtmlListOfNonExistsTests() {
		StringBuilder ret = new StringBuilder();
		
		boolean nonExists = false;
		
		for (int suite = 0; suite < _suites.size(); suite++) {
			for (int test = 0; test < _suites.get(suite).testCount(); test++) {
				if (!_suites.get(suite).getTest(test).isExists() && 
						(_suites.get(suite).getTest(test).getName() != null) &&
						!_suites.get(suite).getTest(test).getName().isEmpty()) {
					ret.append("\t\t\t\t\t<li>");
					ret.append(_suites.get(suite).getTest(test).getName());
					ret.append("</li>");
					ret.append(System.lineSeparator());
					nonExists = true;
				}
					
			}
		}
		
		if (nonExists) {
			StringBuilder t = new StringBuilder("\t\t\t<div class=\"nonexists\">");
			t.append(System.lineSeparator());
			t.append("\t\t\t\t<p>");
			t.append(_bundle.getString("createHtmlListOfNonExistsTests_tests"));
			t.append(":</p>");
			t.append(System.lineSeparator());
			t.append("\t\t\t\t<ul>");
			t.append(System.lineSeparator());
			ret.insert(0, t);
			
			ret.append("\t\t\t\t</ul>");
			ret.append(System.lineSeparator());
			ret.append("\t\t\t</div>");
			ret.append(System.lineSeparator());
		}
		
		return ret.toString();
	}
	
	/**
	 * Generates the HTML framework for the entry of test results.
	 * 
	 * @param html Class, which helps to generate the HTML code.
	 * 
	 * @throws IOException 
	 */
	public String createHtml(HtmlOut html, boolean line) throws IOException {
		StringBuilder ret = new StringBuilder("\t\t<div class=\"testgroup\">");
		ret.append(System.lineSeparator());
		
		ret.append(createHtmlHead(line));
		ret.append(createHtmlListOfLibraries());
		ret.append(createHtmlListOfNonExistsTests());
		
		for (int suite = 0; suite < _suites.size(); suite++) {
			ret.append("\t\t\t<div class=\"testsuite\">");
			ret.append(System.lineSeparator());
			ret.append("\t\t\t\t<table>");
			ret.append(System.lineSeparator());
			
			ret.append("\t\t\t\t\t<tr>");
			ret.append(System.lineSeparator());
			ret.append(createHtmlTableHead(suite));
			ret.append("\t\t\t\t\t</tr>");
			ret.append(System.lineSeparator());
			
			for (int test = 0; test < _suites.get(suite).testCount(); test++) {
				ret.append("\t\t\t\t\t<tr>");
				ret.append(System.lineSeparator());
				ret.append(createHtmlColumn(suite, test, html));
				ret.append("\t\t\t\t\t</tr>");
				ret.append(System.lineSeparator());
			}
			
			ret.append("\t\t\t\t\t<tr>");
			ret.append(System.lineSeparator());
			ret.append(createHtmlTableFooter(suite));
			ret.append("\t\t\t\t\t</tr>");
			ret.append(System.lineSeparator());
			
			ret.append("\t\t\t\t</table>");
			ret.append(System.lineSeparator());
			ret.append("\t\t\t</div>");
			ret.append(System.lineSeparator());
		}
		
		ret.append(createResultTestRunnerTable());
		
		ret.append("\t\t</div>");
		ret.append(System.lineSeparator());
		
		return ret.toString();
	}
	
	/**
	 * Creates the library information for the classpath.
	 * 
	 * @return Library informatiosn for the classpath
	 */
	protected String createLibraryAsString() {
		StringBuilder ret = new StringBuilder();
		
		for (int lib = 0; lib < _library.size(); lib++) {
			if (lib > 0)
				ret.append(File.pathSeparator);
			
			if (_library.get(lib).getPath().isEmpty())
				ret.append(_config.getPathLibrary());
			else
				ret.append(_library.get(lib).getPath());
			ret.append(File.separator);
			ret.append(_library.get(lib).getFileName());
		}
		
		return ret.toString();
	}
	
	/**
	 * Creates from all necessary information to the classpath.
	 * 
	 * @return The created classpath.
	 */
	protected String createClasspath() {
		StringBuilder ret = new StringBuilder();
		
		boolean sep = false;
		boolean space = false;
		
		for (int path = 0; path < _classpath.size(); path++) {
			if (path > 0)
				ret.append(File.pathSeparator);
			ret.append(_classpath.get(path));
			sep = true;
			space = true;
		}
		
		for (int lib = 0; lib < _library.size(); lib++) {
			if (sep || (lib > 0))
				ret.append(File.pathSeparator);
			
			if (!_library.get(lib).getPath().isEmpty()) {
				ret.append(_library.get(lib).getPath());
				ret.append(File.separator);
			} else if(!_config.getPathLibrary().isEmpty()) {
				ret.append(_config.getPathLibrary());
				ret.append(File.separator);
			}
			
			ret.append(_library.get(lib).getFileName());
			space = true;
		}
		
		if (space)
			ret.append(" ");
		
		return ret.toString();
	}
	
	/**
	 * Creates the properties as string
	 * 
	 * @return Created property string
	 */
	protected String createProperty() {
		StringBuilder ret = new StringBuilder();
		
		for (int property = 0; property < _config.propertyCount(); property++) {
			if (property > 0)
				ret.append(" ");
			ret.append("-D");
			ret.append(_config.getProperty(property));
		}
		
		if (_config.propertyCount() > 0)
			ret.append(" ");
		
		return ret.toString();
	}
	
	/**
	 * Writes the lines from InputStream into a string.
	 * 
	 * @param stream InputStream to be returned as a string.
	 * 
	 * @return Then lines from InputStream.
	 */
	protected String inputStreamToString(InputStream stream) {
		StringBuilder ret = new StringBuilder();
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(stream));
			if (br.ready()) {
				String line;
				while ((line = br.readLine()) != null) {
					ret.append(line);
					ret.append(System.lineSeparator());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return replaceHtmlEntities(ret.toString());
	}
	
	/**
	 * Replaces the HTML entities for &lt; and &gt; by their names.
	 * 
	 * @param source String in the HTML entities should be replaced.
	 * 
	 * @return String in the HTML entities were replaced.
	 */
	protected String replaceHtmlEntities(String source) {
		return source.replaceAll("<", "&lt;").replaceAll(">", "&gt;")
				.replaceAll(System.lineSeparator(), "<br/>");
	}

	/**
	 * Adds a TestEventListener to the list of TestEventListener.
	 * 
	 * @param listener The new TestEventListener.
	 */
	public void addTestEventListener(TestEventListener listener) {
		_testEventListeners.add(listener);
	}
	
	/**
	 * Sets the new list with test event listeners
	 * 
	 * @param vector New list with test event listeners
	 */
	public void setTestEventListenerList(Vector<TestEventListener> vector) {
		_testEventListeners = vector;
	}
	
	/**
	 * Returns the list with test event listeners
	 * 
	 * @return List with test event listeners
	 */
	public Vector<TestEventListener> getTestEventListenerList() {
		return _testEventListeners;
	}
	
	/**
	 * Deletes a TestEventListener from the list of TestEventListener.
	 * 
	 * @param listener Listener to be deleted.
	 */
	public void removeTestEventListener(TestEventListener listener) {
		_testEventListeners.remove(listener);
	}

	/**
	 * Creates the event data with the given values and sends it to all listed
	 * TestEventListeners.
	 * 
	 * @param source Object that the event triggered.
	 * 
	 * @param pName The name of test suite package.
	 * 
	 * @param tName The name of the test.
	 * 
	 * @param suiteId The test suite id.
	 * 
	 * @param testId The test id.
	 * 
	 * @param result The result string.
	 */
	public void fireTestExecutedCompleted(Object source, String pName, 
			String tName, int suiteId, int testId, String result) {
		for (int i = 0; i < _testEventListeners.size(); i++)
			_testEventListeners.get(i).testExecutedCompleted(new TestEvent(
					source, pName, tName, suiteId, testId, result));
	}
	
	/**
	 * Execute the tests in list.
	 * 
	 * @param suite The actual test suite
	 * 
	 * @param test The actual test
	 */
	public void run(TestSuite suite, Test test, Thread thread) {
		// Create the name
		String name = suite.getPackage() + "." + test.getName();
		String result = new String();
		
		// Verify that file exists
		if (!suite.isExists() || !test.isExists()) {
			test.setExitStatus(100);
			System.out.print(name + " ");
			System.out.println(result = _bundle.getString("run_notFound"));
			fireTestExecutedCompleted(this, suite.getPackage(), test.getName(),
					suite.getId(), test.getId(), result);
			return;
		}
		
		// Should the test not be executed?
		if (!test.isExecuted()) {
			System.out.print(name + " ");
			System.out.println(result = _bundle.getString(
					"createHtmlColumn_noneExecuted"));
			fireTestExecutedCompleted(this, 
					suite.getPackage(), test.getName(), suite.getId(),
					test.getId(), result);
			return;
		}
		
		try {
			// Start time
			test.setStart(new Date().getTime());
			
			System.out.print(name + ": ");

			final Process p = Runtime.getRuntime().exec(exec(name, suite, test));

			Timer timer = new Timer((int)_config.getMaxDuration(),
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							p.destroy();
						}
			});

			int exit = EXIT_TERMINATED_EXEC;
			try {
				timer.start();
				exit = p.waitFor();
				timer.stop();
			} catch (InterruptedException e) {
				System.out.println(" --- Interrupt --- ");
				if (thread != null)
					thread.interrupt();
			}
			
			// End time
			test.setEnd(new Date().getTime());
			test.setExitStatus(exit);
			
			// Verifies exit status
			if (exit == EXIT_TERMINATED_EXEC) {
				result = _bundle.getString("run_terminated");
				test.setTerminated(true);
			} else if (exit == EXIT_OK)
				result = _bundle.getString("run_pass");
			else
				result = _bundle.getString("run_failure");
			
			// Result output on the console
			result += " (" + _bundle.getString("run_duration") + " " +
					String.valueOf(test.getDurationTime()) + " ms)";
			System.out.println(result);
			
			// Output from the console and the error
			test.setError(inputStreamToString(p.getErrorStream()));
			test.setStringConsole(inputStreamToString(p.getInputStream()));
			
			// Evaluation output
			evaluation(test);
		} catch (Exception e) {
			e.printStackTrace();
			test.setExitStatus(EXIT_ERROR);
		}
		
		fireTestExecutedCompleted(this, 
				suite.getPackage(), test.getName(), suite.getId(), test.getId(),
				result);
	}
	
	/**
	 * Generate the HTML table with result of all test runners.
	 * 
	 * @param runners List with test runner
	 * 
	 * @return HTML table with result of all test runners.
	 */
	public static String createHtmlAllResultTable(List<TestRunner> runners) {
		StringBuilder ret = new StringBuilder("\t\t\t\t<table>");
		ret.append(System.lineSeparator());
		
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_FILE);
		
		int tests_all = 0;
		int tests_terminated = 0;
		int tests_ignored = 0;
		int tests_executed = 0;
		int tests_not_exists = 0;
		long duration = 0;
		
		for (int runner = 0; runner < runners.size(); runner++) {
			for (int suite = 0; suite < runners.get(runner).testSuiteCount(); 
					suite++) {
				tests_all += runners.get(runner).getTestSuite(suite).testCount();
				for (int test = 0; test < runners.get(runner)
						.getTestSuite(suite).testCount(); test++) {
					duration += runners.get(runner).getTestSuite(suite)
							.getTest(test).getDurationTime();

					if (runners.get(runner).getTestSuite(suite).getTest(test)
							.isTerminated())
						tests_terminated++;
					
					if (runners.get(runner).getTestSuite(suite).getTest(test)
							.isExecuted() && runners.get(runner)
							.getTestSuite(suite).getTest(test).isExists())
						tests_executed++;
					else if (!runners.get(runner).getTestSuite(suite)
							.getTest(test).isExists())
						tests_not_exists++;
					else
						tests_ignored++;
				}
			}
		}

		
		String tr = "\t\t\t\t\t<tr>";
		String tr_end = "\t\t\t\t\t</tr>";
		String th = "\t\t\t\t\t\t<th>";
		String td = "\t\t\t\t\t\t<td>";
		
		ret.append(tr);
		ret.append(System.lineSeparator());
		
		ret.append(th);
		ret.append(bundle.getString("test_runner_result_name"));
		ret.append("</th>");
		ret.append(System.lineSeparator());
		
		ret.append(th);
		ret.append(bundle.getString("test_runner_result_tests"));
		ret.append("</th>");
		ret.append(System.lineSeparator());
		
		ret.append(th);
		ret.append(bundle.getString("test_runner_result_tests_executed"));
		ret.append("</th>");
		ret.append(System.lineSeparator());
		
		ret.append(th);
		ret.append(bundle.getString("test_runner_result_tests_terminated"));
		ret.append("</th>");
		ret.append(System.lineSeparator());
		
		ret.append(th);
		ret.append(bundle.getString("test_runner_result_tests_ignored"));
		ret.append("</th>");
		ret.append(System.lineSeparator());
		
		ret.append(th);
		ret.append(bundle.getString("test_runner_result_tests_not_exists"));
		ret.append("</th>");
		ret.append(System.lineSeparator());
		
		ret.append(th);
		ret.append(bundle.getString("test_runner_result_duration"));
		ret.append("</th>");
		ret.append(System.lineSeparator());

		ret.append(tr_end);
		ret.append(System.lineSeparator());
		
		ret.append(tr);
		ret.append(System.lineSeparator());
		
		ret.append(td);
		ret.append(bundle.getString("test_runner_result_all"));
		ret.append("</td>");
		ret.append(System.lineSeparator());
		
		ret.append(td);
		ret.append(String.valueOf(tests_all));
		ret.append("</td>");
		ret.append(System.lineSeparator());
		
		ret.append(td);
		ret.append(String.valueOf(tests_executed));
		ret.append("</td>");
		ret.append(System.lineSeparator());
		
		ret.append(td);
		ret.append(String.valueOf(tests_terminated));
		ret.append("</td>");
		ret.append(System.lineSeparator());
		
		ret.append(td);
		ret.append(String.valueOf(tests_ignored));
		ret.append("</td>");
		ret.append(System.lineSeparator());
		
		ret.append(td);
		ret.append(String.valueOf(tests_not_exists));
		ret.append("</td>");
		ret.append(System.lineSeparator());
		
		// OPT Insert in HelperCalendar
		DateFormat df = new SimpleDateFormat("HH:mm:ss.SSS");
		ret.append(td);
		ret.append(df.format(new Date(duration - 3600000)));
		ret.append("</td>");
		ret.append(System.lineSeparator());

		ret.append(tr_end);
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t</table>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t</table>");
		ret.append(System.lineSeparator());
		
		return ret.toString();
		
	}
	
	/**
	 * Called to evaluation error or console output.
	 * 
	 * If the to be evaluated, so this method must be overridden.
	 * 
	 * @param test The actual test
	 */
	protected void evaluation(Test test) {
		// Empty method
	}
	
	/**
	 * Called to insert a new test class in the list.
	 * 
	 * @param name Name of the new test
	 * 
	 * @param id ID of the new test 
	 */
	public abstract org.testsuite.data.Test newTest(String name, int id);
	
	/**
	 * Called to create the column headings for the HTML table.
	 */
	protected abstract String createHtmlTableHead(int suite);
	
	/**
	 * Called to generate the columns for a row in the HTML table.
	 * @throws IOException 
	 */
	protected abstract String createHtmlColumn(int suite, int test,
			HtmlOut html) throws IOException;
	
	/**
	 * Called to generate the footer for the HTML table
	 */
	protected abstract String createHtmlTableFooter(int suite);
	
	/**
	 * Called to generate the result of all tests in test runner
	 */
	protected abstract String createResultTestRunnerTable();
	
	/**
	 * String containing the execute command.
	 * 
	 * @param test Name of the test class or name of file
	 */
	protected abstract String exec(String name, TestSuite suite, Test test);
}
