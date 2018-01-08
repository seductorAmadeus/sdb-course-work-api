package daoImpl;

import dao.UserProfileDAO;
import entities.UserProfile;
import entities.Users;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UserProfileDAOImpl implements UserProfileDAO {

    @Override
    // This method does not create only the user profile, it also creates the user!
    public BigDecimal create(UserProfile userProfile) {
        UsersDAOImpl usersDAO = new UsersDAOImpl();
        Users users = userProfile.getUsers();
        users.setUserProfile(userProfile);
        BigDecimal userProfileId = usersDAO.create(users);
        return userProfileId;
    }

    @Override
    public UserProfile get(BigDecimal id) {
        Transaction transaction = null;
        UserProfile userProfile = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            userProfile = session.get(UserProfile.class, id);
            transaction.commit();
        } catch (IllegalArgumentException exp) {
            System.out.println("The specified user profile is not in the database. Check it out correctly and try again");
        } catch (Exception exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }
        return userProfile;
    }

    // TODO: add documentation
   // if you only need to delete the profile, then comment out this method.
    public void delete(Class<UserProfile> userProfileClass, BigDecimal id) {
        UsersDAOImpl usersDAO = new UsersDAOImpl();
        usersDAO.delete(Users.class, id);
    }

    @Override
    public List<UserProfile> getList() {
        Transaction transaction = null;
        List<UserProfile> list = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List tempList = session.createQuery("from UserProfile").list();
            for (Object aTempList : tempList) {
                UserProfile userProfile = (UserProfile) aTempList;
                list.add(userProfile);
            }
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }
        return list;
    }
}
