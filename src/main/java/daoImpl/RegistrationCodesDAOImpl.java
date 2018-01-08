package daoImpl;

import dao.RegistrationCodesDAO;
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

public class RegistrationCodesDAOImpl implements RegistrationCodesDAO {

    public BigDecimal create(RegistrationCodes registrationCodes) {
        Connection connection = null;
        ConnectionJDBC connectionHandler = new ConnectionJDBC();
        BigDecimal inviteCodeId = null;
        try {
            connection = connectionHandler.createConnection();

            CallableStatement callableStatement = connection.prepareCall("{? = call CREATEPCKG.ADDREGISTRATIONCODES(?,?,?)}");
            callableStatement.registerOutParameter(1, Types.DECIMAL);
            callableStatement.setBigDecimal(2, registrationCodes.getInviteCode());
            callableStatement.setString(3, registrationCodes.getInviteCodeStatus());
            callableStatement.setString(4, registrationCodes.getEmail());
            callableStatement.execute();

            // we're getting id;
            inviteCodeId = (BigDecimal) callableStatement.getObject(1);

        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            connectionHandler.close(connection);
        }

        return inviteCodeId;
    }

    @Deprecated
    public BigDecimal addRegistrationCodeH(RegistrationCodes registrationCodes) {
        Transaction transaction = null;
        BigDecimal inviteCode = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(registrationCodes);
            transaction.commit();
            inviteCode = registrationCodes.getInviteCode();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }

        return inviteCode;
    }

    public List<RegistrationCodes> getList() {
        Transaction transaction = null;
        List<RegistrationCodes> list = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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
        }

        return list;

    }

    public RegistrationCodes getAvailableCode() {
        Transaction transaction = null;
        RegistrationCodes freeRegistrationCode = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            freeRegistrationCode = (RegistrationCodes) session.createQuery("from RegistrationCodes where inviteCodeStatus = 'available'").setMaxResults(1).uniqueResult();
            freeRegistrationCode.setInviteCodeStatus("not available");
            transaction.commit();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }
        return freeRegistrationCode;
    }

    public RegistrationCodes get(BigDecimal registrationCodeId) {
        Transaction transaction = null;
        RegistrationCodes registrationCode = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            registrationCode = (RegistrationCodes) session.createQuery("from RegistrationCodes where regCodeId = :regCodeId")
                    .setParameter("regCodeId", registrationCodeId)
                    .setMaxResults(1)
                    .uniqueResult();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }
        return registrationCode;
    }
}
