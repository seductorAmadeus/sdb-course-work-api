package daoImpl;

import dao.BcompDAO;
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
public class BcompDAOImpl implements BcompDAO {

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
        Transaction transaction = null;
        BigDecimal bcompId = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(bcomp);
            transaction.commit();
            bcompId = bcomp.getId();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }

        return bcompId;
    }

    public List<Bcomp> getList() {
        Transaction transaction = null;
        List<Bcomp> list = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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
        }
        return list;
    }

    public Bcomp get(BigDecimal bcompId) {
        Transaction transaction = null;
        Bcomp bcomp = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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
        }
        return bcomp;
    }


}
