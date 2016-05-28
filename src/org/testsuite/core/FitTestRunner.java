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
import org.testsuite.data.Fit;
import org.testsuite.data.Test;
import org.testsuite.data.TestSuite;
import org.testsuite.helper.HelperCalendar;

/**
 * Executes the Fit tests.
 * 
 * @author René Majewski
 *
 * @version 0.2
 */
public class FitTestRunner extends TestRunner {
	
	/**
	 * Initialize the data of the class.
	 * 
	 * <strong>Important</strong>: It must also be initialized config. Please
	 * use {@link #setConfig(Config)}.
	 */
	public FitTestRunner() {
		super();
	}
	
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
	 * Creates from the data of the current test the HTML output.
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
		
		if (((Fit)_suites.get(suite).getTest(test)).getIgnore() > 0)
			cl = " class=\"ignore\"";
		
		if (((Fit)_suites.get(suite).getTest(test)).getException() > 0)
			cl = " class=\"exception\"";
		
		if (!_suites.get(suite).getTest(test).isExecuted() ||
				_suites.get(suite).getTest(test).isTerminated())
			cl = " class=\"ignore\"";
		
		String td = "\t\t\t\t\t\t<td" + cl + ">";
		
		StringBuilder ret = new StringBuilder(td);
		
		if (_suites.get(suite).getTest(test).isExists()) {
			if (_suites.get(suite).getTest(test).isExecuted() &&
					!_suites.get(suite).getTest(test).isTerminated()) {
				ret.append("<a href=\"");
				ret.append(_config.getPathSuitesResult());
				ret.append(File.separator);
				ret.append(_suites.get(suite).getPackage().replaceAll("\\.", 
						File.separator));
				ret.append(File.separator);
				ret.append(_suites.get(suite).getTest(test).getName());
				ret.append(".html\">");
				ret.append(_suites.get(suite).getTest(test).getName());
				ret.append("</a>");
			} else {
				ret.append(_suites.get(suite).getTest(test).getName());
			}
			ret.append(html.generateTestOut(_suites.get(suite).getId(), 
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
				if (_suites.get(suite).getTest(test).isTerminated()) {
					ret.append("\t\t\t\t\t\t<td class=\"ignore\" ");
					ret.append("colspan=\"4\">");
					ret.append(_bundle.getString("createHtmlColumn_terminated"));
					ret.append("</td>");
					ret.append(System.lineSeparator());
				} else {
					ret.append(td);
					ret.append(((Fit)_suites.get(suite).getTest(test)).getOk());
					ret.append("</td>");
					ret.append(System.lineSeparator());
					
					ret.append(td);
					ret.append(((Fit)_suites.get(suite).getTest(test))
								.getFail());
					ret.append("</td>");
					ret.append(System.lineSeparator());
					
					ret.append(td);
					ret.append(((Fit)_suites.get(suite).getTest(test))
								.getIgnore());
					ret.append("</td>");
					ret.append(System.lineSeparator());
					
					ret.append(td);
					ret.append(((Fit)_suites.get(suite).getTest(test)).getException());
					ret.append("</td>");
					ret.append(System.lineSeparator());
				}
				
				ret.append(td);
				ret.append(
						((Fit)_suites.get(suite).getTest(test))
							.getDurationTimeFormattedString());
			} else {
				ret.append("\t\t\t\t\t\t<td");
				ret.append(cl);
				ret.append(" colspan=\"5\">");
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
		int ignore = 0;
		int exception = 0;
		long duration = 0;
		
		for (int test = 0; test < _suites.get(suite).testCount(); test++) {
			right += ((Fit)_suites.get(suite).getTest(test)).getOk();
			wrong += ((Fit)_suites.get(suite).getTest(test)).getFail();
			ignore += ((Fit)_suites.get(suite).getTest(test)).getIgnore();
			exception += ((Fit)_suites.get(suite).getTest(test)).getException();
			duration += _suites.get(suite).getTest(test).getDurationTime();
		}
		
		ret.append(td);
		ret.append("&nbsp;</td>");
		ret.append(System.lineSeparator());
		
		ret.append(td);
		ret.append(String.valueOf(right));
		ret.append("</td>");
		ret.append(System.lineSeparator());
		
		ret.append(td);
		ret.append(String.valueOf(wrong));
		ret.append("</td>");
		ret.append(System.lineSeparator());
		
		ret.append(td);
		ret.append(String.valueOf(ignore));
		ret.append("</td>");
		ret.append(System.lineSeparator());
		
		ret.append(td);
		ret.append(String.valueOf(exception));
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
		int ignore = 0;
		int exception = 0;
		int tests_all = 0;
		int tests_terminated = 0;
		int tests_ignored = 0;
		int tests_executed = 0;
		int tests_not_exists = 0;
		long duration = 0;

		for (int suite = 0; suite < _suites.size(); suite++) {
			tests_all += _suites.get(suite).testCount();
			for (int test = 0; test < _suites.get(suite).testCount(); test++) {
				right += ((Fit)_suites.get(suite).getTest(test)).getOk();
				wrong += ((Fit)_suites.get(suite).getTest(test)).getFail();
				ignore += ((Fit)_suites.get(suite).getTest(test)).getIgnore();
				exception += ((Fit)_suites.get(suite).getTest(test)).getException();
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
		
		ret.append(th);
		ret.append(_bundle.getString("test_runner_result_ignore"));
		ret.append("</th>");
		ret.append(System.lineSeparator());
		
		ret.append(th);
		ret.append(_bundle.getString("test_runner_result_exception"));
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
		
		ret.append(td);
		ret.append(String.valueOf(ignore));
		ret.append("</td>");
		ret.append(System.lineSeparator());
		
		ret.append(td);
		ret.append(String.valueOf(exception));
		ret.append("</td>");
		ret.append(System.lineSeparator());
		
		ret.append(tr_end);
		ret.append(System.lineSeparator());
		
		ret.append(tr);
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t\t<th colspan=\"3\">");
		ret.append(_bundle.getString("test_runner_result_duration"));
		ret.append("</th>");
		ret.append(System.lineSeparator());

		ret.append(tr_end);
		ret.append(System.lineSeparator());
		
		ret.append(tr);
		ret.append(System.lineSeparator());
		
		ret.append("\t\t\t\t\t\t<td colspan=\"3\">");
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
		Fit ret = new Fit();
		ret.setName(name);
		ret.setId(id);
		return ret;
	}
	
	/**
	 * Creates the command to execute.
	 * 
	 * @param test Name of the test class or name of file
	 * 
	 * @param suite The actual test suite
	 * 
	 * @param test The actual test
	 * 
	 * @return Command to execute
	 */
	@Override
	public String exec(String name, TestSuite suite, Test test) {
		StringBuilder ret = new StringBuilder("java -cp ");
		ret.append(createClasspath());
		ret.append(createProperty());
		ret.append("fit.FileRunner ");
		
		ret.append(_config.getPathSrc());
		ret.append(File.separator);
		ret.append(name.replaceAll("\\.", File.separator));
		ret.append(".");
		ret.append(_fileExtension);
		ret.append(" ");
		
		// Path for result files
		String resultPath = _config.getPathResult() + File.separator + 
				_config.getPathSuitesResult() + File.separator + 
				suite.getPackage().replaceAll("\\.", File.separator);
		File r = new File(resultPath);
		if (!r.exists()) {
			r.mkdirs();
		}
		
		// File for the result
		ret.append(resultPath);
		ret.append(File.separator);
		ret.append(test.getName());
		ret.append(".html");
		
		return ret.toString();
	}
	
	/**
	 * Detected from the error output was carried the test.
	 * 
	 * @param test The actual test
	 */
	@Override
	protected void evaluation(Test test) {
		String[] lines = test.getError().split("<br/>");
		
		for (int i = 0; i < lines.length; i++){
			if ((lines[i].indexOf("right") > -1) &&
					(lines[i].indexOf("wrong") > -1)) {
				String[] tmp = lines[i].split(" ");
				((Fit)test).setOk(Integer.valueOf(tmp[0]));
				((Fit)test).setFail(Integer.valueOf(tmp[2]));
				((Fit)test).setIgnore(Integer.valueOf(tmp[4]));
				((Fit)test).setException(Integer.valueOf(tmp[6]));
			}
		}
	}
}
