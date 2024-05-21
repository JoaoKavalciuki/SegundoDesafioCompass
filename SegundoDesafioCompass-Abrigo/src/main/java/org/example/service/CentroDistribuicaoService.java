package org.example.service;


import org.example.entity.CentroDistribuicao;
import org.example.repository.CentroDistribuicaoRepository;

import java.util.List;

public class CentroDistribuicaoService {

    private CentroDistribuicaoRepository centroDistribuicaoRepository;

    public CentroDistribuicaoService() {
        this.centroDistribuicaoRepository = new CentroDistribuicaoRepository();
    }

    public void saveCentroDistribuicao(CentroDistribuicao centroDistribuicao) {
        centroDistribuicaoRepository.save(centroDistribuicao);
    }

    public List<CentroDistribuicao> getAllCentrosDistribuicao() {
        return centroDistribuicaoRepository.findAll();
    }

}
