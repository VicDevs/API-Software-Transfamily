package br.com.family.manutencao_preventiva.modules.checklist.dto;

import br.com.family.manutencao_preventiva.dto.response.ChecklistItemResponseDTO;
import br.com.family.manutencao_preventiva.modules.checklist.domain.enums.StatusChecklist;

import java.util.List;

public record ChecklistResponseDTO(
        Long id,
        Long viagemId,
        String placaVeiculo,
        String nomeMotorista,
        StatusChecklist status,
        Integer kmAtual,
        String tipo,
        List<ChecklistItemResponseDTO> itens
) {}