package br.com.family.manutencao_preventiva.service;

import br.com.family.manutencao_preventiva.domain.enums.RespostaItem;
import br.com.family.manutencao_preventiva.domain.enums.StatusChecklist;
import br.com.family.manutencao_preventiva.domain.enums.StatusViagem;
import br.com.family.manutencao_preventiva.domain.model.*;
import br.com.family.manutencao_preventiva.dto.request.ChecklistUpdateDTO;
import br.com.family.manutencao_preventiva.dto.response.ChecklistResponseDTO;
import br.com.family.manutencao_preventiva.exception.BusinessException;
import br.com.family.manutencao_preventiva.mapper.ChecklistMapper;
import br.com.family.manutencao_preventiva.mapper.CheklistTemplateMapper;
import br.com.family.manutencao_preventiva.mapper.VeiculoMapper;
import br.com.family.manutencao_preventiva.repository.ChecklistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChecklistService {

    private final ChecklistRepository checklistRepository;
    private final ChecklistMapper checklistMapper;
    private final VeiculoMapper veiculoMapper;
    private final CheklistTemplateMapper templateMapper;

    @Transactional
    public ChecklistResponseDTO salvarEFinalizar(Long checklistId, ChecklistUpdateDTO lote) {
        Checklist checklist = checklistMapper.mapChecklist(checklistId);

        if (checklist.getStatus() != StatusChecklist.ABERTO) {
            throw new BusinessException("Este checklist já não está mais aberto para edições.");
        }

        atualizarRespostas(checklist, lote);

        checklist.finalizar();

        if ("RETORNO".equalsIgnoreCase(checklist.getTipo())) {
            Viagem viagem = checklist.getViagem();
            viagem.setStatus(StatusViagem.CONCLUIDA);
            viagem.setDataFim(LocalDateTime.now());
            viagem.setKmRetorno(checklist.getKmAtual());

            checklist.getViagem().getVeiculo().atualizarQuilometragem(checklist.getKmAtual());
        }

        return checklistMapper.toResponseDTO(checklistRepository.save(checklist));
    }

    private void atualizarRespostas(Checklist checklist, ChecklistUpdateDTO lote) {
        var itensMap = checklist.getItens().stream()
                .collect(Collectors.toMap(ChecklistItem::getId, item -> item));

        for (var resp : lote.respostas()) {
            ChecklistItem item = itensMap.get(resp.itemId());
            if (item != null) {
                item.setRespostaItem(resp.resposta());
                item.setObservacao(resp.observacao());
            }
        }

        if (checklist.getItens().stream().anyMatch(i -> i.getRespostaItem() == RespostaItem.PENDENTE)) {
            throw new BusinessException("Ainda existem itens pendentes.");
        }
    }
}
