package org.example.entitys;

import jakarta.persistence.*;
import org.example.entitys.enums.TipoTransferencia;

import java.time.Instant;

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

    @Enumerated(EnumType.STRING)
    private TipoTransferencia tipo;

    public Transferencia() {
    }

    //TODO get e set

}
