package org.example.utils;

import org.example.entities.Abrigo;
import org.example.services.AbrigoServiceImpl;
import org.example.services.EstoqueAbrigoServiceImpl;
import org.example.services.interfaces.EstoqueAbrigoService;

import java.util.Scanner;

public class AbrigoSystemUtil {
    private final AbrigoServiceImpl abrigoService;
    private EstoqueAbrigoServiceImpl estoqueAbrigoService;
    private Scanner sc = new Scanner(System.in);

    public AbrigoSystemUtil(AbrigoServiceImpl abrigoService, EstoqueAbrigoServiceImpl estoqueAbrigoService) {
        this.abrigoService = abrigoService;
        this.estoqueAbrigoService = estoqueAbrigoService;
    }

    public void create(){
        abrigoService.createAbrigo(new Abrigo());
    }

    public void listAbrigos(){
        abrigoService.listarAbrigos();
    }
    public void update (){
        System.out.print("ID do Abrigo a ser atualizado: ");
        Long id = sc.nextLong();
        sc.nextLine();
        abrigoService.updateAbrigo(id);
    }

    public void delete(){
        System.out.print("ID do Abrigo a ser deletado: ");
        Long id = sc.nextLong();
        sc.nextLine();
        abrigoService.deleteAbrigo(id);
    }

    public void listAbrigoEstoque(){
        System.out.print("ID do Abrigo: ");
        Long id = sc.nextLong();
        sc  .nextLine();
        estoqueAbrigoService.listarEstoquePorAbrigo(id);
    }
}
