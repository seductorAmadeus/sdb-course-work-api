package daos;

import entities.RegistrationCodes;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.math.BigDecimal;

public class RegistrationCodesDAO {

    public BigDecimal addRegistrationCode(RegistrationCodes registrationCodes) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        BigDecimal inviteCode = null;

        try {
            transaction = session.beginTransaction();

            session.persist(registrationCodes);

            transaction.commit();

            inviteCode = registrationCodes.getInviteCode();

        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        } finally {
            session.close();
        }

        return inviteCode;
    }


}
