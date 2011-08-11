package content;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import wizard.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.JLabel;

import JarHandler.JarUtil;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;



public class AdvancedPanel extends JPanel {
 
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;  //  @jve:decl-index=0:visual-constraint="-35,34"
	private JLabel jLabel = null;
	private JButton browseButton = null;
	private JTextArea installDirTextArea = null;
	private JTextArea pathSuffix = null;
	private JLabel jLabel1 = null;
	private JCheckBox miniBirchCheckBox = null;
	private JRadioButton developmentRadioButton = null;
	private JRadioButton releaseRadioButton = null;
	private JRadioButton gitRadioButton = null;
	private JComboBox gitTagSelect = null;
	private JLabel jLabel2 = null;
	private JCheckBox solarisSparcCheckBox = null;
	private JCheckBox solaris64CheckBox = null;
	private JCheckBox linuxIntelCheckBox = null;
	private JCheckBox linux64CheckBox = null;
	private JCheckBox osxCheckBox = null;
	private JCheckBox windows32Checkbox =null;
	private JPanel contentPanel;
	private JLabel iconLabel;
	private ImageIcon icon;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JCheckBox logToggleBox = null;
	private JTextField installLogText = null;
	private JButton logBrowseButton = null;
	private JLabel jLabel6 = null;


	

	
    public AdvancedPanel() {
     
        super();
                
        contentPanel = getJContentPane();
        contentPanel.setVisible(true);
        contentPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        setLayout(new java.awt.BorderLayout());

        icon = getImageIcon();
        iconLabel = new JLabel();
        
        if (icon != null)
            iconLabel.setIcon(icon);
        
        iconLabel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        
        add(iconLabel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

    }
    
    
    public void setActionListners(ActionListener al){
    	browseButton.addActionListener(al);
    	gitRadioButton.addActionListener(al);
	developmentRadioButton.addActionListener(al);
	releaseRadioButton.addActionListener(al);

    }

    public JRadioButton getGitButton()
    {    
	return gitRadioButton;
    }
    
    public void setPlatform(String platform){
    	
    	final String linux_intel="linux-intel";
    	final String linux_64="linux-x86_64";
    	final String solaris_sparc="solaris-sparc";
    	final String solaris_64="solaris-amd64";
    	final String osx_64="osx-x86_64";
    	final String winxp_32="winxp-32";

    	
    	if (platform.equals(linux_intel)){
    		linuxIntelCheckBox.setSelected(true);
    		linuxIntelCheckBox.setText(linuxIntelCheckBox.getText()+"*");
    	}else if (platform.equals(linux_64)){
    		linux64CheckBox.setSelected(true);
    		linux64CheckBox.setText(linux64CheckBox.getText()+"*");
    	}else if (platform.equals(solaris_sparc)){
    		solarisSparcCheckBox.setSelected(true);
    		solarisSparcCheckBox.setText(solarisSparcCheckBox.getText()+"*");
    	}else if (platform.equals(solaris_64)){
    		solaris64CheckBox.setSelected(true);
    		solaris64CheckBox.setText(solaris64CheckBox.getText()+"*");
    	}else if (platform.equals(osx_64)){
    		osxCheckBox.setSelected(true);
    		osxCheckBox.setText(osxCheckBox.getText()+"*");
    	}else if (platform.equals(winxp_32)){
    		JOptionPane.showMessageDialog(null, "Platform detected as windows XP. Binaries have not yet been ported, this is an alpha version.");
    		windows32Checkbox.setSelected(true);
    		windows32Checkbox.setText(windows32Checkbox.getText()+"*");
    		
    		StartPanelDescriptor.getInstance().setIsWindows();
    	}
    	
    }
    
    public void setDiscInstall(){
    	miniBirchCheckBox.setEnabled(false);
    	developmentRadioButton.setEnabled(false); 
    	


    }
    
    public void browseForInstallDir(){
    	JFileChooser fc = new JFileChooser();

    	fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.showOpenDialog(null);
		String path = fc.getSelectedFile().getPath();
		installDirTextArea.setText(path);

    }
    

    
    public JButton getBrowseButton() {
		return browseButton;
	}
    
    public String getInstallDir(){
    	return installDirTextArea.getText()+"/"+pathSuffix.getText();
    }
    
    public String getFrameworkVersion(){
    	String version;
    	
    	version = "";
    	
    	if (miniBirchCheckBox.isSelected()){
    		version+="M";
    	}
    	
    	if(developmentRadioButton.isSelected()){
    		version+="D";
    	}
    	
    	
    	return version;
    }
    
    public boolean isDevelopment(){
    	
    	return developmentRadioButton.isSelected();
    }
    
    public boolean isGitInstall()
    {
    	return gitRadioButton.isSelected();
    }
    
    /**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getLogTextField() {
		String path;

		
		if (installLogText == null) {
			
			path= System.getProperty("user.home");
			
			installLogText = new JTextField();
			installLogText.setEditable(false);
			installLogText.setBounds(new Rectangle(12, 91, 290, 20));
			installLogText.setText(path);
		}
		return installLogText;
	}
	
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getLogButton() {
		if (logBrowseButton == null) {
			logBrowseButton = new JButton();
			logBrowseButton.setText("Browse");

			
			logBrowseButton.setBounds(new Rectangle(310, 90, 98, 23));
		}
		return logBrowseButton;
	}
	public void setActionListeners(ActionListener al){
		logBrowseButton.addActionListener(al);
    
    }
	
    public void browseForLog(){
    	JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.showOpenDialog(null);
		String path = fc.getSelectedFile().getPath();
		installLogText.setText(path);
    }
    
    public String getLogDir(){
    	String dir;
    	
    	dir= null;
    	

    	dir=installLogText.getText();

    	return dir;
    }
    
    
    public ArrayList<String> getBinariesSelected(){
    	ArrayList<String> binaries;
    	
    	binaries= new ArrayList<String>();
    	
    	if (solarisSparcCheckBox.isSelected()){
    		binaries.add("solaris-sparc");
    	}
    	if (solaris64CheckBox.isSelected()){
    		binaries.add("solaris-amd64");
    	}
    	if (linuxIntelCheckBox.isSelected()){
    		binaries.add("linux-intel");
    	}
    	if (linux64CheckBox.isSelected() ){
    		binaries.add("linux-x86_64");
    	}
    	if (osxCheckBox.isSelected() ){
    		binaries.add("osx-x86_64");
    	}
/*    	if (windows32Checkbox.isSelected() ){
    		binaries.add("winxp-32");
    	}
*/    	
    	

    	
    	return binaries;
    }
    
    
    private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(11, 72, 218, 19));
			jLabel6.setText("Install log directory:");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(11, 2, 394, 26));
			jLabel5.setText("Advanced Install. Please select your preferences");
			ButtonGroup group = new ButtonGroup();
			pathSuffix = new JTextArea();
			pathSuffix.setBounds(new Rectangle(232, 48, 61, 21));
			pathSuffix.setText("BIRCH");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(22, 351, 312, 20));
			jLabel4.setText("* - Automatically detected, recommended.");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(220, 48, 10, 21));
			jLabel3.setText(" /");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(18, 250, 132, 18));
			jLabel2.setText("Binaries:");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(11, 115, 127, 21));
			jLabel1.setText("Framework Type:");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(11, 28, 131, 17));
			jLabel.setText("Install directory:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setSize(new Dimension(464, 376));
			jContentPane.add(jLabel, null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJTextArea(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(pathSuffix, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJCheckBox(), null);
			jContentPane.add(getJCheckBox1(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getJCheckBox2(), null);
			jContentPane.add(getJCheckBox3(), null);
			jContentPane.add(getJCheckBox4(), null);
			jContentPane.add(getJCheckBox5(), null);
			jContentPane.add(getJCheckBox6(), null);

			jContentPane.add(getLogTextField(), null);
			jContentPane.add(getLogButton(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getWindows32Checkbox(), null);
			jContentPane.add(getJRadioButton(), null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(getGitRadioButton(), null);
			jContentPane.add(getGitComboBox(), null);
			group.add(releaseRadioButton);
			group.add(developmentRadioButton);
			group.add(gitRadioButton);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (browseButton == null) {
			browseButton = new JButton();
			browseButton.setBounds(new Rectangle(310, 45, 98, 23));
			browseButton.setText("Browse");
		}
		return browseButton;
	}

	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea() {
		if (installDirTextArea == null) {

			String path;

			path= System.getProperty("user.home");
			
			installDirTextArea = new JTextArea();
			installDirTextArea.setEditable(false);
			installDirTextArea.setText(System.getProperty("user.home"));
			
			installDirTextArea.setBounds(new Rectangle(13, 49, 206, 21));
		}
		return installDirTextArea;
	}

	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox() {
		if (miniBirchCheckBox == null) {
			miniBirchCheckBox = new JCheckBox("miniBirch");
			miniBirchCheckBox.setBounds(new Rectangle(268, 137, 149, 19));
		}
		return miniBirchCheckBox;
	}

	/**
	 * This method initializes jCheckBox1	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JRadioButton getJCheckBox1() {
		if (developmentRadioButton == null) {
			developmentRadioButton = new JRadioButton("Development Version");
			developmentRadioButton.setBounds(new Rectangle(13, 160, 231, 23));
		}
		return developmentRadioButton;
	}

	/**
	 * This method initializes jCheckBox2	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox2() {
		if (solarisSparcCheckBox == null) {
			solarisSparcCheckBox = new JCheckBox("bin-solaris-sparc");
			solarisSparcCheckBox.setBounds(new Rectangle(18, 300, 183, 22));
		}
		return solarisSparcCheckBox;
	}

	/**
	 * This method initializes jCheckBox3	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox3() {
		if (solaris64CheckBox == null) {
			solaris64CheckBox = new JCheckBox("bin-solaris-amd64");
			solaris64CheckBox.setBounds(new Rectangle(18, 275, 188, 22));
		}
		return solaris64CheckBox;
	}

	/**
	 * This method initializes jCheckBox4	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox4() {
		if (linuxIntelCheckBox == null) {
			linuxIntelCheckBox = new JCheckBox("bin-linux-intel");
			linuxIntelCheckBox.setBounds(new Rectangle(211, 275, 190, 22));
		}
		return linuxIntelCheckBox;
	}

	/**
	 * This method initializes jCheckBox5	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox5() {
		if (linux64CheckBox == null) {
			linux64CheckBox = new JCheckBox("bin-linux-x86_64");
			linux64CheckBox.setBounds(new Rectangle(211, 300, 193, 22));
		}
		return linux64CheckBox;
	}

	/**
	 * This method initializes jCheckBox6	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox6() {
		if (osxCheckBox == null) {
			osxCheckBox = new JCheckBox("bin-osx-x86_64");
			osxCheckBox.setBounds(new Rectangle(18, 325, 180, 22));
		}
		return osxCheckBox;
	}
	
	/**
	 * This method initializes windows32Checkbox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getWindows32Checkbox() {
		if (windows32Checkbox == null) {
			windows32Checkbox = new JCheckBox("bin-winxp-32");
			windows32Checkbox.setBounds(new Rectangle(211, 325, 192, 22));
			windows32Checkbox.setEnabled(false);
		}
		return windows32Checkbox;
	}
    
	private ImageIcon getImageIcon() {
        return new ImageIcon((URL)getResource("dna.jpg"));
    }
    
    private Object getResource(String key) {

        URL url = null;
        String name = key;

        if (name != null) {

            try {
                Class c = Class.forName("content.AdvancedPanel");
                url = c.getResource(name);
            } catch (ClassNotFoundException cnfe) {
                System.err.println("Unable to find Main class");
            }
            return url;
        } else
            return null;

    }

    public void setVersion(String version){
    	releaseRadioButton.setText(releaseRadioButton.getText()+" Version: "+version);
    }
    
    public String[] getInstallInfo(){
    	String[] info ={"Install directory "+getInstallDir(),
    			"Log File directory"+getLogDir(),
    			"Binaries:"+binariesToString()
    			
    			
    	};
    	
    	return info;
    }
    
    private String binariesToString(){
    	String binString;
    	ArrayList binaries=getBinariesSelected();
    	
    	binString="";
    	for (int i =0; i<binaries.size(); i++){
    		binString=binString+binaries.get(i);
    		if (i+1<binaries.size()){
    			binString=binString+",";
    		}
    	}
    	
    	return binString;
    }
	/**
	 * This method initializes jRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton() {
		if (releaseRadioButton == null) {
			releaseRadioButton = new JRadioButton("Current ");
			releaseRadioButton.setBounds(new Rectangle(13, 140, 214, 16));
			releaseRadioButton.setSelected(true);
		}
		return releaseRadioButton;
	}


	/**
	 * This method initializes jRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getGitRadioButton() {
		if (gitRadioButton == null) {
			gitRadioButton = new JRadioButton("Git (bleeding edge)");
			gitRadioButton.setBounds(new Rectangle(13, 180, 231, 23));
		}
		return gitRadioButton;
	}


	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	public JComboBox getGitComboBox() {
		if (gitTagSelect == null) {
			gitTagSelect = new JComboBox();
			gitTagSelect.setBounds(new Rectangle(16, 205, 421, 24));
			gitTagSelect.setEnabled(false);
		}
		return gitTagSelect;
	}


	



	
    

}
