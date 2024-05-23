package org.example;

import java.util.Scanner;

import org.example.repositories.PedidoRepository;
import org.example.services.*;
import org.example.services.interfaces.EstoqueAbrigoService;
import org.example.utils.*;

import jakarta.persistence.EntityManager;

public class Main {
    public static void main(String[] args) {
        JPAUtil.initialize();
        EntityManager em = JPAUtil.getEntityManager();
        Scanner sc = new Scanner(System.in);

        DoacaoSystemUtil doacaoSystemUtil = new DoacaoSystemUtil();
        CentroSystemUtil centroSystemUtil = new CentroSystemUtil(new CentroDistribuicaoServiceImpl());
        ItemSystemUtil itemSystemUtil = new ItemSystemUtil(new ItemServiceImpl());
        EstoqueAbrigoService estoqueAbrigoService = new EstoqueAbrigoServiceImpl(em,sc);
        AbrigoServiceImpl abrigoService = new AbrigoServiceImpl(em,sc, estoqueAbrigoService);
        AbrigoSystemUtil abrigoSystemUtil = new AbrigoSystemUtil(new AbrigoServiceImpl(em, sc, estoqueAbrigoService), new EstoqueAbrigoServiceImpl(em, sc));
        PedidoSystemUtil pedidoSystemUtil = new PedidoSystemUtil(abrigoService, new ItemServiceImpl(), new PedidoServiceImpl(new PedidoRepository()));

        int op = 0;
        while (op != 5) {
            op = menu(sc);
            switch (op) {
                case 1:
                    centroSystemUtil.listCentros();
                    break;
                case 2:
                    abrigoMenu(sc, abrigoSystemUtil);
                    break;
                case 3:
                    itemMenu(sc, itemSystemUtil);
                    break;
                case 4:
                    doacaoMenu(sc, doacaoSystemUtil);
                    break;
                case 5:
                    System.out.println("Saindo do programa");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
        sc.close();
        JPAUtil.closeEntityManager(em);
        JPAUtil.closeEntityManagerFactory();
    }

    private static int menu(Scanner sc) {
        System.out.println("Menu:");
        System.out.println("1. Listar Centros de Distribuição");
        System.out.println("2. Gerenciar Abrigos");
        System.out.println("3. Gerenciar Itens");
        System.out.println("4. Gerenciar Doações");
        System.out.println("5. Sair");
        System.out.print("Escolha uma opção: ");
        int op = sc.nextInt();
        sc.nextLine();
        return op;
    }

    private static void doacaoMenu(Scanner sc, DoacaoSystemUtil doacaoSystemUtil) {
        int op = 0;
        while (op != 6) {
            System.out.println("Menu de Doações: ");
            System.out.println("1. Listar Doações");
            System.out.println("2. Listar Doações por Categoria");
            System.out.println("3. Fazer nova Doação");
            System.out.println("4. Editar Doação");
            System.out.println("5. Excluir Doação");
            System.out.println("6. Voltar ao menu principal");
            op = sc.nextInt();
            sc.nextLine();
            switch (op) {
                case 1:
                    doacaoSystemUtil.listAll();
                    break;
                case 2:
                    doacaoSystemUtil.listByCategoria();
                    break;
                case 3:
                    doacaoSystemUtil.saveDoacao();
                    break;
                case 4:
                    doacaoSystemUtil.update();
                    break;
                case 5:
                    doacaoSystemUtil.deleteById();
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Digite uma opção válida");
            }
        }
    }

    private static void itemMenu(Scanner sc, ItemSystemUtil itemSystemUtil) {
        int op = 0;
        while (op != 6) {
            System.out.println("Menu de Itens: ");
            System.out.println("1. Listar Itens");
            System.out.println("2. Listar Itens por Categoria");
            System.out.println("3. Cadastrar Item");
            System.out.println("4. Editar Item");
            System.out.println("5. Excluir Item");
            System.out.println("6. Voltar ao menu principal");
            op = sc.nextInt();
            sc.nextLine();
            switch (op) {
                case 1:
                    itemSystemUtil.listAll();
                    break;
                case 2:
                    itemSystemUtil.listByCategoria();
                    break;
                case 3:
                    itemSystemUtil.save();
                    break;
                case 4:
                    itemSystemUtil.update();
                    break;
                case 5:
                    itemSystemUtil.deleteById();
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Digite uma opção válida");
            }
        }
    }

    private static void abrigoMenu(Scanner sc, AbrigoSystemUtil abrigoSystemUtil) {
        int op = 0;
        while (op != 5) {
            System.out.println("Menu de Abrigos:");
            System.out.println("1. Cadastrar Abrigo");
            System.out.println("2. Listar Abrigos");
            System.out.println("3. Atualizar Abrigo");
            System.out.println("4. Deletar Abrigo");
            System.out.println("5. Listar Estoque dos Abrigos");
            System.out.println("6. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            op = sc.nextInt();
            sc.nextLine();
            switch (op) {
                case 1:
                    abrigoSystemUtil.create();
                    break;
                case 2:
                    abrigoSystemUtil.listAbrigos();
                    System.out.println("Desja fazer um pedido de algum item para algum abrigo? (S/N)");
                    char resposta = sc.next().charAt(0);
                    sc.nextLine();

                    while (resposta != 'S' && resposta != 'N'){
                        System.out.print("A resposta precisa ser S ou N. Insira um resposta válida: ");
                        resposta = sc.next().charAt(0);
                        sc.nextLine();
                    }

                    if(resposta == 'S'){
                        pedidoSystemutil.fazerDoacao();
                    }
                    break;
                case 3:
                    abrigoSystemUtil.update();
                    break;
                case 4:
                    abrigoSystemUtil.delete();
                    break;
                case 5:
                    abrigoSystemUtil.listAbrigoEstoque();
                    break;
                case 6:
                    System.out.println("Voltando ao Menu Principal");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}
