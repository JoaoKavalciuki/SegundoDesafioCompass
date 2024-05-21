package org.example.entities;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "doacoes")
public class Doacao implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "quantidade")
    private Integer quantidade;
    @Column(name = "data_doacao")
    private LocalDate data;
    @ManyToOne
    @JoinColumn(name = "cd_id")
    private CentroDistribuicao centroDistribuicao;
    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;

    public Doacao() {
    }

    public Doacao(Long id, Integer quantidade, LocalDate data, CentroDistribuicao centroDistribuicao, Item item) {
        this.id = id;
        this.quantidade = quantidade;
        this.data = data;
        this.centroDistribuicao = centroDistribuicao;
        this.item = item;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Doacao other = (Doacao) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
