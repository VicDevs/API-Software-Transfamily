package br.com.family.manutencao_preventiva.dto.response;

import java.util.List;

public record ChecklistAtualDTO(
        Long id,
        List<ChecklistItemResponseDTO> itens
) {}
