package org.example.services;

import org.example.entities.Doacao;
import org.example.entities.EstoqueCentro;
import org.example.repositories.DoacaoRepository;
import org.example.repositories.EstoqueCentroRepository;
import org.example.services.interfaces.DoacaoService;

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
                    "O centro não pode armazenar mais do que 1000 itens deste tipo. Quantidade Atual: " + total);
            return true;
        }
        return false;
    }

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

        System.out.println("Doação enviada ao centro!");
    }

}
