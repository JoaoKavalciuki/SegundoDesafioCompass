package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.entity.CentroDistribuicao;
import org.example.entity.Item;
import org.example.entity.Pedido;
import org.example.entity.PedidoItem;

import java.util.List;

public class PedidoRepository {

    private EntityManagerFactory emf;
    private EntityManager em;

    public PedidoRepository(){
        emf = Persistence.createEntityManagerFactory("desafio2");
        em = emf.createEntityManager();
    }

    public void save(Pedido pedido) {
        em.getTransaction().begin();
        em.persist(pedido);
        em.getTransaction().commit();

    }

    public List<CentroDistribuicao> findAll() {
        return em.createQuery("FROM CentroDistribuicao", CentroDistribuicao.class).getResultList(); //JPQL
    }

    public Pedido findById(Long id){
        return em.find(Pedido.class, id);
    }

}
