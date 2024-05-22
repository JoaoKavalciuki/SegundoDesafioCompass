package org.example.repositories;

import java.util.List;

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
                "WHERE d.item.categoria = :categoria AND d.centroDistribuicao = :centroDistribuicao";
        var total = em.createQuery(query, Long.class)
                .setParameter("categoria", item.getCategoria())
                .setParameter("centroDistribuicao", centroDistribuicao)
                .getSingleResult();
        return total != null ? total.intValue() : 0;
    }

    public List<Doacao> listAll() {
        String query = "SELECT d FROM Doacao d JOIN d.item i JOIN d.centroDistribuicao c";
        return em.createQuery(query, Doacao.class)
                .getResultList();
    }

    public List<Doacao> listByCategoria(String categoria) {
        String query = "SELECT d FROM Doacao d " +
                "JOIN d.item i JOIN d.centroDistribuicao c " +
                "WHERE i.categoria = :categoria";
        return em.createQuery(query, Doacao.class)
                .setParameter("categoria", categoria)
                .getResultList();
    }

}
