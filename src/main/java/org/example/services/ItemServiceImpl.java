package org.example.services;

import java.util.List;

import org.example.entities.Item;
import org.example.repositories.ItemRepository;
import org.example.services.interfaces.ItemService;

import jakarta.persistence.EntityNotFoundException;

public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;

    public ItemServiceImpl() {
        this.itemRepository = new ItemRepository();
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item findById(Long id) {
        Item item = itemRepository.findById(id);
        if (item == null) {
            throw new EntityNotFoundException("Item não encontrado com ID: " + id);
        }
        return item;
    }

    public void saveItem(Item item) {
        itemRepository.saveItem(item);
    }

    public void updateItem(Item updated, Long id) {
        if (id <= 31) {
            throw new IllegalArgumentException("O item deste id não pode ser alterado");
        }
        updated.setId(this.findById(id).getId());
        itemRepository.updateItem(updated);
    }
}
