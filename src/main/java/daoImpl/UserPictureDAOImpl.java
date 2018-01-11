package daoImpl;

import dao.GenericDAO;
import entities.UserPicture;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UserPictureDAOImpl implements GenericDAO<UserPicture, BigDecimal> {

    @Override
    public BigDecimal create(UserPicture userPicture) {
        Transaction transaction = null;
        BigDecimal userPictureId = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(userPicture);
            transaction.commit();
            userPictureId = userPicture.getId();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }
        return userPictureId;
    }

    @Override
    public UserPicture get(BigDecimal id) {
        Transaction transaction = null;
        UserPicture userPicture = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            userPicture = (UserPicture) session.createQuery("from UserPicture where id = :userPictureId")
                    .setParameter("userPictureId", id)
                    .setMaxResults(1)
                    .uniqueResult();
        } catch (HibernateException exp) {
            if (transaction != null) {
                transaction.rollback();
                exp.printStackTrace();
            }
        }
        return userPicture;
    }

    @Override
    public List<UserPicture> getList() {
        Transaction transaction = null;
        List<UserPicture> list = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List tempList = session.createQuery("from UserPicture ").list();
            for (Object aTempList : tempList) {
                UserPicture userPicture = (UserPicture) aTempList;
                list.add(userPicture);
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
