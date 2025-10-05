package repository;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import model.Ingredient;
import model.InventoryTransaction;
import model.User;
import model.InventoryTransaction.TypeInventoryTransaction;

public class InventoryRepositoryImplement extends BaseDao<InventoryTransaction> {
	private static final Logger logger = LogManager.getLogger(InventoryRepositoryImplement.class);

	public boolean createInventoryTrans(String user_phone, int quantity, String name_ingredients, String note,
			TypeInventoryTransaction type) throws Exception {
		InventoryTransaction inventoryTransaction = InventoryTransaction.builder().quantity(quantity).note(note)
				.user(User.builder().phone_number(user_phone).build()).type(type)
				.ingredient(Ingredient.builder().name_ingredients(name_ingredients).build()).build();
		return saveOrUpdate(inventoryTransaction) != null;
	}

	public String deleteInventoryById(Long id) throws Exception {
		String result = "delete failed";
		try {
			startOperation();
			String hql = "DELETE FROM InventoryTransaction i WHERE i.id=:id";
			int rowEffected = session.createQuery(hql, Integer.class).setParameter("id", id).executeUpdate();
			if (rowEffected > 0)
				result = "delete success";
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			SessionUtils.rollback(transaction);
			logger.error("Error deleteInventoryById: {}", e.getMessage(), e);
			throw e;
		} finally {
			closeSession();
		}
		return result;
	}

	public List<InventoryTransaction> getListInventoryByUserId(String id_user) throws Exception {
		List<InventoryTransaction> inventory_list;
		try {
			startOperation();
			String hql = "SELECT i FROM InventoryTransaction i WHERE i.user.id_user=:id_user";
			inventory_list = session.createQuery(hql, InventoryTransaction.class).setParameter("id_user", id_user)
					.list();
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			SessionUtils.rollback(transaction);
			logger.error("Error getListInventoryByUserId: {}", e.getMessage(), e);
			throw e;
		} finally {
			closeSession();
		}
		return inventory_list;
	}

	public InventoryTransaction findByInventoryId(Long id) throws Exception {
		return findById(InventoryTransaction.class, id);
	}

	public InventoryTransaction updateInventory(Long id, Optional<Integer> quantity,
			Optional<TypeInventoryTransaction> type, Optional<String> note) throws Exception {
		InventoryTransaction inventoryTransaction = findByInventoryId(id);
		if (inventoryTransaction == null) {
			throw new Exception("inventoryTransaction with id '" + id + "' not found");
		}
		// update
		quantity.ifPresent(inventoryTransaction::setQuantity);
		type.ifPresent(inventoryTransaction::setType);
		note.ifPresent(inventoryTransaction::setNote);

		return saveOrUpdate(inventoryTransaction);
	}

	public List<InventoryTransaction> getListInventoryByMonthInYear(int month, int year) throws Exception {
		List<InventoryTransaction> inventory_list;
		try {
			startOperation();
			String hql = "SELECT i FROM Inventory i WHERE function('month', created_at) =:month AND function('year', created_at) =:year";
			inventory_list = session.createQuery(hql, InventoryTransaction.class).setParameter("month", month)
					.setParameter("year", year).list();
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			SessionUtils.rollback(transaction);
			logger.error("Error getListInventoryByMonthInYear: {}", e.getMessage(), e);
			throw e;
		} finally {
			closeSession();
		}
		return inventory_list;
	}
	
	public String deleteInventoryByMonthInYear(int month, int year) throws Exception {
		String result = "delete failed";
		try {
			startOperation();
			String hql = "DELETE FROM InventoryTransaction i WHERE function('month', i.created_at) =:month AND function('year', created_at)=:year";
			int rowEffected = session.createQuery(hql, Integer.class).setParameter("month", month).setParameter("year", year).executeUpdate();
			if (rowEffected > 0)
				result = "delete success";
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			SessionUtils.rollback(transaction);
			logger.error("Error deleteInventoryById: {}", e.getMessage(), e);
			throw e;
		} finally {
			closeSession();
		}
		return result;
	}
}
