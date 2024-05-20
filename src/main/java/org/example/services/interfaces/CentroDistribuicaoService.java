package org.example.services.interfaces;

import java.util.List;

import org.example.entities.CentroDistribuicao;

public interface CentroDistribuicaoService {

    void save(CentroDistribuicao centroDistribuicao);

    List<CentroDistribuicao> findAll();

    CentroDistribuicao findById(int id);
}
