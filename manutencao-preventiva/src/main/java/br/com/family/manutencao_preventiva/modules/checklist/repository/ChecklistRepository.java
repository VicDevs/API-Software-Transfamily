package br.com.family.manutencao_preventiva.modules.checklist.repository;

import br.com.family.manutencao_preventiva.modules.checklist.domain.model.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChecklistRepository extends JpaRepository<Checklist,Long> {

}
