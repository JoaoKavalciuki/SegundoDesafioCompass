package org.example.repositories;

import org.example.entities.CentroDistribuicao;
import org.example.entities.Doacao;
import org.example.entities.Item;
import org.example.utils.JPAUtil;

import jakarta.persistence.EntityManager;

public class DoacaoRepository {
    private EntityManager em;

    public DoacaoRepository() {
        em = JPAUtil.getEntityManager();
    }

    public void save(Doacao doacao) {
        em.getTransaction().begin();
        em.persist(doacao);
        em.getTransaction().commit();
    }

    public Integer calcularQuantidadeTotal(Item item, CentroDistribuicao centroDistribuicao) {
        String query = "SELECT SUM(d.quantidade) FROM Doacao d " +
                "WHERE d.item = :item AND d.centroDistribuicao = :centroDistribuicao";
        var total = em.createQuery(query, Long.class)
                .setParameter("item", item)
                .setParameter("centroDistribuicao", centroDistribuicao)
                .getSingleResult();
        return total != null ? total.intValue() : 0;
    }
}
