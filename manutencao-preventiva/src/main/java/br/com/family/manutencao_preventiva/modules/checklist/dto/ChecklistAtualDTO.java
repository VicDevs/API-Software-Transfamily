package br.com.family.manutencao_preventiva.modules.checklist.dto;

import br.com.family.manutencao_preventiva.dto.response.ChecklistItemResponseDTO;

import java.util.List;

public record ChecklistAtualDTO(
        Long id,
        List<ChecklistItemResponseDTO> itens
) {}
