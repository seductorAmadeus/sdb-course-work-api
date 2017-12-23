package daos;

import entities.BcompSettings;
import entities.UserSession;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

/**
 * This class is a data access object that provides CRUD operations to session_settings table from DB.
 *
 * @author Rayla Martin
 * @version 0.1
 * @since 0.1
 */

public class SessionSettingsDAO {

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

    public void dropAllSessionSettingsRecords() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("truncate table session_settings").executeUpdate();
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
}
