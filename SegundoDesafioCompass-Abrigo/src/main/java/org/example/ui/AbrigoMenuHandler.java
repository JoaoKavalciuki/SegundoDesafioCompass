package org.example.ui;

import org.example.entity.Abrigo;
import org.example.service.AbrigoService;

import java.util.Scanner;

public class AbrigoMenuHandler {

    private final AbrigoService abrigoService;
    private final Scanner scanner;

    public AbrigoMenuHandler(AbrigoService abrigoService) {
        this.abrigoService = abrigoService;
        this.scanner = new Scanner(System.in);

    }

    public void displayMenu() {
        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Cadastrar Abrigo");
            System.out.println("2. Listar Abrigos");
            System.out.println("3. Atualizar Abrigo");
            System.out.println("4. Deletar Abrigo");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            int option = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (option) {
                case 1:
                    abrigoService.createAbrigo(new Abrigo());
                    break;
                case 2:
                    abrigoService.listarAbrigos();
                    break;
                case 3:
                    System.out.print("ID do Abrigo a ser atualizado: ");
                    Long id = scanner.nextLong();
                    scanner.nextLine();
                    abrigoService.updateAbrigo(id);
                    break;
                case 4:
                    System.out.print("ID do Abrigo a ser deletado: ");
                    id = scanner.nextLong();
                    scanner.nextLine();
                    abrigoService.deleteAbrigo(id);
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

}
