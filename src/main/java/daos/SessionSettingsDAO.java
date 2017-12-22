package daos;

import entities.BcompSettings;
import entities.UserSession;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

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
}
