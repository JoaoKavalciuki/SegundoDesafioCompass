package org.example.views;

import java.util.Scanner;
import org.example.entitys.Abrigo;
import org.example.services.AbrigoService;


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
            System.out.println("|5-Listar por ID                 |");
            System.out.println("|6-Criar pedido para Abrigo      |");
            System.out.println("|0-Sair                          |");
            System.out.print("Escolha uma opção: ");
            int choice = scanner.nextInt();

            switch (choice) {
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
                    System.out.print("Digite o ID do abrigo: ");
                    Long idAbrigo = scanner.nextLong();
                    scanner.nextLine(); // Consumir nova linha
                    try {
                        abrigoService.listarInformacoesPorAbrigo(idAbrigo);
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 6:
                    System.out.print("Digite o ID do abrigo: ");
                    Long idPedidoAbrigo = scanner.nextLong();
                    scanner.nextLine();
                    try {
                        abrigoService.criarPedidoParaAbrigo(idPedidoAbrigo);
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 0:
                    System.exit(0);
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}