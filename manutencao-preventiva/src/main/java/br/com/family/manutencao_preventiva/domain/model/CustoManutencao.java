package br.com.family.manutencao_preventiva.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "custo_manutencao")
@Getter
@NoArgsConstructor
public class CustoManutencao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //add manutencao

    @Setter
    @Column(nullable = false)
    private String descricao;

    @Setter
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDateTime dataLancamento;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manutencao_id", nullable = false)
    private Manutencao manutencao;

    public CustoManutencao(String descricao, BigDecimal valor) {
        this.descricao = descricao;
        this.valor = valor;
    }

    @PrePersist
    private void onCreate() {
        this.dataLancamento = LocalDateTime.now();
    }
}
