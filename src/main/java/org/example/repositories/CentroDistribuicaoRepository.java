package org.example.repositories;

import java.util.List;

import org.example.entities.CentroDistribuicao;
import org.example.utils.JPAUtil;

import jakarta.persistence.EntityManager;

public class CentroDistribuicaoRepository {
    private EntityManager em;

    public CentroDistribuicaoRepository() {
        em = JPAUtil.getEntityManager();
    }

    public void save(CentroDistribuicao centroDistribuicao) {
        em.getTransaction().begin();
        em.persist(centroDistribuicao);
        em.getTransaction().commit();
    }

    public List<CentroDistribuicao> findAll() {
        return em.createQuery("FROM " + CentroDistribuicao.class.getName(), CentroDistribuicao.class).getResultList();
    }

    public CentroDistribuicao findById(int id) {
        return em.find(CentroDistribuicao.class, id);
    }
}
