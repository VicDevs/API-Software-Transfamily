package br.com.family.manutencao_preventiva.repository;

import br.com.family.manutencao_preventiva.domain.enums.StatusViagem;
import br.com.family.manutencao_preventiva.domain.model.Viagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ViagemRepository extends JpaRepository<Viagem, Long> {

    // Busca a viagem atual de um veículo (útil para o Front-end saber se o caminhão está na rua)
    Optional<Viagem> findByVeiculoIdAndStatus(Long veiculoId, StatusViagem status);

    // Busca a viagem atual de um motorista
    Optional<Viagem> findByMotoristaIdAndStatus(Long motoristaId, StatusViagem status);

    // CONSULTA TURBO: Traz a viagem, o veículo e o motorista em um único SQL (Join Fetch)
    // Use isso para a tela de Detalhes da Viagem, economizando memória no Railway
    @Query("SELECT v FROM Viagem v " +
            "JOIN FETCH v.veiculo " +
            "JOIN FETCH v.motorista " +
            "LEFT JOIN FETCH v.checklists " +
            "WHERE v.id = :id")
    Optional<Viagem> findByIdFull(@Param("id") Long id);

    // Lista viagens por período (para seus relatórios de custos/KM)
    List<Viagem> findByDataInicioBetween(java.time.LocalDateTime inicio, java.time.LocalDateTime fim);
}
