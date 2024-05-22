package org.example.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.example.entities.Abrigo;
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

    public EstoqueCentroRepository findByTipo(String tipo) {
        return null;
    }

    public void verifica(Long cdid, Long itemid) {

    }

    public List<EstoqueCentroRepository> findAll() {
        return null;
    }

    public void update(Abrigo abrigo) {
    }


    public void close() {
        if (em.isOpen()) {
            em.close();
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
}