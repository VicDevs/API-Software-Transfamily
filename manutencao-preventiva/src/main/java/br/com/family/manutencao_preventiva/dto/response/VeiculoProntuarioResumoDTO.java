package br.com.family.manutencao_preventiva.dto.response;

public record VeiculoProntuarioResumoDTO(
        Long veiculoId,
        String placa,
        String modelo,
        String marca,
        Integer anoFabricacao,
        Integer kmAtual,
        String statusAtual, // Ex: DISPONIVEL, EM_VIAGEM, MANUTENCAO
        Integer viagensNoMes,
        Long kmRodadoNoMes
) {}
