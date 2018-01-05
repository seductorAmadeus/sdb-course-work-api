package daoImpl;

import dao.GenericDAO;
import entities.UserProfile;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.math.BigDecimal;
import java.util.List;

public class UserProfileDAOImpl implements GenericDAO<UserProfile, BigDecimal> {
    // TODO: fix it!
    @Override
    public BigDecimal create(UserProfile newInstance) {
        return null;
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

    @Override
    public List<UserProfile> getList() {
        return null;
    }
}
