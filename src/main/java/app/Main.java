package app;

import javax.swing.SwingUtilities;

import org.hibernate.Session;
import org.hibernate.Transaction;

import GUI.Form.LoginAndRegister_Form;
import controller.LoginAndRegister_Controller;
import model.User;
import repository.interfaceRepo.SessionUtils;

public class Main {
	public static void main(String[] args) {
		// khởi tạo Hibernate
		initAdminAccount();

		// chạy giao diện Swing (phải chạy trong luồng UI)
		SwingUtilities.invokeLater(() -> {
			AppRouter.goToLogin();
		});
	}

	private static void initAdminAccount() {
		Session session = null;
		Transaction tx = null;
		try {
			session = SessionUtils.getSession();
			tx = session.beginTransaction();

			String phone = "0123456789";
			User existingUser = session.createQuery("FROM User u WHERE u.phone_number = :phone", User.class)
					.setParameter("phone", phone).uniqueResult();

			if (existingUser == null) {
				User user = User.builder().phone_number(phone).pass("123456").role("admin").build();
				session.persist(user);
				System.out.println("✅ Đã tạo user admin mặc định!");
			} else {
				System.out.println("ℹ️ User admin đã tồn tại, bỏ qua insert.");
			}

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			System.out.println("❌ Lỗi khi tạo user admin");
		} finally {
			if (session != null)
				session.close();
		}
	}
}