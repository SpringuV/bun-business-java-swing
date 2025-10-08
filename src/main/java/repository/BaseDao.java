package repository;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public abstract class BaseDao<T> {

    private static final Logger logger = LogManager.getLogger(BaseDao.class);

    protected Session session;
    protected Transaction transaction;

    /**
     * Mở session và bắt đầu transaction
     * DDL – Data Definition Language CREATE - ALTER - DROP - TRUNCATE (Xóa toàn bộ dữ liệu trong bảng nhưng vẫn giữ cấu trúc).
     * DML – Data Manipulation Language INSERT UPDATE DELETE -  quản lý dữ liệu trong bảng
     * DQL – Data Query Language SELECT
     * DCL – Data Control Language GRANT - REVOKE Ngôn ngữ điều khiển dữ liệu (dùng để phân quyền và kiểm soát truy cập).
     * TCL – Transaction Control Language COMMIT ROLLBACK SAVEPOINT quản lý các thao tác trong transaction
     */
    protected void startOperation() {
        session = SessionUtils.getSession(); // Session (tạm coi như kết nối DB)
        transaction = session.beginTransaction(); // bắt đầu 1 phiên giao dịch, insert, update, delete, query…, chưa được ghi chính thức xuống DB
//        transaction.commit(); gửi toàn bộ các thay đổi trong Persistence Context xuống DB
//        Nếu không commit mà đóng session
//        Hibernate sẽ rollback ngầm, các thay đổi không được ghi vào DB
    }

    /**
     * Lưu hoặc cập nhật entity,  persist(), merge(), hoặc save() + update() // Hibernate sẽ đẩy các thay đổi vào trong Persistence Context (tạm hiểu như bộ nhớ tạm ở tầng Hibernate).
     * merge: nếu entity có id -> update, nếu không -> insert
     * persist: insert mới
     */
    public T saveOrUpdate(T entity) throws Exception {
        try {
            startOperation();
            session.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            SessionUtils.rollback(transaction);
            logger.error("Error saveOrUpdate: {}", e.getMessage(), e);
            throw e;
        } finally {
            closeSession();
        }
        
        return entity; // trả về entity managed
    }

    /**
     * Xóa entity
     */
    public void delete(T entity) throws Exception {
        try {
            startOperation();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
        	SessionUtils.rollback(transaction);
            logger.error("Error delete: {}", e.getMessage(), e);
            throw e;
        } finally {
            closeSession();
        }
    }

    /**
     * Tìm entity theo ID
     */
	public T findById(Class<T> clazz, Object id) throws Exception {
        T entity;
        try {
            startOperation();
            entity = session.get(clazz, id);
        } catch (Exception e) {
        	SessionUtils.rollback(transaction);
            logger.error("Error findById: {}", e.getMessage(), e);
            throw e;
        } finally {
            closeSession();
        }
        return entity;
    }

    /**
     * Lấy tất cả entity
     */
	@SuppressWarnings("hiding")
	public <T> List<T> findAll(Class<T> clazz) throws Exception {
	    List<T> list;
	    try {
	        startOperation();
	        Query<T> query = session.createQuery("from " + clazz.getSimpleName(), clazz);
	        list = query.list();
	        transaction.commit();
	    } catch (Exception e) {
	        SessionUtils.rollback(transaction);
	        logger.error("Error findAll: {}", e.getMessage(), e);
	        throw e;
	    } finally {
	        closeSession();
	    }
	    return list;
	}

    /**
     * Thực thi HQL Update/Delete
     */
    public int runQuery(String hql) throws Exception {
        int result;
        try {
            startOperation();
            Query<?> query = session.createQuery(hql);
            result = query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
        	SessionUtils.rollback(transaction);
            logger.error("Error runQuery: {}", e.getMessage(), e);
            throw e;
        } finally {
            closeSession();
        }
        return result;
    }


    /**
     * Đóng session
     */
    protected void closeSession() {
        if (session != null) {
            try {
                session.close();
            } catch (Exception e) {
                logger.error("Could not close session: {}", e.getMessage(), e);
            }
        }
    }
}