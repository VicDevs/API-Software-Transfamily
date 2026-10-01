package br.com.family.manutencao_preventiva.modules.checklist.dto;

import java.util.List;

public record ChecklistAtualDTO(
        Long id,
        List<ChecklistItemResponseDTO> itens
) {}
