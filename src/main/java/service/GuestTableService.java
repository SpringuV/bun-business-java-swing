package service;

import java.util.List;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import model.GuestTable;
import model.GuestTable.TypeTable;
import repository.interfaceRepo.GuestTableRepository;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class GuestTableService {
	final GuestTableRepository repository;

    public GuestTableService(GuestTableRepository repository) {
        this.repository = repository;
    }

    public List<GuestTable> getAllTables() {
        try {
            return repository.getListTable();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public boolean addTable(TypeTable type, boolean available) {
        try {
            return repository.createGuestTable(type, available);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
