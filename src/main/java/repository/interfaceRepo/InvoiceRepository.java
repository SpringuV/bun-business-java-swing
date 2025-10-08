package repository.interfaceRepo;

import java.util.List;

import model.Customer;
import model.Invoice;
import model.Orders;
import model.User;
import model.Invoice.PaymentMethod;
import model.Invoice.PaymentStatus;

public interface InvoiceRepository {
	boolean createInvoice(Orders order, double discount, PaymentMethod payment_method, PaymentStatus payment_status,
			String note, User user, Customer customer) throws Exception;

	Invoice findInvoiceById(String id_invoice) throws Exception;
	
	List<Invoice> getListInvoiceByCustomerPhoneNumber(String phone_number) throws Exception;
	
	List<Invoice> getListInvoiceByStartDateAndEndDate(String start_date, String end_date) throws Exception;
	
	List<Invoice> getListInvoiceByUserPhoneNumberAndDateOrderASC(String phone_number, String date_order) throws Exception;
	
	List<Invoice> getListInvoicesByCustomerPhoneOnDate(String phone_number, String date_order) throws Exception;
	
	List<Invoice> getListInvoiceByMonthInYear(int month, int year) throws Exception;
	
	String deleteInvoiceByMonthInYear(int month, int year) throws Exception;
	String deleteInvoiceByCustomerPhoneNumber(String phone_number) throws Exception;
}
