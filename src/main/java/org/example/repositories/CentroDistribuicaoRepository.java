package org.example.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.entities.CentroDistribuicao;

import java.util.List;

public class CentroDistribuicaoRepository {

    private EntityManagerFactory emf;
    private EntityManager em;

    public CentroDistribuicaoRepository(){
        emf = Persistence.createEntityManagerFactory("desafio2");
        em = emf.createEntityManager();
    }

    public void save(CentroDistribuicao centroDistribuicao) {
        em.getTransaction().begin();
        em.persist(centroDistribuicao);
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    public List<CentroDistribuicao> findAll() {
        return em.createQuery("FROM CentroDistribuicao", CentroDistribuicao.class).getResultList(); //JPQL
    }

}
