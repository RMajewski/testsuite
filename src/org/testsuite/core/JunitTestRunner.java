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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import org.testsuite.data.Config;
import org.testsuite.data.Junit;

/**
 * Executes the junit tests.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class JunitTestRunner extends TestRunner {

	/**
	 * Initialis the data of the class.
	 * 
	 * @param extension Extension of the test files.
	 * 
	 * @param config The configuration.
	 */
	public JunitTestRunner(String extension, Config config) {
		super(extension, config);
	}

	/**
	 * Executes the junit tests.
	 */
	@Override
	public void run() {
		for (int suite = 0; suite < _suites.size(); suite++) {
			// Test-Suite Name
			System.out.println(_suites.get(suite).getName());
			
			// juni-Tests ausführen
			for (int test = 0; test < _suites.get(suite).testCount(); test++) {
				String name = _suites.get(suite).getPackage() + "." +
						_suites.get(suite).getTest(test).getName();
				
				try {
					_suites.get(suite).getTest(test).setStart(
							new Date().getTime());

					System.out.print(name + ": ");
					Process p = Runtime.getRuntime().exec("java -cp " +
							System.getProperty("java.class.path")+
							" -Dtesting=true org.junit.runner.JUnitCore " +
							name);
					
					int exit = p.waitFor();
					_suites.get(suite).getTest(test).setExitStatus(exit);
					
					// Ausgabe wie der Test verlaufen ist
					_suites.get(suite).getTest(test).setEnd(
							new Date().getTime());

					if (exit == 0)
						System.out.print("wurde erfolgreich ausgeführt");
					else
						System.out.print("weißt Fehler auf");

					System.out.print(" (Dauer des Tests: ");
					System.out.print(String.valueOf(
							_suites.get(suite).getTest(test).getDurationTime()));
					System.out.println(" ms)");
					
					// Console-Ausgabe und Error-Ausgabe speichern
					_suites.get(suite).getTest(test).setError(p.getErrorStream());
					_suites.get(suite).getTest(test).setIn(p.getInputStream());

					InputStream is = p.getInputStream();
					is.mark(is.available());
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					String line;
					while ((line = br.readLine()) != null) {
						if (line.indexOf("OK (") > -1) {
							String ok = new String("OK (");
							
							String tmp = line.substring(ok.length(),
									line.indexOf(" tests)"));
							((Junit)_suites.get(suite).getTest(test)).setOk(
									Integer.valueOf(tmp));
						} else if (line.indexOf("Tests run") > -1) {
							String ok = new String("Tests run: ");
							String fail = new String(",  Failures: ");
							int indexOk = ok.length();
							int indexFail = line.indexOf(fail);

							((Junit)_suites.get(suite).getTest(test)).setOk(
									Integer.valueOf(line.substring(indexOk, 
											indexFail)));
							
							indexFail += fail.length();
							((Junit)_suites.get(suite).getTest(test)).setFail(
									Integer.valueOf(line.substring(indexFail)));
						}
					}
					is.reset();
				} catch (IOException e) {
					e.printStackTrace();
					_suites.get(suite).getTest(test).setExitStatus(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
					_suites.get(suite).getTest(test).setExitStatus(100);
				}
			} // for über alle Tests
			
			System.out.println();
		} // for über alle Test-Suites
	}

	/**
	 * Generate the HTML code from the test results.
	 * 
	 * @param html Class, which helps to generate the HTML code.
	 * 
	 * @throws IOException 
	 */
	@Override
	public void createHtml(HtmlOut html) throws IOException {
		for (int suite = 0; suite < _suites.size(); suite++) {
			int ok = 0;
			int fail = 0;
			long time = 0;
			
			for (int test = 0; test < _suites.get(suite).testCount(); test++) {
				int okTest = ((Junit)_suites.get(suite).getTest(test)).getOk();
				int failTest = ((Junit)_suites.get(suite).getTest(test)).getFail();
				long timeTest = _suites.get(suite).getTest(test).getDurationTime();
				
				// Ausgabe des Tests
				html.test( _suites.get(suite).getTest(test).getName(),
						okTest, failTest, timeTest, 
						_suites.get(suite).getTest(test).getIn(),
						_suites.get(suite).getTest(test).getError());
				
				// Fehler bzw. Richtig für Test-Suite erhöhen
				ok += okTest;
				fail += failTest;
				time += timeTest;
			} // for über alle Tests
			
			// Ausgabe für die Test-Suite
			html.suiteHtml(_suites.get(suite).getName(),
					_suites.get(suite).getPackage(), ok, fail, time);
		} // for über alle Test-Suits
	}

}
