package br.com.family.manutencao_preventiva.dto.response;

import br.com.family.manutencao_preventiva.domain.enums.StatusViagem;
import br.com.family.manutencao_preventiva.modules.checklist.dto.ChecklistResponseDTO;

import java.time.LocalDateTime;

public record ViagemResponseDTO(
        Long id,
        String placaVeiculo,
        String nomeMotorista,
        Integer kmSaida,
        StatusViagem status,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        ChecklistResponseDTO checklistAtual
) {}
