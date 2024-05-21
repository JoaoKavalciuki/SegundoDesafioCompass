package org.example.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "doacoes")
public class Doacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "cd_id")
    private CentroDistribuicao centroDistribuicao;

    private int quantidade;
    private Instant dataDoacao;

    public Doacao() {
    }

    //TODO get e set

}
