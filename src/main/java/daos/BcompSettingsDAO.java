package daos;

import entities.BcompSettings;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.math.BigDecimal;

public class BcompSettingsDAO {

    public BigDecimal addBcompSettings(BcompSettings bcompSettings) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        BigDecimal bcompSettingsId = null;

        try {
            transaction = session.beginTransaction();
            session.persist(bcompSettings);
            transaction.commit();
            bcompSettingsId = bcompSettings.getId();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        } finally {
            session.close();
        }

        return bcompSettingsId;
    }

    public BcompSettings getBcompSettingsById(BigDecimal bcompSettingsId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        BcompSettings bcompSettings = null;
        try {
            transaction = session.beginTransaction();
            bcompSettings = (BcompSettings) session.createQuery("from BcompSettings where id = :bcompSettingsId")
                    .setParameter("bcompSettingsId", bcompSettingsId)
                    .setMaxResults(1)
                    .uniqueResult();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        } finally {
            session.close();
        }
        return bcompSettings;
    }
}
