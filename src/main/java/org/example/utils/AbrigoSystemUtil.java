package org.example.utils;

import org.example.entities.Abrigo;
import org.example.entities.EstoqueAbrigo;
import org.example.services.AbrigoServiceImpl;
import org.example.services.EstoqueAbrigoServiceImpl;

import java.util.List;
import java.util.Scanner;

public class AbrigoSystemUtil {
    private final AbrigoServiceImpl abrigoService;
    private EstoqueAbrigoServiceImpl estoqueAbrigoService;
    private Scanner sc = new Scanner(System.in);

    public AbrigoSystemUtil(AbrigoServiceImpl abrigoService, EstoqueAbrigoServiceImpl estoqueAbrigoService) {
        this.abrigoService = abrigoService;
        this.estoqueAbrigoService = estoqueAbrigoService;
    }

    public void create() {
        abrigoService.createAbrigo(new Abrigo());
    }

    public List<Abrigo> listAbrigos() {
        return abrigoService.listarAbrigos();
    }


    public void update() {
        System.out.print("ID do Abrigo a ser atualizado: ");
        Long id = sc.nextLong();
        sc.nextLine();
        abrigoService.updateAbrigo(id);
    }

    public void delete() {
        System.out.print("ID do Abrigo a ser deletado: ");
        Long id = sc.nextLong();
        sc.nextLine();
        abrigoService.deleteAbrigo(id);
    }

    public void listAbrigoEstoque() {
        System.out.print("ID do Abrigo: ");
        Long id = sc.nextLong();
        sc.nextLine();
        estoqueAbrigoService.listarEstoquePorAbrigo(id);
    }

    public void filtroAbrigo() {
        int tipo = 0;
        while (tipo != 7) {
            System.out.println("Filtro de listagem: ");
            System.out.println("1 - Capacidade Crescente");
            System.out.println("2 - Capacidade Decrescente");
            System.out.println("3 - Ocupação Crescente");
            System.out.println("4 - Ocupação Decrescente");
            System.out.println("5 - Itens Recebidos Crescente");
            System.out.println("6 - Itens Recebidos Decrescente");
            System.out.println("7 - Voltar ao menu");
            System.out.print("Digite o número da operação desejada: ");

            tipo = sc.nextInt();
            sc.nextLine();

            switch (tipo) {
                case 1:
                    List<Abrigo> abrigosCapacidadeAsc = abrigoService.findByOrderByCapacidadeAsc();
                    if (abrigosCapacidadeAsc == null || abrigosCapacidadeAsc.isEmpty()) {
                        System.out.println("Nenhum abrigo encontrado.");
                    } else {
                        for (Abrigo abrigo : abrigosCapacidadeAsc) {
                            System.out.println(abrigo);
                        }
                    }
                    break;
                case 2:
                    List<Abrigo> abrigosCapacidadeDesc = abrigoService.findByOrderByCapacidadeDesc();
                    if (abrigosCapacidadeDesc == null || abrigosCapacidadeDesc.isEmpty()) {
                        System.out.println("Nenhum abrigo encontrado.");
                    } else {
                        for (Abrigo abrigo : abrigosCapacidadeDesc) {
                            System.out.println(abrigo);
                        }
                    }
                    break;
                case 3:
                    List<Abrigo> abrigosOcupacaoAsc = abrigoService.findByOrderByOcupacaoAsc();
                    if (abrigosOcupacaoAsc == null || abrigosOcupacaoAsc.isEmpty()) {
                        System.out.println("Nenhum abrigo encontrado.");
                    } else {
                        for (Abrigo abrigo : abrigosOcupacaoAsc) {
                            System.out.println(abrigo);
                        }
                    }
                    break;
                case 4:
                    List<Abrigo> abrigosOcupacaoDesc = abrigoService.findByOrderByOcupacaoDesc();
                    if (abrigosOcupacaoDesc == null || abrigosOcupacaoDesc.isEmpty()) {
                        System.out.println("Nenhum abrigo encontrado.");
                    } else {
                        for (Abrigo abrigo : abrigosOcupacaoDesc) {
                            System.out.println(abrigo);
                        }
                    }
                    break;
                case 5:
                    List<EstoqueAbrigo> estoqueAbrigosRecebidosAsc = abrigoService.findByQuantidadeRecebidaAsc();
                    if (estoqueAbrigosRecebidosAsc == null || estoqueAbrigosRecebidosAsc.isEmpty()) {
                        System.out.println("Nenhum abrigo encontrado.");
                    } else {
                        for (EstoqueAbrigo estoqueAbrigo : estoqueAbrigosRecebidosAsc) {
                            System.out.println("ID: " + estoqueAbrigo.getAbrigo().getId() +
                                    ", Nome: " + estoqueAbrigo.getAbrigo().getNome() +
                                    ", Itens Recebidos: " + estoqueAbrigo.getQuantidade() +
                                    ", Tipo do Item: " + estoqueAbrigo.getItem().getItemTipo());
                        }
                    }
                    break;
                case 6:
                    List<EstoqueAbrigo> estoqueAbrigosRecebidosDesc = abrigoService.findByQuantidadeRecebidaDesc();
                    if (estoqueAbrigosRecebidosDesc == null || estoqueAbrigosRecebidosDesc.isEmpty()) {
                        System.out.println("Nenhum abrigo encontrado.");
                    } else {
                        for (EstoqueAbrigo estoqueAbrigo : estoqueAbrigosRecebidosDesc) {
                            System.out.println("ID: " + estoqueAbrigo.getAbrigo().getId() +
                                    ", Nome: " + estoqueAbrigo.getAbrigo().getNome() +
                                    ", Itens Recebidos: " + estoqueAbrigo.getQuantidade() +
                                    ", Tipo do Item: " + estoqueAbrigo.getItem().getItemTipo());
                        }
                    }
                    break;
                case 7:
                    break;
                default:
                    System.out.println("Código desconhecido");
                    continue;
            }
        }
    }
}
