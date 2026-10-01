package br.com.family.manutencao_preventiva.modules.motorista.mapper;

import br.com.family.manutencao_preventiva.modules.motorista.domain.model.Motorista;
import br.com.family.manutencao_preventiva.modules.motorista.dto.MotoristaRequestDTO;
import br.com.family.manutencao_preventiva.modules.motorista.dto.MotoristaResponseDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-01T00:33:09-0300",
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
