/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import GUI.Form.Profession_Form;
import GUI.Form.Warehouse_Form;
import java.awt.event.ActionListener;

/**
 *
 * @author Admin
 */
public class Profession_Controller {
    private Profession_Form pF_Form;

    public Profession_Controller(Profession_Form pF_Form) {
        this.pF_Form = pF_Form;
        listenActioner();
    }
    
    private void listenActioner() {
        //e đang sửa đoạn này 
//        ActionListener buttonListener = (e) -> {
//            String command = e.getActionCommand();
//            switch (command){
//                case "switchToWarehouse": pF_Form.switchToPanel("GUEST_TABLE"); break;
//            }
//            
//        };
//        pF_Form.getBtnNhapKho().addActionListener(buttonListener);
        
    }
    
}
