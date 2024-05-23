package org.example.services.interfaces;

import java.util.List;

import org.example.entities.Item;

public interface ItemService {
    List<Item> findAll();

    List<Item> findByCategoria(String categoria);

    Item findById(Long id);

    boolean findByName(String name);

    void save(Item item);

    void update(String novoNome, Long id);

    void deleteById(Long id);
}
