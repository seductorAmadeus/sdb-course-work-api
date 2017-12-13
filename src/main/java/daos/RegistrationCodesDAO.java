package daos;

import entities.RegistrationCodes;
import entities.UserSession;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.math.BigDecimal;
import java.math.BigInteger;

public class RegistrationCodesDAO {

    public static void main(String[] args) {
       RegistrationCodes registrationCodes = new RegistrationCodes(
               "available", "martinraila@mail.ru");

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.persist(registrationCodes);

        session.getTransaction().commit();
        session.close();
    }

    public BigDecimal addRegistrationCode(String inviteCodeStatus, String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        BigInteger inviteCode = null;

        try {
            transaction = session.beginTransaction();

            RegistrationCodes registrationCodes = new RegistrationCodes();
            registrationCodes.setInviteCodeStatus(inviteCodeStatus);
            registrationCodes.setEmail(email);

            inviteCode = (BigInteger) session.save(registrationCodes);
            transaction.commit();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        } finally {
            session.close();
        }

        return new BigDecimal(inviteCode);
    }
}
