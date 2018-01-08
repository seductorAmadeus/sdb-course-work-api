package dao;

import entities.Bcomp;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.math.BigDecimal;

public interface BcompDAO extends GenericDAO<Bcomp, BigDecimal> {
    default void update(Bcomp bcomp) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // TODO: check this method!
            session.saveOrUpdate(bcomp);
            transaction.commit();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }
    }
}
