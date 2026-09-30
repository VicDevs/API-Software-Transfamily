package br.com.family.manutencao_preventiva.dto.response;

public record DashboardResumoDTO(
        long frotaTotal,
        long veiculosEmRota,
        long veiculosEmManutencao,
        long alertasCriticosHoje
) {}