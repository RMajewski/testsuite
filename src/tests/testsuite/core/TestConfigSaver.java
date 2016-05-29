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

import static org.mockito.Mockito.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testsuite.core.ConfigSaver;
import org.testsuite.core.FitTestRunner;
import org.testsuite.core.JemmyTestRunner;
import org.testsuite.core.JunitTestRunner;
import org.testsuite.data.Config;

import org.testsuite.core.TestRunner;

/**
 * Tests the class {@link org.testsuite.core.ConfigSaver}.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ConfigSaver.class})
public class TestConfigSaver {

	/**
	 * Tests if the configuration file is created
	 */
	@Test
	public void testSave() throws Exception {
		File file = mock(File.class);
		
		FileWriter writer = mock(FileWriter.class);
		
		PowerMockito.whenNew(FileWriter.class)
			.withArguments(file)
			.thenReturn(writer);
		
		BufferedWriter bw = mock(BufferedWriter.class);
		PowerMockito.whenNew(BufferedWriter.class)
			.withArguments(writer).thenReturn(bw);
		
		JunitTestRunner runner1 = new JunitTestRunner();
		JemmyTestRunner runner2 = new JemmyTestRunner();
		FitTestRunner runner3 = new FitTestRunner();
		
		List<TestRunner> list = new ArrayList<TestRunner>();
		list.add(runner1);
		list.add(runner2);
		list.add(runner3);
		
		Config config = new Config();
		
		ConfigSaver.save(config, list, file);
		verify(bw).write("<?xml version=\"1.0\"?>");
		verify(bw).write("<configurationFile");
		verify(bw).write(" xmlns=\"https://github.com/RMajewski/testsuite\"");
		verify(bw).write(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		verify(bw).write(" xsi:schemaLocation=\"https://github.com/RMajewski/");
		verify(bw).write("testsuite https://raw.githubusercontent.com/RMajewski/");
		verify(bw).write("testsuite/master/src/resources/xml/config.xsd\"");
	}

}
