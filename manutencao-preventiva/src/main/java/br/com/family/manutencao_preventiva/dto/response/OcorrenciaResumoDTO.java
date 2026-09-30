package br.com.family.manutencao_preventiva.dto.response;

import br.com.family.manutencao_preventiva.domain.enums.NivelCriticidade;
import java.time.LocalDateTime;

public record OcorrenciaResumoDTO(
        Long id,
        String tipo,
        NivelCriticidade criticidade,
        String descricao,
        String veiculoPlaca,
        String motoristaNome,
        LocalDateTime dataHora
) {}