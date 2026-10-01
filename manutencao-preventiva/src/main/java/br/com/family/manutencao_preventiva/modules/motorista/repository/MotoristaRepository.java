package br.com.family.manutencao_preventiva.modules.motorista.repository;

import br.com.family.manutencao_preventiva.modules.motorista.domain.model.Motorista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotoristaRepository extends JpaRepository<Motorista, Long> {

}
