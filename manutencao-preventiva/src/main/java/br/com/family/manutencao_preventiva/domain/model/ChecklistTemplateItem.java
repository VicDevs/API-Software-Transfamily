package br.com.family.manutencao_preventiva.domain.model;

import br.com.family.manutencao_preventiva.domain.enums.NivelCriticidade;
import br.com.family.manutencao_preventiva.exception.BusinessException;
import br.com.family.manutencao_preventiva.modules.checklistTemplate.domain.model.ChecklistTemplate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "checklist_template_item")
@Getter
@Setter
@NoArgsConstructor
public class ChecklistTemplateItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private Integer ordem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelCriticidade criticidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private ChecklistTemplate checklistTemplate;

    public ChecklistTemplateItem(String descricao, int ordem, NivelCriticidade criticidade) {
        if (ordem <= 0) {
            throw new BusinessException("Ordem deve ser maior que zero");
        }
        this.descricao = descricao;
        this.ordem = ordem;
        this.criticidade = criticidade;
    }
}
