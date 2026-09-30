package br.com.family.manutencao_preventiva.repository;

import br.com.family.manutencao_preventiva.domain.model.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChecklistRepository extends JpaRepository<Checklist,Long> {

}
