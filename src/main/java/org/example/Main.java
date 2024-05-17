package org.example;

import org.example.entity.CentroDistribuicao;
import org.example.service.CentroDistribuicaoService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        CentroDistribuicaoService centroDistribuicaoService = new CentroDistribuicaoService();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Listar Centros de Distribuição");
            System.out.println("2. Sair");
            System.out.print("Escolha uma opção: ");
            int option = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (option) {
                case 1:
                    List<CentroDistribuicao> centros = centroDistribuicaoService.getAllCentrosDistribuicao();
                    for (CentroDistribuicao cd : centros) {
                        System.out.println(cd);
                    }
                    break;
                case 2:
                    System.exit(0);
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}