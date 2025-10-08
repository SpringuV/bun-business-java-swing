package repository.implement;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.Customer;
import model.User;
import repository.interfaceRepo.BaseDao;
import repository.interfaceRepo.CustomerRepository;
import repository.interfaceRepo.SessionUtils;

public class CustomerRepositoryImplement extends BaseDao<Customer> implements CustomerRepository {
	private static final Logger logger = LogManager.getLogger(CustomerRepositoryImplement.class);

	@Override
	public boolean createCustomer(String phone_number, String name, boolean sex, String description) throws Exception {
		Customer customer = Customer.builder().phone_number(phone_number).name_cus(name).sex_cus(sex)
				.description(description).build();
		return saveOrUpdate(customer) != null;
	}
	
	@Override
	public List<User> getListUser() throws Exception{
		return findAll(User.class);
	}

	@Override
	public String deleteCustomerByPhoneNumber(String phone) throws Exception {
		String result = "delete failed";
		try {
			startOperation();
			String hql = "DELETE FROM Customer c WHERE c.phone_number=:phone_number";
			int rowEffected = session.createQuery(hql, Integer.class).setParameter("phone_number", phone)
					.executeUpdate();
			if (rowEffected > 0)
				result = "delete success";
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			SessionUtils.rollback(transaction);
			logger.error("Error deleteOrderByCustomer: {}", e.getMessage(), e);
			throw e;
		} finally {
			closeSession();
		}
		return result;
	}
	
	
}
