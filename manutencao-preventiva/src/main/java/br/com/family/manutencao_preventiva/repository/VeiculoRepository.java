package br.com.family.manutencao_preventiva.repository;

import br.com.family.manutencao_preventiva.domain.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo,Long> {

}
