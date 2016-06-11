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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testsuite.checksource.CSMethod;
import org.testsuite.checksource.HtmlOut;
import org.testsuite.checksource.MessageColor;
import org.testsuite.checksource.SourceLine;
import org.testsuite.checksource.annotation.CheckSource;
import org.testsuite.helper.HelperUsedColor;

/**
 * Tests the class {@link org.testsuite.checksource.Htmlout}.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({HtmlOut.class})
public class TestHtmlOut {
	/**
	 * Saves the instance of HtmlOut.
	 */
	private HtmlOut _html;
	
	/**
	 * Saves the name of result file
	 */
	private String _resultFile;

	/**
	 * Initialize the tests.
	 */
	@Before
	public void setUp() throws Exception {
		_resultFile = "Test.html";
		_html = new HtmlOut(_resultFile);
	}

	/**
	 * Tests if was correctly initialized. 
	 */
	@Test
	public void testHtmlOut() throws Exception {
		Field field = HtmlOut.class.getSuperclass().getDeclaredField("_resultFile");
		field.setAccessible(true);
		assertEquals(_resultFile, field.get(_html));
	}

	/**
	 * Tests if the HTML file is generated.
	 */
	@CheckSource(method="sourceCode")
	@Test
	public void testCreateHtml() throws Exception {
		FileWriter fw = mock(FileWriter.class);
		PowerMockito.whenNew(FileWriter.class)
			.withArguments(_resultFile)
			.thenReturn(fw);
		
		BufferedWriter bw = mock(BufferedWriter.class);
		PowerMockito.whenNew(BufferedWriter.class)
			.withArguments(fw)
			.thenReturn(bw);
		
		List<SourceLine> sources = new ArrayList<SourceLine>();
		List<CSMethod> methodes = new ArrayList<CSMethod>();
		
		String className1 = "Class1";
		String className2 = "Class2";
		String modifier1 = "public";
		String modifier2 = "private";
		String methodName1 = "method1";
		String methodName2 = "method2";
		String source1 = "public String test() {";
		String source2 = "private String test() {";
		String messageText1 = "This is a test";
		String messageText2 = "This is a test";
		
		MessageColor message1 = mock(MessageColor.class);
		when(message1.getColor()).thenReturn(HelperUsedColor.ERROR);
		when(message1.getMessage()).thenReturn(messageText1);
		
		MessageColor message2 = mock(MessageColor.class);
		when(message2.getColor()).thenReturn(HelperUsedColor.WARNING);
		when(message2.getMessage()).thenReturn(messageText2);
		
		SourceLine line1 = mock(SourceLine.class);
		when(line1.getLine()).thenReturn(source1);
		when(line1.getLineNumber()).thenReturn(1);
		when(line1.messageCount()).thenReturn(1);
		when(line1.getMessage(0)).thenReturn(message1);
		when(line1.getMessage(0)).thenReturn(message2);
		when(line1.isLineTested()).thenReturn(true);
		when(line1.isBeginMethod()).thenReturn(true);
		when(line1.isJavadoc()).thenReturn(false);
		when(line1.isMultiLineComment()).thenReturn(false);
		sources.add(line1);
		
		SourceLine line2 = mock(SourceLine.class);
		when(line2.getLine()).thenReturn(source2);
		when(line2.getLineNumber()).thenReturn(2);
		when(line2.isLineTested()).thenReturn(false);
		when(line2.isBeginMethod()).thenReturn(true);
		when(line2.isJavadoc()).thenReturn(false);
		when(line2.isMultiLineComment()).thenReturn(false);
		sources.add(line2);
		
		CSMethod method1 = mock(CSMethod.class);
		when(method1.getClassName()).thenReturn(className1);
		when(method1.callsCount()).thenReturn(1);
		when(method1.getCall(0)).thenReturn(1);
		when(method1.getModifier()).thenReturn(modifier1);
		when(method1.getLine()).thenReturn(1);
		when(method1.getName()).thenReturn(methodName1);
		methodes.add(method1);
		
		CSMethod method2 = mock(CSMethod.class);
		when(method2.getClassName()).thenReturn(className2);
		when(method2.callsCount()).thenReturn(0);
		when(method2.getModifier()).thenReturn(modifier2);
		when(method2.getLine()).thenReturn(2);
		when(method2.getName()).thenReturn(methodName2);
		methodes.add(method2);
		
		_html.createHtml(sources, methodes);
		
		verify(method1, times(5)).getClassName();
		verify(method1, times(2)).getLine();
		verify(method1, times(4)).getName();
		verify(method1).getModifier();
		verify(method1, times(2)).callsCount();
		verify(method1, never()).getCall(0);
		
		verify(method2, times(4)).getClassName();
		verify(method2).getLine();
		verify(method2, times(4)).getName();
		verify(method2, times(3)).getModifier();
		verify(method2, times(2)).callsCount();
		verify(method2, never()).getCall(0);
	}

}
