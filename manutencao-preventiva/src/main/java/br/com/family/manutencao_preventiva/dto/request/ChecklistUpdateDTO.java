package br.com.family.manutencao_preventiva.dto.request;

import br.com.family.manutencao_preventiva.domain.enums.RespostaItem;

import java.util.List;


public record ChecklistUpdateDTO(
        List<ItemRespostaDTO> respostas
) {
    public record ItemRespostaDTO(
            Long itemId,
            RespostaItem resposta,
            String observacao) {}
}
