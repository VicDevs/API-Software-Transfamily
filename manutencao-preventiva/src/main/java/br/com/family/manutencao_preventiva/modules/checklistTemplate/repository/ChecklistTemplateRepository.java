package br.com.family.manutencao_preventiva.modules.checklistTemplate.repository;

import br.com.family.manutencao_preventiva.domain.enums.TipoVeiculo;
import br.com.family.manutencao_preventiva.modules.checklistTemplate.domain.model.ChecklistTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChecklistTemplateRepository extends JpaRepository<ChecklistTemplate,Long> {

    List<ChecklistTemplate> findByAtivoTrueAndTipoVeiculo(TipoVeiculo tipo);
}
