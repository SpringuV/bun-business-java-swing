package repository.interfaceRepo;

import java.util.List;

import model.GuestTable;
import model.GuestTable.TypeTable;

public interface GuestTableRepository {
	boolean createGuestTable(TypeTable type_table) throws Exception;
	
	List<GuestTable> getListTalbeAvailable() throws Exception;
	
	String deleteTableById(int id) throws Exception;
	
	
}
