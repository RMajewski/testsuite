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
import org.testsuite.data.Config;
import org.testsuite.data.Test;
import org.testsuite.data.TestSuite;
import org.testsuite.helper.HelperCalendar;

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
	 */
	public JemmyTestRunner() {
		super();
	}

	/**
	 * Initialize the data of the class.
	 * 
	 * @param config The configuration.
	 * 
	 * @deprecated use {@link #JemmyTestRunner()}
	 */
	public JemmyTestRunner(Config config) {
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
					_suites.get(suite).getTest(test).getConsole(), 
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
					if (_suites.get(suite).getTest(test).getExitStatus() == 
							EXIT_OK)
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
			
			ret.append("\t\t\t\t\t\t<td colspan=\"2\">");
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
			if (_suites.get(suite).getTest(test).getExitStatus() == 
					EXIT_OK)
				right++;
			else
				wrong++;
			duration += _suites.get(suite).getTest(test).getDurationTime();
		}
		
		ret.append(td);
		ret.append("&nbsp;</td>");
		ret.append(System.lineSeparator());
		
		
		ret.append(td);
		ret.append(_bundle.getString("createHtmlTableHead_ok"));
		ret.append(": ");
		ret.append(right);
		ret.append("<br/>");
		ret.append(_bundle.getString("createHtmlTableHead_exception"));
		ret.append(": ");
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
				duration += _suites.get(suite).getTest(test).getDurationTime();
			
				if (_suites.get(suite).getTest(test).isExecuted() && 
						_suites.get(suite).getTest(test).isExists()) {
					tests_executed++;
					
					if (_suites.get(suite).getTest(test).isTerminated())
						tests_terminated++;
					
					else if (_suites.get(suite).getTest(test).getExitStatus() == EXIT_OK)
						right++;
					else
						wrong++;
				} else if (!_suites.get(suite).getTest(test).isExists())
					tests_not_exists++;
				else
					tests_ignored++;
			}
		}
		
		String tr = "\t\t\t\t\t<tr>";
		String tr_end = "\t\t\t\t\t</tr>";
		String th = "\t\t\t\t\t\t<th>";
		String td = "\t\t\t\t\t\t<td>";
		
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
	protected String exec(String name, TestSuite suite, Test test) {
		StringBuffer ret = new StringBuffer("java -cp ");
		ret.append(createClasspath());
		ret.append(createProperty());
		ret.append(name);
		
		return ret.toString();
	}
	
	/**
	 * Without separate JVM no test is performed.
	 * 
	 * @param name Name of the test file
	 * 
	 * @param test The object of Test.
	 * 
	 * @param exit The exit status.
	 * 
	 * @return False, because no test was executed.
	 * 
	 * @deprecated Use {@link #runWithoutJvm(String, Test)}
	 */
	@Override
	protected boolean runWithoutJvm(String name, Test test, int exit) {
		return false;
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
		return false;
	}
}
