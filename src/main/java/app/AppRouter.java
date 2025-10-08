package app;

import GUI.Form.Auth.LoginAndRegister_Form;
import GUI.Form.GuestTable.GuestTable_Form;
import controller.GuestTable_Controller;
import controller.LoginAndRegister_Controller;
import repository.interfaceRepo.GuestTableRepository;
import service.GuestTableService;

public class AppRouter {

	// Hiển thị Guest Table
	public static void goToGuestTable() {
		// 1. Tạo repository và service
		GuestTableRepository repo = AppContext.guestTableRepository;
		GuestTableService service = new GuestTableService(repo);

		// 2. Tạo form (view)
		GuestTable_Form form = new GuestTable_Form();

		// 3. Tạo controller và gắn vào form
		GuestTable_Controller controller = new GuestTable_Controller(service, form);
		// gán controller vào form để các listener trong form có thể dùng
		form.setController(controller); // thêm hàm này trong form nếu chưa có

		// 4. Load dữ liệu bàn và hiển thị
		controller.loadTables();
		form.setVisible(true);
	}

	// hiển thị login
	public static void goToLogin() {
		LoginAndRegister_Form formLogin = new LoginAndRegister_Form();
		new LoginAndRegister_Controller(formLogin, AppContext.userRepository);
		formLogin.setVisible(true);
	}

}
