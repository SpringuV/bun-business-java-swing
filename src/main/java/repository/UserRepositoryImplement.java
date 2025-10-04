package repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.Query;

import model.Order;
import model.User;

// viết các hàm truy vấn database
public class UserRepositoryImplement extends BaseDao<User> {
	private static final Logger logger = LogManager.getLogger(UserRepositoryImplement.class);

	public User createUser(String phoneNumber, String pass, String role) throws Exception {
		User user = User.builder().phone_number(phoneNumber).pass(pass).role(role).build();

		return saveOrUpdate(user);
	}

	public User findByPhoneNumber(String phone_number) {
		User user = null;
		try {
			// bắt đầu session
			startOperation();
			// câu truy vấn kiểu hibernate
			String hql = "FROM User WHERE phone_number = :phone_number";
			Query<User> query = session.createQuery(hql, User.class);
			// set tham số
			query.setParameter("phoneNumber", phone_number);
			user = query.uniqueResult(); // lấy 1 kết quả duy nhất
			// hoàn thành truy vấn
			transaction.commit();
		} catch (Exception e) {
			SessionUtils.rollback(transaction);
			logger.error("Error findByPhoneNumber: {}", e.getMessage(), e);
			throw e;
		} finally {
			closeSession();
		}
		return user;
	}

	public List<User> getListUser() throws Exception {
		return findAll(User.class);
	}
	
	public User login(String phone_number, String password) throws Exception {
		User user = null;
		try {
			startOperation();
			String hql = "FROM User WHERE phone_number =:phone_number AND pass= :password";
			Query<User> query = session.createQuery(hql, User.class);
			user = query.uniqueResult();
			// lưu vào db
			transaction.commit();
		}catch (Exception e) {
			SessionUtils.rollback(transaction);
			logger.error("Error login: {}", e.getMessage(), e);
			throw e;
		} finally {
			closeSession();
		}
		return user;
	}
	
	
}
