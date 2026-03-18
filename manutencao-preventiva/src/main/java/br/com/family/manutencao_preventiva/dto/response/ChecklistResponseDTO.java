package br.com.family.manutencao_preventiva.dto.response;

import br.com.family.manutencao_preventiva.domain.enums.StatusChecklist;

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