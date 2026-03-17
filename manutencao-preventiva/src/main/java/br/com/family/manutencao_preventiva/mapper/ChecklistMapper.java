package br.com.family.manutencao_preventiva.mapper;

import br.com.family.manutencao_preventiva.domain.model.*;
import br.com.family.manutencao_preventiva.dto.request.ChecklistRequestDTO;
import br.com.family.manutencao_preventiva.dto.response.ChecklistItemResponseDTO;
import br.com.family.manutencao_preventiva.dto.response.ChecklistResponseDTO;
import br.com.family.manutencao_preventiva.repository.ChecklistRepository;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ChecklistMapper {

    @Autowired
    protected ChecklistRepository checklistRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "veiculo", source = "veiculo")
    @Mapping(target = "motorista", source = "motorista")
    @Mapping(target = "template", source = "template")
    @Mapping(target = "kmAtual", source = "dto.kmAtual")
    @Mapping(target = "itens", ignore = true)
    public abstract Checklist toEntity(ChecklistRequestDTO dto, Veiculo veiculo, Motorista motorista, ChecklistTemplate template);

    @Mapping(source = "veiculo.placa", target = "placaVeiculo")
    @Mapping(source = "motorista.nome", target = "nomeMotorista")
    public abstract ChecklistResponseDTO toResponseDTO(Checklist checklist);

    @Mapping(source = "checklistTemplateItem.descricao", target = "descricao")
    @Mapping(source = "respostaItem", target = "resposta")
    public abstract ChecklistItemResponseDTO toItemDTO(ChecklistItem item);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "checklist", ignore = true)
    @Mapping(target = "ordem", source = "itemTemplate.ordem")
    @Mapping(target = "checklistTemplateItem", source = "itemTemplate")
    @Mapping(target = "respostaItem", constant = "PENDENTE")
    @Mapping(target = "descricao", source = "descricao")
    public abstract ChecklistItem toChecklistItem(ChecklistTemplateItem itemTemplate);

    public Checklist mapChecklist(Long id) {
        return id == null ? null : checklistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Checklist não encontrado"));
    }
}




