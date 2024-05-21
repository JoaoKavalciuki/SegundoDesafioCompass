package org.example.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "itens")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoria;
    private String item;
    private String genero;
    private String tamanho;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Doacao> doacoes;

    public Item() {
    }

    //TODO get e set

}
