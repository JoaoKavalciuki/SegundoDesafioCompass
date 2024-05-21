package org.example.service;

import org.example.entity.Abrigo;

import java.time.Instant;
import org.example.entity.Item;
import org.example.entity.Pedido;
import org.example.entity.PedidoItem;
import org.example.entity.enums.StatusPedido;
import org.example.exceptions.*;
import org.example.repositorys.AbrigoRepository;
import org.example.repositorys.ItemRepository;
import org.example.repositorys.PedidoRepository;

import java.util.*;

public class AbrigoService {

    private final AbrigoRepository abrigoRepository;
    private final ItemRepository itemRepository;
    private final PedidoRepository pedidoRepository;
    private final Scanner scanner;


    public AbrigoService() {
        this.abrigoRepository = new AbrigoRepository();
        this.pedidoRepository = new PedidoRepository();
        this.itemRepository = new ItemRepository();
        this.scanner = new Scanner(System.in);

    }

    public void createAbrigo(Abrigo abrigo) {
        try {
            System.out.print("Nome: ");
            abrigo.setNome(scanner.nextLine());
            System.out.print("Endereço: ");
            abrigo.setEndereco(scanner.nextLine());
            System.out.print("Responsável: ");
            abrigo.setResponsavel(scanner.nextLine());
            System.out.print("Telefone: ");
            abrigo.setTelefone(scanner.nextLine());
            System.out.print("Email: ");
            abrigo.setEmail(scanner.nextLine());
            System.out.print("Capacidade: ");
            abrigo.setCapacidade(scanner.nextInt());
            System.out.print("Ocupação: ");
            abrigo.setOcupacao(scanner.nextDouble());
            scanner.nextLine();  // Consume newline

            validateAbrigo(abrigo);
            abrigoRepository.save(abrigo);
            System.out.println("Abrigo cadastrado com sucesso!");
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Abrigo> getAllAbrigos() {
        return abrigoRepository.findAll();
    }

    public void listarAbrigos() {
        List<Abrigo> abrigos = getAllAbrigos();
        for (Abrigo a : abrigos) {
            System.out.println(a);
        }
    }

    public Abrigo getAbrigo(Long id) {
        Abrigo abrigo = abrigoRepository.findById(id);
        if (abrigo == null) {
            throw new ResourceNotFoundException("Abrigo não encontrado com ID: " + id);
        }
        return abrigo;
    }

    public void updateAbrigo(Long id) {
        Abrigo existingAbrigo = getAbrigo(id);
        if (existingAbrigo != null) {
            try {
                System.out.print("Nome (" + existingAbrigo.getNome() + "): ");
                String nome = scanner.nextLine();
                if (!nome.isEmpty()) {
                    existingAbrigo.setNome(nome);
                }
                System.out.print("Endereço (" + existingAbrigo.getEndereco() + "): ");
                String endereco = scanner.nextLine();
                if (!endereco.isEmpty()) {
                    existingAbrigo.setEndereco(endereco);
                }
                System.out.print("Responsável (" + existingAbrigo.getResponsavel() + "): ");
                String responsavel = scanner.nextLine();
                if (!responsavel.isEmpty()) {
                    existingAbrigo.setResponsavel(responsavel);
                }
                System.out.print("Telefone (" + existingAbrigo.getTelefone() + "): ");
                String telefone = scanner.nextLine();
                if (!telefone.isEmpty()) {
                    existingAbrigo.setTelefone(telefone);
                }
                System.out.print("Email (" + existingAbrigo.getEmail() + "): ");
                String email = scanner.nextLine();
                if (!email.isEmpty()) {
                    existingAbrigo.setEmail(email);
                }
                System.out.print("Capacidade (" + existingAbrigo.getCapacidade() + "): ");
                String capacidadeStr = scanner.nextLine();
                if (!capacidadeStr.isEmpty()) {
                    int capacidade = Integer.parseInt(capacidadeStr);
                    existingAbrigo.setCapacidade(capacidade);
                }
                System.out.print("Ocupação (" + existingAbrigo.getOcupacao() + "): ");
                String ocupacaoStr = scanner.nextLine();
                if (!ocupacaoStr.isEmpty()) {
                    double ocupacao = Double.parseDouble(ocupacaoStr);
                    existingAbrigo.setOcupacao(ocupacao);
                }

                validateAbrigo(existingAbrigo);
                abrigoRepository.update(existingAbrigo);
                System.out.println("Abrigo atualizado com sucesso!");
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Abrigo não encontrado!");
        }
    }

    public void deleteAbrigo(Long id) {
        abrigoRepository.delete(id);
        System.out.println("Abrigo deletado com sucesso!");
    }


    private void validateAbrigo(Abrigo abrigo) {
        if (abrigo.getNome() == null || abrigo.getNome().isEmpty() ||
                abrigo.getEndereco() == null || abrigo.getEndereco().isEmpty() ||
                abrigo.getResponsavel() == null || abrigo.getResponsavel().isEmpty() ||
                abrigo.getTelefone() == null || abrigo.getTelefone().isEmpty() ||
                abrigo.getEmail() == null || abrigo.getEmail().isEmpty() ||
                abrigo.getOcupacao() < 0.0 || abrigo.getOcupacao() > 100.0) {
            throw new ValidationException("Dados do abrigo são inválidos!");
        }
    }

    public void listarInformacoesPorAbrigo(Long abrigoId) {
        Abrigo abrigo = abrigoRepository.findById(abrigoId);
        if (abrigo == null) {
            throw new ResourceNotFoundException("Abrigo não encontrado com ID: " + abrigoId);
        }

        System.out.println("Abrigo:");
        System.out.println("ID: " + abrigo.getId());
        System.out.println("Nome: " + abrigo.getNome());
        System.out.println("Endereço: " + abrigo.getEndereco());
        System.out.println("Responsável: " + abrigo.getResponsavel());
        System.out.println("Telefone: " + abrigo.getTelefone());
        System.out.println("Email: " + abrigo.getEmail());
        System.out.println("Capacidade: " + abrigo.getCapacidade());
        System.out.println("Ocupação: " + abrigo.getOcupacao());

        List<Pedido> pedidos = pedidoRepository.findByAbrigo(abrigo);
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido encontrado para este abrigo.");
        } else {
            System.out.println("Pedidos:");
            for (Pedido pedido : pedidos) {
                System.out.println("  Pedido ID: " + pedido.getId());
                System.out.println("  Status: " + pedido.getStatusPedido());
                System.out.println("  Motivo da Recusa: " + (pedido.getMotivoRecusa() == null ? "N/A" : pedido.getMotivoRecusa()));

                List<PedidoItem> itens = pedido.getItens();
                if (itens.isEmpty()) {
                    System.out.println("    Nenhum item encontrado para este pedido.");
                } else {
                    System.out.println("    Itens:");
                    for (PedidoItem item : itens) {
                        System.out.println("      Item: " + item.getItem().getItem());
                        System.out.println("      Categoria: " + item.getItem().getCategoria());
                        System.out.println("      Gênero: " + item.getItem().getGenero());
                        System.out.println("      Tamanho: " + item.getItem().getTamanho());
                        System.out.println("      Quantidade: " + item.getQuantidade());
                    }
                }
            }
        }
    }

    public void criarPedidoParaAbrigo(Long abrigoId) {
        Abrigo abrigo = abrigoRepository.findById(abrigoId);
        if (abrigo == null) {
            throw new ResourceNotFoundException("Abrigo não encontrado com ID: " + abrigoId);
        }

        Pedido pedido = new Pedido();
        pedido.setAbrigo(abrigo);
        pedido.setStatusPedido(StatusPedido.PENDENTE);

        // Defina a data do pedido como a data atual
        pedido.setDataPedido(Instant.now());

        // Agora, você precisa associar os itens ao pedido
        List<Item> itensDisponiveis = itemRepository.findAll();
        List<PedidoItem> pedidoItens = new ArrayList<>();

        while (true) {
            System.out.println("Itens disponíveis:");
            for (Item item : itensDisponiveis) {
                System.out.println(item.getId() + ": " + item.getItem() + " (" + item.getCategoria() + ")");
            }

            System.out.print("Digite o ID do item para adicionar ao pedido (ou 0 para terminar): ");
            Long itemId = scanner.nextLong();
            scanner.nextLine();

            if (itemId == 0) break;

            Item item = itemRepository.findById(itemId);
            if (item == null) {
                System.out.println("Item não encontrado com ID: " + itemId);
                continue;
            }

            System.out.print("Digite a quantidade: ");
            int quantidade = scanner.nextInt();
            scanner.nextLine(); // Consumir nova linha

            PedidoItem pedidoItem = new PedidoItem();
            pedidoItem.setPedido(pedido);
            pedidoItem.setItem(item);
            pedidoItem.setQuantidade(quantidade);
            pedidoItens.add(pedidoItem);
        }

        if (!pedidoItens.isEmpty()) {
            // Associe os itens ao pedido
            pedido.setItens(pedidoItens);

            // Salve o pedido no banco de dados
            pedidoRepository.save(pedido);
            System.out.println("Pedido criado com sucesso!");
        } else {
            System.out.println("Nenhum item foi adicionado ao pedido. Pedido não foi criado.");
        }
    }

    private void close() {
        abrigoRepository.close();
        pedidoRepository.close();
        itemRepository.close();
    }
}

