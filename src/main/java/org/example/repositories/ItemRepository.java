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

    public Item findById(int id) {
        return em.find(Item.class, id);
    }
}
