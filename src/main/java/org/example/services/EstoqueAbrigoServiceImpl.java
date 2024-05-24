package org.example.services;

import org.example.entities.EstoqueAbrigo;
import org.example.exceptions.ResourceNotFoundException;
import org.example.repositories.EstoqueAbrigoRepository;
import org.example.services.interfaces.EstoqueAbrigoService;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class EstoqueAbrigoServiceImpl implements EstoqueAbrigoService {

    private EntityManager em;
    private EstoqueAbrigoRepository estoqueAbrigoRepository;
    private Scanner scanner;

    public EstoqueAbrigoServiceImpl(EntityManager em, Scanner scanner) {
        this.em = em;
        this.estoqueAbrigoRepository = new EstoqueAbrigoRepository(em);
        this.scanner = scanner;
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
}
