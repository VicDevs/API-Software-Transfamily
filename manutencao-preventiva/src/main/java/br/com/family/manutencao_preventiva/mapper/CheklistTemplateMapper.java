package br.com.family.manutencao_preventiva.mapper;

import br.com.family.manutencao_preventiva.domain.model.ChecklistTemplate;
import br.com.family.manutencao_preventiva.domain.model.ChecklistTemplateItem;
import br.com.family.manutencao_preventiva.dto.request.ChecklistTemplateItemDTO;
import br.com.family.manutencao_preventiva.dto.request.ChecklistTemplateRequestDTO;
import br.com.family.manutencao_preventiva.dto.response.ChecklistTemplateResponseDTO;
import br.com.family.manutencao_preventiva.repository.ChecklistTemplateRepository;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(componentModel = "spring")
public abstract class CheklistTemplateMapper {

    @Autowired
    protected ChecklistTemplateRepository templateRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "itens", ignore = true)
    public abstract ChecklistTemplate toEntity(ChecklistTemplateRequestDTO dto);

    public abstract ChecklistTemplateResponseDTO toResponseDTO(ChecklistTemplate entity);

    public abstract ChecklistTemplateItem toItemEntity(ChecklistTemplateItemDTO dto);

    public ChecklistTemplate mapTemplate(Long id) {
        return id == null ? null : templateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Template não encontrado"));
    }

    @AfterMapping
    protected void vincularItens(@MappingTarget ChecklistTemplate entity, ChecklistTemplateRequestDTO dto) {
        if (dto.itens() != null && !dto.itens().isEmpty()) {
            dto.itens().forEach(itemDto -> {
                ChecklistTemplateItem itemEntity = toItemEntity(itemDto);

                entity.addItem(itemEntity);
            });
        }
    }
}
