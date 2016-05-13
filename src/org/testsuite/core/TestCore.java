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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.testsuite.data.Config;
import org.testsuite.data.Fit;
import org.testsuite.data.Junit;
import org.testsuite.data.Test;
import org.testsuite.data.TestSuite;

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
	 * Saves the configuration.
	 */
	private Config _config;
	
	/**
	 * Saves the list of classes TestRunner.
	 */
	private List<TestRunner> _testRunner;

	/**
	 * Initialize the test management
	 */
	public TestCore() {
		_config = new Config();
		_testRunner = new ArrayList<TestRunner>();
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new Date());
		DecimalFormat df = new DecimalFormat("00");
		_config.setPathSuitesResult(
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
		
		ConfigParser parser = new ConfigParser(_config, fileName);
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
		for (int runner = 0; runner < _testRunner.size(); runner++)
			_testRunner.get(runner).run();
	}
	
	/**
	 * Creates the HTML file containing the results
	 */
	public void createResultHtml()  {
		if (_config.isCreateHtml()) {
			String htmlFile = _config.getPathResult() + File.separator;
			
			// FIXME In Tests einbinden
			File file = new File(htmlFile);
			if (!file.exists())
				file.mkdirs();
			
			htmlFile += "Ergebnisse_" + _config.getPathSuitesResult() + ".html";
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
				
				html.htmlEnd();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
