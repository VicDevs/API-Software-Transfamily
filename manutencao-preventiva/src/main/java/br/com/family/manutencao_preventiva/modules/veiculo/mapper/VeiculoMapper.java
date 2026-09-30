package br.com.family.manutencao_preventiva.modules.veiculo.mapper;

import br.com.family.manutencao_preventiva.dto.request.VeiculoRequestDTO;
import br.com.family.manutencao_preventiva.dto.response.VeiculoResponseDTO;
import br.com.family.manutencao_preventiva.modules.veiculo.domain.Veiculo;
import br.com.family.manutencao_preventiva.modules.veiculo.repository.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class VeiculoMapper {

    @Autowired
    protected VeiculoRepository veiculoRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    public abstract Veiculo toEntity(VeiculoRequestDTO dto);

    @Mapping(source = "ativo", target = "ativo")
    public abstract VeiculoResponseDTO toResponseDTO(Veiculo veiculo);

    @Mapping(target = "id", ignore = true)
    public abstract void updateEntityFromDto(VeiculoRequestDTO dto, @MappingTarget Veiculo veiculo);

    public Veiculo mapVeiculo(Long id) {
       return id == null ? null : veiculoRepository.findById(id)
               .orElseThrow(() -> new EntityNotFoundException("Veiculo não encontrado"));
    }
}
