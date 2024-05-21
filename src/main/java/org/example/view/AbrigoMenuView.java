package org.example.view;

import java.util.Scanner;
import org.example.entity.Abrigo;
import org.example.service.AbrigoService;


public class AbrigoMenuView implements View {

    private final Scanner scanner;
    private final AbrigoService abrigoService;

    public AbrigoMenuView() {
        this.abrigoService = new AbrigoService();
        this.scanner = new Scanner(System.in);

    }

    @Override
    public void display() {
        while (true) {
            System.out.println("|# # # # # # #Abrigo# # # # # # #|");
            System.out.println("|1-Cadastrar Abrigo              |");
            System.out.println("|2-Listar Abrigos                |");
            System.out.println("|3-Atualizar Abrigo              |");
            System.out.println("|4-Deletar Abrigo                |");
            System.out.println("|5-Sair                          |");
            System.out.print("Escolha uma opção: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    abrigoService.createAbrigo(new Abrigo());
                    break;
                case 2:
                    abrigoService.listarAbrigos();
                    System.out.println();
                    System.out.print("Deseja fazer uma Pedido de itens para algum abrigo (S/N)?");
                    char resposta = scanner.next().charAt(0);
                    scanner.nextLine();
                    while(resposta != 'S' && resposta != 'N'){
                        System.out.println("A resposta deve ser S ou N");
                        resposta = scanner.next().charAt(0);
                        scanner.nextLine();
                    }
                    if(resposta == 'S'){
                        PedidoView pedidoView = new PedidoView();
                        pedidoView.display();
                    }
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