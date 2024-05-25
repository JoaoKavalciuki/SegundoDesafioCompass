package org.example.services;

import org.example.entities.EstoqueCentro;
import org.example.repositories.EstoqueCentroRepository;
import org.example.services.interfaces.EstoqueCentroService;

import java.util.List;
import java.util.Optional;

public class EstoqueCentroServiceImpl implements EstoqueCentroService {

    private final EstoqueCentroRepository estoqueCentroRepository;

    public EstoqueCentroServiceImpl(EstoqueCentroRepository estoqueCentroRepository) {
        this.estoqueCentroRepository = estoqueCentroRepository;
    }

    @Override
    public void save(EstoqueCentro estoqueCentro) {
        if (estoqueCentro == null) {
            throw new IllegalArgumentException("EstoqueCentro não pode ser nulo.");
        }
        estoqueCentroRepository.save(estoqueCentro);
    }

    @Override
    public Optional<EstoqueCentro> findByCDeItem(Long cdID, Long itemID) {
        if (cdID == null || itemID == null) {
            throw new IllegalArgumentException("IDs do centro de distribuição e do item não podem ser nulos.");
        }
        return estoqueCentroRepository.findByCDeItem(cdID, itemID);
    }

    @Override
    public List<EstoqueCentro> findEstoquesByItemTipo(String tipo) {
        if (tipo == null || tipo.isEmpty()) {
            throw new IllegalArgumentException("O tipo do item não pode ser nulo ou vazio.");
        }
        return estoqueCentroRepository.findEstoquesByItemTipo(tipo);
    }

    @Override
    public void reduzirEstoque(Long centroId, Long itemId, int quantidade) {
        if (centroId == null || itemId == null) {
            throw new IllegalArgumentException("IDs do centro de distribuição e do item não podem ser nulos.");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }

        Optional<EstoqueCentro> estoqueOpt = estoqueCentroRepository.findByCDeItem(centroId, itemId);
        if (estoqueOpt.isPresent()) {
            EstoqueCentro estoqueCentro = estoqueOpt.get();
            if (estoqueCentro.getQuantidade() < quantidade) {
                throw new IllegalStateException("Quantidade insuficiente no estoque.");
            }
            estoqueCentro.setQuantidade(estoqueCentro.getQuantidade() - quantidade);
            estoqueCentroRepository.save(estoqueCentro);
        } else {
            throw new IllegalStateException("Estoque não encontrado para o item e centro especificados.");
        }
    }
}
