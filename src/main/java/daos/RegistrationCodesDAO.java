package daos;

import entities.RegistrationCodes;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import java.math.BigDecimal;
import java.math.BigInteger;

public class RegistrationCodesDAO {
    private static SessionFactory factory;

    public static void main(String[] args) {
        try {
            factory = new AnnotationConfiguration().
                    configure().
                            addAnnotatedClass(entities.RegistrationCodes.class).
                            buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        RegistrationCodesDAO registrationCodesDAO = new RegistrationCodesDAO();

        BigDecimal inviteCode = registrationCodesDAO.addRegistrationCode("Y", "12");
    }

    public BigDecimal addRegistrationCode(String inviteCodeStatus, String email) {
        Session session = factory.openSession();
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
