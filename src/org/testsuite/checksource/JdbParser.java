package org.testsuite.checksource;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.Timer;

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
public class JdbParser implements Parser{
	
	private static final int RUN_SET_BREAKPOINTS = 1;
	
	private static final int RUN_JDB = 2;
	
	private static final int RUN_RUN = 3; 
	
	private static final int RUN_NEXT = 4;
	
	private static final int RUN_CONT = 5;
	
	private static final int RUN_CLEAR_BREAKPOINTS = 6;
	
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
	 * Saves the list of methods
	 */
	private List<CSMethod> _methods;
	
	/**
	 * Saves the class name
	 */
	private String _className;
	
	/**
	 * Saves the inputs from jdb
	 */
	private List<String> _input;
	
	/**
	 * Saves the number of breakpoint
	 */
	private int _breakpointNumber;
	
	/**
	 * Saves, which is to be executed. 
	 */
	private boolean _run;
	
	/**
	 * Saves, this is to be canceled.
	 */
	private boolean _canceled;
	
	/**
	 * Initialize the parser
	 * 
	 * @deprecated Use {@link #JdbParser()}
	 */
	public JdbParser(String testFile, String sourceFile, List<SourceLine> lines) {
		_testFile = testFile;
		_sourceFile = sourceFile;
		_input = new ArrayList<String>();
		_breakpoints = new ArrayList<String>();
		_source = new ArrayList<SourceLine>();
		_className = _sourceFile.replaceAll(File.separator, ".").substring(4, 
				_sourceFile.replaceAll(File.separator, ".").indexOf(".java"));
		_breakpointNumber = -1;
		_run = false;
		_canceled = false;
	}
	
	public JdbParser() {
		_testFile = new String();
		_sourceFile = new String();
		_input = new ArrayList<String>();
		_breakpoints = new ArrayList<String>();
		_source = new ArrayList<SourceLine>();
		_className = new String();
		_breakpointNumber = -1;
		_run = false;
		_canceled = false;
		_methods = new ArrayList<CSMethod>();
	}

	@Override
	public void setSources(List<SourceLine> sources) {
		_source = sources;
	}
	
	@Override
	public void setMethods(List<CSMethod> methods) {
		_methods = methods;
	}
	
	@Override
	public void setSourceFileName(String name) {
		_sourceFile = name;
		_className = _sourceFile.replaceAll(File.separator, ".").substring(4, 
				_sourceFile.replaceAll(File.separator, ".").indexOf(".java"));
	}
	
	@Override
	public void setTestFileName(String name) {
		_testFile = name;
	}
	
	/**
	 * @param methods
	 * @param lines
	 * @param testFile
	 * @param sourceFile
	 */
	@Override
	public void parse() {
		if (!new File(_sourceFile).exists())
			return;
		
		if (!new File(_testFile).exists())
			return;

		for (int method = 0; method < _methods.size(); method++) {
			if (_methods.get(method).getBreakpoint() == -1)
				continue;
			int pb = _methods.get(method).getBreakpoint();
			_breakpoints.add(_className + ":" + String.valueOf(pb));
		}
		
		try {
			final Process p = Runtime.getRuntime().exec("jdb -classpath bin:" +
					"/usr/share/eclipse/plugins/org.junit_4.12.0.v201504281640/" +
					"junit.jar:lib/mockito-all-1.10.19.jar:lib/powermock-mockito" +
					"-1.6.3-full.jar:lib/cglib-nodep-2.2.2.jar:lib/javassist-" +
					"3.20.0-GA.jar:lib/objenesis-2.1.jar:/usr/share/eclipse/" +
					"plugins//org.hamcrest.core_1.3.0.v201303031735.jar " +
					"-Dtesting=\"true\" -sourcepath src");
			
			Timer timer = new Timer(
					(int)CSConfig.getInstance().getParserTimeout(),
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent ae) {
							_canceled = true;
						}
			});
			
			_writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
			_reader = new InputStreamReader(p.getInputStream());
			
			timer.start();
			
			run();
			
			timer.stop();
			
			_writer.close();
			_reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 * 
	 * @deprecated
	 */
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
	
	private String[] readArray() throws IOException {
		List<String> ret = new ArrayList<String>();
		char[] buffer = new char[1];
		String read = new String();
		boolean loop = true;
		while (loop && !_canceled) {
			while(_reader.ready()) {
				if (_reader.read(buffer) == 1) {
					read += String.valueOf(buffer);
					if (read.endsWith(System.lineSeparator())) {
						ret.add(read.substring(0, read.indexOf(
								System.lineSeparator())));
						read = new String();
					}
					
					if (read.indexOf("The application exited") > -1) {
						loop = false;
					} else if (read.indexOf("Nothing suspended") > -1) {
						loop = false;
					}
					
					if (_canceled)
						loop = false;
				}
			}
			
			if (read.isEmpty() && (ret.size() > 0) && !_reader.ready())
				loop = false;
			else if (read.startsWith("> ") && !_run && !_reader.ready()) {
				ret.add(read);
				loop = false;
			} else if ((read.indexOf("[") > -1) && read.endsWith("] ") && 
					_run && !_reader.ready()) {
				ret.add(read);
				loop = false;
			}
			
			if (_canceled)
				loop = false;
		}
		
		return ret.toArray(new String[ret.size()]);
	}
	
