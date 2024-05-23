package org.example.utils;

import org.example.entities.*;
import org.example.entities.enums.StatusPedido;
import org.example.exceptions.ResourceNotFoundException;
import org.example.repositories.AbrigoRepository;
import org.example.repositories.CentroDistribuicaoRepository;
import org.example.repositories.EstoqueCentroRepository;
import org.example.repositories.ItemRepository;
import org.example.services.interfaces.AbrigoService;
import org.example.services.interfaces.ItemService;
import org.example.services.interfaces.PedidoService;

import java.util.*;

public class PedidoSystemUtil {
    Scanner sc = new Scanner(System.in);

    private AbrigoService abrigoService;
    private ItemService itemService;

    private PedidoService pedidoService;
    public PedidoSystemUtil(AbrigoService abrigoService, ItemService itemService, PedidoService pedidoService) {
        this.abrigoService = abrigoService;
        this.itemService = itemService;
        this. pedidoService = pedidoService;
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

        if(quantidade <= 0){
            throw new InputMismatchException("A quantidade tem de ser maior que zero");
        }

        Pedido pedido = new Pedido(abrigo, StatusPedido.PENDENTE, null, itemDoado, quantidade);

        System.out.println(itemDoado.getItemTipo());

        System.out.println("Centros que tem o item do pedido");

        EstoqueCentroRepository estoqueCentroRepository = new EstoqueCentroRepository();

        List<EstoqueCentro> estoques = estoqueCentroRepository.findEstoquesByItemTipo(itemDoado.getItemTipo());

        for(EstoqueCentro estoque: estoques){
            System.out.println(estoque);
        }

        System.out.println("Informe o id do centro que o pedido vaii ser enviado: ");
        String[] centrosIDsResposta = sc.nextLine().split(" ");
        Long[] ids = new Long[centrosIDsResposta.length];

        for(int i = 0; i<centrosIDsResposta.length; i++){
            ids[i] = Long.parseLong(centrosIDsResposta[i]);
        }

        for(Long idCentros : ids){
            System.out.println(idCentros);
        }

        CentroDistribuicaoRepository centroRepository = new CentroDistribuicaoRepository();


        List<CentroDistribuicao> pedidoEnviadoCentros = new ArrayList<>();

        for(int i = 0; i<ids.length; i++){
            CentroDistribuicao centroDistribuicao = centroRepository.findById(ids[i]);

            pedidoEnviadoCentros.add(centroDistribuicao);

        }
        pedido.getCentrosDeDistribuicao().addAll(pedidoEnviadoCentros);

        pedidoEnviadoCentros.forEach(centroDistribuicao -> centroDistribuicao.getPedidos().add(pedido));

        pedidoService.savePedido(pedido);
        System.out.println("Orderm de pedido enviada com sucesso");

    }
}
