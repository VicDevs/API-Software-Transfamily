package br.com.family.manutencao_preventiva.dto.request;

public record ChecklistRequestDTO(
    Long veiculoId,
    Long templateId,
    Integer kmAtual
) {}