package app;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.User;
import repository.SessionUtils;

public class Main {
	public static void main(String[] args) {
		Session session = null;
		Transaction tx = null;
		try {
			// mở session
			session = SessionUtils.getSession();
			tx = session.beginTransaction();

			// test insert 1 user
			User user = User.builder().phone_number("0123456789").pass("123456").role("admin").build();

			session.persist(user);

			tx.commit();
			System.out.println("✅ Kết nối thành công và đã lưu User!");
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			System.out.println("❌ Lỗi kết nối hoặc query");
		} finally {
			if (session != null)
				session.close();
		}
	}
}