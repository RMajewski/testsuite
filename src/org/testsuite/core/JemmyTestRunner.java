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

import java.io.IOException;
import java.util.Date;

import org.testsuite.data.Config;

/**
 * Executes the jemmy tests.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class JemmyTestRunner extends TestRunner {

	/**
	 * Initialis the data of the class.
	 * 
	 * @param config The configuration.
	 */
	public JemmyTestRunner(Config config) {
		super(config);
	}

	/**
	 * Executes the jemmy tests
	 */
	@Override
	public void run() {
		for (int suite = 0; suite < _suites.size(); suite++) {
			// Test-Suite Name
			System.out.println(_suites.get(suite).getName());
			
			// gui-Tests ausführen
			for (int test = 0; test < _suites.get(suite).testCount(); test++) {
				String name = _suites.get(suite).getPackage() + "." +
						_suites.get(suite).getTest(test).getName();
				
				// Überprüfen, ob Datei existiert
				if (!_suites.get(suite).isExists() || 
						!_suites.get(suite).getTest(test).isExists()) {
					_suites.get(suite).getTest(test).setExitStatus(100);
					System.out.println(name + " konnte nicht gefunden werden.");
					continue;
				}
				
				try {
					_suites.get(suite).getTest(test).setStart(
							new Date().getTime());

					System.out.print(name + ": ");
					Process p = Runtime.getRuntime().exec("java -cp " +
							System.getProperty("java.class.path")+
							" -Dtesting=true " + name);
					int exit = p.waitFor();
					
					// Ausgabe wie der Test verlaufen ist
					_suites.get(suite).getTest(test).setEnd(new Date().getTime());
					_suites.get(suite).getTest(test).setExitStatus(exit);
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
				} catch (IOException e) {
					e.printStackTrace();
					_suites.get(suite).getTest(test).setExitStatus(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
					_suites.get(suite).getTest(test).setExitStatus(100);
				}
				
			}
		}
	}

	/**
	 * Generate the HTML code from the test results.
	 * 
	 * @param html Class, which helps to generate the HTML code.
	 * 
	 * @throws IOException
	 * 
	 * @deprecated
	 */
	public void createHtml(HtmlOut html) throws IOException {
		for (int suite = 0; suite < _suites.size(); suite++) {
			int right = 0;
			int exception = 0;
			long time = 0;
			
			for (int test = 0; test < _suites.get(suite).testCount(); test++) {
				int rightTest = 0;
				int exceptionTest = 0;
				long timeTest = _suites.get(suite).getTest(test).getDurationTime();
				
				// Überprüfen, ob der Test positiv abgelaufen ist.
				if (_suites.get(suite).getTest(test).getExitStatus() == 0)
					rightTest++;
				else 
					exceptionTest++;
				
				// Ausgabe des Tests
				html.test( _suites.get(suite).getTest(test).getName(),
						rightTest, exceptionTest, time,
						_suites.get(suite).getTest(test).getIn(),
						_suites.get(suite).getTest(test).getError());
				
				// Fehler bzw. Richtig für Test-Suite erhöhen
				right += rightTest;
				exception += exceptionTest;
				time += timeTest;
			} // for über alle Tests
			
			// Ausgabe für die Test-Suite
			html.suiteHtml(_suites.get(suite).getName(),
					_suites.get(suite).getPackage(), right, exception, time);
		} // for über alle Test-Suits
	}

	@Override
	protected String createHtmlTableHead(int suite) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String createHtmlColumn(int suite, int test) {
		// TODO Auto-generated method stub
		return null;
	}

}
