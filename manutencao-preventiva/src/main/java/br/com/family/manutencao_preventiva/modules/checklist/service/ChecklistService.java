package br.com.family.manutencao_preventiva.modules.checklist.service;

import br.com.family.manutencao_preventiva.modules.checklist.domain.enums.RespostaItem;
import br.com.family.manutencao_preventiva.modules.checklist.domain.enums.StatusChecklist;
import br.com.family.manutencao_preventiva.modules.checklist.domain.model.Checklist;
import br.com.family.manutencao_preventiva.modules.checklist.domain.model.ChecklistItem;
import br.com.family.manutencao_preventiva.modules.checklist.dto.ChecklistUpdateDTO;
import br.com.family.manutencao_preventiva.modules.checklist.dto.ChecklistResponseDTO;
import br.com.family.manutencao_preventiva.exception.BusinessException;
import br.com.family.manutencao_preventiva.modules.checklist.mapper.ChecklistMapper;
import br.com.family.manutencao_preventiva.modules.checklist.repository.ChecklistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChecklistService {

    private final ChecklistRepository checklistRepository;
    private final ChecklistMapper checklistMapper;

    @Transactional(readOnly = true)
    public ChecklistResponseDTO buscarPorId(Long id) {
        Checklist checklist = checklistRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Checklist não encontrado."));

        return checklistMapper.toResponseDTO(checklist);
    }

    @Transactional
    public ChecklistResponseDTO salvarEFinalizar(Long checklistId, ChecklistUpdateDTO lote) {
        Checklist checklist = checklistMapper.mapChecklist(checklistId);

        if (checklist.getStatus() != StatusChecklist.ABERTO) {
            throw new BusinessException("Este checklist já não está mais aberto para edições.");
        }

        atualizarRespostas(checklist, lote);

        checklist.finalizar();

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
                item.setFotoPath(resp.fotoPath());
            }
        }

        if (checklist.getItens().stream().anyMatch(i -> i.getRespostaItem() == RespostaItem.PENDENTE)) {
            throw new BusinessException("Ainda existem itens pendentes.");
        }
    }
}
