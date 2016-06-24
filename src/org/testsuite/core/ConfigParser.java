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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.testsuite.data.Config;
import org.testsuite.data.Library;
import org.testsuite.data.TestSuite;

/**
 * Parse the configuration file.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class ConfigParser {
	/**
	 * Saves the configuration.
	 * 
	 * @deprecated Use {@link org.testsuite.data.Config#getInstance()}
	 */
	private Config _config;
	
	/**
	 * Saves the name of configuration file.
	 */
	private String _configFile;
	
	/**
	 * Saves the list of classes TestRunner.
	 */
	private List<TestRunner> _testRunner;
	
	/**
	 * Initialize the data of the class.
	 * 
	 * @param config The global configuration
	 * 
	 * @param configFile The name of configuration file
	 * 
	 * @deprecated Use {@link #ConfigParser(String)}
	 */
	public ConfigParser(Config config, String configFile) {
	}
	
	/**
	 * Initialize the data of the class.
	 * 
	 * @param configFile The name of configuration file
	 */ 
	public ConfigParser(String configFile) {
		_config = Config.getInstance();
		_configFile = configFile;
		_testRunner = new ArrayList<TestRunner>();
	}
	
	/**
	 * Parse the configuration file.
	 * 
	 * @return True if the configuration file has been successfully processed.
	 * False if errors have occurred.
	 */
	public boolean parse() {
		// Überprüfen, ob die Datei existiert
		File file = new File(_configFile);
		if (!file.exists())
			return false;
		
		try {
			InputStream is = new FileInputStream(_configFile);
			XMLEventReader parser = 
					XMLInputFactory.newInstance().createXMLEventReader(is);
			
			String data = new String();
			boolean config = false;
			boolean stylesheet = false;
			boolean javascript = false;
			boolean testGroup = false;
			boolean libraries = false;
			boolean classPath = false;
			boolean testSuite = false;
			boolean todo = false;
			TestRunner runner = null;
			Library library = null;
			TestSuite suite = null;
			int suiteId = 0;
			int testId = 0;
			boolean executed = true;
			boolean jvm = true;
			String checkSource = new String();
			
			while(parser.hasNext()) {
				XMLEvent event = parser.nextEvent();
				switch (event.getEventType()) {
					case XMLStreamConstants.END_DOCUMENT:
						parser.close();
						break;
						
					case XMLStreamConstants.START_ELEMENT:
						StartElement element = event.asStartElement();
						data = new String();
						switch (element.getName().getLocalPart()) {
							case "config":
								config = true;
								break;
								
							case "noneTestedList":
								Attribute atr = (Attribute)element
									.getAttributes().next();
								if (atr.getName().toString().equals("path"))
									Config.getInstance().setNoneListedPath(
											atr.getValue());
								break;
								
							case "testGroup":
								testGroup = true;
								break;
								
							case "libraries":
								libraries = true;
								break;
								
							case "library":
								library = new Library();
								for (Iterator<?> atrs = element.getAttributes(); 
										atrs.hasNext();) {
									atr = (Attribute)atrs.next();
									switch (atr.getName().toString()) {
										case "version":
											library.setVersion(atr.getValue());
											break;
											
										case "name":
											library.setName(atr.getValue());
											break;
											
										case "path":
											library.setPath(atr.getValue());
											break;
									}
								}
								break;
								
							case "classpath":
								classPath = true;
								break;
								
							case "testSuite":
								testSuite = true;
								suite = new TestSuite();
								suite.setId(suiteId++);
								testId = 0;
								break;
								
							case "test":
								for (Iterator<?> atrs = element.getAttributes();
										atrs.hasNext();) {
									atr = (Attribute) atrs.next();
									
									if (atr.getName().toString().equals(
											"executed"))
										executed = Boolean.parseBoolean(
												atr.getValue().toString());
									
									else if (atr.getName().toString().equals(
											"jvm"))
										jvm = Boolean.parseBoolean(
												atr.getValue().toString());
									else if (atr.getName().toString().equals(
											"checkSource"))
										checkSource = atr.getValue();
								}
								break;
							
							case "javascript":
								javascript = true;
								break;
								
							case "stylesheet":
								stylesheet = true;
								break;
								
							case "todo":
								todo = true;
								break;
						}
						break;
						
					case XMLStreamConstants.CHARACTERS:
						Characters characters = event.asCharacters();
						if (!characters.isWhiteSpace())
							data += characters.getData();
						break;
						
					case XMLStreamConstants.END_ELEMENT:
						EndElement eelement = event.asEndElement();
						switch(eelement.getName().getLocalPart()) {
							case "config":
								config = false;
								break;
								
							case "javascript":
								javascript = false;
								break;
								
							case "stylesheet":
								stylesheet = false;
								break;
								
							case "todo":
								todo = false;
								break;
								
							case "testGroup":
								if (testGroup && (runner != null))
									_testRunner.add(runner);
								testGroup = false;
								runner = null;
								break;
								
							// The configuration
							case "resultPath":
								if (config)
									Config.getInstance().setPathResult(data);
								break;
								
							case "srcPath":
								if (config)
									Config.getInstance().setPathSrc(data);
								break;
								
							case "libPath":
								if (config)
									Config.getInstance().setPathLibrary(data);
								break;
								
							case "maxDuration":
								if (config)
									Config.getInstance().setMaxDuration(Long.parseLong(data));
								break;
								
							case "htmlOut":
								if (config)
									Config.getInstance().setCreateHtml(
										Boolean.parseBoolean(data));
								break;
								
							case "noneTestedList":
								if (config)
									Config.getInstance().setListNoneTestedFiles(
											Boolean.parseBoolean(data));
								break;
								
							case "lineWidth":
								if (config)
									Config.getInstance().setLineWidth(
											Integer.parseInt(data));
								break;
								
							case "property":
								if (config)
									Config.getInstance().addProperty(data);
								break;
								
							case "file":
								if (javascript && (Config.getInstance() != null))
									Config.getInstance().addJavascriptFile(data);
								else if (stylesheet && (Config.getInstance() != null))
									Config.getInstance().addStylesheetFile(data);
								break;
								
							// The TestRunner
							case "testRunner":
								if (testGroup && (runner == null))
									runner = (TestRunner)getClass()
												.getClassLoader()
												.loadClass(data).
												getConstructor(Config.class).
												newInstance(Config.getInstance());
								break;
								
							case "extension":
								if (testGroup && (runner != null))
									runner.setFileExtension(data);
								break;
								
							case "description":
								if (testGroup && (runner != null))
									runner.setDescription(data);
								break;
								
							case "library":
								if (libraries) {
									if (library != null) {
										library.setFileName(data);
										if (runner != null) 
											runner.addLibrary(library);
									}
									library = null;
								}
								break;
								
							case "libraries":
								libraries = false;
								break;
								
							case "classpath":
								classPath = false;
								break;
								
							case "path":
								if (classPath && !config && (runner != null))
									runner.addClassPath(data);
								else if (classPath && config)
									Config.getInstance().addClassPath(data);
								break;
								
							// The TestSuite
							case "testSuite":
								if (testSuite && (runner != null) && 
										(suite != null))
									runner.addTestSuite(suite);
								suite = null;
								testSuite = false;
								break;
								
							case "name":
								if (testSuite && (suite != null))
									suite.setName(data);
								else if (todo && (Config.getInstance() != null))
									Config.getInstance().addToDoWord(data);
								break;
								
							case "package":
								if (testSuite && (suite != null))
									suite.setPackage(data);
								break;
								
							// The Test
							case "test":
								if (testSuite && (suite != null) &&
										(runner != null)) {
									org.testsuite.data.Test test = 
											runner.newTest(data, testId++);
									test.setExecuted(executed);
									test.setJvm(jvm);
									test.setCheckSource(checkSource);
									suite.addTest(test);
								}
								jvm = true;
								executed = true;
								checkSource = new String();
								break;
						}
						break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		// Da bisher nicht abgebrochen wurde, true zurpck geben
		return true;
	}
	
	/**
	 * Returns the configuration.
	 * 
	 * @return Configuration
	 * 
	 * @deprecated Use {@link org.testsuite.data.Config#getInstance()}.
	 */
	public Config getConfig() {
		return _config;
	}
	
	/**
	 * Sets the configuration.
	 * 
	 * @param config The new configuration.
	 * 
	 * @deprecated Use {@link org.testsuite.data.Config#getInstance()}.
	 */
	public void setConfig(Config config) {
		if (config == null)
			throw new IllegalArgumentException();
		_config = config;
	}
	
	/**
	 * Returns the name of configuration file.
	 * 
	 * @return The name of configuration file.
	 */
	public String getConfigFile() {
		return _configFile;
	}
	
	/**
	 * Sets the name of configuration file.
	 * 
	 * @param configFile The new name of configuration file.
	 */
	public void setConfigFile(String configFile) {
		if ((configFile == null) || configFile.isEmpty())
			throw new IllegalArgumentException();
		_configFile = configFile;
	}
	
	/**
	 * Returns the list of classes TestRunner.
	 * 
	 * @return List of TestRunner.
	 */
	public List<TestRunner> getTestRunnerList() {
		return _testRunner;
	}
}
