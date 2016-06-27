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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.xml.stream.XMLStreamException;

import org.testsuite.checksource.html.HtmlDeprecated;
import org.testsuite.checksource.html.HtmlNoneExistFile;
import org.testsuite.checksource.html.HtmlNoneTestedMethods;
import org.testsuite.checksource.html.HtmlNoneTestedSourceFiles;
import org.testsuite.checksource.html.HtmlOutOverview;
import org.testsuite.checksource.html.HtmlTodo;
import org.testsuite.data.Config;

/**
 * Implements the management of tests.
 * 
 * It is read in the configuration file. If no error has occurred, the
 * individual tests are performed. Finally, an HTML page is embarrassed, in
 * which the results of each test are summarized.
 * 
 * @author René Majewski
 *
 * @version 0.2
 */
public class TestCore {
	/**
	 * The name of resource bundle file
	 */
	public static final String BUNDLE_FILE = 
			"resources.lang.org.testsuite.core.TestCore";
	
	/**
	 * Saves the configuration.
	 * 
	 * @deprecated Use {@link org.testsuite.data.Config#getInstance()}
	 */
	@SuppressWarnings("unused")
	private Config _config;
	
	/**
	 * Saves the list of classes TestRunner.
	 */
	private List<TestRunner> _testRunner;
	
	/**
	 * Saves the instance of resource bundle
	 */
	private ResourceBundle _bundle;

	/**
	 * Initialize the test management
	 */
	public TestCore() {
		try {
			_bundle = ResourceBundle.getBundle(BUNDLE_FILE);
		} catch (MissingResourceException e) {
			e.printStackTrace();
		}
		
		_config = Config.getInstance();
		_testRunner = new ArrayList<TestRunner>();
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new Date());
		DecimalFormat df = new DecimalFormat("00");
		Config.getInstance().setPathSuitesResult(
				df.format(gc.get(GregorianCalendar.YEAR)) +
				df.format(gc.get(GregorianCalendar.MONTH) + 1) +
				df.format(gc.get(GregorianCalendar.DAY_OF_MONTH)) +
				df.format(gc.get(GregorianCalendar.HOUR_OF_DAY)) +
				df.format(gc.get(GregorianCalendar.MINUTE)) +
				df.format(gc.get(GregorianCalendar.SECOND)));
	}

	/**
	 * Reads the specified configuration file.
	 * 
	 * @param fileName Name of the configuration file
	 * 
	 * @return Was the configuration file is read correctly?
	 */
	public boolean readConfig(String fileName) 
			throws XMLStreamException, FileNotFoundException {
		if ((fileName == null) || fileName.isEmpty())
			throw new IllegalArgumentException();
		
		ConfigParser parser = new ConfigParser(fileName);
		boolean ret = parser.parse();
		
		if (ret)
			_testRunner = parser.getTestRunnerList();
		
		return ret;
	}
	
	/**
	 * Checks if the files exist
	 */
	public void checkFileExists() {
		for (int runner = 0; runner < _testRunner.size(); runner++)
			_testRunner.get(runner).checkFileExists();
	}
	
	/**
	 * Executes the individual tests
	 */
	public void run() {
		for (int runner = 0; runner < _testRunner.size(); runner++) {
			for (int suite = 0; suite < _testRunner.get(runner).testSuiteCount(); suite++) {
				System.out.println();
				System.out.print(
						_testRunner.get(runner).getTestSuite(suite).getName());
				System.out.println(":");
				for (int test = 0; test < _testRunner.get(runner).getTestSuite(suite).testCount(); test++) {
					_testRunner.get(runner).run(
							_testRunner.get(runner).getTestSuite(suite),
							_testRunner.get(runner).getTestSuite(suite)
								.getTest(test), null);
					_testRunner.get(runner).runCheckSource(
							_testRunner.get(runner).getTestSuite(suite),
							_testRunner.get(runner).getTestSuite(suite)
								.getTest(test));
				} // for test
			} // for suite
		} // for runner
		
		// Create HTML output files
		HtmlTodo.getInstance().createHtml();
		HtmlNoneExistFile.getInstance().createHtml();
		HtmlNoneTestedMethods.getInstance().createHtml();
		HtmlNoneTestedSourceFiles.getInstance().createHtml();
		HtmlDeprecated.getInstance().createHtml();
		HtmlOutOverview.getInstance().createHtml();
	}
	
	/**
	 * Executes the check source
	 */
	public void checkSource() {
		for (int runner = 0; runner < _testRunner.size(); runner++) {
			for (int suite = 0; suite < _testRunner.get(runner).testSuiteCount(); suite++) {
				System.out.println();
				System.out.print(
						_testRunner.get(runner).getTestSuite(suite).getName());
				System.out.println(":");
				for (int test = 0; test < _testRunner.get(runner).getTestSuite(suite).testCount(); test++) {
					_testRunner.get(runner).runCheckSource(
							_testRunner.get(runner).getTestSuite(suite),
							_testRunner.get(runner).getTestSuite(suite)
								.getTest(test));
				} // for test
			} // for suite
		} // for runner
		HtmlOutOverview.getInstance().createHtml();
	}
	
	/**
	 * Creates the HTML file containing the results
	 */
	public void createResultHtml()  {
		if (Config.getInstance().isCreateHtml()) {
			String htmlFile = Config.getInstance().getPathResult() + File.separator;
			
			File file = new File(htmlFile);
			if (!file.exists())
				file.mkdirs();
			
			htmlFile += _bundle.getString("html_result") + "_" +
					Config.getInstance().getPathSuitesResult() + ".html";
			try {
				
				HtmlOut html = new HtmlOut(htmlFile);
				html.htmlHead();
				
				for (int runner = 0; runner < _testRunner.size(); runner++) {
					boolean line = true;
					if (runner == 0)
						line = false;
					html.writeHtml(_testRunner.get(runner).createHtml(html, 
							line));
				}
				
				html.writeHtml(TestRunner.createHtmlAllResultTable(_testRunner));
				
				html.htmlEnd();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
