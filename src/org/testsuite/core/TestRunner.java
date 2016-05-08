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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
	 * Saves the file extension.
	 */
	protected String _fileExtension;
	
	/**
	 * Initialis the data of the class.
	 * 
	 * @param extension The extension of the test file.
	 */
	public TestRunner(String extension) {
		_suites = new ArrayList<TestSuite>();
		_fileExtension = extension;
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
	 * Verifies that the directories and files Test exist.
	 */
	public void checkFileExists() {
		for (int suite = 0; suite < _suites.size(); suite++) {
			// Überprüft, ob das Verzeichnis existiert
			File path = new File(_suites.get(suite).getPackage());
			boolean pathExists = false;
			if (path.exists())
				pathExists = true;
			_suites.get(suite).setExists(pathExists);
			
			for (int test = 0; test < _suites.get(suite).testCount(); test++) {
				// Überprüft, ob das Verzeichnis existiert
				boolean fileExists = false;
				if (pathExists) {
					String fileName = _suites.get(suite).getPackage() +
							File.separator +
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
	 * Called to start the stored tests.
	 */
	public abstract void run();
	
	/**
	 * Called to generate the HTML code from the test results.
	 * 
	 * @param html Class, which helps to generate the HTML code.
	 */
	public abstract void createHtml(HtmlOut html);
}
