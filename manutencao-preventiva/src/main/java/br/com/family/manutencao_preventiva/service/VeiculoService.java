package br.com.family.manutencao_preventiva.service;

import br.com.family.manutencao_preventiva.domain.model.Veiculo;
import br.com.family.manutencao_preventiva.dto.request.VeiculoRequestDTO;
import br.com.family.manutencao_preventiva.dto.response.VeiculoResponseDTO;
import br.com.family.manutencao_preventiva.mapper.VeiculoMapper;
import br.com.family.manutencao_preventiva.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final VeiculoMapper  veiculoMapper;

    @Transactional
    public VeiculoResponseDTO criar(VeiculoRequestDTO dto) {
        System.out.println("VALOR NO DTO: " + dto.kmAtual());

        Veiculo veiculo = veiculoMapper.toEntity(dto);
        System.out.println("VALOR NA ENTIDADE: " + veiculo.getKmAtual());

        return veiculoMapper.toResponseDTO(veiculoRepository.save(veiculo));
    }

    @Transactional(readOnly = true)
    public List<VeiculoResponseDTO> listar() {
        return veiculoRepository.findAll().stream()
                .map(veiculoMapper::toResponseDTO)
                .toList();
    }
}
