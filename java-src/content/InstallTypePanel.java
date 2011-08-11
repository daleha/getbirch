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


public class InstallTypePanel extends JPanel{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;  //  @jve:decl-index=0:visual-constraint="8,39"
	private JRadioButton defaultInstallButton = null;
	private JRadioButton advancedInstallButton = null;
	private ButtonGroup bgroup = null;
	private JPanel contentPanel=null;
    
    private JLabel iconLabel;
    private ImageIcon icon;
    

	private JLabel jLabel = null;
	public JTextArea jTextArea = null;

	private boolean networkInstall;
	private JLabel jLabel1 = null;

    
    public InstallTypePanel() {
    	
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

    	networkInstall=true;
        
    }
    
    public void setActionListeners(ActionListener al){
    	defaultInstallButton.addActionListener(al);
		advancedInstallButton.addActionListener(al);

    
    }
    public boolean isDefaultSelected(){
    	return defaultInstallButton.isSelected();
    }


    
    public void setDiscInstall(){
    	networkInstall=false;
    	defaultInstallButton.setText("Default install (from disc)");
    }
    
    public boolean isNetworkInstall(){
    	return networkInstall;
    }
    

    
	private JPanel getJContentPane() {
		if (jContentPane == null) {
	
			

			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(18, 197, 465, 26));
			jLabel1.setText("-------------------------------------------------------------------------");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(15, 15, 476, 27));
			jLabel.setText("Please choose default or advanced install type");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setSize(new Dimension(512, 344));
			jContentPane.add(getJRadioButton(), null);
			jContentPane.add(getJRadioButton1(), null);

			jContentPane.add(jLabel, null);
			jContentPane.add(getJTextArea(), null);
			jContentPane.add(jLabel1, null);
			bgroup = new ButtonGroup();
			bgroup.add(defaultInstallButton);
			bgroup.add(advancedInstallButton);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton() {
		if (defaultInstallButton == null) {
			defaultInstallButton = new JRadioButton("Default install (from network)",true);
			
			defaultInstallButton.setBounds(new Rectangle(17, 57, 370, 17));
		}
		return defaultInstallButton;
	}

	/**
	 * This method initializes jRadioButton1	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton1() {
		if (advancedInstallButton == null) {
			advancedInstallButton = new JRadioButton("Advanced install",false);
			
			advancedInstallButton.setBounds(new Rectangle(16, 220, 368, 17));
		}
		return advancedInstallButton;
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
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setBounds(new Rectangle(15, 75, 467, 114));
			jTextArea.setEditable(false);
			
		}
		return jTextArea;
	}


}
