package br.com.family.manutencao_preventiva.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(
        @NotBlank(message = "O CPF é obrigatório")
        String cpf,

        @NotBlank(message = "A senha é obrigatória")
        String password
) {}
