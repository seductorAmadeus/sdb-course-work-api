package daos;

import oracle.jdbc.OracleTypes;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;
import utils.HibernateUtil;

import java.math.BigDecimal;
import java.sql.*;

public class UserStudyingDAO {

    public static ResultSet resultSet;
    public static BigDecimal bigDecimal;

    public BigDecimal addGroupToUser(String userGroup) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.doWork(new Work() {
                @Override
                public void execute(Connection connection) throws SQLException {
                    CallableStatement callableStatement = connection.prepareCall("{? = call READPCKG.GETSTUDYINGIDFROMGROUP(?)}");
                    callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
                    callableStatement.setString(2, userGroup);
                    callableStatement.execute();
                    resultSet = (ResultSet) callableStatement.getObject(1);
                    while (resultSet.next()) {
                        bigDecimal = resultSet.getBigDecimal("user_studying_id");
                    }
                }
            });

            transaction.commit();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        } finally {
            session.close();
        }
        return bigDecimal;
    }


    public void generateAllUsersGroups() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
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
        } finally {
            session.close();
        }
    }
}
