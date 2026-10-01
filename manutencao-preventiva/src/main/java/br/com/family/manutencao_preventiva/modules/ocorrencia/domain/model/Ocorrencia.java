package br.com.family.manutencao_preventiva.modules.ocorrencia.domain.model;

import br.com.family.manutencao_preventiva.modules.ocorrencia.domain.enums.TipoOcorrencia;
import br.com.family.manutencao_preventiva.domain.model.Viagem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Ocorrencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "viagem_id")
    private Viagem viagem;

    private LocalDateTime dataHora;

    private TipoOcorrencia tipo;

    private String descricao;

    private String fotoPath;

}
