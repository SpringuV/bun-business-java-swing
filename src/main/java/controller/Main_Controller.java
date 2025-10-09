package controller;

import GUI.Form.Main_Form;
import app.AppContext;

import java.awt.event.ActionListener;

public class Main_Controller {
	private final Main_Form mainForm;

    public Main_Controller(Main_Form mainForm) {
        this.mainForm = mainForm;
        listenActioner();
    }

    private void listenActioner() {
        ActionListener buttonListener = (e) -> {
            String command = e.getActionCommand();
            switch (command) {
                case "switchToGuestTable" -> mainForm.switchToPanel("GUEST_TABLE");
                case "switchToProfession" -> mainForm.switchToPanel("PROFESSION");
                case "switchToCategory" -> mainForm.switchToPanel("CATEGORY");
                case "switchToAccount" -> mainForm.switchToPanel("ACCOUNT");
            }
        };

        mainForm.getBtnGuestTable().addActionListener(buttonListener);
        mainForm.getBtnProfession().addActionListener(buttonListener);
        mainForm.getBtnCategory().addActionListener(buttonListener);
        mainForm.getBtnAccount().addActionListener(buttonListener);
    }
}
