package repository.interfaceRepo;

import java.util.List;

import model.GuestTable;
import model.GuestTable.TypeTable;

public interface GuestTableRepository {
	boolean createGuestTable(TypeTable type_table, boolean is_available) throws Exception;
	
	List<GuestTable> getListTable() throws Exception;
	
	String deleteTableById(int id) throws Exception;
	
	
}
