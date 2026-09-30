package br.com.family.manutencao_preventiva.dto.response;

public record EstatisticasMotoristaDTO(
        Integer viagensNoMes,
        Long kmRodadosNoMes,
        UltimaViagemResumoDTO ultimaViagem
) {}
