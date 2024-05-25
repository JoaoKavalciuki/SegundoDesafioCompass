package org.example.entities;

import java.sql.Timestamp;

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

    @JoinColumn(name = "data_transferencia", nullable = false)
    private Timestamp dataTransferencia;

    private String tipo;

    public Transferencia() {
    }

	public Transferencia(Item item, CentroDistribuicao origemCentro, CentroDistribuicao destinoCentro,
			Abrigo abrigo, int quantidade, Timestamp dataTransferencia, String tipo) {
		this.item = item;
		this.origemCentro = origemCentro;
		this.destinoCentro = destinoCentro;
		this.abrigo = abrigo;
		this.quantidade = quantidade;
		this.dataTransferencia = dataTransferencia;
		this.tipo = tipo;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public CentroDistribuicao getOrigemCentro() {
		return origemCentro;
	}

	public void setOrigemCentro(CentroDistribuicao origemCentro) {
		this.origemCentro = origemCentro;
	}

	public CentroDistribuicao getDestinoCentro() {
		return destinoCentro;
	}

	public void setDestinoCentro(CentroDistribuicao destinoCentro) {
		this.destinoCentro = destinoCentro;
	}

	public Abrigo getAbrigo() {
		return abrigo;
	}

	public void setAbrigo(Abrigo abrigo) {
		this.abrigo = abrigo;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Timestamp getDataTransferencia() {
		return dataTransferencia;
	}

	public void setDataTransferencia(Timestamp dataTransferencia) {
		this.dataTransferencia = dataTransferencia;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

    
}