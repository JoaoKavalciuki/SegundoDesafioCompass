package org.example.entities;

import org.example.entities.Item;

import jakarta.persistence.*;

@Entity
@Table(name = "pedido_itens")
public class PedidoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private int quantidade;

    public PedidoItem() {
    }

    // TODO get e set

}