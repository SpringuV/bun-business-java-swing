package repository.interfaceRepo;

import java.util.List;

import model.OrderItem;
import model.Orders;

public interface OrderRepository {
	Orders createOrder(String user_phone, String cus_phone, List<OrderItem> order_item_list_tmp,
			double total_amount) throws Exception;
	
	Orders updateOrder(int id_order, List<OrderItem> order_item_list_tmp, double total_amount) throws Exception;
	
	List<Orders> getListOrderByUserPhoneNumber(String phone_number) throws Exception;
	
	List<Orders> getListOrderByStatus(Orders.OrderStatus status) throws Exception;
	
	List<Orders> getListOrderByStartDateAndEndDate(String start_date, String end_date) throws Exception;
	
	List<Orders> getListOrderByCustomerPhoneNumber(String phone_number) throws Exception;
	
	String deleteOrderById(int id_order) throws Exception;
	
	String deleteOrderByCustomer(String phone_number) throws Exception;
	
	
}
