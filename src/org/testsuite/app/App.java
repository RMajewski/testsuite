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

package org.testsuite.app;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.html.HTMLEditorKit;

import org.testsuite.core.ConfigParser;
import org.testsuite.core.HtmlOut;
import org.testsuite.core.TestRunner;
import org.testsuite.data.Config;
import org.testsuite.data.TestEvent;
import org.testsuite.data.TestEventListener;

import javax.swing.JTextPane;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JProgressBar;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JScrollBar;

/**
 * Window that the test app displays.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
@SuppressWarnings("serial")
public class App extends JFrame implements ActionListener, TestEventListener {
	/**
	 * Saves the string of action command for the configuration load button.
	 */
	private static final String AC_LOAD = "App.btnLoad";
	
	/**
	 * Saves the string of action command for the cancel button.
	 */
	private static final String AC_CANCEL = "App.btnCancel";
	
	/**
	 * Saves the string of action command for the run button.
	 */
	private static final String AC_RUN = "App.btnRun";
	
	/**
	 * Saves the title of the main window
	 */
	public static final String WND_TITLE = "TestSuiteApp";
	
	/**
	 * Saves the name of the bundle file
	 */
	public static final String BUNDLE_FILE = 
			"resources.lang.org.testsuite.app.App";
	
	/**
	 * Saves the button for run tests.
	 */
	private JButton _btnRun;
	
	/**
	 * Saves the button for cancel run tests.
	 */
	private JButton _btnCancel;
	
	/**
	 * Saves the button for load the configuration file
	 */
	private JButton _btnLoad;
	
	/**
	 * Saves the button for exit the app.
	 */
	private JButton _btnExit;
	
	/**
	 * Saves the instance of resource bundle
	 */
	private ResourceBundle _bundle;
	
	/**
	 * Saves the instance of the configuration.
	 */
	private Config _config;
	
	/**
	 * Saves the instance of the JTree.
	 */
	private JTree _tree;
	
	/**
	 * Saves the instance of the JProgressBar.
	 */
	private JProgressBar _pBar;
	
	/**
	 * Saves the instance of the JTextArea for html output.
	 */
	private JEditorPane _txtHtml;
	
	/**
	 * Saves the instance of the JTextArea for output messages.
	 */
	private JTextArea _txtMessage;
	
	/**
	 * Saves the instance of the JScrollPane for _tpMessage.
	 */
	private JScrollPane _spMessage;
	
	/**
	 * Saves the thread of run tests.
	 */
	private Thread _thread;
	
	/**
	 * Initializes the main window.
	 */
	public App() {
		super();
		
		_bundle = ResourceBundle.getBundle(BUNDLE_FILE);
		
		_config = new Config();
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new Date());
		DecimalFormat df = new DecimalFormat("00");
		_config.setPathSuitesResult(
				df.format(gc.get(GregorianCalendar.YEAR)) +
				df.format(gc.get(GregorianCalendar.MONTH) + 1) +
				df.format(gc.get(GregorianCalendar.DAY_OF_MONTH)) +
				df.format(gc.get(GregorianCalendar.HOUR_OF_DAY)) +
				df.format(gc.get(GregorianCalendar.MINUTE)) +
				df.format(gc.get(GregorianCalendar.SECOND)));
		
		setTitle(WND_TITLE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setSize(600, 400);
		setExtendedState(MAXIMIZED_BOTH);
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.25);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		_tree = new JTree();
		_tree.setModel(new TestRunnerModel());
		_tree.setCellRenderer(new TestRunnerRenderer());
		_tree.addMouseListener(new MouseListener() {

			/**
			 * If you double-click a test selected will toggle.
			 * 
			 * @param me Dates of the events.
			 */
			@Override
			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount() == 2) {
					Object c = _tree.getLastSelectedPathComponent();
					if (c instanceof org.testsuite.data.Test) {
						((org.testsuite.data.Test)c).setExecuted(
								!((org.testsuite.data.Test)c).isExecuted());
						((TestRunnerModel)_tree.getModel())
							.fireTreeNodesChanged(_tree.getSelectionPath());
					}
				}
			}

			/**
			 * Is not needed.
			 */
			@Override
			public void mouseEntered(MouseEvent arg0) {
				
			}

			/**
			 * Is not needed.
			 */
			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			/**
			 * Is not needed.
			 */
			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			/**
			 * Is not needed.
			 */
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			
		});
		
		splitPane.setLeftComponent(_tree);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setRightComponent(scrollPane);
		
		_txtHtml = new JEditorPane();
		_txtHtml.setContentType("text/html");
		_txtHtml.setEditorKit(new HTMLEditorKit());
		_txtHtml.setEditable(false);
		scrollPane.setViewportView(_txtHtml);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 10));
		
		_pBar = new JProgressBar();
		_pBar.setMaximum(0);
		panel.add(_pBar, BorderLayout.NORTH);
		
		JPanel panButtons = new JPanel();
		panel.add(panButtons, BorderLayout.SOUTH);
		panButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		_btnRun = new JButton(_bundle.getString("btnRun"));
		_btnRun.setEnabled(false);
		_btnRun.addActionListener(this);
		_btnRun.setActionCommand(AC_RUN);
		_btnRun.setName(AC_RUN);
		panButtons.add(_btnRun);
		
		_btnCancel = new JButton(_bundle.getString("btnCancel"));
		_btnCancel.setEnabled(false);
		_btnCancel.addActionListener(this);
		_btnCancel.setActionCommand(AC_CANCEL);
		_btnCancel.setName(AC_CANCEL);
		panButtons.add(_btnCancel);
		
		_btnLoad = new JButton(_bundle.getString("btnLoad"));
		_btnLoad.addActionListener(this);
		_btnLoad.setActionCommand(AC_LOAD);
		_btnLoad.setName(AC_LOAD);
		panButtons.add(_btnLoad);
		
		_btnExit = new JButton(_bundle.getString("btnExit"));
		_btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				dispose();
			}
		});
		panButtons.add(_btnExit);
		
		_txtMessage = new JTextArea();
		_txtMessage.setEditable(false);
		_spMessage = new JScrollPane(_txtMessage);
		Dimension d = new Dimension();
		d.setSize(400, 75);
		_spMessage.setPreferredSize(d);
		panel.add(_spMessage, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	/**
	 * Returns the configuration.
	 * 
	 * @return Configuration
	 */
	public Config getConfig() {
		return _config;
	}

	/**
	 * Responds to a button click.
	 * 
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case AC_CANCEL:
				_thread.stop();
				break;
				
			case AC_LOAD:
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle(_bundle.getString("configFileOpenTitle"));
				fc.setFileFilter(new FileNameExtensionFilter(
						"Konfigurationsdatei", "xml"));
				fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
				int state = fc.showOpenDialog(this);
				File file = fc.getSelectedFile();
				
				if ((state == JFileChooser.APPROVE_OPTION) && (file != null) && 
						(file.exists())) {
					ConfigParser parser = new ConfigParser(_config, 
							file.getPath());
					
					if (parser.parse()) {
						((TestRunnerModel)_tree.getModel()).setListOfTestRunner(
								parser.getTestRunnerList());
						_btnRun.setEnabled(true);
						
						int tests = 0;
						for (int index = 0; 
								index < parser.getTestRunnerList().size(); 
								index++)
							tests += parser.getTestRunnerList().get(index)
								.getTestsCount();
						_pBar.setMaximum(tests);
						_pBar.setValue(0);
					}
				}
				
				break;
				
			case AC_RUN:
				TestRun run = new TestRun(((TestRunnerModel)_tree.getModel())
					.getTestRunnerList(), this);
				_thread = new Thread(run);
				_btnExit.setEnabled(false);
				_btnRun.setEnabled(false);
				_btnCancel.setEnabled(true);
				_btnLoad.setEnabled(false);
				_thread.start();
				
				Thread thread = new Thread() {
					@Override
					public void run() {
						boolean loop = true;
						while (loop) {
							yield();
							if (!_thread.isAlive()) {
								loop = false;
								_btnCancel.setEnabled(false);
								_btnRun.setEnabled(true);
								_btnLoad.setEnabled(true);
								_btnExit.setEnabled(true);
							}
						}
						
						// Create HTML ouput
						String htmlFile = _config.getPathResult() + File.separator;
						File f = new File(htmlFile);
						if (!f.exists())
							f.mkdirs();
						
						htmlFile += _bundle.getString("html_result") + "_" +
								_config.getPathSuitesResult() + ".html";
						try {
							
							HtmlOut html = new HtmlOut(htmlFile);
							html.htmlHead();
							
							List<TestRunner> testRunner = 
									((TestRunnerModel)_tree.getModel())
									.getTestRunnerList();
							
							for (int runner = 0; runner < testRunner.size(); runner++) {
								boolean line = true;
								if (runner == 0)
									line = false;
								html.writeHtml(testRunner.get(runner).createHtml(html, 
										line));
							}
							
							html.htmlEnd();
						} catch (IOException ex) {
							ex.printStackTrace();
						}
						
						// Read Html
						try {
							f = new File(htmlFile);
							if (f.exists())
								_txtHtml.setPage(f.toURI().toURL());
						} catch (Exception er) {
							er.printStackTrace();
						}
						
					}
				};
				thread.start();
				break;
		}
	}

	/**
	 * 
	 * 
	 * @param te Data of the event.
	 */
	@Override
	public void testExecutedCompleted(TestEvent te) {
		_pBar.setValue(_pBar.getValue() + 1);
		String tmp = te.getPackageName() + "." + te.getName() + ": " + 
				te.getResult();
		_txtMessage.setText(_txtMessage.getText() + System.lineSeparator() + tmp);
		_txtMessage.setRows(_txtMessage.getRows() + 1);
		_spMessage.getVerticalScrollBar().getModel().setValue(
				_spMessage.getVerticalScrollBar().getModel().getValue() +
				_txtMessage.getRows());
	}

	/**
	 * Starts the test app.
	 * 
	 * @param args Parameters from command line.
	 */
	public static void main(String[] args) {
		new App();
	}
}
