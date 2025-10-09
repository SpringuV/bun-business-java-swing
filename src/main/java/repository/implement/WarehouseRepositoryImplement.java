package repository.implement;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.WareHouse;
import repository.interfaceRepo.BaseDao;
import repository.interfaceRepo.SessionUtils;
import repository.interfaceRepo.WareHouseRepository;

public class WarehouseRepositoryImplement extends BaseDao<WareHouse> implements WareHouseRepository{
	private static final Logger logger = LogManager.getLogger(WarehouseRepositoryImplement.class);
	
	@Override
	public List<WareHouse> getListWareHouse() throws Exception {
		return findAll(WareHouse.class);
	}

	@Override
	public List<WareHouse> getListWareHouseByName(String name) throws Exception {
		List<WareHouse> wareHouse_list;
		try {
			startOperation();
			String hql = "FROM WareHouse";
			wareHouse_list = session.createQuery(hql, WareHouse.class).list();
			transaction.commit();
		} catch (Exception e) {
			SessionUtils.rollback(transaction);
			logger.error("Error findByPhoneNumber: {}", e.getMessage(), e);
			throw e;
		} finally {
			closeSession();
		}
		return wareHouse_list;
	}

}
