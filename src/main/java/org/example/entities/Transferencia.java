package org.example.entities;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transferencias")
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "origem_cd_id")
    private CentroDistribuicao origemCentro;

    @ManyToOne
    @JoinColumn(name = "destino_cd_id")
    private CentroDistribuicao destinoCentro;

    @ManyToOne
    @JoinColumn(name = "abrigo_id")
    private Abrigo abrigo;

    private int quantidade;

    private Instant dataTransferencia;

    private String tipo;

    public Transferencia() {
    }

    // TODO get e set

}