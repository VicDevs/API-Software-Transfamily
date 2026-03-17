package br.com.family.manutencao_preventiva.dto.request;

import br.com.family.manutencao_preventiva.domain.enums.NivelCriticidade;
import jakarta.validation.constraints.NotBlank;

public record ChecklistTemplateItemDTO(
        @NotBlank String descricao,
        Integer ordem,
        NivelCriticidade criticidade
) {}
