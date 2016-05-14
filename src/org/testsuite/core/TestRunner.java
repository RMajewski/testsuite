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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.testsuite.data.Config;
import org.testsuite.data.Library;
import org.testsuite.data.TestSuite;

/**
 * From this class all TestRunner be derived.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public abstract class TestRunner {
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
	 * Initialis the data of the class.
	 * 
	 * @param config The configuration
	 */
	public TestRunner(Config config) {
		try {
			_bundle = ResourceBundle.getBundle(
					"resources.lang.org.testsuite.core.TestRunner");
		} catch (MissingResourceException e) {
			e.printStackTrace();
		}
		_suites = new ArrayList<TestSuite>();
		_library = new ArrayList<Library>();
		_classpath = new ArrayList<String>();
		_fileExtension = new String();
		_config = config;
		_description = new String();
		_lastSuiteId = -1;
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
	 * Adds a test suite to the list.
	 * 
	 * @param suite Test Suite, which is to be added to the list.
	 */
	public void addTestSuite(TestSuite suite) {
		_suites.add(suite);
	}
	
	// FIXME Test hinzufügen und javadoc erstellen
	public TestSuite getTestSuite(int index) {
		return _suites.get(index);
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
	
	public Library getLibrary(int index) {
		return _library.get(index);
		
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

		if (!_description.isEmpty()) {
			if (line) {
				ret.append("\t\t\t<hr/>");
				ret.append(System.lineSeparator());
			}
			
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
			
			ret.append("\t\t\t\t</table>");
			ret.append(System.lineSeparator());
			ret.append("\t\t\t</div>");
			ret.append(System.lineSeparator());
		}
		
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
			String line;
			while ((line = br.readLine()) != null) {
				ret.append(line);
				ret.append("<br/>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret.toString().replaceAll("<", "&lt;").replaceAll(">", "&gt;");
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
	 * Called to start the stored tests.
	 */
	public abstract void run();
	
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
}
