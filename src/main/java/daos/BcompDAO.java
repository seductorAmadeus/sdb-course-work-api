package daos;

import entities.Bcomp;
import entities.UserSession;
import entities.Users;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.math.BigDecimal;

public class BcompDAO {

    public BigDecimal createEmptyBcomp(Bcomp bcomp) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        BigDecimal bcompId = null;

        try {
            transaction = session.beginTransaction();
            session.persist(bcomp);
            transaction.commit();
            bcompId = bcomp.getId();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        } finally {
            session.close();
        }

        return bcompId;
    }
}
