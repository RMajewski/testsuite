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

package tests.fixtures.app;

/**
 * Implements the fixtures for testing the read HTML output.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class FixtureAppReadHtmlOut extends FixtureApp {
	/**
	 * Determines whether lines are included in the text field for HTML output.
	 * 
	 * @return Exist line in HTML output?
	 */
	public boolean drawsHtmlFileIntoApp() throws InterruptedException {
		Thread.sleep(500);
		return _tests.rowsFromHtmlTextEditor() > 0;
	}

}
