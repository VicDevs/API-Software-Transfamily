package br.com.family.manutencao_preventiva.modules.veiculo.mapper;

import br.com.family.manutencao_preventiva.domain.enums.TipoVeiculo;
import br.com.family.manutencao_preventiva.dto.request.VeiculoRequestDTO;
import br.com.family.manutencao_preventiva.dto.response.VeiculoResponseDTO;
import br.com.family.manutencao_preventiva.modules.veiculo.domain.Veiculo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-28T19:56:49-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class VeiculoMapperImpl extends VeiculoMapper {

    @Override
    public Veiculo toEntity(VeiculoRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Veiculo veiculo = new Veiculo();

        veiculo.setPlaca( dto.placa() );
        veiculo.setRenavam( dto.renavam() );
        veiculo.setModelo( dto.modelo() );
        veiculo.setMarca( dto.marca() );
        veiculo.setAno( dto.ano() );
        veiculo.setKmAtual( dto.kmAtual() );
        veiculo.setTipo( dto.tipo() );

        return veiculo;
    }

    @Override
    public VeiculoResponseDTO toResponseDTO(Veiculo veiculo) {
        if ( veiculo == null ) {
            return null;
        }

        boolean ativo = false;
        Long id = null;
        String placa = null;
        String renavam = null;
        String modelo = null;
        String marca = null;
        int ano = 0;
        Integer kmAtual = null;
        TipoVeiculo tipo = null;

        ativo = veiculo.isAtivo();
        id = veiculo.getId();
        placa = veiculo.getPlaca();
        renavam = veiculo.getRenavam();
        modelo = veiculo.getModelo();
        marca = veiculo.getMarca();
        ano = veiculo.getAno();
        kmAtual = veiculo.getKmAtual();
        tipo = veiculo.getTipo();

        String descricaoExibicao = null;

        VeiculoResponseDTO veiculoResponseDTO = new VeiculoResponseDTO( id, placa, renavam, modelo, marca, ano, kmAtual, tipo, ativo, descricaoExibicao );

        return veiculoResponseDTO;
    }

    @Override
    public void updateEntityFromDto(VeiculoRequestDTO dto, Veiculo veiculo) {
        if ( dto == null ) {
            return;
        }

        veiculo.setPlaca( dto.placa() );
        veiculo.setRenavam( dto.renavam() );
        veiculo.setModelo( dto.modelo() );
        veiculo.setMarca( dto.marca() );
        veiculo.setAno( dto.ano() );
        veiculo.setKmAtual( dto.kmAtual() );
        veiculo.setTipo( dto.tipo() );
    }
}
