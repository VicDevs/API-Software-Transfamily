package br.com.family.manutencao_preventiva.domain.model;

import br.com.family.manutencao_preventiva.domain.enums.StatusViagem;
import br.com.family.manutencao_preventiva.modules.veiculo.domain.Veiculo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "viagem")
public class Viagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo_id")
    @Setter
    private Veiculo veiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motorista_id")
    @Setter
    private Motorista motorista;

    @Setter
    private Integer kmSaida;

    @Setter
    private Integer kmRetorno;

    @Setter
    private LocalDateTime dataInicio;

    @Setter
    private LocalDateTime dataFim;

    @Enumerated(EnumType.STRING)
    @Setter
    private StatusViagem status;

    @Column(name = "ultimo_tipo_checklist")
    @Setter
    private String ultimoTipoChecklist = "NENHUM";

    @OneToMany(mappedBy = "viagem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Checklist> checklists = new ArrayList<>();

    @OneToMany(mappedBy = "viagem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ocorrencia> ocorrencias = new ArrayList<>();

    public Integer getKmRodado() {
        if (kmSaida != null && kmRetorno != null) {
            return kmRetorno - kmSaida;
        }
        return 0;
    }

    @PrePersist
    protected void onCreate() {
        this.dataInicio = LocalDateTime.now();

        if (this.status == null) {
            this.status = StatusViagem.EM_CURSO;
        }
    }

    public void addChecklist(Checklist checklist) {
        this.checklists.add(checklist);
        checklist.setViagem(this);
    }

    public void addOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencias.add(ocorrencia);
        ocorrencia.setViagem(this);
    }
}
