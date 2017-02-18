package com.lonsec.gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.springframework.context.ApplicationContext;

import com.lonsec.config.AppContext;
import com.lonsec.process.ReturnsProcessor;

/**
 * GUI based launcher of the application. It allows the user to browse and select
 * files and compute the output into a file.
 *
 */
@SuppressWarnings("serial")
public class Launch extends JPanel {

	private static final int TEXT_SIZE = 32;
	private static final String FUND_FILE = "Fund File";
	private static final String BENCHMARK_FILE = "Benchmark File";
	private static final String FUND_RETURN_FILE = "Fund Return File";
	private static final String BENCHMARK_RETURN_FILE = "Benchmark Return File";
	private JTextField fundFile = new JTextField(TEXT_SIZE);
	private JTextField benchmarkFile = new JTextField(TEXT_SIZE);
	private JTextField fundReturnFile = new JTextField(TEXT_SIZE);
	private JTextField benchmarkReturnFile = new JTextField(TEXT_SIZE);
	private JTabbedPane tabbedPane;
	
	public Launch() {
		super(new GridLayout(0, 1));
		
		this.add(createPathPanel(FUND_FILE, fundFile));
		this.add(createPathPanel(BENCHMARK_FILE, benchmarkFile));
		this.add(createPathPanel(FUND_RETURN_FILE, fundReturnFile));
		this.add(createPathPanel(BENCHMARK_RETURN_FILE, benchmarkReturnFile));
		
		JPanel submitPanel = new JPanel();
		submitPanel.add(new JButton(new AbstractAction("Compute") {

			public void actionPerformed(ActionEvent e) {
				String fundFileValue = fundFile.getText();
				String benchmarkFileValue = benchmarkFile.getText();
				String fundReturnsFileValue = fundReturnFile.getText();
				String benchmarkReturnsFileValue = benchmarkReturnFile.getText();
				
				if(fundFileValue.trim().length() == 0 || benchmarkFileValue.trim().length() == 0 || fundReturnsFileValue.trim().length() == 0 
						|| benchmarkReturnsFileValue.trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "Please specify all files", "Error", JOptionPane.PLAIN_MESSAGE);
					return;
				}
				
				ApplicationContext context = AppContext.getContext();
				ReturnsProcessor processor = context.getBean(ReturnsProcessor.class);
				processor.process(fundFileValue, benchmarkFileValue, fundReturnsFileValue, benchmarkReturnsFileValue);
				
				JOptionPane.showMessageDialog(null, "Computation completed.", "Info", JOptionPane.PLAIN_MESSAGE);
			}
		}));

		this.add(submitPanel);
	}
	
	private JPanel createPathPanel(String name, final JTextField jtf) {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(new JButton(new AbstractAction(name) {

			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser(new File(System
						.getProperty("user.dir")));
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int result = jfc.showOpenDialog(Launch.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					jtf.setText(jfc.getSelectedFile().getPath());
				}
			}
		}));
		panel.add(jtf);
		
		return panel;
	}

	public void display() {
		JFrame f = new JFrame("Funds Performance");
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		tabbedPane = new JTabbedPane();
		f.add(tabbedPane);
		tabbedPane.add("Files for computation", this);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.setSize(new Dimension(700, 400));
	}

	public static void main(final String[] args) throws Exception {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Launch pf = new Launch();
				pf.display();
			}
		});
	}
}
