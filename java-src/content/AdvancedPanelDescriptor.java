package content;



import java.awt.*;
import java.awt.event.*;
import gitutil.GitFunctions;
import java.util.*;

import javax.swing.*;

import org.eclipse.jgit.lib.Ref;

import wizard.*;


public class AdvancedPanelDescriptor extends WizardPanelDescriptor implements ActionListener {
    
    public static final String IDENTIFIER = "CONNECTOR_CHOOSE_PANEL";
    private long lastcalled;
    AdvancedPanel panel2;
    static AdvancedPanelDescriptor instance;
    private String version;
    private ArrayList<String[]> tagList;
    private final String URL="git://git.cc.umanitoba.ca/birchdev.git";
    private JComboBox gitTagSelect;
    
    public AdvancedPanelDescriptor() {
        
        panel2 = new AdvancedPanel();
        
        
        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(panel2);
        panel2.setActionListeners(this);
        instance=this;
        
    }
    
    public static AdvancedPanelDescriptor getInstance(){
    	
    	return instance;
    }
    
    public Object getNextPanelDescriptor() {
        return FINISH;
    }
    
    public Object getBackPanelDescriptor() {
        return InstallTypePanelDescriptor.IDENTIFIER;
    }
    
    public void setDiscInstall(){
    	panel2.setDiscInstall();
    }
    
    public void aboutToDisplayPanel() {

        panel2.setActionListners(this);
    }    

    public void setPlatform(String platform){
    	panel2.setPlatform(platform);
    }
    
    public void setVersion(String version){
    	this.version=version;
    	panel2.setVersion(version);
    
    }
    
    public String getInstallInfo(){
    	String installInfo;
    	String[] rawInfo;
    	
    	rawInfo=panel2.getInstallInfo();
    	
    	installInfo="";
    	
    	for (int i=0; i<rawInfo.length; i++){
    		installInfo=installInfo+rawInfo[i]+"\n";
    	}
    	
    	installInfo=installInfo+"Version: "+version+"\n";
    	
    	return installInfo;
    	
    }
    public void actionPerformed(ActionEvent e) {
    	if (e.getSource()== panel2.getBrowseButton() && e.getWhen()!=lastcalled){
    		lastcalled=e.getWhen();
    		panel2.browseForInstallDir();
    	}else if (e.getSource() instanceof JButton){
			panel2.browseForLog();
		}else if (e.getSource() instanceof JRadioButton){

			gitTagSelect= panel2.getGitComboBox();
			if (e.getSource()== panel2.getGitButton())
			{	
				gitTagSelect.setEnabled(true);
				if (gitTagSelect.isEnabled())
					updateGitTagList();
			}else
			{
				gitTagSelect.setEnabled(false);
			}
		}
        
    }
    
    public void updateGitTagList ()
    {
    	tagList=GitFunctions.listRepo(URL);
    	gitTagSelect.removeAllItems();
		
		for (Iterator<String[]> i = tagList.iterator(); i.hasNext(); )
		{
			String[] curr= i.next();
			String str=curr[0]+":"+curr[1];
			gitTagSelect.addItem(str);
			if (curr[0].equals("HEAD"))
				gitTagSelect.setSelectedItem(str);
		}	
    	
    }
    
    public String[] getSelectedTag()
    {
    	String[] selectedTag;
    	int index = gitTagSelect.getSelectedIndex();
    	selectedTag=tagList.get(index);	
    	return selectedTag;
    }
    
  

            

}
