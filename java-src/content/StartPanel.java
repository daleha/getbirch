package content;


import java.awt.*;
import java.io.File;
import java.net.*;
import javax.swing.*;
import javax.swing.border.*;

import wizard.Wizard;
import wizard.WizardModel;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JButton;

import JarHandler.JarUtil;
import javax.swing.JLabel;
import javax.swing.JTextArea;


public class StartPanel extends JPanel{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;  //  @jve:decl-index=0:visual-constraint="8,39"
	private JRadioButton newInstallButton = null;
	private JRadioButton updateButton = null;
	private ButtonGroup bgroup = null;
	private JPanel contentPanel=null;
    
    private JLabel iconLabel;
    private ImageIcon icon;

	private JTextField updateDirectoryText = null;
	private JButton updateDirectoryButton = null;
	private JLabel jLabel = null;
	private JLabel jLabel2 = null;
	private JTextArea jTextArea = null;
	private JLabel jLabel3 = null;
	private JTextArea jTextArea1 = null;
	private boolean isInstall;
	private JCheckBox backupBirch = null;
	private JTextField jTextField = null;
	private JLabel jLabel1 = null;

    
    public StartPanel() {
    	
        iconLabel = new JLabel();
        contentPanel = getJContentPane();
        contentPanel.setVisible(true);
        contentPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

        icon = getImageIcon();

        setLayout(new java.awt.BorderLayout());

        if (icon != null)
            iconLabel.setIcon(icon);
        
        iconLabel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        
        add(iconLabel, BorderLayout.WEST);
        

        add(contentPanel, BorderLayout.CENTER);
        updateDirectoryText.setEnabled(updateButton.isSelected());
    	updateDirectoryButton.setEnabled(updateButton.isSelected());
    	isInstall=true;
        
    }
    
    public void setActionListeners(ActionListener al){
    	newInstallButton.addActionListener(al);
		updateButton.addActionListener(al);
		updateButton.addActionListener(al);
		updateDirectoryButton.addActionListener(al);

    
    }
    public boolean isDefaultSelected(){
    	return newInstallButton.isSelected();
    }
    
    public void toggleUpdateThaw(){
    	
    	
    	updateDirectoryText.setEnabled(updateButton.isSelected());
    	updateDirectoryButton.setEnabled(updateButton.isSelected());
    	backupBirch.setEnabled(updateButton.isSelected());
    	
    	if (updateButton.isSelected()){
    		isInstall=false;
    		
    		if (updateDirectoryText.getText().equals(System.getProperty("user.home"))){
    			browseForUpdate();
    		}
    	}else{
    		isInstall=true;
    	}
    }
    
    public void disableUpdate(){
    	updateButton.setEnabled(false);
    }
    
    public void browseForUpdate(){
    	JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.showOpenDialog(null);
		String path = fc.getSelectedFile().getPath();
		updateDirectoryText.setText(path);
    }
    

    public boolean makeBackup(){
    	return backupBirch.isSelected();
    }
    public boolean isInstall(){
    	return isInstall;
    }
    
    public String getUpdateDir(){
    	String dir;
    	
    	dir= null;
    	
    	if (updateButton.isSelected()){
    		dir=updateDirectoryText.getText();
    	}
    	return dir;
    }
    
	private JPanel getJContentPane() {
		if (jContentPane == null) {
	
			
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(16, 144, 75, 26));
			jLabel1.setText("Email:");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(4, 185, 473, 26));
			jLabel3.setText("Update an existing installation:");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(4, 45, 472, 30));
			jLabel2.setText("Create a new installation:");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(4, 15, 476, 27));
			jLabel.setText("Welcome to the getbirch BIRCH installer.");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setSize(new Dimension(512, 344));
			jContentPane.add(getJRadioButton(), null);
			jContentPane.add(getJRadioButton1(), null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getJTextArea(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getJTextArea1(), null);
			jContentPane.add(getJCheckBox(), null);
			jContentPane.add(getJTextField2(), null);
			jContentPane.add(jLabel1, null);
			bgroup = new ButtonGroup();
			bgroup.add(newInstallButton);
			bgroup.add(updateButton);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton() {
		if (newInstallButton == null) {
			newInstallButton = new JRadioButton("Create a new installation",true);
			
			newInstallButton.setBounds(new Rectangle(4, 85, 370, 17));
		}
		return newInstallButton;
	}

	/**
	 * This method initializes jRadioButton1	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton1() {
		if (updateButton == null) {
			updateButton = new JRadioButton("Update an existing installation",false);
			
			updateButton.setBounds(new Rectangle(4, 220, 368, 17));
		}
		return updateButton;
	}

    
    private ImageIcon getImageIcon() {
        return new ImageIcon((URL)getResource("dna.jpg"));
    }
    
    private Object getResource(String key) {

        URL url = null;
        String name = key;

        if (name != null) {

            try {
                Class c = Class.forName("content.StartPanel");
                url = c.getResource(name);
            } catch (ClassNotFoundException cnfe) {
                System.err.println("Unable to find Main class");
            }
            return url;
        } else
            return null;

    }







	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField() {
		String path;

		
		if (updateDirectoryText == null) {
			
			path= System.getProperty("user.home");
			
			updateDirectoryText = new JTextField();
			updateDirectoryText.setEnabled(false);
			updateDirectoryText.setEditable(false);
			updateDirectoryText.setBounds(new Rectangle(30, 310, 350, 20));
			updateDirectoryText.setText(path);
		}
		return updateDirectoryText;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (updateDirectoryButton == null) {
			updateDirectoryButton = new JButton();
			updateDirectoryButton.setText("Browse");
			updateDirectoryButton.setEnabled(false);
			
			updateDirectoryButton.setBounds(new Rectangle(387, 310, 98, 23));
		}
		return updateDirectoryButton;
	}

	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setBounds(new Rectangle(19, 105, 473, 36));
			jTextArea.setEditable(false);
			jTextArea.setText("This will allow you to set up a new BIRCH installation, \neither from network or from an install disc.");
		}
		return jTextArea;
	}

	/**
	 * This method initializes jTextArea1	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea1() {
		if (jTextArea1 == null) {
			jTextArea1 = new JTextArea();
			jTextArea1.setBounds(new Rectangle(17, 238, 473, 34));
			jTextArea1.setEditable(false);
			jTextArea1.setText("Update to the newest release.\nNote: A backup is strongly recommended if you have custom changes.");
		}
		return jTextArea1;
	}

	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox() {
		if (backupBirch == null) {
			backupBirch = new JCheckBox();
			backupBirch.setText("Create a backup of existing install");
			backupBirch.setSelected(true);
			backupBirch.setBounds(new Rectangle(30, 276, 338, 21));
			backupBirch.setEnabled(updateButton.isSelected());
		}
		return backupBirch;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField2() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(103, 145, 273, 24));
			jTextField.setText("Enter BIRCH Administrator's email");
		}
		return jTextField;
	}
	
	public String getEmail(){
		return jTextField.getText();
		
	}
}
