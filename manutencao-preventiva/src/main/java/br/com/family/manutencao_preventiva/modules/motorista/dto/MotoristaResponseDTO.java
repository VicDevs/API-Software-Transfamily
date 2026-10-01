package br.com.family.manutencao_preventiva.modules.motorista.dto;

public record MotoristaResponseDTO(
        Long id,
        String nome,
        String cpf,
        String cnh,
        String categoriaCnh,
        boolean ativo,
        String resumoExibicao
) {
    public MotoristaResponseDTO {
        if (resumoExibicao == null) {
            resumoExibicao = nome + " (Cat: " + categoriaCnh + ")";
        }
    }
}
