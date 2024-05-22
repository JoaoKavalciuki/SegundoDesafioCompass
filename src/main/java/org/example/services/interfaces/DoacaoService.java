package org.example.services.interfaces;

import java.util.List;

import org.example.entities.Doacao;

public interface DoacaoService {
    void save(Doacao doacao);

    List<Doacao> listAll();

    List<Doacao> listByCategoria(String categoria);
}
