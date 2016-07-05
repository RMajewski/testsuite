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

package tests.testsuite.checksource;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.testsuite.checksource.CSMethod;
import org.testsuite.checksource.SimpleParser;
import org.testsuite.checksource.SourceLine;

/**
 * Tests the class {@link org.testsuite.checksource.SimpleParser}.
 * 
 * @author René Majewski
 * 
 * @version 0.1
 */
public class TestParser {
	/**
	 * Initialize the tests
	 */
	@Before
	public void setUp() throws Exception{
		Field field = SimpleParser.class.getDeclaredField("_endLoop");
		field.setAccessible(true);
		field.set(null, -1);
	}
	
	/**
	 * Gets the field "_endLoop" from the parser class.
	 * 
	 * @return That which is in the "_endLoop".
	 */
	private int endLoop() throws Exception{
		Field field = SimpleParser.class.getDeclaredField("_endLoop");
		field.setAccessible(true);
		return (int)field.get(null);
	}
	
	/**
	 * Gets the method "searchKeywords" from the parser class.
	 * 
	 * @param word The keyword
	 * 
	 * @param method The actual method
	 * 
	 * @param lines The source lines
	 * 
	 * @param number The number of actually source line
	 * 
	 * @return The return of the method "searchKeyword".
	 */
	private int searchKeywords(String word, CSMethod method, 
			List<SourceLine> lines, int number) throws Exception {
		Method result = SimpleParser.class.getDeclaredMethod("searchKeywords", 
				String.class, CSMethod.class, List.class, int.class);
		result.setAccessible(true);
		return (int)result.invoke(null, word, method, lines, number);
	}
	
	/**
	 * Tests the keyword <b>assert</b>.
	 */
	@Test
	public void testKeywordAssert() throws Exception {
		int lastLine = 100;
		int actualLine = 0;
		
		CSMethod method = mock(CSMethod.class);
		when(method.getLastLineNumber()).thenReturn(lastLine);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn("assert(true);");
		lines.add(line1);
		
		assertEquals(actualLine + 1, searchKeywords("assert", method, lines, 
				actualLine));
		verify(line1).setLineTested(true);
	}
	
	/**
	 * Tests the keyword <b>break</b>.
	 */
	@Test
	public void testKeywordBreak() throws Exception {
		int lastLine = 100;
		int actualLine = 0;
		
		CSMethod method = mock(CSMethod.class);
		when(method.getLastLineNumber()).thenReturn(lastLine);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn("break;");
		lines.add(line1);
		
		assertEquals(lastLine, searchKeywords("break", method, lines, 
				actualLine));
		verify(line1).setLineTested(true);
	}
	
	/**
	 * Tests the keyword <b>case</b>.
	 */
	@Test
	public void testKeywordCase() throws Exception {
		int lastLine = 100;
		int actualLine = 0;
		
		CSMethod method = mock(CSMethod.class);
		when(method.getLastLineNumber()).thenReturn(lastLine);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn("case 10:");
		lines.add(line1);
		
		assertEquals(actualLine + 1, searchKeywords("case", method, lines, 
				actualLine));
		verify(line1).setLineTested(true);
	}
	
	/**
	 * Tests the keyword <b>catch</b>.
	 */
	@Test
	public void testKeywordCatch() throws Exception {
		int lastLine = 100;
		int actualLine = 0;
		
		CSMethod method = mock(CSMethod.class);
		when(method.getLastLineNumber()).thenReturn(lastLine);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn("} catch (Exception e) {");
		lines.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.getLine()).thenReturn("}");
		lines.add(line2);
		
