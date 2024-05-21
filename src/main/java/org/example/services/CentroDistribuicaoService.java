package org.example.services;


import org.example.entitys.CentroDistribuicao;
import org.example.repositorys.CentroDistribuicaoRepository;

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
