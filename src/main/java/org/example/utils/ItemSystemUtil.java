package org.example.utils;

import java.util.List;
import java.util.Scanner;

import org.example.entities.Item;
import org.example.services.interfaces.ItemService;

public class ItemSystemUtil {
    private final ItemService itemService;
    private Scanner sc = new Scanner(System.in);

    public ItemSystemUtil(ItemService itemService) {
        this.itemService = itemService;
    }

    public void listByCategoria() {
        String categoria = getCategoria();
        List<Item> itens = itemService.findByCategoria(categoria);
        for (Item i : itens) {
            System.out.println(i);
        }
    }

    public void listAll() {
        List<Item> itens = itemService.findAll();
        for (Item i : itens) {
            System.out.println(i);
        }
    }

    public String getCategoria() {
        System.out.println("Categorias:\n1 - Roupas.\n2 - Produtos de Higiene\n3 - Alimentos");
        System.out.println("Qual categoria? Digite a palavra correspodente à categoria.");
        String categoria = sc.nextLine();
        return categoria;
    }

    public Item getItem() {
        this.listByCategoria();
        System.out.println("Qual item será doado? Digite o ID respectivo ao Item.");
        long itemId = sc.nextLong();
        sc.nextLine();
        return itemService.findById(itemId);
    }

    public void save() {
        System.out.println("Categorias:\n1 - Roupas.\n2 - Produtos de Higiene\n3 - Alimentos");
        System.out.println("Qual categoria? Digite a palavra correspodente à categoria.");
        String categoria = sc.nextLine();
        System.out.println("Qual o nome do item?");
        String nome = sc.nextLine();
        if (categoria.equalsIgnoreCase("roupas")) {
            System.out.println("Qual o gênero? (M/F)");
            String genero = sc.nextLine();
            System.out.println("Qual o tamanho?\nInfantil/PP/P/M/G/GG");
            String tamanho = sc.nextLine();
            Item item = new Item(null, categoria, nome, genero, tamanho);
            itemService.save(item);
        } else {
            Item item = new Item(null, categoria, nome, null, null);
            itemService.save(item);
        }
    }

    public void update() {
        this.listByCategoria();
        System.out.println("Digite o id correspodente ao item que será alterado: ");
        Long id = sc.nextLong();
        sc.nextLine();
        System.out.println("Digite o novo nome: ");
        String nome = sc.nextLine();
        itemService.update(nome, id);
    }

    public void deleteById() {
        this.listAll();
        System.out.println("Digite o id correspodente ao item que será deletado: ");
        Long id = sc.nextLong();
        sc.nextLine();
        itemService.deleteById(id);
    }
}
