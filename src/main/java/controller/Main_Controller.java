/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import GUI.Form.Main_Form;
import java.awt.event.ActionListener;

/**
 *
 * @author Admin
 */
public class Main_Controller {
    private Main_Form mainForm;

    public Main_Controller(Main_Form mainForm) {
        this.mainForm = mainForm;
        listenActioner();
    }

    private void listenActioner() {
        ActionListener buttonListener = (e) -> {
            String command = e.getActionCommand();
            switch (command){
                case "switchToGuestTable": mainForm.switchToPanel("GUEST_TABLE"); break;
                case "switchToProfession": mainForm.switchToPanel("PROFESSION"); break;
                case "switchToCategory": mainForm.switchToPanel("CATEGORY"); break;
                case "switchToAccount": mainForm.switchToPanel("ACCOUNT"); break;
            }
            
        };
        mainForm.getBtnGuestTable().addActionListener(buttonListener);
        mainForm.getBtnProfession().addActionListener(buttonListener);
        mainForm.getBtnCategory().addActionListener(buttonListener);
        mainForm.getBtnAccount().addActionListener(buttonListener);
    }
    
}
