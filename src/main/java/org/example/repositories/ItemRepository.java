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

    public List<Item> findAll() {
        return em.createQuery("FROM " + Item.class.getName(), Item.class).getResultList();
    }

    public Item findById(Long id) {
        return em.find(Item.class, id);
    }

    public void saveItem(Item item) {
        em.getTransaction().begin();
        em.persist(item);
        em.getTransaction().commit();
    }

    public void updateItem(Item updated) {
        em.getTransaction().begin();
        em.merge(updated);
        em.getTransaction().commit();
    }
}
