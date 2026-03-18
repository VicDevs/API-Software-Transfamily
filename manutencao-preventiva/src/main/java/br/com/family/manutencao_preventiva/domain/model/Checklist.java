package br.com.family.manutencao_preventiva.domain.model;

import br.com.family.manutencao_preventiva.domain.enums.StatusChecklist;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "checklist")
@Getter
@NoArgsConstructor
public class Checklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    private LocalDateTime dataConclusao;

    @Column(nullable = false)
    @Setter
    private StatusChecklist status = StatusChecklist.ABERTO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id", nullable = false)
    @Setter
    private ChecklistTemplate template;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "viagem_id")
    @Setter
    private Viagem viagem;

    @Setter
    private Integer kmAtual;

    @Setter
    private String tipo;


    @OneToMany(mappedBy = "checklist", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("ordem ASC")
    private List<ChecklistItem> itens = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.criadoEm = LocalDateTime.now();
    }

    public void finalizar() {
        this.status = StatusChecklist.FINALIZADO;
        this.dataConclusao = LocalDateTime.now();
    }

    public void addItem(ChecklistItem item) {
        if (this.itens == null) {
            this.itens = new ArrayList<>();
        }
        this.itens.add(item);
        item.setChecklist(this);
    }

    public void removeItem(ChecklistItem item) {
        this.itens.remove(item);
        item.setChecklist(null);
    }

}

