package daoImpl;

import dao.UsersDao;
import entities.UserProfile;
import entities.Users;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a data access object that provides CRUD operations to {@link Users} entity.
 *
 * @author Rayla Martin
 * @version 0.1
 * @since 0.1
 */
public class UsersDAOImpl implements UsersDao {

    public BigDecimal create(Users user) {
        Transaction transaction = null;
        UserProfile userProfile = user.getUserProfile();
        BigDecimal userId = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            userProfile.setUsers(user);
            user.setUserProfile(userProfile);
            session.persist(userProfile);
            session.getTransaction().commit();
            userId = userProfile.getProfileId();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }

        return userId;
    }

    // return null if user not exists
    public BigDecimal getUserId(String username, String password) throws NullPointerException {
        Transaction transaction = null;
        BigDecimal userId = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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
        }
        return userId;
    }

    public Users get(BigDecimal userId) {
        Transaction transaction = null;
        Users user = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            user = session.get(Users.class, userId);
            transaction.commit();
        } catch (IllegalArgumentException exp) {
            System.out.println("The specified user is not in the database. Check it out correctly and try again");
        } catch (Exception exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public List<Users> getList() {
        Transaction transaction = null;
        List<Users> list = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List tempList = session.createQuery("from Users ").list();
            for (Object aTempList : tempList) {
                Users user = (Users) aTempList;
                list.add(user);
            }
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }
        return list;
    }

    public boolean isExists(Class<Users> usersClass, BigDecimal id) {
        return UsersDao.super.isExists(usersClass, id);
    }
}
