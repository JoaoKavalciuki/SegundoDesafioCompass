package org.example.utils;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.example.entities.CentroDistribuicao;
import org.example.entities.EstoqueCentro;
import org.example.entities.Pedido;
import org.example.entities.enums.StatusPedido;
import org.example.repositories.EstoqueCentroRepository;
import org.example.services.interfaces.CentroDistribuicaoService;
import org.example.services.interfaces.EstoqueAbrigoService;
import org.example.services.interfaces.EstoqueCentroService;
import org.example.services.interfaces.PedidoService;

public class CentroSystemUtil {
    private CentroDistribuicaoService centroService;
    private PedidoService pedidoService;
    private EstoqueCentroService estoqueCentroService;
    private EstoqueAbrigoService estoqueAbrigoService;
    private Scanner sc = new Scanner(System.in);

    public CentroSystemUtil(CentroDistribuicaoService centroService, PedidoService pedidoService, EstoqueCentroService estoqueCentroService, EstoqueAbrigoService estoqueAbrigoService) {
        this.centroService = centroService;
        this.pedidoService = pedidoService;
        this.estoqueCentroService = estoqueCentroService;
        this.estoqueAbrigoService = estoqueAbrigoService;
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
        System.out.println("Selecione um pedido a ser processado: ");

        int index = sc.nextInt();
        sc.nextLine();

        Pedido pedidoSelecionado = pedidosPendentes.get(index - 1);

        System.out.println("Deseja aceitar (A) ou recusar (R) o pedido selecionado ?");
        String opcao = sc.nextLine();
        switch (opcao) {
            case "A":
                pedidoSelecionado.setStatusPedido(StatusPedido.ACEITO);
                estoqueCentroService.reduzirEstoque(centro.getId(), pedidoSelecionado.getItem().getId(), pedidoSelecionado.getQuantidade());
                estoqueAbrigoService.updateEstoque(pedidoSelecionado.getAbrigo().getId(), pedidoSelecionado.getItem().getId(), pedidoSelecionado.getQuantidade());
                System.out.println("Pedido aceito com sucesso");
                pedidoService.savePedido(pedidoSelecionado);
                break;
            case "R":
                System.out.println("Informe o motivo da recusa:");
                String motivoRecusa = sc.nextLine();
                pedidoSelecionado.setMotivoRecusa(motivoRecusa);
                pedidoSelecionado.setStatusPedido(StatusPedido.RECUSADO);
                pedidoService.savePedido(pedidoSelecionado);
                System.out.println("Pedido recusado com sucesso");
                break;
            default:
                System.out.println("Opção Invalida!!");
                break;
        }
    }
}