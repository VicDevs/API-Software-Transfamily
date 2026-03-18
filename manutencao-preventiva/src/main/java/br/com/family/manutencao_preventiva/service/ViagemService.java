package br.com.family.manutencao_preventiva.service;
import br.com.family.manutencao_preventiva.domain.enums.StatusChecklist;
import br.com.family.manutencao_preventiva.domain.enums.StatusViagem;
import br.com.family.manutencao_preventiva.domain.model.*;
import br.com.family.manutencao_preventiva.dto.request.ChecklistRequestDTO;
import br.com.family.manutencao_preventiva.dto.response.ViagemResponseDTO;
import br.com.family.manutencao_preventiva.exception.BusinessException;
import br.com.family.manutencao_preventiva.mapper.ChecklistMapper;
import br.com.family.manutencao_preventiva.mapper.ViagemMapper;
import br.com.family.manutencao_preventiva.repository.ChecklistTemplateRepository;
import br.com.family.manutencao_preventiva.repository.VeiculoRepository;
import br.com.family.manutencao_preventiva.repository.ViagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ViagemService {

    private final ViagemRepository viagemRepository;
    private final VeiculoRepository veiculoRepository;
    private final ChecklistTemplateRepository templateRepository;

    private final ChecklistMapper checklistMapper;
    private final ViagemMapper viagemMapper;

    @Transactional
    public ViagemResponseDTO iniciarViagem(ChecklistRequestDTO dto, Motorista motorista) {

        var veiculoOcupado = viagemRepository.findByVeiculoIdAndStatus(dto.veiculoId(), StatusViagem.EM_CURSO);

        if (veiculoOcupado.isPresent()) {
            throw new BusinessException("Este veículo já possui uma viagem em curso.");
        }

        Veiculo veiculo = veiculoRepository.findById(dto.veiculoId())
                .orElseThrow(() -> new BusinessException("Veículo não encontrado"));

        ChecklistTemplate template = templateRepository.findById(dto.templateId())
                .orElseThrow(() -> new BusinessException("Template não encontrado"));

        veiculo.validarNovaQuilometragem(dto.kmAtual());

        Viagem viagem = new Viagem();
        viagem.setVeiculo(veiculo);
        viagem.setMotorista(motorista);
        viagem.setKmSaida(dto.kmAtual());

        Checklist checklistSaida = checklistMapper.toEntity(dto ,template);
        checklistSaida.setTipo("SAIDA");

        template.getItens().forEach(itemTemplate -> {
            ChecklistItem novoItem = checklistMapper.toChecklistItem(itemTemplate);
            checklistSaida.addItem(novoItem);
        });

        viagem.addChecklist(checklistSaida);
        Viagem viagemSalva = viagemRepository.save(viagem);

        return viagemMapper.toResponseDTO(viagemSalva);
    }

    @Transactional
    public ViagemResponseDTO abrirChecklistRetorno(Long viagemId, ChecklistRequestDTO dto) {

        Viagem viagem = viagemRepository.findById(viagemId)
                .orElseThrow(() -> new BusinessException("Viagem não encontrada"));

        validarPreRequisitosRetorno(viagem, dto.kmAtual());

        Optional<Checklist> retornoExistente = viagem.getChecklists().stream()
                .filter(c -> "RETORNO".equals(c.getTipo()))
                .findFirst();

        if (retornoExistente.isPresent()) {
            return viagemMapper.toResponseDTO(viagem);
        }

        ChecklistTemplate template = viagem.getChecklists().stream()
                .filter(c -> "SAIDA".equals(c.getTipo()))
                .map(Checklist::getTemplate)
                .findFirst()
                .orElseThrow(() -> new BusinessException("Template de saída não encontrado para esta viagem."));

        Checklist checklistRetorno = new Checklist();
        checklistRetorno.setTipo("RETORNO");
        checklistRetorno.setKmAtual(dto.kmAtual());
        checklistRetorno.setTemplate(template);
        checklistRetorno.setViagem(viagem);

        template.getItens().forEach(itemTemp -> {
            checklistRetorno.addItem(checklistMapper.toChecklistItem(itemTemp));
        });

        viagem.addChecklist(checklistRetorno);
        viagem.setKmRetorno(dto.kmAtual());

        return viagemMapper.toResponseDTO(viagemRepository.save(viagem));
    }

    @Transactional
    public ViagemResponseDTO finalizarViagem(Long viagemId) {
        Viagem viagem = viagemRepository.findById(viagemId)
                .orElseThrow(() -> new BusinessException("Viagem não encontrada"));

        Checklist checklistRetorno = viagem.getChecklists().stream()
                .filter(c -> "RETORNO".equals(c.getTipo()))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Checklist de retorno não foi iniciado."));

        if (checklistRetorno.getStatus() != StatusChecklist.FINALIZADO) {
            throw new BusinessException("Você precisa finalizar o checklist de retorno antes de encerrar a viagem.");
        }

        viagem.setStatus(StatusViagem.CONCLUIDA);
        viagem.setDataFim(LocalDateTime.now());

        viagem.getVeiculo().setKmAtual(checklistRetorno.getKmAtual());
        viagem.setKmRetorno(checklistRetorno.getKmAtual());

        return viagemMapper.toResponseDTO(viagemRepository.save(viagem));
    }

    @Transactional(readOnly = true)
    public ViagemResponseDTO buscarViagemAtiva(Motorista motorista) {
        return viagemRepository.findByMotoristaIdAndStatus(motorista.getId(), StatusViagem.EM_CURSO)
                .map(viagemMapper::toResponseDTO)
                .orElse(null);
    }

    private void validarPreRequisitosRetorno(Viagem viagem, Integer kmRetornoDigitado) {
        if (viagem.getStatus() != StatusViagem.EM_CURSO) {
            throw new BusinessException("Não é possível abrir retorno para uma viagem com status: " + viagem.getStatus());
        }
        if (kmRetornoDigitado < viagem.getKmSaida()) {
            throw new BusinessException(String.format(
                    "KM de retorno (%d) não pode ser inferior ao KM de saída (%d).",
                    kmRetornoDigitado,
                    viagem.getKmSaida()
            ));
        }

        boolean saidaPendente = viagem.getChecklists().stream()
                .filter(c -> "SAIDA".equals(c.getTipo()))
                .anyMatch(c -> c.getStatus() == StatusChecklist.ABERTO);

        if (saidaPendente) {
            throw new BusinessException("O checklist de saída ainda está aberto. Finalize-o antes de iniciar o retorno.");
        }
    }
}

