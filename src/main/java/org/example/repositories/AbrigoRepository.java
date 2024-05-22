package org.example.repositories;

import jakarta.persistence.EntityTransaction;
import org.example.entities.Abrigo;
import jakarta.persistence.EntityManager;

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

    public void close() {
      if(em.isOpen()){
          em.close();
      }
    }
}