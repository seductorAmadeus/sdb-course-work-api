package dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, PK extends Serializable> {
    PK create(T newInstance);

    T get(PK id);

    default void delete(Class<T> tClass, PK id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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
        }
    }

    default boolean isExists(Class<T> tClass, PK id) {
        boolean condition = false;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            if (session.get(tClass, id) != null) {
                condition = true;
            }
        } catch (HibernateException exp) {

        }

        return condition;
    }

    List<T> getList();
}
