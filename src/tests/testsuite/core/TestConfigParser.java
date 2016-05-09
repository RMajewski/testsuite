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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testsuite.core.ConfigParser;
import org.testsuite.data.Config;

/**
 * Tests the class {@link org.testsuite.core.ConfigParser}
 * 
 * @author René Majewski
 * 
 * @version 0.1
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ConfigParser.class})
public class TestConfigParser {

	/**
	 * Hold an instance of the ConfigParser
	 */
	private ConfigParser _parser;
	
	/**
	 * Holds the mock object for configuration.
	 */
	private Config _config;
	
	/**
	 * Holds the name of configuration file.
	 */
	private String _configFile;

	/**
	 * Initialize the tests
	 */
	@Before
	public void setUp() throws Exception {
		_config = mock(Config.class);
		_configFile = "test.xml";
		
		_parser = new ConfigParser(_config, _configFile);
	}

	/**
	 * Tests if the configuration is returned.
	 */
	@Test
	public void testGetConfig() {
		assertEquals(_config, _parser.getConfig());
	}
	
	/**
	 * Tests if the configuration can be set.
	 */
	@Test
	public void testSetConfig() {
		_config = mock(Config.class);
		_parser.setConfig(_config);
		assertEquals(_config, _parser.getConfig());
	}
	
	/**
	 * Tests if the error occurs IllegalArgumentException if null is passed as a
	 * parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetConfigWithNullAsParameter() {
		_parser.setConfig(null);
	}
	
	@Test
	public void testGetConfigFile() {
		assertEquals(_configFile, _parser.getConfigFile());
	}
	
	@Test
	public void testSetConfigFile() {
		_configFile = "neu.xml";
		_parser.setConfigFile(_configFile);
		assertEquals(_configFile, _parser.getConfigFile());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetConfigFileWithNullAsParameter() {
		_parser.setConfigFile(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetConfigFileWithEmptyStringAsParameter() {
		_parser.setConfigFile(new String());
	}

	@Test
	public void testParseWithNonExistingFileReturnFalse() throws Exception {
		File file = mock(File.class);
		when(file.exists()).thenReturn(false);
		
		PowerMockito.whenNew(File.class)
			.withParameterTypes(String.class)
			.withArguments(Matchers.anyString())
			.thenReturn(file);
		
		assertFalse(_parser.parse());
		
		PowerMockito.verifyNew(File.class).withArguments(_configFile);
		verify(file).exists();
	}
}
