package br.com.family.manutencao_preventiva.domain.model;

import br.com.family.manutencao_preventiva.domain.enums.StatusManutencao;
import br.com.family.manutencao_preventiva.domain.enums.TipoManutencao;
import br.com.family.manutencao_preventiva.modules.veiculo.domain.Veiculo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "manutencao")
@Getter
@NoArgsConstructor
public class Manutencao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoManutencao tipoManutencao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusManutencao statusManutencao;

    @Column(nullable = false)
    private LocalDateTime dataAbertura;

    private LocalDateTime dataFechamento;

    @Column(nullable = false)
    private String descricaoProblema;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    @OneToMany(mappedBy = "manutencao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustoManutencao> custos = new ArrayList<>();

    public Manutencao(TipoManutencao tipoManutencao, String descricaoProblema) {
        this.tipoManutencao = tipoManutencao;
        this.statusManutencao = StatusManutencao.ABERTA;
        this.descricaoProblema = descricaoProblema;
    }

    @PrePersist
    protected void onCreate() {
        this.dataAbertura = LocalDateTime.now();
    }

    public void finalizar() {
        this.statusManutencao = StatusManutencao.FINALIZADA;
        this.dataFechamento = LocalDateTime.now();
    }

}
