package br.com.family.manutencao_preventiva.dto.request;
import br.com.family.manutencao_preventiva.domain.enums.TipoOcorrencia;

public record OcorrenciaRequestDTO(
        Long viagemId,
        TipoOcorrencia tipo,
        String descricao,
        String fotoPath
) {}