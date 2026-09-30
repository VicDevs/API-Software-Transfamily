package br.com.family.manutencao_preventiva.modules.veiculo.repository;

import br.com.family.manutencao_preventiva.domain.enums.StatusVeiculo;
import br.com.family.manutencao_preventiva.modules.veiculo.domain.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo,Long> {

    long countByStatus(StatusVeiculo status);
}
