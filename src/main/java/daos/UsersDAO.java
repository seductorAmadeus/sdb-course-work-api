package daos;

import entities.UserProfile;
import entities.Users;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.math.BigDecimal;

public class UsersDAO {

    public BigDecimal addUser(Users user, UserProfile userProfile) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        BigDecimal userId = null;
        try {
            transaction = session.beginTransaction();
            userProfile.setUsers(user);
            user.setProfile(userProfile);
            session.persist(userProfile);
            session.getTransaction().commit();
            userId = userProfile.getProfileId();
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

    // return null if user not exists
    public BigDecimal getUserId(String username, String password) throws NullPointerException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        BigDecimal userId = null;
        try {
            transaction = session.beginTransaction();
            Users user = (Users) session.createQuery("from Users where username=:username and password=:password")
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .setMaxResults(1).uniqueResult();

            if (user != null) {
                userId = user.getUserId();
            }
            transaction.commit();

        } catch (IllegalArgumentException exp) {
            System.out.println("The specified user is not in the database. Check it out correctly and try again");
        } catch (Exception exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        } finally {
            session.close();
        }
        return userId;
    }

    public Users getUserById(BigDecimal userId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        Users user = null;
        try {
            transaction = session.beginTransaction();
            user = (Users) session.get(Users.class, userId);
            transaction.commit();
        } catch (IllegalArgumentException exp) {
            System.out.println("The specified user is not in the database. Check it out correctly and try again");
        } catch (Exception exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        } finally {
            session.close();
        }
        return user;
    }

    public void deleteUser(BigDecimal userId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Users user = (Users) session.get(Users.class, userId);
            session.delete(user);
            transaction.commit();
        } catch (IllegalArgumentException exp) {
            System.out.println("The specified user is not in the database. Check it out correctly and try again");
        } catch (Exception exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        } finally {
            session.close();
        }
    }
}
