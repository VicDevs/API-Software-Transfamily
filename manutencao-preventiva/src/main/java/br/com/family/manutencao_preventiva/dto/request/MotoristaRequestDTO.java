package br.com.family.manutencao_preventiva.dto.request;

public record MotoristaRequestDTO(
        String nome,
        String cpf,
        String cnh,
        String categoriaCnh
) {}
