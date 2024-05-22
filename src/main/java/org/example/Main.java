package org.example;

import java.util.Scanner;

import org.example.entities.Abrigo;
import org.example.utils.JPAUtil;
import org.example.utils.SystemUtil;
import org.example.utils.AbrigoSystemUtil;

import jakarta.persistence.EntityManager;

public class Main {
    public static void main(String[] args) {
        JPAUtil.initialize();
        EntityManager em = JPAUtil.getEntityManager();
        Scanner sc = new Scanner(System.in);
        SystemUtil systemUtil = new SystemUtil(em, sc);
        AbrigoSystemUtil abrigoSystemUtil = new AbrigoSystemUtil(em, sc);

        int op = 0;
        while (op != 5) {
            op = menu(sc);
            switch (op) {
                case 1:
                    systemUtil.listCentros();
                    break;
                case 2:
                    abrigoMenu(sc, abrigoSystemUtil);
                    break;
                case 3:
                    systemUtil.listItens();
                    break;
                case 4:
                    systemUtil.saveDoacao();
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
        System.out.println("3. Listar Itens Disponíveis para Doação");
        System.out.println("4. Doar");
        System.out.println("5. Sair");
        System.out.print("Escolha uma opção: ");
        int op = sc.nextInt();
        sc.nextLine();
        return op;
    }

    private static void abrigoMenu(Scanner sc, AbrigoSystemUtil abrigoSystemUtil) {
        int op = 0;
        while (op != 5) {
            System.out.println("Menu de Abrigos:");
            System.out.println("1. Cadastrar Abrigo");
            System.out.println("2. Listar Abrigos");
            System.out.println("3. Atualizar Abrigo");
            System.out.println("4. Deletar Abrigo");
            System.out.println("5. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            op = sc.nextInt();
            sc.nextLine();
            switch (op) {
                case 1:
                    abrigoSystemUtil.createAbrigo(new Abrigo());
                    break;
                case 2:
                    abrigoSystemUtil.listarAbrigos();
                    break;
                case 3:
                    System.out.print("ID do Abrigo a ser atualizado: ");
                    Long id = sc.nextLong();
                    sc.nextLine();
                    abrigoSystemUtil.updateAbrigo(id);
                    break;
                case 4:
                    System.out.print("ID do Abrigo a ser atualizado: ");
                    id = sc.nextLong();
                    sc.nextLine();
                    abrigoSystemUtil.deleteAbrigo(id);
                    break;
                case 5:
                    System.out.println("Voltando ao Menu Principal");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}
