package org.example.services;

import java.util.List;

import org.example.entities.Item;
import org.example.exceptions.DuplicateEntryException;
import org.example.exceptions.IllegalEntryException;
import org.example.exceptions.ResourceNotFoundException;
import org.example.repositories.ItemRepository;
import org.example.services.interfaces.ItemService;

public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;

    public ItemServiceImpl() {
        this.itemRepository = new ItemRepository();
    }

    @Override
    public List<Item> findAll() {
        List<Item> itens = itemRepository.findAll();
        if (!itens.isEmpty())
            System.out.println("LISTA DE ITENS POR CATEGORIA:");
        return itens;
    }

    @Override
    public List<Item> findByCategoria(String categoria) {
        List<Item> itens = itemRepository.findByCategoria(categoria);
        if (!itens.isEmpty())
            System.out.println("LISTA DE ITENS POR CATEGORIA:");
        return itens;
    }

    @Override
    public Item findById(Long id) {
        Item item = itemRepository.findById(id);
        if (item == null) {
            throw new ResourceNotFoundException("Item não encontrado com ID: " + id);
        }
        return item;
    }

    @Override
    public void save(Item item) {
        try {
            if (findByName(item.getItemTipo())) {
                throw new DuplicateEntryException("Já existe um item com este nome no banco de dados");
            }
            if (item.getItemTipo().length() < 4) {
                throw new IllegalEntryException("Nome muito curto");
            }
            itemRepository.save(item);
            System.out.println("ITEM INSERIDO");
        } catch (DuplicateEntryException | IllegalEntryException e) {
            System.out.println("ERRO: " + e.getMessage());
            return;
        }
    }

    @Override
    public void update(String novoNome, Long id) {
        try {
            if (id <= 31) {
                throw new IllegalEntryException("O item deste id não pode ser alterado");
            }
            if (novoNome.length() < 4) {
                throw new IllegalEntryException("Nome muito curto");
            }
            Item old = this.findById(id);
            Item updated = new Item(old.getId(), old.getCategoria(), novoNome, old.getGenero(), old.getTamanho());
            itemRepository.update(updated);
            System.out.println("ITEM ATUALIZADO");
        } catch (IllegalEntryException | ResourceNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
            return;
        }
    }

    @Override
    public boolean findByName(String name) {
        Item item = itemRepository.findByName(name);
        return item != null;
    }

    @Override
    public void deleteById(Long id) {
        try {
            if (id <= 31) {
                throw new IllegalEntryException("O item deste id não pode ser excluído");
            }
            Item item = findById(id);
            itemRepository.deleteById(item.getId());
            System.out.println("ITEM DELETADO");
        } catch (IllegalEntryException | ResourceNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
            return;
        }
    }
}
