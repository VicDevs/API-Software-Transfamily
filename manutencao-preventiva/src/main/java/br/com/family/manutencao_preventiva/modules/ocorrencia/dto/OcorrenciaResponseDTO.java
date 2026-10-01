package br.com.family.manutencao_preventiva.modules.ocorrencia.dto;
import java.time.LocalDateTime;

public record OcorrenciaResponseDTO(
        Long id,
        Long viagemId,
        String tipo,
        String descricaoTipo,
        String criticidade,
        String acaoRecomendada,
        String descricaoMotorista,
        String fotoPath,
        LocalDateTime dataHora
) {}