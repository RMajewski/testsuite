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
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.swing.Timer;

import org.testsuite.data.Config;
import org.testsuite.data.Test;
import org.testsuite.data.TestSuite;

/**
 * Executes the jemmy tests.
 * 
 * @author René Majewski
 *
 * @version 0.2
 */
public class JemmyTestRunner extends TestRunner {
	
	/**
	 * Initialize the data of the class.
	 * 
	 * <strong>Important</strong>: It must also be initialized config. Please
	 * use {@link #setConfig(Config)}.
	 */
	public JemmyTestRunner() {
		super();
	}

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
	 * 
	 * @deprecated Since version 0.3 in the test runner class.
	 */
	public void run_old() {
		for (int suite = 0; suite < _suites.size(); suite++) {
			// Test-Suite Name
			System.out.println(_suites.get(suite).getName());
			
			// gui-Tests ausführen
			for (int test = 0; test < _suites.get(suite).testCount(); test++) {
				String name = _suites.get(suite).getPackage() + "." +
						_suites.get(suite).getTest(test).getName();
				String result = new String();
				
				// Überprüfen, ob Datei existiert
				if (!_suites.get(suite).isExists() || 
						!_suites.get(suite).getTest(test).isExists()) {
					_suites.get(suite).getTest(test).setExitStatus(100);
					System.out.print(name + " ");
					System.out.println(result = _bundle.getString("run_notFound"));
					fireTestExecutedCompleted(this, 
							_suites.get(suite).getPackage(),
							_suites.get(suite).getTest(test).getName(),
							_suites.get(suite).getId(),
							_suites.get(suite).getTest(test).getId(), result);
					continue;
				}
				
				// Überprüft, ob der Test nicht ausgeführt werden soll
				if (!_suites.get(suite).getTest(test).isExecuted()) {
					System.out.print(name + " ");
					System.out.println(result = _bundle.getString(
							"createHtmlColumn_noneExecuted"));
					fireTestExecutedCompleted(this, 
							_suites.get(suite).getPackage(),
							_suites.get(suite).getTest(test).getName(),
							_suites.get(suite).getId(),
							_suites.get(suite).getTest(test).getId(), result);
					continue;
				}
				
				try {
					_suites.get(suite).getTest(test).setStart(
							new Date().getTime());

					System.out.print(name + ": ");
					// OPT -- Begin -- Into run in TestRunner class
					final Process p = Runtime.getRuntime().exec("java -cp " +
							createClasspath() + createProperty() + name);

					Timer timer = new Timer((int)_config.getMaxDuration(),
							new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									p.destroy();
								}
					});
					// OPT -- End -- Into run in TestRunner class
					
					timer.start();
					int exit = p.waitFor();
					timer.stop();
					
					// Ausgabe wie der Test verlaufen ist
					_suites.get(suite).getTest(test).setEnd(new Date().getTime());
					_suites.get(suite).getTest(test).setExitStatus(exit);
					if (exit == 143) {
						result = _bundle.getString("run_terminated");
						_suites.get(suite).getTest(test).setTerminated(true);
					} else if (exit == 0)
						result = _bundle.getString("run_pass");
					else
						result = _bundle.getString("run_failure");
					
					result += " (" + _bundle.getString("run_duration") + " " +
							String.valueOf(
									_suites.get(suite).getTest(test)
									.getDurationTime()) + " ms)";
					System.out.println(result);
					
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
				
				fireTestExecutedCompleted(this, 
						_suites.get(suite).getPackage(),
						_suites.get(suite).getTest(test).getName(),
						_suites.get(suite).getId(),
						_suites.get(suite).getTest(test).getId(), result);
			}
		}
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
		ret.append("?");
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
		
		String cl = new String();
		if ((_suites.get(suite).getTest(test).getExitStatus() == 0) &&
				_suites.get(suite).getTest(test).isExists())
			cl = " class=\"pass\"";
		else if ((_suites.get(suite).getTest(test).getExitStatus() != 0) &&
				_suites.get(suite).getTest(test).isExists())
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
					_suites.get(suite).getTest(test).getError(),
					exec(_suites.get(suite).getPackage() + "." + 
							_suites.get(suite).getTest(test).getName(),
							_suites.get(suite), 
							_suites.get(suite).getTest(test))));
			ret.append("\t\t\t\t\t\t</td>");
			ret.append(System.lineSeparator());
			
			if (_suites.get(suite).getTest(test).isExecuted()) {
				ret.append(td);
				
				if (_suites.get(suite).getTest(test).isTerminated()) {
					ret.append(_bundle.getString("createHtmlColumn_terminated"));
				} else {
					if (_suites.get(suite).getTest(test).getExitStatus() == 0)
						ret.append(_bundle.getString("createHtmlColumn_yes"));
					else
						ret.append(_bundle.getString("createHtmlColumn_no"));
				}

				ret.append("</td>");
				ret.append(System.lineSeparator());
				
				ret.append(td);
				ret.append(String.valueOf(
						_suites.get(suite).getTest(test).getDurationTimeFormattedString()));
			} else {
				ret.append("\t\t\t\t\t\t<td");
				ret.append(cl);
				ret.append(" colspan=\"2\">");
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
			
			ret.append("\t\t\t\t\t\t<td colspan=\"2\">");
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
		org.testsuite.data.Test ret = new org.testsuite.data.Test();
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
		StringBuffer ret = new StringBuffer("java -cp ");
		ret.append(createClasspath());
		ret.append(createProperty());
		ret.append(name);
		
		return ret.toString();
	}
}
