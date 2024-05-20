package org.example.services;

import org.example.entities.Doacao;
import org.example.repositories.DoacaoRepository;
import org.example.services.interfaces.DoacaoService;

public class DoacaoServiceImpl implements DoacaoService {
    private DoacaoRepository doacaoRepository;

    public DoacaoServiceImpl() {
        this.doacaoRepository = new DoacaoRepository();
    }

    @Override
    public void save(Doacao doacao) {
        var total = doacaoRepository.calcularQuantidadeTotal(doacao.getItem(), doacao.getCentroDistribuicao());
        if (total + doacao.getQuantidade() > 1000) {
            System.out.println(
                    "O centro não pode armazenar mais do que 1000 itens deste tipo. Quantidade Atual: " + total);
            return;
        }
        doacaoRepository.save(doacao);
        System.out.println("Doação enviada ao centro!");
    }
}
