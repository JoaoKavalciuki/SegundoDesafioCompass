package org.example.utils;

import org.example.entities.CentroDistribuicao;
import org.example.entities.Doacao;
import org.example.entities.Item;
import org.example.exceptions.ResourceNotFoundException;
import org.example.services.CentroDistribuicaoServiceImpl;
import org.example.services.DoacaoServiceImpl;
import org.example.services.ItemServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class DoacaoSystemUtil {
    private final DoacaoServiceImpl doacaoService = new DoacaoServiceImpl();
    private Scanner sc = new Scanner(System.in);
    private final CentroSystemUtil centroUtil = new CentroSystemUtil(new CentroDistribuicaoServiceImpl());
    private final ItemSystemUtil itemUtil = new ItemSystemUtil(new ItemServiceImpl());

    public void saveDoacao() {
        try {
            Item item = itemUtil.getItem();
            CentroDistribuicao centro = centroUtil.getCentro();
            System.out.println("Qual a quantidade?");
            int quantidade = sc.nextInt();
            sc.nextLine();
            Doacao doacao = new Doacao(null, quantidade, LocalDate.now(), centro, item);
            doacaoService.save(doacao);
        } catch (ResourceNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    public void listByCategoria() {
        String categoria = itemUtil.getCategoria();
        List<Doacao> doacoes = doacaoService.listByCategoria(categoria);
        for (Doacao d : doacoes) {
            System.out.println("Id Doacao: " + d.getId());
            System.out.println("Nome do item: " + d.getItem().getItemTipo());
            System.out.println("Id centro: " + d.getCentroDistribuicao().getId());
            System.out.println("Quantidade doada: " + d.getQuantidade());
            System.out.println();
        }
    }

    public void listAll() {
        List<Doacao> doacoes = doacaoService.listAll();
        for (Doacao d : doacoes) {
            System.out.println("Id Doacao: " + d.getId());
            System.out.println("Nome do item: " + d.getItem().getItemTipo());
            System.out.println("Id centro: " + d.getCentroDistribuicao().getId());
            System.out.println("Quantidade doada: " + d.getQuantidade());
            System.out.println();
        }
    }

    public void update() {
        this.listByCategoria();
        System.out.println("Digite o ID da doação a ser modificada: ");
        Long id = sc.nextLong();
        sc.nextLine();
        System.out.println("Digite a nova quantidade: ");
        int quantidade = sc.nextInt();
        sc.nextLine();
        doacaoService.update(quantidade, id);
    }

    public void deleteById() {
        this.listByCategoria();
        System.out.println("Digite o ID da doação a ser deletada: ");
        Long id = sc.nextLong();
        sc.nextLine();
        doacaoService.deleteById(id);
    }
}
