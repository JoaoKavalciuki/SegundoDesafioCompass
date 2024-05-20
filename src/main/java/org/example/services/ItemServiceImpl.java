package org.example.services;

import java.util.List;

import org.example.entities.Item;
import org.example.repositories.ItemRepository;
import org.example.services.interfaces.ItemService;

public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;

    public ItemServiceImpl() {
        this.itemRepository = new ItemRepository();
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item findById(int id) {
        return itemRepository.findById(id);
    }
}
