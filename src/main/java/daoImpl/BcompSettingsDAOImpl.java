package daoImpl;

import dao.GenericDAO;
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

public class BcompSettingsDAOImpl implements GenericDAO<BcompSettings, BigDecimal> {

    public BigDecimal create(BcompSettings bcompSettings) {
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
        Transaction transaction = null;
        BigDecimal bcompSettingsId = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(bcompSettings);
            transaction.commit();
            bcompSettingsId = bcompSettings.getId();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }

        return bcompSettingsId;
    }

    public BcompSettings get(BigDecimal bcompSettingsId) {
        Transaction transaction = null;
        BcompSettings bcompSettings = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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
        }
        return bcompSettings;
    }

    public List<BcompSettings> getList() {
        Transaction transaction = null;
        List<BcompSettings> list = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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
        }

        return list;
    }

    @Deprecated
    public void dropAllBcompSettingsRecords() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE bcomp_settings").executeUpdate();
            transaction.commit();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }
    }

    /*TODO: Add warning! This method will delete all records thar related with BcompSettings! (i.e bcomp, user_session, bcomp_setting, session setting*/
    @Override
    public void delete(Class<BcompSettings> bcompSettingsClass, BigDecimal id) {
        delete(bcompSettingsClass, id);
    }
}
