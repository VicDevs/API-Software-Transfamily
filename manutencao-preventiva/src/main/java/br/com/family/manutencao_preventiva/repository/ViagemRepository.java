package br.com.family.manutencao_preventiva.repository;

import br.com.family.manutencao_preventiva.domain.enums.StatusViagem;
import br.com.family.manutencao_preventiva.domain.model.Viagem;
import br.com.family.manutencao_preventiva.dto.response.ViagemResumoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ViagemRepository extends JpaRepository<Viagem, Long> {

    boolean existsByVeiculoIdAndStatus(Long veiculoId, StatusViagem status);

    // 1. Query para a Home (Busca ativa) - JÁ CORRIGIDA
    @Query("""
        SELECT new br.com.family.manutencao_preventiva.dto.response.ViagemResumoDTO(
            v.id, 
            v.veiculo.placa, 
            v.veiculo.modelo, 
            v.dataInicio, 
            v.dataFim, 
            v.kmSaida, 
            v.kmRetorno, 
            v.status, 
            v.ultimoTipoChecklist,
            (SELECT c.status FROM Checklist c WHERE c.viagem.id = v.id AND c.tipo = v.ultimoTipoChecklist),
            (SELECT c.id FROM Checklist c WHERE c.viagem.id = v.id AND c.tipo = v.ultimoTipoChecklist)
        )
        FROM Viagem v
        WHERE v.motorista.id = :motoristaId 
        AND v.status = :status
    """)
    Optional<ViagemResumoDTO> findViagemAtivaResumo(Long motoristaId, StatusViagem status);

    // 2. Query para o Histórico (Com filtro e paginação) - CORRIGIDA AGORA
    @Query("""
        SELECT new br.com.family.manutencao_preventiva.dto.response.ViagemResumoDTO(
            v.id,
            v.veiculo.placa,
            v.veiculo.modelo,
            v.dataInicio,
            v.dataFim,
            v.kmSaida,
            v.kmRetorno,
            v.status,
            v.ultimoTipoChecklist,
            (SELECT c.status FROM Checklist c WHERE c.viagem.id = v.id AND c.tipo = v.ultimoTipoChecklist),
            (SELECT c.id FROM Checklist c WHERE c.viagem.id = v.id AND c.tipo = v.ultimoTipoChecklist)
        )
        FROM Viagem v
        WHERE v.motorista.id = :motoristaId
        AND (:inicio IS NULL OR v.dataInicio >= :inicio)
        AND (:fim IS NULL OR v.dataInicio <= :fim)
    """)
    Page<ViagemResumoDTO> findComFiltro(
            Long motoristaId,
            LocalDateTime inicio,
            LocalDateTime fim,
            Pageable pageable
    );

    // 3. Query para retorno de ID único (Pós-save) - CORRIGIDA AGORA
    @Query("""
        SELECT new br.com.family.manutencao_preventiva.dto.response.ViagemResumoDTO(
            v.id, 
            v.veiculo.placa, 
            v.veiculo.modelo, 
            v.dataInicio, 
            v.dataFim, 
            v.kmSaida, 
            v.kmRetorno, 
            v.status, 
            v.ultimoTipoChecklist,
            (SELECT c.status FROM Checklist c WHERE c.viagem.id = v.id AND c.tipo = v.ultimoTipoChecklist),
            (SELECT c.id FROM Checklist c WHERE c.viagem.id = v.id AND c.tipo = v.ultimoTipoChecklist)
        )
        FROM Viagem v 
        WHERE v.id = :id
    """)
    Optional<ViagemResumoDTO> findResumoPorId(Long id);

    List<Viagem> findByStatusOrderByDataInicioDesc(StatusViagem status);

    // 1. Conta quantas viagens CONCLUIDAS o motorista tem a partir de uma data
    @Query("SELECT COUNT(v) FROM Viagem v WHERE v.motorista.id = :motoristaId AND v.status = 'CONCLUIDA' AND v.dataInicio >= :inicioMes")
    Integer contarViagensNoMes(@Param("motoristaId") Long motoristaId, @Param("inicioMes") LocalDateTime inicioMes);

    // 2. Soma a diferença entre o KM de Retorno e o KM de Saída por motorista
    @Query("SELECT SUM(v.kmRetorno - v.kmSaida) FROM Viagem v WHERE v.motorista.id = :motoristaId AND v.status = 'CONCLUIDA' AND v.dataInicio >= :inicioMes")
    Long somarKmRodadosNoMes(@Param("motoristaId") Long motoristaId, @Param("inicioMes") LocalDateTime inicioMes);

    // 1. Conta quantas viagens o CAMINHÃO fez no mês atual
    @Query("SELECT COUNT(v) FROM Viagem v WHERE v.veiculo.id = :veiculoId AND v.status = 'CONCLUIDA' AND v.dataInicio >= :inicioMes")
    Integer contarViagensDoVeiculoNoMes(@Param("veiculoId") Long veiculoId, @Param("inicioMes") LocalDateTime inicioMes);

    // 2. Soma o KM rodado pelo CAMINHÃO no mês atual
    @Query("SELECT SUM(v.kmRetorno - v.kmSaida) FROM Viagem v WHERE v.veiculo.id = :veiculoId AND v.status = 'CONCLUIDA' AND v.dataInicio >= :inicioMes")
    Long somarKmDoVeiculoNoMes(@Param("veiculoId") Long veiculoId, @Param("inicioMes") LocalDateTime inicioMes);

    // 3. Pega a última viagem concluida dele (para exibir a placa/data no card)
    Optional<Viagem> findFirstByMotoristaIdAndStatusOrderByDataInicioDesc(Long motoristaId, StatusViagem status);
}