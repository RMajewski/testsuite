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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.testsuite.checksource.CSConfig;
import org.testsuite.data.Config;
import org.testsuite.data.TestSuite;

/**
 * Saves the configuration into a xml file
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class ConfigSaver {
	/**
	 * Saves the configuration into a xml file
	 * 
	 * @param config General configuration
	 * 
	 * @param list List of test runner classes
	 * 
	 * @param file Configuration file
	 * 
	 * @deprecated Use {@link #save(List, File)}
	 */
	public static void save(Config config, List<TestRunner> list, File file) {
		save(list, file);
	}
	
	/**
	 * Saves the configuration into a xml file
	 * 
	 * @param list List of test runner classes
	 * 
	 * @param file Configuration file
	 */
	public static void save(List<TestRunner> list, File file) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file));
			bw.write("<?xml version=\"1.0\"?>");
			bw.newLine();
			bw.write("<configurationFile");
			bw.write(" xmlns=\"https://github.com/RMajewski/testsuite\"");
			bw.write(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
			bw.write(" xsi:schemaLocation=\"https://github.com/RMajewski/");
			bw.write("testsuite https://raw.githubusercontent.com/RMajewski/");
			bw.write("testsuite/master/src/resources/xml/config.xsd\"");
			bw.write(">");
			bw.newLine();
			
			writeConfig(bw);
			
			for (int runner = 0; runner < list.size(); runner++)
				writeTestRunner(bw, list.get(runner));
			
			bw.write("</configurationFile>");
			bw.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null)
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	/**
	 * Writes the general configuration into the buffered writer
	 * 
	 * @param bw Open buffered writer
	 */ 
	private static void writeConfig(BufferedWriter bw) throws IOException {
		bw.write("\t<config>");
		bw.newLine();
		
		if (!Config.getInstance().getPathResult().isEmpty()) {
			bw.write("\t\t<resultPath>");
			bw.write(Config.getInstance().getPathResult());
			bw.write("</resultPath>");
			bw.newLine();
		}
		
		if (!Config.getInstance().getPathSrc().isEmpty()) {
			bw.write("\t\t<srcPath>");
			bw.write(Config.getInstance().getPathSrc());
			bw.write("</srcPath>");
			bw.newLine();
		}
		
		if (!Config.getInstance().getPathLibrary().isEmpty()) {
			bw.write("\t\t<libPath>");
			bw.write(Config.getInstance().getPathLibrary());
			bw.write("</libPath>");
			bw.newLine();
		}

		if (Config.getInstance().getMaxDuration() > 0) {
			bw.write("\t\t<maxDuration>");
			bw.write(String.valueOf(Config.getInstance().getMaxDuration()));
			bw.write("</maxDuration>");
			bw.newLine();
		}

		bw.write("\t\t<htmlOut>");
		bw.write(String.valueOf(String.valueOf(
				Config.getInstance().isCreateHtml())));
		bw.write("</htmlOut>");
		bw.newLine();
		
		if (Config.getInstance().propertyCount() > 0) {
			bw.write("\t\t<systemProperty>");
			bw.newLine();

			for (int i = 0; i < Config.getInstance().propertyCount(); i++) {
				bw.write("\t\t\t<property>");
				bw.write(Config.getInstance().getProperty(i));
				bw.write("</property>");
				bw.newLine();
			}
			
			bw.write("\t\t</systemProperty>");
			bw.newLine();
		}
		
		if (Config.getInstance().toDoWordsCount() > 0) {
			bw.write("\t\t<todo>");
			bw.newLine();

			for (int i = 0; i < Config.getInstance().toDoWordsCount(); i++) {
				bw.write("\t\t\t<name>");
				bw.write(Config.getInstance().getToDoWord(i));
				bw.write("</name>");
				bw.newLine();
			}
			
			bw.write("\t\t</todo>");
			bw.newLine();
		}
		
		writeCheckSourceConfig(bw);
		
		writeClassPath(bw, Config.getInstance().getClassPathList());
		
		bw.write("\t</config>");
		bw.newLine();
	}
	
	/**
	 * Writes the configuration for the check source tests in the configuration
	 * file.
	 * 
	 * @param bw BufferedWriter to write the configuration to the configuration
	 * file. 
	 */
	private static void writeCheckSourceConfig(BufferedWriter bw)
			throws IOException {
		bw.write("\t\t<checkSourceConfiguration>");
		bw.newLine();
		
		if (CSConfig.getInstance().getLineWidth() > 0) {
			bw.write("\t\t\t<lineWidth");
			
			if (CSConfig.getInstance().getTabSpace() > -1) {
				bw.write(" tabSpace=\"");
				bw.write(String.valueOf(CSConfig.getInstance().getTabSpace()));
				bw.write("\"");
			}
			
			bw.write(">");
			bw.write(CSConfig.getInstance().getLineWidth());
			bw.write("</lineWidth>");
			bw.newLine();
		}
		
		if (CSConfig.getInstance().isListNoneTestedFiles() || 
				!CSConfig.getInstance().getNoneListedPath().isEmpty()) {
			bw.write("\t\t\t<noneTestedList");
			
			if (!CSConfig.getInstance().getNoneListedPath().isEmpty()) {
				bw.write(" path=\"");
				bw.write(CSConfig.getInstance().getNoneListedPath());
				bw.write("\"");
			}
			
			bw.write(">");
			bw.write(String.valueOf(
					CSConfig.getInstance().isListNoneTestedFiles()));
			bw.write("</noneTestedList>");
			bw.newLine();
		}
		
		bw.write("\t\t\t<parser timeout=\"");
		bw.write(String.valueOf(CSConfig.getInstance().getParserTimeout()));
		bw.write("\" parse=\"");
		bw.write(String.valueOf(CSConfig.getInstance().getParserParse()));
		bw.write("\">");
		bw.write(CSConfig.getInstance().getParserName());
		bw.write("</parser>");
		bw.newLine();
		
		bw.write("\t\t\t<path>");
		bw.write(CSConfig.getInstance().getPathCheckSourceTests());
		bw.write("</path>");
		bw.newLine();
		
		for (int i = 0; i < CSConfig.getInstance().testCount(); i++) {
			bw.write("\t\t\t<test>");
			bw.write(CSConfig.getInstance().getTestName(i));
			bw.write("</test>");
			bw.newLine();
		}
		
		bw.write("\t\t</checkSourceConfiguration>");
		bw.newLine();
	}

	/**
	 * Writes the list of test runner into the buffered writer
	 *  
	 * @param bw Opened buffered writer
	 * 
	 * @param runner Test runner class
	 */
	private static void writeTestRunner(BufferedWriter bw, 
			TestRunner runner) throws IOException {
		bw.write("\t<testGroup>");
		bw.newLine();
		
		bw.write("\t\t<testRunner>");
		bw.write(runner.getClass().getName());
		bw.write("</testRunner>");
		bw.newLine();
		
		if (!runner.getDescription().isEmpty()) {
			bw.write("\t\t<description>");
			bw.write(runner.getDescription());
			bw.write("</description>");
			bw.newLine();
		}
		
		bw.write("\t\t<extension>");
		bw.write(runner.getFileExtension());
		bw.write("</extension>");
		bw.newLine();

		if (runner.libraryCount() > 0) {
			bw.write("\t\t<libraries>");
			bw.newLine();

			for (int i = 0; i < runner.libraryCount(); i++)
				writeLibrary(bw, runner.getLibrary(i).getFileName(),
						runner.getLibrary(i).getPath(),
						runner.getLibrary(i).getName(),
						runner.getLibrary(i).getVersion());
			
			bw.write("\t\t</libraries>");
			bw.newLine();
		}

		writeClassPath(bw, runner.getClassPathList());

		if (runner.testSuiteCount() > 0)
			for (int i = 0; i < runner.testSuiteCount(); i++)
				writeTestSuite(bw, runner.getTestSuite(i));
		
		bw.write("\t</testGroup>");
		bw.newLine();
	}

	/**
	 * Write a library into the buffered writer
	 * 
	 * @param bw Opened buffered writer
	 * 
	 * @param fileName File name of the library
	 * 
	 * @param path Path of the library
	 * 
	 * @param name Name of the library
	 * 
	 * @param version Version of the library
	 */
	private static void writeLibrary(BufferedWriter bw, String fileName, 
			String path, String name, String version) throws IOException {
		bw.write("\t\t\t<library");
		
		if (!version.isEmpty()) {
			bw.write(" version=\"");
			bw.write(version);
			bw.write("\"");
		}
		
		if (!name.isEmpty()) {
			bw.write(" name=\"");
			bw.write(name);
			bw.write("\"");
		}
		
		if (!path.isEmpty()) {
			bw.write(" path=\"");
			bw.write(path);
			bw.write("\"");
		}
		
		bw.write(">");
		bw.write(fileName);
		bw.write("</library>");
		bw.newLine();
	}

	/**
	 * Writes the test suite into the buffered writer
	 * 
	 * @param bw Opened buffered writer
	 * 
	 * @param suite Test suite to write into the buffered writer
	 */
	private static void writeTestSuite(BufferedWriter bw, TestSuite suite) 
			throws IOException{
		bw.write("\t\t<testSuite>");
		bw.newLine();
		
		bw.write("\t\t\t<name>");
		bw.write(suite.getName());
		bw.write("</name>");
		bw.newLine();
		
		bw.write("\t\t\t<package>");
		bw.write(suite.getPackage());
		bw.write("</package>");
		bw.newLine();
		
		for (int i = 0; i < suite.testCount(); i++) {
			bw.write("\t\t\t<test");
			
			if (!suite.getTest(i).isExecuted())
				bw.write(" execute=\"false\"");
			
			if (!suite.getTest(i).isJvm())
				bw.write(" jvm=\"false\"");
			
			if (suite.getTest(i).isCheckSource()) {
				bw.write(" checkSource=\"");
				bw.write(suite.getTest(i).getCheckSource());
				bw.write("\"");
			}
			
			bw.write(">");
			bw.write(suite.getTest(i).getName());
			bw.write("</test>");
			bw.newLine();
		}
		
		bw.write("\t\t</testSuite>");
		bw.newLine();
	}
	
	/**
	 * Write the class paths into the BufferWriter
	 * 
	 * @param bw Opened buffered writer
	 * 
	 * @param list List of class paths.
	 */
	private static void writeClassPath(BufferedWriter bw, List<String> list) 
			throws IOException{
		if (list.size() > 0) {
			bw.write("\t\t<classpath>");
			bw.newLine();

			for (int i = 0; i < list.size(); i++) {
				bw.write("\t\t\t<path>");
				bw.write(list.get(i));
				bw.write("</path>");
				bw.newLine();
			}
			
			bw.write("\t\t</classpath>");
			bw.newLine();
		}
	}
}
