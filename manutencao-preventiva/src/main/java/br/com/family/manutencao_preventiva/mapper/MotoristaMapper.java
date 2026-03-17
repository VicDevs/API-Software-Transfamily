package br.com.family.manutencao_preventiva.mapper;

import br.com.family.manutencao_preventiva.domain.model.Motorista;
import br.com.family.manutencao_preventiva.dto.request.MotoristaRequestDTO;
import br.com.family.manutencao_preventiva.dto.response.MotoristaResponseDTO;
import br.com.family.manutencao_preventiva.repository.MotoristaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class MotoristaMapper {

    @Autowired
    protected MotoristaRepository motoristaRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    public abstract Motorista toEntity(MotoristaRequestDTO request);

    @Mapping(target = "resumoExibicao", expression = "java(m.getNome() + \" (Cat: \" + m.getCategoriaCnh() + \")\")")
    @Mapping(source = "ativo", target = "ativo")
    public abstract MotoristaResponseDTO toResponseDTO(Motorista m);

    public Motorista mapMotorista(Long id) {
        return id == null ? null : motoristaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Motorista não encontrado"));
    }

}
