package br.com.family.manutencao_preventiva.dto.response;

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
