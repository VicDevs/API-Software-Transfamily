package br.com.family.manutencao_preventiva.dto.response;

public record ViagemDetalhadaDTO(
        @com.fasterxml.jackson.annotation.JsonUnwrapped
        ViagemResumoDTO resumo,
        ChecklistAtualDTO checklistAtual
) {}