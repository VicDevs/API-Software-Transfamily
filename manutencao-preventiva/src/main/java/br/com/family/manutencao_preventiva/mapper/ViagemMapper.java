package br.com.family.manutencao_preventiva.mapper;

import br.com.family.manutencao_preventiva.domain.model.Checklist;
import br.com.family.manutencao_preventiva.domain.model.Viagem;
import br.com.family.manutencao_preventiva.dto.response.ChecklistResponseDTO;
import br.com.family.manutencao_preventiva.dto.response.ViagemResponseDTO;
import br.com.family.manutencao_preventiva.dto.response.ViagemResumoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {ChecklistMapper.class})
public abstract class ViagemMapper {

    @Autowired
    protected ChecklistMapper checklistMapper;

    @Mapping(source = "veiculo.placa", target = "placaVeiculo")
    @Mapping(source = "motorista.nome", target = "nomeMotorista")
    @Mapping(target = "checklistAtual", expression = "java(mapUltimoChecklist(viagem))")
    public abstract ViagemResponseDTO toResponseDTO(Viagem viagem);

    @Mapping(source = "veiculo.placa", target = "placaVeiculo")
    @Mapping(source = "veiculo.modelo", target = "modeloVeiculo")
    @Mapping(source = "ultimoTipoChecklist", target = "tipoChecklist")
    public abstract ViagemResumoDTO toResumoDTO(Viagem viagem);

    protected ChecklistResponseDTO mapUltimoChecklist(Viagem viagem) {
        if (viagem.getChecklists() == null || viagem.getChecklists().isEmpty()) {
            return null;
        }
        Checklist ultimo = viagem.getChecklists().get(viagem.getChecklists().size() - 1);
        return checklistMapper.toResponseDTO(ultimo);
    }
}