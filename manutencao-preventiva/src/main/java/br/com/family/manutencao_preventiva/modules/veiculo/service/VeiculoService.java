package br.com.family.manutencao_preventiva.modules.veiculo.service;

import br.com.family.manutencao_preventiva.domain.enums.StatusVeiculo;
import br.com.family.manutencao_preventiva.modules.veiculo.domain.model.Veiculo;
import br.com.family.manutencao_preventiva.dto.request.VeiculoRequestDTO;
import br.com.family.manutencao_preventiva.dto.response.VeiculoProntuarioResumoDTO;
import br.com.family.manutencao_preventiva.dto.response.VeiculoResponseDTO;
import br.com.family.manutencao_preventiva.exception.BusinessException;
import br.com.family.manutencao_preventiva.modules.veiculo.mapper.VeiculoMapper;
import br.com.family.manutencao_preventiva.modules.veiculo.repository.VeiculoRepository;
import br.com.family.manutencao_preventiva.service.ViagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final VeiculoMapper  veiculoMapper;
    private final ViagemService viagemService;

    @Transactional
    public VeiculoResponseDTO criar(VeiculoRequestDTO dto) {
        System.out.println("VALOR NO DTO: " + dto.kmAtual());

        Veiculo veiculo = veiculoMapper.toEntity(dto);

        return veiculoMapper.toResponseDTO(veiculoRepository.save(veiculo));
    }

    @Transactional(readOnly = true)
    public List<VeiculoResponseDTO> listar() {
        return veiculoRepository.findAll().stream()
                .map(veiculoMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public Veiculo buscarPorId(Long id) {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Veículo não encontrado"));
    }

    @Transactional
    public long contarFrota() {
        return veiculoRepository.count();
    }

    @Transactional
    public long veiculosEmRota() {
        return veiculoRepository.countByStatus(StatusVeiculo.EM_USO);
    }

    @Transactional
    public long veiculosEmManutencao () {
        return veiculoRepository.countByStatus(StatusVeiculo.EM_MANUTENCAO);
    }

    @Transactional
    public VeiculoProntuarioResumoDTO buscarResumoProntuario(Long veiculoId) {
        Veiculo veiculo = veiculoRepository.findById(veiculoId)
                .orElseThrow(() -> new BusinessException("Veículo não encontrado."));

        LocalDateTime inicioDoMes = YearMonth.now().atDay(1).atStartOfDay();

        Integer viagens = viagemService.contarViagensDoVeiculoNoMes(veiculoId, inicioDoMes);
        Long kmRodado = viagemService.somarKmDoVeiculoNoMes(veiculoId, inicioDoMes);

        viagens = (viagens != null) ? viagens : 0;
        kmRodado = (kmRodado != null) ? kmRodado : 0L;

        return new VeiculoProntuarioResumoDTO(
                veiculo.getId(),
                veiculo.getPlaca(),
                veiculo.getModelo(),
                veiculo.getMarca(),
                veiculo.getAno(),
                veiculo.getKmAtual(),
                veiculo.getStatus().name(),
                viagens,
                kmRodado
        );
    }
}
