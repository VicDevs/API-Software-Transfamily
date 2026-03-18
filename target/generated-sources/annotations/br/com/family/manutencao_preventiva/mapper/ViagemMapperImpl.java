package br.com.family.manutencao_preventiva.mapper;

import br.com.family.manutencao_preventiva.domain.enums.StatusViagem;
import br.com.family.manutencao_preventiva.domain.model.Motorista;
import br.com.family.manutencao_preventiva.domain.model.Veiculo;
import br.com.family.manutencao_preventiva.domain.model.Viagem;
import br.com.family.manutencao_preventiva.dto.response.ChecklistResponseDTO;
import br.com.family.manutencao_preventiva.dto.response.ViagemResponseDTO;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-18T01:15:46-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class ViagemMapperImpl extends ViagemMapper {

    @Override
    public ViagemResponseDTO toResponseDTO(Viagem viagem) {
        if ( viagem == null ) {
            return null;
        }

        String placaVeiculo = null;
        String nomeMotorista = null;
        Long id = null;
        Integer kmSaida = null;
        StatusViagem status = null;
        LocalDateTime dataInicio = null;
        LocalDateTime dataFim = null;

        placaVeiculo = viagemVeiculoPlaca( viagem );
        nomeMotorista = viagemMotoristaNome( viagem );
        id = viagem.getId();
        kmSaida = viagem.getKmSaida();
        status = viagem.getStatus();
        dataInicio = viagem.getDataInicio();
        dataFim = viagem.getDataFim();

        ChecklistResponseDTO checklistAtual = mapUltimoChecklist(viagem);

        ViagemResponseDTO viagemResponseDTO = new ViagemResponseDTO( id, placaVeiculo, nomeMotorista, kmSaida, status, dataInicio, dataFim, checklistAtual );

        return viagemResponseDTO;
    }

    private String viagemVeiculoPlaca(Viagem viagem) {
        Veiculo veiculo = viagem.getVeiculo();
        if ( veiculo == null ) {
            return null;
        }
        return veiculo.getPlaca();
    }

    private String viagemMotoristaNome(Viagem viagem) {
        Motorista motorista = viagem.getMotorista();
        if ( motorista == null ) {
            return null;
        }
        return motorista.getNome();
    }
}
