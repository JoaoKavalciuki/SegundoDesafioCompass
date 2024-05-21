package org.example.entitys;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "centros_distribuicao")
public class CentroDistribuicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    @OneToMany(mappedBy = "centroDistribuicao", cascade = CascadeType.ALL)
    private List<Doacao> doacoes;

    public CentroDistribuicao() {
    }

    //TODO get e set


    @Override
    public String toString() {
        return "CentroDistribuicao{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", logradouro='" + logradouro + '\'' +
                ", numero='" + numero + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                ", cep='" + cep + '\'' +
                ", doacoes=" + doacoes +
                '}';
    }
}
