package org.example;

import java.util.Scanner;

import org.example.utils.JPAUtil;
import org.example.utils.SystemUtil;

import jakarta.persistence.EntityManager;

public class Main {
    public static void main(String[] args) {

        JPAUtil.initialize();
        EntityManager em = JPAUtil.getEntityManager();
        Scanner sc = new Scanner(System.in);
        SystemUtil systemUtil = new SystemUtil(em, sc);
        int op = 0;
        while (op != 4) {
            op = menu(sc);
            switch (op) {
                case 1:
                    systemUtil.listCentros();
                    break;
                case 2:
                    systemUtil.listItens();
                    break;
                case 3:
                    systemUtil.saveDoacao();
                    break;
                case 4:
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
        int op = 0;
        System.out.println("Menu:");
        System.out.println("1. Listar Centros de Distribuição");
        System.out.println("2. Listar Itens Disponíveis para Doação");
        System.out.println("3. Doar");
        System.out.println("4. Sair");
        System.out.print("Escolha uma opção: ");
        op = sc.nextInt();
        sc.nextLine();
        return op;
    }
}
