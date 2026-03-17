package br.com.family.manutencao_preventiva.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "motorista")
@Getter
@NoArgsConstructor
public class Motorista extends User {

    @Column(nullable = false,unique = true)
    @Setter
    private String cnh;

    @Column(nullable = false, length = 2)
    @Setter
    private  String categoriaCnh;

    @Column(nullable = false)
    private boolean ativo = true;

    public Motorista(String cnh, String categoriaCnh) {
        this.cnh = cnh;
        this.categoriaCnh = categoriaCnh;
    }

    public void desativar() {
        this.ativo = false;
    }

    public void ativar() {
        this.ativo = true;
    }
}
