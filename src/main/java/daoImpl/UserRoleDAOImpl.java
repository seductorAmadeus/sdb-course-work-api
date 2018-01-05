package daoImpl;

import dao.GenericDAO;
import entities.UserRole;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.math.BigDecimal;
import java.util.List;

/**
 * This class is a data access object that provides CRUD operations to {@link UserRole} entity.
 *
 * @author Rayla Martin
 * @version 0.1
 * @since 0.1
 */

// TODO: Create new interface or implement this
public class UserRoleDAOImpl implements GenericDAO<UserRole, BigDecimal> {

    public BigDecimal addRootRole() {
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

    public BigDecimal addAdminRole() {
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

    public BigDecimal addTeacherRole() {
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

    public BigDecimal addStudRole() {
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
            Query query = session.createSQLQuery("INSERT INTO USER_ROLE (TYPE) values(USER_T('yes','no','no','no'))");
            query.executeUpdate();
            Query query2 = session.createSQLQuery("INSERT INTO USER_ROLE (TYPE) values(USER_T('no','yes','no','no'))");
            query2.executeUpdate();
            Query query3 = session.createSQLQuery("INSERT INTO USER_ROLE (TYPE) values(USER_T('no','no','yes','no'))");
            query3.executeUpdate();
            Query query4 = session.createSQLQuery("INSERT INTO USER_ROLE (TYPE) values(USER_T('no','no','no','yes'))");
            query4.executeUpdate();
            transaction.commit();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }
    }

    public void dropAllUserRoleRecords() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("truncate table user_role").executeUpdate();
            transaction.commit();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }
    }

    public boolean checkUserRoleExists(BigDecimal userRoleId) {
        boolean userRoleExists = false;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            if (session.get(UserRole.class, userRoleId) != null) {
                userRoleExists = true;
            }
        } catch (HibernateException exp) {

        }

        return userRoleExists;
    }

    @Override
    public BigDecimal create(UserRole newInstance) {
        return null;
    }

    @Override
    public UserRole read(BigDecimal id) {
        return null;
    }

    @Override
    public void update(UserRole transientObject) {

    }

    public void delete(BigDecimal id) {

    }

    public boolean isExists(BigDecimal id) {
        return false;
    }

    @Override
    public List<UserRole> getList() {
        return null;
    }
}
