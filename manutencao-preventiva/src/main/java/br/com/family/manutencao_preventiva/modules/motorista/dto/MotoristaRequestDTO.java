package br.com.family.manutencao_preventiva.modules.motorista.dto;

public record MotoristaRequestDTO(
        String nome,
        String cpf,
        String cnh,
        String categoriaCnh
) {}
