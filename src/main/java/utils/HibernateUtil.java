package utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
    private static EntityManager entityManager;

    static {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPersistence");
            entityManager = emf.createEntityManager();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static EntityManager getEntityManager() {
        return entityManager;
    }
}