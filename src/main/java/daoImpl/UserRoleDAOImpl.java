package daoImpl;

import dao.UserRoleDAO;
import entities.UserRole;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a data access object that provides CRUD operations to {@link UserRole} entity.
 *
 * @author Rayla Martin
 * @version 0.1
 * @since 0.1
 */
public class UserRoleDAOImpl implements UserRoleDAO {

    public BigDecimal getRootRoleId() {
        Transaction transaction = null;
        BigDecimal bigDecimal = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            bigDecimal = (BigDecimal) session.createSQLQuery("SELECT USER_ROLE.USER_ROLE_ID FROM USER_ROLE \n" +
                    "WHERE USER_ROLE.TYPE = USER_T( :root, :admin, :teacher, :stud)")
                    .setParameter("root", "yes")
                    .setParameter("admin", "no")
                    .setParameter("teacher", "no")
                    .setParameter("stud", "no")
                    .setMaxResults(1).uniqueResult();
            transaction.commit();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }
        return bigDecimal;
    }

    public BigDecimal getAdminRoleId() {
        Transaction transaction = null;
        BigDecimal bigDecimal = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            bigDecimal = (BigDecimal) session.createSQLQuery("SELECT USER_ROLE.USER_ROLE_ID FROM USER_ROLE \n" +
                    "WHERE USER_ROLE.TYPE = USER_T( :root, :admin, :teacher, :stud)")
                    .setParameter("root", "no")
                    .setParameter("admin", "yes")
                    .setParameter("teacher", "no")
                    .setParameter("stud", "no").setMaxResults(1).uniqueResult();
            transaction.commit();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }
        return bigDecimal;
    }

    public BigDecimal getTeacherRoleId() {
        Transaction transaction = null;
        BigDecimal bigDecimal = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            bigDecimal = (BigDecimal)
                    session.createSQLQuery("SELECT USER_ROLE.USER_ROLE_ID FROM USER_ROLE \n" +
                            "WHERE USER_ROLE.TYPE = USER_T( :root, :admin, :teacher, :stud)")
                            .setParameter("root", "no")
                            .setParameter("admin", "no")
                            .setParameter("teacher", "yes")
                            .setParameter("stud", "no")
                            .setMaxResults(1).uniqueResult();
            transaction.commit();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }
        return bigDecimal;
    }

    public BigDecimal getStudRoleId() {
        Transaction transaction = null;
        BigDecimal bigDecimal = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            bigDecimal = (BigDecimal) session.createSQLQuery("SELECT USER_ROLE.USER_ROLE_ID FROM USER_ROLE \n" +
                    "WHERE USER_ROLE.TYPE = USER_T( :root, :admin, :teacher, :stud)")
                    .setParameter("root", "no")
                    .setParameter("admin", "no")
                    .setParameter("teacher", "no")
                    .setParameter("stud", "yes").setMaxResults(1).uniqueResult();
            transaction.commit();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }
        return bigDecimal;
    }

    public void generateAllUsersRoles() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            if (getRootRoleId() == null) {
                Query query = session.createSQLQuery("INSERT INTO USER_ROLE (TYPE) VALUES(USER_T('yes','no','no','no'))");
                query.executeUpdate();
            }
            if (getAdminRoleId() == null) {
                Query query2 = session.createSQLQuery("INSERT INTO USER_ROLE (TYPE) VALUES(USER_T('no','yes','no','no'))");
                query2.executeUpdate();
            }
            if (getTeacherRoleId() == null) {
                Query query3 = session.createSQLQuery("INSERT INTO USER_ROLE (TYPE) VALUES(USER_T('no','no','yes','no'))");
                query3.executeUpdate();
            }
            if (getStudRoleId() == null) {
                Query query4 = session.createSQLQuery("INSERT INTO USER_ROLE (TYPE) VALUES(USER_T('no','no','no','yes'))");
                query4.executeUpdate();
            }
            transaction.commit();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }
    }

    @Override
    public BigDecimal create(UserRole userRole) {
        Transaction transaction = null;
        BigDecimal userRoleId = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(userRole);
            transaction.commit();
            userRoleId = userRole.getId();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }
        return userRoleId;
    }

    @Override
    public UserRole get(BigDecimal id) {
        Transaction transaction = null;
        UserRole userRole = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            userRole = (UserRole) session.createQuery("from UserRole where id = :userRoleId")
                    .setParameter("userRoleId", id)
                    .setMaxResults(1)
                    .uniqueResult();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }
        return userRole;
    }


    @Override
    public List<UserRole> getList() {
        Transaction transaction = null;
        List<UserRole> list = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List tempList = session.createQuery("from UserRole ").list();
            for (Object aTempList : tempList) {
                UserRole userRole = (UserRole) aTempList;
                list.add(userRole);
            }
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }

        return list;
    }

    public boolean isExists(Class<UserRole> userRoleClass, BigDecimal id) {
        return UserRoleDAO.super.isExists(userRoleClass, id);
    }
}
