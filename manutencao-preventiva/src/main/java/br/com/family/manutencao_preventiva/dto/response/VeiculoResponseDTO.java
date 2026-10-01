package br.com.family.manutencao_preventiva.dto.response;

import br.com.family.manutencao_preventiva.modules.veiculo.domain.enums.TipoVeiculo;

public record VeiculoResponseDTO(
        Long id,
        String placa,
        String renavam,
        String modelo,
        String marca,
        int ano,
        Integer kmAtual,
        TipoVeiculo tipo,
        boolean ativo,
        String descricaoExibicao
) {

    public VeiculoResponseDTO {
        if (descricaoExibicao == null) {
            descricaoExibicao = marca + " " + modelo + " (" + placa + ")";
        }
    }
}
