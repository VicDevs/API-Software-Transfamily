package br.com.family.manutencao_preventiva.domain.model;

import br.com.family.manutencao_preventiva.domain.enums.TipoVeiculo;
import br.com.family.manutencao_preventiva.exception.BusinessException;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "veiculo")
@Getter
@NoArgsConstructor
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Setter
    private String placa;

    @Column(nullable = false, unique = true, length = 11)
    @Setter
    private String renavam;

    @Column(nullable = false)
    @Setter
    private String modelo;

    @Column(nullable = false)
    @Setter
    private String marca;

    @Column(nullable = false)
    @Setter
    private int ano;

    @Column(nullable = false)
    @Setter
    private Integer kmAtual;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Setter
    private TipoVeiculo tipo;

    @Column(nullable = false)
    private boolean ativo = true;

    public Veiculo(String placa, String renavam, String modelo, String marca, int ano, Integer kmAtual, TipoVeiculo tipo) {
        this.placa = placa;
        this.renavam = renavam;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.kmAtual = kmAtual;
        this.tipo = tipo;
    }

    public void desativar() {
        this.ativo = false;
    }

    public void ativar() {
        this.ativo = true;
    }

    public void atualizarQuilometragem(Integer novoKm) {
        if (novoKm < this.kmAtual) {
            throw new BusinessException("KM não pode ser menor que o anterior");
        }
        this.kmAtual = novoKm;
    }

    public void validarNovaQuilometragem(Integer kmInformado) {
        if (kmInformado == null) {
            throw new BusinessException("A quilometragem atual é obrigatória.");
        }
        if (this.kmAtual != null && kmInformado < this.kmAtual) {
            throw new BusinessException(
                    String.format("KM informado (%d) é menor que o KM atual do veículo (%d).",
                            kmInformado, this.kmAtual)
            );
        }
    }
}
