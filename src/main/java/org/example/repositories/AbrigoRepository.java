package org.example.repositories;

import jakarta.persistence.EntityTransaction;
import org.example.entities.Abrigo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.entities.EstoqueAbrigo;

import java.util.List;

public class AbrigoRepository {

    private EntityManager em;

    public AbrigoRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Abrigo abrigo) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(abrigo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao salvar o abrigo: " + e.getMessage(), e);
        }
    }

    public Abrigo findById(Long id) {
        return em.find(Abrigo.class, id);
    }

    public List<Abrigo> findAll() {
        return em.createQuery("SELECT a FROM Abrigo a", Abrigo.class).getResultList();
    }

    public void update(Abrigo abrigo) {
        EntityTransaction transaction = em.getTransaction();
        try{
            transaction.begin();
            em.merge(abrigo);
            transaction.commit();
        }catch (Exception e){
            if (transaction.isActive()){
                transaction.rollback();
            }
            throw new RuntimeException("Error de UPDATE! ! !");
        }
    }

    public void delete(Long id) {
        EntityTransaction transaction = em.getTransaction();
        try{
            transaction.begin();
            Abrigo abrigo = em.find(Abrigo.class, id);
            if (abrigo != null) {
                em.remove(abrigo);
            }
            transaction.commit();
        }catch (Exception e){
            if (transaction.isActive()){
                transaction.rollback();
            }
            throw new RuntimeException("Error de DELETE! ! !");
        }
    }

    public List<Abrigo> findByOrderByCapacidadeAsc() {
        TypedQuery<Abrigo> query = em.createQuery("SELECT a FROM Abrigo a ORDER BY a.capacidade ASC", Abrigo.class);
        return query.getResultList();
    }

    public List<Abrigo> findByOrderByCapacidadeDesc() {
        TypedQuery<Abrigo> query = em.createQuery("SELECT a FROM Abrigo a ORDER BY a.capacidade DESC", Abrigo.class);
        return query.getResultList();
    }

    public List<Abrigo> findByOrderByOcupacaoAsc() {
        TypedQuery<Abrigo> query = em.createQuery("SELECT a FROM Abrigo a ORDER BY a.ocupacao ASC", Abrigo.class);
        return query.getResultList();
    }

    public List<Abrigo> findByOrderByOcupacaoDesc() {
        TypedQuery<Abrigo> query = em.createQuery("SELECT a FROM Abrigo a ORDER BY a.ocupacao DESC", Abrigo.class);
        return query.getResultList();
    }

    public List<EstoqueAbrigo> findByQuantidadeRecebidaAsc() {
        TypedQuery<EstoqueAbrigo> query = em.createQuery(
                "SELECT e FROM EstoqueAbrigo e ORDER BY e.quantidade ASC", EstoqueAbrigo.class);
        return query.getResultList();
    }

    public List<EstoqueAbrigo> findByQuantidadeRecebidaDesc() {
        TypedQuery<EstoqueAbrigo> query = em.createQuery(
                "SELECT e FROM EstoqueAbrigo e ORDER BY e.quantidade DESC", EstoqueAbrigo.class);
        return query.getResultList();
    }

    public boolean existsByNomeAndEndereco(String nome, String endereco) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(a) FROM Abrigo a WHERE a.nome = :nome AND a.endereco = :endereco",
                Long.class);
        query.setParameter("nome", nome);
        query.setParameter("endereco", endereco);
        Long count = query.getSingleResult();
        return count > 0;
    }
}