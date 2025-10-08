package repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import model.Customer;
import model.Invoice;
import model.Orders;
import model.User;
import model.Invoice.PaymentMethod;
import model.Invoice.PaymentStatus;

public class InvoiceRepositoryImplement extends BaseDao<Invoice> {
	private static final Logger logger = LogManager.getLogger(InvoiceRepositoryImplement.class);

	public boolean createInvoice(Orders order, double discount, PaymentMethod payment_method, PaymentStatus payment_status, String note, User user, Customer customer) throws Exception {
		Invoice invoice = Invoice.builder()
				.user(user)
				.customer(customer)
				.orders(order)
				.discount(discount)
				.payment_method(payment_method)
				.payment_status(payment_status)
				.note(note)
				.build();
		return saveOrUpdate(invoice) != null;
	}
	
	public Invoice findInvoiceById(String id_invoice) throws Exception {
		Invoice invoice = Invoice.builder().build();
		try {
			startOperation();
			String hql = "FROM Invoice WHERE id_invoice=:id_invoice";
			invoice = session.createQuery(hql, Invoice.class).setParameter("id_invoice", id_invoice).uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			SessionUtils.rollback(transaction);
			logger.error("Error getListInvoiceByCustomerPhoneNumber: {}", e.getMessage(), e);
			throw e;
		} finally {
			closeSession();
		}
		return invoice;
	}
	
	public List<Invoice> getListInvoiceByCustomerPhoneNumber(String phone_number) throws Exception {
		List<Invoice> invoice_list;
		try {
			startOperation();
			String hql = "SELECT i FROM Invoice i WHERE i.customer.phone_number = :phone_number";
			invoice_list = session.createQuery(hql, Invoice.class).setParameter("phone_number", phone_number).list();
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			SessionUtils.rollback(transaction);
			logger.error("Error getListInvoiceByCustomerPhoneNumber: {}", e.getMessage(), e);
			throw e;
		} finally {
			closeSession();
		}
		return invoice_list;
	}

	public List<Invoice> getListInvoiceByStartDateAndEndDate(String start_date, String end_date) throws Exception {
		List<Invoice> invoice_list;
		try {
			// chuyển từ string sang localdatetime
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate startDate = LocalDate.parse(start_date, formatter);
			LocalDate endDate = LocalDate.parse(end_date, formatter);
			// start session and transaction
			startOperation();

			String hql = "FROM Invoice WHERE created_at BETWEEN :start_date AND :end_date";
			invoice_list = session.createQuery(hql, Invoice.class).setParameter("start_date", startDate)
					.setParameter("end_date", endDate).list();
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			SessionUtils.rollback(transaction);
			logger.error("Error getListInvoiceByStartDateAndEndDate: {}", e.getMessage(), e);
			throw e;
		} finally {
			closeSession();
		}
		return invoice_list;
	}

	public List<Invoice> getListInvoiceByUserPhoneNumberAndDateOrderASC(String phone_number, String date_order)
			throws Exception {
		List<Invoice> invoice_list;
		try {
			// chuyển từ string sang localdatetime
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate dateOrder = LocalDate.parse(date_order, formatter);
			LocalDateTime startOfDay = dateOrder.atStartOfDay();
            LocalDateTime endOfDay = dateOrder.plusDays(1).atStartOfDay();
			// start session and transaction
			startOperation();
			String hql = "SELECT i FROM Invoice i "
                    + "WHERE i.user.phone_number = :phone_number "
                    + "AND i.created_at >= :startOfDay AND i.created_at < :endOfDay "
                    + "ORDER BY i.created_at ASC";
			invoice_list = session.createQuery(hql, Invoice.class).setParameter("phone_number", phone_number)
					.setParameter("date_order", dateOrder).setParameter("startOfDay", startOfDay)
                    .setParameter("endOfDay", endOfDay).list();
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			SessionUtils.rollback(transaction);
			logger.error("Error getListInvoiceByUserPhoneNumberAndDateOrder: {}", e.getMessage(), e);
			throw e;
		} finally {
			closeSession();
		}
		return invoice_list;
	}

