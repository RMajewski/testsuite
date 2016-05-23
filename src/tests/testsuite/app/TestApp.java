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

import java.awt.Component;
import java.io.File;
import java.util.ResourceBundle;

import javax.swing.JCheckBox;
import javax.swing.JMenu;

import org.netbeans.jemmy.ClassReference;
import org.netbeans.jemmy.Scenario;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JCheckBoxOperator;
import org.netbeans.jemmy.operators.JFileChooserOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JMenuOperator;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.netbeans.jemmy.operators.JProgressBarOperator;
import org.netbeans.jemmy.operators.JTextAreaOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.jemmy.operators.JTreeOperator;
import org.netbeans.jemmy.operators.Operator;
import org.testsuite.app.App;
import org.testsuite.data.Config;

/**
 * Tests the test suite app.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
public class TestApp implements Scenario {
	/**
	 * Saves the type for configuration file open dialog.
	 */
	public static final int DIALOG_CONFIG_FILE_OPEN = 1;
	
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
	private JTextAreaOperator _txtHtml;
	
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
	 * Saves the instance of open configuration file dialog.
	 */
	private JFileChooserOperator _fileChooser;
	
	/**
	 * Saves the instance of pop-up menu
	 */
	private JPopupMenuOperator _popup;
	
	/**
	 * Saves the instance of ClassReference.
	 */
	private ClassReference _cr;

	/**
	 * Initializes the tests.
	 */
	public TestApp() {
		try {
			_cr = (new ClassReference("org.testsuite.app.App"));
			_cr.startApplication();
			_bundle = ResourceBundle.getBundle(App.BUNDLE_FILE);
		} catch (Exception e) {
			e.printStackTrace();
		}

		_wnd = new JFrameOperator(App.WND_TITLE);
		_tree = new JTreeOperator(_wnd);
		_txtHtml = new JTextAreaOperator(_wnd, 0);
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
		return _txtHtml.isEnabled();
	}
	
	/**
	 * Determines whether the text pane for html output is editable.
	 */
	public boolean isTextPaneEditable() {
		return _txtHtml.isEditable();
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
	 * Click on the exit button
	 */
	public void pushExitButton() {
		_btnExit.push();
	}
	
	/**
	 * Determines whether the main window is visible.
	 */
	public boolean isMainWindowVisible() {
		return _wnd.isVisible();
	}
	
	/**
	 * Click on load configuration file button.
	 */
	public void pushNoBlockButtonLoadConfigurationFile() {
		_btnLoad.pushNoBlock();
	}
	
	/**
	 * Gets the specified dialog.
	 * 
	 * @param type Type of the dialog.
	 */
	public void determineDialog(int type) {
		switch (type) {
			case DIALOG_CONFIG_FILE_OPEN:
				_fileChooser = new JFileChooserOperator();
			break;
		}
	}
	
	/**
	 * Returns is the dialog showing or not.
	 * 
	 * @return Is the dialog showing?
	 */
	public boolean isDialogShowing() {
		if (_fileChooser != null) {
			return _fileChooser.isShowing();
		}
		
		return false;
	}
	
	/**
	 * Click on the cancel button on dialog.
	 * 
	 * @param index The button index
	 */
	public void pushDialogButton(int index) {
		JButtonOperator btn;
		if (_fileChooser != null) {
			btn = new JButtonOperator(_fileChooser, index);
			btn.push();
		}
	}
	
	/**
	 * Sets the text in text field in the dialog.
	 * 
	 * @param text Text to sets into the text field.
	 */
	public void dialogTextFieldSetText(String text) {
		if (_fileChooser != null) {
			JTextFieldOperator tf = new JTextFieldOperator(_fileChooser);
			tf.setText(text);
		}
	}
	
	/**
	 * Returns the number of nodes in the tree.
	 * 
	 * @return Number of nodes in the tree.
	 */
	public int getTreeRootItemCount() {
		return _tree.getModel().getChildCount(_tree.getModel().getRoot());
	}
	
	/**
	 * Returns the the name selected configuration file.
	 * 
	 * @return File name of configuration file.
	 */
	public String getConfigurationFileName() {
		File file = _fileChooser.getSelectedFile();
		String[] tmp = file.getPath().split("/");
		int length = tmp.length - 1;
		return tmp[length - 2] + "/" + tmp[length - 1] + "/" + tmp[length];
	}
	
	/**
	 * Returns whether the selected configuration file exists.
	 * 
	 * @return Exists the configuration file?
	 */
	public boolean existsConfigurationFile() {
		File file = _fileChooser.getSelectedFile();
		return file.exists();
	}
	
	/**
	 * Determines whether the specified check box from the tree is enabled.
	 * 
	 * @param index Specifying the check box.
	 * 
	 * @return If the check box is enabled?
	 */
	public boolean isCheckBoxFromTreeEnabled(int index) {
		Component c = _tree.getRenderedComponent(_tree.getPathForRow(index));
		if (c instanceof JCheckBox) {
			JCheckBoxOperator checkbox = new JCheckBoxOperator((JCheckBox)c);
			return checkbox.isEnabled();
		}
		
		return false;
	}
	
	/**
	 * Determines whether the specified check box from the tree is selected.
	 * 
	 * @param index Specifying the check box.
	 * 
	 * @return If the check box is selected?
	 */
	public boolean isCheckBoxFromTreeSelected(int index) {
		Component c = _tree.getRenderedComponent(_tree.getPathForRow(index));
		if (c instanceof JCheckBox) {
			JCheckBoxOperator checkbox = new JCheckBoxOperator((JCheckBox)c);
			return checkbox.isSelected();
		}
		
		return false;
	}
	
	/**
	 * Tree expand.
	 */
	public void treeExpandAll() {
		_tree.expandRow(1);
		_tree.expandRow(2);
	}
	
	/**
	 * Performs a double click on the specified check box.
	 * 
	 * @param index Specifying the check box.
	 */
	public void doubleClickOnCheckBoxFromTree(int index) {
		_tree.clickOnPath(_tree.getPathForRow(index), 2);
	}
	
	public int getMaximumOfProgressBar() {
		return _progress.getMaximum();
	}
	
	public int getMinimumOfProgressBar() {
		return _progress.getMinimum();
	}
	
	public int getValueOfProgressBar() {
		return _progress.getValue();
	}
	
	public void pushButtonRun() {
		_btnRun.push();
	}
	
	public void pushButtonCancel() {
		_btnCancel.push();
	}
	
	public boolean existsResultHtmlFile() throws Exception{
		Config config = ((App)_wnd.getSource()).getConfig();
		String html = config.getPathResult() + File.separator +
				_bundle.getString("html_result") + "_" +
				config.getPathSuitesResult() + ".html";
		
		System.out.println(html);
		File file = new File(html);
		return file.exists();
	}
	
	public int rowsFromHtmlTextEditor() {
		return _txtHtml.getRows();
	}
	
	public void openTreePopup() {
		if (_popup == null) {
			_tree.clickForPopup();
			_popup = new JPopupMenuOperator();
		}
	}
	
	public boolean isPopupItemEnabled(int menu, int item) {
		JMenuOperator mo = new JMenuOperator((JMenu)_popup.getComponent(menu));
		return mo.getItem(item).isEnabled();
	}
	
	public void selectElementInTree(int row) {
		_tree.setSelectionRow(row);
	}

	/**
	 * Start the jemmy tests.
	 */
	@Override
	public int runIt(Object arg0) {
		return 0;
	}

}
