package br.com.family.manutencao_preventiva.dto.response;

import java.time.LocalDateTime;

public record ViagemAtivaDTO(
        Long viagemId,
        String motoristaNome,
        String veiculoPlaca,
        String veiculoModelo,
        LocalDateTime dataSaida
) {}
