package br.com.family.manutencao_preventiva.dto.request;

import br.com.family.manutencao_preventiva.domain.enums.TipoVeiculo;

public record VeiculoRequestDTO(
        String placa,
        String renavam,
        String modelo,
        String marca,
        int ano,
        Integer kmAtual,
        TipoVeiculo tipo
) { }
