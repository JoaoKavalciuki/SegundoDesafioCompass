package org.example.utils;

import java.util.List;
import java.util.Scanner;

import org.example.entities.CentroDistribuicao;
import org.example.services.interfaces.CentroDistribuicaoService;

public class CentroSystemUtil {
    private CentroDistribuicaoService centroService;
    private Scanner sc = new Scanner(System.in);

    public CentroSystemUtil(CentroDistribuicaoService centroService) {
        this.centroService = centroService;
    }

    public void listCentros() {
        List<CentroDistribuicao> centros = centroService.findAll();
        for (CentroDistribuicao cd : centros) {
            System.out.println(cd);
        }
    }

    public CentroDistribuicao getCentro() {
        this.listCentros();
        System.out.println("Para qual centro?");
        long centroId = sc.nextLong();
        sc.nextLine();
        return centroService.findById(centroId);
    }
}
