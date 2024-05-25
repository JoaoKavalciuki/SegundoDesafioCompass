package org.example.repositories;

import org.example.entities.Transferencia;

import jakarta.persistence.EntityManager;
import java.util.List;

public class TransferenciaRepository {
    private EntityManager em;

    public TransferenciaRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Transferencia transferencia) {
        em.getTransaction().begin();
        em.persist(transferencia);
        em.getTransaction().commit();
    }

    public List<Transferencia> findAll() {
        return em.createQuery("from Transferencia", Transferencia.class).getResultList();
    }

    public Transferencia findById(Long id) {
        return em.find(Transferencia.class, id);
    }

    public void update(Transferencia transferencia) {
        em.getTransaction().begin();
        em.merge(transferencia);
        em.getTransaction().commit();
    }

    public void delete(Long id) {
        Transferencia transferencia = findById(id);
        if (transferencia != null) {
            em.getTransaction().begin();
            em.remove(transferencia);
            em.getTransaction().commit();
        }
    }

    public List<Transferencia> findByAbrigoId(Long abrigoId) {
        return em.createQuery("from Transferencia where abrigo.id = :abrigoId", Transferencia.class)
                .setParameter("abrigoId", abrigoId)
                .getResultList();
    }
}
