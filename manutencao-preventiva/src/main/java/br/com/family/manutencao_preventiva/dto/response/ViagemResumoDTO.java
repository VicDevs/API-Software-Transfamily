package br.com.family.manutencao_preventiva.dto.response;

import br.com.family.manutencao_preventiva.modules.checklist.domain.enums.StatusChecklist;
import br.com.family.manutencao_preventiva.domain.enums.StatusViagem;

import java.time.LocalDateTime;

public record ViagemResumoDTO(
        Long id,
        String placaVeiculo,
        String modeloVeiculo,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        Integer kmSaida,
        Integer kmRetorno,
        StatusViagem status,
        String tipoChecklist,
        StatusChecklist statusChecklist,
        Long checklistId
) {}