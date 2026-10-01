package br.com.family.manutencao_preventiva.modules.dashboard.service;

import br.com.family.manutencao_preventiva.domain.enums.NivelCriticidade;
import br.com.family.manutencao_preventiva.modules.ocorrencia.domain.enums.TipoOcorrencia;
import br.com.family.manutencao_preventiva.modules.ocorrencia.domain.model.Ocorrencia;
import br.com.family.manutencao_preventiva.domain.model.Viagem;
import br.com.family.manutencao_preventiva.modules.dashboard.dto.DashboardResumoDTO;
import br.com.family.manutencao_preventiva.modules.ocorrencia.dto.OcorrenciaResumoDTO;
import br.com.family.manutencao_preventiva.dto.response.ViagemAtivaDTO;
import br.com.family.manutencao_preventiva.modules.ocorrencia.service.OcorrenciaService;
import br.com.family.manutencao_preventiva.modules.veiculo.service.VeiculoService;
import br.com.family.manutencao_preventiva.service.ViagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final VeiculoService  veiculoService;
    private final OcorrenciaService ocorrenciaService;
    private final ViagemService viagemService;

    @Transactional(readOnly = true)
    public List<ViagemAtivaDTO> buscarViagensAtivas() {
        List<Viagem> viagens = viagemService.viagensAtivas();

        return viagens.stream()
                .map(v -> new ViagemAtivaDTO(
                        v.getId(),
                        v.getMotorista().getNome(),
                        v.getVeiculo().getPlaca(),
                        v.getVeiculo().getModelo(),
                        v.getDataInicio()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public DashboardResumoDTO gerarResumo() {

        long frotaTotal = veiculoService.contarFrota();
        long veiculosEmRota = veiculoService.veiculosEmRota();
        long veiculosEmManutencao = veiculoService.veiculosEmManutencao();

        LocalDateTime inicioDoDia = LocalDate.now().atStartOfDay();

        List<TipoOcorrencia> tiposCriticos = Arrays.stream(TipoOcorrencia.values())
                .filter(tipo -> tipo.getCriticidadePadrao() == NivelCriticidade.ALTA)
                .toList();

        long alertasCriticosHoje = ocorrenciaService.countByTipoInAndDataHoraGreaterThanEqual(tiposCriticos ,inicioDoDia);

        return new DashboardResumoDTO(
                frotaTotal,
                veiculosEmRota,
                veiculosEmManutencao,
                alertasCriticosHoje
        );
    }

    @Transactional(readOnly = true)
    public List<OcorrenciaResumoDTO> buscarOcorrenciasRecentes() {
        List<Ocorrencia> recentes = ocorrenciaService.findTop10ByOrderByDataHoraDesc();

        return recentes.stream()
                .map(o -> new OcorrenciaResumoDTO(
                        o.getId(),
                        o.getTipo().name(),
                        o.getTipo().getCriticidadePadrao(),
                        o.getDescricao(),
                        o.getViagem().getVeiculo().getPlaca(),
                        o.getViagem().getMotorista().getNome(),
                        o.getDataHora()
                ))
                .toList();
    }
}