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
import org.testsuite.data.Fit;

/**
 * Executes the Fit tests.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class FitTestRunner extends TestRunner {
	/**
	 * Initialis the data of the class.
	 * 
	 * @param extension Extension of the test files.
	 * 
	 * @param config The configuration
	 */
	public FitTestRunner(Config config) {
		super(config);
	}

	/**
	 * Executes the Fit tests
	 */
	@Override
	public void run() {
		for (int suite = 0; suite < _suites.size(); suite++) {
			// Test-Suite Name
			System.out.println(_suites.get(suite).getName());
			
			for (int test = 0; test < _suites.get(suite).testCount(); test++) {
				// Name der Fit-Datei
				String fit = composeFileName(_config.getPathSrc() + "." + 
						_suites.get(suite).getPackage(), 
						_suites.get(suite).getTest(test).getName(),
						_fileExtension);
				String result = new String();
				
				// Überprüfen, ob Datei existiert
				if (!_suites.get(suite).isExists() || 
						!_suites.get(suite).getTest(test).isExists()) {
					_suites.get(suite).getTest(test).setExitStatus(100);
					System.out.print(fit + " ");
					System.out.println(result = _bundle.getString("run_notFound"));
					fireTestExecutedCompleted(this, 
							_suites.get(suite).getPackage(),
							_suites.get(suite).getTest(test).getName(),
							_suites.get(suite).getId(),
							_suites.get(suite).getTest(test).getId(), result);
					continue;
				}
				
				// Überprüfen, ob der Test nicht ausgeführt werden soll
				if (!_suites.get(suite).getTest(test).isExecuted()) {
					System.out.print(fit + " ");
					System.out.println(result = _bundle.getString(
							"createHtmlColumn_noneExecuted"));
					fireTestExecutedCompleted(this, 
							_suites.get(suite).getPackage(),
							_suites.get(suite).getTest(test).getName(),
							_suites.get(suite).getId(),
							_suites.get(suite).getTest(test).getId(), result);
					continue;
				}
				
				// Überprüfen ob das Result-Verzeichnis existiert
				String resultPath = _config.getPathResult() + File.separator + 
						_config.getPathSuitesResult() + File.separator + 
						_suites.get(suite).getPackage().replaceAll("\\.", 
								File.separator);
				File r = new File(resultPath);
				if (!r.exists()) {
					// Verzeichnis anlegen
					r.mkdirs();
				}
				
				// Name der Result-Datei ermitteln und als Endung html
				String resultFileName = resultPath + File.separator + 
						_suites.get(suite).getTest(test).getName() + ".html";
				
				// Ausführen
				try {
					_suites.get(suite).getTest(test).setStart(new Date().getTime());

					System.out.print(fit + ": ");
					String exec = "java -cp " + createClasspath() +
							createProperty() + "fit.FileRunner " + fit + " " +
							resultFileName;
					Process p = Runtime.getRuntime().exec(exec);
					int exit = p.waitFor();
					
					// Endzeit ermitteln
					_suites.get(suite).getTest(test).setEnd(new Date().getTime());
					
					// Überpüfen ob Exit-Status 0 ist
					_suites.get(suite).getTest(test).setExitStatus(exit);
					if (exit == 0)
						result = _bundle.getString("run_pass");
					else
						result = _bundle.getString("run_failure");
					
					
					// Dauer ausgeben
					result += " (" + _bundle.getString("run_duration") + " " +
							_suites.get(suite).getTest(test).getDurationTime() + 
							" ms)";
					System.out.println(result);
					
					// Console speichern
					_suites.get(suite).getTest(test).setStringConsole(
							inputStreamToString(p.getInputStream()));

					// Error auslesen
					InputStream is = p.getErrorStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					StringBuilder error = new StringBuilder();
					String line;
					while ((line = br.readLine()) != null) {
						error.append(line);
						error.append("<br/>");
						if ((line.indexOf("right") > -1) &&
								(line.indexOf("wrong") > -1)) {
							String[] tmp = line.split(" ");
							((Fit)_suites.get(suite).getTest(test)).setOk(
									Integer.valueOf(tmp[0]));
							((Fit)_suites.get(suite).getTest(test)).setFail(
									Integer.valueOf(tmp[2]));
							((Fit)_suites.get(suite).getTest(test)).setIgnore(
									Integer.valueOf(tmp[4]));
							((Fit)_suites.get(suite).getTest(test)).setException(
									Integer.valueOf(tmp[6]));
						}
					}
					_suites.get(suite).getTest(test).setError(
							error.toString());
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
				
			} // for über die Fit-Tests
			
		} // for über die Fit-Suites
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
	 * Creates the column headers.
	 * 
	 * @param suite The index for des test suite.
	 */
	@Override
	protected String createHtmlTableHead(int suite) {
		StringBuilder ret = new StringBuilder("\t\t\t\t\t\t<th>");
		ret.append(_suites.get(suite).getName());
		ret.append("</th>");
		ret.append(System.lineSeparator());

		ret.append("\t\t\t\t\t\t<th>");
		ret.append(_bundle.getString("createHtmlTableHead_ok"));
		ret.append("</th>");
		ret.append(System.lineSeparator());

		ret.append("\t\t\t\t\t\t<th>");
		ret.append(_bundle.getString("createHtmlTableHead_wrong"));
		ret.append("</th>");
		ret.append(System.lineSeparator());

		ret.append("\t\t\t\t\t\t<th>");
		ret.append(_bundle.getString("createHtmlTableHead_ignore"));
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

		ret.append("\t\t\t\t\t\t<th colspan=\"6\">");
		ret.append(_suites.get(suite).getPackage());
		ret.append("</th>");
		ret.append(System.lineSeparator());
		
		return ret.toString();
	}

	/**
	 * 
	 * 
	 * @param suite The index for the test suite.
	 * 
	 * @param test The index for the test.
	 * 
	 * @param html The instance of HtmlOut.
	 */
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
		if (((Fit)_suites.get(suite).getTest(test)).getOk() > 0)
			cl = " class=\"pass\"";

		if (((Fit)_suites.get(suite).getTest(test)).getFail() > 0)
			cl = " class=\"wrong\"";
		
		if (!_suites.get(suite).getTest(test).isExecuted())
			cl = " class=\"ignore\"";
		
		String td = "\t\t\t\t\t\t<td" + cl + ">";
		
		StringBuilder ret = new StringBuilder(td);
		
		if (_suites.get(suite).getTest(test).isExists()) {
			ret.append("<a href=\"");
			ret.append(_config.getPathSuitesResult());
			ret.append(File.separator);
			ret.append(_suites.get(suite).getTest(test).getName());
			ret.append(".html\">");
			ret.append(_suites.get(suite).getTest(test).getName());
			ret.append("</a>");
			ret.append(html.generateTestOut(_suites.get(suite).getId(), 
						_suites.get(suite).getTest(test).getId(), 
						_suites.get(suite).getTest(test).getIn(),
						_suites.get(suite).getTest(test).getError()));
			ret.append("\t\t\t\t\t\t</td>");
			ret.append(System.lineSeparator());
			
			if (_suites.get(suite).getTest(test).isExecuted()) {
				ret.append(td);
				ret.append(((Fit)_suites.get(suite).getTest(test)).getOk());
				ret.append("</td>");
				ret.append(System.lineSeparator());
				
				ret.append(td);
				ret.append(((Fit)_suites.get(suite).getTest(test)).getFail());
				ret.append("</td>");
				ret.append(System.lineSeparator());
				
				ret.append(td);
				ret.append(((Fit)_suites.get(suite).getTest(test)).getIgnore());
				ret.append("</td>");
				ret.append(System.lineSeparator());
				
				ret.append(td);
				ret.append(((Fit)_suites.get(suite).getTest(test)).getException());
				ret.append("</td>");
				ret.append(System.lineSeparator());
				
				ret.append(td);
				ret.append(
						((Fit)_suites.get(suite).getTest(test))
							.getDurationTimeFormattedString());
			} else {
				ret.append("\t\t\t\t\t\t<td");
				ret.append(cl);
				ret.append(" colspan=\"4\">");
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
			
			ret.append("\t\t\t\t\t\t<td colspan=\"5\">");
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
		Fit ret = new Fit();
		ret.setName(name);
		ret.setId(id);
		return ret;
	}
}
