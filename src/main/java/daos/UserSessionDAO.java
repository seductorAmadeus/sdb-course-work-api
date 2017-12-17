package daos;

import entities.UserSession;
import entities.Users;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.math.BigDecimal;

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

}
