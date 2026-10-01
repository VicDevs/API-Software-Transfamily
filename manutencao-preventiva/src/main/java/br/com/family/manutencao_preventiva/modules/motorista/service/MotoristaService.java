package br.com.family.manutencao_preventiva.modules.motorista.service;

import br.com.family.manutencao_preventiva.domain.enums.UserRole;
import br.com.family.manutencao_preventiva.modules.motorista.domain.model.Motorista;
import br.com.family.manutencao_preventiva.modules.motorista.dto.MotoristaRequestDTO;
import br.com.family.manutencao_preventiva.modules.motorista.dto.MotoristaResponseDTO;
import br.com.family.manutencao_preventiva.modules.motorista.mapper.MotoristaMapper;
import br.com.family.manutencao_preventiva.modules.motorista.repository.MotoristaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MotoristaService {

    private final MotoristaRepository motoristaRepository;
    private final MotoristaMapper  motoristaMapper;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MotoristaResponseDTO criar(MotoristaRequestDTO dto) {

        Motorista motorista = motoristaMapper.toEntity(dto);

        String cpfLimpo = motorista.getCpf().replaceAll("\\D", "");
        String senhaInicial = cpfLimpo.substring(cpfLimpo.length() - 4);
        String senhaCriptografada = passwordEncoder.encode(senhaInicial);

        motorista.setPassword(senhaCriptografada);

        motorista.setRole(UserRole.MOTORISTA);

        return motoristaMapper.toResponseDTO(motoristaRepository.save(motorista));
    }

    @Transactional(readOnly = true)
    public List<MotoristaResponseDTO> listar() {
        return motoristaRepository.findAll().stream()
                .map(motoristaMapper::toResponseDTO)
                .toList();
    }
}
