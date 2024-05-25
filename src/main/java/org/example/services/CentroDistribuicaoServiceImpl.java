package org.example.services;

import java.util.List;

import org.example.entities.CentroDistribuicao;
import org.example.exceptions.ResourceNotFoundException;
import org.example.repositories.CentroDistribuicaoRepository;
import org.example.services.interfaces.CentroDistribuicaoService;

public class CentroDistribuicaoServiceImpl implements CentroDistribuicaoService {

    private CentroDistribuicaoRepository centroDistribuicaoRepository;

    public CentroDistribuicaoServiceImpl() {
        this.centroDistribuicaoRepository = new CentroDistribuicaoRepository();
    }

    public void save(CentroDistribuicao centroDistribuicao) {
        centroDistribuicaoRepository.save(centroDistribuicao);
    }

    public List<CentroDistribuicao> findAll() {
        return centroDistribuicaoRepository.findAll();
    }

    public CentroDistribuicao findById(Long id) {
        CentroDistribuicao centro = centroDistribuicaoRepository.findById(id);
        if (centro == null) {
            throw new ResourceNotFoundException("Centro de distribuição não encontrado com ID: " + id);
        }
        return centro;
    }

}