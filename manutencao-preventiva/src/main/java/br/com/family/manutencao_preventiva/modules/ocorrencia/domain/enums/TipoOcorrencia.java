package br.com.family.manutencao_preventiva.modules.ocorrencia.domain.enums;

import br.com.family.manutencao_preventiva.domain.enums.NivelCriticidade;
import lombok.Getter;

@Getter
public enum TipoOcorrencia {

    // 1. Ocorrências Operacionais (Caminhão parado, custo imediato)
    PNEU_AVARIADO("Problema com Pneu", NivelCriticidade.ALTA, "Acionar borracheiro ou suporte de rodovia."),
    FALHA_MECANICA("Falha Mecânica", NivelCriticidade.ALTA, "Acionar guincho ou oficina parceira mais próxima."),
    FALHA_ELETRICA("Falha Elétrica", NivelCriticidade.MEDIA, "Avaliar risco. Caminhão pode precisar parar se for farol à noite."),

    // 2. Ocorrências de Risco/Sinistro (Envolvem terceiros, seguro e B.O.)
    ACIDENTE_COLISAO("Acidente / Colisão", NivelCriticidade.ALTA, "Acionar seguradora, jurídico e verificar saúde do motorista."),
    ROUBO_FURTO("Roubo / Assalto", NivelCriticidade.ALTA, "Bloquear veículo via rastreador e acionar Polícia Militar (190)."),

    // 3. Ocorrências Logísticas (Não afeta o caminhão, mas afeta o prazo do cliente)
    VIA_INTERDITADA("Via Interditada / Bloqueio", NivelCriticidade.MEDIA, "Avisar cliente sobre atraso na entrega. Recalcular rota."),

    AVARIA_ESTETICA("Dano Estético Leve", NivelCriticidade.BAIXA, "Registrar para não cobrar do motorista na devolução."),
    OUTROS("Outros", NivelCriticidade.BAIXA, "Analisar descrição do motorista.");

    private final String descricao;
    private final NivelCriticidade criticidadePadrao;
    private final String acaoAdmin;

    TipoOcorrencia(String descricao, NivelCriticidade criticidadePadrao, String acaoAdmin) {
        this.descricao = descricao;
        this.criticidadePadrao = criticidadePadrao;
        this.acaoAdmin = acaoAdmin;
    }
}