package org.testsuite.checksource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

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

/**
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class Parser {
	
	private static final int RUN_SET_BREAKPOINTS = 1;
	
	private static final int RUN_JDB = 2;
	
	private static final int RUN_RUN = 3; 
	
	/**
	 * Saves the BufferedWriter
	 */
	private BufferedWriter _writer;
	
	/**
	 * Saves the BufferedReader
	 */
	private InputStreamReader _reader;
	
	/**
	 * Saves the file for tests
	 */
	private String _testFile;
	
	/**
	 * Saves the file for source code
	 */
	private String _sourceFile;
	
	/**
	 * Saves the list of breakpoints
	 */
	private List<String> _breakpoints;
	
	/**
	 * Saves the list of source lines
	 */
	private List<SourceLine> _source;
	
	/**
	 * Saves the class name
	 */
	private String _className;
	
	/**
	 * Saves the inputs from jdb
	 */
	private List<String> _input;
	
	/**
	 * Initialize the parser
	 */
	public Parser(String testFile, String sourceFile, List<SourceLine> lines) {
		_testFile = testFile;
		_sourceFile = sourceFile;
		_input = new ArrayList<String>();
		_breakpoints = new ArrayList<String>();
		_source = lines;
		_className = _sourceFile.replaceAll(File.separator, ".").substring(4, 
				_sourceFile.replaceAll(File.separator, ".").indexOf(".java"));
	}
	
	/**
	 * @param methods
	 * @param lines
	 * @param testFile
	 * @param sourceFile
	 */
	public void parse(List<CSMethod> methods) {
		if (!new File(_sourceFile).exists())
			return;
		
		if (!new File(_testFile).exists())
			return;

		System.out.println(methods.size());
		for (int method = 0; method < methods.size(); method++)
			_breakpoints.add(_className + ":" + String.valueOf(
							methods.get(method).getBreakpoint()));
		
		try {
			Process p = Runtime.getRuntime().exec("jdb -classpath bin:" +
					"/usr/share/eclipse/plugins/org.junit_4.12.0.v201504281640/" +
					"junit.jar:lib/mockito-all-1.10.19.jar:lib/powermock-mockito" +
					"-1.6.3-full.jar:lib/cglib-nodep-2.2.2.jar:lib/javassist-" +
					"3.20.0-GA.jar:lib/objenesis-2.1.jar:/usr/share/eclipse/" +
					"plugins//org.hamcrest.core_1.3.0.v201303031735.jar " +
					"-Dtesting=\"true\" -sourcepath src");
			
			_writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
			_reader = new InputStreamReader(p.getInputStream());
			
			run();
			
			_writer.close();
			_reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private String read() throws IOException {
		char[] buffer = new char[1024];
		String ret = new String();
		int offset = 0;
		
		try {
			while (_reader.ready()) {
				int read = _reader.read(buffer, offset, 1023);
				if (read > 0) {
					ret += String.valueOf(buffer);
					offset += read;
				}
			}
		} catch (IndexOutOfBoundsException e) {
		}
		
		return ret;
	}
	
	private void stopAtList() throws IOException {
		for (int bp = 0; bp < _breakpoints.size(); bp++) {
			_writer.write("stop at ");
			_writer.write(_breakpoints.get(bp));
			_writer.newLine();
			_writer.flush();
		}
	}
	
	private void jdb() throws IOException {
		_writer.write("run org.junit.runner.JUnitCore " + 
				_testFile.replaceAll(File.separator, ".").substring(4, 
						_testFile.replaceAll(File.separator, ".").indexOf(
								".java")));
		_writer.newLine();
		_writer.flush();
	}
	
	private void next() throws IOException {
		_writer.write("next");
		_writer.newLine();
		_writer.flush();
	}
	
	private void clearBreakpoints() throws IOException {
		for (int i = 0; i < _breakpoints.size(); i++) {
			_writer.write("clear ");
			_writer.write(_breakpoints.get(i));
			_writer.newLine();
			_writer.flush();
		}
	}
	
	private void cont() throws IOException {
		_writer.write("cont");
		_writer.newLine();
		_writer.flush();
	}
	
	private void run() throws IOException {
		int run = RUN_SET_BREAKPOINTS;
		
		if (_breakpoints.size() == 0)
			run = RUN_JDB;
		
		boolean loop = true;
		String threadName = new String();
		String methodName = new String();
		boolean breakpoint = false;
		int line = -1;
		while (loop)  {
			while (!_reader.ready()) {
			}
			String[] read = read().split(System.lineSeparator());
			for (int i = 0; i < read.length; i++) {
				_input.add(read[i]);
				System.err.println(read[i]);
				if ((read[i].indexOf(">") > -1) && 
						(run == RUN_SET_BREAKPOINTS)) {
					stopAtList();
					run = RUN_JDB;
				} else if ((read[i].indexOf(">") > -1) && (run == RUN_JDB)) {
					jdb();
					run = RUN_RUN;
				} else if ((read[i].indexOf(threadName + "[") > -1) && 
						(run == RUN_RUN)) {
					if (breakpoint)
						next();
				} else if ((read[i].startsWith("Breakpoint hit:"))) {
					breakpoint = true;
					clearBreakpoints(); // clear breakpoint list
				} else if (read[i].indexOf("The application exited") > -1) {
					breakpoint = false;
					loop = false;
				}
				
				if (breakpoint && (read[i]).indexOf("\"thread=") > -1) {
					String[] str = read[i].split(", ");
					threadName = str[0].substring(str[0].indexOf("=") + 1, 
							str[0].lastIndexOf("\""));
					String className = str[1].substring(0, str[1].lastIndexOf("."));
					line = Integer.parseInt(str[2].substring(
							str[2].indexOf("=") + 1, str[2].indexOf(" ")));
					
					if (methodName.isEmpty())
						methodName = str[1];
					else if (!methodName.equals(str[1])) {
						methodName = new String();
						breakpoint = false;
						stopAtList();
						cont();
					} else if (_className.equals(className)) {
//						if (_source.get(line - 1).getClassName().indexOf(
//								className) > -1)
						System.out.println(line + ": tested");
						_source.get(line - 1).setLineTested(true);
					}
				}
			}
		}
	}

	public void debug() {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter("test.debug"));
			for (int i = 0; i < _input.size(); i++) {
				bw.write(_input.get(i));
				bw.newLine();
			}
		} catch (IOException  e) {
			e.printStackTrace();
		}
		finally {
			try  {
				if (bw != null)
					bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		List<CSMethod> methods = new ArrayList<CSMethod>();

//		CSMethod method = new CSMethod();
//		method.setBreakpoint(69);
//		methods.add(method);
		
		CSMethod method = new CSMethod();
		method.setBreakpoint(82);
		methods.add(method);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		for (int i = 0; i < 300; i++) {
			SourceLine line = new SourceLine();
			line.setClassName("CheckSource");
			lines.add(i, line);
		}
		
		SourceLine line = new SourceLine();
		line.setLineNumber(69);
		line.setLine("setNameSourceFile(nameSrc);");
		line.setClassName("org.testsuite.checksource.CheckSource");
		lines.add(68, line);

		line = new SourceLine();
		line.setLineNumber(70);
		line.setClassName("org.testsuite.checksource.CheckSource");
		line.setLine("setNameTestFile(nameTest);");
		lines.add(69, line);

		line = new SourceLine();
		line.setLineNumber(71);
		line.setClassName("org.testsuite.checksource.CheckSource");
		line.setLine("setNameResultFile(nameResult);");
		lines.add(70, line);

		line = new SourceLine();
		line.setLineNumber(72);
		line.setClassName("org.testsuite.checksource.CheckSource");
		line.setLine("_source = new SourceFile();");
		lines.add(71, line);

		line = new SourceLine();
		line.setLineNumber(73);
		line.setClassName("org.testsuite.checksource.CheckSource");
		line.setLine("_source.setFileName(_nameSrc);");
		lines.add(72, line);
		
		Parser parser = new Parser(
				"src/tests/testsuite/checksource/TestCheckSource.java", 
				"src/org/testsuite/checksource/CheckSource.java", lines);
		
		parser.parse(methods);
		parser.debug();
	}
}
