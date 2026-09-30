package br.com.family.manutencao_preventiva.mapper;

import br.com.family.manutencao_preventiva.domain.model.Motorista;
import br.com.family.manutencao_preventiva.dto.request.MotoristaRequestDTO;
import br.com.family.manutencao_preventiva.dto.response.MotoristaResponseDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-30T01:21:05-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class MotoristaMapperImpl extends MotoristaMapper {

    @Override
    public Motorista toEntity(MotoristaRequestDTO request) {
        if ( request == null ) {
            return null;
        }

        Motorista motorista = new Motorista();

        motorista.setNome( request.nome() );
        motorista.setCpf( request.cpf() );
        motorista.setCnh( request.cnh() );
        motorista.setCategoriaCnh( request.categoriaCnh() );

        return motorista;
    }

    @Override
    public MotoristaResponseDTO toResponseDTO(Motorista m) {
        if ( m == null ) {
            return null;
        }

        boolean ativo = false;
        Long id = null;
        String nome = null;
        String cpf = null;
        String cnh = null;
        String categoriaCnh = null;

        ativo = m.isAtivo();
        id = m.getId();
        nome = m.getNome();
        cpf = m.getCpf();
        cnh = m.getCnh();
        categoriaCnh = m.getCategoriaCnh();

        String resumoExibicao = m.getNome() + " (Cat: " + m.getCategoriaCnh() + ")";

        MotoristaResponseDTO motoristaResponseDTO = new MotoristaResponseDTO( id, nome, cpf, cnh, categoriaCnh, ativo, resumoExibicao );

        return motoristaResponseDTO;
    }
}
