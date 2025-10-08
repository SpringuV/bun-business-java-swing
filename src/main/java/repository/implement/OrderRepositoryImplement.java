package repository.implement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.Query;

import com.alibaba.excel.event.Order;

import model.Customer;
import model.KeyOrderItem;
import model.OrderItem;
import model.Orders;
import model.User;
import repository.interfaceRepo.BaseDao;
import repository.interfaceRepo.OrderRepository;
import repository.interfaceRepo.SessionUtils;

public class OrderRepositoryImplement extends BaseDao<Orders> implements OrderRepository{
	private static final Logger logger = LogManager.getLogger(OrderRepositoryImplement.class);

	@Override
	public Orders createOrder(String user_phone, String cus_phone, List<OrderItem> order_item_list_tmp,
			double total_amount) throws Exception {
		// tạo các đối tượng để lưu
		User user_staff = User.builder().phone_number(user_phone).build();
		Customer cus = Customer.builder().phone_number(cus_phone).build();
		// tạo order trước
		Orders order = Orders.builder().customer(cus).user(user_staff).total_amount(total_amount).build();
		// lưu order vào db để có id
		order = session.merge(order);
		// Duyệt từng phần tử trong list để lưu vào db
		List<OrderItem> newListOrder = new ArrayList<OrderItem>();

		for (OrderItem item : order_item_list_tmp) {
			OrderItem orderItem = OrderItem.builder()
					.keyOrderItem(KeyOrderItem.builder().id_order(order.getId_order()).id_food(item.getKeyOrderItem().getId_food()).build())
					.note(item.getNote())
					.quantity(item.getQuantity())
					.discount(item.getDiscount())
					.unit(item.getUnit())
					.price(item.getPrice())
					.order(order)
					.size(item.getSize())
					.build();
			newListOrder.add(orderItem);
		}
		order.setOrder_item_list(newListOrder);
		// Hibernate tự tạo id_order khi flush
		return saveOrUpdate(order);
	}

	@Override
	public Orders updateOrder(int id_order, List<OrderItem> order_item_list_tmp, double total_amount)
			throws Exception {
		Orders order = findById(Orders.class, id_order);
		if (order == null) {
			throw new Exception("Orders with id '" + id_order + "' not found");
		}
		// Xóa hết các OrderItem cũ khỏi order Hibernate sẽ tự DELETE do orphanRemoval = true
		order.getOrder_item_list().clear();

		for (OrderItem item : order_item_list_tmp) {
			item.setOrder(order);
			item.setKeyOrderItem(KeyOrderItem.builder().id_food(item.getKeyOrderItem().getId_food()).id_order(id_order).build());
			order.getOrder_item_list().add(item);
		}
		return saveOrUpdate(order);
	}

	@Override
	public List<Orders> getListOrderByUserPhoneNumber(String phone_number) throws Exception {
		List<Orders> user_order_list;
		try {
			startOperation();
			String hql = "FROM Orders o WHERE o.user.phone_number =:phone_number";
			user_order_list = session.createQuery(hql, Orders.class).setParameter("phone_number", phone_number).list();
			transaction.commit();
		} catch (Exception e) {
			SessionUtils.rollback(transaction);
			logger.error("Error getListOrderByUserPhoneNumber: {}", e.getMessage(), e);
			throw e;
		} finally {
			closeSession();
		}
		return user_order_list;
	}

	@Override
	public List<Orders> getListOrderByStatus(Orders.OrderStatus status) throws Exception {
		List<Orders> order_list = new ArrayList<Orders>();
		try {
			startOperation();
			String hql = "FROM Orders WHERE order_status=:status";
			order_list = session.createQuery(hql, Orders.class).setParameter("status", status).list();
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			SessionUtils.rollback(transaction);
			logger.error("Error getListOrderByStatus: {}", e.getMessage(), e);
			throw e;
		} finally {
			closeSession();
		}

		return order_list;
	}

	@Override
	public List<Orders> getListOrderByStartDateAndEndDate(String start_date, String end_date) throws Exception {
		List<Orders> order_list;
		// chuyển từ string sang localdatetime
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate startDate = LocalDate.parse(start_date, formatter);
		LocalDate endDate = LocalDate.parse(end_date, formatter);
		try {
			startOperation();
			String hql = "SELECT o FROM Orders o WHERE o.created_at BETWEEN :start_date AND :end_date";
			order_list = session.createQuery(hql, Orders.class).setParameter("start_date", startDate)
					.setParameter("end_date", endDate).list();
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			SessionUtils.rollback(transaction);
			logger.error("Error getListOrderByStartDateAndEndDate: {}", e.getMessage(), e);
			throw e;
		} finally {
			closeSession();
		}

		return order_list;
	}

	@Override
	public List<Orders> getListOrderByCustomerPhoneNumber(String phone_number) throws Exception {
		List<Orders> customer_order_list;

		try {
			startOperation();
			String hql = "FROM Orders o WHERE o.customer.phone_number =:phone_number";
			customer_order_list = session.createQuery(hql, Orders.class).setParameter("phone_number", phone_number)
					.list();
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			SessionUtils.rollback(transaction);
			logger.error("Error getListOrderByCustomerPhoneNumber: {}", e.getMessage(), e);
			throw e;
		} finally {
			closeSession();
		}

		return customer_order_list;
	}

	@Override
	public String deleteOrderById(int id_order) throws Exception {
		String result = "delete failed";
		try {
			startOperation();
			String hql = "DELETE FROM Orders WHERE id_order=:id_order";
			int affectedRows = session.createQuery(hql, Integer.class).setParameter("id_order", id_order)
					.executeUpdate();
			if (affectedRows > 0) {
				result = "delete success";
			}
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			SessionUtils.rollback(transaction);
			logger.error("Error deleteOrderById: {}", e.getMessage(), e);
			throw e;
		} finally {
			closeSession();
		}
		return result;
	}

	@Override
	public String deleteOrderByCustomer(String phone_number) throws Exception {
		String result = "delete failed";
		try {
			startOperation();
			String hql = "DELETE FROM Orders o WHERE o.customer.phone_number=:phone_number";
			int affectedRows = session.createQuery(hql, Integer.class).setParameter("phone_number", phone_number)
					.executeUpdate();
			if (affectedRows > 0) {
				result = "delete success";
			}
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
