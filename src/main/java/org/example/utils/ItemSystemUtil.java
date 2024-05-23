package org.example.utils;

import org.example.entities.Item;
import org.example.exceptions.ResourceNotFoundException;
import org.example.services.interfaces.ItemService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ItemSystemUtil {
    private final ItemService itemService;
    private Scanner sc = new Scanner(System.in);

    public ItemSystemUtil(ItemService itemService) {
        this.itemService = itemService;
    }

    public void listByCategoria() {
        String categoria = this.getCategoria();
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
        List<String> categorias = new ArrayList<>(Arrays.asList("Roupas", "Produtos de Higiene", "Alimentos"));
        while (true) {
            System.out.println("Categorias:\n1 - Roupas.\n2 - Produtos de Higiene\n3 - Alimentos");
            System.out.println("Qual categoria? Digite o número correspondente à categoria.");
            try {
                int categoriaInt = sc.nextInt();
                sc.nextLine();
                if (categoriaInt < 1 || categoriaInt > categorias.size()) {
                    throw new ResourceNotFoundException("Categoria não existe!");
                }
                return categorias.get(categoriaInt - 1);
            } catch (ResourceNotFoundException e) {
                System.out.println("ERRO: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("ERRO: Entrada inválida. Digite apenas inteiros");
                sc.nextLine();
            }
        }
    }

    public Item getItem() {
        this.listByCategoria();
        System.out.println("Qual item será doado? Digite o ID respectivo ao Item.");
        long itemId = sc.nextLong();
        sc.nextLine();
        return itemService.findById(itemId);
    }

    public void save() {
        String categoria = this.getCategoria();
        System.out.println("Itens já cadastrados desta categoria: ");
        List<Item> itens = itemService.findByCategoria(categoria);
        for (Item i : itens) {
            System.out.println(i);
        }
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
        this.listByCategoria();
        System.out.println("Digite o id correspodente ao item que será deletado: ");
        Long id = sc.nextLong();
        sc.nextLine();
        itemService.deleteById(id);
    }
}
