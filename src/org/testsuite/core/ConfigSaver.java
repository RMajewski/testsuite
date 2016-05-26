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
	 */
	public static void save(Config config, List<TestRunner> list, File file) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file));
			bw.write("<?xml version=\"1.0\"?>");
			bw.newLine();
			bw.write("<configurationFile>");
			bw.newLine();
			
			writeConfig(bw, config);
			
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
	 * 
	 * @param config General configuration
	 */
	private static void writeConfig(BufferedWriter bw, Config config) 
			throws IOException {
		bw.write("\t<config>");
		bw.newLine();
		
		if (!config.getPathResult().isEmpty()) {
			bw.write("\t\t<resultPath>");
			bw.write(config.getPathResult());
			bw.write("</resultPath>");
			bw.newLine();
		}
		
		if (!config.getPathSrc().isEmpty()) {
			bw.write("\t\t<srcPath>");
			bw.write(config.getPathSrc());
			bw.write("</srcPath>");
			bw.newLine();
		}
		
		if (!config.getPathLibrary().isEmpty()) {
			bw.write("\t\t<libPath>");
			bw.write(config.getPathLibrary());
			bw.write("<libPath>");
			bw.newLine();
		}

		if (config.getMaxDuration() > 0) {
			bw.write("\t\t<maxDuration>");
			bw.write(String.valueOf(config.getMaxDuration()));
			bw.write("</maxDuration>");
			bw.newLine();
		}

		bw.write("\t\t<htmlOut>");
		bw.write(String.valueOf(String.valueOf(config.isCreateHtml())));
		bw.write("</htmlOut>");
		bw.newLine();
		
		if (config.propertyCount() > 0) {
			bw.write("\t\t<systemProperty>");
			bw.newLine();

			for (int i = 0; i < config.propertyCount(); i++) {
				bw.write("\t\t\t<property>");
				bw.write(config.getProperty(i));
				bw.write("</property>");
				bw.newLine();
			}
			
			bw.write("\t\t</systemProperty>");
			bw.newLine();
		}
		
		bw.write("\t</config>");
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

		if (runner.libraryCount() > 0) {
			bw.write("\t\t<classpath>");
			bw.newLine();

			for (int i = 0; i < runner.classPathCount(); i++) {
				bw.write("\t\t\t<path>");
				bw.write(runner.getClassPath(i));
				bw.write("</path>");
				bw.newLine();
			}
			
			bw.write("\t\t</classpath>");
			bw.newLine();
		}

		if (runner.testSuiteCount() > 0) {
			bw.write("\t\t<testSuite>");
			bw.newLine();

			for (int i = 0; i < runner.classPathCount(); i++)
				writeTestSuite(bw, runner.getTestSuite(i));
				
			bw.write("\t\t</testSuite>");
			bw.newLine();
		}
		
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
			
			bw.write(">");
			bw.write(suite.getTest(i).getName());
			bw.write("</test>");
			bw.newLine();
		}
		
		bw.write("\t\t</testSuite>");
		bw.newLine();
	}
}
