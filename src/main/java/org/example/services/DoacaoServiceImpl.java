package org.example.services;

import java.util.List;

import org.example.entities.Doacao;
import org.example.repositories.DoacaoRepository;
import org.example.services.interfaces.DoacaoService;

public class DoacaoServiceImpl implements DoacaoService {
    private DoacaoRepository doacaoRepository;

    public DoacaoServiceImpl() {
        this.doacaoRepository = new DoacaoRepository();
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
        doacaoRepository.save(doacao);
        System.out.println("Doação enviada ao centro!");
    }

    @Override
    public List<Doacao> listAll() {
        return doacaoRepository.listAll();
    }

    @Override
    public List<Doacao> listByCategoria(String categoria) {
        return doacaoRepository.listByCategoria(categoria);
    }
}
