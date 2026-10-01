package br.com.family.manutencao_preventiva.modules.checklistTemplate.dto;

import br.com.family.manutencao_preventiva.domain.enums.TipoVeiculo;
import br.com.family.manutencao_preventiva.dto.response.ChecklistTemplateItemResponseDTO;

import java.util.List;

public record ChecklistTemplateResponseDTO(
        Long id,
        String nome,
        String descricao,
        TipoVeiculo tipoVeiculo,
        List<ChecklistTemplateItemResponseDTO> itens
) {
}
