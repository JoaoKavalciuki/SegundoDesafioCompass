package org.example.utils;

import org.example.entities.*;
import org.example.entities.enums.StatusPedido;
import org.example.exceptions.ResourceNotFoundException;
import org.example.repositories.AbrigoRepository;
import org.example.repositories.CentroDistribuicaoRepository;
import org.example.repositories.EstoqueCentroRepository;
import org.example.repositories.ItemRepository;
import org.example.services.interfaces.*;

import java.util.*;

public class PedidoSystemUtil {
    Scanner sc = new Scanner(System.in);

    private AbrigoService abrigoService;
    private ItemService itemService;

    private PedidoService pedidoService;
    private CentroDistribuicaoService centroDistribuicaoService;
    private EstoqueCentroService estoqueCentroService;

    public PedidoSystemUtil(AbrigoService abrigoService, ItemService itemService, PedidoService pedidoService,
                            CentroDistribuicaoService centroDistribuicaoService, EstoqueCentroService estoqueCentroService) {
        this.abrigoService = abrigoService;
        this.itemService = itemService;
        this.pedidoService = pedidoService;
        this.centroDistribuicaoService = centroDistribuicaoService;
        this.estoqueCentroService = estoqueCentroService;
    }

    public void fazerDoacao(){
        System.out.print("Informe o ID do abrigo que será feita o pedido: ");
        Long id = sc.nextLong();
        sc.nextLine();

        Abrigo abrigo = abrigoService.getAbrigo(id);
        System.out.println();

        System.out.println("Categorias:\n1 - Roupas.\n2 - Produtos de Higiene\n3 - Alimentos");
        System.out.print("Digite qual a categoria do item do pedido: ");
        String categoria = sc.nextLine();

        List<Item> items = itemService.findByCategoria(categoria);

        for(Item item : items){
            System.out.println(item);
        }

        //Fazer tratamento de id errado

        System.out.println();

        System.out.print("Digite o id do item que deseja fazer a doação: ");
        Integer idItem = sc.nextInt();
        sc.nextLine();

        Item itemDoado = items.get(idItem-1);

        System.out.print("Informe a quantidade de " + itemDoado.getItemTipo() + " de genero "
                + itemDoado.getGenero()  + " e tamanho " + itemDoado.getTamanho() + " a serem doadas(os): ");

        Integer quantidade = sc.nextInt();
        sc.nextLine();

        while (quantidade <= 0){
            System.out.print("A quantidade tem que ser maior que zero, informe uma quantidade válida: ");
            quantidade = sc.nextInt();
            sc.nextLine();
        }

        Pedido pedido = new Pedido(abrigo, StatusPedido.PENDENTE, null, itemDoado, quantidade);

        System.out.println("Centros que tem o item do pedido");

        List<EstoqueCentro> estoques = estoqueCentroService.findEstoquesByItemTipo(itemDoado.getItemTipo());

        for(EstoqueCentro estoque: estoques){
            System.out.println(estoque);
        }

        System.out.println("Informe o id do centro que o pedido vaii ser enviado: ");
        String[] centrosIDsResposta = sc.nextLine().split(" ");
        Long[] ids = converteInputArrayStringParaArrayLong(centrosIDsResposta);

        List<CentroDistribuicao> centrosQueReceberamPedido = new ArrayList<>();

        salvarPedido(pedido, ids, centrosQueReceberamPedido );

    }

    private Long[] converteInputArrayStringParaArrayLong(String[] array){
        Long[] ids = new Long[array.length];

        for(int i = 0; i<array.length; i++){
            ids[i] = Long.parseLong(array[i]);
        }
        return  ids;
    }

    private void salvarPedido(Pedido pedido, Long[] ids, List<CentroDistribuicao> pedidosEnviados){
        for(int i = 0; i<ids.length; i++){
            CentroDistribuicao centroDistribuicao = centroDistribuicaoService.findById(ids[i]);

            pedidosEnviados.add(centroDistribuicao);

        }
        pedido.getCentrosDeDistribuicao().addAll(pedidosEnviados);

        pedidosEnviados.forEach(centroDistribuicao -> centroDistribuicao.getPedidos().add(pedido));

        pedidoService.savePedido(pedido);
        System.out.println("Orderm de pedido enviada com sucesso");

    }
}
