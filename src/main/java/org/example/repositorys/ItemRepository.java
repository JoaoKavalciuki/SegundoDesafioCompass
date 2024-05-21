package org.example.repositorys;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import org.example.entity.Abrigo;
import org.example.entity.Item;

import java.util.List;

public class ItemRepository {
    private EntityManager em;

    public ItemRepository() {
        this.em = Persistence.createEntityManagerFactory("desafio2").createEntityManager();
    }

    public List<Item> findAll() {
        return em.createQuery("SELECT i FROM Item i", Item.class).getResultList();
    }

    public Item findById(Long id) {
        return em.find(Item.class, id);
    }

    public void save(Item item) {
        em.getTransaction().begin();
        em.persist(item);
        em.getTransaction().commit();
    }

    public List<Item> findItensByAbrigo(Abrigo abrigo) {
        return em.createQuery("SELECT i FROM Item i JOIN i.pedidoItens pi JOIN pi.pedido p WHERE p.abrigo = :abrigo", Item.class)
                .setParameter("abrigo", abrigo)
                .getResultList();
    }

    public void close() {
        em.close();
    }
}
