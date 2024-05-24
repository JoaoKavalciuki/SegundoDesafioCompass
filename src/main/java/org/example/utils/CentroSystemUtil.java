package org.example.utils;

import java.util.List;
import java.util.Scanner;
import org.example.entities.CentroDistribuicao;
import org.example.entities.Pedido;
import org.example.entities.enums.StatusPedido;
import org.example.services.interfaces.CentroDistribuicaoService;
import org.example.services.interfaces.PedidoService;

public class CentroSystemUtil {
    private CentroDistribuicaoService centroService;
    private PedidoService pedidoService;
    private Scanner sc = new Scanner(System.in);

    public CentroSystemUtil(CentroDistribuicaoService centroService ,PedidoService pedidoService) {
        this.centroService = centroService;
        this.pedidoService = pedidoService;
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

    public void listarPedidosPendentes() {
        CentroDistribuicao centro = getCentro();
        if (centro == null) {
            System.out.println("Centro de distribuição não encontrado.");
            return;
        }

        List<Pedido> pedidosPendentes = pedidoService.listarPedidosPendentesPorCentro(centro.getId());
        if (pedidosPendentes.isEmpty()) {
            System.out.println("Nenhum pedido pendente encontrado para este centro.");
            return;
        }

        System.out.println("Pedidos pendentes para o centro " + centro.getNome() + ":");
        for (Pedido pedido : pedidosPendentes) {
            System.out.println(pedido);
        }
    }

}
