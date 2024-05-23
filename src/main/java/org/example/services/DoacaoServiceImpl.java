package org.example.services;

import org.example.entities.Doacao;
import org.example.entities.EstoqueCentro;
import org.example.exceptions.IllegalEntryException;
import org.example.exceptions.ResourceNotFoundException;
import org.example.repositories.DoacaoRepository;
import org.example.repositories.EstoqueCentroRepository;
import org.example.services.interfaces.DoacaoService;

import java.util.List;
import java.util.Optional;

public class DoacaoServiceImpl implements DoacaoService {
    private DoacaoRepository doacaoRepository;
    private EstoqueCentroRepository estoqueCentroRepository;

    public DoacaoServiceImpl() {
        this.doacaoRepository = new DoacaoRepository();
        this.estoqueCentroRepository = new EstoqueCentroRepository();
    }

    public boolean verificaQuantidadeTotal(Doacao doacao) {
        var total = doacaoRepository.calcularQuantidadeTotal(doacao.getItem(), doacao.getCentroDistribuicao());
        if (total + doacao.getQuantidade() > 1000) {
            System.out.println(
                    "O centro não pode armazenar mais do que 1000 itens do tipo \"" + doacao.getItem().getCategoria()
                            + "\". Quantidade Atual: " + total);
            return true;
        }
        return false;
    }

    @Override
    public void save(Doacao doacao) {
        if (verificaQuantidadeTotal(doacao))
            return;
        Optional<EstoqueCentro> estoqueCentroOpt = estoqueCentroRepository.findByCDeItem(
                doacao.getCentroDistribuicao().getId(), doacao.getItem().getId());
        if (estoqueCentroOpt.isPresent()) {
            EstoqueCentro estoqueCentro = estoqueCentroOpt.get();
            estoqueCentro.setQuantidade(estoqueCentro.getQuantidade() + doacao.getQuantidade());
            estoqueCentroRepository.save(estoqueCentro);
        } else {
            EstoqueCentro novoEstoqueCentro = new EstoqueCentro();
            novoEstoqueCentro.setCentroDistribuicao(doacao.getCentroDistribuicao());
            novoEstoqueCentro.setItem(doacao.getItem());
            novoEstoqueCentro.setQuantidade(doacao.getQuantidade());
            estoqueCentroRepository.save(novoEstoqueCentro);
        }
        doacaoRepository.save(doacao);
        System.out.println("DOAÇÃO ENVIADA AO CENTRO");
    }

    @Override
    public List<Doacao> listAll() {
        System.out.println("LISTA DE DOAÇÕES: ");
        return doacaoRepository.listAll();
    }

    @Override
    public List<Doacao> listByCategoria(String categoria) {
        System.out.println("LISTA DE DOAÇÕES POR CATEGORIA: ");
        return doacaoRepository.listByCategoria(categoria);
    }

    @Override
    public Doacao findById(Long id) {
        Doacao doacao = doacaoRepository.findById(id);
        if (doacao == null) {
            throw new ResourceNotFoundException("Não existe uma doação registrada para o ID: " + id);
        }
        return doacao;
    }

    @Override
    public void update(int novaQuantidade, Long id) {
        try {
            Doacao old = this.findById(id);
            EstoqueCentro eC = estoqueCentroRepository
                    .findByCDeItem(old.getCentroDistribuicao().getId(), old.getItem().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Estoque do centro não encontrado para o item da doação."));
            int diferenca = novaQuantidade - old.getQuantidade();
            if (diferenca > eC.getQuantidade()) {
                throw new IllegalEntryException("Quantidade maior que a disponível em estoque");
            }
            eC.setQuantidade(eC.getQuantidade() + diferenca);
            estoqueCentroRepository.save(eC);
            old.setQuantidade(novaQuantidade);
            doacaoRepository.update(old);
            System.out.println("ALTERADO!");
        } catch (ResourceNotFoundException | IllegalEntryException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            Doacao toDelete = findById(id);
            EstoqueCentro eC = estoqueCentroRepository
                    .findByCDeItem(toDelete.getCentroDistribuicao().getId(), toDelete.getItem().getId()).get();
            eC.setQuantidade(eC.getQuantidade() - toDelete.getQuantidade());
            estoqueCentroRepository.save(eC);
            doacaoRepository.deleteById(id);
            System.out.println("DELETADO!");
        } catch (ResourceNotFoundException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
