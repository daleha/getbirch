
import java.awt.BorderLayout;



import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

import java.awt.Dimension;
import javax.swing.JTextArea;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JScrollPane;

import JarHandler.JarUtil;


public class OutputConsole extends JFrame implements WindowListener{

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JTextArea jTextArea = null;
	private JScrollPane jScrollPane = null;
	private JProgressBar progressBar;

	/**
	 * This is the default constructor
	 */
	public OutputConsole() {
		super();
		this.setSize(441, 261);
		initialize();
		this.addWindowListener(this);
		show();


	}
	
	public void writeLine(String line){
		jTextArea.setText(jTextArea.getText()+line+"\n");
		jScrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
			public void adjustmentValueChanged(AdjustmentEvent e) {  
			e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
			}});
	}
	
	public void setProgress(int progress){
		progressBar.setEnabled(true);
		progressBar.setVisible(true);
		progressBar.setValue(progress);
		
	}

	public void setIndeterminate(boolean isIndeterminate)
	{
		progressBar.setIndeterminate(isIndeterminate);
	}

	public void setProgressString(String message)
	{
		progressBar.setString(message);
	}
	
	public void hideProgress(){
		progressBar.setVisible(false);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(475, 257);
		this.setContentPane(getJContentPane());
		this.setTitle("Getbirch BIRCH Installer Console");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJTextArea(), BorderLayout.CENTER);
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
			jContentPane.add(getProgressBar(),BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	
	private JProgressBar getProgressBar(){
		
		
		progressBar = new JProgressBar(0,100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setEnabled(false);
		progressBar.setVisible(false);
		
		return progressBar;
	}
	
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setEditable(false);
		}
		return jTextArea;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane(jTextArea);
		}
		
		
		return jScrollPane;
	}
	public void shutdown(){
		
		JarUtil.cleanUp(JarUtil.getPath()+"/getbirch_temp");
		JarUtil.cleanUp(JarUtil.getPath()+"/cachedir");
		System.exit(0);
	}

	public void windowClosing(WindowEvent arg0) {
		JOptionPane.showMessageDialog(null,"Installation cancelled.");
		shutdown();
		
	}
	

	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	public void windowClosed(WindowEvent arg0) {
		
		
	}

	


	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
