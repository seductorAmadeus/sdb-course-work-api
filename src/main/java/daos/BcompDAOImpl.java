package daos;

import entities.Bcomp;
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
 * This class is a data access object that provides CRUD operations to {@link Bcomp} entity.
 *
 * @author Rayla Martin
 * @version 0.1
 * @since 0.1
 */
public class BcompDAOImpl implements GenericDAO<Bcomp, BigDecimal> {

    public BigDecimal create(Bcomp bcomp) {
        Connection connection = null;
        ConnectionJDBC connectionHandler = new ConnectionJDBC();
        BigDecimal bcompId = null;
        try {
            connection = connectionHandler.createConnection();

            CallableStatement callableStatement = connection.prepareCall("{? = call CREATEPCKG.ADDBCOMP(?,?, ?, ?, ?, ?, ?, ?, ?,?" +
                    ", ?, ?, ?,?, ?,?, ?,?,?, ?, ?, ?)}");
            callableStatement.registerOutParameter(1, Types.DECIMAL);
            callableStatement.setBigDecimal(2, bcomp.getUserSession().getId());
            callableStatement.setNull(3, Types.CLOB);
            callableStatement.setNull(4, Types.VARCHAR);
            callableStatement.setNull(5, Types.VARCHAR);
            callableStatement.setNull(6, Types.VARCHAR);
            callableStatement.setNull(7, Types.VARCHAR);
            callableStatement.setNull(8, Types.VARCHAR);
            callableStatement.setNull(9, Types.VARCHAR); // br
            callableStatement.setNull(10, Types.VARCHAR); // ac
            callableStatement.setNull(11, Types.VARCHAR); // c
            callableStatement.setNull(12, Types.VARCHAR); // kr
            callableStatement.setNull(13, Types.VARCHAR); // bit
            callableStatement.setNull(14, Types.VARCHAR); //int_req_ed_1
            callableStatement.setNull(15, Types.VARCHAR);//int_req_ed_2
            callableStatement.setNull(16, Types.VARCHAR);//int_req_ed_3
            callableStatement.setNull(17, Types.VARCHAR); //rd_ed_1
            callableStatement.setNull(18, Types.VARCHAR);//rd_ed_2
            callableStatement.setNull(19, Types.VARCHAR);//rd_ed_3
            callableStatement.setNull(20, Types.CLOB); // memory_mc
            callableStatement.setNull(21, Types.VARCHAR); //c_mc
            callableStatement.setNull(22, Types.VARCHAR); //r_mc
            callableStatement.setNull(23, Types.CLOB); //asm

            callableStatement.execute();

            // we're getting id;
            bcompId = (BigDecimal) callableStatement.getObject(1);

        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            connectionHandler.close(connection);
        }

        return bcompId;
    }

    @Deprecated
    public BigDecimal createEmptyBcompH(Bcomp bcomp) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        BigDecimal bcompId = null;

        try {
            transaction = session.beginTransaction();
            session.persist(bcomp);
            transaction.commit();
            bcompId = bcomp.getId();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        } finally {
            session.close();
        }

        return bcompId;
    }

    /*TODO: fix it*/
    @Deprecated
    public void dropAllBcompRecords() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE bcomp").executeUpdate();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        } finally {
            session.close();
        }
    }

    public List<Bcomp> getList() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Bcomp> list = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            List tempList = session.createQuery("from Bcomp").list();
            for (Object aTempList : tempList) {
                Bcomp bcomp = (Bcomp) aTempList;
                list.add(bcomp);
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

    public boolean isExists(BigDecimal bcompId) {
        boolean bcompExists = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            if (session.get(Bcomp.class, bcompId) != null) {
                bcompExists = true;
            }
        } catch (HibernateException exp) {

        } finally {
            session.close();
        }

        return bcompExists;
    }

    public Bcomp read(BigDecimal bcompId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        Bcomp bcomp = null;
        try {
            transaction = session.beginTransaction();
            bcomp = (Bcomp) session.createQuery("from Bcomp where id = :bcompId")
                    .setParameter("bcompId", bcompId)
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
        return bcomp;
    }

    // TODO: change it
    public void update(Bcomp bcomp) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(bcomp);
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

    public void delete(BigDecimal bcompId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Bcomp bcomp = session.get(Bcomp.class, bcompId);
            session.delete(bcomp);
            transaction.commit();
        } catch (IllegalArgumentException exp) {
            System.out.println("The specified bcomp is not in the database. Check it out correctly and try again");
        } catch (Exception exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        } finally {
            session.close();
        }
    }

}
