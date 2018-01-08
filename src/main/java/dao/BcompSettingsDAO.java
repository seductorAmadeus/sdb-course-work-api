package dao;

import entities.BcompSettings;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.math.BigDecimal;

public interface BcompSettingsDAO extends GenericDAO<BcompSettings, BigDecimal> {

    default void update(BcompSettings bcompSettings) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // TODO: check this method!
            session.saveOrUpdate(bcompSettings);
            transaction.commit();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }
    }
}
