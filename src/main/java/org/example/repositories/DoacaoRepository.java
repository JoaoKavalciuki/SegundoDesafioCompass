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
        String query = "SELECT SUM(e.quantidade) FROM EstoqueCentro e " +
                "WHERE e.item.categoria = :categoria AND e.centroDistribuicao = :centroDistribuicao";
        var total = em.createQuery(query, Long.class)
                .setParameter("categoria", item.getCategoria())
                .setParameter("centroDistribuicao", centroDistribuicao)
                .getSingleResult();
        return total != null ? total.intValue() : 0;
    }

    public List<Doacao> findAll() {
        String query = "SELECT d FROM Doacao d JOIN d.item i JOIN d.centroDistribuicao c";
        return em.createQuery(query, Doacao.class)
                .getResultList();
    }

    public List<Doacao> findByCategoria(String categoria) {
        String query = "SELECT d FROM Doacao d " +
                "JOIN d.item i JOIN d.centroDistribuicao c " +
                "WHERE i.categoria = :categoria";
        return em.createQuery(query, Doacao.class)
                .setParameter("categoria", categoria)
                .getResultList();
    }

    public void update(Doacao toUpdate) {
        em.getTransaction().begin();
        em.merge(toUpdate);
        em.getTransaction().commit();
    }

    public Doacao findById(Long id) {
        return em.find(Doacao.class, id);
    }

    public void deleteById(Long id) {
        em.getTransaction().begin();
        em.remove(findById(id));
        em.getTransaction().commit();
    }
}
