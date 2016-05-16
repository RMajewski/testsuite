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

import java.lang.reflect.InvocationTargetException;

import org.netbeans.jemmy.ClassReference;
import org.netbeans.jemmy.Scenario;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.testsuite.app.App;

/**
 * Implemented a test that duration time too long. With these test It is 
 * determined whether the test automatically canceled after the maximum
 * duration time.
 * 
 * @author René Majewski
 * 
 * @version 0.1
 */
public class TestTooLongJemmy implements Scenario {
	public TestTooLongJemmy() throws Exception {
		(new ClassReference("org.testsuite.app.App")).startApplication();
		
		JFrameOperator wnd = new JFrameOperator("test is too long");
	}

	@Override
	public int runIt(Object arg0) {
		return 0;
	}
	
	static public void main(String[] args) throws Exception {
		new TestTooLongJemmy();
	}
}
