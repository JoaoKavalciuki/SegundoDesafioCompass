package org.example.services;

import org.example.entities.Abrigo;
import org.example.entities.EstoqueAbrigo;
import org.example.exceptions.ResourceNotFoundException;
import org.example.exceptions.ValidationException;
import org.example.repositories.AbrigoRepository;
import org.example.services.interfaces.AbrigoService;

import jakarta.persistence.EntityManager;
import org.example.services.interfaces.EstoqueAbrigoService;
import org.example.utils.PedidoSystemUtil;

import java.util.List;
import java.util.Scanner;

public class AbrigoServiceImpl implements AbrigoService {

    private EntityManager em;
    private AbrigoRepository abrigoRepository;
    private Scanner sc;
    private EstoqueAbrigoService estoqueAbrigoService;
    private PedidoSystemUtil pedidoSystemUtil;
    private AbrigoRepository estoqueAbrigoRepository;

    public AbrigoServiceImpl(EntityManager em, Scanner sc, EstoqueAbrigoService estoqueAbrigoService) {
        this.em = em;
        this.abrigoRepository = new AbrigoRepository(em);
        this.sc = sc;
        this.estoqueAbrigoService = estoqueAbrigoService;
    }

    public void createAbrigo(Abrigo abrigo) {
        boolean isValid = false;
        while (!isValid) {
            // Validar Nome
            boolean isNomeValid = false;
            while (!isNomeValid) {
                System.out.print("Nome: ");
                String nome = sc.nextLine();
                if (nome.trim().isEmpty() || nome.matches(".*\\d.*")) {
                    System.out.println("Nome não pode estar vazio ou conter números!");
                } else {
                    abrigo.setNome(nome);
                    isNomeValid = true;
                }
            }

            // Validar Endereço
            boolean isEnderecoValid = false;
            while (!isEnderecoValid) {
                System.out.print("Endereço: ");
                String endereco = sc.nextLine();
                if (endereco.trim().isEmpty()) {
                    System.out.println("Endereço não pode estar vazio!");
                } else {
                    abrigo.setEndereco(endereco);
                    isEnderecoValid = true;
                }
            }

            if (abrigoRepository.existsByNomeAndEndereco(abrigo.getNome(), abrigo.getEndereco())) {
                System.out.println("Abrigo já cadastrado com os mesmos dados!");
                continue;
            }

            // Validar Responsável
            boolean isResponsavelValid = false;
            while (!isResponsavelValid) {
                System.out.print("Responsável: ");
                String responsavel = sc.nextLine();
                if (responsavel.trim().isEmpty() || responsavel.matches(".*\\d.*")) {
                    System.out.println("Responsável não pode estar vazio ou conter números!");
                } else {
                    abrigo.setResponsavel(responsavel);
                    isResponsavelValid = true;
                }
            }

            // Validar Telefone
            boolean isTelefoneValid = false;
            while (!isTelefoneValid) {
                System.out.print("Telefone: ");
                String telefone = sc.nextLine();
                if (!telefone.matches("\\d+")) {
                    System.out.println("Telefone contém caracteres inválidos!");
                } else {
                    abrigo.setTelefone(telefone);
                    isTelefoneValid = true;
                }
            }

            // Validar Email
            boolean isEmailValid = false;
            while (!isEmailValid) {
                System.out.print("Email: ");
                String email = sc.nextLine();
                if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                    System.out.println("Email inválido!");
                } else {
                    abrigo.setEmail(email);
                    isEmailValid = true;
                }
            }

            // Validar Capacidade
            boolean isCapacidadeValid = false;
            while (!isCapacidadeValid) {
                System.out.print("Capacidade: ");
                if (!sc.hasNextInt()) {
                    sc.next();
                    System.out.println("Capacidade deve ser um número inteiro!");
                } else {
                    int capacidade = sc.nextInt();
                    if (capacidade <= 0) {
                        System.out.println("Capacidade deve ser um número positivo!");
                    } else {
                        abrigo.setCapacidade(capacidade);
                        isCapacidadeValid = true;
                    }
                }
            }

            // Validar Ocupação
            boolean isOcupacaoValid = false;
            while (!isOcupacaoValid) {
                System.out.print("Ocupação: ");
                if (!sc.hasNextInt()) {
                    sc.next();
                    System.out.println("Ocupação deve ser um número inteiro!");
                } else {
                    int ocupacao = sc.nextInt();
                    if (ocupacao < 0) {
                        System.out.println("Ocupação não pode ser um número negativo!");
                    } else if (ocupacao > abrigo.getCapacidade()) {
                        System.out.println("Ocupação não pode ser maior que a capacidade!");
                    } else {
                        abrigo.setOcupacao(ocupacao);
                        isOcupacaoValid = true;
                    }
                }
                sc.nextLine();
            }

            abrigoRepository.save(abrigo);
            System.out.println("Abrigo cadastrado com sucesso!");
            isValid = true;
        }
    }

    public List<Abrigo> getAllAbrigos() {
        return abrigoRepository.findAll();
    }

    public List<Abrigo> listarAbrigos() {
        List<Abrigo> abrigos = getAllAbrigos();
        return abrigos;
    }

    @Override
    public List<Abrigo> findByOrderByCapacidadeAsc() {
        return abrigoRepository.findByOrderByCapacidadeAsc();
    }

    @Override
    public List<Abrigo> findByOrderByCapacidadeDesc() {
        return abrigoRepository.findByOrderByCapacidadeDesc();
    }

    @Override
    public List<Abrigo> findByOrderByOcupacaoAsc() {
        return abrigoRepository.findByOrderByOcupacaoAsc();
    }

    @Override
    public List<Abrigo> findByOrderByOcupacaoDesc() {
        return abrigoRepository.findByOrderByOcupacaoDesc();
    }

    public List<EstoqueAbrigo> findByQuantidadeRecebidaAsc() {
        return estoqueAbrigoRepository.findByQuantidadeRecebidaAsc();
    }

    public List<EstoqueAbrigo> findByQuantidadeRecebidaDesc() {
        return estoqueAbrigoRepository.findByQuantidadeRecebidaDesc();
    }

    public Abrigo getAbrigo(Long id) {
        Abrigo abrigo = abrigoRepository.findById(id);
        if (abrigo == null) {
            throw new ResourceNotFoundException("Abrigo não encontrado com ID: " + id);
        }
        return abrigo;
    }

    public void updateAbrigo(Long id) {
        Abrigo existingAbrigo = getAbrigo(id);
        if (existingAbrigo != null) {
            try {
                System.out.print("Nome (" + existingAbrigo.getNome() + "): ");
                String nome = sc.nextLine();
                if (!nome.isEmpty()) {
                    existingAbrigo.setNome(nome);
                }
                System.out.print("Endereço (" + existingAbrigo.getEndereco() + "): ");
                String endereco = sc.nextLine();
                if (!endereco.isEmpty()) {
                    existingAbrigo.setEndereco(endereco);
                }
                System.out.print("Responsável (" + existingAbrigo.getResponsavel() + "): ");
                String responsavel = sc.nextLine();
                if (!responsavel.isEmpty()) {
                    existingAbrigo.setResponsavel(responsavel);
                }
                System.out.print("Telefone (" + existingAbrigo.getTelefone() + "): ");
                String telefone = sc.nextLine();
                if (!telefone.isEmpty()) {
                    existingAbrigo.setTelefone(telefone);
                }
                System.out.print("Email (" + existingAbrigo.getEmail() + "): ");
                String email = sc.nextLine();
                if (!email.isEmpty()) {
                    existingAbrigo.setEmail(email);
                }
                System.out.print("Capacidade (" + existingAbrigo.getCapacidade() + "): ");
                String capacidadeStr = sc.nextLine();
                if (!capacidadeStr.isEmpty()) {
                    int capacidade = Integer.parseInt(capacidadeStr);
                    existingAbrigo.setCapacidade(capacidade);
                }
                System.out.print("Ocupação (" + existingAbrigo.getOcupacao() + "): ");
                String ocupacaoStr = sc.nextLine();
                if (!ocupacaoStr.isEmpty()) {
                    double ocupacao = Double.parseDouble(ocupacaoStr);
                    existingAbrigo.setOcupacao(ocupacao);
                }

                validateAbrigo(existingAbrigo);
                abrigoRepository.update(existingAbrigo);
                System.out.println("Abrigo atualizado com sucesso!");
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Abrigo não encontrado!");
        }
    }

    public void deleteAbrigo(Long id) {
        abrigoRepository.delete(id);
        System.out.println("Abrigo deletado com sucesso!");
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
