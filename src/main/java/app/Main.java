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

            // kiểm tra xem user admin đã tồn tại chưa
            String phone = "0123456789";
            User existingUser = session.createQuery(
                    "FROM User u WHERE u.phone_number = :phone", User.class)
                    .setParameter("phone", phone)
                    .uniqueResult();

            if (existingUser == null) {
                // chưa có thì tạo mới
                User user = User.builder()
                        .phone_number(phone)
                        .pass("123456")
                        .role("admin")
                        .build();

                session.persist(user);
                System.out.println("✅ Đã tạo user admin mặc định!");
            } else {
                System.out.println("ℹ️ User admin đã tồn tại, bỏ qua insert.");
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            System.out.println("❌ Lỗi kết nối hoặc query");
        } finally {
            if (session != null) session.close();
        }
    }
}