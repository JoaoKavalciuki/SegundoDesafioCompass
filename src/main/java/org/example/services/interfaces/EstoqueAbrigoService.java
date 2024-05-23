package org.example.services.interfaces;

import org.example.entities.EstoqueAbrigo;
import java.util.List;

public interface EstoqueAbrigoService {
    List<EstoqueAbrigo> listarEstoque();
    EstoqueAbrigo obterItemEstoque(Long id);
    List<EstoqueAbrigo> listarEstoquePorAbrigo(Long abrigoId);
}
