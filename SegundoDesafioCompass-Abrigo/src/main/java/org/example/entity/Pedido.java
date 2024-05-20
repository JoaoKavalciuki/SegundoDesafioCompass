package org.example.entity;

import jakarta.persistence.*;
import org.example.entity.enums.StatusPedido;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "abrigo_id")
    private Abrigo abrigo;

    private Instant dataPedido;

    @Enumerated(EnumType.STRING) //ARMAZENA A STRING E NÃO O INDICE DO ENUM
    private StatusPedido statusPedido;

    private String motivoRecusa; //PODE SER TEXT NO LUGAR DE STRING

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<PedidoItem> itens;

    public Pedido() {
    }

    //TODO get e set

}
