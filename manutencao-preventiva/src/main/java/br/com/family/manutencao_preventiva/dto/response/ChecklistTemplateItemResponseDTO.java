package br.com.family.manutencao_preventiva.dto.response;

import br.com.family.manutencao_preventiva.domain.enums.NivelCriticidade;

public record ChecklistTemplateItemResponseDTO(
        Long id,
        String descricao,
        Integer ordem,
        NivelCriticidade criticidade
) {

}
