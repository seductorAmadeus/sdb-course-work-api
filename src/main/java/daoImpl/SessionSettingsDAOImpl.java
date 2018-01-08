package daoImpl;

import dao.GenericDAO;
import entities.BcompSettings;
import entities.UserSession;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.io.Serializable;
import java.util.List;

/**
 * This class is a data access object that provides CRUD operations to session_settings table from DB.
 *
 * @author Rayla Martin
 * @version 0.1
 * @since 0.1
 */
public class SessionSettingsDAOImpl implements GenericDAO {

//    public void deleteByBcompSettingsId(BigDecimal id) {
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        Transaction transaction = null;
//        try {
//            transaction = session.beginTransaction();
//           javax.persistence.Query query =  session.createQuery("delete from ")
//
//            transaction.commit();
//        } catch (IllegalArgumentException exp) {
//            System.out.println("The specified bcomp settings is not in the database. Check it out correctly and try again");
//        } catch (Exception exp) {
//            if (transaction != null) {
//                transaction.rollback();
//                exp.printStackTrace();
//            }
//        } finally {
//            session.close();
//        }
//    }

    public void assignUserSettings(UserSession userSession, BcompSettings bcompSettings) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        userSession.getBcompSettings().add(bcompSettings);

        try {
            transaction = session.beginTransaction();
            //TODO: check it. How it works?
            session.merge(userSession);
            transaction.commit();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        } finally {
            session.close();
        }

    }

    @Override
    public Serializable create(Object newInstance) {
        return null;
    }

    @Override
    public Object get(Serializable id) {
        return null;
    }

    public void delete(Serializable id) {

    }

    public boolean isExists(Serializable id) {
        return false;
    }

    @Override
    public List getList() {
        return null;
    }
}
