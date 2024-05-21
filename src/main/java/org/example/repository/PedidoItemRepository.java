package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.entity.PedidoItem;

import java.util.List;

public class PedidoItemRepository {

    private EntityManagerFactory emf;
    private EntityManager em;

    public PedidoItemRepository(){
        emf = Persistence.createEntityManagerFactory("desafio2");
        em = emf.createEntityManager();
    }

    public void save(PedidoItem pedidoItem) {
        em.getTransaction().begin();
        em.persist(pedidoItem);
        em.getTransaction().commit();
    }

    public List<PedidoItem> findAll() {
        return em.createQuery("FROM PedidoItem ", PedidoItem.class).getResultList(); //JPQL
    }


}
