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

package tests.testsuite.core;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testsuite.core.HtmlOut;
import org.testsuite.core.TestCore;

/**
 * Tests the class {@link org.testsuite.TestCore}
 * 
 * @author René Majewski
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({TestCore.class, XMLInputFactory.class})
public class TestTestCore {

	/**
	 * Save the TestCore class
	 */
	private TestCore _core;
	
	/**
	 * Save the result path
	 */
	private String _resultPath;
	
	/**
	 * Save the result path für fit tests
	 */
	private String _fitResult;
	
	/**
	 * Save the path for library
	 */
	private String _bibPath;
	
	/**
	 * Save the path to source code
	 */
	private String _srcPath;
	
	/**
	 * Read the path from the TestCore class.
	 */
	private void readPaths() throws Exception {
		Field field = TestCore.class.getDeclaredField("_bibPath");
		field.setAccessible(true);
		_bibPath = (String)field.get(_core);
		
		field = TestCore.class.getDeclaredField("_fitResult");
		field.setAccessible(true);
		_fitResult = (String)field.get(_core);
		
		field = TestCore.class.getDeclaredField("_resultPath");
		field.setAccessible(true);
		_resultPath = (String)field.get(_core);
		
		field = TestCore.class.getDeclaredField("_srcPath");
		field.setAccessible(true);
		_srcPath = (String)field.get(_core);
	}
	
	/**
	 * Initialize the tests
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		_core = new TestCore();
		readPaths();
	}

	/**
	 * Tests if the initialization of the class TestCore is correct.
	 * 
	 * @see org.testsuite.TestCore#TestCore()
	 */
	@Test
	public void testTestCore() {
		assertNotNull(_fitResult);
		assertFalse(_fitResult.isEmpty());
		assertEquals(14, _fitResult.length());
		assertNull(_bibPath);
		assertNull(_resultPath);
		assertNull(_srcPath);
	}

	/**
	 * Tests if the error occurs IllegalArgumentException if null is passed as
	 * the configuration file.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testReadConfigNullAsConfigFile() throws Exception{
		assertFalse(_core.readConfig(null));
	}

	/**
	 * Tests whether the error IllegalArgumentException occurs when an empty
	 * string is passed as the configuration file.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testReadConfigEmptyStringAsConfigFile() throws Exception {
		assertFalse(_core.readConfig(new String()));
	}
	
	/**
	 * Tests if false is returned if the specified configuration file does not
	 * exist.
	 */
	@Test
	public void testReadConfigReturnFalseConfigFileNotExists()
			throws Exception {
		String config = "test.xml";
		File file = mock(File.class);
		when(file.exists()).thenReturn(false);
		
		PowerMockito.whenNew(File.class)
			.withParameterTypes(String.class)
			.withArguments(config)
			.thenReturn(file);
		
		assertFalse(_core.readConfig(config));
		
		PowerMockito.verifyNew(File.class).withArguments(config);
		verify(file, times(1)).exists();
	}
	
	/**
	 * Tests if the configuration file is read correctly.
	 */
	@Test
	public void testReadConfig() throws Exception {
		String config = "test.xml";
		File file = mock(File.class);
		when(file.exists()).thenReturn(true);
		
		PowerMockito.whenNew(File.class)
			.withParameterTypes(String.class)
			.withArguments(config)
			.thenReturn(file);
		
		FileInputStream fis = mock(FileInputStream.class);
		
		
		PowerMockito.whenNew(FileInputStream.class)
			.withParameterTypes(File.class)
			.withArguments(file)
			.thenReturn(fis);
		
		XMLStreamReader parser = mock(XMLStreamReader.class);
		when(parser.hasNext()).thenReturn(false);
		
		XMLInputFactory factory = mock(XMLInputFactory.class);
		when(factory.createXMLStreamReader(fis)).thenReturn(parser);
		
		PowerMockito.mockStatic(XMLInputFactory.class);
		PowerMockito.when(XMLInputFactory.newInstance()).thenReturn(factory);
		
		assertTrue(_core.readConfig(config));
	}
	
	/**
	 * Tests if the preferences were retrieved from the configuration file.
	 */
	@Test
	public void testReadConfigReadRightPreferences() throws Exception {
		String config = "test.xml";
		File file = mock(File.class);
		when(file.exists()).thenReturn(true);
		
		PowerMockito.whenNew(File.class)
			.withParameterTypes(String.class)
			.withArguments(config)
			.thenReturn(file);
		
		FileInputStream fis = mock(FileInputStream.class);
		
		
		PowerMockito.whenNew(FileInputStream.class)
			.withParameterTypes(File.class)
			.withArguments(file)
			.thenReturn(fis);
		
		String srcPath = "srcPath";
		String bibPath = "bibPath";
		String resultPath = "resultPath";
		
		XMLStreamReader parser = mock(XMLStreamReader.class);
		when(parser.hasNext()).thenReturn(true, true, true, true, true, true,
				true, false);
		when(parser.getEventType()).thenReturn(
				XMLStreamConstants.START_ELEMENT,
				XMLStreamConstants.CHARACTERS,
				XMLStreamConstants.END_ELEMENT,
				XMLStreamConstants.CHARACTERS,
				XMLStreamConstants.END_ELEMENT,
				XMLStreamConstants.CHARACTERS,
				XMLStreamConstants.END_ELEMENT);
		when(parser.getLocalName()).thenReturn("config", "bibPath",
				"resultPath", "srcPath");
		when(parser.getText()).thenReturn(bibPath, resultPath, srcPath);
		
		XMLInputFactory factory = mock(XMLInputFactory.class);
		when(factory.createXMLStreamReader(fis)).thenReturn(parser);
		
		PowerMockito.mockStatic(XMLInputFactory.class);
		PowerMockito.when(XMLInputFactory.newInstance()).thenReturn(factory);
		
		assertTrue(_core.readConfig(config));
		
		readPaths();
		
		assertEquals(srcPath, _srcPath);
		assertEquals(resultPath, _resultPath);
		assertEquals(bibPath, _bibPath);
	}

	/**
	 * Tests whether the HTML file was created.
	 */
	@Test
	public void testCreateResultHtml() throws Exception{
		HtmlOut html = mock(HtmlOut.class);
		
		PowerMockito.whenNew(HtmlOut.class)
			.withParameterTypes(String.class)
			.withArguments(Matchers.anyString())
			.thenReturn(html);
		
		_core.createResultHtml();
		
		PowerMockito.verifyNew(HtmlOut.class).withArguments(Matchers.anyString());
		verify(html, times(1)).htmlHead();
		verify(html, times(1)).guiHead();
		verify(html, times(1)).junitHead();
		verify(html, times(1)).fitHead();
		verify(html, times(1)).htmlEnd();
		
	}

}
