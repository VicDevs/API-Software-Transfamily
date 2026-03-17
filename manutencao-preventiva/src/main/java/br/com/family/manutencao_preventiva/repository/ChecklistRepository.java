package br.com.family.manutencao_preventiva.repository;

import br.com.family.manutencao_preventiva.domain.enums.StatusChecklist;
import br.com.family.manutencao_preventiva.domain.model.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ChecklistRepository extends JpaRepository<Checklist,Long> {

    Optional<Checklist> findByVeiculoIdAndStatus(Long veiculoId, StatusChecklist status);
}
