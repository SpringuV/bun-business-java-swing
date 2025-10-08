package repository.interfaceRepo;

import java.util.List;
import java.util.Optional;

import model.User;
import model.User.UserStatus;

//Dễ test, mock (sau này làm unit test hoặc đổi qua database khác).
//Dễ mở rộng (ví dụ thêm UserRepositoryJdbc, UserRepositoryJpa, v.v.)
//Giúp tuân theo nguyên tắc Dependency Inversion (SOLID).
public interface UserRepository {
	boolean createUser(String phoneNumber, String pass, String role) throws Exception;

	User findByPhoneNumber(String phone_number) throws Exception;

	List<User> getListUser() throws Exception;

	User findById(String id_user) throws Exception;

	User updateUser(Optional<UserStatus> status, Optional<String> full_name, Optional<String> pass,
			Optional<String> phone_number, String id_user) throws Exception;

	User login(String phone_number, String password) throws Exception;
}
