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
 * @version 0.1
 */
public class TestCore {
	/**
	 * Saves the configuration.
	 */
	private Config _config;

	/**
	 * Initialize the test management
	 */
	public TestCore() {
		_config = new Config();
		
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
	 * @param config Name of the configuration file
	 * 
	 * @return Was the configuration file is read correctly?
	 */
	public boolean readConfig(String config) 
			throws XMLStreamException, FileNotFoundException{
		// Überprüfen, ob eine Konfigurationsdatei angegeben wurde.
		if ((config == null) || config.isEmpty())
			throw new IllegalArgumentException();
			
		// Überprüfen, ob die config-Datei existiert
		File configFile = new File(config);
		if (!configFile.exists()) {
			System.out.println("Konfigurations-Datei (" + config + 
					") existiert nicht.");
			return false;
		}
	
		// InputStream
		InputStream stream = new FileInputStream(configFile);
		
		// XMl-Factory
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader parser = factory.createXMLStreamReader(stream);
		
		// Daten vorbereiten
		Test test = new Test();
		TestSuite suite = new TestSuite();
		int type = -1;
		String str = new String();
		
		// XML-Dokument einlesen
		while (parser.hasNext()) {
			switch (parser.getEventType()) {
				// Dokument ist zu Ende
				case XMLStreamConstants.END_DOCUMENT:
					parser.close();
					break;
					
				// Start eines neuen Elementes
				case XMLStreamConstants.START_ELEMENT:
					// Welches Element?
					switch (parser.getLocalName()) {
						// Konfiguration
						case "config":
							type = 0;
							break;
							
						// GUI-Tests
						case "guiTests":
							type = 1;
							break;
							
						// junit-Tests
						case "junitTests":
							type = 2;
							break;
							
						// Fit-Tests
						case "fitTests":
							type = 3;
							break;
							
						// Neue Test-Suite
						case "testSuite":
							if (type == 1)
								suite = new TestSuite();
							else if (type == 2)
								//Junit
								System.out.println("Junit");
							else if (type == 3)
								// Fit
								System.out.println("Fit");
							break;
							
						// Name
						case "name":
							break;
							
						// Package
						case "package":
							break;
							
						// Neuer Test
						case "test":
							if (type == 1)
								test = new Test();
							else if (type == 2)
								test = new Junit();
							else if (type == 3)
								test = new Fit();
							break;
					}
					break;
					
				// Ende eines Elementes
				case XMLStreamConstants.END_ELEMENT:
					switch(parser.getLocalName()) {
						// Suite
						case "testSuite":
							if (type == 1)
								//_gui.add(suite);
								System.out.println("jemmy");
							else if (type == 2)
								//JUNIT
								System.out.println("Junit");
							else if (type == 3)
								//Fit
								System.out.println("Fit");
							break;
							
						// Name
						case "name":
							suite.setName(str);
							break;
							
						// Package
						case "package":
							suite.setPackage(str);
							break;
							
						// Test
						case "test":
							test.setName(str);
							suite.addTest(test);
							break;
							
						// Result-Pfad
						case "resultPath":
							_config.setPathResult(str);
							break;
							
						// Source-Pfad
						case "srcPath":
							_config.setPathSrc(str);
							break;
							
						// Bibliotheks-Pfad
						case "bibPath":
							_config.setPathLibrary(str);
							break;
					}
					break;
					
				// Zeichen
				case XMLStreamConstants.CHARACTERS:
					str = parser.getText();
					break;
			}
			
			// Nextes Element
			parser.next();
		}
		
		// Wurde die Konfigurations-Datei richtig gelesen?
		return true;
	}
	
	/**
	 * Checks if the files exist
	 */
	public void checkFileExists() {
	}

	/**
	 * Passes through the specified list and checks whether the files exist
	 * 
	 * @param list List that is to be executed.
	 */
	private void listCheckFiles(List<TestSuite> list) {
		for (int i = 0; i < list.size(); i++)
			suiteCheckFiles(list.get(i), "java");
	}
	
	/**
	 * Passes through the specified test suite and checks whether the files
	 * exist.
	 * 
	 * @param suite Test suite, which is to be executed.
	 * 
	 * @param extension File extension of the file
	 */
	private void suiteCheckFiles(TestSuite suite, String extension) {
		suite.setExists(fileExists(suite.getPackage().replaceAll("\\.", "/")));
		String path = suite.getPackage();
		for (int i = 0; i < suite.testCount(); i++)
				suite.getTest(i).setExists(fileExists(composeFileName(path,
						suite.getTest(i).getName(), extension)));
	}
	
	/**
	 * Sets the full file name with path together.
	 * 
	 * @param path Directory where the file is located
	 * 
	 * @param name Name of file
	 * 
	 * @param extension Extension of file
	 * 
	 * @return Directory and file as a string
	 */
	private String composeFileName(String path, String name, String extension) {
		return new String(path + "." + name).replaceAll("\\.", "/") + 
				"." + extension;
	}
	
	/**
	 * Checks whether the specified file exists.
	 * 
	 * @param file Name of File
	 * 
	 * @return If the file exists?
	 */
	private boolean fileExists(String file) {
		File f = new File(file);
		if (f.exists())
			return true;
		return false;
	}
	
	/**
	 * Executes the individual tests
	 */
	public void run() {
	}
	
	/**
	 * Creates the HTML file containing the results
	 */
	public void createResultHtml()  {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new Date());
		
		String htmlFile = _config.getPathResult() + File.separator + 
				"Ergebnisse_" + _config.getPathSuitesResult() + ".html";
		try {
			HtmlOut html = new HtmlOut(htmlFile);
			html.htmlHead();
			
			html.guiHead();
			
			html.junitHead();
			
			html.fitHead();
			
			html.htmlEnd();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
