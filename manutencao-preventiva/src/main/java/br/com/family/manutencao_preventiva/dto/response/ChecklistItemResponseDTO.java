package br.com.family.manutencao_preventiva.dto.response;

import br.com.family.manutencao_preventiva.domain.enums.RespostaItem;

public record ChecklistItemResponseDTO(
        Long id,
        String descricao,
        Integer ordem,
        RespostaItem resposta,
        String observacao
) {}
