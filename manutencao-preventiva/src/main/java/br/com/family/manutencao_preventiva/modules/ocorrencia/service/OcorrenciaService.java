package br.com.family.manutencao_preventiva.modules.ocorrencia.service;

import br.com.family.manutencao_preventiva.domain.enums.StatusViagem;
import br.com.family.manutencao_preventiva.modules.ocorrencia.domain.enums.TipoOcorrencia;
import br.com.family.manutencao_preventiva.modules.ocorrencia.domain.model.Ocorrencia;
import br.com.family.manutencao_preventiva.domain.model.Viagem;
import br.com.family.manutencao_preventiva.modules.ocorrencia.dto.OcorrenciaRequestDTO;
import br.com.family.manutencao_preventiva.modules.ocorrencia.dto.OcorrenciaResponseDTO;
import br.com.family.manutencao_preventiva.exception.BusinessException;
import br.com.family.manutencao_preventiva.modules.ocorrencia.repository.OcorrenciaRepository;
import br.com.family.manutencao_preventiva.service.ViagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OcorrenciaService {

    private final OcorrenciaRepository ocorrenciaRepository;
    private final ViagemService viagemService;

    @Transactional
    public OcorrenciaResponseDTO registrarOcorrencia(OcorrenciaRequestDTO dto) {

        Viagem viagem = viagemService.buscarPorId(dto.viagemId());

        if (viagem.getStatus() != StatusViagem.EM_CURSO) {
            throw new BusinessException("Só é possível registrar ocorrências em viagens ativas.");
        }

        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setViagem(viagem);
        ocorrencia.setTipo(dto.tipo());
        ocorrencia.setDescricao(dto.descricao());
        ocorrencia.setFotoPath(dto.fotoPath());
        ocorrencia.setDataHora(LocalDateTime.now());

        viagem.addOcorrencia(ocorrencia);

        Ocorrencia ocorrenciaSalva = ocorrenciaRepository.save(ocorrencia);

        return montarResponseDTO(ocorrenciaSalva);
    }

    @Transactional
    public long countByTipoInAndDataHoraGreaterThanEqual(List<TipoOcorrencia>tipos, LocalDateTime inicioDoDia) {
        return ocorrenciaRepository
                .countByTipoInAndDataHoraGreaterThanEqual(tipos, inicioDoDia);
    }

    @Transactional
    public List<Ocorrencia>  findTop10ByOrderByDataHoraDesc() {
        return ocorrenciaRepository.findTop10ByOrderByDataHoraDesc();
    }


    //adicionar mapper aqui!!!!!
    private OcorrenciaResponseDTO montarResponseDTO(Ocorrencia ocorrencia) {
        return new OcorrenciaResponseDTO(
                ocorrencia.getId(),
                ocorrencia.getViagem().getId(),
                ocorrencia.getTipo().name(),
                ocorrencia.getTipo().getDescricao(),
                ocorrencia.getTipo().getCriticidadePadrao().name(),
                ocorrencia.getTipo().getAcaoAdmin(),
                ocorrencia.getDescricao(),
                ocorrencia.getFotoPath(),
                ocorrencia.getDataHora()
        );
    }
}