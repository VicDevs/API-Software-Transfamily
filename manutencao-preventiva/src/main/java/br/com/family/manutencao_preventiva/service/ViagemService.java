package br.com.family.manutencao_preventiva.service;

import br.com.family.manutencao_preventiva.domain.enums.*;
import br.com.family.manutencao_preventiva.modules.checklist.domain.enums.StatusChecklist;
import br.com.family.manutencao_preventiva.modules.checklist.domain.model.Checklist;
import br.com.family.manutencao_preventiva.domain.model.ChecklistTemplate;
import br.com.family.manutencao_preventiva.domain.model.Motorista;
import br.com.family.manutencao_preventiva.domain.model.Viagem;
import br.com.family.manutencao_preventiva.modules.checklist.dto.ChecklistAtualDTO;
import br.com.family.manutencao_preventiva.modules.checklist.dto.ChecklistRequestDTO;
import br.com.family.manutencao_preventiva.dto.response.*;
import br.com.family.manutencao_preventiva.exception.BusinessException;
import br.com.family.manutencao_preventiva.modules.checklist.mapper.ChecklistMapper;
import br.com.family.manutencao_preventiva.modules.veiculo.domain.model.Veiculo;
import br.com.family.manutencao_preventiva.modules.veiculo.mapper.VeiculoMapper;
import br.com.family.manutencao_preventiva.repository.ChecklistTemplateRepository;
import br.com.family.manutencao_preventiva.repository.ViagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ViagemService {

    private final ViagemRepository viagemRepository;

    private final VeiculoMapper veiculoMapper;
    private final ChecklistTemplateRepository templateRepository;

    private final ChecklistMapper checklistMapper;

    @Transactional
    public ViagemDetalhadaDTO iniciarViagem(ChecklistRequestDTO dto, Motorista motorista) {

        if (viagemRepository.existsByVeiculoIdAndStatus(dto.veiculoId(), StatusViagem.EM_CURSO)) {
            throw new BusinessException("Este veículo já possui uma viagem em curso.");
        }

        Veiculo veiculo = veiculoMapper.mapVeiculo(dto.veiculoId());

        ChecklistTemplate template = templateRepository.findById(dto.templateId())
                .orElseThrow(() -> new BusinessException("Template não encontrado"));

        veiculo.validarNovaQuilometragem(dto.kmAtual());

        veiculo.setStatus(StatusVeiculo.EM_USO);


        // 2. Criação da Viagem
        Viagem viagem = new Viagem();
        viagem.setVeiculo(veiculo);
        viagem.setMotorista(motorista);
        viagem.setKmSaida(dto.kmAtual());
        viagem.setStatus(StatusViagem.EM_CURSO);
        viagem.setUltimoTipoChecklist("SAIDA");

        Checklist checklistSaida = new Checklist();
        checklistSaida.setTipo("SAIDA");
        checklistSaida.setKmAtual(dto.kmAtual());
        checklistSaida.setTemplate(template);
        checklistSaida.setViagem(viagem);
        checklistSaida.setStatus(StatusChecklist.ABERTO);

        template.getItens().forEach(itemTemplate -> {
            checklistSaida.addItem(checklistMapper.toChecklistItem(itemTemplate));
        });

        viagem.addChecklist(checklistSaida);

        Viagem viagemSalva = viagemRepository.save(viagem);
        return montarDetalhado(viagemSalva);
    }

    @Transactional
    public ViagemDetalhadaDTO abrirChecklistRetorno(Long viagemId, ChecklistRequestDTO dto) {

        Viagem viagem = viagemRepository.findById(viagemId)
                .orElseThrow(() -> new BusinessException("Viagem não encontrada"));

        validarPreRequisitosRetorno(viagem, dto.kmAtual());

        Optional<Checklist> retornoExistente = viagem.getChecklists().stream()
                .filter(c -> "RETORNO".equals(c.getTipo()))
                .findFirst();

        if (retornoExistente.isPresent()) {
            return montarDetalhado(viagem);
        }

        ChecklistTemplate template = viagem.getChecklists().stream()
                .filter(c -> "SAIDA".equals(c.getTipo()))
                .map(Checklist::getTemplate)
                .findFirst()
                .orElseThrow(() -> new BusinessException("Template de saída não encontrado."));

        // Cria o Checklist de RETORNO
        Checklist checklistRetorno = new Checklist();
        checklistRetorno.setTipo("RETORNO");
        checklistRetorno.setKmAtual(dto.kmAtual());
        checklistRetorno.setTemplate(template);
        checklistRetorno.setViagem(viagem);
        checklistRetorno.setStatus(StatusChecklist.ABERTO);

        template.getItens().forEach(itemTemp -> {
            checklistRetorno.addItem(checklistMapper.toChecklistItem(itemTemp));
        });

        viagem.addChecklist(checklistRetorno);
        viagem.setUltimoTipoChecklist("RETORNO");
        viagem.setKmRetorno(dto.kmAtual());

        Viagem viagemSalva = viagemRepository.save(viagem);
        return montarDetalhado(viagemSalva);
    }

    @Transactional
    public Viagem buscarPorId(long id) {
        return viagemRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Viagem não encontrada."));
    }

    @Transactional
    public ViagemResumoDTO finalizarViagem(Long viagemId) {
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
        viagem.setKmRetorno(checklistRetorno.getKmAtual());

        viagem.getVeiculo().setKmAtual(checklistRetorno.getKmAtual());

        boolean temProblemaCritico = checklistRetorno.getItens().stream()
                .anyMatch(item -> item.getRespostaItem() == RespostaItem.NAO_OK
                        && item.getCriticidade() == NivelCriticidade.ALTA);

        if (temProblemaCritico) {
            viagem.getVeiculo().setStatus(StatusVeiculo.EM_MANUTENCAO);
        } else {
            viagem.getVeiculo().setStatus(StatusVeiculo.DISPONIVEL);
        }

        viagemRepository.save(viagem);

        return viagemRepository.findResumoPorId(viagemId)
                .orElseThrow(() -> new BusinessException("Erro ao recuperar resumo da viagem finalizada"));
    }

    @Transactional(readOnly = true)
    public ViagemResumoDTO buscarViagemAtiva(Long motoristaId) {
        return viagemRepository.findViagemAtivaResumo(motoristaId, StatusViagem.EM_CURSO)
                .orElse(null);
    }

    @Transactional
    public List<Viagem> viagensAtivas() {
        return viagemRepository.findByStatusOrderByDataInicioDesc(StatusViagem.EM_CURSO);
    }

    @Transactional(readOnly = true)
    public Page<ViagemResumoDTO> listarHistorico(
            Long motoristaId,
            LocalDate inicio,
            LocalDate fim,
            int pagina,
            int tamanho
    ) {
        LocalDateTime dataInicio = (inicio != null) ? inicio.atStartOfDay() : null;

        LocalDateTime dataFim = (fim != null) ? fim.atTime(LocalTime.MAX) : null;

        Pageable pageable = PageRequest.of(pagina, tamanho, Sort.by("dataInicio").descending());

        return viagemRepository.findComFiltro(motoristaId, dataInicio, dataFim, pageable);
    }

    public EstatisticasMotoristaDTO buscarEstatisticasDoMotorista(Long motoristaId) {
        LocalDateTime inicioDoMes = YearMonth.now().atDay(1).atStartOfDay();

        Integer viagens = viagemRepository.contarViagensNoMes(motoristaId, inicioDoMes);
        Long kmRodados = viagemRepository.somarKmRodadosNoMes(motoristaId, inicioDoMes);

        kmRodados = (kmRodados != null) ? kmRodados : 0L;
        viagens = (viagens != null) ? viagens : 0;

        Optional<Viagem> ultimaViagemOpt = viagemRepository.findFirstByMotoristaIdAndStatusOrderByDataInicioDesc(motoristaId, StatusViagem.CONCLUIDA);

        UltimaViagemResumoDTO ultimaViagemDTO = null;
        if (ultimaViagemOpt.isPresent()) {
            Viagem v = ultimaViagemOpt.get();
            ultimaViagemDTO = new UltimaViagemResumoDTO(
                    v.getId(),
                    v.getDataFim() != null ? v.getDataFim().toString() : v.getDataInicio().toString(),
                    v.getVeiculo().getPlaca(),
                    v.getVeiculo().getModelo()
            );
        }

        return new EstatisticasMotoristaDTO(viagens, kmRodados, ultimaViagemDTO);
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

    @Transactional(readOnly = true)
    public Integer contarViagensDoVeiculoNoMes(Long veiculoId, LocalDateTime inicioDoMes) {
        return viagemRepository.contarViagensDoVeiculoNoMes(veiculoId, inicioDoMes);
    }

    @Transactional(readOnly = true)
    public Long somarKmDoVeiculoNoMes(Long veiculoId, LocalDateTime inicioDoMes) {
        return viagemRepository.somarKmDoVeiculoNoMes(veiculoId, inicioDoMes);
    }


    private ViagemDetalhadaDTO montarDetalhado(Viagem viagem) {
        ViagemResumoDTO resumo = viagemRepository.findResumoPorId(viagem.getId())
                .orElseThrow(() -> new BusinessException("Erro ao gerar resumo"));

        Checklist checklistAtivo = viagem.getChecklists().stream()
                .filter(c -> c.getTipo().equals(viagem.getUltimoTipoChecklist()))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Checklist não encontrado"));

        List<ChecklistItemResponseDTO> itensDto = checklistAtivo.getItens().stream()
                .map(checklistMapper::toItemDTO)
                .toList();

        return new ViagemDetalhadaDTO(
                resumo,
                new ChecklistAtualDTO(checklistAtivo.getId(), itensDto)
        );
    }
}

