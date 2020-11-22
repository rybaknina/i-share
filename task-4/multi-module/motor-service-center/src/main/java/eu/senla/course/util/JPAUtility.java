package eu.senla.course.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtility {
    private static final EntityManagerFactory emFactory;
    static {
        emFactory = Persistence.createEntityManagerFactory("jpa.hibernate");
    }
    public static EntityManager getEntityManager() {
        return emFactory.createEntityManager();
    }
    public static void close() {
        if (emFactory != null) {
            emFactory.close();
        }
    }
}
