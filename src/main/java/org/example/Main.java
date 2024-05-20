package org.example;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.example.entities.CentroDistribuicao;
import org.example.entities.Doacao;
import org.example.entities.Item;
import org.example.services.CentroDistribuicaoServiceImpl;
import org.example.services.DoacaoServiceImpl;
import org.example.services.ItemServiceImpl;
import org.example.utils.JPAUtil;

import jakarta.persistence.EntityManager;

public class Main {
    public static void main(String[] args) {

        JPAUtil.initialize();
        EntityManager em = JPAUtil.getEntityManager();
        CentroDistribuicaoServiceImpl centroDistribuicaoService = new CentroDistribuicaoServiceImpl();
        ItemServiceImpl itemService = new ItemServiceImpl();
        DoacaoServiceImpl doacaoService = new DoacaoServiceImpl();
        Scanner sc = new Scanner(System.in);

        try {
            int option = 0;
            while (option != 4) {
                System.out.println("Menu:");
                System.out.println("1. Listar Centros de Distribuição");
                System.out.println("2. Listar Itens Disponíveis para Doação");
                System.out.println("3. Doar");
                System.out.println("4. Sair");
                System.out.print("Escolha uma opção: ");
                option = sc.nextInt();
                sc.nextLine();
                switch (option) {
                    case 1:
                        listCentros(centroDistribuicaoService.findAll());
                        break;
                    case 2:
                        listItens(itemService.findAll());
                        break;
                    case 3:
                        listItens(itemService.findAll());
                        System.out.println("Qual item será doado?");
                        int itemId = sc.nextInt();
                        sc.nextLine();
                        // ! Tratar possível exceção caso id inserido errado
                        Item item = itemService.findById(itemId);
                        listCentros(centroDistribuicaoService.findAll());
                        System.out.println("Para qual centro?");
                        int centroId = sc.nextInt();
                        sc.nextLine();
                        // ! Tratar possível exceção caso id inserido errado
                        CentroDistribuicao centro = centroDistribuicaoService.findById(centroId);
                        System.out.println("Qual a quantidade?");
                        int quantidade = sc.nextInt();
                        sc.nextLine();
                        Doacao doacao = new Doacao(null, quantidade, LocalDate.now(), centro, item);
                        doacaoService.save(doacao);
                        break;
                    case 4:
                        System.out.println("Saindo do programa");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            }
        } finally {
            JPAUtil.closeEntityManager(em);
            JPAUtil.closeEntityManagerFactory();
            sc.close();
        }
    }

    private static void listCentros(List<CentroDistribuicao> centros) {
        for (CentroDistribuicao cd : centros) {
            System.out.println(cd);
        }
    }

    private static void listItens(List<Item> itens) {
        for (Item item : itens) {
            System.out.println(item);
        }
    }
}
