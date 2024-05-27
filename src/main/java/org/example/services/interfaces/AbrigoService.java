package org.example.services.interfaces;

import org.example.entities.Abrigo;
import org.example.entities.EstoqueAbrigo;

import java.util.List;

public interface AbrigoService{
    void createAbrigo(Abrigo abrigo);
    List<Abrigo> getAllAbrigos();
    Abrigo getAbrigo(Long id);
    List<Abrigo> listarAbrigos();
    void updateAbrigo(Long id);
    void deleteAbrigo(Long id);
    List<Abrigo> findByOrderByCapacidadeAsc();
    List<Abrigo> findByOrderByCapacidadeDesc();
    List<Abrigo> findByOrderByOcupacaoAsc();
    List<Abrigo> findByOrderByOcupacaoDesc();
    List<EstoqueAbrigo> findByQuantidadeRecebidaAsc();
    List<EstoqueAbrigo> findByQuantidadeRecebidaDesc();
}
