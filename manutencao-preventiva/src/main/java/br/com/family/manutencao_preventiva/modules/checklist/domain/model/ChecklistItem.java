package br.com.family.manutencao_preventiva.modules.checklist.domain.model;

import br.com.family.manutencao_preventiva.domain.enums.NivelCriticidade;
import br.com.family.manutencao_preventiva.modules.checklist.domain.enums.RespostaItem;
import br.com.family.manutencao_preventiva.domain.model.ChecklistTemplateItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "checklist_item")
@Getter @Setter
@NoArgsConstructor
public class ChecklistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RespostaItem respostaItem;

    private String observacao;

    @ManyToOne(fetch = FetchType.LAZY)
    private Checklist checklist;

    @Column(nullable = false)
    private Integer ordem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_item_id")
    private ChecklistTemplateItem checklistTemplateItem;

    @Column(nullable = false)
    private String descricao;

    @Setter
    private NivelCriticidade criticidade;

    private String fotoPath;

    public ChecklistItem(RespostaItem respostaItem, String observacao, String fotoPath) {
        this.respostaItem = respostaItem;
        this.observacao = observacao;
        this.fotoPath = fotoPath;
    }
}
