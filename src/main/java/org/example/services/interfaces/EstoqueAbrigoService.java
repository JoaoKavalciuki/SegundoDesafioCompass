package org.example.services.interfaces;

import org.example.entities.EstoqueAbrigo;
import java.util.List;
import java.util.Optional;

public interface EstoqueAbrigoService {
    List<EstoqueAbrigo> listarEstoque();
    EstoqueAbrigo obterItemEstoque(Long id);
    List<EstoqueAbrigo> listarEstoquePorAbrigo(Long abrigoId);

    Optional<EstoqueAbrigo> findEstoqueByItemTipo(Long abrigoId, String tipo);
    void updateEstoque(Long abrigoId, Long itemId, int quantidade);
}
