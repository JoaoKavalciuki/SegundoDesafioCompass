package org.example.utils;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.example.entities.CentroDistribuicao;
import org.example.entities.Doacao;
import org.example.entities.Item;
import org.example.services.CentroDistribuicaoServiceImpl;
import org.example.services.DoacaoServiceImpl;
import org.example.services.ItemServiceImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;

public class SystemUtil {
    private final CentroDistribuicaoServiceImpl centroDistribuicaoService = new CentroDistribuicaoServiceImpl();
    private final ItemServiceImpl itemService = new ItemServiceImpl();
    private final DoacaoServiceImpl doacaoService = new DoacaoServiceImpl();
    private EntityManager em;
    private Scanner sc;

    public SystemUtil(EntityManager em, Scanner sc) {
        this.em = em;
        this.sc = sc;
    }

    public void listCentros() {
        List<CentroDistribuicao> centros = centroDistribuicaoService.findAll();
        for (CentroDistribuicao cd : centros) {
            System.out.println(cd);
        }
    }

    public void listItens() {
        List<Item> itens = itemService.findAll();
        for (Item item : itens) {
            System.out.println(item);
        }
    }

    public Item getItem() {
        this.listItens();
        System.out.println("Qual item será doado?");
        long itemId = sc.nextLong();
        sc.nextLine();
        return itemService.findById(itemId);
    }

    public CentroDistribuicao getCentro() {
        this.listCentros();
        System.out.println("Para qual centro?");
        long centroId = sc.nextLong();
        sc.nextLine();
        return centroDistribuicaoService.findById(centroId);
    }

    public void saveDoacao() throws EntityNotFoundException {
        Item item = this.getItem();
        CentroDistribuicao centro = this.getCentro();
        System.out.println("Qual a quantidade?");
        int quantidade = sc.nextInt();
        sc.nextLine();
        Doacao doacao = new Doacao(null, quantidade, LocalDate.now(), centro, item);
        doacaoService.save(doacao);
    }
}
