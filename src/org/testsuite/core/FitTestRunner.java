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
				
				// Überprüfen, ob Datei existiert
				if (!_suites.get(suite).isExists() || 
						!_suites.get(suite).getTest(test).isExists()) {
					_suites.get(suite).getTest(test).setExitStatus(100);
					System.out.println(fit + " konnte nicht gefunden werden.");
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
					String exec = "java -cp " +
							"bin:resource:" + _config.getPathLibrary() +
							"/fit.jar:" + _config.getPathLibrary() +
							"/jemmy.jar:" + _config.getPathLibrary() +
							"/sqlite-jdbc-3.8.11.2.jar -Dtesting=true " +
							"fit.FileRunner " + fit + " " + resultFileName;
					Process p = Runtime.getRuntime().exec(exec);
					int exit = p.waitFor();
					
					// Endzeit ermitteln
					_suites.get(suite).getTest(test).setEnd(new Date().getTime());
					
					// Überpüfen ob Exit-Status 0 ist
					_suites.get(suite).getTest(test).setExitStatus(exit);
					if (exit == 0)
						System.out.print("wurde erfolgreich ausgeführt.");
					else
						System.out.print("weißt einen Fehler auf.");
					
					
					// Dauer ausgeben
					System.out.println(" (Dauer des Tests: " + 
							_suites.get(suite).getTest(test).getDurationTime() 
							+ " ms)");
					
					// Console in Datei speichern
					_suites.get(suite).getTest(test).setIn(p.getInputStream());

					// Error auslesen
					InputStream is = p.getErrorStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					String line;
					while ((line = br.readLine()) != null) {
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
				} catch (IOException e) {
					e.printStackTrace();
					_suites.get(suite).getTest(test).setExitStatus(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
					_suites.get(suite).getTest(test).setExitStatus(100);
				}
				
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

	@Override
	protected String createHtmlTableHead(int suite) {
		StringBuilder ret = new StringBuilder("\t\t\t\t\t\t<th>");
		ret.append(_suites.get(suite).getName());
		ret.append("</th>");
		ret.append(System.lineSeparator());

		ret.append("\t\t\t\t\t\t<th>Erfolgreich</th>");
		ret.append(System.lineSeparator());

		ret.append("\t\t\t\t\t\t<th>Falsch</th>");
		ret.append(System.lineSeparator());

		ret.append("\t\t\t\t\t\t<th>Ignoriert</th>");
		ret.append(System.lineSeparator());

		ret.append("\t\t\t\t\t\t<th>Fehlerhaft</th>");
		ret.append(System.lineSeparator());

		ret.append("\t\t\t\t\t\t<th>Zeit</th>");
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

	@Override
	protected String createHtmlColumn(int suite, int test, HtmlOut html)
			throws IOException {
		StringBuilder ret = new StringBuilder("\t\t\t\t\t\t<td>");
		
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
			
			ret.append("\t\t\t\t\t\t<td>");
			ret.append(((Fit)_suites.get(suite).getTest(test)).getOk());
			ret.append("</td>");
			ret.append(System.lineSeparator());
			
			ret.append("\t\t\t\t\t\t<td>");
			ret.append(((Fit)_suites.get(suite).getTest(test)).getFail());
			ret.append("</td>");
			ret.append(System.lineSeparator());
			
			ret.append("\t\t\t\t\t\t<td>");
			ret.append(((Fit)_suites.get(suite).getTest(test)).getIgnore());
			ret.append("</td>");
			ret.append(System.lineSeparator());
			
			ret.append("\t\t\t\t\t\t<td>");
			ret.append(((Fit)_suites.get(suite).getTest(test)).getException());
			ret.append("</td>");
			ret.append(System.lineSeparator());
			
			ret.append("\t\t\t\t\t\t<td>");
			ret.append(
					((Fit)_suites.get(suite).getTest(test)).getDurationTime());
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
			ret.append("Test existiert nicht");
		}
		
		ret.append("</td>");
		ret.append(System.lineSeparator());
		
		return ret.toString();
	}
}
