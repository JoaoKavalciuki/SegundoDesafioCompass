package org.example.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import org.example.entities.Abrigo;
import org.example.entities.EstoqueCentro;
import org.example.exceptions.ResourceNotFoundException;
import org.example.utils.JPAUtil;

import java.util.List;
import java.util.Optional;

public class EstoqueCentroRepository {

	private EntityManager em;

	public EstoqueCentroRepository() {
		em = JPAUtil.getEntityManager();
	}

	public void save(EstoqueCentro estoqueCentro) {
		EntityTransaction transaction = em.getTransaction();
		try {
			if (estoqueCentro.getId() == null) {
				transaction.begin();
				em.persist(estoqueCentro);
				transaction.commit();
			} else {
				em.merge(estoqueCentro);
			}
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			throw new RuntimeException("Erro ao salvar o abrigo: " + e.getMessage(), e);
		}
	}

	public EstoqueCentro findByTipo(String tipo) {
		return null;
	}

	public void verifica(Long cdid, Long itemid) {
		if (findByCDeItem(cdid, itemid).isEmpty()) {
			throw new ResourceNotFoundException("Não há o item especificado no centro de origem");
		}

	}

	public List<EstoqueCentro> findAll() {
		return null;
	}

	public void update(EstoqueCentro estoqueCentro) {
		em.getTransaction().begin();
		em.merge(estoqueCentro);
		em.getTransaction().commit();
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
					EstoqueCentro.class).setParameter("centroId", cdID).setParameter("itemId", itemID)
					.getSingleResult();
			return Optional.of(estoqueCentro);
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}

	public List<EstoqueCentro> findEstoquesByItemTipo(String tipo) {
		List<EstoqueCentro> estoquesCentros = em.createQuery("""

				    SELECT e FROM EstoqueCentro e LEFT JOIN e.item i WHERE i.itemTipo = :itemTipo
				""", EstoqueCentro.class).setParameter("itemTipo", tipo).getResultList();
		return estoquesCentros;
	}
}