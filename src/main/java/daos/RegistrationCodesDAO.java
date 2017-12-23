package daos;

import entities.RegistrationCodes;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.ConnectionJDBC;
import utils.HibernateUtil;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a data access object that provides CRUD operations to {@link RegistrationCodes} entity.
 *
 * @author Rayla Martin
 * @version 0.1
 * @since 0.1
 */

public class RegistrationCodesDAO {

    public BigDecimal addRegistrationCode(RegistrationCodes registrationCodes) {
        Connection connection = null;
        ConnectionJDBC connectionHandler = new ConnectionJDBC();
        BigDecimal inviteCode = null;
        try {
            connection = connectionHandler.createConnection();

            CallableStatement callableStatement = connection.prepareCall("{? = call CREATEPCKG.ADDREGISTRATIONCODES(?,?,?)}");
            callableStatement.registerOutParameter(1, Types.DECIMAL);
            callableStatement.setBigDecimal(2, registrationCodes.getInviteCode());
            callableStatement.setString(3, registrationCodes.getInviteCodeStatus());
            callableStatement.setString(4, registrationCodes.getEmail());
            callableStatement.execute();

            // we're getting id;
            inviteCode = (BigDecimal) callableStatement.getObject(1);

        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            connectionHandler.close(connection);
        }

        return inviteCode;
    }

    @Deprecated
    public BigDecimal addRegistrationCodeH(RegistrationCodes registrationCodes) {
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

    public List<RegistrationCodes> listRegistrationCodes() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<RegistrationCodes> list = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            List tempList = session.createQuery("from RegistrationCodes").list();
            for (Object aTempList : tempList) {
                RegistrationCodes registrationCode = (RegistrationCodes) aTempList;
                list.add(registrationCode);
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

    public void updateRegistrationCodeStatus(BigDecimal inviteCode, String inviteCodeStatus) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            RegistrationCodes registrationCode = (RegistrationCodes) session.get(RegistrationCodes.class, inviteCode);
            registrationCode.setInviteCodeStatus(inviteCodeStatus);
            session.update(registrationCode);
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

    public void deleteRegistrationCode(BigDecimal inviteCode) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            RegistrationCodes registrationCode = (RegistrationCodes) session.get(RegistrationCodes.class, inviteCode);
            session.delete(registrationCode);
            transaction.commit();
        } catch (IllegalArgumentException exp) {
            System.out.println("The specified registration code is not in the database. Check it out correctly and try again");
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        } finally {
            session.close();
        }
    }

    public RegistrationCodes findFreeRegistrationCode() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        RegistrationCodes freeRegistrationCode = null;
        try {
            transaction = session.beginTransaction();
            freeRegistrationCode = (RegistrationCodes) session.createQuery("from RegistrationCodes where inviteCodeStatus = 'available'").setMaxResults(1).uniqueResult();
            freeRegistrationCode.setInviteCodeStatus("not available");
            transaction.commit();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        } finally {
            session.close();
        }
        return freeRegistrationCode;
    }

    public void dropAllRegistrationCodesRecords() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("truncate table REGISTRATION_CODES").executeUpdate();
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
