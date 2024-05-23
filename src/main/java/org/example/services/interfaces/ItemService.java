package org.example.services.interfaces;

import org.example.entities.Item;

import java.util.List;

public interface ItemService {
    List<Item> findAll();

    List<Item> findByCategoria(String categoria);

    Item findById(Long id);

    boolean findByName(String name);

    void save(Item item);

    void update(String novoNome, Long id);

    void deleteById(Long id);
}
