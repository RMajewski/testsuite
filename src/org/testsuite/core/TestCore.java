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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.testsuite.data.Fit;
import org.testsuite.data.FitSuite;
import org.testsuite.data.Junit;
import org.testsuite.data.JunitSuite;
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
	 * Saves the GUI tests
	 */
	private List<TestSuite> _gui;
	
	/**
	 * Saves the junit tests
	 */
	private List<JunitSuite> _junit;
	
	/**
	 * Saves the Fit tests
	 */
	private List<FitSuite> _fit;
	
	/**
	 * Saves the result directory for Fit Tests
	 */
	private String _fitResult;
	
	/**
	 * Saves the path for the results
	 */
	private String _resultPath;
	
	/**
	 * Saves the directory for the source code
	 */
	private String _srcPath;
	
	/**
	 * Saves the directory to the libraries
	 */
	private String _bibPath;

	/**
	 * Initialize the test management
	 */
	public TestCore() {
		// Listen initalisieren
		_gui = new ArrayList<TestSuite>();
		_junit = new ArrayList<JunitSuite>();
		_fit = new ArrayList<FitSuite>();
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new Date());
		DecimalFormat df = new DecimalFormat("00");
		_fitResult = df.format(gc.get(GregorianCalendar.YEAR)) +
				df.format(gc.get(GregorianCalendar.MONTH) + 1) +
				df.format(gc.get(GregorianCalendar.DAY_OF_MONTH)) +
				df.format(gc.get(GregorianCalendar.HOUR_OF_DAY)) +
				df.format(gc.get(GregorianCalendar.MINUTE)) +
				df.format(gc.get(GregorianCalendar.SECOND));
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
								suite = new JunitSuite();
							else if (type == 3)
								suite = new FitSuite();
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
								_gui.add(suite);
							else if (type == 2)
								_junit.add((JunitSuite)suite);
							else if (type == 3)
								_fit.add((FitSuite)suite);
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
							_resultPath = str;
							break;
							
						// Source-Pfad
						case "srcPath":
							_srcPath = str;
							break;
							
						// Bibliotheks-Pfad
						case "bibPath":
							_bibPath = str;
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
		listCheckFiles(_gui);
		listCheckJunitFiles();
		listCheckFitFiles();
	}
	
	/**
	 * Passes through the specified list and checks whether the files exist.
	 */
	private void listCheckFitFiles() {
		for (int i = 0; i < _fit.size(); i++)
			suiteCheckFiles(_fit.get(i), "fit");
	}
	
	/**
	 * Cycles through junit suite list and checks whether the files exist.
	 */
	private void listCheckJunitFiles() {
		for (int i = 0; i < _junit.size(); i++)
			suiteCheckFiles(_junit.get(i), "java");
		
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
		runGui();
		runJunit();
		runFit();
	}
	
	/**
	 * Executes the individual GUI tests
	 */
	private void runGui() {
		for (int suite = 0; suite < _gui.size(); suite++) {
			// Test-Suite Name
			System.out.println(_gui.get(suite).getName());
			
			// gui-Tests ausführen
			for (int test = 0; test < _gui.get(suite).testCount(); test++) {
				String name = _gui.get(suite).getPackage() + "." +
						_gui.get(suite).getTest(test).getName();
				try {
					_gui.get(suite).getTest(test).setStart(
							new Date().getTime());

					System.out.print(name + ": ");
					Process p = Runtime.getRuntime().exec("java -cp " +
							System.getProperty("java.class.path")+
							" -Dtesting=true " + name);
					int exit = p.waitFor();
					
					// Ausgabe wie der Test verlaufen ist
					_gui.get(suite).getTest(test).setEnd(new Date().getTime());
					_gui.get(suite).getTest(test).setExitStatus(exit);
					if (exit == 0)
						System.out.print("wurde erfolgreich ausgeführt");
					else
						System.out.print("weißt Fehler auf");
					
					System.out.print(" (Dauer des Tests: ");
					System.out.print(String.valueOf(
							_gui.get(suite).getTest(test).getDurationTime()));
					System.out.println(" ms)");
					
					// Console-Ausgabe und Error-Ausgabe speichern
					_gui.get(suite).getTest(test).setError(p.getErrorStream());
					_gui.get(suite).getTest(test).setIn(p.getInputStream());
				} catch (IOException e) {
					e.printStackTrace();
					_gui.get(suite).getTest(test).setExitStatus(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
					_gui.get(suite).getTest(test).setExitStatus(100);
				}
				
			}
		}
	}
	
	/**
	 * Performs single junit test from
	 */
	private void runJunit() {
		for (int suite = 0; suite < _junit.size(); suite++) {
			// Test-Suite Name
			System.out.println(_junit.get(suite).getName());
			
			// juni-Tests ausführen
			for (int test = 0; test < _junit.get(suite).testCount(); test++) {
				String name = _junit.get(suite).getPackage() + "." +
						_junit.get(suite).getTest(test).getName();
				
				try {
					_junit.get(suite).getTest(test).setStart(
							new Date().getTime());

					System.out.print(name + ": ");
					Process p = Runtime.getRuntime().exec("java -cp " +
							System.getProperty("java.class.path")+
							" -Dtesting=true org.junit.runner.JUnitCore " +
							name);
					
					int exit = p.waitFor();
					_junit.get(suite).getTest(test).setExitStatus(exit);
					
					// Ausgabe wie der Test verlaufen ist
					_junit.get(suite).getTest(test).setEnd(
							new Date().getTime());

					if (exit == 0)
						System.out.print("wurde erfolgreich ausgeführt");
					else
						System.out.print("weißt Fehler auf");

					System.out.print(" (Dauer des Tests: ");
					System.out.print(String.valueOf(
							_junit.get(suite).getTest(test).getDurationTime()));
					System.out.println(" ms)");
					
					// Console-Ausgabe und Error-Ausgabe speichern
					_junit.get(suite).getTest(test).setError(p.getErrorStream());
					_junit.get(suite).getTest(test).setIn(p.getInputStream());

					InputStream is = p.getInputStream();
					is.mark(is.available());
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					String line;
					while ((line = br.readLine()) != null) {
						if (line.indexOf("OK (") > -1) {
							String ok = new String("OK (");
							
							String tmp = line.substring(ok.length(),
									line.indexOf(" tests)"));
							_junit.get(suite).getTest(test).setOk(
									Integer.valueOf(tmp));
						} else if (line.indexOf("Tests run") > -1) {
							String ok = new String("Tests run: ");
							String fail = new String(",  Failures: ");
							int indexOk = ok.length();
							int indexFail = line.indexOf(fail);

							_junit.get(suite).getTest(test).setOk(
									Integer.valueOf(line.substring(indexOk, 
											indexFail)));
							
							indexFail += fail.length();
							_junit.get(suite).getTest(test).setFail(
									Integer.valueOf(line.substring(indexFail)));
						}
					}
					is.reset();
				} catch (IOException e) {
					e.printStackTrace();
					_junit.get(suite).getTest(test).setExitStatus(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
					_junit.get(suite).getTest(test).setExitStatus(100);
				}
			} // for über alle Tests
			
			System.out.println();
		} // for über alle Test-Suites
	}
	
	/**
	 * Performs single Fit test from
	 */
	private void runFit() {
		for (int suite = 0; suite < _fit.size(); suite++) {
			// Test-Suite Name
			System.out.println(_fit.get(suite).getName());
			
			for (int test = 0; test < _fit.get(suite).testCount(); test++) {
				// Name der Fit-Datei
				String fit = composeFileName(_srcPath + "." + 
						_fit.get(suite).getPackage(), 
						_fit.get(suite).getTest(test).getName(), "fit");
				
				// Überprüfen, ob die Datei existiert
				File f = new File(fit);
				if (!f.exists() || f.isDirectory()) {
					_fit.get(suite).getTest(test).setExists(false);
					_fit.get(suite).getTest(test).setExitStatus(100);
					System.out.println("Die Fit-Datei: '" + fit +
							"' existiert nicht oder ist ein Verzeichnis");
					continue;
				}
				_fit.get(suite).getTest(test).setExists(true);
				
				// Überprüfen ob das Result-Verzeichnis existiert
				String resultPath = _resultPath + File.separator + _fitResult + 
						File.separator + 
						_fit.get(suite).getPackage().replaceAll("\\.", 
								File.separator);
				File r = new File(resultPath);
				if (!r.exists()) {
					// Verzeichnis anlegen
					r.mkdirs();
				}
				
				// Name der Result-Datei ermitteln und als Endung html
				String resultFileName = resultPath + File.separator + 
						_fit.get(suite).getTest(test).getName() + ".html";
				
				// Ausführen
				try {
					_fit.get(suite).getTest(test).setStart(new Date().getTime());

					System.out.print(fit + ": ");
					String exec = "java -cp " +
							"bin:resource:" + _bibPath + "/fit.jar:" + _bibPath +
							"/jemmy.jar:" + _bibPath +
							"/sqlite-jdbc-3.8.11.2.jar -Dtesting=true " +
							"fit.FileRunner " + fit + " " + resultFileName;
					Process p = Runtime.getRuntime().exec(exec);
					int exit = p.waitFor();
					
					// Endzeit ermitteln
					_fit.get(suite).getTest(test).setEnd(new Date().getTime());
					
					// Überpüfen ob Exit-Status 0 ist
					_fit.get(suite).getTest(test).setExitStatus(exit);
					if (exit == 0)
						System.out.print("wurde erfolgreich ausgeführt.");
					else
						System.out.print("weißt einen Fehler auf.");
					
					
					// Dauer ausgeben
					System.out.println(" (Dauer des Tests: " + 
							_fit.get(suite).getTest(test).getDurationTime() 
							+ " ms)");
					
					// Console in Datei speichern
					_fit.get(suite).getTest(test).setIn(p.getInputStream());

					// Error auslesen
					InputStream is = p.getErrorStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					String line;
					while ((line = br.readLine()) != null) {
						if ((line.indexOf("right") > -1) &&
								(line.indexOf("wrong") > -1)) {
							String[] tmp = line.split(" ");
							_fit.get(suite).getTest(test).setOk(
									Integer.valueOf(tmp[0]));
							_fit.get(suite).getTest(test).setFail(
									Integer.valueOf(tmp[2]));
							_fit.get(suite).getTest(test).setIgnore(
									Integer.valueOf(tmp[4]));
							_fit.get(suite).getTest(test).setException(
									Integer.valueOf(tmp[6]));
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
					_fit.get(suite).getTest(test).setExitStatus(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
					_fit.get(suite).getTest(test).setExitStatus(100);
				}
				
			} // for über die Fit-Tests
			
		} // for über die Fit-Suites
	}
	
	/**
	 * Creates the HTML file containing the results
	 */
	public void createResultHtml()  {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new Date());
		
		String htmlFile = _resultPath + File.separator + "Ergebnisse_" +
				_fitResult + ".html";
		try {
			HtmlOut html = new HtmlOut(htmlFile);
			html.htmlHead();
			
			html.guiHead();
			htmlGui(_gui, html);
			
			html.junitHead();
			htmlJunit(html);
			
			html.fitHead();
			htmlFit(html);
			
			html.htmlEnd();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates the HTML output for the fit test.
	 * 
	 * @param html Class that generates HTML output.
	 * 
	 * @throws IOException
	 */
	private void htmlJunit(HtmlOut html) throws IOException {
		for (int suite = 0; suite < _junit.size(); suite++) {
			int ok = 0;
			int fail = 0;
			long time = 0;
			
			for (int test = 0; test < _junit.get(suite).testCount(); test++) {
				int okTest = _junit.get(suite).getTest(test).getOk();
				int failTest = _junit.get(suite).getTest(test).getFail();
				long timeTest = _junit.get(suite).getTest(test).getDurationTime();
				
				// Ausgabe des Tests
				html.test( _junit.get(suite).getTest(test).getName(),
						okTest, failTest, timeTest, 
						_junit.get(suite).getTest(test).getIn(),
						_junit.get(suite).getTest(test).getError());
				
				// Fehler bzw. Richtig für Test-Suite erhöhen
				ok += okTest;
				fail += failTest;
				time += timeTest;
			} // for über alle Tests
			
			// Ausgabe für die Test-Suite
			html.suiteHtml(_junit.get(suite).getName(),
					_junit.get(suite).getPackage(), ok, fail, time);
		} // for über alle Test-Suits
	}

	/**
	 * Creates the HTML output for the fit test.
	 * 
	 * @param html Class that generates HTML output.
	 * 
	 * @throws IOException
	 */
	private void htmlFit(HtmlOut html) throws IOException {
		for (int suite = 0; suite < _fit.size(); suite++) {
			int right = 0;
			int wrong = 0;
			int ignore = 0;
			int exception = 0;
			long time = 0;
			
			for (int test = 0; test < _fit.get(suite).testCount(); test++) {
				int rightTest = _fit.get(suite).getTest(test).getOk();
				int wrongTest = _fit.get(suite).getTest(test).getFail();
				int ignoreTest = _fit.get(suite).getTest(test).getIgnore();
				int exceptionTest = _fit.get(suite).getTest(test).getException();
				long timeTest = _fit.get(suite).getTest(test).getDurationTime();
				
				// Ausgabe des Tests
				html.test( _fit.get(suite).getTest(test).getName(),
						rightTest, wrongTest, ignoreTest, exceptionTest,
						timeTest, _fit.get(suite).getTest(test).getIn(),
						_fit.get(suite).getTest(test).getError(), true,
						_fitResult + File.separator + 
						_fit.get(suite).getPackage().replaceAll("\\.", 
								File.separator));
				
				// Fehler bzw. Richtig für Test-Suite erhöhen
				right += rightTest;
				wrong += wrongTest;
				ignore += ignoreTest;
				exception += exceptionTest;
				time += timeTest;
			} // for über alle Tests
			
			// Ausgabe für die Test-Suite
			html.suiteHtml(_fit.get(suite).getName(),
					_fit.get(suite).getPackage(), right, wrong, ignore, 
						exception, time);
		} // for über alle Test-Suits
	}

	/**
	 * Creates the HTML output for GUI testing and junit tests.
	 * 
	 * @param list List of test suites, where the HTML output you want to
	 * create.
	 * 
	 * @param html Class that generates HTML output.
	 * 
	 * @throws IOException
	 */
	private void htmlGui(List<TestSuite> list, HtmlOut html) 
			throws IOException {
		for (int suite = 0; suite < list.size(); suite++) {
			int right = 0;
			int exception = 0;
			long time = 0;
			
			for (int test = 0; test < list.get(suite).testCount(); test++) {
				int rightTest = 0;
				int exceptionTest = 0;
				long timeTest = list.get(suite).getTest(test).getDurationTime();
				
				// Überprüfen, ob der Test positiv abgelaufen ist.
				if (list.get(suite).getTest(test).getExitStatus() == 0)
					rightTest++;
				else 
					exceptionTest++;
				
				// Ausgabe des Tests
				html.test( list.get(suite).getTest(test).getName(),
						rightTest, exceptionTest, time,
						list.get(suite).getTest(test).getIn(),
						list.get(suite).getTest(test).getError());
				
				// Fehler bzw. Richtig für Test-Suite erhöhen
				right += rightTest;
				exception += exceptionTest;
				time += timeTest;
			} // for über alle Tests
			
			// Ausgabe für die Test-Suite
			html.suiteHtml(list.get(suite).getName(),
					list.get(suite).getPackage(), right, exception, time);
		} // for über alle Test-Suits
		
	}
}
