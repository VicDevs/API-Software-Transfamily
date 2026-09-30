package br.com.family.manutencao_preventiva.dto.response;

import br.com.family.manutencao_preventiva.modules.checklist.dto.ChecklistAtualDTO;

public record ViagemDetalhadaDTO(
        @com.fasterxml.jackson.annotation.JsonUnwrapped
        ViagemResumoDTO resumo,
        ChecklistAtualDTO checklistAtual
) {}