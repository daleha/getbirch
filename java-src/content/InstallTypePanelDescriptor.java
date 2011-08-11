package content;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import wizard.*;


public class InstallTypePanelDescriptor extends WizardPanelDescriptor implements ActionListener {
    
    public static final String IDENTIFIER = "INTRODUCTION_PANEL";
    private Object nextPanel=FINISH;
    private InstallTypePanel panel;
    
    public InstallTypePanelDescriptor(Wizard wizard) {
    	
    	
    	panel= new InstallTypePanel();
    	
        setPanelDescriptorIdentifier(IDENTIFIER);
		setPanelComponent(panel);
		panel.setActionListeners(this);
    }
    
    public Object getNextPanelDescriptor() {
        return nextPanel;
    }
    
    public Object getBackPanelDescriptor() {
        return StartPanelDescriptor.IDENTIFIER;
    }
    
    public void setDiscInstall(){
    	panel.setDiscInstall();
    }

	
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() instanceof JRadioButton){
			setNextButton();
		}
	}
	
	public void aboutToDisplayPanel() {
		if (panel.isDefaultSelected()){
			nextPanel=FINISH;
		}else{
			nextPanel=AdvancedPanelDescriptor.IDENTIFIER;
		}
		
		panel.jTextArea.setText(AdvancedPanelDescriptor.getInstance().getInstallInfo());
		
		
		
    }
	
	
	
	private void setNextButton(){
		if (panel.isDefaultSelected()){
            getWizard().setNextFinishButtonEnabled(true);
			nextPanel=FINISH;
			getWizard().getModel().setNextFinishButtonText(Wizard.FINISH_TEXT);
			getWizard().getModel().setNextFinishButtonIcon(Wizard.FINISH_ICON);
		}else{
            getWizard().getModel().setNextFinishButtonText(Wizard.NEXT_TEXT);
            getWizard().getModel().setNextFinishButtonIcon(Wizard.NEXT_ICON);
			nextPanel=AdvancedPanelDescriptor.IDENTIFIER;
		
		}
	}

    

}
