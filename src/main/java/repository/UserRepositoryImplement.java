package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.Query;

import model.Orders;
import model.User;
import model.User.UserStatus;

// viết các hàm truy vấn database
public class UserRepositoryImplement extends BaseDao<User> {
	private static final Logger logger = LogManager.getLogger(UserRepositoryImplement.class);

	public boolean createUser(String phoneNumber, String pass, String role) throws Exception {
		User user = User.builder().phone_number(phoneNumber).pass(pass).role(role).build();
		return saveOrUpdate(user) != null;
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
	
	public User findById(String id_user) throws Exception {
		return findById(User.class, id_user);
	}
	
	public User updateUser(Optional<UserStatus> status, Optional<String> full_name, Optional<String> pass, Optional<String> phone_number, String id_user) throws Exception {
		User user = findById(id_user);
		if(user == null) {
			throw new Exception("User with id '" + id_user + "' not found");
		}
		status.ifPresent(user::setStatus);
		full_name.ifPresent(user::setFull_name);
		pass.ifPresent(user::setPass);
		phone_number.ifPresent(user::setPhone_number);
		return saveOrUpdate(user);
	}
	
	public User login(String phone_number, String password) throws Exception {
		User user = null;
		try {
			startOperation();
			String hql = "FROM User WHERE phone_number =:phone_number AND pass= :password";
			Query<User> query = session.createQuery(hql, User.class)
					.setParameter("phone_number", phone_number)
					.setParameter("password", password);
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
