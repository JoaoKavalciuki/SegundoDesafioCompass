package org.example.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.example.entities.Item;
import org.example.exceptions.ResourceNotFoundException;
import org.example.services.interfaces.ItemService;

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
            } catch (InputMismatchException e) {
                System.out.println("ERRO: Entrada inválida. Digite apenas inteiros");
                sc.nextLine();
            }
        }
    }

    public Item getItem() {
        try {
            this.listByCategoria();
            System.out.println("Qual item será doado? Digite o ID respectivo ao Item.");
            long itemId = sc.nextLong();
            sc.nextLine();
            return itemService.findById(itemId);
        } catch (ResourceNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("ERRO: Entrada inválida. Digite apenas inteiros");
            sc.nextLine();
        }
        return null;
    }

    public void save() {
        try {
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
                while (!genero.equals("M") && !genero.equals("F")) {
                    System.out.println("Qual o gênero? (M/F)");
                    genero = sc.nextLine().toUpperCase();
                }
                System.out.println("Qual o tamanho?\nInfantil/PP/P/M/G/GG");
                String tamanho = sc.nextLine().toUpperCase();
                while (!tamanho.equals("Infantil") && !tamanho.equals("PP") && !tamanho.equals("P")
                        && !tamanho.equals("M") && !tamanho.equals("G") && !tamanho.equals("GG")) {
                    System.out.println("Qual o tamanho?\nInfantil/PP/P/M/G/GG");
                    tamanho = sc.nextLine().toUpperCase();
                }
                Item item = new Item(null, categoria, nome, genero, tamanho);
                itemService.save(item);
            } else {
                Item item = new Item(null, categoria, nome, null, null);
                itemService.save(item);
            }
        } catch (ResourceNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("ERRO: Entrada inválida. Digite apenas inteiros");
            sc.nextLine();
        }
    }

    public void update() {
        try {
            this.listByCategoria();
            System.out.println("Digite o id correspondente ao item que será alterado: ");
            Long id = sc.nextLong();
            sc.nextLine();
            itemService.findById(id);
            System.out.println("Digite o novo nome: ");
            String nome = sc.nextLine();
            itemService.update(nome, id);
        } catch (ResourceNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("ERRO: Entrada inválida. Digite apenas inteiros");
            sc.nextLine();
        }
    }

    public void deleteById() {
        try {
            this.listByCategoria();
            System.out.println("Digite o id correspodente ao item que será deletado: ");
            Long id = sc.nextLong();
            sc.nextLine();
            itemService.deleteById(id);
        } catch (ResourceNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("ERRO: Entrada inválida. Digite apenas inteiros");
            sc.nextLine();
        }
    }
}
