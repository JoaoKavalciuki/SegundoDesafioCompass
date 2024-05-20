package org.example.view;

import org.example.service.AbrigoService;

import java.util.Scanner;

public class CentrosMenuView implements View {

    private final Scanner scanner;
    private final AbrigoMenuView abrigoMenuView;

    public CentrosMenuView() {
        this.scanner = new Scanner(System.in);
        this.abrigoMenuView = new AbrigoMenuView();
    }

    @Override
    public void display() {
        while (true) {
            System.out.println("|# # # # # # #MENU# # # # # # # #|");
            System.out.println("|1-Centros de Distribuição       |");
            System.out.println("|2-Abrigos                       |");
            System.out.println("|3-..............................|");
            System.out.println("|4-..............................|");
            System.out.println("|# # # # # # # # # # # # # # # # |");
            System.out.print("Escolha: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Chamar o menu de Centros de Distribuição
                    break;
                case 2:
                    abrigoMenuView.display();
                    break;
                case 3:
                    // Chamar outra view
                    break;
                case 4:
                    // Chamar outra view
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}


