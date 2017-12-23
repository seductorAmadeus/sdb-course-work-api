package daos;

import entities.BcompSettings;
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
 * This class is a data access object that provides CRUD operations to {@link BcompSettings} entity.
 *
 * @author Rayla Martin
 * @version 0.1
 * @since 0.1
 */

public class BcompSettingsDAO {

    public BigDecimal addBcompSettings(BcompSettings bcompSettings) {
        Connection connection = null;
        ConnectionJDBC connectionHandler = new ConnectionJDBC();
        BigDecimal bcompSettingsId = null;
        try {
            connection = connectionHandler.createConnection();

            CallableStatement callableStatement = connection.prepareCall("{? = call CREATEPCKG.ADDBCOMPSETTINGS(?,?)}");
            callableStatement.registerOutParameter(1, Types.DECIMAL);
            callableStatement.setString(2, bcompSettings.getValue());
            callableStatement.setString(3, bcompSettings.getType());
            callableStatement.execute();

            // we're getting id;
            bcompSettingsId = (BigDecimal) callableStatement.getObject(1);

        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            connectionHandler.close(connection);
        }

        return bcompSettingsId;
    }

    @Deprecated
    public BigDecimal addBcompSettingsH(BcompSettings bcompSettings) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        BigDecimal bcompSettingsId = null;

        try {
            transaction = session.beginTransaction();
            session.persist(bcompSettings);
            transaction.commit();
            bcompSettingsId = bcompSettings.getId();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        } finally {
            session.close();
        }

        return bcompSettingsId;
    }

    public BcompSettings getBcompSettingsById(BigDecimal bcompSettingsId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        BcompSettings bcompSettings = null;
        try {
            transaction = session.beginTransaction();
            bcompSettings = (BcompSettings) session.createQuery("from BcompSettings where id = :bcompSettingsId")
                    .setParameter("bcompSettingsId", bcompSettingsId)
                    .setMaxResults(1)
                    .uniqueResult();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        } finally {
            session.close();
        }
        return bcompSettings;
    }

    public List<BcompSettings> getBcompSettingsList() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<BcompSettings> list = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            List tempList = session.createQuery("from BcompSettings").list();
            for (Object aTempList : tempList) {
                BcompSettings bcompSettings = (BcompSettings) aTempList;
                list.add(bcompSettings);
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

    public void dropAllBcompSettingsRecords() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("truncate table bcomp_settings").executeUpdate();
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

    public boolean checkBcompSettingsExists(BigDecimal bcompSettingsId) {
        boolean bcompSettingsExists = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            if (session.get(BcompSettings.class, bcompSettingsId) != null) {
                bcompSettingsExists = true;
            }
        } catch (HibernateException exp) {

        } finally {
            session.close();
        }

        return bcompSettingsExists;
    }
}
