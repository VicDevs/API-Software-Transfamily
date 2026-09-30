package br.com.family.manutencao_preventiva.service;

import br.com.family.manutencao_preventiva.domain.model.User;
import br.com.family.manutencao_preventiva.dto.request.AlterarSenhaDTO;
import br.com.family.manutencao_preventiva.exception.BusinessException;
import br.com.family.manutencao_preventiva.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void alterarSenha(Long usuarioId, AlterarSenhaDTO dto) {
        if (!dto.novaSenha().equals(dto.confirmacaoSenha())) {
            throw new BusinessException("A nova senha e a confirmação não conferem.");
        }

        User usuario = userRepository.findById(usuarioId)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado."));

        if (!passwordEncoder.matches(dto.senhaAtual(), usuario.getPassword())) {
            throw new BusinessException("A senha atual está incorreta.");
        }

        String novaSenhaCriptografada = passwordEncoder.encode(dto.novaSenha());
        usuario.setPassword(novaSenhaCriptografada);

        userRepository.save(usuario);
    }
}