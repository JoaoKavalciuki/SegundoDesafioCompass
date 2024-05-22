package org.example.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "itens")
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "categoria")
    private String categoria;
    @Column(name = "tipo")
    private String itemTipo;
    @Column(name = "genero")
    private String genero;
    @Column(name = "tamanho")
    private String tamanho;

    public Item() {
    }

    public Item(Long id, String categoria, String itemTipo, String genero, String tamanho) {
        this.id = id;
        this.categoria = categoria;
        this.itemTipo = itemTipo;
        this.genero = genero;
        this.tamanho = tamanho;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getItemTipo() {
        return itemTipo;
    }

    public void setItemTipo(String itemTipo) {
        this.itemTipo = itemTipo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
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
        Item other = (Item) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Item:\n");
        sb.append("ID: ").append(id).append("\n");
        sb.append("Categoria: ").append(categoria).append("\n");
        sb.append("Nome do Item: ").append(itemTipo).append("\n");
        if (genero != null)
            sb.append("Gênero: ").append(genero).append("\n");
        if (tamanho != null)
            sb.append("Tamanho: ").append(tamanho).append("\n");
        sb.append("\n");
        return sb.toString();
    }
}
