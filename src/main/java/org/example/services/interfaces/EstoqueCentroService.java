package org.example.services.interfaces;

import org.example.entities.EstoqueCentro;

import java.util.List;
import java.util.Optional;

public interface EstoqueCentroService {

    void save(EstoqueCentro estoqueCentro);

    Optional<EstoqueCentro> findByCDeItem(Long cdID, Long itemID);

    List<EstoqueCentro> findEstoquesByItemTipo(String tipo);

    void reduzirEstoque(Long centroId, Long itemId, int quantidade);

}
