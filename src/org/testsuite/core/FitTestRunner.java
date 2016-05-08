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
	 * Holds the source path
	 */
	private String _srcPath;
	
	/**
	 * Holds the result path
	 */
	private String _resultPath;
	
	/**
	 * Holds the result path of fit files.
	 */
	private String _suitesResult;
	
	/**
	 * Holds the path of libraries.
	 */
	private String _bibPath;

	/**
	 * Initialis the data of the class.
	 * 
	 * @param extension Extension of the test files.
	 * 
	 * @param src The source path.
	 * 
	 * @param result The result path.
	 */
	public FitTestRunner(String extension, String src, String result,
			String suitesResult, String bibPath) {
		super(extension);
		_srcPath = src;
		_resultPath = result;
		_suitesResult = suitesResult;
		_bibPath = bibPath;
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
				String fit = composeFileName(_srcPath + "." + 
						_suites.get(suite).getPackage(), 
						_suites.get(suite).getTest(test).getName(), "fit");
				
				// Überprüfen, ob die Datei existiert
				File f = new File(fit);
				if (!f.exists() || f.isDirectory()) {
					_suites.get(suite).getTest(test).setExists(false);
					_suites.get(suite).getTest(test).setExitStatus(100);
					System.out.println("Die Fit-Datei: '" + fit +
							"' existiert nicht oder ist ein Verzeichnis");
					continue;
				}
				_suites.get(suite).getTest(test).setExists(true);
				
				// Überprüfen ob das Result-Verzeichnis existiert
				String resultPath = _resultPath + File.separator + _suitesResult + 
						File.separator + 
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
							"bin:resource:" + _bibPath + "/fit.jar:" + _bibPath +
							"/jemmy.jar:" + _bibPath +
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
	 * Generate the HTML code from the test results.
	 * 
	 * @param html Class, which helps to generate the HTML code.
	 * 
	 * @throws IOException 
	 */
	@Override
	public void createHtml(HtmlOut html) throws IOException {
		for (int suite = 0; suite < _suites.size(); suite++) {
			int right = 0;
			int wrong = 0;
			int ignore = 0;
			int exception = 0;
			long time = 0;
			
			for (int test = 0; test < _suites.get(suite).testCount(); test++) {
				int rightTest = ((Fit)_suites.get(suite).getTest(test)).getOk();
				int wrongTest = 
						((Fit)_suites.get(suite).getTest(test)).getFail();
				int ignoreTest = 
						((Fit)_suites.get(suite).getTest(test)).getIgnore();
				int exceptionTest = 
						((Fit)_suites.get(suite).getTest(test)).getException();
				long timeTest = 
						((Fit)_suites.get(suite).getTest(test)).getDurationTime();
				
				// Ausgabe des Tests
				html.test( _suites.get(suite).getTest(test).getName(),
						rightTest, wrongTest, ignoreTest, exceptionTest,
						timeTest, _suites.get(suite).getTest(test).getIn(),
						_suites.get(suite).getTest(test).getError(), true,
						_suitesResult + File.separator + 
						_suites.get(suite).getPackage().replaceAll("\\.", 
								File.separator));
				
				// Fehler bzw. Richtig für Test-Suite erhöhen
				right += rightTest;
				wrong += wrongTest;
				ignore += ignoreTest;
				exception += exceptionTest;
				time += timeTest;
			} // for über alle Tests
			
			// Ausgabe für die Test-Suite
			html.suiteHtml(_suites.get(suite).getName(),
					_suites.get(suite).getPackage(), right, wrong, ignore, 
						exception, time);
		} // for über alle Test-Suits
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
}
