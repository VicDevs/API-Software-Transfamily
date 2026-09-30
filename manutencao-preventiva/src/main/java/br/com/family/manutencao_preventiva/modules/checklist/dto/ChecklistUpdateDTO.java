package br.com.family.manutencao_preventiva.modules.checklist.dto;

import br.com.family.manutencao_preventiva.domain.enums.RespostaItem;

import java.util.List;


public record ChecklistUpdateDTO(
        List<ItemRespostaDTO> respostas
) {
    public record ItemRespostaDTO(
            Long itemId,
            RespostaItem resposta,
            String observacao,
            String fotoPath) {

    }
}
