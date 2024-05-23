package org.example.repositories;

import org.example.entities.EstoqueAbrigo;

import jakarta.persistence.EntityManager;
import java.util.List;

public class EstoqueAbrigoRepository {
    private EntityManager em;

    public EstoqueAbrigoRepository(EntityManager em) {
        this.em = em;
    }

    public void save(EstoqueAbrigo estoqueAbrigo) {
        em.getTransaction().begin();
        em.persist(estoqueAbrigo);
        em.getTransaction().commit();
    }

    public List<EstoqueAbrigo> findAll() {
        return em.createQuery("from Estoque", EstoqueAbrigo.class).getResultList();
    }

    public EstoqueAbrigo findById(Long id) {
        return em.find(EstoqueAbrigo.class, id);
    }

    public void update(EstoqueAbrigo estoqueAbrigo) {
        em.getTransaction().begin();
        em.merge(estoqueAbrigo);
        em.getTransaction().commit();
    }

    public void delete(Long id) {
        EstoqueAbrigo estoqueAbrigo = findById(id);
        if (estoqueAbrigo != null) {
            em.getTransaction().begin();
            em.remove(estoqueAbrigo);
            em.getTransaction().commit();
        }
    }

    public List<EstoqueAbrigo> findByAbrigoId(Long abrigoId) {
        return em.createQuery("from EstoqueAbrigo where abrigo.id = :abrigoId", EstoqueAbrigo.class)
                .setParameter("abrigoId", abrigoId)
                .getResultList();
    }
}
