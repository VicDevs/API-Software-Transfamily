package br.com.family.manutencao_preventiva.modules.checklistTemplate.service;

import br.com.family.manutencao_preventiva.domain.enums.TipoVeiculo;
import br.com.family.manutencao_preventiva.modules.checklistTemplate.domain.model.ChecklistTemplate;
import br.com.family.manutencao_preventiva.modules.checklistTemplate.dto.ChecklistTemplateRequestDTO;
import br.com.family.manutencao_preventiva.modules.checklistTemplate.dto.ChecklistTemplateResponseDTO;
import br.com.family.manutencao_preventiva.modules.checklistTemplate.mapper.CheklistTemplateMapper;
import br.com.family.manutencao_preventiva.modules.checklistTemplate.repository.ChecklistTemplateRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChecklistTemplateService {
    private final ChecklistTemplateRepository repository;
    private final CheklistTemplateMapper mapper;

    @Transactional
    public ChecklistTemplateResponseDTO criar(ChecklistTemplateRequestDTO dto) {

        ChecklistTemplate template = mapper.toEntity(dto);

        ChecklistTemplate templateSalvo = repository.save(template);

        return mapper.toResponseDTO(templateSalvo);
    }

    @Transactional(readOnly = true)
    public List<ChecklistTemplateResponseDTO> listar() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ChecklistTemplateResponseDTO> buscarAtivosPorTipo(TipoVeiculo tipoVeiculo) {
        List<ChecklistTemplate> templates = repository.findByAtivoTrueAndTipoVeiculo(tipoVeiculo);

        if (templates.isEmpty()) {
            throw new EntityNotFoundException("Nenhum checklist ativo encontrado para " + tipoVeiculo);
        }

        return templates.stream().map(mapper::toResponseDTO).toList();
    }
}
