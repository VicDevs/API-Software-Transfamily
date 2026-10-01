package br.com.family.manutencao_preventiva.modules.checklist.dto;

import br.com.family.manutencao_preventiva.modules.checklist.domain.enums.RespostaItem;

public record ChecklistItemResponseDTO(
        Long id,
        String descricao,
        Integer ordem,
        RespostaItem resposta,
        String observacao,
        String fotoPath
) {}
