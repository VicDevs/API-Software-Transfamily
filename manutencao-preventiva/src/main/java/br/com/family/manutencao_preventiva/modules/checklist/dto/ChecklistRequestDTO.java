package br.com.family.manutencao_preventiva.modules.checklist.dto;

public record ChecklistRequestDTO(
    Long veiculoId,
    Long templateId,
    Integer kmAtual
) {}