package org.example.service;

import org.example.entity.Abrigo;
import org.example.exceptions.*;
import org.example.repository.AbrigoRepository;
import java.util.Scanner;

import java.util.List;

public class AbrigoService {

    private AbrigoRepository repository;
    private Scanner scanner;

    public AbrigoService() {
        this.repository = new AbrigoRepository();
        this.scanner = new Scanner(System.in);
    }

    public void createAbrigo(Abrigo abrigo) {
        try {
            System.out.print("Nome: ");
            abrigo.setNome(scanner.nextLine());
            System.out.print("Endereço: ");
            abrigo.setEndereco(scanner.nextLine());
            System.out.print("Responsável: ");
            abrigo.setResponsavel(scanner.nextLine());
            System.out.print("Telefone: ");
            abrigo.setTelefone(scanner.nextLine());
            System.out.print("Email: ");
            abrigo.setEmail(scanner.nextLine());
            System.out.print("Capacidade: ");
            abrigo.setCapacidade(scanner.nextInt());
            System.out.print("Ocupação: ");
            abrigo.setOcupacao(scanner.nextDouble());
            scanner.nextLine();  // Consume newline

            validateAbrigo(abrigo);
            repository.save(abrigo);
            System.out.println("Abrigo cadastrado com sucesso!");
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Abrigo> getAllAbrigos() {
        return repository.findAll();
    }

    public void listarAbrigos() {
        List<Abrigo> abrigos = getAllAbrigos();
        for (Abrigo a : abrigos) {
            System.out.println(a);
        }
    }

    public Abrigo getAbrigo(Long id) {
        Abrigo abrigo = repository.findById(id);
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
                String nome = scanner.nextLine();
                if (!nome.isEmpty()) {
                    existingAbrigo.setNome(nome);
                }
                System.out.print("Endereço (" + existingAbrigo.getEndereco() + "): ");
                String endereco = scanner.nextLine();
                if (!endereco.isEmpty()) {
                    existingAbrigo.setEndereco(endereco);
                }
                System.out.print("Responsável (" + existingAbrigo.getResponsavel() + "): ");
                String responsavel = scanner.nextLine();
                if (!responsavel.isEmpty()) {
                    existingAbrigo.setResponsavel(responsavel);
                }
                System.out.print("Telefone (" + existingAbrigo.getTelefone() + "): ");
                String telefone = scanner.nextLine();
                if (!telefone.isEmpty()) {
                    existingAbrigo.setTelefone(telefone);
                }
                System.out.print("Email (" + existingAbrigo.getEmail() + "): ");
                String email = scanner.nextLine();
                if (!email.isEmpty()) {
                    existingAbrigo.setEmail(email);
                }
                System.out.print("Capacidade (" + existingAbrigo.getCapacidade() + "): ");
                String capacidadeStr = scanner.nextLine();
                if (!capacidadeStr.isEmpty()) {
                    int capacidade = Integer.parseInt(capacidadeStr);
                    existingAbrigo.setCapacidade(capacidade);
                }
                System.out.print("Ocupação (" + existingAbrigo.getOcupacao() + "): ");
                String ocupacaoStr = scanner.nextLine();
                if (!ocupacaoStr.isEmpty()) {
                    double ocupacao = Double.parseDouble(ocupacaoStr);
                    existingAbrigo.setOcupacao(ocupacao);
                }

                validateAbrigo(existingAbrigo);
                repository.update(existingAbrigo);
                System.out.println("Abrigo atualizado com sucesso!");
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Abrigo não encontrado!");
        }
    }

    public void deleteAbrigo(Long id) {
        repository.delete(id);
        System.out.println("Abrigo deletado com sucesso!");
    }

    public void close() {
        repository.close(); // Assumindo que AbrigoRepository tem um método close() para liberar recursos
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
