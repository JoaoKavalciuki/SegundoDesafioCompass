package org.example.view;


import org.example.repository.PedidoRepository;
import org.example.service.AbrigoService;
import org.example.service.PedidoService;

import java.util.Scanner;

public class PedidoView implements View{
    private PedidoService pedidoService;
    private  Scanner scanner;

    private AbrigoService abrigoService;
    public PedidoView() {
        this.pedidoService = new PedidoService(new PedidoRepository());
        this.scanner = new Scanner(System.in);
        this.abrigoService = new AbrigoService();

    }
    @Override
    public void display() {
        System.out.println();
        System.out.print("Informe o ID do abrigo que será feito o pedido: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        abrigoService.getAbrigo(id);

        System.out.println();
    }
}
