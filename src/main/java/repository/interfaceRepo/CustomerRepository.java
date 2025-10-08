package repository.interfaceRepo;

import java.util.List;

import model.User;

public interface CustomerRepository {
	boolean createCustomer(String phone_number, String name, boolean sex, String description) throws Exception;

	List<User> getListUser() throws Exception;

	String deleteCustomerByPhoneNumber(String phone) throws Exception;

}
