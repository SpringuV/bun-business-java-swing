package service;

import java.util.List;

import model.WareHouse;
import repository.interfaceRepo.WareHouseRepository;

public class WarehouseService {
	private final WareHouseRepository repository;

	public WarehouseService(WareHouseRepository repository) {
		this.repository = repository;
	}
	
	public List<WareHouse> getAllWarehouses() throws Exception {
        return repository.getListWareHouse();
    }
}