		assertEquals(actualLine + 1, searchKeywords("catch", method, lines, 
				actualLine));
		assertEquals(1, endLoop());
		verify(line1).setLineTested(true);
	}
	
	/**
	 * Tests the keyword <b>continue</b>.
	 */
	@Test
	public void testKeywordContinue() throws Exception {
		int lastLine = 100;
		int actualLine = 0;
		
		CSMethod method = mock(CSMethod.class);
		when(method.getLastLineNumber()).thenReturn(lastLine);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn("continue;");
		lines.add(line1);
		
		assertEquals(lastLine, searchKeywords("continue", method, lines, 
				actualLine));
		verify(line1).setLineTested(true);
	}
	
	/**
	 * Tests the keyword <b>default</b>.
	 */
	@Test
	public void testKeywordDefault() throws Exception {
		int lastLine = 100;
		int actualLine = 0;
		
		CSMethod method = mock(CSMethod.class);
		when(method.getLastLineNumber()).thenReturn(lastLine);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn("default:");
		lines.add(line1);
		
		assertEquals(actualLine + 1, searchKeywords("default", method, lines, 
				actualLine));
		verify(line1).setLineTested(true);
	}
	
	/**
	 * Tests the keyword <b>do</b>.
	 */
	@Test
	public void testKeywordDo() throws Exception {
		int lastLine = 100;
		int actualLine = 0;
		
		CSMethod method = mock(CSMethod.class);
		when(method.getLastLineNumber()).thenReturn(lastLine);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn("do {");
		lines.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.getLine()).thenReturn("test++;");
		lines.add(line2);
		
		SourceLine line3 = mock(SourceLine.class);
		when(line3.getLine()).thenReturn("} while (test < 100);");
		lines.add(line3);
		
		assertEquals(actualLine + 1, searchKeywords("do", method, lines, 
				actualLine));
		verify(line1).setLineTested(true);
		verify(line2, never()).setLineTested(Matchers.anyBoolean());
		verify(line3, never()).setLineTested(Matchers.anyBoolean());
	}
	
	/**
	 * Tests the keywords <b>do</b> and <b>while</b>.
	 */
	@Test
	public void testKeywordDoWithLoop() throws Exception {
		int lastLine = 100;
		int actualLine = 0;
		
		CSMethod method = mock(CSMethod.class);
		when(method.getLastLineNumber()).thenReturn(lastLine);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn("do {");
		lines.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.getLine()).thenReturn("test++;");
		lines.add(line2);
		
		SourceLine line3 = mock(SourceLine.class);
		when(line3.getLine()).thenReturn("} while (test < 100);");
		lines.add(line3);
		
		assertEquals(actualLine + 1, searchKeywords("do", method, lines, 
				actualLine));
		
		actualLine = 2;
		assertEquals(actualLine + 1, searchKeywords("while", method, lines, 
				actualLine));
		verify(line1).setLineTested(true);
		verify(line2, never()).setLineTested(Matchers.anyBoolean());
		verify(line3).setLineTested(true);
	}
	
	/**
	 * Tests the keywords <b>do</b> and <b>break</b>.
	 */
	@Test
	public void testKeywordDoWithLoopWithBreak() throws Exception {
		int lastLine = 100;
		int actualLine = 0;
		
		CSMethod method = mock(CSMethod.class);
		when(method.getLastLineNumber()).thenReturn(lastLine);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn("do {");
		lines.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.getLine()).thenReturn("break;");
		lines.add(line2);
		
		SourceLine line3 = mock(SourceLine.class);
		when(line3.getLine()).thenReturn("} while (test < 100);");
		lines.add(line3);
		
		assertEquals(actualLine + 1, searchKeywords("do", method, lines, 
				actualLine));
		
		assertEquals(2, endLoop());
		
		actualLine = 1;
		assertEquals(actualLine + 2, searchKeywords("break", method, lines, 
				actualLine));
		
		assertEquals(-1, endLoop());
		
		verify(line1).setLineTested(true);
		verify(line2).setLineTested(true);
		verify(line3, never()).setLineTested(Matchers.anyBoolean());
	}
	
	/**
	 * Tests the keywords <b>do</b> and <b>continue</b>.
	 */
	@Test
	public void testKeywordDoWithLoopWithContinue() throws Exception {
		int lastLine = 100;
		int actualLine = 0;
		
		CSMethod method = mock(CSMethod.class);
		when(method.getLastLineNumber()).thenReturn(lastLine);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn("do {");
		lines.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.getLine()).thenReturn("continue;");
		lines.add(line2);
		
		SourceLine line3 = mock(SourceLine.class);
		when(line3.getLine()).thenReturn("} while (test < 100);");
		lines.add(line3);
		
		assertEquals(actualLine + 1, searchKeywords("do", method, lines, 
				actualLine));
		
		assertEquals(2, endLoop());
		
		actualLine = 1;
		assertEquals(actualLine + 1, searchKeywords("continue", method, lines, 
				actualLine));
		
		assertEquals(2, endLoop());
		
		verify(line1).setLineTested(true);
		verify(line2).setLineTested(true);
		verify(line3, never()).setLineTested(Matchers.anyBoolean());
	}
	
	/**
	 * Tests the keywords <b>do</b> and <b>return</b>.
	 */
	@Test
	public void testKeywordDoWithLoopWithReturn() throws Exception {
		int lastLine = 100;
		int actualLine = 0;
		
		CSMethod method = mock(CSMethod.class);
		when(method.getLastLineNumber()).thenReturn(lastLine);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn("do {");
		lines.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.getLine()).thenReturn("return;");
		lines.add(line2);
		
		SourceLine line3 = mock(SourceLine.class);
		when(line3.getLine()).thenReturn("} while (test < 100);");
		lines.add(line3);
		
		assertEquals(actualLine + 1, searchKeywords("do", method, lines, 
				actualLine));
		
		assertEquals(2, endLoop());
		
		actualLine = 1;
		assertEquals(lastLine, searchKeywords("return", method, lines, 
				actualLine));
		
		assertEquals(-1, endLoop());
		
		verify(line1).setLineTested(true);
		verify(line2).setLineTested(true);
		verify(line3, never()).setLineTested(Matchers.anyBoolean());
	}
	
	/**
	 * Tests the keyword <b>else</b>.
	 */
	@Test
	public void testKeywordElseWithoutIfWithSquareBracket() throws Exception {
		int lastLine = 100;
		int actualLine = 0;
		
		CSMethod method = mock(CSMethod.class);
		when(method.getLastLineNumber()).thenReturn(lastLine);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn("} else {");
		lines.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.getLine()).thenReturn("}");
		lines.add(line2);
		
		assertEquals(actualLine + 1, searchKeywords("else", method, lines, 
				actualLine));
		assertEquals(1, endLoop());
		verify(line1).setLineTested(true);
		verify(line2, never()).setLineTested(Matchers.anyBoolean());
	}
	
	/**
	 * Tests the keyword <b>else</b>.
	 */
	@Test
	public void testKeywordElseWithoutIfWithoutSquareBracket()
			throws Exception {
		int lastLine = 100;
		int actualLine = 0;
		
		CSMethod method = mock(CSMethod.class);
		when(method.getLastLineNumber()).thenReturn(lastLine);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn("} else");
		lines.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.getLine()).thenReturn("test = 10;");
		lines.add(line2);
		
		SourceLine line3 = mock(SourceLine.class);
		when(line3.getLine()).thenReturn("test = 200;");
		lines.add(line3);
		
		assertEquals(actualLine + 2, searchKeywords("else", method, lines, 
				actualLine));
		assertEquals(-1, endLoop());
		verify(line1).setLineTested(true);
		verify(line2).setLineTested(true);
		verify(line3, never()).setLineTested(Matchers.anyBoolean());
	}
	
	/**
	 * Tests the keyword <b>else if</b>.
	 */
	@Test
	public void testKeywordElseWithIf() throws Exception {
		int lastLine = 100;
		int actualLine = 0;
		
		CSMethod method = mock(CSMethod.class);
		when(method.getLastLineNumber()).thenReturn(lastLine);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn("} else if (test == 10) {");
		lines.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.getLine()).thenReturn("}");
		lines.add(line2);
		
		assertEquals(-1, searchKeywords("else", method, lines, actualLine));
		assertEquals(-1, endLoop());
		verify(line1, never()).setLineTested(Matchers.anyBoolean());
		verify(line2, never()).setLineTested(Matchers.anyBoolean());
	}
	
	/**
	 * Tests the keyword <b>finally</b>.
	 */
	@Test
	public void testKeywordFinally() throws Exception {
		int lastLine = 100;
		int actualLine = 0;
		
		CSMethod method = mock(CSMethod.class);
		when(method.getLastLineNumber()).thenReturn(lastLine);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn("finally {");
		lines.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.getLine()).thenReturn("}");
		lines.add(line2);
		
		assertEquals(actualLine + 1, searchKeywords("finally", method, lines, 
				actualLine));
		assertEquals(1, endLoop());
		verify(line1).setLineTested(true);
		verify(line2, never()).setLineTested(Matchers.anyBoolean());
	}
	
	/**
	 * Tests the keyword <b>for</b>.
	 */
	@Test
	public void testKeywordFor() throws Exception {
		int lastLine = 100;
		int actualLine = 0;
		
		CSMethod method = mock(CSMethod.class);
		when(method.getLastLineNumber()).thenReturn(lastLine);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn("for (int i; i < 10; i++) {");
		lines.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.getLine()).thenReturn("}");
		lines.add(line2);
		
		assertEquals(actualLine + 1, searchKeywords("for", method, lines, 
				actualLine));
		assertEquals(1, endLoop());
		verify(line1).setLineTested(true);
		verify(line2, never()).setLineTested(Matchers.anyBoolean());
	}
	
	/**
	 * Tests the keyword <b>if</b> without square bracket.
	 */
	@Test
	public void testKeywordIfWithoutSquareBracket() throws Exception {
		int lastLine = 100;
		int actualLine = 0;
		
		CSMethod method = mock(CSMethod.class);
		when(method.getLastLineNumber()).thenReturn(lastLine);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn("if (test) {");
		lines.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.getLine()).thenReturn("}");
		lines.add(line2);
		
		assertEquals(actualLine + 1, searchKeywords("if", method, lines, 
				actualLine));
		assertEquals(1, endLoop());
		verify(line1).setLineTested(true);
		verify(line2, never()).setLineTested(Matchers.anyBoolean());
	}
	
	/**
	 * Tests the keyword <b>if</b> with square bracket.
	 */
	@Test
	public void testKeywordIfWithSquareBracket() throws Exception {
		int lastLine = 100;
		int actualLine = 0;
		
		CSMethod method = mock(CSMethod.class);
		when(method.getLastLineNumber()).thenReturn(lastLine);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn("if (test) {");
		lines.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.getLine()).thenReturn("}");
		lines.add(line2);
		
		assertEquals(actualLine + 1, searchKeywords("if", method, lines, 
				actualLine));
		assertEquals(1, endLoop());
		verify(line1).setLineTested(true);
		verify(line2, never()).setLineTested(Matchers.anyBoolean());
	}
	
	/**
	 * Tests the keyword <b>if</b> over several lines.
	 */
	@Test
	public void testKeywordIfOverSeveralLines() throws Exception {
		int lastLine = 100;
		int actualLine = 0;
		
		CSMethod method = mock(CSMethod.class);
		when(method.getLastLineNumber()).thenReturn(lastLine);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn("if (test) {");
		lines.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.getLine()).thenReturn("}");
		lines.add(line2);
		
		assertEquals(actualLine + 1, searchKeywords("if", method, lines, 
				actualLine));
		assertEquals(1, endLoop());
		verify(line1).setLineTested(true);
		verify(line2, never()).setLineTested(Matchers.anyBoolean());
	}
	
	/**
	 * Tests the keyword <b>instanceof</b>.
	 */
	@Test
	public void testKeywordInstanceOf() throws Exception {
		int actualLine = 0;
		
		CSMethod method = mock(CSMethod.class);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn("if (test instanceof Test)");
		lines.add(line1);
		
		assertEquals(-10, searchKeywords("instanceof", method, lines, 
				actualLine));
		verify(line1, never()).setLineTested(Matchers.anyBoolean());
	}
	
	/**
	 * Tests the keyword <b>new</b>.
	 */
	@Test
	public void testKeywordNew() throws Exception {
		int actualLine = 0;
		
		CSMethod method = mock(CSMethod.class);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn("Test test = new Test();");
		lines.add(line1);
		
		assertEquals(-10, searchKeywords("new", method, lines, 
				actualLine));
		verify(line1, never()).setLineTested(Matchers.anyBoolean());
	}
	
	/**
	 * Tests the keyword <b>return</b>.
	 */
	@Test
	public void testKeywordReturnWithSquareBracketInNextLine() throws Exception {
		int lastLine = 100;
		int actualLine = 0;
		
		CSMethod method = mock(CSMethod.class);
		when(method.getLastLineNumber()).thenReturn(lastLine);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn("return;");
		lines.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.getLine()).thenReturn("\t}");
		lines.add(line2);
		
		assertEquals(lastLine, searchKeywords("return", method, lines, 
				actualLine));
		verify(line1).setLineTested(true);
		verify(line2).setLineTested(true);
	}
	
	/**
	 * Tests the keyword <b>return</b>.
	 */
	@Test
	public void testKeywordReturnWithoutSquareBracketInNextLine() throws Exception {
		int lastLine = 100;
		int actualLine = 0;
		
		CSMethod method = mock(CSMethod.class);
		when(method.getLastLineNumber()).thenReturn(lastLine);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn("return;");
		lines.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.getLine()).thenReturn("test = 10;");
		lines.add(line2);
		
		assertEquals(lastLine, searchKeywords("return", method, lines, 
				actualLine));
		verify(line1).setLineTested(true);
		verify(line2, never()).setLineTested(Matchers.anyBoolean());
	}
	
	/**
	 * Tests the keyword <b>switch</b>.
	 */
	@Test
	public void testKeywordSwitch() throws Exception {
		int lastLine = 100;
		int actualLine = 0;
		
		CSMethod method = mock(CSMethod.class);
		when(method.getLastLineNumber()).thenReturn(lastLine);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn("switch(test) {");
		lines.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.getLine()).thenReturn("case 10:");
		lines.add(line2);
		
		SourceLine line3 = mock(SourceLine.class);
		when(line3.getLine()).thenReturn("}");
		lines.add(line3);
		
		assertEquals(actualLine + 1, searchKeywords("switch", method, lines, 
				actualLine));
		assertEquals(2, endLoop());
		verify(line1).setLineTested(true);
		verify(line2, never()).setLineTested(Matchers.anyBoolean());
		verify(line3, never()).setLineTested(Matchers.anyBoolean());
	}
	
	/**
	 * Tests the keywords <b>switch</b> and <b>case</b>.
	 */
	@Test
	public void testKeywordSwitchWithCase() throws Exception {
		int lastLine = 100;
		int actualLine = 0;
		
		CSMethod method = mock(CSMethod.class);
		when(method.getLastLineNumber()).thenReturn(lastLine);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn("switch(test) {");
		lines.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.getLine()).thenReturn("case 10:");
		lines.add(line2);
		
		SourceLine line3 = mock(SourceLine.class);
		when(line3.getLine()).thenReturn("}");
		lines.add(line3);
		
		assertEquals(actualLine + 1, searchKeywords("switch", method, lines, 
				actualLine));
		
		assertEquals(2, endLoop());
		
		actualLine++;
		assertEquals(actualLine + 1, searchKeywords("case", method, lines,
				actualLine));
		
		assertEquals(2, endLoop());
		
		verify(line1).setLineTested(true);
		verify(line2).setLineTested(true);
		verify(line3, never()).setLineTested(Matchers.anyBoolean());
	}
	
	/**
	 * Tests the keywords <b>switch</b> and <b>default</b>.
	 */
	@Test
	public void testKeywordSwitchWithDefault() throws Exception {
		int lastLine = 100;
		int actualLine = 0;
		
		CSMethod method = mock(CSMethod.class);
		when(method.getLastLineNumber()).thenReturn(lastLine);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn("switch(test) {");
		lines.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.getLine()).thenReturn("default:");
		lines.add(line2);
		
		SourceLine line3 = mock(SourceLine.class);
		when(line3.getLine()).thenReturn("}");
		lines.add(line3);
		
		assertEquals(actualLine + 1, searchKeywords("switch", method, lines, 
				actualLine));
		
		assertEquals(2, endLoop());
		
		actualLine++;
		assertEquals(actualLine + 1, searchKeywords("default", method, lines,
				actualLine));
		
		assertEquals(2, endLoop());
		
		verify(line1).setLineTested(true);
		verify(line2).setLineTested(true);
		verify(line3, never()).setLineTested(Matchers.anyBoolean());
	}
	
	/**
	 * Tests the keywords <b>switch</b> and <b>return</b>.
	 */
	@Test
	public void testKeywordSwitchWithReturn() throws Exception {
		int lastLine = 100;
		int actualLine = 0;
		
		CSMethod method = mock(CSMethod.class);
		when(method.getLastLineNumber()).thenReturn(lastLine);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn("switch(test) {");
		lines.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.getLine()).thenReturn("return;");
		lines.add(line2);
		
		SourceLine line3 = mock(SourceLine.class);
		when(line3.getLine()).thenReturn("case 10:");
		lines.add(line3);
		
		SourceLine line4 = mock(SourceLine.class);
		when(line4.getLine()).thenReturn("}");
		lines.add(line4);
		
		assertEquals(actualLine + 1, searchKeywords("switch", method, lines, 
				actualLine));
		
		assertEquals(3, endLoop());
		
		actualLine++;
		assertEquals(lastLine, searchKeywords("return", method, lines,
				actualLine));
		
		assertEquals(-1, endLoop());
		
		verify(line1).setLineTested(true);
		verify(line2).setLineTested(true);
		verify(line3, never()).setLineTested(Matchers.anyBoolean());
		verify(line4, never()).setLineTested(Matchers.anyBoolean());
	}

	/**
	 * Tests the keywords <b>switch</b> and <b>case</b>.
	 */
	@Test
	public void testKeywordSwitchWithBreak() throws Exception {
		int lastLine = 100;
		int actualLine = 0;
		
		CSMethod method = mock(CSMethod.class);
		when(method.getLastLineNumber()).thenReturn(lastLine);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn("switch(test) {");
		lines.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.getLine()).thenReturn("break;");
		lines.add(line2);
		
		SourceLine line3 = mock(SourceLine.class);
		when(line3.getLine()).thenReturn("}");
		lines.add(line3);
		
		assertEquals(actualLine + 1, searchKeywords("switch", method, lines, 
				actualLine));
		
		assertEquals(2, endLoop());
		
		actualLine++;
		assertEquals(actualLine + 2, searchKeywords("break", method, lines,
				actualLine));
		
		assertEquals(-1, endLoop());
		
		verify(line1).setLineTested(true);
		verify(line2).setLineTested(true);
		verify(line3, never()).setLineTested(Matchers.anyBoolean());
	}
	
	/**
	 * Tests the keyword <b>throw</b>.
	 */
	@Test
	public void testKeywordThrow() throws Exception {
		int lastLine = 100;
		int actualLine = 0;
		
		CSMethod method = mock(CSMethod.class);
		when(method.getLastLineNumber()).thenReturn(lastLine);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn("throw new Exception();");
		lines.add(line1);
		
		assertEquals(lastLine, searchKeywords("throw", method, lines, 
				actualLine));
		verify(line1).setLineTested(true);
	}
	
	/**
	 * Tests the keyword <b>try</b>.
	 */
	@Test
	public void testKeywordTry() throws Exception {
		int lastLine = 100;
		int actualLine = 0;
		
		CSMethod method = mock(CSMethod.class);
		when(method.getLastLineNumber()).thenReturn(lastLine);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn("try {");
		lines.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.getLine()).thenReturn("}");
		lines.add(line2);
		
		assertEquals(actualLine + 1, searchKeywords("try", method, lines, 
				actualLine));
		assertEquals(1, endLoop());
		verify(line1).setLineTested(true);
		verify(line2, never()).setLineTested(Matchers.anyBoolean());
	}
	
	/**
	 * Tests the keyword <b>while</b>.
	 */
	@Test
	public void testKeywordWhile() throws Exception {
		int lastLine = 100;
		int actualLine = 0;
		
		CSMethod method = mock(CSMethod.class);
		when(method.getLastLineNumber()).thenReturn(lastLine);
		
		List<SourceLine> lines = new ArrayList<SourceLine>();
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn("while(test) {");
		lines.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.getLine()).thenReturn("}");
		lines.add(line2);
		
		assertEquals(actualLine + 1, searchKeywords("while", method, lines, 
				actualLine));
		assertEquals(1, endLoop());
		verify(line1).setLineTested(true);
		verify(line2, never()).setLineTested(Matchers.anyBoolean());
	}
}
