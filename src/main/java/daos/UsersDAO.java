package daos;

import entities.Users;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.math.BigDecimal;

public class UsersDAO {

    public BigDecimal addUser(Users user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        BigDecimal userId = null;

        try {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            userId = user.getUserId();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        } finally {
            session.close();
        }

        return userId;
    }
}
