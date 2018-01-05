package daos;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, PK extends Serializable> {
    PK create(T newInstance);

    T read(PK id);

    default void update(T transientObject) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(transientObject);
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

    default void delete(Class<T> tClass, PK id) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            T classInstance = session.get(tClass, id);
            session.delete(classInstance);
            transaction.commit();
        } catch (IllegalArgumentException exp) {
            System.out.println("The specified " + tClass.getName() + " is not in the database. Check it out correctly and try again");
        } catch (Exception exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        } finally {
            session.close();
        }

    }

    default boolean isExists(Class<T> tClass, PK id) {
        boolean condition = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            if (session.get(tClass, id) != null) {
                condition = true;
            }
        } catch (HibernateException exp) {

        } finally {
            session.close();
        }

        return condition;
    }

    List<T> getList();
}
