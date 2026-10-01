package br.com.family.manutencao_preventiva.modules.ocorrencia.dto;
import br.com.family.manutencao_preventiva.modules.ocorrencia.domain.enums.TipoOcorrencia;

public record OcorrenciaRequestDTO(
        Long viagemId,
        TipoOcorrencia tipo,
        String descricao,
        String fotoPath
) {}