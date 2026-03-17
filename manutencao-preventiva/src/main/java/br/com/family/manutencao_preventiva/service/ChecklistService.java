package br.com.family.manutencao_preventiva.service;

import br.com.family.manutencao_preventiva.domain.enums.RespostaItem;
import br.com.family.manutencao_preventiva.domain.enums.StatusChecklist;
import br.com.family.manutencao_preventiva.domain.model.*;
import br.com.family.manutencao_preventiva.dto.request.ChecklistRequestDTO;
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

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChecklistService {

    private final ChecklistRepository checklistRepository;
    private final ChecklistMapper checklistMapper;
    private final VeiculoMapper veiculoMapper;
    private final CheklistTemplateMapper templateMapper;

    @Transactional
    public ChecklistResponseDTO iniciar(ChecklistRequestDTO dto, Motorista motorista) {

        return checklistRepository.findByVeiculoIdAndStatus(dto.veiculoId(), StatusChecklist.ABERTO)
                .map(checklistMapper::toResponseDTO)
                .orElseGet(() -> {

                    Veiculo veiculo = veiculoMapper.mapVeiculo(dto.veiculoId());
                    ChecklistTemplate template = templateMapper.mapTemplate(dto.templateId());

                    veiculo.validarNovaQuilometragem(dto.kmAtual());

                    Checklist novoChecklist = checklistMapper.toEntity(dto, veiculo, motorista, template);

                    template.getItens().forEach(itemTemplate -> {
                        ChecklistItem novoItem = checklistMapper.toChecklistItem(itemTemplate);
                        novoChecklist.addItem(novoItem);
                    });

                    return checklistMapper.toResponseDTO(checklistRepository.save(novoChecklist));
                });
    }

    @Transactional
    public Checklist salvarEFinalizar(Long checklistId, ChecklistUpdateDTO lote) {

        Checklist checklist = checklistMapper.mapChecklist(checklistId);

        if (checklist.getStatus() != StatusChecklist.ABERTO) {
            throw new BusinessException("Este checklist já não está mais aberto para edições.");
        }

        var itensMap = checklist.getItens().stream()
                .collect(Collectors.toMap(ChecklistItem::getId, item -> item));

        for (var resp : lote.respostas()) {
            ChecklistItem item = itensMap.get(resp.itemId());
            if (item != null) {
                item.setRespostaItem(resp.resposta());
                item.setObservacao(resp.observacao());
            }
        }

        boolean possuiPendencias = checklist.getItens().stream()
                .anyMatch(item -> item.getRespostaItem() == RespostaItem.PENDENTE);

        if (possuiPendencias) {
            throw new BusinessException("Não é possível finalizar: ainda existem itens pendentes.");
        }

        checklist.finalizar();

        checklist.getVeiculo().atualizarQuilometragem(checklist.getKmAtual());

        return checklistRepository.save(checklist);
    }

}
