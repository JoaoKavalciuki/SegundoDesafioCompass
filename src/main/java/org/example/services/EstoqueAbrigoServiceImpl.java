package org.example.services;

import org.example.entities.EstoqueAbrigo;
import org.example.exceptions.ResourceNotFoundException;
import org.example.repositories.EstoqueAbrigoRepository;
import org.example.services.interfaces.EstoqueAbrigoService;

import java.util.List;
import java.util.Optional;

public class EstoqueAbrigoServiceImpl implements EstoqueAbrigoService {

    private EstoqueAbrigoRepository estoqueAbrigoRepository;

    public EstoqueAbrigoServiceImpl(EstoqueAbrigoRepository estoqueAbrigoRepository) {
        this.estoqueAbrigoRepository = estoqueAbrigoRepository;
    }

    @Override
    public List<EstoqueAbrigo> listarEstoque() {
        return estoqueAbrigoRepository.findAll();
    }

    @Override
    public EstoqueAbrigo obterItemEstoque(Long id) {
        EstoqueAbrigo estoque = estoqueAbrigoRepository.findById(id);
        if (estoque == null) {
            throw new ResourceNotFoundException("Item de estoque não encontrado com ID: " + id);
        }
        return estoque;
    }

    @Override
    public List<EstoqueAbrigo> listarEstoquePorAbrigo(Long abrigoId) {
        List<EstoqueAbrigo> estoques = estoqueAbrigoRepository.findByAbrigoId(abrigoId);
        if (estoques.isEmpty()) {
            System.out.println("Nenhum item de estoque encontrado para o abrigo com ID: " + abrigoId);
        } else {
            System.out.println("Estoque do Abrigo:");
            boolean first = true;
            for (EstoqueAbrigo estoque : estoques) {
                if (!first) {
                    System.out.println();
                } else {
                    first = false;
                }
                System.out.println("Categoria: " + estoque.getItem().getCategoria());
                System.out.println("Tipo: " + estoque.getItem().getItemTipo());
                if (estoque.getItem().getGenero() != null) {
                    System.out.println("Gênero: " + estoque.getItem().getGenero());
                }
                if (estoque.getItem().getTamanho() != null) {
                    System.out.println("Tamanho: " + estoque.getItem().getTamanho());
                }
                System.out.println("Quantidade: " + estoque.getQuantidade());
                System.out.println("");
            }
        }
        return estoques;
    }

    @Override
    public Optional<EstoqueAbrigo> findEstoqueByItemTipo(Long abrigoId, String tipo) {
        if (abrigoId == null) {
            throw new IllegalArgumentException("ID do abrigo não pode ser nulo.");
        }
        if (tipo == null || tipo.isEmpty()) {
            throw new IllegalArgumentException("O tipo do item não pode ser nulo ou vazio.");
        }
        Optional<EstoqueAbrigo> resultado = estoqueAbrigoRepository.findEstoqueByItemTipo(abrigoId, tipo);
        return resultado;
    }

    @Override
    public void updateEstoque(Long abrigoId, Long itemId, int quantidade) {
        if (abrigoId == null || itemId == null) {
            throw new IllegalArgumentException("IDs do abrigo e do item não podem ser nulos.");
        }
        if (quantidade < 0) {
            throw new IllegalArgumentException("A quantidade não pode ser negativa.");
        }
        estoqueAbrigoRepository.updateEstoque(abrigoId, itemId, quantidade);
    }
}
