package br.com.family.manutencao_preventiva.security;

import br.com.family.manutencao_preventiva.domain.enums.UserRole;
import br.com.family.manutencao_preventiva.domain.model.User;
import br.com.family.manutencao_preventiva.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.findByCpf("50941333817").isEmpty()) {
            User admin = new User();
            admin.setNome("Administrador Sistema");
            admin.setCpf("50941333817");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(UserRole.ADMIN);
            usuarioRepository.save(admin);
        }
    }
}
