package br.com.family.manutencao_preventiva.dto.response;

import br.com.family.manutencao_preventiva.domain.enums.UserRole;

public record LoginResponseDTO(
        String nome,
        String cpf,
        UserRole role
) {}