	public List<Invoice> getListInvoicesByCustomerPhoneOnDate(String phone_number, String date_order) throws Exception {
		List<Invoice> invoice_list;
		try {
			// chuyển từ string sang localdatetime
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate dateOrder = LocalDate.parse(date_order, formatter);
			LocalDateTime startOfDay = dateOrder.atStartOfDay();
			LocalDateTime endOfDay = dateOrder.plusDays(1).atStartOfDay();
			// start session and transaction
			startOperation();
			String hql = "SELECT i FROM Invoice i WHERE i.customer.phone_number=:phone_number"
					+ " AND i.created_at >= :startOfDay AND i.created_at < :endOfDay ORDER BY i.created_at ASC";
			invoice_list = session.createQuery(hql, Invoice.class).setParameter("phone_number", phone_number)
					.setParameter("date_order", dateOrder).setParameter("startOfDay", startOfDay)
					.setParameter("endOfDay", endOfDay).list();
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			SessionUtils.rollback(transaction);
			logger.error("Error getInvoicesByCustomerPhoneOnDate: {}", e.getMessage(), e);
			throw e;
		} finally {
			closeSession();
		}
		return invoice_list;
	}

	public List<Invoice> getListInvoiceByMonthInYear(int month, int year) {
		List<Invoice> invoice_list;
		try {
			LocalDate startDate = LocalDate.of(year, month, 1);
            LocalDateTime startOfMonth = startDate.atStartOfDay();
            LocalDateTime startOfNextMonth = startDate.plusMonths(1).atStartOfDay();
			startOperation();
			String hql = "SELECT i FROM Invoice i "
                    + "WHERE i.created_at BETWEEN :startOfMonth AND :startOfNextMonth "
                    + "ORDER BY i.created_at ASC";
			invoice_list = session.createQuery(hql, Invoice.class).setParameter("startOfMonth", startOfMonth)
                    .setParameter("startOfNextMonth", startOfNextMonth).list();
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			SessionUtils.rollback(transaction);
			logger.error("Error getListInvoiceByMonthInYear: {}", e.getMessage(), e);
			throw e;
		} finally {
			closeSession();
		}
		return invoice_list;
	}

	public String deleteInvoiceByMonthInYear(int month, int year) throws Exception {
		String result = "delete Invoice " + month + " failed";
		try {
			LocalDate startDate = LocalDate.of(year, month, 1);
            LocalDateTime startOfMonth = startDate.atStartOfDay();
            LocalDateTime startOfNextMonth = startDate.plusMonths(1).atStartOfDay();
			startOperation();
			String hql = "DELETE FROM Invoice i "
                    + "WHERE i.created_at BETWEEN :startOfMonth AND :startOfNextMonth";
			int rowEffected = session.createQuery(hql, Integer.class).setParameter("startOfMonth", startOfMonth)
                    .setParameter("startOfNextMonth", startOfNextMonth).executeUpdate();
			if (rowEffected > 0)
				result = "delete Invoice " + month + " success";
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			SessionUtils.rollback(transaction);
			logger.error("Error deleteInvoiceByMonthInYear: {}", e.getMessage(), e);
			throw e;
		} finally {
			closeSession();
		}
		return result;
	}

	public String deleteInvoiceByCustomerPhoneNumber(String phone_number) throws Exception {
		String result = "delete failed";
		try {
			startOperation();
			String hql = "DELETE FROM Invoice WHERE customer.phone_number=:phone_number";
			int rowEffected = session.createQuery(hql, Integer.class).setParameter("phone_number", phone_number)
					.executeUpdate();
			if (rowEffected > 0)
				result = "delete success";
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			SessionUtils.rollback(transaction);
			logger.error("Error deleteInvoiceByCustomerPhoneNumber: {}", e.getMessage(), e);
			throw e;
		} finally {
			closeSession();
		}
		return result;
	}

	
}
