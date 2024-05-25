package org.example.utils;

import org.example.entities.CentroDistribuicao;
import org.example.entities.Pedido;
import org.example.entities.enums.StatusPedido;
import org.example.exceptions.IllegalEntryException;
import org.example.services.interfaces.CentroDistribuicaoService;
import org.example.services.interfaces.EstoqueAbrigoService;
import org.example.services.interfaces.EstoqueCentroService;
import org.example.services.interfaces.PedidoService;

import java.util.List;
import java.util.Scanner;

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
        for (int i = 0; i < pedidosPendentes.size(); i++) {
            System.out.println((i + 1) + ". " + pedidosPendentes.get(i));
        }

        int index = -1;
        Scanner sc = new Scanner(System.in);
        while (index < 1 || index > pedidosPendentes.size()) {
            System.out.println("Selecione um pedido a ser processado (1 - " + pedidosPendentes.size() + "): ");
            try {
                index = sc.nextInt();
                sc.nextLine();
            } catch (IllegalEntryException e) {
                sc.nextLine();
                throw new IllegalEntryException("Entrada inválida! Por favor, insira um número válido.");
            }
        }

        Pedido pedidoSelecionado = pedidosPendentes.get(index - 1);

        String opcao = "";
        while (!opcao.equals("A") && !opcao.equals("R") && !opcao.equals("S")) {
            System.out.println("Deseja aceitar (A), recusar (R) o pedido selecionado? | sair (S)");
            opcao = sc.nextLine().toUpperCase();
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
                case "S":
                    System.out.println("Operação cancelada pelo usuário.");
                    break;
                default:
                    throw new IllegalEntryException("Opção inválida! Por favor, selecione A para aceitar, R para recusar ou S para sair.");
            }
        }
    }
}