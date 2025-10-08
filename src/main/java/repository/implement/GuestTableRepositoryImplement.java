package repository.implement;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.GuestTable;
import model.GuestTable.TypeTable;
import repository.interfaceRepo.BaseDao;
import repository.interfaceRepo.GuestTableRepository;
import repository.interfaceRepo.SessionUtils;

public class GuestTableRepositoryImplement extends BaseDao<GuestTable> implements GuestTableRepository{
	private static final Logger logger = LogManager.getLogger(GuestTableRepositoryImplement.class);
	
	@Override
	public boolean createGuestTable(TypeTable type_table, boolean is_available) throws Exception {
		GuestTable guestTable = GuestTable.builder().is_available(is_available).type(type_table).build();
		return saveOrUpdate(guestTable) != null;
	}
	
	@Override
	public List<GuestTable> getListTable() throws Exception {
		List<GuestTable> guest_table_list;
		try {
			startOperation();
			String hql = "FROM GuestTable";
			guest_table_list = session.createQuery(hql, GuestTable.class).list();
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			SessionUtils.rollback(transaction);
			logger.error("Error getListTalbeAvailable: {}", e.getMessage(), e);
			throw e;
		} finally {
			closeSession();
		}
		return guest_table_list;
	}
	
	@Override
	public String deleteTableById(int id) throws Exception {
		String result = "delete failed";
		try {
			startOperation();
			String hql = "DELETE FROM GuestTable WHERE id_table=:id_table";
			int rowEffected = session.createQuery(hql, Integer.class).setParameter("id_table", id).executeUpdate();
			if(rowEffected > 0) result = "delete success";
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			SessionUtils.rollback(transaction);
			logger.error("Error deleteTableById: {}", e.getMessage(), e);
			throw e;
		} finally {
			closeSession();
		}
		return result;
	}
	
}
