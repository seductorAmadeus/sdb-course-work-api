package daos;

import entities.Users;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import java.math.BigDecimal;
import java.math.BigInteger;

public class UsersDAO {
    private static SessionFactory factory;

    public static void main(String[] args) {
        try {
            factory = new AnnotationConfiguration().
                    configure().
                    //addPackage("com.xyz") //add package if used.
                            addAnnotatedClass(Users.class).
                            buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }


        UsersDAO usersDAO = new UsersDAO();

        BigDecimal empID1 = usersDAO.addUser("Zara", "Ali", new BigDecimal(124));
        BigDecimal empID2 = usersDAO.addUser("Zara2", "Ali2", new BigDecimal(1241));

    }

    public BigDecimal addUser(String username, String password, BigDecimal inviteCode) {
        Session session = factory.openSession();
        Transaction transaction = null;
        BigInteger userID = null;

        try {
            transaction = session.beginTransaction();
            Users user = new Users();
            user.setUsername(username);
            user.setPassword(password);
            user.setInviteCode(inviteCode);

            userID = (BigInteger) session.save(user);
            transaction.commit();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        } finally {
            session.close();
        }

        // TODO: change this test value
        return new BigDecimal(userID);
    }
}
