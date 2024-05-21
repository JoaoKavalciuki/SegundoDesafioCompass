package org.example.repositorys;

import org.example.entitys.Abrigo;
import org.example.entitys.Item;
import org.example.entitys.Pedido;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import org.example.entitys.PedidoItem;

import java.util.List;

public class PedidoRepository {

    private EntityManager em;

    public PedidoRepository() {
        this.em = Persistence.createEntityManagerFactory("desafio2").createEntityManager();
    }
    public List<Item> findAllItens() {
        return em.createQuery("SELECT i FROM Item i", Item.class).getResultList();
    }

    public Item findItemById(Long id) {
        return em.find(Item.class, id);
    }

    public void saveItem(Item item) {
        em.getTransaction().begin();
        em.persist(item);
        em.getTransaction().commit();
    }

    public void save(Pedido pedido) {
        em.getTransaction().begin();
        em.persist(pedido);
        for (PedidoItem pedidoItem : pedido.getItens()) {
            em.persist(pedidoItem);
        }
        em.getTransaction().commit();
    }
    public List<Pedido> findByAbrigo(Abrigo abrigo) {
        return em.createQuery("SELECT p FROM Pedido p WHERE p.abrigo = :abrigo", Pedido.class)
                .setParameter("abrigo", abrigo)
                .getResultList();
    }

    public void close() {
        em.close();
    }
}