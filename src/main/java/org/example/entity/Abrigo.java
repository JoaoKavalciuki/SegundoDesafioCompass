package org.example.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "abrigos")
public class Abrigo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String endereco;
    private String responsavel;
    private String telefone;
    private String email;
    private int capacidade;
    private double ocupacao;

    @OneToMany(mappedBy = "abrigo", cascade = CascadeType.ALL)
    private List<Pedido> pedidos;

    public Abrigo() {
    }

    //TODO get e set

}

