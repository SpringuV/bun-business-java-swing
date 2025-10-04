package repository;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.Order;

public class OrderRepositoryImplement extends BaseDao<Order> {
	private static final Logger logger = LogManager.getLogger(UserRepositoryImplement.class);

	public List<Order> getListOrderByUserPhoneNumber(String phone_number) throws Exception {
		List<Order> user_order_list;

		try {
			startOperation();
			String hql = "FROM Order o WHERE o.user.phone_number =:phone_number";
			user_order_list = session.createQuery(hql, Order.class).list();
		} catch (Exception e) {
			// TODO: handle exception
			SessionUtils.rollback(transaction);
			logger.error("Error getListOrderByUserPhoneNumber: {}", e.getMessage(), e);
			throw e;
		} finally {
			closeSession();
		}

		return user_order_list;
	}
}
