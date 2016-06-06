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
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.testsuite.data.Config;
import org.testsuite.data.Junit;
import org.testsuite.data.Test;
import org.testsuite.data.TestSuite;
import org.testsuite.helper.HelperCalendar;

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
	 */
	public JunitTestRunner() {
		super();
	}

	/**
	 * Initialize the data of the class.
	 * 
	 * @param config The configuration.
	 * 
	 * @deprecated {@link #JunitTestRunner()}
	 */
	public JunitTestRunner(Config config) {
		super(config);
	}

	/**
	 * Creates the column headers.
	 * 
	 * @param suite The index for test suite.
	 */
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

	/**
	 * Creates from the data of the current test the HTML output.
	 * 
	 * @param suite The index for the test suite.
	 * 
	 * @param test The index for the test.
	 * 
	 * @param html The instance of HtmlOut.
	 * 
	 * @deprecated Use {@link #createHtmlRow(int, int, HtmlOut)}
	 */
	@Override
	protected String createHtmlColumn(int suite, int test, HtmlOut html)
		throws IOException {
		return createHtmlRow(suite, test, html);
	}
	
	/**
	 * Creates from the data of the current test the row output for HTML table.
	 * 
	 * @param suite The index for the test suite.
	 * 
	 * @param test The index for the test.
	 * 
	 * @param html The instance of HtmlOut.
	 * 
	 * @return The HTML code for the row of HTML table.
	 */
	@Override
	protected String createHtmlRow(int suite, int test, HtmlOut html) 
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
			String command = new String();
			
			if (_suites.get(suite).getTest(test).isJvm())
				command = exec(_suites.get(suite).getPackage() + "." + 
						_suites.get(suite).getTest(test).getName(),
						_suites.get(suite), 
						_suites.get(suite).getTest(test));
			
			ret.append(_suites.get(suite).getTest(test).getName());
			ret.append(System.lineSeparator());
			ret.append(html.generateTestOut(
					_suites.get(suite).getId(),
					_suites.get(suite).getTest(test).getId(), 
					_suites.get(suite).getTest(test).getConsole(), 
					_suites.get(suite).getTest(test).getError(), command));
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
			ret.append(Config.getInstance().getPathSrc());
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
	 * Generate the footer for the HTML table
	 * 
	 * @param suite Index for test suite
	 * 
	 * @return Footer for the HTML table.
	 */
	@Override
	protected String createHtmlTableFooter(int suite) {
		StringBuilder ret = new StringBuilder();
		String td = "\t\t\t\t\t\t<td>";
		
		int right = 0;
		int wrong = 0;
		long duration = 0;
		
		for (int test = 0; test < _suites.get(suite).testCount(); test++) {
			right += ((Junit)_suites.get(suite).getTest(test)).getOk();
			wrong += ((Junit)_suites.get(suite).getTest(test)).getFail();
			duration += _suites.get(suite).getTest(test).getDurationTime();
		}
		
		ret.append(td);
		ret.append("&nbsp;</td>");
		ret.append(System.lineSeparator());
		
		ret.append(td);
		ret.append(right);
		ret.append("</td>");
		ret.append(System.lineSeparator());
		
		ret.append(td);
		ret.append(wrong);
		ret.append("</td>");
		ret.append(System.lineSeparator());
		
		ret.append(td);
		ret.append(HelperCalendar.timeToString(duration));
		ret.append("</td>");
		ret.append(System.lineSeparator());
		
		return ret.toString();
	}
	
	/**
	 * Generate the result table of all tests in this test runner
	 * 
	 * @return Result table of all tests in this test runner
	 */
	@Override
	protected String createResultTestRunnerTable() {
		StringBuilder ret = new StringBuilder("\t\t\t\t<table>");
		ret.append(System.lineSeparator());
		
		int right = 0;
		int wrong = 0;
		int tests_all = 0;
		int tests_terminated = 0;
		int tests_ignored = 0;
		int tests_executed = 0;
		int tests_not_exists = 0;
		long duration = 0;

		for (int suite = 0; suite < _suites.size(); suite++) {
			tests_all += _suites.get(suite).testCount();
			for (int test = 0; test < _suites.get(suite).testCount(); test++) {
				right += ((Junit)_suites.get(suite).getTest(test)).getOk();
				wrong += ((Junit)_suites.get(suite).getTest(test)).getFail();
				duration += _suites.get(suite).getTest(test).getDurationTime();
				
				if (_suites.get(suite).getTest(test).isTerminated())
					tests_terminated++;
				
				if (_suites.get(suite).getTest(test).isExecuted() && 
						_suites.get(suite).getTest(test).isExists())
					tests_executed++;
				else if (!_suites.get(suite).getTest(test).isExists())
					tests_not_exists++;
				else
					tests_ignored++;
			}
		}
		
		String tr = "\t\t\t\t\t<tr>";
		String tr_end = "\t\t\t\t\t</tr>";
		String th = "\t\t\t\t\t\t<th>";
		String td = "\t\t\t\t\t\t<td>";
		
		// Name of TestRunner
		ret.append(tr);
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t\t<th colspan=\"3\">");
		ret.append(_bundle.getString("test_runner_result_name"));
		ret.append("</th>");
		ret.append(System.lineSeparator());
		
		ret.append(tr_end);
		ret.append(System.lineSeparator());
		
		ret.append(tr);
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t\t<td colspan=\"3\">");
		ret.append(this.getClass().getName());
		ret.append("</td>");
		ret.append(System.lineSeparator());
		
		ret.append(tr_end);
		ret.append(System.lineSeparator());
		
		ret.append(tr);
		ret.append(System.lineSeparator());
		
		ret.append(th);
		ret.append(_bundle.getString("test_runner_result_tests"));
		ret.append("</th>");
		ret.append(System.lineSeparator());
		
		ret.append(th);
		ret.append(_bundle.getString("test_runner_result_tests_executed"));
		ret.append("</th>");
		ret.append(System.lineSeparator());
		
		ret.append(th);
		ret.append(_bundle.getString("test_runner_result_tests_terminated"));
		ret.append("</th>");
		ret.append(System.lineSeparator());
		
		ret.append(tr_end);
		ret.append(System.lineSeparator());
		
		ret.append(tr);
		ret.append(System.lineSeparator());
		
		ret.append(td);
		ret.append(String.valueOf(tests_all));
		ret.append("</td>");
		ret.append(System.lineSeparator());
		
		ret.append(td);
		ret.append(String.valueOf(tests_executed));
		ret.append("</td>");
		ret.append(System.lineSeparator());
		
		ret.append(td);
		ret.append(String.valueOf(tests_terminated));
		ret.append("</td>");
		ret.append(System.lineSeparator());
		
		ret.append(tr_end);
		ret.append(System.lineSeparator());
		
		ret.append(tr);
		ret.append(System.lineSeparator());
		
		ret.append(th);
		ret.append(_bundle.getString("test_runner_result_tests_ignored"));
		ret.append("</th>");
		ret.append(System.lineSeparator());
		
		ret.append(th);
		ret.append(_bundle.getString("test_runner_result_tests_not_exists"));
		ret.append("</th>");
		ret.append(System.lineSeparator());
		
		ret.append(th);
		ret.append(_bundle.getString("test_runner_result_right"));
		ret.append("</th>");
		ret.append(System.lineSeparator());
		
		ret.append(tr_end);
		ret.append(System.lineSeparator());
		
		ret.append(tr);
		ret.append(System.lineSeparator());
		
		ret.append(td);
		ret.append(String.valueOf(tests_ignored));
		ret.append("</td>");
		ret.append(System.lineSeparator());
		
		ret.append(td);
		ret.append(String.valueOf(tests_not_exists));
		ret.append("</td>");
		ret.append(System.lineSeparator());
		
		ret.append(td);
		ret.append(String.valueOf(right));
		ret.append("</td>");
		ret.append(System.lineSeparator());
		
		ret.append(tr_end);
		ret.append(System.lineSeparator());
		
		ret.append(tr);
		ret.append(System.lineSeparator());
		
		ret.append(th);
		ret.append(_bundle.getString("test_runner_result_wrong"));
		ret.append("</th>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t\t<th colspan=\"2\">");
		ret.append(_bundle.getString("test_runner_result_duration"));
		ret.append("</th>");
		ret.append(System.lineSeparator());

		ret.append(tr_end);
		ret.append(System.lineSeparator());
		
		ret.append(tr);
		ret.append(System.lineSeparator());
		
		ret.append(td);
		ret.append(String.valueOf(wrong));
		ret.append("</td>");
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t\t<td colspan=\"2\">");
		ret.append(HelperCalendar.timeToString(duration));
		ret.append("</td>");
		ret.append(System.lineSeparator());

		ret.append(tr_end);
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t</table>");
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
	protected String exec(String name, TestSuite suite, Test test) {
		StringBuilder ret = new StringBuilder("java -cp ");
		ret.append(createClasspath());
		ret.append(createProperty());
		ret.append("org.junit.runner.JUnitCore ");
		ret.append(name);
		
		return ret.toString();
	}
	
	
	/**
	 * Detected from the console output was carried the test.
	 * 
	 * @param test The actual test
	 */
	@Override
	protected void evaluation(Test test) {
		String[] lines = test.getConsole().split("<br/>");
		
		for (int i = 0; i < lines.length; i++) {
			if (lines[i].indexOf("OK (") > -1) {
				String ok = new String("OK (");
				
				String tmp = lines[i].substring(ok.length(), 
						lines[i].indexOf(" test"));
				((Junit)test).setOk(Integer.valueOf(tmp));
			} else if (lines[i].indexOf("Tests run") > -1) {
				String ok = new String("Tests run: ");
				String fail = new String(",  Failures: ");
				int indexOk = ok.length();
				int indexFail = lines[i].indexOf(fail);

				((Junit)test).setOk(Integer.valueOf(lines[i].substring(indexOk, 
								indexFail)));
				
				indexFail += fail.length();
				((Junit)test).setFail(Integer.valueOf(
						lines[i].substring(indexFail)));
			}
		}
	}
	
	/**
	 * To executed the test without a separate JVM.
	 * 
	 * @param name Name of the test file
	 * 
	 * @param test The object of Test.
	 * 
	 * @param exit The exit status.
	 * 
	 * @return True if the test has been executed. False if not.
	 * 
	 * @deprecated Use {@link #runWithoutJvm(String, Test)}
	 */
	@Override
	protected boolean runWithoutJvm(String name, Test test, int exit) {
		return runWithoutJvm(name, test);
	}
	
	/**
	 * To executed the test without a separate JVM.
	 * 
	 * @param name Name of the test file
	 * 
	 * @param test The object of Test.
	 * 
	 * @return True if the test has been executed. False if not.
	 */
	@Override
	protected boolean runWithoutJvm(String name, Test test) {
		try {
			test.setStart();
			
			Class<?> testclass = getClass().getClassLoader().loadClass(name);
			Result result = JUnitCore.runClasses(testclass);
			
			test.setEnd();
			
			StringBuilder error = new StringBuilder();
			for (Failure failure : result.getFailures()) {
				error.append(failure.toString());
				error.append(System.lineSeparator());
			}
			test.setError(error.toString());
			test.setStringConsole(new String());
			
			((Junit)test).setFail(result.getFailureCount());
			((Junit)test).setOk(result.getRunCount());
		} catch (ClassNotFoundException e) {
			return false;
		}
		
		return true;
	}
}
