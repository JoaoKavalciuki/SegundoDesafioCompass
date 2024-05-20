package org.example.entity;

import jakarta.persistence.*;
import org.example.entity.enums.StatusPedido;

import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "abrigo_id")
    private Abrigo abrigo;

    @ManyToMany
    @JoinTable(name = "tb_pedidos_centro",
            joinColumns = @JoinColumn(name = "id_produto"),
            inverseJoinColumns = @JoinColumn(name = "id_centro"))
    private Set<CentroDistribuicao> centrosDeDistribuicao = new HashSet<>();

    private Instant dataPedido;

    @Enumerated(EnumType.STRING) //ARMAZENA A STRING E NÃO O INDICE DO ENUM
    private StatusPedido statusPedido;

    private String motivoRecusa; //PODE SER TEXT NO LUGAR DE STRING

    @OneToMany(mappedBy = "id.pedido", cascade = CascadeType.ALL)
    private List<PedidoItem> itens;

    public Pedido() {
    }

    public Pedido(Abrigo abrigo, Instant dataPedido, StatusPedido statusPedido) {
        this.abrigo = abrigo;
        this.dataPedido = dataPedido;
        this.statusPedido = statusPedido;
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

    public Set<CentroDistribuicao> getCentrosDeDistribuicao() {
        return centrosDeDistribuicao;
    }

    public void setCentrosDeDistribuicao(Set<CentroDistribuicao> centrosDeDistribuicao) {
        this.centrosDeDistribuicao = centrosDeDistribuicao;
    }

    public Instant getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Instant dataPedido) {
        this.dataPedido = dataPedido;
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

    public List<PedidoItem> getItens() {
        return itens;
    }

    public void setItens(List<PedidoItem> itens) {
        this.itens = itens;
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
        return "Pedido do abrigo " +this.getAbrigo().getNome()
                + ", rua: " + this.getAbrigo().getEndereco()
                + "\nitens: " + this.getItens().get(0).getId().getItem().getItem() + ", quantidade: " +
                this.getItens().get(0).getQuantidade();
    }
}
