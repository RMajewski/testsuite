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
import java.io.IOException;
import java.util.Date;

import org.testsuite.data.Config;
import org.testsuite.data.Fit;
import org.testsuite.data.Junit;

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
					_suites.get(suite).getTest(test).setError(
							inputStreamToString(p.getErrorStream()));
					_suites.get(suite).getTest(test).setStringConsole(
							inputStreamToString(p.getInputStream()));
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

	@Override
	protected String createHtmlTableHead(int suite) {
		StringBuilder ret = new StringBuilder("\t\t\t\t\t\t<th>");
		ret.append(_suites.get(suite).getName());
		ret.append("</th>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t\t<th>");
		ret.append("Erfolgreich?");
		ret.append("</th>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t\t<th>");
		ret.append("Zeit");
		ret.append("</th>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t</tr>");
		ret.append(System.lineSeparator());
		ret.append("\t\t\t\t\t<tr>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t\t<th colspan=\"3\">");
		ret.append(_suites.get(suite).getPackage());
		ret.append("</th>");
		ret.append(System.lineSeparator());
		
		return ret.toString();
	}

	@Override
	protected String createHtmlColumn(int suite, int test, HtmlOut html) 
			throws IOException{
		if (suite < 0)
			throw new IllegalArgumentException();
		
		if (test < 0)
			throw new IllegalArgumentException();
		
		if (html == null)
			throw new IllegalArgumentException();
		
		StringBuilder ret = new StringBuilder("\t\t\t\t\t\t<td>");
		
		if (_suites.get(suite).getTest(test).isExists()) {
			ret.append(_suites.get(suite).getTest(test).getName());
			ret.append(System.lineSeparator());
			ret.append(html.generateTestOut(
					_suites.get(suite).getId(),
					_suites.get(suite).getTest(test).getId(), 
					_suites.get(suite).getTest(test).getIn(), 
					_suites.get(suite).getTest(test).getError()));
			ret.append("\t\t\t\t\t\t</td>");
			ret.append(System.lineSeparator());
			
			ret.append("\t\t\t\t\t\t<td>");
			
			if (_suites.get(suite).getTest(test).getExitStatus() == 0)
				ret.append("Ja");
			else
				ret.append("Nein");
			
			ret.append("</td>");
			ret.append(System.lineSeparator());
			
			ret.append("\t\t\t\t\t\t<td>");
			ret.append(String.valueOf(
					_suites.get(suite).getTest(test).getDurationTime()));
		} else {
			ret.append(_config.getPathSrc());
			ret.append(File.separator);
			ret.append(_suites.get(suite).getPackage().replaceAll("\\.", 
					File.separator));
			ret.append(File.separator);
			ret.append(_suites.get(suite).getTest(test).getName());
			ret.append(".");
			ret.append(_fileExtension);
			ret.append("</td>");
			ret.append(System.lineSeparator());
			
			ret.append("\t\t\t\t\t\t<td colspan=\"2\">");
			ret.append("Test existiert nicht");
		}
		
		ret.append("</td>");
		ret.append(System.lineSeparator());
		
		return ret.toString();
	}

	/**
	 * Creates a new test and gives him the name and its id.
	 * 
	 * @param name Name of the new test
	 * 
	 * @param id Id of the new test
	 */
	@Override
	public org.testsuite.data.Test newTest(String name, int id) {
		org.testsuite.data.Test ret = new org.testsuite.data.Test();
		ret.setName(name);
		ret.setId(id);
		return ret;
	}

}
