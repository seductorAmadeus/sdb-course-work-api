package daoImpl;

import dao.UserStudyingDAO;
import entities.UserStudying;
import oracle.jdbc.OracleTypes;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.ConnectionJDBC;
import utils.HibernateUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a data access object that provides CRUD operations to {@link UserStudying} entity.
 *
 * @author Rayla Martin
 * @version 0.1
 * @since 0.1
 */
public class UserStudyingDAOImpl implements UserStudyingDAO {

    private ResultSet resultSet;
    private BigDecimal bigDecimal;

    public BigDecimal getIdByUserGroup(String userGroup) {
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


    public BigDecimal create(UserStudying userStudying) {
        Connection connection = null;
        ConnectionJDBC connectionHandler = new ConnectionJDBC();
        BigDecimal userStudyingId = null;
        // check that userGroup exists
        if (getIdByUserGroup(userStudying.getUserGroup()) == null) {
            try {
                connection = connectionHandler.createConnection();

                CallableStatement callableStatement = connection.prepareCall("{ ? = call CREATEPCKG.addUserStudying(?)}");
                callableStatement.registerOutParameter(1, Types.DECIMAL);
                callableStatement.setString(2, userStudying.getUserGroup());
                callableStatement.execute();

                // we're getting id;
                userStudyingId = (BigDecimal) callableStatement.getObject(1);

            } catch (SQLException exp) {
                exp.printStackTrace();
            } finally {
                connectionHandler.close(connection);
            }
        }
        return userStudyingId;
    }

    public void generateAllUsersGroups() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.doWork(connection -> {
                CallableStatement callableStatement = connection.prepareCall("{ ? = call CREATEPCKG.addUserStudying(?)}");
                callableStatement.registerOutParameter(1, Types.DECIMAL);
                if (getIdByUserGroup("P3100") == null) {
                    callableStatement.setString(2, "P3100");
                    callableStatement.executeUpdate();
                }
                if (getIdByUserGroup("P3101") == null) {
                    callableStatement.setString(2, "P3101");
                    callableStatement.executeUpdate();
                }
                if (getIdByUserGroup("P3102") == null) {
                    callableStatement.setString(2, "P3102");
                    callableStatement.executeUpdate();
                }
                if (getIdByUserGroup("P3110") == null) {
                    callableStatement.setString(2, "P3110");
                    callableStatement.executeUpdate();
                }
                if (getIdByUserGroup("P3111") == null) {
                    callableStatement.setString(2, "P3111");
                    callableStatement.executeUpdate();
                }
            });
            transaction.commit();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }
    }

    @Override
    public UserStudying get(BigDecimal id) {
        Transaction transaction = null;
        UserStudying userStudying = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            userStudying = (UserStudying) session.createQuery("from UserStudying where id = :id")
                    .setParameter("id", id)
                    .setMaxResults(1)
                    .uniqueResult();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }
        return userStudying;
    }

    @Override
    public List<UserStudying> getList() {
        Transaction transaction = null;
        List<UserStudying> list = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List tempList = session.createQuery("from UserStudying").list();
            for (Object aTempList : tempList) {
                UserStudying userStudying = (UserStudying) aTempList;
                list.add(userStudying);
            }
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }

        return list;
    }
}
