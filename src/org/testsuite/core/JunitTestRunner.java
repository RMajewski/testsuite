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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import javax.swing.Timer;

import org.testsuite.data.Config;
import org.testsuite.data.Junit;
import org.testsuite.data.Test;
import org.testsuite.data.TestSuite;

/**
 * Executes the junit tests.
 * 
 * @author René Majewski
 *
 * @version 0.2
 */
public class JunitTestRunner extends TestRunner {
	
	/**
	 * Initialize the data of the class.
	 * 
	 * <strong>Important</strong>: It must also be initialized config. Please
	 * use {@link #setConfig(Config)}.
	 */
	public JunitTestRunner() {
		super();
	}

	/**
	 * Initialize the data of the class.
	 * 
	 * @param config The configuration.
	 */
	public JunitTestRunner(Config config) {
		super(config);
	}

	/**
	 * Executes the junit tests.
	 * 
	 * @deprecated Since version 0.3 in the test runner class.
	 */
	public void run_old() {
		for (int suite = 0; suite < _suites.size(); suite++) {
			// Test-Suite Name
			System.out.println(_suites.get(suite).getName());
			
			// juni-Tests ausführen
			for (int test = 0; test < _suites.get(suite).testCount(); test++) {
				String name = _suites.get(suite).getPackage() + "." +
							_suites.get(suite).getTest(test).getName();
				String result = new String();

				// OPT -- Begin -- Into run in TestRunner class
				// Überprüfen, ob Datei existiert
				if (!_suites.get(suite).isExists() || 
						!_suites.get(suite).getTest(test).isExists()) {
					_suites.get(suite).getTest(test).setExitStatus(100);
					result = _bundle.getString("run_notFound");
					System.out.print(name + " ");
					System.out.println(result);
					fireTestExecutedCompleted(this, 
							_suites.get(suite).getPackage(),
							_suites.get(suite).getTest(test).getName(),
							_suites.get(suite).getId(),
							_suites.get(suite).getTest(test).getId(), result);
					continue;
				}
				
				// Überprüfen, ob der Test nicht ausgeführt werden soll
				if (!_suites.get(suite).getTest(test).isExecuted()) {
					System.out.print(name + " ");
					result = _bundle.getString(
							"createHtmlColumn_noneExecuted");
					System.out.println(result);
					fireTestExecutedCompleted(this, 
							_suites.get(suite).getPackage(),
							_suites.get(suite).getTest(test).getName(),
							_suites.get(suite).getId(),
							_suites.get(suite).getTest(test).getId(), result);
				continue;
				}
				// OPT -- End -- Into run in TestRunner class
			
				try {
					_suites.get(suite).getTest(test).setStart(
							new Date().getTime());

					System.out.print(name + ": ");
					// OPT -- Begin -- Into run in TestRunner class
					final Process p = Runtime.getRuntime().exec("java -cp " +
							createClasspath() + createProperty() +
							"org.junit.runner.JUnitCore " +
							name);

					Timer timer = new Timer((int)_config.getMaxDuration(),
							new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									p.destroy();
								}
					});
					timer.start();
					
					int exit = p.waitFor();
					timer.stop();
					_suites.get(suite).getTest(test).setExitStatus(exit);
					// OPT -- End -- Into run in TestRunner class
					
					// Ausgabe wie der Test verlaufen ist
					_suites.get(suite).getTest(test).setEnd(
							new Date().getTime());

					if (exit == 143){
						result = _bundle.getString("run_terminated");
						_suites.get(suite).getTest(test).setTerminated(true);
					} else  if (exit == 0)
						result = _bundle.getString("run_pass");
					else
						result = _bundle.getString("run_failure");

					result += " (" + _bundle.getString("run_duration") +" " + 
							String.valueOf(
									_suites.get(suite).getTest(test)
									.getDurationTime()) + " ms)";
					System.out.println(result);
					
					// Console-Ausgabe und Error-Ausgabe speichern
					_suites.get(suite).getTest(test).setError(
							inputStreamToString(p.getErrorStream()));

					// OPT -- Begin -- Into the error evaluation method
					InputStream is = p.getInputStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					String line;
					StringBuilder console = new StringBuilder();
					if (br.ready()) {
						while ((line = br.readLine()) != null) {
							console.append(line);
							console.append(System.lineSeparator());
							if (line.indexOf("OK (") > -1) {
								String ok = new String("OK (");
								
								String tmp = line.substring(ok.length(),
										line.indexOf(" test"));
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
					}
					_suites.get(suite).getTest(test).setStringConsole(
							replaceHtmlEntities(console.toString()));
					// OPT -- End -- Into the error evaluation method
				} catch (IOException e) {
					e.printStackTrace();
					_suites.get(suite).getTest(test).setExitStatus(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
					_suites.get(suite).getTest(test).setExitStatus(100);
				}
				
				fireTestExecutedCompleted(this, 
						_suites.get(suite).getPackage(),
						_suites.get(suite).getTest(test).getName(),
						_suites.get(suite).getId(),
						_suites.get(suite).getTest(test).getId(), result);
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
		
		if (!_suites.get(suite).getTest(test).isExecuted() ||
				_suites.get(suite).getTest(test).isTerminated())
			cl = " class=\"ignore\"";
		
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
			
			if (_suites.get(suite).getTest(test).isExecuted()) {
				if (_suites.get(suite).getTest(test).isTerminated()) {
					ret.append("\t\t\t\t\t\t<td class=\"ignore\" ");
					ret.append("colspan=\"2\">");
					ret.append(_bundle.getString("createHtmlColumn_terminated"));
					ret.append("</td>");
					ret.append(System.lineSeparator());
				} else {
					ret.append(td);
					ret.append(String.valueOf(
							((Junit)_suites.get(suite).getTest(test)).getOk()));
					ret.append("</td>");
					ret.append(System.lineSeparator());
					
					ret.append(td);
					ret.append(String.valueOf(
							((Junit)_suites.get(suite).getTest(test))
							.getFail()));
					ret.append("</td>");
					ret.append(System.lineSeparator());
				}
				
				ret.append(td);
				ret.append(String.valueOf(
						_suites.get(suite).getTest(test)
							.getDurationTimeFormattedString()));
			} else {
				ret.append("\t\t\t\t\t\t<td colspan=\"3\" class=\"ignore\">");
				ret.append(_bundle.getString("createHtmlColumn_noneExecuted"));
			}
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
			
			ret.append("\t\t\t\t\t\t<td colspan=\"3\" class=\"ignore\">");
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
	
	/**
	 * Creates the command to execute.
	 * 
	 * @param test Name of the test class or name of file
	 * 
	 * @return Command to execute
	 */
	@Override
	public String exec(String name, TestSuite suite, Test test) {
		StringBuilder ret = new StringBuilder("java -cp ");
		ret.append(createClasspath());
		ret.append(createProperty());
		ret.append("org.junit.runner.JunitCore");
		ret.append(test);
		
		return ret.toString();
	}
}
