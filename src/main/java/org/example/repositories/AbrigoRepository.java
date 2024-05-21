package org.example.repositories;

import org.example.entities.Abrigo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.util.List;

public class AbrigoRepository {

    private EntityManager em;

    public AbrigoRepository() {
        this.em = Persistence.createEntityManagerFactory("desafio2").createEntityManager();
    }

    public void save(Abrigo abrigo) {
        em.getTransaction().begin();
        em.persist(abrigo);
        em.getTransaction().commit();
    }

    public Abrigo findById(Long id) {
        return em.find(Abrigo.class, id);
    }

    public List<Abrigo> findAll() {
        return em.createQuery("SELECT a FROM Abrigo a", Abrigo.class).getResultList();
    }

    public void update(Abrigo abrigo) {
        em.getTransaction().begin();
        em.merge(abrigo);
        em.getTransaction().commit();
    }

    public void delete(Long id) {
        em.getTransaction().begin();
        Abrigo abrigo = em.find(Abrigo.class, id);
        if (abrigo != null) {
            em.remove(abrigo);
        }
        em.getTransaction().commit();
    }

    public void close() {
        em.close();
    }
}