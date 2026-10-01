package br.com.family.manutencao_preventiva.modules.checklistTemplate.domain.model;

import br.com.family.manutencao_preventiva.domain.enums.TipoVeiculo;
import br.com.family.manutencao_preventiva.domain.model.ChecklistTemplateItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "checklist_template")
@Getter
@NoArgsConstructor
public class ChecklistTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Setter
    private String nome;

    @Column(nullable = false)
    @Setter
    private String descricao;

    @Column(nullable = false)
    private boolean ativo = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Setter
    private TipoVeiculo tipoVeiculo;

    @OneToMany(mappedBy = "checklistTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("ordem ASC")
    private List<ChecklistTemplateItem> itens = new ArrayList<>();

    public ChecklistTemplate(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public void setItens(List<ChecklistTemplateItem> novosItens) {
        this.itens.clear();

        if (novosItens != null) {
            novosItens.forEach(this::addItem);

        }
    }

    public void addItem(ChecklistTemplateItem item) {
        this.itens.add(item);
        item.setChecklistTemplate(this);

        if (item.getOrdem() == null) {
            item.setOrdem(this.itens.size() + 1);
        }
    }

    public void removeItem(ChecklistTemplateItem item) {
        this.itens.remove(item);
        item.setChecklistTemplate(null);
    }

    public void desativar() { this.ativo = false; }
    public void ativar() { this.ativo = true; }
}