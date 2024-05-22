package org.example.repositories;

import java.util.List;

import org.example.entities.Item;
import org.example.utils.JPAUtil;

import jakarta.persistence.EntityManager;

public class ItemRepository {
    private EntityManager em;

    public ItemRepository() {
        em = JPAUtil.getEntityManager();
    }

    public List<Item> findByCategoria(String categoria) {
        String query = "SELECT i FROM Item i WHERE i.categoria = :categoria";
        return em.createQuery(query, Item.class)
                .setParameter("categoria", categoria)
                .getResultList();
    }

    public List<Item> findAll() {
        return em.createQuery("FROM " + Item.class.getName(), Item.class).getResultList();
    }

    public Item findById(Long id) {
        return em.find(Item.class, id);
    }

    public Item findByName(String nome) {
        String query = "SELECT i FROM Item i WHERE i.itemNome = :nome";
        return em.createQuery(query, Item.class)
                .setParameter("nome", nome)
                .getSingleResult();
    }

    public void save(Item item) {
        em.getTransaction().begin();
        em.persist(item);
        em.getTransaction().commit();
    }

    public void update(Item toUpdate) {
        em.getTransaction().begin();
        em.merge(toUpdate);
        em.getTransaction().commit();
    }

    public void deleteById(Long id) {
        em.getTransaction().begin();
        em.remove(findById(id));
        em.getTransaction().commit();
    }
}
