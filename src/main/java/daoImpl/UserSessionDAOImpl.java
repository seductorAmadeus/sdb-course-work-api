package daoImpl;

import dao.GenericDAO;
import entities.UserPicture;
import entities.UserSession;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a data access object that provides CRUD operations to {@link UserSession} entity.
 *
 * @author Rayla Martin
 * @version 0.1
 * @since 0.1
 */
public class UserSessionDAOImpl implements GenericDAO<UserSession, BigDecimal> {

    public BigDecimal create(UserSession userSession) {
        Transaction transaction = null;
        BigDecimal userSessionId = null;

        // attempt to set initial status for session
        userSession.setStatus("active");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(userSession);
            transaction.commit();
            userSessionId = userSession.getId();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }

        return userSessionId;
    }

    public UserSession get(BigDecimal userSessionId) {
        Transaction transaction = null;
        UserSession userSession = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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
        }
        return userSession;
    }

    public List<UserSession> getList() {
        Transaction transaction = null;
        List<UserSession> list = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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
        }
        return list;
    }

    public boolean isExists(Class<UserSession> userSessionClass, BigDecimal id) {
        return GenericDAO.super.isExists(userSessionClass, id);
    }
}
