package br.com.family.manutencao_preventiva.modules.ocorrencia.repository;

import br.com.family.manutencao_preventiva.modules.ocorrencia.domain.enums.TipoOcorrencia;
import br.com.family.manutencao_preventiva.modules.ocorrencia.domain.model.Ocorrencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {
    List<Ocorrencia> findByViagemIdOrderByDataHoraDesc(Long viagemId);

    long countByTipoInAndDataHoraGreaterThanEqual(List<TipoOcorrencia> tipos, LocalDateTime dataInicio);

    List<Ocorrencia> findTop10ByOrderByDataHoraDesc();
}