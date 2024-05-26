package org.example.repositories;

import java.util.List;
import java.util.Optional;

import org.example.entities.Abrigo;
import org.example.entities.EstoqueAbrigo;
import org.example.entities.Item;
import org.example.utils.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;

public class EstoqueAbrigoRepository {
    private EntityManager em;

    public EstoqueAbrigoRepository() {
        em = JPAUtil.getEntityManager();
    }

    public void save(EstoqueAbrigo estoqueAbrigo) {
        em.getTransaction().begin();
        em.persist(estoqueAbrigo);
        em.getTransaction().commit();
    }

    public List<EstoqueAbrigo> findAll() {
        return em.createQuery("from Estoque", EstoqueAbrigo.class).getResultList();
    }

    public EstoqueAbrigo findById(Long id) {
        return em.find(EstoqueAbrigo.class, id);
    }

    public void update(EstoqueAbrigo estoqueAbrigo) {
        em.getTransaction().begin();
        em.merge(estoqueAbrigo);
        em.getTransaction().commit();
    }

    public void delete(Long id) {
        EstoqueAbrigo estoqueAbrigo = findById(id);
        if (estoqueAbrigo != null) {
            em.getTransaction().begin();
            em.remove(estoqueAbrigo);
            em.getTransaction().commit();
        }
    }

    public List<EstoqueAbrigo> findByAbrigoId(Long abrigoId) {
        return em.createQuery("from EstoqueAbrigo where abrigo.id = :abrigoId", EstoqueAbrigo.class)
                .setParameter("abrigoId", abrigoId)
                .getResultList();
    }

    public Optional<EstoqueAbrigo> findEstoqueByItemTipo(Long abrigoId, String tipo, Long itemId) {
        try {
            EstoqueAbrigo estoqueAbrigo = em.createQuery(
                            "SELECT ea FROM EstoqueAbrigo ea JOIN ea.item i WHERE ea.abrigo.id = :abrigoId AND i.itemTipo = :itemTipo",
                            EstoqueAbrigo.class)
                    .setParameter("abrigoId", abrigoId)
                    .setParameter("itemTipo", tipo)
                    .getSingleResult();
            return Optional.of(estoqueAbrigo);
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (NonUniqueResultException e) {
            List<EstoqueAbrigo> results = em.createQuery(
                            "SELECT ea FROM EstoqueAbrigo ea JOIN ea.item i WHERE ea.abrigo.id = :abrigoId AND i.itemTipo = :itemTipo",
                            EstoqueAbrigo.class)
                    .setParameter("abrigoId", abrigoId)
                    .setParameter("itemTipo", tipo)
                    .getResultList();
            
            return results.stream()
                    .filter(estoque -> estoque.getItem().getId().equals(itemId))
                    .findFirst();
        }
    }

    public void updateEstoque(Long abrigoId, Long itemId, int quantidade) {
        em.getTransaction().begin();
        int updatedRows = em.createQuery("UPDATE EstoqueAbrigo e SET e.quantidade = e.quantidade + :quantidade " +
                        "WHERE e.abrigo.id = :abrigoId AND e.item.id = :itemId")
                .setParameter("quantidade", quantidade)
                .setParameter("abrigoId", abrigoId)
                .setParameter("itemId", itemId)
                .executeUpdate();
        em.getTransaction().commit();
        if (updatedRows == 0) {
            em.getTransaction().begin();
            EstoqueAbrigo novoEstoqueAbrigo = new EstoqueAbrigo();
            novoEstoqueAbrigo.setAbrigo(em.find(Abrigo.class, abrigoId));
            novoEstoqueAbrigo.setItem(em.find(Item.class, itemId));
            novoEstoqueAbrigo.setQuantidade(quantidade);
            em.persist(novoEstoqueAbrigo);
            em.getTransaction().commit();
        }
    }
}
