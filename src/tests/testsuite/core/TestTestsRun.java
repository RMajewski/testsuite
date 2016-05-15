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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testsuite.core.TestCore;
import org.testsuite.core.TestsRun;

/**
 * Tests of the class {@link org.testsuite.core.TestsRun}.
 * 
 * @author René Majewski
 * 
 * @version 0.1
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({TestsRun.class})
public class TestTestsRun {

	/**
	 * Tests if the tests are run properly.
	 */
	@Test
	public void testRun() throws Exception {
		String configName = "test.xml";

		TestCore core = mock(TestCore.class);
		when(core.readConfig(configName)).thenReturn(true);
		
		PowerMockito.whenNew(TestCore.class).withNoArguments().thenReturn(core);
		
		TestsRun.run(configName);
		
		PowerMockito.verifyNew(TestCore.class).withNoArguments();
		
		InOrder order = inOrder(core);
		order.verify(core).readConfig(configName);
		order.verify(core).checkFileExists();
		order.verify(core).run();
		order.verify(core).createResultHtml();
	}

	/**
	 * Tests if the tests are not performed if the configuration file was not
	 * read correctly.
	 */
	@Test
	public void testRunWithNoReadConfigFile() throws Exception {
		String configName = "test.xml";

		TestCore core = mock(TestCore.class);
		when(core.readConfig(configName)).thenReturn(false);
		
		PowerMockito.whenNew(TestCore.class).withNoArguments().thenReturn(core);
		
		TestsRun.run(configName);
		
		PowerMockito.verifyNew(TestCore.class).withNoArguments();
		
		InOrder order = inOrder(core);
		order.verify(core).readConfig(configName);
		order.verify(core, never()).checkFileExists();
		order.verify(core, never()).run();
		order.verify(core, never()).createResultHtml();
	}

}
