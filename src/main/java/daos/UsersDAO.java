package daos;

import entities.UserProfile;
import entities.Users;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.math.BigDecimal;

/**
 * This class is a data access object that provides CRUD operations to {@link Users} entity.
 *
 * @author Rayla Martin
 * @version 0.1
 * @since 0.1
 */

public class UsersDAO {

    public BigDecimal addUser(Users user, UserProfile userProfile) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        BigDecimal userId = null;
        try {
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

    public UserProfile getUserProfileById(BigDecimal userProfileId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        UserProfile userProfile = null;
        try {
            transaction = session.beginTransaction();
            userProfile = (UserProfile) session.get(UserProfile.class, userProfileId);
            transaction.commit();
        } catch (IllegalArgumentException exp) {
            System.out.println("The specified user profile is not in the database. Check it out correctly and try again");
        } catch (Exception exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        } finally {
            session.close();
        }
        return userProfile;
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

    public void dropAllUsersRecords() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("truncate table users").executeUpdate();
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

    public boolean checkUserExists(BigDecimal userId) {
        boolean usersExists = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            if (session.get(Users.class, userId) != null) {
                usersExists = true;
            }
        } catch (HibernateException exp) {

        } finally {
            session.close();
        }

        return usersExists;
    }

    public boolean checkUserProfileExists(BigDecimal userProfileId) {
        boolean userProfileExists = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            if (session.get(UserProfile.class, userProfileId) != null) {
                userProfileExists = true;
            }
        } catch (HibernateException exp) {

        } finally {
            session.close();
        }

        return userProfileExists;
    }

    public void updateUserProfile(UserProfile userProfile) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(userProfile);
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
