package br.com.family.manutencao_preventiva.modules.dashboard.dto;

public record DashboardResumoDTO(
        long frotaTotal,
        long veiculosEmRota,
        long veiculosEmManutencao,
        long alertasCriticosHoje
) {}