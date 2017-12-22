package daos;

import entities.UserSession;
import entities.Users;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UserSessionDAO {

    public BigDecimal createSession(BigDecimal userId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        BigDecimal userSessionId = null;
        UserSession userSession = new UserSession();

        // attempt to set initial status for session
        userSession.setStatus("active");

        // create new Hibernate session and get user for adding to user's session
        UsersDAO usersDAO = new UsersDAO();
        Users user = usersDAO.getUserById(userId);
        userSession.setUserID(user);

        try {
            transaction = session.beginTransaction();
            session.persist(userSession);
            transaction.commit();
            userSessionId = userSession.getId();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        } finally {
            session.close();
        }

        return userSessionId;
    }

    public UserSession getUserSessionById(BigDecimal userSessionId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        UserSession userSession = null;
        try {
            transaction = session.beginTransaction();
            userSession = (UserSession) session.createQuery("from UserSession where id = :userSessionId")
                    .setParameter("userSessionId", userSessionId)
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
        return userSession;
    }

    public boolean checkSessionExists(BigDecimal userSessionId) {
        boolean userSessionExists = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            if (session.get(UserSession.class, userSessionId) != null) {
                userSessionExists = true;
            }
        } catch (HibernateException exp) {

        } finally {
            session.close();
        }

        return userSessionExists;
    }

    public List<UserSession> listUserSessions() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<UserSession> list = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            List tempList = session.createQuery("from UserSession").list();
            for (Object aTempList : tempList) {
                UserSession userSession = (UserSession) aTempList;
                list.add(userSession);
            }
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        } finally {
            session.close();
        }

        return list;

    }
}
