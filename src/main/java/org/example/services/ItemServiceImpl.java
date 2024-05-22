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

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public List<Item> findByCategoria(String categoria) {
        return itemRepository.findByCategoria(categoria);
    }

    @Override
    public Item findById(Long id) {
        Item item = itemRepository.findById(id);
        if (item == null) {
            throw new EntityNotFoundException("Item não encontrado com ID: " + id);
        }
        return item;
    }

    @Override
    public void save(Item item) {
        if (findByName(item.getItemNome()) != null) {
            throw new IllegalArgumentException("Já existe um item com este nome no banco de dados");
        }
        itemRepository.save(item);
    }

    @Override
    public void update(String novoNome, Long id) {
        if (id <= 31) {
            throw new IllegalArgumentException("O item deste id não pode ser alterado");
        }
        Item old = this.findById(id);
        Item updated = new Item(old.getId(), old.getCategoria(), novoNome, old.getGenero(), old.getTamanho());
        itemRepository.update(updated);
    }

    @Override
    public Item findByName(String name) {
        return itemRepository.findByName(name);
    }

    @Override
    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }
}
