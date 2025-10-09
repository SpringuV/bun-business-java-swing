package app;

import javax.swing.JOptionPane;

import GUI.Form.Main_Form;
import GUI.Form.Auth.LoginAndRegister_Form;
import GUI.Form.GuestTable.GuestTable_Panel;
import GUI.Form.Profession.Profession_Panel;
import GUI.Form.Profession.OutputInput.InputInventoryTransaction_Panel;
import GUI.Form.Profession.OutputInput.OutputInventoryTransaction_Panel;
import controller.GuestTable_Controller;
import controller.LoginAndRegister_Controller;
import controller.Main_Controller;
import controller.ProfessionController;
import repository.interfaceRepo.GuestTableRepository;
import service.GuestTableService;
import service.WarehouseService;

public class AppRouter {

	// Hiển thị Guest Table
	public static GuestTable_Panel goToGuestTablePanel() {
        GuestTableRepository repo = AppContext.guestTableRepository;
        GuestTableService service = new GuestTableService(repo);
        GuestTable_Panel form = new GuestTable_Panel();
        GuestTable_Controller controller = new GuestTable_Controller(service, form);
        form.setController(controller);
        controller.loadTables();
        return form;
    }

	public static Profession_Panel goToProfessionPanel() {
	    Profession_Panel panelView = new Profession_Panel();
	    WarehouseService service = new WarehouseService(AppContext.wareHouseRepository);
	    ProfessionController controller = new ProfessionController(service, panelView);
	    try {
	        controller.init();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Không thể tải danh sách kho: " + ex.getMessage());
	    }
	    return panelView;
	}
	
	// hiển thị login
	public static void goToLogin() {
		LoginAndRegister_Form formLogin = new LoginAndRegister_Form();
		new LoginAndRegister_Controller(formLogin, AppContext.userRepository);
		formLogin.setVisible(true);
	}
	
	public static OutputInventoryTransaction_Panel goToOutputInventoryTransactionPanel() {
		return new OutputInventoryTransaction_Panel();
	}
	
	public static InputInventoryTransaction_Panel goToInputInventoryTransactionPanel() {
		return new InputInventoryTransaction_Panel();
	}

	public static void goToMainForm() {
		Main_Form form = new Main_Form();
		new Main_Controller(form);
		form.setVisible(true);
	}
}
