package repository.interfaceRepo;

import java.util.List;

import model.WareHouse;

public interface WareHouseRepository {
	List<WareHouse> getListWareHouse() throws Exception;
	
	List<WareHouse> getListWareHouseByName(String name) throws Exception;
	
}
