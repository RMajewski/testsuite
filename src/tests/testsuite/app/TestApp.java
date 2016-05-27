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
import java.io.FileInputStream;
import java.util.ResourceBundle;

import javax.swing.JCheckBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.xml.XMLConstants;
import javax.xml.bind.Validator;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.netbeans.jemmy.ClassReference;
import org.netbeans.jemmy.Scenario;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JCheckBoxOperator;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.operators.JFileChooserOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JListOperator;
import org.netbeans.jemmy.operators.JMenuItemOperator;
import org.netbeans.jemmy.operators.JMenuOperator;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.netbeans.jemmy.operators.JProgressBarOperator;
import org.netbeans.jemmy.operators.JTextAreaOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.jemmy.operators.JTreeOperator;
import org.testsuite.app.App;
import org.testsuite.app.DlgConfigGeneral;
import org.testsuite.app.TestRunnerModel;
import org.testsuite.core.TestRunner;
import org.testsuite.data.Config;
import org.testsuite.data.Library;
import org.testsuite.data.TestSuite;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * Tests the test suite app.
 * 
 * @author René Majewski
 *
 * @version 0.2
 */
public class TestApp implements Scenario {
	/**
	 * Saves the type for configuration file open dialog.
	 */
	public static final int DIALOG_CONFIG_FILE_OPEN = 1;
	
	/**
	 * Saves the type for configuration file save dialog.
	 */
	public static final int DIALOG_CONFIG_FILE_SAVE = 2;
	
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
	 * Saves the instance of button for validate configuration
	 */
	private JButtonOperator _btnConfigValidate;
	
	/**
	 * Saves the instance of button for save configuration
	 */
	private JButtonOperator _btnConfigSave;
	
	/**
	 * Saves the instance of button for all tests select ignore
	 */
	private JButtonOperator _btnAllTestsIgnore;
	
	/**
	 * Saves the instance of button for all tests select execute
	 */
	private JButtonOperator _btnAllTestsExecute;
	
	/**
	 * Saves the instance of open configuration file dialog.
	 */
	private JFileChooserOperator _fileChooser;
	
	/**
	 * Saves the instance of pop-up menu
	 */
	private JPopupMenuOperator _popup;
	
	/**
	 * Saves the instance of dialog
	 */
	private JDialogOperator _dlg;
	
	/**
	 * Saves the instance of second dialog
	 */
	private JDialogOperator _dlg2;
	
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
		_btnLoad = new JButtonOperator(_wnd, _bundle.getString("btnConfigLoad"));
		_btnExit = new JButtonOperator(_wnd, _bundle.getString("btnExit"));
		_btnConfigSave = new JButtonOperator(_wnd, 
				_bundle.getString("btnConfigSave"));
		_btnConfigValidate = new JButtonOperator(_wnd, 
				_bundle.getString("btnConfigValidate"));
		_btnAllTestsIgnore = new JButtonOperator(_wnd, 
				_bundle.getString("btnAllTestsIgnore"));
		_btnAllTestsExecute = new JButtonOperator(_wnd, 
				_bundle.getString("btnAllTestsExecute"));
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
	 * Determines whether the button for save configuration file is visible.
	 */
	public boolean isConfigSaveButtonVisible() {
		return _btnConfigSave.isVisible();
	}
	
	/**
	 * Determines whether the button for save configuration file is enabled.
	 */
	public boolean isConfigSaveButtonEnabled() {
		return _btnConfigSave.isEnabled();
	}
	
	/**
	 * Determines whether the button for validate configuration is visible.
	 */
	public boolean isConfigValidateButtonVisible() {
		return _btnConfigValidate.isVisible();
	}
	
	/**
	 * Determines whether the button for validate configuration is enabled.
	 */
	public boolean isConfigValidateButtonEnabled() {
		return _btnConfigValidate.isEnabled();
	}
	
	/**
	 * Determines whether the button for all tests select ignore is visible.
	 */
	public boolean isAllTestsIgnoreButtonVisible() {
		return _btnAllTestsIgnore.isVisible();
	}
	
