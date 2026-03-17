package br.com.family.manutencao_preventiva.dto.request;

import br.com.family.manutencao_preventiva.domain.enums.TipoVeiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ChecklistTemplateRequestDTO(

        @NotBlank(message = "O nome do template é obrigatório")
        String nome,

        @NotBlank(message = "A descrição é obrigatória")
        String descricao,

        @NotNull(message = "O tipo de veículo deve ser informado")
        TipoVeiculo tipoVeiculo,

        List<ChecklistTemplateItemDTO> itens
) {}
