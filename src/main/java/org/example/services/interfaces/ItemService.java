package org.example.services.interfaces;

import java.util.List;

import org.example.entities.Item;

public interface ItemService {
    List<Item> findAll();

    Item findById(Long id);
}
