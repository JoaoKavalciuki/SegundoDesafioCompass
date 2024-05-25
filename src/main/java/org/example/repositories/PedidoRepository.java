package org.example.repositories;

import jakarta.persistence.EntityManager;
import org.example.entities.Pedido;
import org.example.entities.enums.StatusPedido;
import org.example.utils.JPAUtil;

import java.util.List;

public class PedidoRepository {
    private EntityManager em;

    public PedidoRepository() {
        em = JPAUtil.getEntityManager();
    }

    public void save(Pedido pedido) {
        em.getTransaction().begin();
        em.persist(pedido);
        em.getTransaction().commit();
    }

    public List<Pedido>findAll() {
        return em.createQuery("FROM " + Pedido.class.getName(), Pedido.class).getResultList();
    }

    public Pedido findById(Long id) {
        return em.find(Pedido.class, id);
    }

    public List<Pedido> listarPedidosPendentesPorCentro(Long centroId) {
        return em.createQuery("SELECT p FROM Pedido p JOIN p.centrosDeDistribuicao c WHERE p.statusPedido = :status AND c.id = :centroId", Pedido.class)
                .setParameter("status", StatusPedido.PENDENTE)
                .setParameter("centroId", centroId)
                .getResultList();
    }

}
