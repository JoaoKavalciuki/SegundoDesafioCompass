package org.example.services.interfaces;

import org.example.entities.Abrigo;

import java.util.List;

public interface AbrigoService{
    void createAbrigo(Abrigo abrigo);
    List<Abrigo> getAllAbrigos();
    Abrigo getAbrigo(Long id);
    void listarAbrigos();
    void updateAbrigo(Long id);
    void deleteAbrigo(Long id);
}
