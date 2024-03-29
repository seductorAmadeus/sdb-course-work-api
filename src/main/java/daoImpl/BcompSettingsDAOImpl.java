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

    public boolean isExists(Class<BcompSettings> bcompSettingsClass, BigDecimal id) {
        return GenericDAO.super.isExists(bcompSettingsClass, id);
    }

    @Override
    public void delete(Class<BcompSettings> bcompSettingsClass, BigDecimal id) {
        GenericDAO.super.delete(bcompSettingsClass, id);
    }
}
