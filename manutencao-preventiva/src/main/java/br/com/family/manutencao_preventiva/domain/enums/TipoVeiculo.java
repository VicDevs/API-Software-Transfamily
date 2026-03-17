package br.com.family.manutencao_preventiva.domain.enums;

public enum TipoVeiculo {
    PESADO("Caminhão/Carreta"),
    LEVE("Carro/Utilitário"),
    MAQUINA("Empilhadeira");

    private final String descricao;

    TipoVeiculo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}