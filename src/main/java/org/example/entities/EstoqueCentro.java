package org.example.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "estoque_centro")
public class EstoqueCentro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cd_id")
    private CentroDistribuicao centroDistribuicao;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private int quantidade;

    public EstoqueCentro() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CentroDistribuicao getCentroDistribuicao() {
        return centroDistribuicao;
    }

    public void setCentroDistribuicao(CentroDistribuicao centroDistribuicao) {
        this.centroDistribuicao = centroDistribuicao;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "EstoqueCentro{" +
                "id=" + id +
                ", centroDistribuicao=" + centroDistribuicao +
                ", item=" + item +
                ", quantidade=" + quantidade +
                '}';
    }

}
