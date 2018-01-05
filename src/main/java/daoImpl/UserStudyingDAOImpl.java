package daoImpl;

import dao.GenericDAO;
import entities.UserStudying;
import oracle.jdbc.OracleTypes;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.List;

/**
 * This class is a data access object that provides CRUD operations to {@link UserStudying} entity.
 *
 * @author Rayla Martin
 * @version 0.1
 * @since 0.1
 */

// TODO: Create new interface or implement this

public class UserStudyingDAOImpl implements GenericDAO<UserStudying, BigDecimal> {

    private ResultSet resultSet;
    private BigDecimal bigDecimal;

    public BigDecimal addGroupToUser(String userGroup) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.doWork(connection -> {
                CallableStatement callableStatement = connection.prepareCall("{? = call READPCKG.GETSTUDYINGIDFROMGROUP(?)}");
                callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
                callableStatement.setString(2, userGroup);
                callableStatement.execute();
                resultSet = (ResultSet) callableStatement.getObject(1);
                while (resultSet.next()) {
                    bigDecimal = resultSet.getBigDecimal("user_studying_id");
                }
            });

            transaction.commit();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }
        return bigDecimal;
    }


    public void addNewUserGroup(String group) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.doWork(connection -> {
                CallableStatement callableStatement = connection.prepareCall("{ ? = call CREATEPCKG.addUserStudying(?)}");
                callableStatement.registerOutParameter(1, Types.DECIMAL);
                callableStatement.setString(2, group);
                callableStatement.executeUpdate();
            });
            transaction.commit();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }
    }

    // TODO: add function like "list all groups"
    public boolean groupExist(String group) {
        Transaction transaction = null;
        boolean groupExist = false;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.doWork(connection -> {
                // TODO: fix this function
                CallableStatement callableStatement = connection.prepareCall("{ ? = call CREATEPCKG.addUserStudying(?)}");
                callableStatement.registerOutParameter(1, Types.DECIMAL);
                callableStatement.setString(2, "P3100");
            });
            transaction.commit();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }

        return groupExist;
    }

    @Deprecated
    public void generateAllUsersGroups() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.doWork(connection -> {
                CallableStatement callableStatement = connection.prepareCall("{ ? = call CREATEPCKG.addUserStudying(?)}");
                callableStatement.registerOutParameter(1, Types.DECIMAL);
                callableStatement.setString(2, "P3100");
                callableStatement.executeUpdate();
                callableStatement.setString(2, "P3101");
                callableStatement.executeUpdate();
                callableStatement.setString(2, "P3102");
                callableStatement.executeUpdate();
                callableStatement.setString(2, "P3110");
                callableStatement.executeUpdate();
                callableStatement.setString(2, "P3111");
                callableStatement.executeUpdate();
            });
            transaction.commit();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }
    }

    public void dropAllUserStudyingRecords() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE user_studying").executeUpdate();
            transaction.commit();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }
    }

    public boolean checkUserStudyingExists(BigDecimal userStudyingId) {
        boolean userStudyingExists = false;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            if (session.get(UserStudying.class, userStudyingId) != null) {
                userStudyingExists = true;
            }
        } catch (HibernateException exp) {

        }

        return userStudyingExists;
    }

    @Override
    public BigDecimal create(UserStudying newInstance) {
        return null;
    }

    @Override
    public UserStudying read(BigDecimal id) {
        return null;
    }

    @Override
    public void update(UserStudying transientObject) {

    }

    public void delete(BigDecimal id) {

    }

    @Override
    public List<UserStudying> getList() {
        return null;
    }
}
