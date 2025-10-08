package repository.interfaceRepo;

import java.util.List;
import java.util.Optional;

import model.InventoryTransaction;
import model.InventoryTransaction.TypeInventoryTransaction;

public interface InventoryRepository {
	boolean createInventoryTrans(String user_phone, int quantity, String name_ingredients, String note,
			TypeInventoryTransaction type) throws Exception;
	
	String deleteInventoryById(Long id) throws Exception;
	
	List<InventoryTransaction> getListInventoryByUserId(String id_user) throws Exception;
	
	InventoryTransaction findByInventoryId(Long id) throws Exception;
	
	InventoryTransaction updateInventory(Long id, Optional<Integer> quantity,
			Optional<TypeInventoryTransaction> type, Optional<String> note) throws Exception;
	
	List<InventoryTransaction> getListInventoryByMonthInYear(int month, int year) throws Exception;
	
	String deleteInventoryByMonthInYear(int month, int year) throws Exception;
	
	
}
