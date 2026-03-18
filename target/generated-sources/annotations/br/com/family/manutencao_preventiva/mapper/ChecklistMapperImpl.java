package br.com.family.manutencao_preventiva.mapper;

import br.com.family.manutencao_preventiva.domain.enums.RespostaItem;
import br.com.family.manutencao_preventiva.domain.enums.StatusChecklist;
import br.com.family.manutencao_preventiva.domain.model.Checklist;
import br.com.family.manutencao_preventiva.domain.model.ChecklistItem;
import br.com.family.manutencao_preventiva.domain.model.ChecklistTemplate;
import br.com.family.manutencao_preventiva.domain.model.ChecklistTemplateItem;
import br.com.family.manutencao_preventiva.domain.model.Motorista;
import br.com.family.manutencao_preventiva.domain.model.Veiculo;
import br.com.family.manutencao_preventiva.domain.model.Viagem;
import br.com.family.manutencao_preventiva.dto.request.ChecklistRequestDTO;
import br.com.family.manutencao_preventiva.dto.response.ChecklistItemResponseDTO;
import br.com.family.manutencao_preventiva.dto.response.ChecklistResponseDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-18T01:15:46-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class ChecklistMapperImpl extends ChecklistMapper {

    @Override
    public Checklist toEntity(ChecklistRequestDTO dto, ChecklistTemplate template) {
        if ( dto == null && template == null ) {
            return null;
        }

        Checklist checklist = new Checklist();

        if ( dto != null ) {
            checklist.setKmAtual( dto.kmAtual() );
        }
        checklist.setTemplate( template );
        checklist.setStatus( StatusChecklist.ABERTO );

        return checklist;
    }

    @Override
    public ChecklistResponseDTO toResponseDTO(Checklist checklist) {
        if ( checklist == null ) {
            return null;
        }

        String placaVeiculo = null;
        String nomeMotorista = null;
        Long viagemId = null;
        Long id = null;
        StatusChecklist status = null;
        Integer kmAtual = null;
        String tipo = null;
        List<ChecklistItemResponseDTO> itens = null;

        placaVeiculo = checklistViagemVeiculoPlaca( checklist );
        nomeMotorista = checklistViagemMotoristaNome( checklist );
        viagemId = checklistViagemId( checklist );
        id = checklist.getId();
        status = checklist.getStatus();
        kmAtual = checklist.getKmAtual();
        tipo = checklist.getTipo();
        itens = checklistItemListToChecklistItemResponseDTOList( checklist.getItens() );

        ChecklistResponseDTO checklistResponseDTO = new ChecklistResponseDTO( id, viagemId, placaVeiculo, nomeMotorista, status, kmAtual, tipo, itens );

        return checklistResponseDTO;
    }

    @Override
    public ChecklistItemResponseDTO toItemDTO(ChecklistItem item) {
        if ( item == null ) {
            return null;
        }

        String descricao = null;
        RespostaItem resposta = null;
        Long id = null;
        Integer ordem = null;
        String observacao = null;

        descricao = itemChecklistTemplateItemDescricao( item );
        resposta = item.getRespostaItem();
        id = item.getId();
        ordem = item.getOrdem();
        observacao = item.getObservacao();

        ChecklistItemResponseDTO checklistItemResponseDTO = new ChecklistItemResponseDTO( id, descricao, ordem, resposta, observacao );

        return checklistItemResponseDTO;
    }

    @Override
    public ChecklistItem toChecklistItem(ChecklistTemplateItem itemTemplate) {
        if ( itemTemplate == null ) {
            return null;
        }

        ChecklistItem checklistItem = new ChecklistItem();

        checklistItem.setOrdem( itemTemplate.getOrdem() );
        checklistItem.setChecklistTemplateItem( itemTemplate );
        checklistItem.setDescricao( itemTemplate.getDescricao() );

        checklistItem.setRespostaItem( RespostaItem.PENDENTE );

        return checklistItem;
    }

    private String checklistViagemVeiculoPlaca(Checklist checklist) {
        Viagem viagem = checklist.getViagem();
        if ( viagem == null ) {
            return null;
        }
        Veiculo veiculo = viagem.getVeiculo();
        if ( veiculo == null ) {
            return null;
        }
        return veiculo.getPlaca();
    }

    private String checklistViagemMotoristaNome(Checklist checklist) {
        Viagem viagem = checklist.getViagem();
        if ( viagem == null ) {
            return null;
        }
        Motorista motorista = viagem.getMotorista();
        if ( motorista == null ) {
            return null;
        }
        return motorista.getNome();
    }

    private Long checklistViagemId(Checklist checklist) {
        Viagem viagem = checklist.getViagem();
        if ( viagem == null ) {
            return null;
        }
        return viagem.getId();
    }

    protected List<ChecklistItemResponseDTO> checklistItemListToChecklistItemResponseDTOList(List<ChecklistItem> list) {
        if ( list == null ) {
            return null;
        }

        List<ChecklistItemResponseDTO> list1 = new ArrayList<ChecklistItemResponseDTO>( list.size() );
        for ( ChecklistItem checklistItem : list ) {
            list1.add( toItemDTO( checklistItem ) );
        }

        return list1;
    }

    private String itemChecklistTemplateItemDescricao(ChecklistItem checklistItem) {
        ChecklistTemplateItem checklistTemplateItem = checklistItem.getChecklistTemplateItem();
        if ( checklistTemplateItem == null ) {
            return null;
        }
        return checklistTemplateItem.getDescricao();
    }
}
