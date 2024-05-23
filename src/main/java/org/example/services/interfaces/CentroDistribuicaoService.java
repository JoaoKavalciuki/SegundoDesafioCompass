package org.example.services.interfaces;

import org.example.entities.CentroDistribuicao;

import java.util.List;

public interface CentroDistribuicaoService {

    void save(CentroDistribuicao centroDistribuicao);

    List<CentroDistribuicao> findAll();

    CentroDistribuicao findById(Long id);
}
