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
	 * @param config The configuration.
	 */
	public JunitTestRunner(Config config) {
		super(config);
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
				
				// Überprüfen, ob Datei existiert
				if (!_suites.get(suite).isExists() || 
						!_suites.get(suite).getTest(test).isExists()) {
					_suites.get(suite).getTest(test).setExitStatus(100);
					System.out.print(name + " ");
					System.out.println(_bundle.getString("run_notFound"));
					continue;
				}
			
				try {
					_suites.get(suite).getTest(test).setStart(
							new Date().getTime());

					// FIXME classpath anpassen
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
						System.out.print(_bundle.getString("run_pass"));
					else
						System.out.print(_bundle.getString("run_failure"));

					System.out.print(" (");
					System.out.print(_bundle.getString("run_duration"));
					System.out.print(" ");
					System.out.print(String.valueOf(
							_suites.get(suite).getTest(test).getDurationTime()));
					System.out.println(" ms)");
					
					// Console-Ausgabe und Error-Ausgabe speichern
					_suites.get(suite).getTest(test).setError(
							inputStreamToString(p.getErrorStream()));

					InputStream is = p.getInputStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					String line;
					StringBuilder console = new StringBuilder();
					while ((line = br.readLine()) != null) {
						console.append(line);
						console.append("<br/>");
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
					_suites.get(suite).getTest(test).setStringConsole(
							console.toString());
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

	@Override
	protected String createHtmlTableHead(int suite) {
		StringBuilder ret = new StringBuilder("\t\t\t\t\t\t<th style=\"");
		ret.append("width: 50%;\">");
		ret.append(_suites.get(suite).getName());
		ret.append("</th>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t\t<th>");
		ret.append(_bundle.getString("createHtmlTableHead_ok"));
		ret.append("</th>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t\t<th>");
		ret.append(_bundle.getString("createHtmlTableHead_exception"));
		ret.append("</th>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t\t<th>");
		ret.append(_bundle.getString("createHtmlTableHead_time"));
		ret.append("</th>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t</tr>");
		ret.append(System.lineSeparator());
		ret.append("\t\t\t\t\t<tr>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t\t<th colspan=\"4\">");
		ret.append(_suites.get(suite).getPackage());
		ret.append("</th>");
		ret.append(System.lineSeparator());
		
		return ret.toString();
	}

	@Override
	protected String createHtmlColumn(int suite, int test, HtmlOut html)
		throws IOException {
		if (suite < 0)
			throw new IllegalArgumentException();
		
		if (test < 0)
			throw new IllegalArgumentException();
		
		if (html == null)
			throw new IllegalArgumentException();

		String cl = new String();
		if (((Junit)_suites.get(suite).getTest(test)).getOk() > 0)
			cl = " class=\"pass\"";

		if (((Junit)_suites.get(suite).getTest(test)).getFail() > 0)
			cl = " class=\"wrong\"";
		
		String td = "\t\t\t\t\t\t<td" + cl + ">";

		StringBuilder ret = new StringBuilder(td);
		
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
			
			ret.append(td);
			ret.append(String.valueOf(
					((Junit)_suites.get(suite).getTest(test)).getOk()));
			ret.append("</td>");
			ret.append(System.lineSeparator());
			
			ret.append(td);
			ret.append(String.valueOf(
					((Junit)_suites.get(suite).getTest(test)).getFail()));
			ret.append("</td>");
			ret.append(System.lineSeparator());
			
			ret.append(td);
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
			
			ret.append("\t\t\t\t\t\t<td colspan=\"3\" class=\"wrong\">");
			ret.append(_bundle.getString("createHtmlColumn_noneExistingTest"));
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
		Junit ret = new Junit();
		ret.setName(name);
		ret.setId(id);
		return ret;
	}
}
