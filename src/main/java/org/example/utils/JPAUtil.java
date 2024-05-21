package org.example.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static EntityManagerFactory emf;
    private static EntityManager em;

    private JPAUtil() {
    }

    public static void initialize() {
        emf = Persistence.createEntityManagerFactory("desafio2");
        em = emf.createEntityManager();
    }

    public static EntityManager getEntityManager() {
        return em;
    }

    public static void closeEntityManager(EntityManager em) {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    public static void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
