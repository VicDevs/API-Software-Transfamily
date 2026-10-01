package br.com.family.manutencao_preventiva.modules.checklistTemplate.mapper;

import br.com.family.manutencao_preventiva.domain.enums.NivelCriticidade;
import br.com.family.manutencao_preventiva.domain.enums.TipoVeiculo;
import br.com.family.manutencao_preventiva.domain.model.ChecklistTemplateItem;
import br.com.family.manutencao_preventiva.dto.request.ChecklistTemplateItemDTO;
import br.com.family.manutencao_preventiva.dto.response.ChecklistTemplateItemResponseDTO;
import br.com.family.manutencao_preventiva.modules.checklistTemplate.domain.model.ChecklistTemplate;
import br.com.family.manutencao_preventiva.modules.checklistTemplate.dto.ChecklistTemplateRequestDTO;
import br.com.family.manutencao_preventiva.modules.checklistTemplate.dto.ChecklistTemplateResponseDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-30T22:53:06-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class CheklistTemplateMapperImpl extends CheklistTemplateMapper {

    @Override
    public ChecklistTemplate toEntity(ChecklistTemplateRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ChecklistTemplate checklistTemplate = new ChecklistTemplate();

        checklistTemplate.setNome( dto.nome() );
        checklistTemplate.setDescricao( dto.descricao() );
        checklistTemplate.setTipoVeiculo( dto.tipoVeiculo() );

        vincularItens( checklistTemplate, dto );

        return checklistTemplate;
    }

    @Override
    public ChecklistTemplateResponseDTO toResponseDTO(ChecklistTemplate entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String nome = null;
        String descricao = null;
        TipoVeiculo tipoVeiculo = null;
        List<ChecklistTemplateItemResponseDTO> itens = null;

        id = entity.getId();
        nome = entity.getNome();
        descricao = entity.getDescricao();
        tipoVeiculo = entity.getTipoVeiculo();
        itens = checklistTemplateItemListToChecklistTemplateItemResponseDTOList( entity.getItens() );

        ChecklistTemplateResponseDTO checklistTemplateResponseDTO = new ChecklistTemplateResponseDTO( id, nome, descricao, tipoVeiculo, itens );

        return checklistTemplateResponseDTO;
    }

    @Override
    public ChecklistTemplateItem toItemEntity(ChecklistTemplateItemDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ChecklistTemplateItem checklistTemplateItem = new ChecklistTemplateItem();

        checklistTemplateItem.setDescricao( dto.descricao() );
        checklistTemplateItem.setOrdem( dto.ordem() );
        checklistTemplateItem.setCriticidade( dto.criticidade() );

        return checklistTemplateItem;
    }

    protected ChecklistTemplateItemResponseDTO checklistTemplateItemToChecklistTemplateItemResponseDTO(ChecklistTemplateItem checklistTemplateItem) {
        if ( checklistTemplateItem == null ) {
            return null;
        }

        Long id = null;
        String descricao = null;
        Integer ordem = null;
        NivelCriticidade criticidade = null;

        id = checklistTemplateItem.getId();
        descricao = checklistTemplateItem.getDescricao();
        ordem = checklistTemplateItem.getOrdem();
        criticidade = checklistTemplateItem.getCriticidade();

        ChecklistTemplateItemResponseDTO checklistTemplateItemResponseDTO = new ChecklistTemplateItemResponseDTO( id, descricao, ordem, criticidade );

        return checklistTemplateItemResponseDTO;
    }

    protected List<ChecklistTemplateItemResponseDTO> checklistTemplateItemListToChecklistTemplateItemResponseDTOList(List<ChecklistTemplateItem> list) {
        if ( list == null ) {
            return null;
        }

        List<ChecklistTemplateItemResponseDTO> list1 = new ArrayList<ChecklistTemplateItemResponseDTO>( list.size() );
        for ( ChecklistTemplateItem checklistTemplateItem : list ) {
            list1.add( checklistTemplateItemToChecklistTemplateItemResponseDTO( checklistTemplateItem ) );
        }

        return list1;
    }
}
