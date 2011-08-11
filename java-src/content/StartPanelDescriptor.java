package content;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import wizard.*;
import java.util.regex.*;


public class StartPanelDescriptor extends WizardPanelDescriptor implements ActionListener {
    
    public static final String IDENTIFIER = "INIT_PANEL";
    private Object nextPanel=InstallTypePanelDescriptor.IDENTIFIER;
    private StartPanel panel;
    private long lastcalled;
    static StartPanelDescriptor instance;
    
    public StartPanelDescriptor(Wizard wizard) {
    	
    	
    	panel= new StartPanel();
    	
        setPanelDescriptorIdentifier(IDENTIFIER);
		setPanelComponent(panel);
		panel.setActionListeners(this);
		instance=this;
    }
    
    static StartPanelDescriptor getInstance(){
    	return instance;
    }
    
    public Object getNextPanelDescriptor() {
        return nextPanel;
    }
    
    public Object getBackPanelDescriptor() {
        return null;
    }
    


	
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource()instanceof JRadioButton){
			setNextButton();
			panel.toggleUpdateThaw();


		}else if (arg0.getSource()  instanceof JButton && arg0.getWhen()!=lastcalled){
			lastcalled=arg0.getWhen();
			panel.browseForUpdate();
		}
	}
	
	public void aboutToDisplayPanel() {
		if (panel.isDefaultSelected()){
			nextPanel=InstallTypePanelDescriptor.IDENTIFIER;
			
		}else{
			nextPanel=FINISH;
		}
		
		
		
    }
	
	public boolean validateEmail(){
		
		 //Input the string for validation
	      String email = panel.getEmail();

	      //Set the email pattern string
	      Pattern p = Pattern.compile(".+@.+\\.[a-z]+");

	      //Match the given string with the pattern
	      Matcher m = p.matcher(email);

	      //check whether match is found 
	      boolean matchFound = m.matches();

	      
	      return matchFound;
	
	}
	
	public boolean isInstall(){
		return panel.isDefaultSelected();
	}
	
	
	public void setIsWindows(){
		panel.disableUpdate();
	}
	
	private void setNextButton(){
		if (panel.isDefaultSelected()){
			getWizard().getModel().setNextFinishButtonText(Wizard.NEXT_TEXT);
            getWizard().getModel().setNextFinishButtonIcon(Wizard.NEXT_ICON);
			nextPanel=InstallTypePanelDescriptor.IDENTIFIER;
			
            
		}else{
			getWizard().setNextFinishButtonEnabled(true);
			nextPanel=FINISH;
			getWizard().getModel().setNextFinishButtonText(Wizard.FINISH_TEXT);
			getWizard().getModel().setNextFinishButtonIcon(Wizard.FINISH_ICON);
		
		}
	}

    

}
