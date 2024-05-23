package org.example.services.interfaces;

import org.example.entities.Doacao;

import java.util.List;

public interface DoacaoService {
    void save(Doacao doacao);

    List<Doacao> listAll();

    List<Doacao> listByCategoria(String categoria);

    void update(int novaQuantidade, Long id);

    Doacao findById(Long id);

    void deleteById(Long id);
}
