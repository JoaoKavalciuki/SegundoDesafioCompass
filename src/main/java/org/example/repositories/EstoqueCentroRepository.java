package org.example.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.example.entities.EstoqueCentro;
import org.example.utils.JPAUtil;

import java.util.List;
import java.util.Optional;

public class EstoqueCentroRepository {

    private EntityManager em;

    public EstoqueCentroRepository() {
        em = JPAUtil.getEntityManager();
    }

    public void save(EstoqueCentro estoqueCentro) {
        if (estoqueCentro.getId() == null) {
            em.persist(estoqueCentro);
        } else {
            em.merge(estoqueCentro);
        }
    }

    public Optional<EstoqueCentro> findByCDeItem(Long cdID, Long itemID) {
        try {
            EstoqueCentro estoqueCentro = em.createQuery(
                            "SELECT ecd FROM EstoqueCentro ecd WHERE ecd.centroDistribuicao.id = :centroId AND ecd.item.id = :itemId",
                            EstoqueCentro.class)
                    .setParameter("centroId", cdID)
                    .setParameter("itemId", itemID)
                    .getSingleResult();
            return Optional.of(estoqueCentro);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<EstoqueCentro> findEstoquesByItemTipo(String tipo){
        List<EstoqueCentro> estoquesCentros = em.createQuery(
                """
                    SELECT e FROM EstoqueCentro e LEFT JOIN e.item i
                    WHERE i.itemTipo = :itemTipo
                    ORDER BY e.quantidade DESC
                """,
        EstoqueCentro.class).setParameter("itemTipo", tipo).getResultList();
        return estoquesCentros;
    }

    public void updateEstoque(Long centroId, Long itemId, int quantidade) {
        em.getTransaction().begin();
        em.createQuery("UPDATE EstoqueCentro e SET e.quantidade = e.quantidade - :quantidade " +
                        "WHERE e.centroDistribuicao.id = :centroId AND e.item.id = :itemId")
                .setParameter("quantidade", quantidade)
                .setParameter("centroId", centroId)
                .setParameter("itemId", itemId)
                .executeUpdate();
        em.getTransaction().commit();
    }
}