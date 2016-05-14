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

package tests.testsuite.app;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

import org.netbeans.jemmy.ClassReference;
import org.netbeans.jemmy.Scenario;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JProgressBarOperator;
import org.netbeans.jemmy.operators.JTextPaneOperator;
import org.netbeans.jemmy.operators.JTreeOperator;
import org.testsuite.app.App;

/**
 * Tests the test suite app.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestApp implements Scenario {
	/**
	 * Saves the instance of the main window.
	 */
	private JFrameOperator _wnd;
	
	/**
	 * Saves the instance of the resource bundle.
	 */
	private ResourceBundle _bundle;
	
	/**
	 * Saves the instance of tree.
	 */
	private JTreeOperator _tree;
	
	/**
	 * Saves the instance of text pane.
	 */
	private JTextPaneOperator _textPane;
	
	/**
	 * Saves the instance of progress bar.
	 */
	private JProgressBarOperator _progress;
	
	/**
	 * Saves the instance of button for run tests.
	 */
	private JButtonOperator _btnRun;
	
	/**
	 * Saves the instance of button for cancel tests.
	 */
	private JButtonOperator _btnCancel;
	
	/**
	 * Saves the instance of button for load the configuration file.
	 */
	private JButtonOperator _btnLoad;
	
	/**
	 * Saves the instance of button for exit the app.
	 */
	private JButtonOperator _btnExit;

	/**
	 * Initializes the tests.
	 */
	public TestApp() {
		try {
			(new ClassReference("org.testsuite.app.App")).startApplication();
			_bundle = ResourceBundle.getBundle(App.BUNDLE_FILE);
		} catch (Exception e) {
			e.printStackTrace();
		}

		_wnd = new JFrameOperator(App.WND_TITLE);
		_tree = new JTreeOperator(_wnd);
		_textPane = new JTextPaneOperator(_wnd);
		_progress = new JProgressBarOperator(_wnd);
		_btnRun = new JButtonOperator(_wnd, _bundle.getString("btnRun"));
		_btnCancel = new JButtonOperator(_wnd, _bundle.getString("btnCancel"));
		_btnLoad = new JButtonOperator(_wnd, _bundle.getString("btnLoad"));
		_btnExit = new JButtonOperator(_wnd, _bundle.getString("btnExit"));
	}
	
	/**
	 * Determines whether the tree is enabled.
	 */
	public boolean isTreeEnabled() {
		return _tree.isEnabled();
	}
	
	/**
	 * Determines whether the text pane for html output is enabled.
	 */
	public boolean isTextPaneEnabled() {
		return _textPane.isEnabled();
	}
	
	/**
	 * Determines whether the text pane for html output is editable.
	 */
	public boolean isTextPaneEditable() {
		return _textPane.isEditable();
	}
	
	/**
	 * Determines whether the progress bar is enabled.
	 */
	public boolean isProgressBarEnabled() {
		return _progress.isEnabled();
	}
	
	/**
	 * Determines whether the button for run tests is visible.
	 */
	public boolean isRunButtonVisible() {
		return _btnRun.isVisible();
	}
	
	/**
	 * Determines whether the button for run tests is enabled.
	 */
	public boolean isRunButtonEnabled() {
		return _btnRun.isEnabled();
	}
	
	/**
	 * Determines whether the button for cancel tests is visible.
	 */
	public boolean isCancelButtonVisible() {
		return _btnCancel.isVisible();
	}
	
	/**
	 * Determines whether the button for cancel tests is visible.
	 */
	public boolean isCancelButtonEnabled() {
		return _btnCancel.isEnabled();
	}
	
	/**
	 * Determines whether the button for load configuration file is visible.
	 */
	public boolean isLoadButtonVisible() {
		return _btnLoad.isVisible();
	}
	
	/**
	 * Determines whether the button for load configuration file is enabled.
	 */
	public boolean isLoadButtonEnabled() {
		return _btnLoad.isEnabled();
	}
	
	/**
	 * Determines whether the button for exit the app is visible.
	 */
	public boolean isExitButtonVisible() {
		return _btnExit.isVisible();
	}
	
	/**
	 * Determines whether the button for exit the App is enabled.
	 */
	public boolean isExitButtonEnabled() {
		return _btnExit.isEnabled();
	}
	
	/**
	 * Determines whether the main window is visible.
	 */
	public void pushExitButton() {
		_btnExit.push();
	}
	
	/**
	 * Click on the exit button.
	 */
	public boolean isMainWindowVisible() {
		return _wnd.isVisible();
	}

	/**
	 * Starts tests.
	 */
	@Override
	public int runIt(Object arg0) {
		return 0;
	}

}
