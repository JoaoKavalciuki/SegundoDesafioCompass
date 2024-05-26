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

    private String regex = "^\\d+$";

    public PedidoSystemUtil(AbrigoService abrigoService, ItemService itemService, PedidoService pedidoService,
                            CentroDistribuicaoService centroDistribuicaoService, EstoqueCentroService estoqueCentroService) {
        this.abrigoService = abrigoService;
        this.itemService = itemService;
        this.pedidoService = pedidoService;
        this.centroDistribuicaoService = centroDistribuicaoService;
        this.estoqueCentroService = estoqueCentroService;
    }

    private List<String> mostrarCategoriasMenu(){
        List<String> categorias = new ArrayList<>(Arrays.asList("Roupas", "Produtos de Higiene", "Alimentos"));

        for(int i =0; i< categorias.size(); i++){
            System.out.println((i+1) + " - " + categorias.get(i));
        }
        System.out.println("4 - Sair");
        return categorias;
    }

    public Abrigo getAbrigo(){
        try{
            System.out.print("Informe o ID do abrigo que será feita o pedido: ");
            Long id = sc.nextLong();
            sc.nextLine();

            Abrigo abrigo = abrigoService.getAbrigo(id);
            return  abrigo;
        } catch (InputMismatchException e){
            throw new InputMismatchException("O ID do abrigo precisa ser um número!");
        }
    }
    public void fazerPedido(){


        Abrigo abrigo = getAbrigo();

        System.out.println();

        List<String> categorias = mostrarCategoriasMenu();
        System.out.println();

        System.out.print("Digite o núumero da categoria do item do pedido: ");
        String numeroCategoriaStr = sc.nextLine();

        while (!numeroCategoriaStr.matches(regex)){
            System.out.print("Digite um numero: ");
            numeroCategoriaStr = sc.nextLine();
        }

        Integer numeroCategoria = Integer.parseInt(numeroCategoriaStr);

        while(numeroCategoria <= 0 || numeroCategoria > categorias.size()+1){
            System.out.print("Selecione uma opção válida: ");
            numeroCategoria = sc.nextInt();
            sc.nextLine();
        }

        if(numeroCategoria == 4){
            return;
        }

        Item itemDoado = getItemDoPedido(numeroCategoria, categorias);
        System.out.print("Informe a quantidade necessária: ");

        String quantidadeStr = sc.nextLine();

        while (!quantidadeStr.matches(regex)){
            System.out.print("Digite um numero: ");
            quantidadeStr = sc.nextLine();
        }

        Integer quantidade = Integer.parseInt(quantidadeStr);

        while (quantidade <= 0){
            System.out.print("A quantidade tem que ser maior que zero, informe uma quantidade válida: ");
            quantidade = sc.nextInt();
            sc.nextLine();
        }

        Pedido pedido = new Pedido(abrigo, StatusPedido.PENDENTE, null, itemDoado, quantidade);

        List<EstoqueCentro> estoques = estoqueCentroService.findEstoquesByItemTipo(itemDoado.getItemTipo());

        if(estoques.isEmpty()){
            System.out.println("Nenhum centro há o item solicitado!");
            return;
        }

        System.out.println("Centros que tem o item do pedido:");

        for(EstoqueCentro estoque: estoques){
            System.out.println(estoque);
        }
        System.out.println();

        System.out.print("Informe os id(s) do centro(s) que o pedido será enviado: ");
        String[] centrosIDsResposta = sc.nextLine().split(" ");

        Long[] ids = converteInputArrayStringParaArrayLong(centrosIDsResposta);

        List<CentroDistribuicao> centrosQueReceberamPedido = new ArrayList<>();

        salvarPedido(pedido, ids, centrosQueReceberamPedido);
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
        System.out.println("Pedido enviada com sucesso");
    }

    private Item getItemDoPedido(Integer numeroCategoria, List<String> categorias){
        List<Item> items = itemService.findByCategoria(categorias.get(numeroCategoria-1));

        for(int i = 0; i<items.size(); i++){
            System.out.println("Item " + (i+1) + ":");
            System.out.println(items.get(i));
        }
        System.out.println();

        System.out.print("Digite o número do item que deseja fazer o pedido: ");
        String itemIdStr = sc.nextLine();

        while (!itemIdStr.matches(regex)){
            System.out.print("Digite um numero: ");
            itemIdStr = sc.nextLine();
        }

        Integer itemId = Integer.parseInt(itemIdStr);

        Item itemDoPedido = items.get(itemId-1);
        return itemDoPedido;
    }
}
