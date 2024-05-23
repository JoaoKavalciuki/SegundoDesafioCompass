package org.example.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.example.entities.enums.StatusPedido;

@Entity
@Table(name = "pedidos")
public class Pedido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "abrigo_id")
    private Abrigo abrigo;

    @Enumerated(EnumType.STRING) // ARMAZENA A STRING E NÃO O INDICE DO ENUM
    private StatusPedido statusPedido;

    private String motivoRecusa; // PODE SER TEXT NO LUGAR DE STRING


    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToMany
    @JoinTable(name = "tb_pedidos_centro",
            joinColumns = @JoinColumn(name = "id_pedido"),
            inverseJoinColumns = @JoinColumn(name = "id_centro"))
    private Set<CentroDistribuicao> centrosDeDistribuicao = new HashSet<>();

    private Integer quantidade;
    public Pedido() {
    }

    public Pedido(Abrigo abrigo, StatusPedido statusPedido, String motivoRecusa, Item item, Integer quantidade) {
        this.abrigo = abrigo;
        this.statusPedido = statusPedido;
        this.motivoRecusa = motivoRecusa;
        this.item = item;
        this.quantidade = quantidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Abrigo getAbrigo() {
        return abrigo;
    }

    public void setAbrigo(Abrigo abrigo) {
        this.abrigo = abrigo;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }

    public String getMotivoRecusa() {
        return motivoRecusa;
    }

    public void setMotivoRecusa(String motivoRecusa) {
        this.motivoRecusa = motivoRecusa;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Set<CentroDistribuicao> getCentrosDeDistribuicao() {
        return centrosDeDistribuicao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return "Pedido{" +
                "abrigo=" + abrigo +
                ", statusPedido=" + statusPedido +
                ", motivoRecusa='" + motivoRecusa + '\'' +
                ", item=" + item +
                ", centrosDeDistribuicao=" + centrosDeDistribuicao +
                ", quantidade=" + quantidade +
                '}';
    }
}
