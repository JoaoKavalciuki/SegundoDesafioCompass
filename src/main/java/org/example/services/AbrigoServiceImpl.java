package org.example.services;

import org.example.entities.Abrigo;
import org.example.exceptions.ValidationException;
import org.example.repositories.AbrigoRepository;
import org.example.services.interfaces.AbrigoService;

import java.util.List;

public class AbrigoServiceImpl implements AbrigoService {

    private AbrigoRepository abrigoRepository;

    public AbrigoServiceImpl(AbrigoRepository abrigoRepository) {
        this.abrigoRepository = abrigoRepository;
    }

    public AbrigoServiceImpl() {

    }

    @Override
    public void createAbrigo(Abrigo abrigo) {
        try {
            validateAbrigo(abrigo);
            abrigoRepository.save(abrigo);
        } catch (ValidationException e) {
            throw new RuntimeException("Erro ao validar o abrigo: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Abrigo> getAllAbrigos() {
        return abrigoRepository.findAll();
    }

    @Override
    public void listarAbrigos() {
        List<Abrigo> abrigos = getAllAbrigos();
        for (Abrigo a : abrigos) {
            System.out.println(a);
        }
    }

    @Override
    public Abrigo getAbrigo(Long id) {
        Abrigo abrigo = abrigoRepository.findById(id);
        if (abrigo == null) {
            throw new RuntimeException("Abrigo não encontrado com ID: " + id);
        }
        return abrigo;
    }

    @Override
    public void updateAbrigo(Long id) {
        Abrigo existingAbrigo = getAbrigo(id);
        if (existingAbrigo != null) {
            abrigoRepository.update(existingAbrigo);
        } else {
            throw new RuntimeException("Abrigo não encontrado!");
        }
    }

    @Override
    public void deleteAbrigo(Long id) {
        abrigoRepository.delete(id);
    }

    private void validateAbrigo(Abrigo abrigo) {
        if (abrigo.getNome() == null || abrigo.getNome().isEmpty() ||
                abrigo.getEndereco() == null || abrigo.getEndereco().isEmpty() ||
                abrigo.getResponsavel() == null || abrigo.getResponsavel().isEmpty() ||
                abrigo.getTelefone() == null || abrigo.getTelefone().isEmpty() ||
                abrigo.getEmail() == null || abrigo.getEmail().isEmpty() ||
                abrigo.getOcupacao() < 0.0 || abrigo.getOcupacao() > 100.0) {
            throw new ValidationException("Dados do abrigo são inválidos!");
        }
    }
}
