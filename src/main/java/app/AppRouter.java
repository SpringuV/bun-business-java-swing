package app;

import GUI.Form.LoginAndRegister_Form;
import GUI.Form.GuestTable.GuestTable_Form;
import controller.GuestTable_Controller;
import controller.LoginAndRegister_Controller;

public class AppRouter {
	
	// hiển thị guest table
	public static void goToGuestTable() {
		GuestTable_Form form = GuestTable_Controller.createFormWithController(AppContext.guestTableRepository);
	    form.setVisible(true);
    }
	
	
	// hiển thị login
	public static void goToLogin() {
		LoginAndRegister_Form formLogin = new LoginAndRegister_Form();
		new LoginAndRegister_Controller(formLogin, AppContext.userRepository);
		formLogin.setVisible(true);
	}
	
}