	/**
	 * Determines whether the button for all tests select ignore is enabled.
	 */
	public boolean isAllTestsIgnoreButtonEnabled() {
		return _btnAllTestsIgnore.isEnabled();
	}
	
	/**
	 * Determines whether the button for all tests select execute is visible.
	 */
	public boolean isAllTestsExecuteButtonVisible() {
		return _btnAllTestsExecute.isVisible();
	}
	
	/**
	 * Determines whether the button for all tests select execute is enabled.
	 */
	public boolean isAllTestsExecuteButtonEnabled() {
		return _btnAllTestsExecute.isEnabled();
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
	 * Click on save configuration file button.
	 */
	public void pushNoBlockButtonSaveConfigurationFile() {
		_btnConfigSave.pushNoBlock();
	}
	
	/**
	 * Gets the specified dialog.
	 * 
	 * @param type Type of the dialog.
	 */
	public void determineDialog(int type) {
		// OPT Adjust method.
		switch (type) {
			case DIALOG_CONFIG_FILE_OPEN:
			case DIALOG_CONFIG_FILE_SAVE:
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
	 * Click on the the button on dialog.
	 * 
	 * @param index The button index
	 */
	public void pushNoBlockDialogButton(int index) {
		JButtonOperator btn;
		if (_fileChooser != null) {
			btn = new JButtonOperator(_fileChooser, index);
			btn.pushNoBlock();
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
	 * @param lastPath Paths that you want to include.
	 * 
	 * @return File name of configuration file.
	 */
	public String getConfigurationFileName(int lastPaths) {
		File file = _fileChooser.getSelectedFile();
		String[] tmp = file.getPath().split(File.separator);
		StringBuilder ret = new StringBuilder();
		for (int i = tmp.length - 1 - lastPaths; i < tmp.length; i++) {
			ret.append(tmp[i]);
			if (i < tmp.length - 1)
				ret.append(File.separator);
		}
		return ret.toString();
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
	 * Returns whether the selected configuration file validated.
	 * 
	 * @return Validated the configuration file?
	 */
	public boolean isConfigurationFileValide() {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setValidating(false);
			factory.setNamespaceAware(true);
			
			SchemaFactory schema = SchemaFactory.newInstance(
					"http://www.w3.org/2001/XMLSchema");
			factory.setSchema(schema.newSchema(getClass().getClassLoader()
					.getResource("resources/xml/config.xsd")));
			
			SAXParser parser = factory.newSAXParser();
			
			XMLReader reader = parser.getXMLReader();
			reader.parse(new InputSource("result/test.xml"));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
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
		_tree.expandRow(0);
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
		_tree.clickMouse();
		_tree.clickForPopup();
		_popup = new JPopupMenuOperator();
	}
	
	// OPT mit openConfigTestRunnerLibraryPopup zusammen legen
	public void openConfigGeneralPropertyPopup() {
		getDialogList(0).clickForPopup();
		_popup = new JPopupMenuOperator();
	}
	
	// OPT mit openConfigGeneralPropertyPopup zusammen legen
	public void openConfigTestRunnerLibraryPopup() {
		getDialogList(0).clickForPopup();
		_popup = new JPopupMenuOperator();
	}
	
	public void openConfigTestRunnerClasspathPopup() {
		getDialogList(1).clickForPopup();
		_popup = new JPopupMenuOperator();
	}
	
	public void setConfigTestRunnerLibraryFileName(String name) {
		new JTextFieldOperator(_dlg2, 0).setText(name);
	}
	
	public void pushConfigTestRunnerLibraryPopup(int index) {
		new JMenuItemOperator((JMenuItem)_popup.getComponent(index)).push();
	}
	
	// OPT Rename, da von mehreren Tests benutzt wird
	public void pushNoBlockConfigGeneralPropertyPopup(int index, String name) {
		new JMenuItemOperator((JMenuItem)_popup.getComponent(index))
			.pushNoBlock();
		_dlg2 = new JDialogOperator(_wnd, name);
	}
	
	public boolean isPopupItemEnabled(int menu, int item) {
		JMenuOperator mo = new JMenuOperator((JMenu)_popup.getComponent(menu));
		return mo.getItem(item).isEnabled();
	}
	
	public boolean isPopupItemEnabled(int item) {
		return ((JMenuItem)_popup.getComponent(item)).isEnabled();
	}
	
	public boolean havePopupItem(int item, String text) {
		return ((JMenuItem)_popup.getComponent(item)).getText().equals(text);
	}
	
	public void selectElementInTree(int row) {
		_tree.setSelectionRow(row);
	}
	
	public void pushNoBlockTreePopupItem(int menu, int item) {
		JMenuOperator mo = new JMenuOperator((JMenu)_popup.getComponent(menu));
		_popup.pushMenuNoBlock(mo.getText() + "|" + mo.getItem(item).getText());
	}
	
	public void pushTreePopupItem(int menu, int item) {
		JMenuOperator mo = new JMenuOperator((JMenu)_popup.getComponent(menu));
		_popup.pushMenu(mo.getText() + "|" + mo.getItem(item).getText());
	}
	
	public void waitForDialog(String name) {
		_dlg = new JDialogOperator(_wnd, name);
	}
	
	public void waitForDialog2(String name) {
		_dlg2 = new JDialogOperator(_wnd, name);
	}
	
	public JTextFieldOperator getDialogTextField(int index) {
		return new JTextFieldOperator(_dlg, index);
	}
	
	public JCheckBoxOperator getDialogCheckBox(int index) {
		return new JCheckBoxOperator(_dlg, index);
	}
	
	public JListOperator getDialogList(int index) {
		return new JListOperator(_dlg, index);
	}

	public JButtonOperator getDialogButton(int index) {
		return new JButtonOperator(_dlg, index);
	}
	
	public JTextAreaOperator getDialogTextArea(int index) {
		return new JTextAreaOperator(_dlg, index);
	}
	
	public Config getGeneralConfiguration() {
		return ((DlgConfigGeneral)_dlg.getSource()).getConfig();
	}
	
	public void setConfigGeneralPropertyName(String name) {
		new JTextFieldOperator(_dlg2, 0).setText(name);
	}
	
	public void pushConfigPopupDialogButton(int index) {
		new JButtonOperator(_dlg2, index).push();
	}
	
	public TestRunner getTreeSelectedTestRunner() {
		return (TestRunner)_tree.getLastSelectedPathComponent();
	}
	
	public TestSuite getTreeSelectedTestSuite() {
		return (TestSuite)_tree.getLastSelectedPathComponent();
	}
	
	// OPT mit getTreeRootItemCount zusammenlegen
	public int getTreeTestRunnerItemCount() {
		return _tree.getChildCount(((TestRunnerModel)_tree.getModel())
				.getTestRunnerList().get(0));
	}
	
	public int getTreeTestSuiteItemCount() {
		return _tree.getChildCount(_tree.getLastSelectedPathComponent());
	}
	
	public org.testsuite.data.Test getTreeSelectedTest() {
		return ((org.testsuite.data.Test)_tree.getLastSelectedPathComponent());
	}
	
	public JTextFieldOperator getDialog2TextField(int index) {
		return new JTextFieldOperator(_dlg2, index);
	}
	
	public JButtonOperator getDialog2Button(int index) {
		return new JButtonOperator(_dlg2, index);
	}
	
	public Library getSelectedConfigurationLibrary() {
		return (Library)new JListOperator(_dlg, 0).getModel().getElementAt(0);
	}
	
	public boolean isDialog2Visible() {
		return _dlg2.isVisible();
	}
	
	public void pushButtonValidateConfiguration() {
		_btnConfigValidate.push();
	}
	
	/**
	 * Start the jemmy tests.
	 */
	@Override
	public int runIt(Object arg0) {
		return 0;
	}

}