	private void stopAtList() throws IOException {
		if ((_breakpointNumber > -1) && (_breakpoints.size() > 0)) {
			_writer.write("stop at ");
			_writer.write(_breakpoints.get(_breakpointNumber));
			_writer.newLine();
			_writer.flush();
			_breakpointNumber++;
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
		if ((_breakpointNumber > -1) && (_breakpoints.size() > 0)) {
			_writer.write("clear ");
			_writer.write(_breakpoints.get(_breakpointNumber));
			_writer.newLine();
			_writer.flush();
			_breakpointNumber++;
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
		
		List<Integer> testedLines = new ArrayList<Integer>();
		
		boolean loop = true;
		String threadName = new String();
		String methodName = new String();
		boolean breakpoint = false;
		int line = -1;
		int back = -1;
		while (loop && !_canceled)  {
			String[] read = readArray();
			for (int i = 0; i < read.length; i++) {
				_input.add(read[i]);
				if (((read[i].indexOf(">") > -1) || 
						(read[i].indexOf(threadName + "[") > -1)) && 
						(run == RUN_SET_BREAKPOINTS)) {
					if (_breakpointNumber == -1)
						_breakpointNumber = 0;
					stopAtList();
					if (_breakpointNumber == _breakpoints.size()) {
						if (back > -1)
							run = back;
						else
							run = RUN_JDB;
						_breakpointNumber = -1;
					}
				} else if ((read[i].indexOf(">") > -1) && (run == RUN_JDB)) {
					jdb();
					_run = true;
					run = RUN_NEXT;
				} else if ((read[i].indexOf(threadName + "[") > -1) && 
						(run == RUN_NEXT) && breakpoint) {
						next();
				} else if ((read[i].indexOf(threadName + "[") > -1) &&
						(run == RUN_CONT) && !breakpoint) {
					cont();
					run = RUN_NEXT;
				} else if ((read[i].indexOf(threadName + "[") > -1) && 
						(run == RUN_CLEAR_BREAKPOINTS)) {
					if (_breakpointNumber == -1)
						_breakpointNumber = 0;
					clearBreakpoints();
					if (_breakpointNumber == _breakpoints.size()) {
						run = RUN_NEXT;
						_breakpointNumber = -1;
					}
				} else if (read[i].indexOf("Breakpoint hit:") > -1) {
					breakpoint = true;
					run = RUN_CLEAR_BREAKPOINTS;
				} else if (read[i].indexOf("The application exited") > -1) {
					breakpoint = false;
					loop = false;
					_run = false;
				} else if (read[i].indexOf("Nothing suspended") > -1) {
					breakpoint = false;
					loop = false;
					_run = false;
				}
				
				if (breakpoint && (read[i]).indexOf("\"thread=") > -1) {
					String[] str = read[i].split(", ");
					threadName = str[0].substring(str[0].indexOf("=") + 1, 
							str[0].lastIndexOf("\""));
					String className = str[1].substring(0, str[1].lastIndexOf("."));
					line = Integer.parseInt(str[2].substring(
							str[2].indexOf("=") + 1, str[2].indexOf(" ")));
					
					if ((testedLines.indexOf(Integer.valueOf(line)) == -1) &&
							(_className.equals(className)))
						testedLines.add(Integer.valueOf(line));
						
					if (methodName.isEmpty())
						methodName = str[1];
					else if (!methodName.equals(str[1])) {
						methodName = new String();
						breakpoint = false;
						back = RUN_CONT;
						run = RUN_SET_BREAKPOINTS;
					}
				}
			}
		}
		
		for (int i = 0; i < testedLines.size(); i++)
			_source.get(testedLines.get(i).intValue() - 1).setLineTested(true);
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
	
	@Override
	public void prepaireSource() {
		for (int method = 0; method < _methods.size(); method++) {
			if ((_methods.get(method).getLine() == -1) || 
					(_methods.get(method).getLastLineNumber() == -1))
				continue;
			
			int start = _methods.get(method).getLine() - 1;
			int start2 = -1;
			boolean tested = false;
			
			for (int ln = start; 
					ln < (_methods.get(method).getLastLineNumber()); ln++) {
				String line = _source.get(ln).getLine().trim();
				if ((start2 == -1) && line.endsWith("{"))
					start2 = ln;
				
				if (!tested && _source.get(ln).isLineTested())
					tested = true;
				
				boolean prev = _source.get(ln - 1).isLineTested();
				if ((line.endsWith("}") || line.isEmpty()) && prev)
					_source.get(ln).setLineTested(true);
				
				if (((line.indexOf("try") > -1) || 
						(line.indexOf("finally") > -1)) && prev)
						_source.get(ln).setLineTested(true);
			}
			
			if (tested && (start > -1) && (start2 > -1)) {
				int line = start;
				do {
					_source.get(line).setLineTested(true);
					line++;
				} while (line <= start2);
			}
		}
	}
}