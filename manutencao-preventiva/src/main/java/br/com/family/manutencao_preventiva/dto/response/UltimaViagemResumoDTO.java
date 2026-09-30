package br.com.family.manutencao_preventiva.dto.response;

public record UltimaViagemResumoDTO(
        Long id,
        String dataFim,
        String placa,
        String modelo
) {}
