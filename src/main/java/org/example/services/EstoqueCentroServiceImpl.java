package org.example.services;

import org.example.entities.CentroDistribuicao;
import org.example.entities.EstoqueCentro;
import org.example.entities.Pedido;
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

    }

    @Override
    public Optional<EstoqueCentro> findByCDeItem(Long cdID, Long itemID) {
        return Optional.empty();
    }

    @Override
    public List<EstoqueCentro> findEstoquesByItemTipo(String tipo) {
        return List.of();
    }

    @Override
    public void reduzirEstoque(Long centroId, Long itemId, int quantidade) {
        estoqueCentroRepository.updateEstoque(centroId, itemId, quantidade);
    }

}
