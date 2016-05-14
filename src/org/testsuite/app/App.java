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
import javax.swing.JTextPane;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JProgressBar;
import javax.swing.JButton;

/**
 * Window that the test app displays.
 * 
 * @author René Majewski
 *
 * @version 0.1
 */
@SuppressWarnings("serial")
public class App extends JFrame implements ActionListener {
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
	 * Saves the instance of resource bundle
	 */
	private ResourceBundle _bundle;
	
	/**
	 * Initializes the main window.
	 */
	public App() {
		super();
		
		_bundle = ResourceBundle.getBundle(
				"resources.lang.org.testsuite.app.App");
		
		setTitle("TestSuiteApp");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setSize(600, 400);
		setExtendedState(MAXIMIZED_BOTH);
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.25);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JTree tree = new JTree();
		splitPane.setLeftComponent(tree);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setRightComponent(scrollPane);
		
		JTextPane txtHtml = new JTextPane();
		txtHtml.setEditable(false);
		scrollPane.setViewportView(txtHtml);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 25));
		
		JProgressBar pBar = new JProgressBar();
		panel.add(pBar, BorderLayout.NORTH);
		
		JPanel panButtons = new JPanel();
		panel.add(panButtons, BorderLayout.SOUTH);
		panButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnRun = new JButton(_bundle.getString("btnRun"));
		btnRun.setEnabled(false);
		btnRun.addActionListener(this);
		btnRun.setActionCommand(AC_RUN);
		btnRun.setName(AC_RUN);
		panButtons.add(btnRun);
		
		JButton btnCancel = new JButton(_bundle.getString("btnCancel"));
		btnCancel.setEnabled(false);
		btnCancel.addActionListener(this);
		btnCancel.setActionCommand(AC_CANCEL);
		btnCancel.setName(AC_CANCEL);
		panButtons.add(btnCancel);
		
		JButton btnLoad = new JButton(_bundle.getString("btnLoad"));
		btnLoad.addActionListener(this);
		btnLoad.setActionCommand(AC_LOAD);
		btnLoad.setName(AC_LOAD);
		panButtons.add(btnLoad);
		
		JButton btnExit = new JButton(_bundle.getString("btnExit"));
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				dispose();
			}
		});
		panButtons.add(btnExit);
		
		setVisible(true);
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
				break;
				
			case AC_LOAD:
				break;
				
			case AC_RUN:
				break;
		}
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
